define([ "jquery", "popper","rt/request" ], function($, Popper,http) {

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
	
	var globalConfig = {
		ajax:{
			
		},
		validator:{
			serverFaildCode:"412"
		}
	}
	
	// 元素提交，自带校验和校验回填功能
	$.fn.formSubmit = function(type,url,sCallback,fCallback){ 
		return this.each(function(){
			var $form = $(this);
			http.ajax(url,$form.jsonData(),sCallback,function(resp){
				// 如果是校验失败，则回填校验内容
				if(resp.code == globalConfig.validator.serverFaildCode){
					for(var errorField in resp.data){
						var errMsg = resp.data[errorField][0];
						var $el = $form.find(":input[name="+errorField+"]");
						
						if($el.length == 0 || $el.is(":hidden")){
							alert(errorField + " "+errMsg);
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
				}
				fCallback(resp);
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
	
	function _fillForm($dom,data){
		console.log("fillForm",$dom,data);
		$dom.find(":input").each(function(i,n){
			console.log(i,n,this,$(this));
			var _this = $(this);
			_this.val(data[_this.attr("name")]);
		});
	}
	
	$.fn.formFill = function(data){ 
		var $dom = $(this);
		if(typeof data == "string"){
			http.doGet(data).success($.proxy(function(res){
				_fillForm(this.$dom,res);
			},{$dom : $dom})).error(function(){
				alert("form fill error");
			});
		}else{
			_fillForm($dom,data);
		}
	}  

});