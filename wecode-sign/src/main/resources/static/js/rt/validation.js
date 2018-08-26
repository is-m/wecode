// 校验器，提供校验相关接口，并能提供外部扩展
define(["jquery"],function($){
	
	/**
	 * key:{ args:[obj] getValue:[func] expr:[string] field:[string:字段] msg:[string] onValid:[func],onError:[func], priority:[int] }
	 * message中可以放占位符或者message本身就是一个返回字符串的函数
	 */
	var customRuleMap = {};
	
	var defaultRuleMap = {
		"required":{
			expr:/^\s+$/,
			msg:"不能为空",
			priority:0
		},
		"maxLength":{
			onArgInit:function(arg){
				if(!$.isNumeric(arg)) throw 'validator "maxLength" arg must be numeric';
				if(+arg < 1) throw  'validator "maxLength" arg must greate zero';
				this.arg = +arg;
			},
			onValid:function(value,ruleContext){
				return value.length < ruleContext.arg;
			},
			onMsg:function(value,ruleContext){
				return "内容长度不能超过 "+ruleContext.arg+" 个字符";
			},
			priority:1
		},
		"numeric":{
			expr:/^\d*$/,
			msg:"必须为数值",
			priority:1
		},
		"int":{
			expr:/^\d+$/,
			msg:"必须为整数",
			priority:1
		}
	}
	 
	/**
	 * 全局添加校验器
	 * @param ruleConfig = { expr:[string] msg:[string] onValid:[func],onError:[func], priority:[int] }
	 * @author Administrator
	 */
	var addMethod = function(methodName,ruleConfig){ 
		customRuleMap[method] = ruleConfig;
	}
	
	var _getRule = function(rule){
		var ruleSetting = rule.split(":");
		var ruleName = ruleSetting[0];
		var ruleArg = ruleSetting.length > 1 ? ruleSetting[1] : null;

		var rule = customRuleMap[ruleName] ||  defaultRuleMap[ruleName];
		if(rule) return $.extend(true,rule,{arg:ruleArg});
		console.log('WARN:no defined rule of name -> '+ ruleName);
	} 
	
	var _validRuleContextItem = function(ruleObj){ 
		var val = ruleObj.$dom.val();
		for(var j=0;j<ruleObj.rules.length;j++){
			var rule = ruleObj.rules[j],context = {arg:rule.arg , $dom:ruleObj.$dom , $rule:rule};
			if(rule.expr && _showError(rule.expr.test(val),val,context)) break;
			if(rule.onValid && _showError(!rule.onValid(val,context),val,context)) break;
		} 
	}
	
	var _showError = function(hasError,val,context){
		var $ctrl = context.$dom;
		if(hasError){  
			var message = context.$rule.msg || context.$rule.onMsg(val,context);
			$ctrl.addClass("is-invalid").attr("data-original-title",message).attr("data-placement","bottom").tooltip('show'); 
			// 显示校验失败的消息时，添加blur事件
			if(!$ctrl.data("validTriggerInited")){
				$ctrl.data("validTriggerInited",true);
				$ctrl.on("blur",function(){
					_validRuleContextItem($(this).data("ruleCtrl"));
				});
			}
			
		}else if(context.$dom.is(".is-invalid")){
			$ctrl.removeClass("is-invalid").tooltip('dispose'); 
		}
		return hasError;
	}
	
	$.fn.valid = function(){
		
		var $this = this;
		
		var ruleContext = $this.data("ruleContext");
		if(!ruleContext){
			if($this.data("rule")){
				
			}else{
				var rules = [];
				var items = $this.find("[data-rule]");
				items.each(function(){ 
					var _rules = $(this).data("rule").splitEx(" ")
					.eachReturn(function(item){ return _getRule(item); })
					.sort(function(a,b){ return (a.priority || 99) - (b.priority || 99)  }); 
					$(this).data("ruleCtrl",{ $dom:$(this) , rules: _rules});
					rules.push({ $dom:$(this) ,rules : _rules });
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