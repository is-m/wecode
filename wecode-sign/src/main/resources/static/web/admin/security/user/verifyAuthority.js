// TODO: 1.pageContext 需要预加载 ，2.widget/data/datable 需要在使用时加载
pageContext.define("admin.security.user.verifyAuthority",["widget/data/datatable","widget/tab"],function(page){
	var _record;
	page.ready = function(){
	  $("#roleTabs").xWidget("tab",{
	    style:"nav-bordered",
      pages:[{
        title:"我的角色", 
        url:"/web/admin/security/user/userRole.html", 
        allowClose:true
      },{
        title:"系统角色", 
        url:"/web/admin/security/user/userRole.html", 
        allowClose:true
      }]
    }); 
	}
	
	page.init = function(record){ 
		$("#formAuthority").jsonData(_record = record);
	}
	
	// 添加角色数据范围授权
	page.addAuthority = function(){
	  require(["ui/ui-confirm"],function(msg){
	    msg.dialog({
        title:"授权",
        url:"/web/admin/security/user/authority.html",
        columnClass:"medium",
        buttons:{
          'select':function(){
            
          },
          'cancel':function(){}
        }
      });
	  })
	}
	
});