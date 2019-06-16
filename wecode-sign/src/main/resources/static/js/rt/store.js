// 存储器，用于缓存（浏览器缓存，浏览器数据库）的相关API
define([], function() {
  // sessionStore 会话共享
  // localStorage 永久保存
  // 协议、主机名、端口不同会导致不同源出下跨域访问问题

  // 
  // if(window.sessionStore){

  // }

  var db = window.localStorage || {};

  return {
    /**
     * 设置
     * 
     * @param key
     *          属性名
     * @param value
     *          属性值
     * @param exp
     *          过期时间 timestamp，也可以是 0 表示关闭后过期，不填或者-1表示永久
     */
    set: function(key, value, exp) {
      //alert((typeof db) + " " + (typeof JSON) + " " + typeof(JSON && JSON.stringify));
      /*try{*/
      db[key] = JSON.stringify({
        val: value,
        exp: exp
      });
      /*}catch(e){
        alert("ERROR:"+e);
      }*/
      //alert("alert set storeed")
    },
    /**
     * 获取
     */
    get: function(key) {
      var val = db[key];
      if (val) {
        var item = JSON.parse(val);
        if (item.exp) {
          // 未过期
          if (item.exp > WeApp.getServerTime()) { return item.val; }
          delete db[key];
          console.log('access expired data for store of key ' + key);
          return null;
        }
      }
      return null;
    },
    remove:function(key){
      delete db[key];
    }

  }
});