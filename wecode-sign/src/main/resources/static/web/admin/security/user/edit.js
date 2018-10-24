pageContext.define("admin.security.user.edit",["rt/validation","ui/ui-confirm"], function(page,v,m) {

	var isCreate = true;
	
	page.ready = function() {

	}

	page.init = function(record) {
		if(!record) throw 'record is illegal.';
		
		isCreate = false;
		$("#formEditUser").formFill("/services/security/user/{0}".format(record.id));
		
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
			  m.okTip("新增成功");
				page.closePage();
				$("#gridUser").xWidget().reload();
			},function(resp){
			  m.errTip("系统异常");
			}); 
		}
	}

});