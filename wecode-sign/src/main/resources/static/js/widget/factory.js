define(function (require) {
    "use strict";
    var $ = require("jquery");
    var tmpl = require("template");
    var log = require("rt/logger");
    /*
     TODO:组件要考虑用户可修改，样式可定义
     */
    var config = {
        allowRewrite: false /* 允许覆盖插件 */
    };

    // TODO:所有的组件都应该有AJAX的功能
    var WidgetFactory = {
        constructMap: {}, /* 组件字典 */
        define: function (name, defineContext) { /* 组件定义函数 */
            /* TODO: 需要封装 defineContext 的私有内容 */
            var Widget = this.constructMap[name];
            if (Widget) {
                if (!config.allowRewrite) {
                    throw 'confict widget by name ' + name;
                }
                console.log('override widget by name ' + name);
            }

            Widget = function (name, op, data, $dom) {
                this._task = [];
                this.name = name;
                this.op = op;
                this.data = data;
                this.$dom = $dom;
                this.$events = {};
            };

            Widget._name = name;
            Widget.define = defineContext;
            // 渲染
            Widget.render = function(e){
                $.fn.xWidget.apply($(e),[Widget._name].concat([].slice.call(arguments,1)));
            };

            Widget.prototype = $.extend(true, {
                constructor: Widget,
                /**
                 * 绑定事件
                 * @param event  事件名
                 * @paran func   事件触发时回调函数
                 * @param isOnly 是否唯一，为true时，新设置的事件会覆盖老的事件，为其他时累加事件
                 * @author Administrator
                 */
                on: function (event, func, isOnly) {
                    log.debug("widget add event bound {0}", event);
                    if (!$.isFunction(func)) {
                        throw "on event of name '{0}' callback is not function".format(event);
                    }
                    if (typeof event == "string" && isOnly === true) {
                        $(this).off(event);
                        this.$events[event] = [];
                    }
                    var _events = $.isArray(event) ? event : [event];
                    for (var i = 0; i < _events.length; i++) {
                        var _event = _events[i];
                        $(this).on(_event, func);
                        this.$events[_event] = (this.$events[_event] || []);
                        this.$events[_event].push(func);
                    }
                    return this;
                },
                // 取消事件绑定,如果未传参数则取消所有事件
                off: function (event) {
                    if (event && this.$events[event]) {
                        log.warn("off event of name '{0}',but no reg this!", event);
                        return this;
                    }

                    var keys = (typeof event === 'undefined' ? Object.keys(this.$events) : [event]);
                    for (var i = 0; i < keys.length; i++) {
                        $(this).off(event);
                        this.$events[event] = null;
                        delete this.$events[event];
                    }

                    return this;
                },
                trigger: function (event, args) {
                    $(this).trigger(event, args);
                    return this;
                },
                refresh: function (op, data) {
                    return this;
                }
            }, defineContext);

            return (this.constructMap[name] = Widget);
        },
        defineAPI: function (name, apiConstuct) {

        }
    }

    var idMap = {"__id": 0};
    var componentMap = {};
    var buildComponentId = function (prix) {
        var key = prix || "__id";
        var cidx = (idMap[key] = idMap[key] ? idMap[key]++ : 1);
        return key + cidx;
    }

    var COMPONENT_DEF_OP = {
        id: null,
        ajax: {},
    }

    var renderWidget = function ($control, Widget, widgetOption, data, templateId, templateHtml) {
        var widgetOp = $.extend({}, Widget.define.op, widgetOption || {});
        var widgetManager = new Widget(name, widgetOp, data);

        widgetManager.init && widgetManager.init();

        var tmpl = require("template");
        //debugger
        // 如果存在模版串，后续可能会判断是否需要执行初始化方法
        if (templateHtml) {
            tmpl(templateId, templateHtml);
        }

        var $data = {$win: window, $widget: widgetManager.op, value: 'aui'};

        var templatedHtml = tmpl(templateId, $data);

        // widgetDefine.template = templatedHtml;

        var $dom = $(templatedHtml);

        widgetManager.$dom = $dom;
        var doTemplateHtml = widgetManager.beforeRender ? widgetManager.beforeRender($dom) : $dom;

        $control.html($dom);
        $control.data("__widget", widgetManager);

        // TODO:可以接收一个promise 对象，防止afterRender里存在异步方法
        // TODO:后续，需要整理出一个JS执行流程的内容，存在Promise则等Promise返回后执行各个回调函数

        var promise = widgetManager.afterRender && widgetManager.afterRender();
        if (promise && promise["done"] && promise["fail"] && promise["then"]) {
            promise.done($.proxy(widgetManager.ready, widgetManager));
        } else {
            widgetManager.ready && widgetManager.ready();
        }

        componentMap[buildComponentId(Widget.name)] = widgetManager;
    }

    $.fn.xWidget = function (name, op, data) {
        // 定义一个异步对象
        if (!arguments.length) {
            var managers = [];
            this.each(function () {
                managers.push($(this).data("__widget"));
            });
            return managers.length == 1 ? managers[0] : managers;
        }

        /*var w = require("widget/"+name);*/
        return this.each(function () {
            var self = $(this);
            var _id = buildComponentId(name);
            var $widgetBegin = self;

            // 获取组件内容
            var Widget = WidgetFactory.constructMap[name];
            if (!Widget) throw "no defined xWidget of name " + name;

            var widgetDefine = Widget.define;

            var initCompoent = $.proxy(function () {
                // 如果没有templateUri，该写法待和initWidget内的代码重构
                if (!widgetDefine.templateUri) {
                    var widgetOp = $.extend({}, Widget.define.op, op || {});
                    var widgetManager = new Widget(name, widgetOp, data);
                    widgetManager.$dom = this.widgetBegin;
                    this.widgetBegin.data("__widget", widgetManager);

                    widgetManager.init && widgetManager.init();
                    var promise = widgetManager.afterRender && widgetManager.afterRender();
                    if (promise && promise["done"] && promise["fail"] && promise["then"]) {
                        promise.done($.proxy(widgetManager.ready, widgetManager));
                    } else {
                        widgetManager.ready && widgetManager.ready();
                    }

                    return;
                }

                // 如果没有templateUri，那么TEMPLATE HTML 应该绑定到控件名字上
                if (widgetDefine.templateUri && !widgetDefine.__template) {
                    var templateUri = appConfig.contextPath + "/" + widgetDefine.templateUri;
                    $.ajax({url: templateUri, async: false}).success(function (html) {
                        widgetDefine.__template = html;
                        renderWidget($widgetBegin, Widget, op, data, templateUri, html);
                    }).error(function (err) {
                        console.log(err);
                        if (err.status == 404) {
                            widgetDefine.template = "no found component for uri ";
                            $widgetBegin.after(widgetDefine.template);
                            var widgetManager = new Widget(name, op, data, null);
                            //componentMap[_id] = widgetManager;
                        }
                    });
                } else {
                    renderWidget($widgetBegin, Widget, op, data, widgetDefine.templateUri, widgetDefine.__template);
                }
            }, {Widget: Widget, widgetDefine: widgetDefine, widgetBegin: $widgetBegin, op: op});

            var resourceOp = widgetDefine.resources;
            if (resourceOp) {
                require(["rt/resource"], $.proxy(function (res) {
                    //resourceOp.css = resourceOp.css || [];
                    //resourceOp.js = resourceOp.js || [];

                    if (resourceOp.css) {
                        res.loadCSS(resourceOp.css[0]);
                    }
                    if (resourceOp.js) {
                        res.loadJS(resourceOp.js[0], initCompoent);
                    } else {
                        initCompoent();
                    }
                }, {Widget: Widget, widgetDefine: widgetDefine, widgetBegin: $widgetBegin}));
            } else {
                initCompoent();
            }
        }).xWidget();
    }

    /**
     * 组件
     */
    $.xWidget = function (el, name, op, data) {
        var el, name, op, data, widget;
        var args = arguments;

        switch (args.length) {
            case 1:
                name = args[0];
                break;
            case 2:
                name = args[0], op = args[1];
                break;
            case 3:
                name = args[0], op = args[1], data = args[2];
                break;
            case 4:
                el = args[0], name = args[1], op = args[2], data = args[3];
                break;
            default:
                throw 'no support arguments length by $.xWidget';
        }

        if (el) {
            if (widget = $(el).data("__widget")) {
                return widget;
            }
        } else {
            var widgetConstruct = WidgetFactory.constructMap[name];
            if (widgetConstruct) {

            }
        }

    };

    return WidgetFactory;
})