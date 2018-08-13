// TODO: 1.pageContext 需要预加载 ，2.widget/data/datable 需要在使用时加载
pageContext.define("admin.security.role.authority",["widget/data/datatable","widget/tab","widget/form/dateBox"],function(page){
		
	page.ready = function(){
		
	}
	
	page.init = function(record){
		$("#formAuthority").jsonData(record);
		// 加载角色栏目，权限数据
	}
	
});