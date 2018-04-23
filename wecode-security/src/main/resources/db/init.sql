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