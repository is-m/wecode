
DELIMITER $$
DROP PROCEDURE IF EXISTS `refresh_device_city`$$
CREATE PROCEDURE refresh_device_city(IN device_id BIGINT,IN table_num INT)
BEGIN
    #定义变量
    DECLARE locationTableName VARCHAR(255);
    DECLARE delete_sql VARCHAR(200);
    DECLARE insert_sql VARCHAR(2000);
    DECLARE err INT DEFAULT 0;
-- 如果出现异常，会自动处理并rollback
    DECLARE EXIT HANDLER FOR  SQLEXCEPTION ROLLBACK ;
    START TRANSACTION;
    SET locationTableName= CONCAT('lbs_device_location_', table_num);
    SET @delete_sql = CONCAT('DELETE FROM lbs_device_city WHERE device_id=',device_id);
    SET @insert_sql = CONCAT('INSERT INTO LBS_DEVICE_CITY (device_id,city,into_city_time,create_time)
                             SELECT ',device_id,',a.city city,a.ltime ltime,NOW() FROM( SELECT MIN(a.city) city,MIN(a.ltime) ltime FROM (
                     SELECT CASE WHEN a.city!=@city THEN @i:=@i+1 ELSE @i END i,
                 CASE WHEN a.city!=@city THEN @city:=a.city ELSE @city END tcity,
                 a.city city,a.ltime ltime
                     FROM ( SELECT a.CITY city,a.LOCATION_TIME ltime FROM ',locationTableName,' a
                 WHERE a.DEVICE_ID = ',device_id,' AND a.city !=\'\' AND a.CITY != \'未知\'
                 ORDER BY a.LOCATION_TIME ASC) a ,(SELECT @i:=-1,@city:=\'未知\') b
                         )a WHERE a.i != -1 GROUP BY a.i ) a ORDER BY a.ltime ASC');
#执行SQL语句
    PREPARE delstmt FROM @delete_sql;
    EXECUTE delstmt;
    DEALLOCATE PREPARE delstmt;
    PREPARE insstmt FROM @insert_sql;
    EXECUTE insstmt;
    DEALLOCATE PREPARE insstmt;
    COMMIT;
END$$
DELIMITER ;
