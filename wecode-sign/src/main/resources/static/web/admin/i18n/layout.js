pageContext.controller("admin.i18n.layout",["widget/tab"],function(page){
    page.ready = function(){
        $("#demoTab").xWidget("tab",{
            pages:[{
                title:"数据列表",
                url:"/web/admin/i18n/list.html",
                allowClose:true
            }]
        });
    };
});
