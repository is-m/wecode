define([ "widget/factory", "jquery", "jquery.datetimepicker" ], function(
		widget, $) {

	var defulatConfig = {
		format : "yyyy-MM-dd"
	};

	widget.define("dateBox", {
		templateUri : "js/widget/dateBox.html",
		resources : {
			css : [ "../css/lib/bootstrap-datetimepicker.css" ]
		},
		init : function() {
			if(this.op){
				this.op = $.extend({
					showIconButton:true,
					fontAwesome : true,
					format : 'yyyy-mm-dd',// 'yyyy-mm-dd hh:ii',
					autoclose : true,
					todayBtn : true,
					// startDate: "2013-02-14 10:00",
					// minuteStep: 10
					minView : "month" // month 可以选择到日， day:可以选择到小时
				},this.op);
			} 
		},
		loadData : function() {

		},
		beforeRender : function(html) {
			return html;
		},
		afterRender : function() {
			var $dateEl = this.op.showIconButton ? this.$dom : this.$dom.find("input:eq(0)") ;
			$dateEl.datetimepicker(this.op); 
		},
		ready : function() {

		},
		destory : function() {

		}
	});

	
});