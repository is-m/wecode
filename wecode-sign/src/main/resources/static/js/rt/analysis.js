define(["rt/request","pako"],function(http,pako){ 
	console.log("备注：个人行为分析，浏览器异常监控");
	console.log("1.调用的接口（使用后台日志提取用户相关访问记录），停留时长");
	console.log("2.访问的UI，所属页面，点击区域，点击时间，点击位置按页面位置占比x%,y%，以及实际的位置x,y，点击的元素以及内容，例如<span>123</span>，按时间和数据量定时与定量发送");
	console.log("3.监控浏览器异常");
	// 每10秒钟，检查数据如果存在则回传监控数据
	var data = [];
	var enableAnalysis = !!appConfig.analysisApiUrl;
	var analysisDataTimer = null;
	if(enableAnalysis){
	  analysisDataTimer = window.setInterval(function(){
	  
  	  if(data.length){
  	    var item = null;
  	    var tempData = [];
  	    while((item = data.pop()) != null){
  	      tempData.push(item);
  	    }
  	    
  	    // 对 JSON 字符串进行压缩
  	    var waitPostData = encodeURIComponent(JSON.stringify(tempData));
  	    // pako.gzip(params) 默认返回一个 Uint8Array 对象,如果此时使用 Ajax 进行请求,参数会以数组的形式进行发送,为了解决该问题,添加 {to: "string"} 参数,返回一个二进制的字符串
  	    http.doPost(window.$$path + window.appConfig.analysisApiUrl,pako.gzip(waitPostData, { to: "string" }));
  	  }
  	  
  	},10000);
	}
	
	return {
	  push:function(item){
	    if(enableAnalysis){	      
	      data.push(item);
	    }else{
	      console.log("enableAnalysis is false ",item);
	    }
	  }
	}
	
});