// TODO: 1.pageContext 需要预加载 ，2.widget/data/datable 需要在使用时加载
pageContext.define("admin.security.user.verifyAuthority",["widget/data/datatable","widget/tab"],function(page){
	var _record;
	page.ready = function(){

		var gridOption = {
			selectMode:'multi', /* 多选：multi,单选：single,默认：normal */
			height:300, 
			editable:true,
			pageOp:{
				// el:"#pageDemo",
				pageSize:10,
				curPage:1,
				pageSizeRange:[10,20,50,100,200,500]
			},
			columns:[
				{
					field:"id",
					header:"ID",
					width:120,
					editable:false
				},{
					field:"role",
					header:"角色",
					editor:{
						type:"combobox",
						dataset:"services/xx"
					},
					width:220
				},{
					field:"dataRange",
					header:"数据范围",
					width:220,
					editable:false
				},{
					field:"status",
					header:"状态",
					renderer:function(val){
						return val ? "状态  "+val : "";
					}
				}
			]
			,dataset:[]
			,operation:{
				search:{
					btn:"#btnSearch",
					panel:"#formSearch"
				}/*,
				
				add:{
					btn:"#btnAddAuthority",
					data:{
						dataRange:"No Control"
					}
				},
				save:{
					btm:"#btnSaveAuthority"
				}*/
				// TODO:这里可以添加更多选项，例如批量删除，等选中后批量操作的内容，比如批量更新某个属性（状态）
				// batch {  del : { btn:"#btnBatchDelete" }, enable:{ btn:"#btnEnableStatus" , ajax:{ url:xxx ,data:data, method:"post"}}
			}
		};
		
		// 绑定表格 
		$("#gridVerifyAuthority").xWidget("datatable",gridOption);   
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