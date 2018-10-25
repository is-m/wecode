// 重复元素组件
define(["widget/factory", "jquery", "template"], function(widget, $, tmpl) {

  widget.define("data/repeat", {

    ready: function() {
        var $dom = this.$dom;
        // 属性转换
        var _data = $dom.data("collection");
        // https://blog.csdn.net/baidu_36065997/article/details/72887553
        var _template = "{{ each list }} " + $dom.html() + " {{/each}}";
        $dom.data("_template",_template);
        tmpl(WeApp.newId("$repeat"),_template);
        var render = tmpl.compile(_template);
        var html = render({ list: _data });
        $dom.html(html);  
    }

  });

});