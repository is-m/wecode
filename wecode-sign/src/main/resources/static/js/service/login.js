// 登录服务
define(["jquery", "rt/validation", "rt/store"], function ($, v, store) {

    var _login = function (postData) {
        if (!postData.username || !postData.password) {
            throw 'no found username or password field of ' + JSON.stringify(postData);
        }

        return $.ajax({
            type: "get",
            url: $$path + "/security/authentication/token",
            data: {
                identifier: postData.username
                , secret: postData.password
                , verifyCode: null
            },
            dataType: "json"
        }).success(function (tokenObject) {
            //alert("登录成功，设置用户token之前");

            var oldTokenObj = store.get("$USER_TOKEN$");

            store.set("$USER_TOKEN$", tokenObject, tokenObject.expire);
            // 如果当前是因为token过期导致的用户登陆失败，则检查当前登陆用户是否有有过更新，如果有则提示用于并刷新界面，防止发生信息安全问题
            if(oldTokenObj && oldTokenObj.identifier != tokenObject.identifier){
                require(["ui/ui-confirm"],function (m) {
                    m.confirm("登陆用户已经改变，界面将会被刷新?","登陆提示",function (isOk) {
                        if(isOk){
                            location.reload();
                        }
                    });
                })
            }
            //alert("登录成功 "+store.get("$USER_TOKEN$"));
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
        login: function (submitData) {
            var dtd = $.Deferred();
            //debugger
            var postData = null;
            // 表单型数据
            if (submitData.jquery) {
                var $form = submitData;
                if ($form.valid()) {
                    postData = $form.jsonData();
                }
            } else if ($.isPlainObject(submitData)) { // 直接是json数据
                postData = submitData;
            } else { // 按参数位提交数据
                if (arguments.length == 1) throw 'illegal arg length';
                postData = {username: arguments[0], password: arguments[1]};
            }

            //alert("准备用帐号[{0}]密码[{1}]登录".format(postData.username,postData.password));
            _login(postData).success(dtd.resolve).error(dtd.reject);
            //alert("准备用帐号[{0}]密码[{1}]登录".format(username,password));
            return dtd;
        },
        logout: function () {
            try {
                store.remove("$USER_TOKEN$");
            } finally {
            }
        }
    }

});