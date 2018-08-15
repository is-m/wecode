define(["widget/factory","jquery","rt/util"],function(widget,$,util){
	
	var defulatConfig = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		data: {
			key:{
				name : "name", // 指定label显示字段
				url: false,    // 指定url处理字段
				icon:false     // 指定icon字段
			},
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid", 
				rootPId: null
			}
		},
		dataset:[],         // [] or url string
		expandLevel:-1 
	};
	
	widget.define("data/tree",{
		defultOption:{
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			data: {
				key:{
					name : "name", // 指定label显示字段
					url: false,    // 指定url处理字段
					icon:false     // 指定icon字段
				},
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pid", 
					rootPId: null
				}
			},
			dataset:[],         // [] or url string
			expandLevel:-1 
		},
		template:"<h1>Hello this navbar Widget</h1>", 
		templateUri:"js/widget/data/tree.html",
		init:function(){ 
			this.op = $.extend({},defulatConfig,this.op)
		},
		loadData:function(){
			
		},
		beforeRender:function(html){
		 
			return html;
		},
		afterRender:function(){
			var self = this; 
			util.getDataset(this.op.dataset).done(function(data){
				console.log(self)
				$.fn.zTree.init(self.$dom, self.op , data);  
			});
		},
		ready:function(){
			
		},
		destory:function(){
			
		},
		 
	});
	
});