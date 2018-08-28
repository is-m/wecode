pageContext.define("admin.sitemap.edit",["rt/validation","rt/request"],function(page,validator,http,dateBox){
	var $form = $("#formEditSitemap");
	
	var isCreate = true;
	
	page.ready = function(){   
		// 从后台获取校验内容并绑定到元素
		//validator.bind($("#formEditSitemap"),"CatelogVO",["CreateGroup"]);
		 
		$("#btnSave").on("click",function(){
			// 往后台添加一个栏目
			var isValid = $("#formEditSitemap").valid();
			if(isValid){
				$("#formEditSitemap").formSubmit("post","/services/base/catelog",function(resp){
					// 关闭页签，刷新表格
					$("#demoTab").xWidget().closeTab();
					$("#btnSearch").trigger("click");
				},function(resp){
					alert('server error');
				}); 
			}
		});
		
		$("#btnSaveDraft").on("click",function(){
			alert("你点击了保存草稿");
		});
		
		$("#btnCancel").on("click",function(){
			console.log("click cancel");
			$("#demoTab").xWidget().closeTab();
		});
		
		$("#btnSelectIcon").on("click",function(){ 
			require(["ui/ui-confirm"],function(msg){
				msg.dialog({
					title:"图标选择",
					url:"/web/admin/sitemap/iconSelector.html",
					columnClass:"medium",
					buttons:{
						'select':function(){
							var selectedIcon = pageContext.get("admin.sitemap.iconSelector").getSelectedIcon();
							if(selectedIcon == null){
								$.alert('null');
								return false;
							}
							$("#btnSelectIcon").html("<i class='{0}'></i>".format(selectedIcon.split(":")[1]));
							$("#formEditSitemap").find("[name=icon]").val(selectedIcon);
						},
						'cancel':function(){}
					}
				});
				//uic.alert("需要选择图片哦");
			}); 
		});
		
		$("#btnSelectParent").on("click",function(){ 
			require(["ui/ui-confirm"],function(msg){
				msg.dialog({
					title:"选择父栏目",
					//url:"@{/web/admin/sitemap/iconSelector.html",
					columnClass:"medium",
					format:{
						type:"tree", // page tree table table2 
						setting:{
							check: {
								enable: true,
								chkStyle: "radio",
								radioType: "all"
							},
							data: {
								key:{
									name : "name", // 指定label显示字段
									url: false, // 指定url处理字段
									icon:false  // 指定icon字段
								},
								simpleData: {
									enable: true,
									idKey: "id",
									pIdKey: "pid", 
									rootPId: null
								}
							},
							dataset:"/services/base/catelog/tree",
							expandLevel:-1,
							defaultChecked: $form.find("[name=pid]").val()
						}
					},
					buttons:{
						'select':function(){ 
							var nodes = this.treeManager.getCheckedNodes(true);
							if(nodes.length){
								var node = nodes[0]; 
								$form.find("[name=parentName]").val(node.name);
								$form.find("[name=pid]").val(node.id);
							}else{
								return false;
							}
						},
						'cancel':function(){}
					}
				}); 
			});
		});
	}
	
	page.init = function(record){ 
		if(!record) return;
		
		$("#formEditSitemap").jsonData(record);
		isCreate = false;
	}
	
	page.exit = function(){
		console.log("壮士，别杀我");
	}
});
