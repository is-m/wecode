// TODO: 1.pageContext 需要预加载 ，2.widget/data/datable 需要在使用时加载
require(["rt/pageContext"],function(pageContext){
	pageContext.define("admin.sitemap.sitemapSelector",function(page){
		var _selectValue = null;
		
		page.ready = function(){ 
			 // 加载选择树
			
		};
		
		page.click = function(el){
			
		}
		
		page.getSelectedSitemap = function(){
			return _selectValue;
		}
		
	});
	
});