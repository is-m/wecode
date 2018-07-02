define(["require","jquery","rt/logger"],function(require,$,log){
	
	var Page = function(id,$dom){
		this._id = id; 
		this.$dom = $dom;
	}
	var enableCache = false;
	var pageCache ={};
	var pageContextStack = [];
	var pageContextMap = {};
	var pageContextElStack = [$("#__pageContext")];
	
	/**
	 * id:上下文ID
	 * callback:回调函数 callback(page)
	 */
	var _define = function(id,_components,_callback){
		var components = _callback ? _components : [],callback = _callback || _components;
		if(!$.isArray(components)){
			throw 'components is not array.';
		}
		if(!$.isFunction(callback)){
			throw 'callback is not function.';
		}

		log.debug("load js module of "+id) 
		var page = new Page(id);
		require(components,function(){ 
			callback.apply(this,[page].concat(Array.prototype.slice.call(arguments,0)));
			pageContextStack.push(page);
			pageContextMap[id]=page;
			// pageContext.define 主要是加载内容到核心上下文，而非核心上下文的加载需要通过选中元素后$("xx").loadPage(url); 
			// 或者手动绑定模块 $("xx").boundModel("modelId") 未开发
			var $pc = pageContextElStack[pageContextElStack.length-1];
			//debugger
			page.$dom = $pc;
			var context = $pc.data("context") || {};
			
			$pc.data("context",$.extend(context,{ id : page }));
			
			// TODO:等页面自动渲染部分完成后触发ready函数 
			page.ready && setTimeout(page.ready,0);
		});
	}
	
	var _get = function(id){
		return pageContextMap[id];
	}
	
	var _shutPage = function(el){ 
		var $el = $(el);
		var $waitShuts = [];
		$waitShuts.push(el);
		
		var $childModules = $el.find("[data-module]");
		for(var i=0;i<$childModules.length;i++){
			$waitShuts.push($($childModules[i])); 
		}

		var $shutItem = null;
		while($shutItem=$waitShuts.pop()){
			var context = $shutItem.data("context") || {};
			for(var pageId in context){
				var page = context[pageId]; 
				console.log("shutdown js module of "+page._id);
				page.exit && page.exit();
				delete pageContextMap[page._id]; 
				page.$dom.removeAttr("data-module");
				pageContextStack.remove(page);
			}
			$el.removeAttr("data-module");
		}
	}
	
	var _loadPage = function(el,url,callback){
		var $el = el.jquery ? el : $(el); 
		if(!url.indexOf(appConfig.contextPath) == 0){
			url = appConfig.contextPath + url;
		}
		
		$.get(url).success(function(resp){ 
			var html = resp.replace(/@\{\s*(\S+)\s*\}/g,function(m,i,o,n){
		       return appConfig.contextPath+i;
		    });
			
			if(enableCache) pageCache[url] = html; 
			$el.html(html);
			
			$el.attr("data-module",url);
			pageContextElStack.push($el); 
			callback && callback(true);
		})
		.error(function(){
			$el.html("<div class='col-md-12'><h2 class='center'>Page NotFound 404</h2></div>");
			$el.attr("data-module","error");
			log.error("page context load page error!"); 
			console.log("page context load page error!",resp,status,xhr)
			callback && callback(false);
		});  
	}
	
	var _shutdown = function(){
		var page = null;
		while(page = pageContextStack.pop()){
			console.log("shutdown js module of "+page._id);
			page.exit && page.exit();
			delete pageContextMap[page._id]; 
		} 
		 $("#__pageContext").data("context",null);
	}
	
	var doAction = function(action){ 
		//debugger
		if(!action){
			log.warn("非法调用页面动作");
			return;
		}
		
		var pageAction = null; 
		for(var i=0;i<pageContextStack.length;i++){
			var page = pageContextStack[i];
			var attr = page[action];
			if(attr && !$.isFunction(attr)){
				log.warn("执行指定页面动作时，找到非Function类型属性" + action)
				continue;
			}
			if(attr && pageAction){
				log.error("执行指定页面动作时，找到多个同名动作" + action);
				return;
			}
			pageAction = attr || pageAction; 
		} 
		pageAction ? pageAction() : log.error("执行指定页面动作时，未找到页面动作" + action); 
	}

	return {
		define:_define,
		shutdown:_shutdown,
		shutPage:_shutPage,
		loadPage:_loadPage,
		get:_get,
		$do:doAction
	}
	
});