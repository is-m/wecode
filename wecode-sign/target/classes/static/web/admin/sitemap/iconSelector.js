// TODO: 1.pageContext 需要预加载 ，2.widget/data/datable 需要在使用时加载
require(["rt/pageContext"],function(pageContext){
	pageContext.define("admin.sitemap.iconSelector",function(page){
		var _selectValue = null;
		page.ready = function(){ 
			$("#icon-selector").click(function(e){ 
				var el = e.target;
				if(el.tagName.toLowerCase() == "i"){ 
					//  如果当前点击的不是激活的则取消激活
					if(el.className.indexOf("active") < 0){
						_selectValue = "fa:"+el.className;
						$("#icon-selector").find("i.active").removeClass("active");
						$(el).addClass("active");
					}
					
				}
				
			});
		};
		
		page.click = function(el){
			
		}
		
		page.getSelectedIcon = function(){
			return _selectValue;
		}
		
	});
	
});