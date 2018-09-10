pageContext.define("develop.generator.layout",["widget/tab"],function(page){
	
	page.ready = function(){
		$("#demoTab").xWidget("tab",{
			pages:[{
				title:"项目列表",
				url:"/web/develop/generator/project/list.html", 
				allowClose:true
			}]
		}); 
	}
	
});
