define(["rt/store"],function(store){
 
  /**
   * 初始化布局
   */
	var initLayout = function(){
	  var $body = $("body"),$window = $(window);
	  var ww = $window.width();
	  // xs=超小屏(max-width=768), sm=小屏(width=768-992)  md=中屏(992-1200)，lg=大屏(1200-max)，
	  var isXS= ww < 768,isSM = ww >= 768 && ww < 992,isMD=ww >= 992 && ww < 1200,isLG= ww > 1200;
	  // ww >= 768 && ww <= 1028 ? $body.addClass("enlarged") : 1 != $body.data("keep-enlarged") && $body.removeClass("enlarged");
	  // 移动视角
	  if(isXS){ 
	    $body.addClass("sm");
	    $(document).click(function(){
	      $(".left-side-menu").hide();
	    });
	  }
	  
	  $("#menuSwitch").click(function(e){
	    $(".left-side-menu").toggle();
	    // 非超小频，才自动切换样式
	    if(!isXS){	      
	      $body.toggleClass("sm");
	    }
	    e.stopPropagation();
	  });
	  
	}
	
	/**
	 * ajax请求初始化
	 * 可以在此处添加公共的ajax预处理
	 */
	var ajaxSetup = function(){
	  $.ajaxSetup({  
	    // 超时
	    //timeout:5000,
	    // 
	    cache: false,
	    // 发送前执行的函数
      beforeSend: function (xhr) {
        // 在请求前可以对数据进行加密
        // 设置CSXF Token，用户认证的TOKEN
        // 以及其他的透传参数
          /*var params = arguments[1].data;
          var data = '';
          for (var key in params) {
              var dataUtf8 = CryptoJS.enc.Utf8.parse(params[key]);
              var dataBase64 = CryptoJS.enc.Base64.stringify(dataUtf8);
              data = data.concat('&' + key + '=' + dataBase64);
          };
          arguments[1].data = data.substring(1, data.length);*/
        var tokenObj = store.get("$USER_TOKEN$");
        if(tokenObj){
          xhr.setRequestHeader('X-Authentication', tokenObj.token);
        }
      },
      // 默认不序列化参数
      //processData: false,
      // 对响应的数据进行过滤，例如解密等预处理动作
      //dataFilter: function (data,type) {
          /*var result = '';
          try {
              var a = CryptoJS.enc.Base64.parse(arguments[0]);
              var result = CryptoJS.enc.Utf8.stringify(a);
          } catch(e) {
              result = arguments[0];
          } finally {
              return result;
          }*/
      //},
      error: function(jqXHR, textStatus, errorThrown){
        switch (jqXHR.status) {
          case (500):
            alert("服务器系统内部错误");
            break;
          case (401):
            // 不存在登录页时才弹出登录页
            if(!$("#formMiniLogin").length){
              require(["ui/ui-confirm"],function(c){
                window.miniLoginDialog = c.dialog({ title:" ", url:"/web/_index/miniLogin.html", columnClass:"medium"  });
              });
            }else{
              // TODO:待修改需要根据校验或者什么问题来定位显示错误信息
              //alert(jqXHR.responseJSON);
              alert(jqXHR.responseText);
            } 
            break;
          case (403):
            alert("无权限执行此操作");
            break;
          case (404):
            alert("未找到资源");
            break;
          case (408):
            alert("请求超时");
            break;
          case (503):
            alert("网关超时");
            break;
          default:
            alert("未知错误");
        }
      },
      success: function(data) {
        //console.log(data);
      } 

  });
	  
	};
	
	// 监听浏览器异常，上报
	var listenExplorerError = function(){
	  window.onerror = function(msg, url, line){       
	    // https://blog.csdn.net/hj7jay/article/details/62215252 
	    require(["rt/analysis"],function(analysis){
	      // t:timestamp 时间错,m:metric 指标,v:value 值, a1:扩展属性1,a2 扩展属性2,a3 扩展属性3(使用mongoDB,或者mysql JSON 类型字段存放更多的补充信息)
	      try{
	        analysis.push({ m:"WEB.JS.ERROR",v:0, msg : msg, url:url, line:line})
	      }catch(e){
	        alert('analysis push error '+e);
	      }
	    });
	    //alert( "真不幸，又出错了\n\n错误信息：" + msg   + "\n所在文件：" + url + "\n错误行号：" + line + "\r"+window.userAgent);
	    
    }
	}
	
	var initilize = function(){
	  listenExplorerError();
	  initLayout();
	  ajaxSetup();
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
		

    $("[data-toggle='popover']").each(function() {
        var $el = $(this),url = $el.data("contentUrl"),width=$el.data("width") ;
        var $popver = $el.popover({
            trigger: 'hover',//'manual',
            html: true,
            //title: 'kkkk',
            // template:'',
            placement: 'bottom',
            content: function() {
              // FIXME:目前看到有发起两次请求的情况
              if(!$el.data("popoverContentHtml")){
                var contentId = $(this).attr("aria-describedby");
                $.get(url,function(resp){ 
                  $el.data("popoverContentHtml",resp);
                  $("#"+contentId).find(".popover-body").html(resp); 
                });
                // FIXME:异步加载弹出框时最好设置下弹出框的大小，以免出现异常情况，且大小应该是外部指定
                return "<div style='width:{0}'>loadding...</div>".format(width || "200px");
              }else{
                return $el.data("popoverContentHtml");
              }
              //  return content();
            },
            delay: {hide: 100} 
        }).on('shown.bs.popover', function (event) {
            var that = this;
            var contentId = $(this).attr("aria-describedby");
            $("#"+contentId).on('mouseenter', function () {
                $(that).attr('in', true);
            }).on('mouseleave', function () {
                $(that).removeAttr('in');
                $(that).popover('hide');
            });
        }).on('hide.bs.popover', function (event) {
            if ($(this).attr('in')) {
                event.preventDefault();
            }
        });
 

    }); 
    
    // 后续需要删除
    //模拟动态加载内容(真实情况可能会跟后台进行ajax交互)  
    function  content()  {      
        var  data  =  $("<ul class='list-unstyled mb-0 text-center' style='width:180px'>"  +             
                "<li><div style='font-size:50px;'><i class='fa  fa-user-circle'></i></div></li>"+
                "<li>张三 </li>"  +                
                "<li>中文 </li>"  +                
                "<li>系统管理员</li>"  +                
                "<li><a style='width:50%;display:inline-block'>设置</a><a style='width:50%;display:inline-block'>注销</a></li>"+
                "</ul>");            
        
        return  data;  
    }  
    //模拟悬浮框里面的按钮点击操作  
    function  test()  {      
        alert('关注成功');  
    }
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
				var options = $this.data("xWidgetOption");
				$this.xWidget(this.widgetName,JSON.parse(options || "{}"));
			},{ el:el, widgetName:widgetName })); 
		});
		
		$(document).click(function(){  
			console.log("document click hidden register auto process document click listener handler");
			//$(".tooltip").tooltip("hide");
		});
	}
	
	return { init:initilize };
	
});