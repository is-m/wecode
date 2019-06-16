pageContext.controller("admin.i18n.layout", ["widget/data/datatable","widget/tab"], function (page) {

    page.ready = function () {

        var gridOption = {
            selectMode: 'mutli', /* 多选：mutli,单选：single,默认：normal */
            height: 455,
            pageOp: {
                // el:"#pageDemo",
                pageSize: 10,
                curPage: 1,
                pageSizeRange: [10, 20, 50, 100, 200, 500]
            },
            columns: [
               {
                    field: "id",
                    header: "ID",
                    width: 120
                }, {
                    field: "name",
                    header: "键",
                    width: 200
                }, {
                    field: "value",
                    header: "内容",
                    width: 200
                }, {
                    field: "remark",
                    header: "说明",
                    width: 300
                }
            ]
            , dataset: "/services/security/user?curPage={curPage}&pageSize={pageSize}"
            , operation: {
                search: {
                    btn: "#btnSearch",
                    panel: "#formSearch"
                }
                // TODO:这里可以添加更多选项，例如批量删除，等选中后批量操作的内容，比如批量更新某个属性（状态）
                // batch {  del : { btn:"#btnBatchDelete" }, enable:{ btn:"#btnEnableStatus" , ajax:{ url:xxx ,data:data, method:"post"}}
            }
        };

        // 绑定表格
        $("#gridI18n").xWidget("datatable", gridOption);
    };


});