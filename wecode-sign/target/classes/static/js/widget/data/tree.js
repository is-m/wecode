define(["widget/factory","jquery","rt/util"],function(widget,$,util){ 
	/**
	 * 事件：
	 * after.selected(val) 选中后
	 */
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
			this.ztreeObj = $.fn.zTree.init(this.$dom, this.op , this.op._data);  
		},
		/**
		 * 设置选中的值
		 * prop:属性名
		 * values:选中项
		 */
		setCheckedValues:function(prop,values){
			var ztree = this.ztreeObj;
			var vals = $.isArray(values) ? values : [values];
			for(var i=0;i<vals.length;i++){
				var node = ztree.getNodeByParam(prop,vals[i]);
				ztree.checkNode(node, true, true);
			} 
		},
		/**
		 * 获取选中项
		 * 
		 * 暂时值返回状态为完全选中的数据,如果需要，后续可以给该类提供参数
		 * 查看父节点的check_Child_State字段：1为子节点未被全选中，2表示全选中
		 */
		getCheckedNodes:function(){
			return this.ztreeObj.getCheckedNodes().filter(function(item){return !item.isParent || item.check_Child_State == 2 }); 
		},
		bindData:function(data){ 
			this.ztreeObj = $.fn.zTree.init(self.$dom, self.op , this.op._data = data); 
		},
		reload:function(){
			var self = this; 
			self.$dom.html("loading...");
			util.getDataset(this.op.dataset).done(function(data){
				self.ztreeObj = $.fn.zTree.init(self.$dom, self.op , self.op._data = data);  
			});
		}
	});
	
});