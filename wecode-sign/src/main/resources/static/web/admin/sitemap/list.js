pageContext.controller("admin.sitemap.list",["widget/data/datatable","widget/tab"],function(page,dt,tab){
		
	page.ready = function(){
		
		var gridOption = {
			selectMode:'multi', /* 多选：multi,单选：single,默认：normal */
			height:300,
			treeOp:{
				async:false, 
				idKey: "id",
				pIdKey: "pid",
				rootPId: null,
				field:"name"
			},
			pageOp:false,
			columns:[
				{
					header:"操作",
					width:100,
					type:"operate",
					renderer:function(value,record,columnOp){
						var $btnView = $('<span><a class="btn btn-link btn-sm" >查看</a><a class="btn btn-link  btn-sm"  >编辑</a></span>');
						var rowData = record;
						
						$btnView.find("a:eq(0)").on("click",function(){
							page.doEdit(rowData);
						});
						
						$btnView.find("a:eq(1)").on("click",function(){
							page.doEdit(rowData);
						});
						return $btnView;
					}
				},{
					field:"name",
					header:"菜单标题",
					width:150,
					showTip:true,
					tip:function(value,record,columnOp){
						return value;
					},
					renderer:function(value,record,columnOp){
						return "<a>"+value+"</a>";
					},
					editor: function(){
						return "<input>";
					}
				},{
					field:"icon",
					header:"图标",
					width:50,
					renderer:function(value,record,columnOp){ 
						return value ? "<i class='fa fa-"+value+"'></i>" : "";
					}
				},{
					field:"uri",
					header:"访问路径",
					width:150
				},{
					field:"target",
					header:"访问方式",
					width:150,
					format:{
						dict:"sitemap.showMode"
					}
				},/*{
					field:"pid",
					header:"父节点",
					width:100
				},*/{
					field:"seq",
					header:"顺序",
					width:50
				},{
					field:"status",
					header:"状态",
					width:80,
					renderer:function (val,record) {
						var isEnabled = val === 1;
						return "<i class='status-dot " + ( isEnabled ? "success" : "" ) + "'></i> "+(isEnabled ? "有效" : "无效");
					}
				},{
					field:"allowType",
					header:"显示方式",
					width:150,
					format:{
						dict:"sitemap.showMode"
					}
				},{
					field:"allowValue",
					header:"显示值",
					width:150
				},{
					field:"lastUpdateBy",
					header:"最后修改人",
					width:150
				}
				
			]
			,dataset:"/services/base/catelog/tree"
			,operation:{
				search:{
					btn:"#btnSearch",
					panel:"#formSearch"
				}
			}
		};
		
		// 绑定表格 
		$("#gridSitemap").xWidget("datatable",gridOption);   
	}
	
	page.doDelete = function(){
		require(["ui/ui-confirm"],function(c){
			c.confirm("确定删除吗?",function(isOk){
				if(isOk){
					
				}
			});
		})
	}
	
	page.doImport = function(){
		require(["ui/ui-confirm"],function(msg){
			msg.dialog({
				title:"导入数据", 
				columnClass:"medium",
				format:{
					type:"fileUpload", 
					setting:{
						uploadUrl:"/api/sitemap/import"
					}
				} 
			}); 
		});
	}
	
	page.doEdit = function(record){
		var isCreate = !record; 
		$("#demoTab").xWidget().addPage({
			title: (isCreate ? "添加" : "编辑" ) + "栏目",
			url:"/web/admin/sitemap/edit.html",
			allowClose:true,
			afterLoad:function(page){
				page.init(record);
			}
		});  
	}
	
});
