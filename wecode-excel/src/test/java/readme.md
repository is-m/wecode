# JXLS API 详解





## 自定义表达式解析，比如原本为${},下面执行将以[[xxx.xxx]]作为解析器
JxlsHelper.getInstance().buildExpressionNotation("[[", "]]").processTemplate(is, os, context);

## 好的实践

### 使用 jxls2.X 导出excel文件
`https://blog.csdn.net/lnktoking/article/details/52932679
`https://blog.csdn.net/lnktoking/article/details/79195500