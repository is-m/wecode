pageContext.define("admin.security.user.edit",["rt/validation"], function(page) {

	page.ready = function() {
		$("#btnSave").click(page.formSubmit);
		$("#btnCancel").click(page.closePage);
	}

	page.init = function(record) {
		
	}
	
	page.closePage = function(){
		$("#demoTab").xWidget().closeTab();
	}
	
	page.formSubmit = function(){
		console.log("submit");
		// 往后台添加一个栏目
		var isValid = $("#formEditUser").valid();
		if(isValid){
			$("#formEditUser").formSubmit("post","/services/security/user",function(resp){
				// 关闭页签，刷新表格
				page.closePage();
				$("#btnSearch").trigger("click");
			},function(resp){
				alert('server error');
			}); 
		}
	}

});