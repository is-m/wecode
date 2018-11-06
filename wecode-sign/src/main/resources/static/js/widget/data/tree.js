define(["widget/factory","jquery","rt/util",'ztree',"lib/jquery.ztree.exhide"],function(widget,$,util,tree,exhide){ 
  
   
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
			var self = this;
			// event proxy
			this.op = $.extend({},this.defultOption,this.op,{ 
				callback:{
					onClick:function(){ 
						self.trigger("click",this); 
					},
					onCheck:function(){
						self.trigger("check",this); 
					}
				}
			})
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
		/**
		 * 获取所有节点
		 * 
		 */
		getAllNodes:function(){ 
		    return this.ztreeObj.transformToArray(this.ztreeObj.getNodes());  
		},
		selectedNodes:function(values){
			this.op.defaultChecked=$.isArray(values) ? values : [values];
			this.ztreeObj = $.fn.zTree.init(this.$dom.find(".ztree:eq(0)"), this.op , this.op._data);  
		},
		setCheckNode:function(nodes,v1,v2){
			var ztree = this.ztreeObj;
			var vals = $.isArray(nodes) ? nodes : [nodes];
			for(var i=0;i<vals.length;i++){
				var item = vals[i];
				if(item){
					ztree.checkNode(item,v1,v2);
				} 
			}
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
		 * 设置复选框失效
		 */
		setChkDisabled:function(nodes,isDisabled){
			var ztree = this.ztreeObj,_nodes = $.isArray(nodes) ? nodes : [nodes];
			for(var i=0;i<nodes.length;i++){
				var item = nodes[i];
				if(item){
					ztree.setChkDisabled(item, true); 
				}
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
		_initTree:function(data){
		  var self = this;
		  self.ztreeObj = $.fn.zTree.init(self.$dom.find(".ztree:eq(0)"), self.op , this.op._data = data); 
		  self.$dom.find(".tree-loading").html("");
		  // 如果开启了过滤的华重新绑定事件
		  if(self.op.filterable){
		    self.$dom.find(".tree-filter").unbind("input propertychange");
        require(["lib/jquery.ztree.fuzzySearch"],function(fuzzySearch){
          // zTreeId, searchField, isHighLight, isExpand
          fuzzySearch(self.op.id,self.$dom.find(".tree-filter"),true,true);
        });
		  }
		},
		bindData:function(data){ 
			
		},
		reload:function(){
			var self = this;  
			self.$dom.find(".tree-loading").html("loading...");
			util.getDataset(this.op.dataset).done(function(data){ 
			  self._initTree(data);
			});
		}
	});
	
});