// TODO: 1.pageContext 需要预加载 ，2.widget/data/datable 需要在使用时加载
pageContext.define("admin.excel.import.layout",["widget/tab"],function(page){
	
	page.ready = function(){
		$("#demoTab").xWidget("tab",{
			pages:[{
				title:"导入列表",
				url:"/web/admin/excel/import/list.html", 
				allowClose:true
			}]
		}); 
	}
	
});
