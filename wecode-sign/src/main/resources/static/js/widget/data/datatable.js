define(["widget/factory", "jquery", "jqueryui", "template", "rt/util", "data/adapter", "ui/ui-confirm"], function (widget, $, $ui, tmpl, util, adpt, msg) {
    // 预处理数据行渲染模版
    /*
         '<%if(colOp.renderer){ %>' +
                    '<%=# colOp.renderer(fieldValue,dataItem,colOp) %>' +
                '<%}else{%>' +
                    '<%= fieldValue %>' +
                '<%}%>' +
     */
    var _dataRowTemplate =
        '<%  for(var j=0;j<$widget._data.length;j++){ var dataItem = $widget._data[j]; %>' +
        '<tr data-row-index="<%=j%>"> ' +
        '<% if($widget.showSeq !== false){%>' +
        '	<th><span><%=(j+1)%></span></th>' +
        '<%}%>' +
        '<% if($widget.selectMode == "mutli"){%>' +
        '<td class="table-cell-checkbox row-selection"><span><input type="checkbox" /></span></td>' +
        '<%}%> ' +

        '<% ' +
        'for(var i=0;i<$widget.columns.length;i++){ ' +
        'var colOp = $widget.columns[i];' +
        'var field = colOp.field;' +
        'var fieldValue = field ? dataItem[field] : null;' +
        '%>' +
        '<td data-field="<%=field%>" class="table-cell <%= colOp.editable !== false ? \'cell-editable\' : \'cell-read\' %>"></td>' +
        '<%}%>   ' +
        '</tr>' +
        '<%}%> ';
    tmpl('datatable-datarows', _dataRowTemplate);


    var defaultTreeOp = {}

    widget.define("datatable", {
        template: "<h1>Hello this navbar Widget</h1>",
        templateUri: "js/widget/data/datatable.html",
        init: function () {
            // 初始化表格，列部分参数
            // 计算表格总宽度
            var _op = this.op;
            console.log("init datatable", _op);

            var tableWidth = 0;
            for (var i = 0; i < _op.columns.length; i++) {
                var colOp = _op.columns[i];
                tableWidth += colOp.width || 150;
                // TODO:如果是操作类型，暂且设置下字段，防止渲染单元格出问题，后续可能会进行优化, _bindCellData
                if (colOp.type == 'operate') {
                    colOp.field = "__operate" + i;
                }
            }

            _op["_tableWidth"] = tableWidth;
            // 初始化树选项
            if (this.op.treeOp) {
                this.op._treeColumn = $.extend(this._getColumnOp(this.op.treeOp.field), {treeField: true});
            }
        },
        loadData: function () {

        },
        beforeRender: function (html) {
            return html;
        },
        afterRender: function () {
            var self = this;
            var $tableHead = this.$dom.find(".table-scroll-header:eq(0)");
            var $tableBody = this.$dom.find(".table-scroll-body:eq(0)");

            if (this.op.editable === true) {
                $tableBody.on("click", function (e) {
                    var $el = $(e.target);
                    console.log(e);
                    if ($el.is("td") && $el.is(".cell-editable")) {
                        // 如果单元格属于编辑状态，则不进行处理
                        if ($el.is("editing")) {
                            return;
                        }

                        // 取消表格中正在编辑的单元格（同时只能有一个编辑状态的值），注意取消前要保证数据是通过校验的，如果未通过该单元格无法激活编辑状态
                        var $editableCells = $el.closest("tbody").find("td.cell-editable.editing");
                        if ($editableCells.length) {
                            self._cancelAllEditingCells();
                        }

                        // 标记单元格为编辑
                        $el.addClass("editing");

                        var editorRender = tableEditorRenderMap["default"];
                        var field = $el.data("field");
                        var colOp = self._getColumnOp($el.data("field"));
                        var ctrlId = "editor_" + field;
                        var $editContainer = self.$dom.find("#" + ctrlId);
                        var top = $el.position().top;
                        if ($editContainer.length) {
                            $editContainer.css({top: top, display: "block"});
                        } else {
                            var h = $el.outerHeight(), w = $el.outerWidth(), left = $el.position().left;
                            $editContainer = $('<div id="{0}" style="display:block; position:absolute; top:{1}px; left:{2}px; height:{3}px; width:{4}px;" class="datatable-edit-area" data-field="{5}"></div>'.format(ctrlId, top, left, h, w, field))

                            if (colOp.editor && colOp.editor.type) {
                                editorRender = tableEditorRenderMap[colOp.editor.type];
                            }

                            editorRender.render($editContainer,colOp.editor).done(function ($ctrl) {
                                $ctrl.xWidget() && $ctrl.xWidget().setValue("okay");
                                // 绑定下拉框点击文本输入区域时不触发文档click事件，防止编辑状态的框进入写入表格值的状态
                                $editContainer.find("input").click(function () {
                                    return false;
                                });
                            });

                            self.$dom.append($editContainer);
                            $editContainer.find("input").click(function () {
                                return false;
                            }).val("123");

                            console.log('click bind editor rewrite');
                            // 绑定文档点击时，将编辑器还原成正常表格内容
                            $(document).one("click", function (e) {
                                if ($editContainer.length && $editContainer.is(":visible")) {
                                    var source = $el.html();
                                    var target = $editContainer.find("input").val();
                                    var isOldRow = !$el.closest("tr").is(".datatable-row-added");
                                    if (source != target) {
                                        $el.html(target);
                                        // 不是新行时才添加修改标识
                                        if (isOldRow) {
                                            $el.addClass("table-cell-value-changed");
                                            $el.closest("tr").addClass("datatable-row-edited");
                                        }
                                    } else {
                                        if (isOldRow) {
                                            $el.removeClass("table-cell-value-changed");
                                            if (!$el.closest("tr").find(".table-cell-value-changed").length) {
                                                $el.closest("tr").removeClass("datatable-row-edited");
                                            }
                                        }

                                    }
                                    $editContainer.empty().remove();
                                    $el.removeClass("editing");
                                    console.log("cancel editor of old '{0}' to new '{1}' value".format(source, target));
                                }
                            });
                        }
                        // 阻止浏览器默认事件行为
                        return false;
                    }

                });
            }


            // TODO:检查是否存在需要绑定事件或操作的行
            //$tableDataRows.html(rowHtml);

            //this.$dom.find("th").resizable();
            /*this.$dom.find(".table-th-resize").on("mousedown",function(e){
                console.log("mouse down",e);
                var $this = $(this);
                var $th = $this.closest("th");

                // 清理，重新绑定事件
                $this.off("mousemove mouseup");

                $this.data("srcPageX",e.pageX);
                $this.on("mousemove",function(e){
                    var pageX = e.pageX;
                    var srcPageX = $(this).data("srcPageX");
                    var $th = $(this).closest("th");
                    var width = $th.width();
                    console.log("move",pageX ,srcPageX, width,e);
                    $th.width(pageX - srcPageX + width);
                });

                $this.on("mouseup",function(e){
                    console.log("mouse up",e);
                    $(this).un("mousemove").un("mouseup");
                })

            })*/

            // 绑定滚动条事件
            $tableBody.on("scroll", function () {
                var bodyScrollLeft = $(this).scrollLeft();
                $tableHead.scrollLeft(bodyScrollLeft);
            });

            // 绑定全选事件
            if (this.op.selectMode == "mutli") {
                $tableHead.find(".table-th-selection :input").on("click", function () {
                    if ($(this).is(":checked")) {
                        $tableBody.find(".row-selection :input").each(function (i, el) {
                            if (!$(el).is(".disabled") && !$(el).is("[disable]")) {
                                el.checked = true;
                            }
                        });
                    } else {
                        $tableBody.find(".row-selection :input").attr("checked", false);
                    }

                });
            }

            // 初始化提示框
            //$tableBody.find("tr>td>span",function(){

            //});
            if (this.op.operation) {
                var _oper = this.op.operation;
                if (_oper.search && _oper.search.btn) {
                    util.el(_oper.search.btn).on("click", function () {
                        self.op.pageOp && $.extend(self.op.pageOp, {curPage: 1});
                        self.reload();
                    });
                }
                if (_oper.add && _oper.add.btn) {
                    util.el(_oper.add.btn).on("click", function () {
                        self._renderEditableRow();
                    });
                }
                if (_oper.del && _oper.del.btn) {
                    util.el(_oper.del.btn).on("click", function (e) {
                        var $selections = self.$dom.find(".row-selection :checked");
                        if (!$selections.length) {
                            msg.errTip("没有数据被选中！");
                            return;
                        }
                        // 获取选中的数据
                        $selections.each(function (i, el) {
                            var $row = $(el).closest("tr");
                            // 如果是新行，则直接整行删除
                            if ($row.is(".datatable-row-added")) {
                                $row.empty().remove();
                            } else {
                                $row.removeClass("datatable-row-edited").addClass("datatable-row-deleted");
                                $(el).attr("checked", false);
                            }
                        });
                    });
                }
                if (_oper.save && _oper.save.btn && _oper.save.ajax) {
                    util.el(_oper.save.btn).click(function () {
                        // 推迟调用表格的保存,防止出现编辑控件未赋值给表格的情况
                        setTimeout(function () {
                            self.saveData();
                        },9);
                    });
                }
            }

            if (this.op.autoLoad !== false) {
                self.reload();
            }
        },
        ready: function () {
        },
        destory: function () {

        },
        /**
         * 保存表格数据
         * @param ajaxOption
         */
        saveData:function(ajaxOp){
            var _ = this , modifiedData = { adds: [], upds: [], dels: [] };

            _.$dom.find("tbody>tr").each(function () {
                var $tr = $(this);
                var record = $tr.data("record");
                if ($tr.is(".datatable-row-added")) { // 新增行
                    modifiedData.adds.push(record);
                } else if ($tr.is(".datatable-row-edited")) {
                    modifiedData.upds.push(record);
                } else if ($tr.is(".datatable-row-deleted")) {
                    if (record.id) {
                        modifiedData.dels.push(record.id);
                    } else {
                        // 默认是拿数据的ID作为删除的数据，如果数据ID为空，要么指定table的删除主键属性，要么不能删除
                        throw 'Not captured deleted record field with id';
                    }
                } else {
                    // unchanged data
                }
            });

            if (!(modifiedData.dels.length || modifiedData.adds.length || modifiedData.upds.length)) {
                msg.errTip("没有记录被改变！");
                return;
            }

            var ajaxOption = ajaxOp || {};
            if($.isEmptyObject(ajaxOption)){
                var saveOp = _.op.operation.save;

                if (typeof saveOp.ajax == 'string') {
                    ajaxOption = {
                        url: saveOp.ajax,
                        method: "put"
                    }
                } else if ($.isPlainObject(saveOp.ajax)) {
                    ajaxOption = saveOp.ajax;
                    if (!(ajaxOption.method || ajaxOption.type)) {
                        ajaxOption.method = "put";
                    }
                }
                ajaxOption.data = modifiedData;
            }
            util.doAjax(ajaxOption).success(function (resp) {
                msg.okTip("保存成功！");
            });
        },
        _getColumnOp: function (field) {
            var _cols = this.op.columns;
            if ($.isNumeric(field)) return _cols[field];
            if (typeof field == 'string') {
                for (var i = 0; i < _cols.length; i++) {
                    var col = _cols[i];
                    if (col && col["field"] == field) {
                        return col;
                    }
                }
                throw 'no found column op [' + field + ']';
            }
            return 'getColumnOp arguments not support';
        },
        showMessage: function (html) {
            var $messageBody = this.$dom.find(".table-message-body:eq(0)");
            $messageBody.html(html);
            this.$dom.find(".table-message:eq(0)").show();
        },
        hideMessage: function () {
            this.$dom.find(".table-message:eq(0)").hide();
        },
        _treeRowsExpand: function ($row, isExpand) {
            var self = this;
            var _row = $row.is("tr") ? $row : $row.closest("tr");
            var _rcd = _row.data("record");
            var _treeOp = this.op.treeOp;

            var $childrenRows = this.$dom.find("tr[data-tree-parent={0}]".format(_rcd[_treeOp.idKey]));
            $childrenRows.each(function () {
                var $childTr = $(this);
                if ($childTr.is("[data-tree-expand=true]")) {
                    self._treeRowsExpand($childTr, isExpand);
                }
                $childTr[isExpand ? "show" : "hide"]();
            });
        },
        _renderTreeRows: function ($rows, records) {
            var _self = this;
            var _treeOp = this.op.treeOp;
            // 初始化行
            // 加载树字段内容
            if (_treeOp) {
                $rows.find("td[data-field={0}]".format(this.op._treeColumn.field)).each(function (n, i) {
                    var $td = $(this);
                    var $row = $td.closest("tr");
                    $td.css("text-align", "left");
                    $td.css("padding-left", (+$row.data("treeLevel") * 20 + 10) + "px")

                    var record = $row.data("record");
                    record._isLeaf = typeof record._isLeaf !== "undefined" ? record._isLeaf : !(record.children && record.children.length);
                    var $folderIcon = $('<i class="fa {0}"></i>'.format(record._isLeaf ? "fa-file-o" : "fa-folder"));
                    if (!record._isLeaf) {
                        $folderIcon.on("click", function () {
                            var $this = $(this);
                            $this.toggleClass2("fa-folder", "fa-folder-open", function (curr, el) {
                                //debugger
                                var record = el.closest("tr").data("record");
                                // 当前是打开文件命令
                                if ("fa-folder-open" == curr) {
                                    $row.attr("data-tree-expand", true);
                                    // 如果当前行是已经初始化过的，则直接展示或隐藏子项
                                    if ($row.is("[data-tree-init=true]")) {
                                        _self._treeRowsExpand($row, true);
                                        return;
                                    }
                                    var tempData = $.extend({}, _self.op, {"_data": record.children});
                                    var rowHtml = tmpl('datatable-datarows', {$win: window, $widget: tempData});
                                    var $tempRows = $(rowHtml);

                                    // 在行上绑定数据
                                    $tempRows.each(function (i, n) {
                                        var _rowData = tempData._data[i];
                                        $(this).attr("data-tree-parent", _rowData[_treeOp.pIdKey || 'parentId'])
                                            .attr("data-tree-level", +$row.data("treeLevel") + 1)
                                            .attr("data-tree-expand", false)
                                            .data("record", _rowData);
                                        _self._bindCellData($(this), _rowData);
                                    });
                                    _self._renderTreeRows($tempRows);
                                    // 打开节点
                                    $row.after($tempRows);
                                    $row.attr("data-tree-init", true);
                                    //_self._treeRowsExpand($row,true);
                                } else {
                                    // 关闭节点，找到所有未隐藏的子节点进行隐藏处理
                                    $row.attr("data-tree-expand", false);
                                    _self._treeRowsExpand($row, false);
                                }
                            })
                        });
                    }

                    $td.prepend($folderIcon);
                });
            }
        },
        _renderEditableRow: function () {
            console.log("添加编辑行");
            var tempData = $.extend({}, this.op, {"_data": [{}]});
            var rowHtml = tmpl('datatable-datarows', {$win: window, $widget: tempData});
            var $row = $(rowHtml);
            $row.addClass("datatable-row-added");
            var rowData = this.op.operation.add.data || {};
            $row.data("record", rowData);
            this._bindCellData($row, rowData);
            // 加载数据
            var $tableDataRows = this.$dom.find(".datatable-rows:eq(0)");
            $tableDataRows.append($row);
            this.hideMessage();
        },
        /**
         * 设置单元格的值
         * @param value 设置的值，必填
         * @param cellOrCellIndexOrFieldName 单元格元素或者单元格索引，或者字段名，必填
         * @param rowIndex 行索引，选填，当cellOrCellIndexOrFieldName 为index或者fieldName时必填
         */
        setCellValue: function (value, cellOrCellIndexOrFieldName, rowIndex) {
            if (!cellOrCellIndexOrFieldName && cellOrCellIndexOrFieldName !== 0) throw 'setCellValue cellOrCellIndexOrFieldName cannot be null or empty';
            var _ = this;
            // 获取Cell
            var $cell;
            if ($.isNumeric(cellOrCellIndexOrFieldName)) { // 列索引
                $cell = _.$dom.find("[data-row-index=" + rowIndex + "]").find("[data-feild='" + _.op.columns[cellOrCellIndexOrFieldName].field + "']");
            } else if (typeof cellOrCellIndexOrFieldName == "string") { // fieldName
                $cell = _.$dom.find("[data-row-index=" + rowIndex + "]").find("[data-feild='" + cellOrCellIndexOrFieldName + "']");
            } else if (cellOrCellIndexOrFieldName.jquery) {
                $cell = cellOrCellIndexOrFieldName;
            } else {
                throw 'not support cellOrCellIndexOrFieldName argument with value ' + cellOrCellIndexOrFieldName;
            }

            var colOp = _._getColumnOp($cell.data("field"));
            var record = $cell.closest("tr").data("record") || {};
            if (colOp.renderer) {
                // 渲染数据
                var renderValue = colOp.renderer(value, record, colOp, $cell);
                // 如果返回值不为 undefined 则进行渲染，控件设置方也可以使用$cell自己来社会资单元格内容,如果人为渲染了内容又返回了内容则会用返回的内容替换手动渲染的内容
                $cell.html(typeof renderValue !== 'undefined' ? renderValue : "");
            } else {
                $cell.html(value);
            }
        },
        // 取消并保存全部编辑状态的单元格
        _cancelAllEditingCells: function () {
            var _ = this;

            // 找到所有编辑的单元格
            _.$dom.find(".cell-editable.editing").each(function (i, e) {
                var $cell = $(this), $editor = $cell.children().xWidget();
                // 编辑器校验
                if ($editor.valid && !$editor.valid()) {
                    return false;
                }
                if ($editor.getValue) {
                    var value = $editor.getValue();
                    $editor.remove();
                    _.setCellValue(value, $cell);
                } else {
                    throw 'cannot support get value for cell editor of field is ' + $(this).data("field");
                }

            });

        },
        getRowData: function (rowIndex) {
            return this.op._data[rowIndex];
        },
        getSelectedAllRecords: function () {
            var result = [], _data = this.op._data;
            this.$dom.find(".row-selection :checked").each(function (i, el) {
                result.push(_data[+($(el).closest("[data-row-index]").data("rowIndex"))]);
            });
            return result;
        },
        // 绑定单元格数据
        _bindCellData: function ($row, record) {
            var _self = this;
            // 渲染列值
            $row.find("[data-field]").each(function () {
                var $cell = $(this);
                var field = $cell.data("field");
                // 字段属性为空值时不进行初始化
                if (field === '') return;
                _self.setCellValue(record[field], $cell);
            });
        },
        reload: function () {
            var _self = this;
            _self.$dom.find(".table-th-selection :input").each(function (i, el) {
                el.checked = false;
            });

            // 加载数据
            var $tableDataRows = this.$dom.find(".datatable-rows:eq(0)");

            // TODO:待后续替换成选中数据tbody区域，添加加载框
            this.showMessage("<i class='fa fa-spinner fa-spin'></i><p>正在加载...</p>");

            var dataset = this.op.dataset;
            // 如果当前有设置查询参数区域
            var searchPanel = util.getObj(this, "op.operation.search.panel");
            if (searchPanel != null && typeof dataset == 'string') {
                var data = util.el(searchPanel).jsonData();
                if (this.op.pageOp) {
                    dataset = {url: dataset.format(this.op.pageOp), data: data}
                } else {
                    dataset = {url: dataset, data: data}
                }
            } else if (!$.isArray(dataset)) { // 并且不是静态数据
                throw '存在查询条件区域，但是数据集不是URL请求，暂时未实现其他数据集+查询条件的处理逻辑';
            }

            util.getDataset(dataset).done($.proxy(function (data) {
                if (!data || data.length === 0) {
                    _self.showMessage('<i class="fa fa-info"></i> <p>没有匹配的记录</p>');
                    $tableDataRows.html("");
                    return;
                }

                data = adpt.translate("datatable", data);

                if (data && data.page && data.result) {
                    this.op._page = data.page;
                    this.op._data = data.result;
                } else {
                    this.op._data = $.isArray(data) ? data : (data.content || []);
                }


                // 加载分页内容
                if (this.op.pageOp && this.op._page) {
                    require(["widget/data/pager"], function (pager) {
                        var $tableBody = _self.$dom.find(".table-scroll-body");
                        var $pageEl = $tableBody.siblings(".pageContainer");
                        if (!$pageEl.length) {
                            $pageEl = $("<div class='pageContainer'></div>");
                            $tableBody.after($pageEl);
                        }

                        var pagerWidget = $pageEl.xWidget("pager", $.extend(true, _self.op.pageOp, _self.op._page));
                        pagerWidget.on(["pager.prev", "pager.go", "pager.next"], function (e, page) {
                            _self.op.pageOp.curPage = page;
                            _self.reload();
                        });
                    })
                }


                // 如果是树结构表格，则配置，同步树（默认），异步树，来格式化和显示节点信息，同步树表格不进行翻页
                var _treeOp = this.op.treeOp;
                if (_treeOp) {
                    // 根据树表格参数，重写数据格式 <i class="fa fa-file-o"></i>
                    this.op._data = util.toTree(this.op._data, _treeOp.rootPId, _treeOp.idKey, _treeOp.pIdKey);
                }
                var rowHtml = tmpl('datatable-datarows', {$win: window, $widget: this.op});
                var $rows = $(rowHtml);

                // 在行上绑定数据
                $rows.each(function (i, n) {
                    var _rowData = _self.op._data[i];
                    $(this).data("record", _rowData);
                    _self._bindCellData($(this), _rowData);
                    if (_treeOp) {
                        $(this).attr("data-tree-parent", "root");
                        $(this).attr("data-tree-level", 0);
                    }
                });

                _self._renderTreeRows($rows);

                $tableDataRows.html($rows);
                _self.hideMessage();
            }, this)).fail(function () {
                // TODO:清空数据 ，待优化显示效果
                _self.showMessage('<i class="fa fa-bolt"></i> <p>数据获取失败</p>');
            });
        }

    });

    var tableEditorRenderMap = {
        "default": {
            render: function ($td) {
                var dtd = $.Deferred();
                var $ctrl = $('<input class="form-control" style="width:100%;height:100%" />');
                $td.html($ctrl);
                dtd.resolve($ctrl);
                return dtd;
            },
            setValue: function (v, record) {

            },
            getValue: function () {

            }
        },
        "combobox": {
            render: function ($td,editorOp) {
                //dataset:"services/xx",
                // 						valueField:"id",
                // 						textField:"name"
                var dtd = $.Deferred();
                require(["widget/form/combo"], function (combo) {
                    var $combo = $("<div></div>");
                    $combo.xWidget("form.combo", editorOp || {});
                    $td.html($combo);
                    dtd.resolve($combo);
                });
                return dtd;
            },
            setValue: function (v, record) {

            },
            getValue: function () {

            }
        }
    }
});