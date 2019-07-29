pageContext.controller("admin.richtext.layout", [], function (page, $S) {

    page.ready = function () {

    };

    page.edit = function (record) {
        $("#tabRichText").xWidget().addPage({
            title: record ? "编辑" : "新增",
            url: "/web/admin/richtext/edit.html",
            allowClose: true,
            afterLoad: function (page) {
                page.init(record);
            }
        });
    };


});