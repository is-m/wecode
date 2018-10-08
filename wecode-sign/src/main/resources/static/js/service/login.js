// 登录服务
define(["jquery","rt/validation"],function($,v){

  var _login = function(postData){
    if(!postData.username || !postData.password){
      throw 'no found username or password field of '+JSON.stringify(postData);
    }
    
    return $.ajax({
      type:"POST",
      url:"login.do",
      data:postData,
      dataType:"json"
    });
  };
  
  return {
   
    /**
     * 用户登录
     * @param submitData 登录数据对象 { [  JSON ,Login Form DOM, ( arguments: username,password,xxx ) ], }
     * @return jquery Promiss
     * 
     * @author Administrator
     * 
     */
    /**
     * $.Defferd() 可以重构成 return $.Defferd2(function(){ xx.resolve(123),xx.reject(456) },{ data:submitData });
     * param 1:表示异步执行
     * param 2:表示当前异步执行参数
     */
    login:function(submitData){
      var dtd = $.Deferred();
      debugger
      var postData = null;
      // 表单型数据
      if(submitData.jquery){
        var $form = submitData;
        if($form.valid()){
          postData = $form.jsonData(); 
        }
      }
      // 直接是json数据
      else if($.isPlainObject(submitData)){
        postData = submitData;
      }
      // 按参数位提交数据
      else{
        if(arguments.length == 1) throw 'illegal arg length'; 
        postData = {username:arguments[0],password:arguments[1]};
      }
      
      alert("准备用帐号[{0}]密码[{1}]登录".format(postData.username,postData.password));
      _login(postData);
      //alert("准备用帐号[{0}]密码[{1}]登录".format(username,password));
      return dtd;
    } ,
    logout:function(){
      
    }
  }  
  
});