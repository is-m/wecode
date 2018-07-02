define(["jquery"], function($) {

	"use strict";
	var enableCache = false;
	var routeCache = {}; 
	
	// copy of http://blog.csdn.net/sunxinty/article/details/52586556
	function Router() {
		this.routes = {};
		this.currentUrl = '';
	}

	Router.prototype.route = function(path, callback) {
		this.routes[path] = callback;
	}; 

	Router.prototype.refresh = function() {
		this.currentUrl = location.hash.slice(1) || '/';
		(this.routes[this.currentUrl] || (function(path) {
			return function() { 
				console.log("no register router of path "+path); 
				
				require(["rt/pageContext"],function(pageContext){
					// 触发卸载事件
					pageContext.shutdown();
					// 加载新页面
					var url  = appConfig.contextPath + path;
					if(enableCache && routeCache[url]){
						$("#__pageContext").html(routeCache[url]);
					}else{ 
						$.get(url).success(function(resp){ 
							var html = resp.replace(/@\{\s*(\S+)\s*\}/g,function(m,i,o,n){
						       return appConfig.contextPath+i;
						    });
							
							if(enableCache) routeCache[url] = html; 
							$("#__pageContext").html(html);
						}).error(function(){
							$("#__pageContext").html("<div class='col-md-12'><h2 class='center'>Page NotFound 404</h2></div>");
						}); 
					}
				})
				
				//$("#__pageContext").render();
			};
		}(this.currentUrl)))();
	};

	Router.prototype.init = function() {
		window.addEventListener('load', this.refresh.bind(this), false);
		window.addEventListener('hashchange', this.refresh.bind(this), false);
		var hash = window.location.hash;
		if(hash && hash != "#" && hash != "#/"){
			this.refresh();
		}
	}

	return new Router();
});