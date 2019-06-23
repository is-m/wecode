define(["jquery", "rt/logger", "rt/request", "widget/mapper"], function ($, log, http, mapper) {

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


    var getAllHighLevelComponent = function ($el, resultArray) {
        var result = resultArray || [];
        // 递归找到最后一级往上渲染
        var $children = $el.children();
        for (var i = 0; i < $children.length; i++) {
            var $child = $children.eq(i);
            var widgetName = $child.prop("tagName").toLowerCase();
            if (widgetName.indexOf("-") > 0) {
                var componentMapper = mapper[widgetName];
                var widgetPath = componentMapper ? componentMapper.comp : ("widget/" + widgetName);
                // 如果组件位置不存在，则放到待加载的列表中
                if (result.indexOf(widgetPath) < 0) {
                    result.push(widgetPath);
                }
            } else {
                getAllHighLevelComponent($child, result);
            }
        }
        return result;
    };

    // FIXME：可能需要考虑待移动到更适合他的位置
    var renderWidgetText = function (html) {
        var dtd = $.Deferred();
        // 获取所有一级组件，以便一次性完成组件的加载后完成最后的界面渲染通知后续的处理逻辑,因为require函数在函数内使用时，需要提前加载组件
        var deepHandleComponent = function ($el) {
            // 递归找到最后一级往上渲染
            var $children = $el.children();
            for (var i = 0; i < $children.length; i++) {
                var $child = $children.eq(i);
                var widgetName = $child.prop("tagName").toLowerCase();
                if (widgetName.indexOf("-") > 0) {
                    console.log("pageContext load js widget -- " + widgetName);
                    var componentMapper = mapper[widgetName];
                    var widgetPath = componentMapper ? componentMapper.comp : ("widget/" + widgetName);
                    console.log("pageContext load js widget path --" + widgetPath);
                    // 组件采用同步加载，防止产生后面代码执行顺序问题
                    // https://blog.csdn.net/zhangjieshuaige/article/details/82260184
                    var widget = require(widgetPath);
                    if (widget.render) {
                        var widgetOption = componentMapper.parser($child);
                        console.log("pageContext load js widget option --", widgetOption);
                        widget.render($child, widgetOption); // 加载完组件则初始化组件的基本内容
                    } else {
                        alert("user component " + widget + " not found renderer");
                    }
                } else {
                    deepHandleComponent($child);
                }
            }
        };

        // 外层用元素套住，防止html不是由一个主元素包裹（有注释也不行）导致的无法正常获取内容的问题
        var $context = $("<div>" + html + "</div>");
        var compoentArray = getAllHighLevelComponent($context);
        require(compoentArray, function () {
            // 再次循环处理渲染顶层组件
            deepHandleComponent($context);
            // 处理完成后完成回调
            dtd.resolve($context.children());
        });

        return dtd;
    };
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
            page.$dom = $pc;
            var context = $pc.data("context") || {};

            $pc.data("context", $.extend(context, {id: page}));

            // TODO:等页面自动渲染部分完成后触发ready函数
            page.ready && setTimeout(page.ready, 0);
        });
    };

    var defineController = function (controllerName, componentArray, callback) {
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
            console.debug("call defineFunction");
            var page = {};
            callback && callback.apply(window, [page, $].concat([].slice.call(arguments, 0)))
            return page;
        };
        // FIXME:下面应该只注册一次，需要优化
        define(controllerName, components || [], defineFunction);
        define(components || [], defineFunction);
    };

    var _get = function (id) {
        return pageContextMap[id];
    };

    var _controllerMap = {};

    var formatUrl = function (url) {
        return typeof url != "string" || url.indexOf(appConfig.contextPath) == 0 ? url : appConfig.contextPath + url;
    };

    var defaultSelfClosingTags = ["meta", "base", "br", "hr", "img", "input", "col", "frame", "link", "area", "param", "object", "embed", "keygen", "source"];
    var _loadPage = function (el, pageUrl, controller, callback) {
        var $el = el.jquery ? el : $(el);

        shutdownOldPage($el);

        var url = formatUrl(pageUrl);


        return http.doGet(url).done(function (resp) {
            // 替换静态引用资源
            var html = resp.replace(/@\{\s*(\S+)\s*\}/g, function (m, i, o, n) {
                return appConfig.contextPath + i;
            });

            // 替换自定义标签的自闭合，防止jquery解析dom出问题
            html = html.replace(/<[^>]+\/>/g, function (matched, i) {
                var tagNameMatched = matched.match(/[^<][^\s]+/);
                if (tagNameMatched.length) {
                    var tagName = tagNameMatched[0];
                    // 如果标签名是系统的非标准闭合标签，则加上闭合内容
                    if (tagName && defaultSelfClosingTags.indexOf(tagName.toLowerCase()) < 0) {
                        return matched.replace(/\/>/, '></' + tagName + '>');
                    }
                }
                return matched;
            });
            if (enableCache) pageCache[url] = html;
            renderWidgetText(html).done(function ($content) {
                // 设置元素内容
                $el.empty().append($content);

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
                        if (ctrlValue === "$" || ctrlValue === "page" || ctrlValue === "dynamic") {
                            console.log('url ' + url + ' content enabled dynamic controller');
                            // TODO 待实现默认控制器
                            $ctrlEl.data("controller", $.extend(true, {$s: $ctrlEl, $page: null, dynamic: true}, {}));
                        } else if (_controllerMap[ctrlValue]) {
                            var clonedCtrl = $.extend(true, {$s: $ctrlEl, $page: null}, _controllerMap[ctrl]);
                            $ctrlEl.data("controller", clonedCtrl);
                            clonedCtrl.ready && clonedCtrl.ready();
                            callback && callback(true);
                        } else {
                            require([ctrlValue], function (ctrlObj) {
                                _controllerMap[ctrlValue] = ctrlObj;
                                var clonedCtrl = $.extend(true, {$s: $ctrlEl, $page: clonedCtrl}, ctrlObj);
                                $ctrlEl.data("controller", clonedCtrl);
                                clonedCtrl.ready && clonedCtrl.ready();
                                callback && callback(true);
                            });
                        }
                    } else {
                        var ctrlValue = url.replace(/\.html$/i, function (v) {
                            return ".js";
                        });
                        var ctrlName = ctrlValue.replace(/(^(\/|\\)|\.js$)/ig, '').replace(/(\/|\\)/g, ".");
                        require([ctrlValue], function (ctrlObj) {
                            $ctrlEl.attr("v-ctrl", ctrlName);
                            var thisCtrl = $.extend(true, {$s: $ctrlEl, $page: $ctrlEl}, ctrlObj);
                            $ctrlEl.data("controller", thisCtrl);
                            thisCtrl.ready && thisCtrl.ready();
                            callback && callback(true);
                        });
                    }
                });

                $el.data("inited", true);
                $el.attr("data-module", url);

                if (controller && controller.indexOf(".js") > 0) {
                    controller = formatUrl(controller);

                    require([controller], function (ctrl) {
                        console.log("load controller with " + controller, ctrl);
                        if (ctrl) {
                            $el.attr("v-ctrl", controller);
                            $el.attr("v-ctrl-manual", true);
                            $el.data("controller", ctrl);
                            ctrl.ready && ctrl.ready();
                            callback && callback(true);
                        } else {
                            callback && callback(true);
                            log.warn("controller is empty " + controller);
                        }
                    });
                } else {
                    callback && callback(true);
                }
            });
            // end http get done
        }).fail(function (resp, status, xhr) {
            $el.html("<div class='col-md-12'><h2 class='center'>Page NotFound 404</h2></div>");
            $el.attr("data-module", "error");
            console.log("page context load page error!", resp, status, xhr)
            callback && callback(false);
        });
    };

    var shutdownOldPage = function ($el) {
        if (!$el) throw '$el is not be null or empty';

        // 检查是否当前元素已经加载过页面上下文或者子元素是否有页面上线文，如果加载过则先清理再
        var oldPageController = [];
        // main
        if ($el.is("[v-ctrl]")) {
            oldPageController.push({$dom: $el, controller: $el.data("controller")});
        }
        // children
        var oldChildren = $el.find("[v-ctrl]");
        if (oldChildren.length) {
            oldChildren.each(function () {
                var _ = $(this);
                oldPageController.push({$dom: _, controller: _.data("controller")});
            });
        }

        var len = oldPageController.length;
        if (len) {
            log.info("shutdown page context of controller length " + len);
            while (--len > -1) {
                var shutdownObj = oldPageController[len];
                shutdownObj.$dom.xWidget("destroy");
                shutdownObj.controller.exit && shutdownObj.controller.exit();
            }
        }
    };

    var doAction = function (action, $trigger) {
        if (!action || !$trigger) return;

        var $currPage = $trigger.closest("[v-ctrl]");
        if ($currPage.length) {
            var pageData = $currPage.data();
            if (pageData && pageData.controller && pageData.controller[action]) {
                pageData.controller[action]();
                return;
            }
        }
        throw '未被识别的 pageAction ' + action;
    };

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
    };

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
    };

    return {
        define: _define,
        shutPage: shutdownOldPage,
        loadPage: _loadPage,
        get: _get,
        $do: doAction,
        listenReady: listenReady,
        module: module,
        controller: defineController,
        // FIXME:这个方法待修改名字以及放到更适合它的位置
        renderWidgetText:renderWidgetText
    }

});