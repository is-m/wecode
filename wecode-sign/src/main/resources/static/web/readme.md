## TODO
- [x] 测试内容
- [] JS:内容区域【context】内嵌html和js的预处理（添加界面元素的解析以及加载页面时缓存解析结果，并替换掉js加载标签【将<script src=xxx>标签的内容通过ajax获取后，写入当当前URL对应的HTML的末尾，并缓存当前URL，减少各类交互，要达到的目的即一个HTML&js&css只加载一次，不再触发多余的请求】）
- [] JS:用户行为分析js编写
- [] 3 移动端可能还需要根据浏览器版本加载fastClick组件
- [] JS: 需要重新 界面布局，菜单栏样式，自定义响应式菜单，以及菜单定宽后的滚动条 参考http://themes.getbootstrap.com/preview/?theme_id=6743&show_new= slimScrollBar 


不同的字体库
http://demo.amitjakhu.com/dripicons/


可以将表格的底纹和数据行线条去除



页面隐藏和关闭要按页面事件区分开，
例如tab页签切换时的应酬 ,因为隐藏不需要清理页面的冗余元素（日期控件，tooltip（这个要隐藏））
关闭时则需要清理当前页面对应的HTML元素（不能一刀切，只能按页面上下文处理，防止出现bug）


系统组件需要进行拆分为几种类型，1.标签型，2.HTML扩展属性型，3.class样式型 但是3种都要可以直接通过JS渲染，界面渲染只是另一中形式
标签型：就是在HTML上直接写<xx-table>
属性型：就是在HTML 元素上直接扩展属性，例如<a data-toggle='tooltip' data-tooltip-title='123'>移过来看看</a>
样式型：就是在HTML 元素的样式上直接写例如<form class='init mu-form'></form>


workspaceVO中应该添加替换的HTML或者JS内容，以便上线发布时出现调试功能或者临时替换方案（但是每次发布后这些内容应该失效，防止忘记修改，需要人为指定迁移到当前版本）


boostrap 组件
https://www.cnblogs.com/landeanfen/archive/2016/06/22/5461849.html



组件开发原则，能用HTML解决的不要用js，能用js解决的不要走服务
