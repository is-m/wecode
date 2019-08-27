package com.chinasoft.it.wecode.security.service.impl;

import com.chinasoft.it.wecode.common.util.ArrayUtil;
import com.chinasoft.it.wecode.common.util.Assert;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.StringUtil;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

/**
 * 用户角色服务
 *
 * @author Administrator
 */
@Service
@Validated
public class UserRoleService {

    private static final Logger log = LogUtils.getLogger();

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;


    @Validated
    public List<RoleResultDto> findUserRoles(@NotBlank String userId) {
        Assert.isTrue(userService.isExists(userId), "user not found by user id {0}", userId);

        // 获取用户所有角色
        Query query = em.createNativeQuery("select role_id from sys_user_role where user_id = :userId");
        query.setParameter("userId", userId);

        @SuppressWarnings("unchecked")
        List<String> roleIds = (List<String>) query.getResultList();

        return roleService.findAll(roleIds);
    }

    // https://blog.csdn.net/hanghangde/article/details/53241150
    @Validated
    @Transactional
    public void addUserRoles(@NotBlank String userId, @NotEmpty String[] roleIdArray) {
        Assert.isTrue(userService.isExists(userId), "user not found by user id '{0}'", userId);

        // 检查角色是否与用户已经绑定
        int totalSuccess = 0;
        for (String roleId : roleIdArray) {
            if (!StringUtil.isEmpty(roleId)) {
                boolean roleExists = roleService.isExists(roleId);
                if (roleExists) {
                    Query query = em.createNativeQuery(
                            "insert into sys_user_role(id,user_id,role_id) select :id,:userId,:roleId from dual where not exists (select 1 from sys_user_role where user_id = :userId and role_id = :roleId)");
                    query.setParameter("id", UUID.randomUUID().toString().replaceAll("-", ""));
                    query.setParameter("userId", userId);
                    query.setParameter("roleId", roleId);
                    try {
                        if (query.executeUpdate() < 1) {
                            log.warn("user '{}' role '{}' is existed.", userId, roleId);
                        } else {
                            totalSuccess++;
                        }
                    } catch (DataIntegrityViolationException e) {
                        // 唯一约束冲突说明数据已经存在
                        log.warn("user '{}' role '{}' is existed.", userId, roleId);
                    }
                } else {
                    log.warn("role '{}' is not existed", roleId);
                }

            }
        }

        log.info("add user role total {} success {}", ArrayUtil.length(roleIdArray), totalSuccess);
    }

}
