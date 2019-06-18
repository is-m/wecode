define([ "rt/core", "jquery", "rt/request" ], function(core, $, http) {

	var u = {}

	var _getAjaxData = function(ajaxResult) {
		if (ajaxResult && ajaxResult["code"] && ajaxResult["msg"]
				&& ajaxResult["data"]) {
			return ajaxResult["data"];
		}
		return ajaxResult;
	};

	// 同意数据集获取方法，返回Pormise对象
	u.getDataset = function(dataOrOption) {
		var _d = dataOrOption;
		var dfd = $.Deferred();
		if ($.isArray(_d)) {
			setTimeout(function() {
				dfd.resolve(_d);
			}, 0);
		} else if (typeof _d === "string") {
			http.doGet(_d).done(function(ajaxResult) {
				var data = _getAjaxData(ajaxResult);
				dfd.resolve(data);
			}).fail(function(d){
				dfd.reject(d);
			});
		}else if($.isPlainObject(_d)){
			http.ajax(_d).done(function(ajaxResult) {
				var data = _getAjaxData(ajaxResult);
				dfd.resolve(data);
			}).fail(function(d){
				dfd.reject(d);
			});
		}else{
			setTimeout(function() {
				dfd.reject('未能识别的数据格式');
			}, 0);
		}
		return dfd;
	};
	
	u.el = function(id){
		if(typeof id != 'string') throw 'id is not string';
		return $(id.indexOf("#") == 0 ? id : "#"+id); 
	}
	
	u.getObj = function(src,namespace){ 
		if(src && namespace){
			var tempObj=src,nameArray = namespace.split(".");
			for(var i=0;i<nameArray.length;i++){
				var name = nameArray[i];
				tempObj=tempObj[name];
				if(typeof tempObj == 'undefined') {
					return null; 
				}
			}
			return tempObj;
		}
		return null;
	}
	
	// 线性数据转化为树
	u.toTree = function toTree(data,rootId,idField,parentIdField) { 
        var tree = [],temp, _r= (typeof rootId == 'undefined' ? 0 : rootId ), _i = idField || "id", _p = parentIdField || "parentId";
        for (var i = 0; i < data.length; i++) {
        	var obj = data[i];
        	if(typeof obj.children !== 'undefined'){
        		return data;
        	}
            if (obj[_p] == _r) { 
                temp = toTree(data, obj.id,_i,_p); 
                tree.push($.extend(obj,temp.length ? {children:temp, _isLeaf : false } : {_isLeaf : true}));
            }
        }
        return tree;
    };
	
	u.execFunction = function(scope,namespace,arguments){
		
	};

	u.isEmpty = function() {

	};

	u.doAjax = function(ajaxOption){
		return http.ajax(ajaxOption);
	};

	return u;
});