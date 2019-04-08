define([],function(){ 
	var _globalConfig = {};
	
	var _toString = Object.prototype.toString;
	var _slice = Array.prototype.slice; 

	var isFunction = function(obj){
		return typeof obj === 'function';
	}
	
	/*var _log = console.log;
	var _warn = console.warn;
	var _error = console.error;
	
	console.log = function(){
	  _log.apply(this,[].slice.call(arguments));
	}
	
	console.warn = function(){
	  _warn.apply(this,[].slice.call(arguments));
  }
	
	console.error = function(){
	  _error.apply(this,[].slice.call(arguments));
  }*/
	
	var _addProto = function(target,funcName,func){
		var _proto = null;
		if(!isFunction(target)) throw 'arguments error';
		_proto = target.prototype;
		if(_proto[funcName]) throw 'target include this arguments';
		_proto[funcName] = func;
	}; 
	
	// 通用扩展 
	// 以XX开始
	var oldStartsWith = String.prototype.startsWith;
	String.prototype.startsWith = function(start,igroneCase){ 
		var ig = igroneCase || false ,_this = ig ?  this.toLowerCase() : this ,_start = ig ? start.toLowerCase() : start;
		return oldStartsWith ?  oldStartsWith.call(_this,_start) : this.indexOf(_start) === 0; 
	}
	
	// 以XX结尾
	var oldEndsWith = String.prototype.endsWith;
	String.prototype.endsWith = function(end,igroneCase){ 
		var ig = igroneCase || false ,_this = ig ?  this.toLowerCase() : this ,_end = ig ? end.toLowerCase() : end;
		return oldEndsWith ?  oldEndsWith.call(_this,_end) : (_this.lastIndexOf(_end) === (_this.length-_end.length));
	}
	
	String.prototype.toJSON = function(){
		var temp = this.trim(); 
		if(temp.length == 0) return {}; 
		
		try{
			var v = temp.startsWith("{") && temp.endsWith("}") ? eval("("+this+")") : eval("({"+this+"})"); 
			if(typeof v !== "object") {
				throw 'illegal string to json for '+ this;
			}
			return v; 
		}catch(e){
			try{
				return JSON.parse(temp);
			}catch(e){
				throw 'parse string {0} to json faild'.format(this);
			}
		}
	};
	
	String.prototype.firstCharUpperCase = function(){
	  return this.replace(/^[a-z]/,function(v){ return v.toUpperCase()});
	}
	
	// 字符串格式化，可以是{0},{1}这种占位符，也可以是:fieldName或者{fieldName}这种占位符，但是同一时间只支持一种
	String.prototype.format = function(){
	    var args = arguments;
	    var obj,isObject = false;
	    if(args.length == 1 && $.isPlainObject(args[0])){
	    	obj = args[0];
	    	isObject = true;
	    }
	    return this.replace(/\{\s*(\w+)\s*\}/g,function(m,i,o,n){
	        return isObject ? obj[i] : args[i];
	    });
	} 
	 
	String.prototype.splitEx = function(spiltChar,removeEmpty){
		var temp = this.split(spiltChar);
		
		if(removeEmpty !== true) return temp;
		
		var result = [];
		for(var i=0;i<temp.length;i++){
			if(temp[i]!=''){
				result.push(temp[i]);
			}
		}
		return result;
	}
	
	// 多字符分割
	String.prototype.multiSplit = function(){
		var args=_slice.call(arguments).unique();
		var tempArray = [],result=[this];
		for(var i=0,j=args.length;i<j;i++){
			for(var k=0,v=result.length;k<v;k++){
				tempArray = tempArray.concat(result[k].split(args[i]));
			} 
			result=tempArray;
			tempArray = [];
		}
		return result;
	}
	
	// 字符串转日期
	String.prototype.toDate = function(){
		if(/^\d+$/.test(this)) return new Date(parseInt(this));
		if(/$(\d{4}-\d{1,2}-\d{1,2})/.test(this)){
			var da = this.multiSplit(" ","-",":")
			return new Date(da[0],da[1],da[2],da[3]||0,da[4]||0,da[5]||0);  
		}
		throw 'string to date invalid';
	}
	
	// 数值转日期
	Number.prototype.toDate = function(){
		return new Date(this);
	}
	
	// 日期转字符串
	Date.prototype.format = function(fmt) { 
	     var o = { 
	        "M+" : this.getMonth()+1,                 // 月份 
	        "d+" : this.getDate(),                    // 日 
	        "h+" : this.getHours(),                   // 小时 
	        "m+" : this.getMinutes(),                 // 分 
	        "s+" : this.getSeconds(),                 // 秒 
	        "q+" : Math.floor((this.getMonth()+3)/3), // 季度 
	        "S"  : this.getMilliseconds()             // 毫秒 
	    }; 
	    if(/(y+)/.test(fmt)) {
	            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	    }
	    for(var k in o) {
	        if(new RegExp("("+ k +")").test(fmt)){
	             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	        }
	    }
	    return fmt; 
	}     
	
	Date.prototype.trunc = function(){
		
	};
	
	Date.prototype.add = function(value,unit){
		
	}; 
	
	// 在指定位置插入元素
	Array.prototype.insert = function (index, item) { 
		this.splice(index, 0, item); 
		return this;
	};
	
	// 数组去重复
	Array.prototype.unique = function(){
		var tempMap = {};
		for(var i=0,j=this.length;i<j;i++){
			tempMap[this[i]] = 1; 
		}
		return Object.keys(tempMap);
	}
	
	// 数组包含
	Array.prototype.contains = function(){
		var args=_slice.call(arguments).unique();
		var i=0,j=this.length,k=0,v=args.length,tempObj={}; 
		for(i=0;i<j;i++){ 
			var iv = this[i];
			for(k=0;k<v;k++){
				if(iv === args[k]) {
					tempObj[args[k]] = 1; 
				}
			}
		}
		return Object.keys(tempObj).length === v;
	}  
	
	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) return i;
		}
		return -1;
	};
	
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		return index > -1 ? this.splice(index, 1) : null;
	};
	
	Array.prototype.removeAll = function(){ 
	  return this.length == 0 ? [] : this.splice(0,this.length);
	}
	
	Array.prototype.pushNonEmpty = function(val){
		// TODO:待修改empty逻辑
		if(val) this.push(val);
	}
	
	// 遍历返回
	Array.prototype._map = function(_func){
	  if(typeof _func !== 'function') throw 'arg is not a function';
		var result = [];
		for(var i=0;i<this.length;i++){
			result.pushNonEmpty(_func(this[i])); 
		}
		return result;
	}
	
	Array.prototype.joinProp = function(propString,splitString){
		var result = [];
		for(var i=0;i<this.length;i++){
			var item = this[i];
			if(!item || typeof item != "object"){
				throw 'data is not json of item = ' + item;
			}
			result.pushNonEmpty(item[propString]); 
		}
		return result.join(splitString || ",");
	}
	
	var getType = function(arg){
		_toString(arg)
	}
	
	var isJson = function(str){ 
		try{
			JSON.parse(str);
			return true;
		}catch(e){
			return false;
		} 
	}
	
	var isDate = function(obj){
		return obj instanceof Date;
	}
	
	var isNumeric = function(obj){
		return !isNaN(parseFloat(obj)) && isFinite( obj );
	}
	
	var trunc = function(obj,format){
		if(isDate(obj)){
			
			return obj;
		}
		
		if(isNumeric(obj)){
			
			return obj;
		}
		throw 'no support obj type by trunc';
	}
	
	// 事件处理 
	
	// 
	var isEmpty = function(v){
		if(v){
			
		}
	}
	
	// https://blog.csdn.net/kongjiea/article/details/17612899
	var browser={
      versions:function(){ 
         var u = navigator.userAgent, app = navigator.appVersion; 
         return {//移动终端浏览器版本信息 
              trident: u.indexOf('Trident') > -1, //IE内核
              presto: u.indexOf('Presto') > -1, //opera内核
              webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
              gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
              mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
              ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
              android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
              iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
              iPad: u.indexOf('iPad') > -1, //是否iPad  
              webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
              weixin: u.indexOf('MicroMessenger') > -1, //是否微信 
              qq: u.match(/\sQQ/i) == " qq" //是否QQ
          };
       }(),
      language:(navigator.browserLanguage || navigator.language).toLowerCase()
	}

	
	var getObj = function(){
		
	} 
	
	// 服务端时间与本地事件差异，获取服务端时间为 浏览器当前时间 - 服务端时间差异
	var _serverTimesDifference = null;
	var getServerTime = function(){
	  if(_serverTimesDifference == null) throw '请通过window.setServerTime初始化服务器时间后再进行访问'; 
	  return +(new Date()) + _serverTimesDifference;
	}
	 
	var setServerTime = function(times){
	  var stimes = +times;
	  if(!stimes || stimes < 1) throw '服务端时间设置错误 '+times; 
	  _serverTimesDifference = +(new Date()) - stimes;
	}

	var initGlobalConfig = function(){
	  _globalConfig = config || {};
    setServerTime(_globalConfig.serverTimes);
	}
	
	var idMap = {};
	var idGenerator = function(prix){
	  if(!idMap[prix]){
	    idMap[prix] = 1;
	  }
	  return prix + idMap[prix]++
	};
	
	return { 
		getString:_toString(),
		_slice:_slice, 
		trunc:trunc,
		getServerTime:getServerTime, 
		browser:browser,
		initGlobalConfig:function(config){
		  _globalConfig = config || {};
		  setServerTime(_globalConfig.serverTimes);
		},
		newId:idGenerator,
		body:document.body
	};
});