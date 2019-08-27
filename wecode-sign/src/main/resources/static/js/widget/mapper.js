// 组件映射与组件参数解析
define(["jquery","rt/util"], function ($,util) {
    // 每个组件通常有两个属性，一个是组件提供程序，一个是组件属性解析
    // comp:组件路径，起始位置为 widget，最终会使用该组件对象进行元素的渲染
    // parser：组件属性解析，解析规则：组件身上本身的属性会全部解析到
    var menu = {
        comp: "widget/menu",
        parser: function (html) {
            var $this = $(html);
            var dataset = $this.attr("dataset");
            var displayField = $this.attr("displayField") || "name";
            var urlField = $this.attr("urlField") || "url";
            var iconField = $this.attr("iconField") || "icon";
            var childrenField = $this.attr("childrenField") || "children";
            var openType = $this.attr("openType") || "target";

            var menuOp = {
                dataset : dataset,
                displayField:displayField,
                urlField:urlField,
                iconField:iconField,
                openType:openType,
                childrenField:childrenField
            };

            return menuOp;
        }
    };

    /**
     *
     * @type {{comp: string, parser: (function(*=): {})}}
     */
    var tabs = {
        comp: "widget/tab",
        parser: function (html) {
            // 按html解析tabOption
            var $this = $(html);
            var id = $this.prop("id");
            var $children = $this.children();
            var pages = [];
            for (var i = 0; i < $children.length; i++) {
                var $page = $children.eq(i);
                var tagName = $page.prop("tagName").toLowerCase();
                if (tagName !== "tab") {
                    throw "m-tabs children only include tab elements"
                }
                var pageId = $page.prop("id");
                var pageTitle = $page.prop("title");
                pages.push({
                    id: pageId,
                    title: pageTitle,
                    content: $page.html()
                });
            }
            return {
                id: id,
                pages: pages
            };
        }
    };

    var grid = {
        comp: "widget/data/datatable",
        parser: function (html) {
            var $this = $(html);
            // id="gridList" dataset="/services/richtext/list" pageSize="15" editable="true" sortable="true" selectMode="single/multi"
            var id = $this.prop("id");           // 组件ID
            var showSeq = $this.attr("showSeq"); // 是否显示序号 [true,false]
            var selectMode = $this.attr("selectMode"); // 选择模式，[single,multi]
            var $children = $this.children();
            // attr 和 prop的区别在于
            var dataset = $this.attr("dataset");

            var searchBtn = $this.attr("searchBtnId");
            var searchForm = $this.attr("searchFormId");

            var columns = [];
            for (var i = 0; i < $children.length; i++) {
                var $col = $children.eq(i);
                var tagName = $col.prop("tagName").toLowerCase();
                if (tagName !== "column") {
                    console.warn("m-grid children only include column elements ,skip column type is " + tagName);
                    continue;
                }

                var colOp = {
                    field: $col.attr("field"),
                    type: $col.attr("type"),
                    header: $col.attr("header"),
                    // rest 类型数据
                    rest: $col.attr("rest"),
                    // 字典格式化数据
                    dict: $col.attr("dict"),
                    sortable: $col.attr("sortable"),
                    editable: $col.attr("editable")
                };
                if ($col.text()) {
                    colOp.renderer = $.proxy(function (value, record) {
                        return this.value;
                    }, {value: $col.prop("innerHTML")});
                }
                // field="id"  header="ID"
                columns.push(colOp);
            }

            var gridOp = {
                id: id,
                showSeq: showSeq,
                selectMode: selectMode,
                columns: columns,
                dataset: dataset,
                operation: {}
            };

            if (searchBtn) {
                gridOp.operation.search = {
                    btn: searchBtn,
                    panel: searchForm
                };
            }

            var pageOp = $this.attr("pageable") !== "false" ? {} : false;
            if (pageOp) {
                pageOp.pageSize = parseInt($this.attr("pageSize"));
                if (!pageOp.pageSize || pageOp.pageSize < 10) {
                    pageOp.pageSize = 10;
                }
                pageOp.curPage = parseInt($this.attr("curPage"));
                if (!pageOp.curPage || pageOp.curPage < 0) {
                    pageOp.curPage = 0;
                }
                pageOp.pageSizeRange = [10, 20, 50, 100, 200, 500];
            }
            gridOp.pageOp = pageOp;

            return gridOp;
        }
    };

    var register = function (tag, comp) {
        mapper.tag = {comp: comp};
    };

    var mapper = {
        "m-tabs": tabs,
        "m-menu": menu,
        "m-grid": grid,
        "reg": register// 注册
    };

    return mapper;
});
