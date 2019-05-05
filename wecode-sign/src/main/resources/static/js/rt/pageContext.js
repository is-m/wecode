define(["require", "jquery", "rt/logger", "rt/request"], function (require, $, log, http) {
    // https://github.com/snabbdom/snabbdom 虚拟dom
    var Page = function (id, $dom) {
        this._id = id;
        this.$dom = $dom;
    }
    var enableCache = false;
    var pageCache = {};
    var pageContextStack = [];
    var pageContextMap = {};
    var pageContextElStack = [$("#__pageContext")];

    /**
     * id:上下文ID
     * callback:回调函数 callback(page)
     */
    var _define = function (id, _components, _callback) {
        var components = _callback ? _components : [], callback = _callback || _components;
        if (!$.isArray(components)) {
            throw 'components is not array.';
        }
        if (!$.isFunction(callback)) {
            throw 'callback is not function.';
        }

        log.debug("load js module of " + id)
        var page = new Page(id);
        require(components, function () {
            callback.apply(this, [page].concat(Array.prototype.slice.call(arguments, 0)));
            pageContextStack.push(page);
            pageContextMap[id] = page;
            // pageContext.define 主要是加载内容到核心上下文，而非核心上下文的加载需要通过选中元素后$("xx").loadPage(url);
            var $pc = pageContextElStack[pageContextElStack.length - 1];
            //debugger
            page.$dom = $pc;
            var context = $pc.data("context") || {};

            $pc.data("context", $.extend(context, {id: page}));

            // TODO:等页面自动渲染部分完成后触发ready函数
            page.ready && setTimeout(page.ready, 0);
        });
    }

    var defineController = function (controllerName, componentArray, callback) {
        debugger
        var name, components = [], defineFunction, args = [].slice.call(arguments, 0);
        var argLength = args.length;
        var p1 = args[0], p2 = args[1], p3 = args[2];

        switch (argLength) {
            case 1:
                var p1IsFunction = $.isFunction(p1);
                if (!p1IsFunction || !$.isPlainObject(p1)) {
                    throw '控制器只有一个参数时，必须是函数或对象类型类型';
                }
                if (p1IsFunction) {
                    defineFunction = args[0];
                } else {
                    defineFunction = (function (obj) {
                        return function () {
                            return obj;
                        }
                    }(p1));
                }
                break;
            case 2:
                components = p1;
                defineFunction = p2;
                break;
            case 3:
                name = p1;
                components = p2;
                defineFunction = p3;
                break;
            default:
                throw '定义控制器时，不支持当前参数个数 ' + argLength;
        }
        var defineFunction = function () {
            debugger
            var page = {};
            callback && callback.apply(window, [page, $].concat([].slice.call(arguments, 0)))
            return page;
        };
        // FIXME:下面应该只注册一次，需要优化
        define(controllerName, components || [], defineFunction);
        define(components || [], defineFunction);
    }

    var _get = function (id) {
        return pageContextMap[id];
    }

    var _shutPage = function (el) {
        var $el = $(el);
        var $waitShuts = [];
        $waitShuts.push(el);

        var $childModules = $el.find("[data-module]");
        for (var i = 0; i < $childModules.length; i++) {
            $waitShuts.push($($childModules[i]));
        }

        var $shutItem = null;
        while ($shutItem = $waitShuts.pop()) {
            var context = $shutItem.data("context") || {};
            for (var pageId in context) {
                var page = context[pageId];
                console.log("shutdown js module of " + page._id);
                page.exit && page.exit();
                delete pageContextMap[page._id];
                page.$dom.removeAttr("data-module");
                pageContextStack.remove(page);
            }
            $el.removeAttr("data-module");
        }
    }

    var _controllerMap = {};

    var formatUrl = function(url){
        return typeof url != "string" || url.indexOf(appConfig.contextPath) == 0 ? url : appConfig.contextPath + url;
    };
    var _loadPage = function (el, pageUrl, controller, callback) {
        var $el = el.jquery ? el : $(el);
        var url = formatUrl(pageUrl);


        return http.doGet(url).success(function (resp) {
            var html = resp.replace(/@\{\s*(\S+)\s*\}/g, function (m, i, o, n) {
                return appConfig.contextPath + i;
            });

            if (enableCache) pageCache[url] = html;

            // 设置元素内容
            $el.html(html);

            // 检查需要自动初始化的控件并完成渲染
            // TODO:待跟app.js中同类代码重构
            $el.find("[data-x-widget]").each(function () {
                var $this = $(this);
                var widgetName = $this.data("xWidget").replace(/\./g, "/");
                require(["widget/" + widgetName], $.proxy(function (widget) {
                    // 加载完组件则初始化组件的基本内容
                    var ops = $this.data("xWidgetOption");
                    $this.xWidget(this.widgetName, ops ? ($.isPlainObject(ops) ? ops : ops.toJSON()) : {});
                }, {el: el, widgetName: widgetName}));
            });

            // 绑定控制器
            $el.find('[v-ctrl]').each(function () {
                var $ctrlEl = $(this);
                var ctrlValue = $ctrlEl.attr("v-ctrl");
                if (ctrlValue) {
                    if (_controllerMap[ctrlValue]) {
                        var clonedCtrl = $.extend(true, {$s: $ctrlEl, $page: null}, _controllerMap[ctrl]);
                        $ctrlEl.data("controller", clonedCtrl);
                        clonedCtrl.ready && clonedCtrl.ready();
                        callback && callback(true);
                    } else {
                        require([ctrlValue], function (ctrlObj) {
                            _controllerMap[ctrlValue] = ctrlObj;
                            var clonedCtrl = $.extend(true, {$s: $ctrlEl, $page: clonedCtrl}, ctrlObj);
                            debugger
                            $ctrlEl.data("controller", clonedCtrl);
                            clonedCtrl.ready && clonedCtrl.ready();
                            callback && callback(true);
                        });
                    }
                } else {
                    var ctrlValue = url.replace(/\.html$/i, function (v) { return ".js"; });
                    var ctrlName = ctrlValue.replace(/(^(\/|\\)|\.js$)/ig, '').replace(/(\/|\\)/g, ".");
                    require([ctrlValue], function (ctrlObj) {
                        debugger
                        $ctrlEl.attr("v-ctrl", ctrlName);
                        var thisCtrl = $.extend(true, {$s: $ctrlEl, $page: thisCtrl}, ctrlObj);
                        $ctrlEl.data("controller", thisCtrl);
                        thisCtrl.ready && thisCtrl.ready();
                        callback && callback(true);
                    });
                }
            });

            $el.data("inited", true);
            $el.attr("data-module", url);

            if(controller && controller.indexOf(".js")>0){
                controller = formatUrl(controller);

                require([controller],function (ctrl) {
                    if(ctrl){
                        $el.data("controller",ctrl);
                        ctrl.ready && ctrl.ready();
                        callback && callback(true);
                    }else{
                        callback && callback(true);
                        log.warn("controller is empty " + controller);
                    }
                });
            }else{
                callback && callback(true);
            }
        })
        .error(function (resp, status, xhr) {
            $el.html("<div class='col-md-12'><h2 class='center'>Page NotFound 404</h2></div>");
            $el.attr("data-module", "error");
            console.log("page context load page error!", resp, status, xhr)
            callback && callback(false);
        });
    }

    var _shutdown = function () {
        var page;
        while (page = pageContextStack.pop()) {
            console.log("shutdown js module of " + page._id);
            page.exit && page.exit();
            delete pageContextMap[page._id];
        }
        $("#__pageContext").data("context", null);
    }

    var doAction = function (action) {
        //debugger
        if (!action) {
            log.warn("非法调用页面动作");
            return;
        }

        var pageAction = null;
        for (var i = 0; i < pageContextStack.length; i++) {
            var page = pageContextStack[i];
            var attr = page[action];
            if (attr && !$.isFunction(attr)) {
                log.warn("执行指定页面动作时，找到非Function类型属性" + action)
                continue;
            }
            if (attr && pageAction) {
                log.error("执行指定页面动作时，找到多个同名动作" + action);
                return;
            }
            pageAction = attr || pageAction;
        }
        pageAction ? pageAction() : log.error("执行指定页面动作时，未找到页面动作" + action);
    }

    var listenReady = function (el, callback) {
        if (!el) throw 'el is illegal.';
        var retryCount = arguments[2] || 0;
        if (retryCount > 15) {
            throw 'listenReady faild retry count be overflow.'
        }

        if ($(el).data("inited")) {
            callback();
            return;
        }

        setTimeout(function () {
            listenReady(el, callback, retryCount + 1);
        }, 100);
    }

    /**
     * 监听页面销毁
     */
    var listenPageDestory = function () {

    }

    /**
     * 查找页面模块，并返回promiss对象
     */
    var module = function (id) {
        var dfd = $.Deferred();
        var count = 0;
        var timer = setInterval($.proxy(function () {
            var page = pageContextMap[this.id];
            if (page) {
                clearInterval(timer);
                timer = null;
                dfd.resolve(page);
            } else if (count > 20) {
                clearInterval(timer);
                timer = null;
                dfd.reject("page module not found of out search count 20");
            }
            count++;
        }, {id: id}), 100);

        return dfd;
    }

    return {
        define: _define,
        shutdown: _shutdown,
        shutPage: _shutPage,
        loadPage: _loadPage,
        get: _get,
        $do: doAction,
        listenReady: listenReady,
        module: module,
        controller: defineController
    }

});