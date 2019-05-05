define(["widget/factory","jquery"],function(widget,$){
	
	var defulatConfig = {
		// 当前页
		curPage:1,
		// 页大小
		pageSize:10,
		// 总页数
		totalPage:1,
		// 总记录数
		totalRecords:0,
	};
	
	widget.define("pager",{
		template:"<h1>Hello this navbar Widget</h1>", 
		templateUri:"js/widget/data/pager.html",
		init:function(){
			// 计算总页数
			var pageSize = this.op.pageSize || 10;
			var totalRecords = 0;
			this.op.totalPage = this.op.totalPage < 1 ? 1 : this.op.totalPage;
		},
		loadData:function(){
			
		},
		beforeRender:function(html){
			return html;
		},
		afterRender:function(){
			 
		},
		ready:function(){
			var self = this,$dom = self.$dom;
			$dom.find(".prev").click(self.prev.bind(this));
			$dom.find(".next").click(self.next.bind(this));
			
			$dom.find(".go").click(function(){
				self.go($(this).text());
			});
		},
		destory:function(){
			
		},
		first:function(){
			if(this.op.curPage != 1){
				this.trigger("pager.first",1);
			} 
		},
		prev:function(){
			if(this.op.curPage > 1){ 
				this.trigger("pager.prev",this.op.curPage-1);
			} 
		},
		go:function(index){
			if(index < 1 && this.op.curPage > 1){
				this.trigger("pager.go",1);
			}else if(index > this.op.totalPage && this.op.curPage < this.op.totalPage ){
				this.trigger("pager.go",this.op.totalPage);
			}else if(index != this.op.curPage){
				this.trigger("pager.go",index);
			}
		},
		next:function(){
			if(this.op.curPage < this.op.totalPage){ 
				this.trigger("pager.next",this.op.curPage+1);
			} 
		},
		last:function(){
			if(this.op.curPage != this.op.totalPage){
				this.trigger("pager.last",this.op.totalPage);
			} 
		} 
	});
	
});