DROP DATABASE IF EXISTS test;
CREATE DATABASE test;

USE test;

-- 用户表
-- DROP TABLE IF EXISTS s_user
CREATE TABLE s_user
(
    `id`             VARCHAR(36) COMMENT 'ID',
    `name`           VARCHAR(100) COMMENT '用户名称',
    `password`       VARCHAR(100) COMMENT '用户密码',
    `remark`         VARCHAR(1000) COMMENT '备注',
    `mail`           VARCHAR(100) COMMENT '电子邮件',
    `mobile_phone`   VARCHAR(20) COMMENT '移动号码',
    `status`         INT COMMENT '状态，0：失效，1：生效',
    `active_role_id` VARCHAR(36) COMMENT '当前正在使用的角色ID',
    PRIMARY KEY (id)
);



-- 角色表
-- DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role
(
    id        VARCHAR(36) COMMENT 'ID',
    `CODE`    VARCHAR(100) COMMENT '角色代码',
    `NAME`    VARCHAR(255) COMMENT '显示名称',
    `REMARK`  VARCHAR(4000) COMMENT '描述',
    `URL`     VARCHAR(300) COMMENT '角色首页，如果不填，则进入系统默认首页',
    `VERSION` INT,
    `STATUS`  INT,
    PRIMARY KEY (id),
    UNIQUE (`CODE`)
);

-- 权限点
-- DROP TABLE IF EXISTS sys_permission;
CREATE TABLE sys_permission
(
    `ID`     VARCHAR(36) COMMENT 'ID',
    `PID`    VARCHAR(36) COMMENT '父节点ID',
    `CODE`   VARCHAR(500) COMMENT '权限代码',
    `NOTE`   VARCHAR(255) COMMENT '权限说明',
    `POLICY` VARCHAR(20) COMMENT '描述',
    `TYPE`   VARCHAR(20) COMMENT '权限点类型，module系统模块，operate系统功能',
    `STATUS` INT COMMENT '状态，0失效，1生效',
    PRIMARY KEY (`ID`),
    UNIQUE (`CODE`)
);


-- 角色权限
-- DROP TABLE `sys_role_permission`;
CREATE TABLE sys_role_permission
(
    `ROLE_ID`       VARCHAR(36) COMMENT '角色ID',
    `PERMISSION_ID` VARCHAR(36) COMMENT '权限ID'
);


-- 栏目表
-- DROP TABLE IF EXISTS s_catelog;
CREATE TABLE s_catelog
(
    `id`          VARCHAR(36) COMMENT 'ID',
    `pid`         VARCHAR(36) COMMENT '父节点ID，为空或者root时，表示根节点',
    `name`        VARCHAR(100) COMMENT '栏目名称',
    `uri`         VARCHAR(256) COMMENT '链接地址',
    `icon`        VARCHAR(50) COMMENT '栏目图标',
    `seq`         INT COMMENT '排序列',
    `status`      INT COMMENT '状态，0：不生效，1：生效',
    `allow_type`  VARCHAR(20) COMMENT '允许访问类型，all:全部可见，children:有子栏目时可见,authority:有权限时可见',
    `allow_value` VARCHAR(100) COMMENT '允许访问值，在访问类型为有权限可见时应该需要设置该值',
    `target`      VARCHAR(20) COMMENT '目标类型，page:站内跳转，window：新页面,',
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS sys_property;
CREATE TABLE sys_property
(
    id         VARCHAR(36) COMMENT 'ID',
    pid        VARCHAR(36) COMMENT '父节点ID，根节点 root 表示',
    `NAME`     VARCHAR(256) COMMENT '属性名称',
    `VALUE`    VARCHAR(1000) COMMENT '属性值',
    VALUE_TYPE VARCHAR(20) COMMENT '值类型，simple:普通值,blend:混合值，item:字典项',
    remark     TEXT(20000) COMMENT '描述',
    seq        INT COMMENT '排序列',
    PATH       VARCHAR(4000) COMMENT '属性路径',
    `VERSION`  INT,
    `STATUS`   INT,
    PRIMARY KEY (id)
);

-- 角色权限表
DROP TABLE IF EXISTS sys_user_permission;
CREATE TABLE sys_user_permission
(
    `id`            VARCHAR(36) PRIMARY KEY COMMENT 'id',
    `user_id`       VARCHAR(36) NOT NULL COMMENT '用户ID，关联 sys_user.id',
    `role_id`       VARCHAR(36) NOT NULL COMMENT '角色ID，关联 sys_role.id',
    `data_range_id` VARCHAR(36) COMMENT '数据范围ID，关联sys_data_range.id',
    `expire_date`   TIMESTAMP COMMENT '过期时间',
    `status`        INT COMMENT '状态，0 无效，1 有效'
   /* ,UNIQUE(`user_id`,`role_id`)*/
) COMMENT '用户权限表';


DROP TABLE IF EXISTS wc_rich_text;
CREATE TABLE wc_rich_text (
    `id` VARCHAR(36) PRIMARY KEY COMMENT 'id',
    `type` VARCHAR(10) COMMENT '类型',
    `name` VARCHAR(10) COMMENT '配置名称',
    `title` VARCHAR(10) COMMENT '标题',
    `content` TEXT COMMENT '内容'
)





-- 数据清理
DELIMITER $$
DROP PROCEDURE IF EXISTS `sys_data_remove`$$
CREATE PROCEDURE `sys_data_remove`()
BEGIN


end $$
DELIMITER ;



# 这个是定义一个分隔符，因为一般是;结束，则会执行，而在存储过程中我们需要的是命令输入完毕执行
DELIMITER $$
DROP PROCEDURE IF EXISTS `sys_data_init`$$
CREATE PROCEDURE `sys_data_init`()
BEGIN
    -- https://www.cnblogs.com/Brambling/p/9259375.html 变量定义
    # 定义变量
    DECLARE v_role_admin_id VARCHAR(36);

    -- 如果出现异常，会自动处理并rollback
    DECLARE EXIT HANDLER FOR  SQLEXCEPTION ROLLBACK ;
    START TRANSACTION;
    -- 数据检查，
    -- 数据初始化

    -- 基础角色初始化
    set v_role_admin_id = uuid();
    insert into sys_role(id, CODE, NAME, REMARK, URL, VERSION, STATUS)
    values (v_role_admin_id,'Administrator','Administrator','超级管理员/系统内置',null,null,'1')
          ,(uuid(),'Guest','Guest','访客/系统内置','/welcome',null,'1');

    -- 基础用户初始化
    -- 注意，admin用户登陆时如果使用系统提供的默认密码，则会一直提示修改密码
    insert into s_user(id, name, password, remark, mail, mobile_phone, status, active_role_id)
    values (uuid(),'admin','admin','系统内置',null,null,'1',v_role_admin_id);


    -- 栏目初始化

    COMMIT;
END$$
DELIMITER ;

-- 执行初始化
call sys_data_init();