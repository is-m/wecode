define(["jquery"],function($){
	"use strict";

	var loadCSS = function(href,callback) {
		var head = document.getElementsByTagName('head')[0];
		var link = document.createElement('link');
		link.href = href;
		link.rel = 'stylesheet';
		link.type = 'text/css';
		head.appendChild(link)
		
		callback && callback();
	}
/*	
	var loadMutliCSS = function(array,callback){
		var _arr = [];
		if(array)
		
		var head = document.getElementsByTagName('head')[0];
		var link = document.createElement('link');
		link.href = href;
		link.rel = 'stylesheet';
		link.type = 'text/css';
		head.appendChild(link)
		
		callback && callback();
	}*/

	var loadJS = function(src,callback) {
		$.getScript(src).done(callback).fail(function(jqxhr, settings, exception) {
			debugger
		}).complete(function(){
			debugger
		});
	} 
	
	var loadMutliJS = function(arr,callback){
		// http://www.cnblogs.com/xishuai/p/jquery-load-css-and-js-file.html
		var _arr = $.map(arr, function(src) {
	        return $.getScript(src);
	    });

	    _arr.push($.Deferred(function( deferred ){
	        $( deferred.resolve );
	    }));

	    $.when.apply($, _arr).done(callback); 
	}
	
	var loadResource = function(src,callback){
		if(src.endsWith(".css",true)){
			loadCSS(src,callback);
		}else if(src.endsWith(".js",true)){
			loadJS(src,callback);
		}else{
			throw 'no support file '+src;
		}
	}  

	return {
		loadCSS:loadCSS,
		loadJS:loadJS,
		loadResource:loadResource 
	};

});