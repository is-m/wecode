define(["widget/factory","jquery","holder"],function(widget,$,h){
	// http://www.51xuediannao.com/js/texiao/holder.html
	// https://blog.csdn.net/supergao222/article/details/78650585
	widget.define("devtool/imgPlace",{ 
		ready:function(){
			// 默认宽度100% 高度150px
			// holder官网 https://github.com/imsky/holder
			// 
		    var p = this.op.place || '100px150'; 
			this.$dom.attr("data-src","holder.js/"+p.replace(/%/g,'p'));
			// TODO:holder.js run 这个需要移动到页面加载完成后执行，否则可能会执行多次渲染影响性能,需要检查源码
			h.run();
		    //debugger
			//h.addImage("holder.js/"+p+"?theme=new&text=此处请放一张绿色的图片 \n 最好有边框&auto=yes", this.$dom.get(0)).run() 
		} 
	});
	
});