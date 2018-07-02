define(["jquery","bootstrap","rt/request","widget/tool/dialog"],function($,bs,http,d){
	 
	var popTreeDialog = function(){
		require([],function(){
			var $dialog = $("").xWidget("dialog");
			$("body").append($dialog);
			$dialog.modal('show');
		})
	}
	
	
	
	return {
		popTreeDialog:popTreeDialog
	}
	
	
});