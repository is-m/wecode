define(["widget/factory","jquery","jquery.slimscroll"],function(widget,$,scroll){
	
	var menu = widget.define("menu",{
		template:"<h1>Hello this navbar Widget</h1>", 
		templateUri:"js/widget/menu.html",
		resources:{
			css:["../css/lib/metisMenu.css"] 
		},
		init:function(){
			
		},
		loadData:function(){
			
		},
		beforeRender:function(html){
			return html;
		},
		afterRender:function(){
		  this.$dom.click(function(e){ e.stopPropagation(); });
			this.$dom.find(".scroll-menu").slimScroll({ 
				height: "100%"
			});
			
			// 定义一个异步对象
			var dtd = $.Deferred();
			
			require(["lib/metisMenu"],function(m){
				 $('.side-nav .metismenu').metisMenu({ 
    			   toggle: true,
    			   activeClass: 'active',
    			   wheelStep: 5,
             touchScrollStep: 20
				 }); 
				 // resolve 会触发 done 的回调，reject 触发 fail 回调 
				 dtd.resolve();
			});
			
			// 返回
			return dtd.promise();//此处也可以直接返回dtd，区别在于Deferred 对象有resolve,reject,notify而promise只能设置done/fail函数
		},
		ready:function(){ 
			// 加载点击事件
			this.$dom.on("click","a",function(){ 
				var $this = $(this); 
				$this.attr("href") !== "#" && $("#nav-breadcrumb").html($this.text()); 
			});
		},
		destory:function(){
			
		}
	
	});

	return menu;
});