// ajax 请求封装
define(["jquery"],function($){  
	// TODO:检查并重置URL的函数提取公共方法
	var resolveUrl = function(urlOrObj){
		var isObj =  $.isPlainObject(urlOrObj);
		var url = isObj ? urlOrObj.url : urlOrObj;
		if(url){
			if(url.indexOf("@{")>=0){
				url = url.replace(/@\{\s*(\S+)\s*\}/g,function(m,i,o,n){
				       return appConfig.contextPath+i;
			    });
			} 
			if(url.indexOf("http://") == -1 && url.indexOf("https://") == -1 && url.indexOf(appConfig.contextPath) != 0){
				url =  appConfig.contextPath + url;
			}
		}
		
		if(isObj){
			urlOrObj.url = url;
		}
		return urlOrObj;
	};

	var ajaxDefaultOption = {
		method: "get",
		contentType: "application/json"
	};

	var ajax = function(url,data,sCallback,fCallback,method){
		var op = $.isPlainObject(url) ? url: {
			url: url,
			data: data,
			method: method || "get"
		};

		op = $.extend({},ajaxDefaultOption,op);
		
		if(op.contentType == "application/json" && (op.method == "post" || op.method == "put") && $.isPlainObject(op.data) ){
			op.data = JSON.stringify(op.data);
		}
		
		var _async = $.ajax(resolveUrl(op));
		
		_async.success(function(resp){
			if(resp && resp.code && resp.msg && resp.code != '200'){
				console.log("call service error:",resp);
				fCallback && fCallback(resp);
			}else{
				sCallback && sCallback(resp.data);
			} 
		});
		
		fCallback && _async.error(fCallback); 
		
		return _async;
	}
	
	// 适用于查询
	var get = function(url,data,sCallback,fCallback){
		return ajax(url,data,sCallback,fCallback,"get");
	}
	
	// 适用于创建，或者任意其他类型请求
	var post = function(url,data,sCallback,fCallback){
		return ajax(url,typeof data === 'string' ? data : JSON.stringify(data),sCallback,fCallback,"post"); 
	}
	
	// 适用于修改
	var put = function(url,data,sCallback,fCallback){
		return ajax(url,typeof data === 'string' ? data : JSON.stringify(data),sCallback,fCallback,"put"); 
	}
	
	// 适用于删除
	var del = function(url,data,sCallback,fCallback){
		return ajax(url,data,sCallback,fCallback,"delete");
	}
	
	return {
		ajax:ajax,
		doGet:get,
		doPost:post,
		doPut:put,
		doDelete:del
	};
});