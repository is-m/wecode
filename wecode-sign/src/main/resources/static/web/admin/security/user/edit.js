pageContext.controller("admin.security.user.edit", ["rt/validation", "ui/ui-confirm"], function (page, $S, v, m) {

    var isCreate = true;

    page.ready = function () {

    };

    page.init = function (_isCreate, record) {
        var $form = $("#formEditUser");
        // 更新时加载数据
        if (!_isCreate) {
            isCreate = false;
            if (!record) throw 'record is illegal'
            $form.formFill("/services/security/user/{0}".format(record.id));
        } else {
            record && $form.jsonData(record);
        }
        $form.formValidation("userDto", isCreate ? "Create" : "Upadte");
    };

    page.closePage = function () {
        $("#demoTab").xWidget().closeTab();
    };

    page.formSubmit = function () {
        console.log("submit");
        // 往后台添加一个栏目
        var isValid = $("#formEditUser").valid();
        if (isValid) {
            $("#formEditUser").formSubmit("post", "/services/security/user", function (resp) {
                // 关闭页签，刷新表格
                m.okTip("新增成功");
                page.closePage();
                $("#gridUser").xWidget().reload();
            }, function (resp) {
                m.errTip("系统异常");
            });
        }
    };

});