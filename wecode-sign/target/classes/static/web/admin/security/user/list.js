// TODO: 1.pageContext 需要预加载 ，2.widget/data/datable 需要在使用时加载
require(["rt/pageContext","widget/data/datatable","widget/tab","widget/form/dateBox"],function(pageContext,dt,tab,dateBox){
	pageContext.define("admin.sitemap.list",function(page){
		
		page.ready = function(){    
			//debugger
			$("#createDate1").xWidget("form.DateBox",{ showIconButton:false ,format : 'yyyy-mm-dd hh:ii',minView : "hour"});
			$("#createDate2").xWidget("form.DateBox",{ showIconButton:false ,format : 'yyyy-mm-dd hh:ii',minView : "hour"});
			
			var gridOption = {
				selectMode:'mutli', /* 多选：mutli,单选：single,默认：normal */
				height:425, 
				pageOp:{
					// el:"#pageDemo",
					pageSize:10,
					curPage:1,
					pageSizeRange:[10,20,50,100,200,500]
				},
				columns:[
					{
						header:"操作",
						width:120,
						type:"operate",
						renderer:function(value,record,columnOp){
							var $btnView = $('<span><a class="btn btn-link btn-sm" >查看</a><a class="btn btn-link btn-sm">编辑</a><a class="btn btn-link btn-sm">授权</a></span>');
							var rowData = record;
							
							$btnView.find("a:eq(0)").on("click",function(){
								debugger
								page.doEdit(rowData);
							});
							
							$btnView.find("a:eq(1)").on("click",function(){
								page.doEdit(rowData);
							});
							
							$btnView.find("a:eq(2)").on("click",function(){
								page.doAuthority(rowData);
							});
							return $btnView;
						}
					},{
						field:"id",
						header:"ID",
						width:120
					},{
						field:"name",
						header:"用户名",
						width:120
					},{
						field:"remark",
						header:"备注",
						width:120
					},{
						field:"mobilePhone",
						header:"电话",
						width:120
					},{
						field:"mail",
						header:"邮箱",
						width:120
					},{
						field:"status",
						header:"状态",
						renderer:function(val){
							return "状态  "+val;
						}
					}
				]
				,dataset:"/services/security/user?curPage={curPage}&pageSize={pageSize}"
				,operation:{
					search:{
						btn:"#btnSearch",
						panel:"#formSearch"
					}
					// TODO:这里可以添加更多选项，例如批量删除，等选中后批量操作的内容，比如批量更新某个属性（状态）
					// batch {  del : { btn:"#btnBatchDelete" }, enable:{ btn:"#btnEnableStatus" , ajax:{ url:xxx ,data:data, method:"post"}}
				}
			};
			
			// 绑定表格 
			$("#gridSitemap").xWidget("datatable",gridOption);   
		}
		
		page.doDelete = function(){
			var $grid = $("#gridSitemap").xWidget();
			var records = $grid.getSelectedAllRecords();
			require(["ui/ui-confirm","rt/request"],function(c,http){
				if(records && records.length > 0){ 
					c.confirm("确定删除吗?",function(isOk){
						if(isOk){
							http.doDelete("/services/security/user?ids="+records.joinProp("id")).success(function(res){
								// TODO 显示删除成功 TiP
								// 刷新表格
								$("#gridSitemap").xWidget().reload();
							});
						}
					}); 
				}else{
					c.alert("请选中需要删除的数据"); 
				}
			});
		}
		
		page.doImport = function(){
			require(["ui/ui-confirm"],function(msg){
				var jc = msg.dialog({
					title:"导入数据", 
					columnClass:"medium",
					format:{
						type:"fileUpload", 
						setting:{
							inputName:"file",
							uploadUrl:"/services/security/user/import",
							success:function(resp){
								// 关掉上传窗口，并刷新表格
								jc.close();
								$("#gridSitemap").xWidget().reload();
							}
						}
					}
				}); 
			});
		}
		
		page.doEdit = function(record){
			var isCreate = !record;
			var tab =  $("#demoTab").xWidget(); 
			tab.addPage({
				title: (isCreate ? "添加" : "编辑" ) + "用户",
				url:"/web/admin/security/user/edit.html",
				allowClose:true,
				afterLoad:function(page){
					console.log("open Tab page isCreate {0}".format(isCreate));
					if(!isCreate){
						$("#formEditUser").formFill("/services/security/user/{0}".format(record.id));
					}
				}
			});  
		}
		
		page.doAuthority = function(record){
			var tab =  $("#demoTab").xWidget(); 
			tab.addPage({
				title: "用户授权",
				url:"/web/admin/security/user/verifyAuthority.html",
				allowClose:true,
				afterLoad:function(){
					
				}
			});  
		}
		
	});
});