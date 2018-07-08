// 下拉框
define([ "widget/factory", "jquery" ], function(
		widget, $) {
 
	widget.define("form.combo", {
		templateUri : "js/widget/form/combo.html",
		init : function() {
			 
		},
		loadData : function() {

		},
		beforeRender : function(html) {
			return html;
		},
		afterRender : function() { 
		},
		ready : function() {

		},
		destory : function() {

		},
		setValue:function(val){
			this.$dom.find("input").val(val);
		}
	});

});