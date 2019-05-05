DROP DATABASE IF EXISTS test;
CREATE DATABASE test;

USE test;

-- 用户表
-- DROP TABLE IF EXISTS s_user
CREATE TABLE s_user
(
    `id`           VARCHAR(36) COMMENT 'ID',
    `name`         VARCHAR(100) COMMENT '用户名称',
    `password`     VARCHAR(100) COMMENT '用户密码',
    `remark`       VARCHAR(1000) COMMENT '备注',
    `mail`         VARCHAR(100) COMMENT '电子邮件',
    `mobile_phone` VARCHAR(20) COMMENT '移动号码',
    `status`       INT COMMENT '状态，0：失效，1：生效',
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



