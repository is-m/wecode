pageContext.controller("admin.text.Edit", ["rt/validation", "ui/ui-confirm"], function (page, $S, v, m) {

    var isCreate = true;
    var record = null;
    page.ready = function () {
        // 初始化校验规则
        $("#formEditText").formValidation("richTextCreateDto");
    };

    page.init = function (data) {

        if (data) {
            isCreate = false;
            record = data;

            console.log("update mode");
        } else {
            console.log("create mode");
        }

    };


    page.formSubmit = function () {
        var isValid = $("#formEditText").valid();
        if (isValid) {
            $("#formEditText").formSubmit(isCreate ? "post" : "put", "/services/base/text", function (resp) {
                // 关闭页签，刷新表格
                m.okTip("新增成功");
                page.closePage();
                $("#gridList").xWidget().reload();
            }, function (resp) {
                m.errTip("系统异常");
            });
        }
    };

    page.closePage = function () {
        $("#tabRichText").xWidget().closeTab();
    };

});