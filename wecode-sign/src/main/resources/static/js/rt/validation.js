// 校验器，提供校验相关接口，并能提供外部扩展
define(["jquery","rt/logger"],function($,log){
	
	/**
	 * key:{ args:[obj] getValue:[func] expr:[string] field:[string:字段] msg:[string] onValid:[func],onError:[func], priority:[int] }
	 * message中可以放占位符或者message本身就是一个返回字符串的函数
	 */
	var customRuleMap = {};
	
	// priority:起始为1,1表示最高优先级
	var defaultRuleMap = {
		"required":{
			expr:/^\S+$/,
			msg:"不能为空",
			priority:1
		}, 
		"length":{
		  onArgInit:function(arg){ 
		    // min:{>=0} , max: {>0} 
		    this.arg = { min: arg.min || 0, max: arg.max };
		  },
		  onValid:function(value){  
		    var len = value.length;
        return len >= this.arg.min && len <= this.arg.max;
      },
      onMsg:function(value){
        return "内容长度不能超过 "+this.arg+" 个字符";
      },
      priority:9
		},
		"maxLength":{
			onArgInit:function(arg){
				if(!$.isNumeric(arg)) throw 'validator "maxLength" arg must be numeric';
				if(+arg < 1) throw  'validator "maxLength" arg must greate zero';
				this.arg = +arg;
			},
			onValid:function(value){
				return value.length < this.arg;
			},
			onMsg:function(value){
				return "长度不能小于{min}以及大于{max}".format(this.arg);
			},
			priority:9
		},
		"numeric":{
			expr:/^\d*$/,
			msg:"必须为数值",
			priority:9
		},
		"int":{
			expr:/^\d+$/,
			msg:"必须为整数",
			priority:9
		}
	};
	defaultRuleMap["notBlank"] = defaultRuleMap["required"];
	 
	/**
	 * 全局添加校验器（注册校验器）
	 * 注册后才能在控件上添加校验
	 * 
	 * @param name 校验器名称，
	 * @param config = { expr:[string] msg:[string] onValid:[func],onError:[func], priority:[int] }
	 * @author Administrator
	 */
	var addMethod = function(name,config){  
		if(!name || typeof name !== "string") throw 'illegal validation name '+name;
		customRuleMap[name] = config;
	}
	
	var _getRule = function(rule,$el){
		var ruleSetting = rule.split(":");
		var ruleName = ruleSetting[0];
		// 如果元素上存在data-rule-required
		var ruleArg = $el.data("rule"+ruleName.firstCharUpperCase()) || (ruleSetting.length > 1 ? ruleSetting[1] : null); 

		var rule = customRuleMap[ruleName] ||  defaultRuleMap[ruleName];
		if(rule) return $.extend(true,rule,{arg:ruleArg});
		console.log('WARN:no defined rule of name -> '+ ruleName);
	} 
	
	var resolveRule = function(rule){
	  // 如果存在type参数，则可能是后台返回的校验
	  if(rule.type){
	    var mapper = defaultRuleMap[rule.type];
	    if(mapper){
	      rule.expr = mapper.expr;
	      rule.onValid = mapper.onValid;
	    }else{
	      log.warn("cann't support rule by type "+rule.type)
	    }
	  }
	  return rule;
	}
	
	var _validRuleContextItem = function(ruleObj){ 
	  var $el = ruleObj.$dom;
		var val = $el.val();
		for(var j=0;j<ruleObj.rules.length;j++){
			var rule = resolveRule(ruleObj.rules[j]);
			if(rule.expr && _showError(!rule.expr.test(val),val,rule,$el)) break;
			if(rule.onValid && _showError(!rule.onValid(val),val,rule,$el)) break;
		} 
	}
	
	var _showError = function(hasError,val,rule,$ctrl){  
		if(hasError){   
			var message = rule.msg ? rule.msg.format(rule.arg) : rule.onMsg(val);
			$ctrl.addClass("is-invalid").attr("data-original-title",message).attr("data-placement","bottom").tooltip(); 
			// 显示校验失败的消息时，添加blur事件
			if(!$ctrl.data("validTriggerInited")){
				$ctrl.data("validTriggerInited",true);
				$ctrl.on("blur",function(){
					_validRuleContextItem($(this).data("ruleCtrl"));
				});
			}
		}else if($ctrl.is(".is-invalid")){
			$ctrl.removeClass("is-invalid").tooltip('dispose'); 
		}
		return hasError;
	}
	
	$.fn.valid = function(){
		
		var $this = this;
		
		var ruleContext = $this.data("ruleContext");
		if(!ruleContext){
			if(!$this.data("rule")){ 
				var rules = [];
				var items = $this.find("[data-rule]");
				items.each(function(){ 
				  var $el = $(this);
					var _rules = $el.data("rule").split(/\s+/)
					._map(function(rule){ return _getRule(rule,$el); })
					.sort(function(a,b){ return (a.priority || 99) - (b.priority || 99)  }); 
					var fieldRuleObj = { $dom: $el , rules: _rules};
					$el.data("ruleCtrl",fieldRuleObj);
					rules.push(fieldRuleObj);
				});
				
				$this.data("ruleContext",rules);
				ruleContext = rules;
			}
		} 
		for(var i=0;i<ruleContext.length;i++){  
			_validRuleContextItem(ruleContext[i]); 
		}
		return $this.find(".is-invalid").length == 0;
	}
	
	
	return { 
		addMethod:addMethod
	};
});