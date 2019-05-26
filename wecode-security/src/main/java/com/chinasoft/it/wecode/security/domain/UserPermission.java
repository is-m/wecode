package com.chinasoft.it.wecode.security.domain;

import com.chinasoft.it.wecode.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "sys_user_permission")
public class UserPermission extends BaseEntity {

    /**
     * 用戶ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 数据范围ID
     */
    private String dataRangeId;

    /**
     * 数据有效性
     */
    private String status;

    /**
     * 有效期(保留字段)
     */
    private Date expireDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDataRangeId() {
        return dataRangeId;
    }

    public void setDataRangeId(String dataRangeId) {
        this.dataRangeId = dataRangeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public static UserPermission of(String id) {
        UserPermission result = new UserPermission();
        result.setId(id);
        return result;
    }

    public static UserPermission ofUserId(String userId) {
        UserPermission result = new UserPermission();
        result.setUserId(userId);
        return result;
    }

    public static UserPermission of(String userId,String roleId,String dataRangeId){
        return of(null,userId,roleId,dataRangeId);
    }

    public static UserPermission of(String id,String userId,String roleId,String dataRangeId){
        UserPermission result = of(id);
        result.setUserId(userId);
        result.setRoleId(roleId);
        result.setDataRangeId(dataRangeId);
        return result;
    }
}
