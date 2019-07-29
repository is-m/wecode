define([ "jquery","rt/request","ui/ui-confirm","rt/logger"], function($, http,m,log) {
  var _conf = {
      ajax:{
        
      },
      validator:{
        serverFaildCode:"40"
      }
  }
	// Tool
	
	// 填充或者获取选中区域的表单数据
	// 内部有较多未考虑周全的实现
	$.fn.jsonData = function(jsonObj){
		// 填充选择区域的内容
		if(jsonObj){
			if(!$.isPlainObject(jsonObj)) throw 'not a json';
			
			var ctrlMap = {};
			this.each(function(){
				var $elContainer = $(this);
				$elContainer.find(":input").each(function(){
					var $el = $(this);
					// TODO:取名字和值有待确认后调整
					var name=$el.attr("name");
					ctrlMap[name] = $el;
				});
			});
			
			for(var attr in jsonObj){
				if(ctrlMap[attr]){
					ctrlMap[attr].val(jsonObj[attr]);
				}
			}
			
			ctrlMap = null;
			return;
		}
		
		var jsonResult = {};
		this.each(function(){
			var $elContainer = $(this);
			$elContainer.find(":input").each(function(){
				var $el = $(this);
				// TODO:取名字和值有待确认后调整
				var name=$el.attr("name"),val = $el.val();
				if(name){
					if($el.is(":radio")){
						if($el.is(":checked")){
							jsonResult[name] = val;
						} 
					}else{
						jsonResult[name] = val;
					}
				} 
			});
		});
		return jsonResult;
	}
	
	// 元素提交，自带校验和校验回填功能
	$.fn.formSubmit = function(type,url,sCallback,fCallback){ 
		return this.each(function(){
			var $form = $(this);
			http.ajax(url,$form.jsonData(),sCallback,function(response){
			  var resp = response.responseJSON;
				// 如果是校验失败，则回填校验内容
				if(resp.code == _conf.validator.serverFaildCode){
				  var unTrackErrors = [];
				  // key = create.arg0.password   value = 不能为空
					for(var errorKey in resp.data){ 
					  var errorField = errorKey.substring(errorKey.lastIndexOf(".")); 
						var errMsg = resp.data[errorKey];
						//FIXME:这里因为字段被标记为错误后会出现无法提交的情况，可能需要考虑使用新样式来区分是后台报错，来解决存在.is-invalid时，会触发表单校验的情况
						// 当前前提是考虑清楚是否有必要
						var $el = null;//$form.find(":input[name="+errorField+"]");
						
						if(!$el || $el.length == 0 || $el.is(":hidden")){ 
						  unTrackErrors.push(errMsg + "\r\n"); 
							continue;
						}
						//var $msgEl = $("<div class='alert alert-danger'>"+errMsg+"</div>");
						$el.addClass("is-invalid");
						//$el.after($msgEl); 
						$el.attr("data-toggle","tooltip");
						$el.attr("data-placement","bottom");
						$el.attr("title",errMsg); 
						$el.tooltip();
						
					} 
					
					if(unTrackErrors.length > 0){
					  m.alert(unTrackErrors.join(''));
					}
					
					m.errTip("校验失败"); 
				}else{
				  fCallback(resp);
				}
			},type);
		});
	}
	
	$.fn.toggleClass2 = function(cls1,cls2,callback){
		return this.each(function(i,n){
			var $this = $(this);
			var has2 = $this.hasClass(cls2),removeCls=has2?cls2:cls1,addCls=has2 ? cls1 : cls2;
			$this.removeClass(removeCls).addClass(addCls);
			// 回调时回传当前样式，以及当前对象
			callback && callback(addCls,$this);
		});
	};
	
	$.fn.getInput = function(name){
	  // 这里只是简单实现，这种实现可能无法识别自定义输入控件，每次增加输入类型控件时需要在这里新增
	  return $(this).find(":input[name='"+name+"']");
	}
	
	function _fillForm($dom,data){
		console.log("fillForm",$dom,data);
		$dom.find(":input").each(function(i,n){
			var _this = $(this);
			_this.val(data[_this.attr("name")]);
		});
	}
	
	$.fn.formFill = function(data){ 
		var $dom = $(this);
		if(typeof data == "string"){
			http.doGet(data).done($.proxy(function(res){
				_fillForm(this.$dom,res);
			},{$dom : $dom})).fail(function(){
				alert("form fill error");
			});
		}else{
			_fillForm($dom,data);
		}
	};
	
  // 校验绑定
	var hanldeValidation = function($form,data){
	  // 将后台校验规则绑定设置到表单上
	  console.log("formValidation",$form,data);
	  if(!data || !data.length) log.warn('form validation data is empty');
	  var ruleContext = [];
	  for(var i=0;i<data.length;i++){
      var fieldValidation = data[i];
      
      var field = fieldValidation.field;
      var validations = fieldValidation.validations;
      
      if(!field) continue;
      if(!validations || !validations.length){
        log.warn('form validation {0} validations is empty'.format(field));
        continue;
      }
      
      var $input = $form.getInput(field);
      // TODO:待处理特殊控件，如单选，下拉，等文本框控件
      var len = $input.length; 
      if($input && $input.length){
        var fieldRuleObj = { $dom:$input , rules:validations };
        $input.data("ruleCtrl",fieldRuleObj);
        ruleContext.push(fieldRuleObj);
      }
    }
	  
	  $form.data("ruleContext",ruleContext);
	  log.info("form validation inited");
	};
	
	var validationCache = {};
	// 绑定后台校验
	$.fn.formValidation = function(target,group){
	  var $form = $(this);
	  var cacheName = (group|| "") + target;
	  if(validationCache[cacheName]){
	    hanldeValidation($form,validationCache[cacheName]);
	    return;
	  }
	  
		http.doGet(window.$$path+"/services/validation/front/{0}?group={1}".format(target,group)).done(function(res){
			validationCache[cacheName] = res;
			hanldeValidation($form,res);
		}).fail(function(){
		  alert("form validation error");
		});
	};

	$.fn.nameEl = function(name){
	  return this.find("[name="+name+"]");
	};


	// 延迟绑定事件 on，one
	$.fn.delayOn = function(eventName,eventHandler){
		return this.each(function (e) {
			$(e).on(eventName,eventHandler);
		});
	};
	$.fn.delayOne = function(eventName,eventHandler){
		return this.each(function (e) {
			$(e).one(eventName,eventHandler);
		});
	};
});