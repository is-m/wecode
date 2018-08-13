pageContext.define("admin.security.role.edit",["rt/validation"], function(page) {

	var isCreate = true;
	
	page.ready = function() {

	}

	page.init = function(record) {
		if(!record) throw 'record is illegal.';
		
		isCreate = false;
		$("#formEditRole").formFill("/services/security/role/{0}".format(record.id));
		
	}
	
	page.closePage = function(){
		$("#demoTab").xWidget().closeTab();
	}
	
	page.formSubmit = function(){
		console.log("submit");
		// 往后台添加一个栏目
		debugger
		var isValid = $("#formEditRole").valid();
		if(isValid){ 
			$("#formEditRole").formSubmit("post","/services/security/role",function(resp){
				// 关闭页签，刷新表格
				page.closePage();
				$("#gridRole").xWidget().reload();
				//$("#btnSearch").trigger("click");
			},function(resp){
				alert('server error');
			}); 
		}
	}

});