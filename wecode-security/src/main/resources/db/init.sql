 -- 用户表
 -- DROP TABLE IF EXISTS s_user
 CREATE TABLE s_user( 
    `id` VARCHAR(36) COMMENT 'ID', 
    `name` VARCHAR(100) COMMENT '用户名称',
    `password` VARCHAR(100) COMMENT '用户密码',
    `remark` VARCHAR(1000) COMMENT '备注',
    `mail` VARCHAR(100) COMMENT '电子邮件',
    `mobile_phone` VARCHAR(20) COMMENT '移动号码', 
    `status` INT COMMENT '状态，0：失效，1：生效', 
    PRIMARY KEY(id)
);
 
 
 
-- 角色表
-- DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
    id VARCHAR(36) COMMENT 'ID', 
    `CODE` VARCHAR(100) COMMENT '角色代码',
    `NAME` VARCHAR(255) COMMENT '显示名称', 
    `REMARK` VARCHAR(4000) COMMENT '描述', 
    `URL` VARCHAR(300) COMMENT '角色首页，如果不填，则进入系统默认首页',
    `VERSION` INT,
    `STATUS` INT,
    PRIMARY KEY(id),
    UNIQUE (`CODE`)
);
