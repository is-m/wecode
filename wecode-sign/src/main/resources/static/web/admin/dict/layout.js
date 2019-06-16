pageContext.controller("admin.dict.layout",["widget/tab"],function(page){
	
	page.ready = function(){
		/*$("#demoTab").xWidget("tab",{
			pages:[{
				title:"数据列表",
				url:"/web/admin/dict/list.html", 
				allowClose:true
			}]
		}); */
	};

	page.toggleAdvanceSetting = function(){

	};

	page.selectParent = function(){
		var $baseProps = $("#dictBaseProperties");
		require(["ui/ui-confirm"],function(msg) {
			msg.dialog({
				title: "选择父栏目",
				columnClass: "medium",
				format: {
					type: "tree", // page tree table table2
					setting: {
						check: {
							enable: true,
							chkStyle: "radio",
							radioType: "all"
						},
						data: {
							key: {
								name: "name", // 指定label显示字段
								url: false, // 指定url处理字段
								icon: false  // 指定icon字段
							},
							simpleData: {
								enable: true,
								idKey: "id",
								pIdKey: "pid",
								rootPId: null
							}
						},
						dataset: "/services/base/property/tree",
						expandLevel: -1,
						defaultChecked: $baseProps.find("[name=pid]").val()
					}
				},
				buttons: {
					'select': function () {
						var nodes = this.treeManager.getCheckedNodes(true);
						if (nodes.length) {
							var node = nodes[0];
							$baseProps.nameEl("parentPath").val(node.path);
							$baseProps.nameEl("pid").val(node.id);
						} else {
							return false;
						}
					},
					'cancel': function () {
					}
				}
			});
		});
	};

	// 清理父节点内容
	page.clearParent = function () {
		var $baseProps = $("#dictBaseProperties");
		$baseProps.nameEl("pid").val("");
		$baseProps.nameEl("parentPath").val("");
	};
});
