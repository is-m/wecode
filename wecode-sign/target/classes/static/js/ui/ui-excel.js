define([ "jquery", "rt/util" ], function($, util) {

	"use strict";
	var $file = $("#syFile"); 
	if($file.length == 0){
		$("body").append("<iframe id='syFile' style='display:none;' ></iframe>");
		$file = $("#syFile"); 
	}

	/**
	 * 导入模版下载
	 */
	function downloadTemplate(op) {
		if(typeof op == "string"){
			$file.attr("src",appConfig.contextPath + op);
		}else{
			
		}
	}

	/**
	 * excel 导出
	 */
	function doExport(op) {
		if(typeof op == "string"){
			$file.attr("src",appConfig.contextPath + op);
		}else{
			
		}
	}

	return {
		downloadTemplate : downloadTemplate,
		doExport : doExport
	}

});