define(["widget/factory","jquery","rt/util"],function(widget,$,util){ 
	
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
			expandLevel:-1 ,
			defaultChecked: []  // []
		},
		template:"<h1>Hello this navbar Widget</h1>", 
		templateUri:"js/widget/data/tree.html",
		init:function(){ 
			this.op = $.extend({},this.defultOption,this.op)
		},
		loadData:function(){
			
		},
		beforeRender:function(html){
			return html;
		},
		afterRender:function(){ 
			this.reload();
		},
		ready:function(){
			
		},
		destory:function(){
			
		},
		selectedNodes:function(values){
			this.op.defaultChecked=$.isArray(values) ? values : [values];
			$.fn.zTree.init(this.$dom, this.op , this.op._data);  
		},
		bindData:function(data){ 
			$.fn.zTree.init(self.$dom, self.op , this.op._data = data); 
		},
		reload:function(){
			var self = this; 
			self.$dom.html("loading...");
			util.getDataset(this.op.dataset).done(function(data){
				$.fn.zTree.init(self.$dom, self.op , self.op._data = data);  
			});
		}
	});
	
});