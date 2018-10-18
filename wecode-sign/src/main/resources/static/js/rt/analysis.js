define(["rt/request","pako","rt/store"],function(http,pako,store){ 
	console.log("备注：个人行为分析，浏览器异常监控");
	console.log("1.调用的接口（使用后台日志提取用户相关访问记录），停留时长");
	console.log("2.访问的UI，所属页面，点击区域，点击时间，点击位置按页面位置占比x%,y%，以及实际的位置x,y，点击的元素以及内容，例如<span>123</span>，按时间和数据量定时与定量发送");
	console.log("3.监控浏览器异常");
	
	// ip，userAgent ，等公共重复数据不应该直接放入数据内容区域，应该为{ ip:xx,agent:xx , items:[{...},...] } 来降低请求数据量，又或者头部数据放入到header Or queryString
	
	// 每10秒钟，检查数据如果存在则回传监控数据
	var data = [];
	var enableAnalysis = !!appConfig.analysisApiUrl;
	var analysisDataTimer = null;
	var dataUrl = null;
	
	if(enableAnalysis){
	  dataUrl =  window.$$path + window.appConfig.analysisApiUrl;;
	  analysisDataTimer = window.setInterval(function(){
  	  if(data.length){
  	    var item = null,tempData = [];
  	    while((item = data.pop()) != null){
  	      tempData.push(item);
  	    }
  	    
  	    // 对 JSON 字符串进行压缩
  	    var waitPostData = encodeURIComponent(JSON.stringify(tempData));
  	    // pako.gzip(params) 默认返回一个 Uint8Array 对象,如果此时使用 Ajax 进行请求,参数会以数组的形式进行发送,为了解决该问题,添加 {to: "string"} 参数,返回一个二进制的字符串
  	    http.doPost(dataUrl,pako.gzip(waitPostData, { to: "string" }));
  	  }
  	  
  	},window.appConfig.analysisSendInterval || 10000);
	}
	
	var isNotPrintDisableAnalysis = true;
	
	return {
	  push:function(item){
	    if(enableAnalysis){	 
	      if(typeof item === "object"){	
	        // 如果没有指标，表示数据无效
	        if(!item["m"]){
	          console.log("analysis data not found 'metric' field ",item);
	          return;
	        } 
	        
	        item["t"] = window.getServerTime(); // timestamp
	        item["a"] = navigator.userAgent;    // agent info
	        
	        try{
  	        var user = store.get("$USER_TOKEN$");
  	        item["u"] = user ? user["identifier"] : "unlogin";
	        }catch(e){
	          console.log("Analysis user setting faild " ,e);
	          item["u"] = 'error:'+e;
	        }
	       
          data.push(item);
	      }else{
	        console.log("analysis data format illgeal ",item);
	      }
	    }else if(isNotPrintDisableAnalysis){
        console.log("User Analysis is not be enabled");
        isNotPrintDisableAnalysis = false; 
	    }
	  }
	};
	
});