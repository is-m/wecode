define(["jquery","rt/pageContext"], function($,pc) {

	"use strict";
	var enableCache = false;
	var routeCache = {}; 
	
	// copy of http://blog.csdn.net/sunxinty/article/details/52586556
	function Router() {
		this.routes = {};
		this.url = '';
	}

	Router.prototype.route = function(path, callback) {
		this.routes[path] = callback;
	}; 
	
	// TODO 按角色显示首页
	Router.prototype.homePage = function(){
		// 获取系统首页配置,以及当前用户角色URL
		// 优先检查角色是否具备URL，否则按系统首页URL展示
		// this.refresh(workspace.user.currentRole.url || workspace.sys.homePage || "/"); 
		
		// 后续URL准备好后需要去除下面逻辑 
		this.refresh("/");
	} 

	Router.prototype.refresh = function(u) {
		this.url = typeof u == "string" ? u : ( location.hash && location.hash.substring(1) || "/");
		$.proxy(function(){
			var path = this.url,$context = $("#__pageContext");
			console.log("no register router of path "+path);

			if(this.isHome(this.url)){
				// TODO:Home breadcrumb难道要从后台获取（国际化接口中获取？）？
				$("#nav-breadcrumb").html("Home")
				$context.html("<div class='container'><h2>Welcome to My Home (No User Settings)</h2></div>")
				return;
			}
			
			// 触发卸载事件
			pc.shutdown();
			// 加载新页面
			var url  = appConfig.contextPath + path;
			pc.loadPage($context,url);  
		},this)(); 
	};
	
	Router.prototype.isHome = function(url){  
		return  !url || ["","/", "#" ,"#/"].contains(url.trim().replace(/^#+/,"#")) 
	}

	Router.prototype.init = function() {
		window.addEventListener('load', this.refresh.bind(this), false);
		window.addEventListener('hashchange', this.refresh.bind(this), false);
		this.isHome(location.hash) ? this.homePage() : this.refresh(); 
	}

	return new Router();
});