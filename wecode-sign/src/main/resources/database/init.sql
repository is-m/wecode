DROP TABLE IF EXISTS sn_sign;
CREATE TABLE sn_sign (
    id VARCHAR(36) COMMENT 'ID',
    user_id VARCHAR(50) COMMENT 'USER ID',
    sign_date DATE COMMENT  '签到日期',
    begin_time VARCHAR(10) COMMENT '开始时间,08:30',
    begin_point VARCHAR(20) COMMENT '开始位置,GPS定位参数', 
    end_time VARCHAR(10) COMMENT  '开始时间,18:00',
    end_point VARCHAR(20) COMMENT '结束位置,GPS定位参数', 
    PRIMARY KEY(id)
);

DROP TABLE IF EXISTS sn_sign_log;
CREATE TABLE sn_sign_log (
    id VARCHAR(36) COMMENT 'ID',
    user_id VARCHAR(50) COMMENT 'USER ID',
    sign_time DATETIME COMMENT  '签到日期',
    sign_point VARCHAR(20) '签到日期,GPS定位参数',
    PRIMARY KEY(id)
);
