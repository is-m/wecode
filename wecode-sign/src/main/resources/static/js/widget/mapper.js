// 组件映射与组件参数解析
define(["jquery"], function ($) {
    // 每个组件通常有两个属性，一个是组件提供程序，一个是组件属性解析
    // comp:组件路径，起始位置为 widget
    // parser：组件属性解析，解析规则：组件身上本身的属性会全部解析到
    var menu = {
        comp:"widget/menu",
        parser:function () {

        }
    };

    var tabs = {
        comp:"widget/tab",
        parser:function (html) {
            var $this = $(html);
            debugger

            return {};
        }
    };

    var register = function (tag,comp) {
        mapper.tag = {comp:comp};
    };

    var mapper = {
        "m-tabs":tabs,
        "m-menu":menu,
        "reg": register// 注册
    };

    return mapper;
});
