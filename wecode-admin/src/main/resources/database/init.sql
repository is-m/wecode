DROP TABLE IF EXISTS sys_property;
CREATE TABLE sys_property (
    id VARCHAR(36) COMMENT 'ID',
    pid VARCHAR(36) COMMENT '父节点ID，根节点 root 表示',
    NAME VARCHAR(256) COMMENT '属性名称',
    VALUE VARCHAR(1000) COMMENT '属性值',
    remark TEXT(20000) COMMENT '描述',
    seq INT COMMENT '排序列',
    PATH VARCHAR(4000) COMMENT '属性路径',
    VERSION INT,
    PRIMARY KEY(id) 
);
