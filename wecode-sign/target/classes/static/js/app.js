define(function(require, exports, module){
	
	var initilize = function(){
		// 隐藏整个页面内容
		
		// 绑定URL地址事件
		require(["rt/router","rt/pageContext"],function(router,pageContext){
			router.init(); 
		
			// 初始化基础内容
			console.log("init ui context");
			initWidget();
			
			// 绑定全局事件监听
			$(document).on("click",function(e){
				
				var $trigger = $(e.target);
				// TODO:下面的逻辑可能需要调整，看看是帮上级按钮触发点击事件，还是帮不限层级的上级触发一次事件
				// 如果是图标标签，并且上级是按钮则重新 
				if($trigger.is("i") && $trigger.parent().is("button,.btn")){ 
					// 如果这个图标本身没有点击事件，并且上级是个按钮且没有点击事件，但是有page-action属性的话，帮上级触发点击事件
					var targetEvents =  $._data(e.target,"events");  
					if(!targetEvents || !targetEvents["click"]){
						var $parent = $trigger.parent(); 
						var parentEvents =  $._data($parent[0],"events"); 
						if((!parentEvents || !parentEvents["click"]) && $parent.is("[page-action]")){
							$parent.trigger("click");
						}
					}
				}
				
				// 如果存在全局事件失效的属性，则不触发全局事件
				if($trigger.is(".e-disable")) return;
				
				
				// 如果是重置按钮，则清空
				if($trigger.is(".f-reset")){
					var $fReset = $trigger.closest("form");
					$fReset[0].reset();
					$fReset.find(".f-search").trigger("click"); 
				}
				
				if($trigger.is("[page-action]")){
					var action = $trigger.attr("page-action");
					action && pageContext.$do(action);
				} 
			});
			
			// 触发地址事件 
			console.log("trigger url default event");
		});
	};
	
	var getAllChildrens = function(dom,collector){
		var domArray = typeof dom.length == 'undifined' ? [dom] : dom; 
		for(var i=0;i<domArray.length;i++){
			var currentDom = domArray[i];
			if(currentDom.children.length > 0){
				getAllChildrens(currentDom.children,collector);
			}else{
				collector.push(currentDom);
			}
		}
	}
	
	var initWidget = function(){
		// 读取界面内容
		var allEls = [];
		getAllChildrens(document.body.children,allEls);
		var pageElements = allEls;
		for(var i=0;i<pageElements.length;i++){
			var el = pageElements[i];
			console.log("load js widget -- "+el + "    "+ el.toString() );
			//var tagName = el.tagName;
			if(el.toString() == "[object HTMLUnknownElement]"){
				var widgetName = el.tagName.toLowerCase();
				if(widgetName.indexOf(":")){
					widgetName = widgetName.substring(widgetName.indexOf(":")+1);
				}
				console.log("load js widget -- "+widgetName);
				require(["widget/"+widgetName],$.proxy(function(widget){
					// 加载完组件则初始化组件的基本内容
					$(this.el).xWidget(this.widgetName);
				},{ el:el, widgetName:widgetName })); 
			}
		}
		
		$("body").find("[data-x-widget]").each(function(){
			var $this = $(this);
			var widgetName =  $this.data("xWidget");
			require(["widget/"+widgetName],$.proxy(function(widget){
				// 加载完组件则初始化组件的基本内容
				$this.xWidget(this.widgetName);
			},{ el:el, widgetName:widgetName })); 
		});
	}
	
	return { init:initilize };
	
});