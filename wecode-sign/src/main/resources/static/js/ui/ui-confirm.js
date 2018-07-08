define(["jquery","jquery.confirm","rt/util"],function($,c,util){
	 
	var test = function(){
		$.confirm({ 
		    
		    title: 'Confirm!',
		    content: '确认要删除吗？',
		    buttons: {
		        confirm: function () {
		            $.alert('Confirmed!');
		        },
		        cancel: function () {
		            // $.alert('Canceled!');
		        } 
		    }
		});
	}
	
	var defaultOp = {
		draggable: true,
		closeIcon: true, 
		columnClass:"small", // xlarge 12 large 8 medium 6 small 4 xsmall 2
		// boxWidth: '30%', or '500px' 还需要设置 useBootstrap: false,
		title:"提示",
		content:"确定操作吗?" 
	};
	
	var alert = function(opOrMsg,title,icon,btns){ 
		if(typeof opOrMsg == "string"){
			var _op = $.extend(true,defaultOp,{
				title: title || '提示',
			    content: opOrMsg
			});
			$.alert(_op);
		}else{
			var _op = $.extend(true,defaultOp,opOrMsg);
			$.confirm({
			    title: 'Title',
			    content: 'url:text.txt',
			    onContentReady: function () {
			        var self = this;
			        this.setContentPrepend('<div>Prepended text</div>');
			        setTimeout(function () {
			            self.setContentAppend('<div>Appended text after 2 seconds</div>');
			        }, 2000);
			    },
			    columnClass: 'medium',
			});
		}
	}
	
	var confirm = function(msg,title,callback){
		var _msg=msg,_title,_callback;
		
		if(arguments.length == 2){
			_callback = title;
		}else{
			_title=title;
			_callback=callback;
		}
		
		$.confirm({
			title : _title || '提示',
			content : _msg || '确定进行操作吗',
			buttons : {
				confirm : function() {
					_callback(true);
				},
				cancel : function() {
					_callback(false);
				}
			}
		});
	};
	
	// TODO:如果加载了模块，关闭时，在销毁alert前，需要先销毁模块引用
	var dialog = function(op){
		if(op){
			var _op = $.extend(true,{},defaultOp,op);
			var _format = _op.format;
			if( _format && _format.type){ 
				var _type = _format.type,_setting = _format.setting;
				switch(_type){
					case "tree":
						if(!$.isPlainObject(_setting)) throw 'tree dialog setting no matched!'; 
						
						_op.content = function(){
							var self = this; 
							if(_setting.dataset){
								return util.getDataset(_setting.dataset)
								.done(function(data){
									if(data && data.length){
										var $treeObj = $("<div class='ztree'></div>")
										var treeManager = $.fn.zTree.init($treeObj, _setting , data); 
							        	self.setContent($treeObj);
							        	if(_setting.expandLevel == -1){
											treeManager.expandAll(true); 
										} 
							        	
							        	var _defChecked = _setting.defaultChecked;
							        	if(_defChecked){
							        		var defaultNode = treeManager.getNodeByParam(_setting.data.simpleData.idKey || "id",_defChecked);
 
							        		treeManager.checkNode(defaultNode,true, true);
							        		// 解决ztree显示样式为inline导致选中图标不显示的问题
							        		$treeObj.find(".radio_true_full,.radio_false_part").css({"display":"inline-block"});
							        	}
							        	self.treeManager = treeManager;
									}else{
										self.setContent("<div style='color:red'>未发现匹配的记录!</div>");
									} 
								})
								.fail(function(e){
									self.setContent("<div style='color:red'>获取数据失败!</div>");
								}); 
							} else{
								return "<div style='color:red'>未使用dataset选项，无法加载数据!</div>";
							}
						};
						break; 
					case "fileUpload":
						_op.content = function(){
							var self = this; 
							var dfd = $.Deferred();
							setTimeout(function(){
								require(["fileinputTheme"],function(fileInput){
									dfd.resolve(fileInput); 
								});
							},0);
							
							dfd.done(function(fileInput){
								var $fileInput = $('<div class="file-loading"><input id="fileImport" name="'+(_setting.inputName || 'file')+'" type="file" multiple></div>')
								self.setContent($fileInput);
								
								var $ctrlFile = $fileInput.find("#fileImport").fileinput({
								    theme: "explorer",
								    uploadUrl: appConfig.contextPath + _setting.uploadUrl,
								    showUpload: false, // 是否显示上传按钮,跟随文本框的那个
								    showRemove: true, // 是否显示清理按钮
								    layoutTemplates:{
								    	actionDelete:'<button type="button" class="kv-file-remove {removeClass}" title="{removeTitle}" {dataUrl}{dataKey}><i class="fa fa-trash-o"></i></button>\n',
								    	actionUpload:'', // 缩略图中的上传按钮
								    	actionZoom:'',   // 缩略图中的预览按钮
								    	close:''         // 头部的关闭按钮
								    },
								    minFileCount: 1,
								    maxFileCount: 5,
								    overwriteInitial: false,
								    previewFileIcon: '<i class="fa fa-file"></i>',
								    //showPreview:false,
								    initialPreview: [],
								    initialPreviewAsData: true, // defaults markup  
								    initialPreviewConfig: [],
								    preferIconicPreview: true, // this will force thumbnails to display icons for following file extensions
								    previewFileIconSettings: { // configure your icon file extensions
								        'doc': '<i class="fa fa-file-word-o text-primary"></i>',
								        'xls': '<i class="fa fa-file-excel-o text-success"></i>',
								        'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
								        'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
								        'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
								        'htm': '<i class="fa fa-file-code-o text-info"></i>',
								        'txt': '<i class="fa fa-file-text-o text-info"></i>',
								        'mov': '<i class="fa fa-file-movie-o text-warning"></i>',
								        'mp3': '<i class="fa fa-file-audio-o text-warning"></i>',
								        // note for these file types below no extension determination logic 
								        // has been configured (the keys itself will be used as extensions)
								        'jpg': '<i class="fa fa-file-photo-o text-danger"></i>', 
								        'gif': '<i class="fa fa-file-photo-o text-muted"></i>', 
								        'png': '<i class="fa fa-file-photo-o text-primary"></i>'    
								    },
								    previewFileExtSettings: { // configure the logic for determining icon file extensions
								        'doc': function(ext) {
								            return ext.match(/(doc|docx)$/i);
								        },
								        'xls': function(ext) {
								            return ext.match(/(xls|xlsx)$/i);
								        },
								        'ppt': function(ext) {
								            return ext.match(/(ppt|pptx)$/i);
								        },
								        'zip': function(ext) {
								            return ext.match(/(zip|rar|tar|gzip|gz|7z)$/i);
								        },
								        'htm': function(ext) {
								            return ext.match(/(htm|html)$/i);
								        },
								        'txt': function(ext) {
								            return ext.match(/(txt|ini|csv|java|php|js|css)$/i);
								        },
								        'mov': function(ext) {
								            return ext.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i);
								        },
								        'mp3': function(ext) {
								            return ext.match(/(mp3|wav)$/i);
								        }
								    }
								}); 
								
								$ctrlFile.on("filebatchselected", function(event, files) {
						            $(this).fileinput("upload");
						        })
						        .on("fileuploaded", function(event, data) {
						        	console.log("file upload done!",arguments);
							        if(data.response){
							        	_setting.success && _setting.success(data);
							            //alert('处理成功');
							        }else{
							        	console.log("file upload faild",data);
							        }
							    });
							});
							
							return dfd;
						}
						break;
					case "page": 
					case "table": 
					case "table2":
						alert("no implement of ui-confirm "+_type);
						return;
					default:
						alert("no support of ui-confirm "+_type);
						return;
				} 
			} else if(_op.url){
				$.extend(_op,{
					content: function(){
				        var self = this; 
				        return $.ajax({
				            url:  appConfig.contextPath + op.url,
				            dataType: 'text',
				            method: 'get'
				        }).done(function (response) {
				        	response = response.replace(/@\{\s*(\S+)\s*\}/g,function(m,i,o,n){
				     	       return appConfig.contextPath+i;
				     	    });
				            self.setContentAppend($(response));
				            // TODO 找到上级绑定js模块
				        }).fail(function(){
				            self.setContentAppend('<div>Fail!</div>');
				        });
				    }
				});  
			}
			
			return _op.buttons ?  $.confirm(_op) : $.dialog(_op);
		}else{
			window.alert('no found url');
		}
	};
	 
	return {
		test:test,
		alert:alert,
		confirm:confirm,
		dialog:dialog 
	};
	
});