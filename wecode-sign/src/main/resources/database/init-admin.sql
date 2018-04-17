-- 栏目表
-- DROP TABLE IF EXISTS s_catelog;
CREATE TABLE s_catelog( 
    `id` VARCHAR(36) COMMENT 'ID',
    `pid` VARCHAR(36) COMMENT '父节点ID，为空或者root时，表示根节点',
    `name` VARCHAR(100) COMMENT '栏目名称',
    `path` VARCHAR(256) COMMENT  '栏目短路径',
    `full_path` VARCHAR(256) COMMENT  '栏目完整路径',
    `icon` VARCHAR(50) COMMENT  '栏目图标',
    `seq` INT COMMENT  '排序列',
    `status` INT COMMENT  '状态，0：不生效，1：生效',
    `allow_type` VARCHAR(20) COMMENT '允许访问类型，all:全部可见，children:有子栏目时可见,authority:有权限时可见',
    `allow_value` VARCHAR(100) COMMENT '允许访问值，在访问类型为有权限可见时应该需要设置该值',
    `target` VARCHAR(20) COMMENT '目标类型，page:站内跳转，window：新页面,',
    PRIMARY KEY(id)
 );
 