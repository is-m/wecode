// TODO: 1.pageContext 需要预加载 ，2.widget/data/datable 需要在使用时加载
pageContext.define("admin.security.role.authority",["rt/request"],function(page,http){
		
	page.ready = function(){
		
	}
	
	page.init = function(record){
		$("#formAuthority").jsonData(record);
		// 加载角色栏目，权限数据 
		// 加载绑定的栏目
	}
	
	page.sync = function(){
		// 将系统所有权限获取，并写入到权限表 
		http.doPost("/services/security/permission/sync").success(function(res){ 
			$("#treeCatelog").xWidget().reload();
			$("#treeFunction").xWidget().reload();
		});
	}
	
	page.clear = function(){
		http.doDelete("/services/security/permission/clearInvalid").success(function(res){ 
			$("#treeCatelog").xWidget().reload();
			$("#treeFunction").xWidget().reload();
		});
	}
	
	page.closePage = function(){
		$("#demoTab").xWidget().closeTab();
	}
});