pageContext.define("admin.sitemap.layout",["widget/tab"],function(page){
	
	page.ready = function(){
		$("#demoTab").xWidget("tab",{
			pages:[{
				title:"数据字典",
				url:"/web/admin/sitemap/list.html", 
				allowClose:true
			}]
		}); 
	}
	
});
