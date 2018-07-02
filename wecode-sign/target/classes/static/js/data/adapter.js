define(["jquery","rt/logger"],function($,log){
	
	// 表格数据适配
	var _config = {
		"datatable" : {
			page:{
				// 当前页
				curPage:"number",
				// 页大小
				pageSize:"size",
				// 总页数
				totalPage:"totalPages",
				// 总记录数
				totalRecords:"totalElements" 
			},
			result:"content"
		}
	}
	
	// TODO:需要进行格式和类型检查，如过不检查格式只能使用$.extend({},{}); 
    function register(name,option){
		if(_config[name]){
			log.debug("data adapter option changed of name [{0}] old [{1}] new [{2}]",name,_config[name],option)
		}
		_config[name] = option;
		return null;
	}
	
	var deepAttr = function(obj,callback,parentAttr){
		parentAttr = parentAttr || "";
		if($.isPlainObject(obj)){
			for(var attr in obj){
				var v = obj[attr];
				if($.isPlainObject(v)){
					deepAttr(v,callback,parentAttr + attr + ".");
				}else if(typeof v == "string") {
					callback(parentAttr  + attr,obj[attr])
				}
			}
		}
	}
	
	/**
	 * 设置值
	 * @param key 设置dist的属性名
	 * @param value 设置dist属性名来源于src的属性名
	 * @param src  数据源
	 * @param dist 输出对象
	 * @returns
	 */
	function setValue(key,value,src,dist){
		var keyArr = key.split(".");
		var valueArr = value.split(".");
		
		var currentDistAttr = dist;
		for(var i=0;i<keyArr.length -1;i++){
			var name = keyArr[i];
			currentDistAttr[name] = currentDistAttr[name] || {};
			currentDistAttr = currentDistAttr[name];
		} 
		try{
			var currentValueAttr = src;
			for(var i=0;i<valueArr.length;i++){
				if(currentValueAttr[valueArr[i]]){
					currentValueAttr = currentValueAttr[valueArr[i]];
				}
			} 
			currentDistAttr[keyArr[keyArr.length-1]] = currentValueAttr;
		}catch(e){
			currentDistAttr[keyArr[keyArr.length-1]] = "error,"+e;
		}
	}
	
	/**
	 * 数据格式转换
	 */
	var translate = function(name,data){
		var translateStruct = _config[name];
		if(!translateStruct || !data) return data;
		var result = {};
		deepAttr(translateStruct,function(key,value){
			setValue(key,value,data,result); 
		});
		return result;
	}
	
	return {
		register:register,
		translate:translate
	}
});