

##代码编写注意事项

数据库关键字尽量不要使用，否则会导致SQL语句执行失败的情况
`You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'desc, pid, type, id) values ('SYS::Test::test01CreatePermission', '测试创建' at line 1`
实在要用关键字，mysql建议在列上标注
@Column(name = "`name`")

数据库关键列表

desc,code,name,