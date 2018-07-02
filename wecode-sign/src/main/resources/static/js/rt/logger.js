/**
 * 日志组件
 */
define(["rt/core"],function(core){
	var levelMap = {
		'debug':{
			priority:10,
			prefix:"DEBUG:"
		},
		'info':{
			priority:20,
			prefix:" INFO:"
		},
		'warn':{
			priority:30,
			prefix:" WARN:"
		},
		'error':{
			priority:40,
			prefix:"ERROR:"
		}
	};
	
	var currentLevel = 'debug';
	var enableErrorStack = true;
	var currentConfig = levelMap[currentLevel];
	
	// 打印日志
	var printLog = function(level,msg,params){ 
		// 如果正在打印日志消息的内容小于系统日志级别的优先级，说明不需要打印日志
		var levelConfig = levelMap[level];
		if(levelConfig["priority"] >= currentConfig["priority"]){
			var args = arguments , argLen = args.length;
			//console.log(levelConfig["prefix"] + (argLen > 2 ? msg.format(Array.prototype.splice.call(args,2,argLen)) : msg));
			console.log(levelConfig["prefix"] + (argLen > 2 ? msg.format(Array.prototype.splice.call(args,2,argLen)) : msg));
			if(level == "error" && enableErrorStack){   
				console.log("STACK",arguments.callee.caller.caller,arguments.callee.caller.caller.caller);
			} 
		} 
	};
	  
	var logger = {};
	
	// 调试信息
	logger.debug = function(msg){ 
		printLog.apply(this,Array.prototype.slice.call(arguments).insert(0,"debug")); 
	};
	
	// 普通信息
	logger.info = function(msg){
		printLog.apply(this,Array.prototype.slice.call(arguments).insert(0,"info"));  
	};
	
	// 警告信息
	logger.warn = function(msg){
		printLog.apply(this,Array.prototype.slice.call(arguments).insert(0,"warn"));   
	};
	
	// 异常信息
	logger.error = function(msg){
		printLog.apply(this,Array.prototype.slice.call(arguments).insert(0,"error"));   
	};
	
	
	return logger;
	
});