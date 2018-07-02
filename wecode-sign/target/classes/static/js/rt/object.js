// 对象:【属性，方法，事件】，特性：【抽象，继承，封装，多态】，接口：【定义行为规范】
define([],function(){
	
	/**
	 * 类型定义，其中带下划线的均为私有的，$开始的表示事件处理程序
	 * 调用模版：define("Student",[cA,cB,...],{
	 *		_name:"zhangsan",
	 *      name:"zs",
	 *		constructor:function(){
	 *      },
	 *      _method:function(){},
	 *      method:function(){},
	 *      $stateChange:function(){
	 *      	// function(sender,eventArgs){}
	 *      	$(this).find("a").click(function(){
	 *      		this.trigger("onStateChange",this,{ argument : "123" })
	 *      	}); 
	 *      } 
	 * });
	 */
	var define = function(parents,classOption){
		
	};
	
	
	
	return {
		define:define
	};
	
});

	