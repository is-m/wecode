// TODO: 1.pageContext 需要预加载 ，2.widget/data/datable 需要在使用时加载
require(["rt/pageContext","widget/data/datatable","widget/container/tab"],function(pageContext){
	pageContext.define("admin.sitemap.list",function(page){
		
		page.ready = function(){  
			var gridOption = {
				
				pageOp:{
					el:"#pageDemo",
					pageSize:15,
					curPage:1
				},
				cols:[{
					field:"username",
					header:"用户名",
					width:100,
					renderer:function(value,record,columnOp){
						return "<a>"+value+"</a>";
					},
					editor: function(){
						return "<input>";
					}
				}]	
			};
			
			// 绑定表格
			
			$("#gridSitemap").xWidget("datatable",gridOption); 
			
			$("#btnAdd").on("click",function(){ 
				var demoTab = $("#demoTab").xWidget();
				var page = demoTab.addPage({
					title:"添加栏目",
					url:"@{/web/docs/pages/edit.html",
					allowClose:true
				}); 
			});
		}
		
	});
});