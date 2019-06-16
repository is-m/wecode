// TODO: 1.pageContext 需要预加载 ，2.widget/data/datable 需要在使用时加载
pageContext.controller("admin.security.role.authority",["rt/request"],function(page,$S,http){
	var role;
	
	page.ready = function(){
		
	}
	
	page.init = function(record){
		$("#formAuthority").jsonData(record);
		role = record;
		// 加载角色栏目，权限数据 
		http.doGet("/services/security/role/{0}/permissionIds".format(role.id)).success(function(permissionIds) {
			$("#treeFunction").xWidget().setCheckedValues("id",permissionIds);
			// 加载完权限后，根据权限来影响栏目的选项
			var treeCatelog = $("#treeCatelog").xWidget();
			var undisabledNodes = treeCatelog.getAllNodes().filter(function(item){return item.allowType!="authority";});
			treeCatelog.setCheckNode(undisabledNodes.filter(function(item){ return !item.isParent; }), true, true); 
			//treeCatelog.setChkDisabled(undisabledNodes,true);

			// treeObj.setChkDisabled(nodes[i], true);
		});
		// 加载绑定的栏目
	}

	page.sync = function() {
		// 将系统所有权限获取，并写入到权限表 
		http.doPost("/services/security/permission/sync").success(
				function(res) {
					$("#treeCatelog").xWidget().reload();
					$("#treeFunction").xWidget().reload();
				});
	}

	page.clear = function() {
		http.doDelete("/services/security/permission/clearInvalid").success(
				function(res) {
					$("#treeCatelog").xWidget().reload();
					$("#treeFunction").xWidget().reload();
		});
	}

	page.save = function() {
		// 获取选中的权限数据
		var checkedData = $("#treeFunction").xWidget().getCheckedNodes().filter(function(item){ return item.type == "operate" });
		http.doPost("/services/security/role/{0}/permissions".format(role.id), checkedData.joinProp("id").split(","))
		.success(function() { alert("保存权限成功"); });
	}

	page.closePage = function() {
		$("#demoTab").xWidget().closeTab();
	}

});