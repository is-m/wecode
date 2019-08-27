package com.chinasoft.it.wecode.security.service.impl;

import com.chinasoft.it.wecode.common.dto.BatchDto;
import com.chinasoft.it.wecode.common.util.StringUtil;
import com.chinasoft.it.wecode.exception.AuthenticationException;
import com.chinasoft.it.wecode.exception.PasswordInvalidException;
import com.chinasoft.it.wecode.security.UserPrincipal;
import com.chinasoft.it.wecode.security.domain.User;
import com.chinasoft.it.wecode.security.domain.UserPermission;
import com.chinasoft.it.wecode.security.dto.RoleResultDto;
import com.chinasoft.it.wecode.security.dto.RoleVO;
import com.chinasoft.it.wecode.security.dto.UserPermissionDto;
import com.chinasoft.it.wecode.security.dto.UserVO;
import com.chinasoft.it.wecode.security.repository.RolePermissionRepository;
import com.chinasoft.it.wecode.security.repository.UserPermissionRepository;
import com.chinasoft.it.wecode.security.spi.UserDetailService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户权限服务类
 *
 * @author Administrator
 */
@Service
public class UserPermissionService implements UserDetailService {

    @Autowired
    private UserPermissionRepository repo;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DataRangeService dataRangeService;

    @Autowired
    private UserService userService;

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 获取用户权限列表
     *
     * @param userId
     * @return
     */
    @Validated
    public List<UserPermissionDto> findUserPermissions(@NotEmpty(message = "用户ID不能为空") String userId) {
        List<UserPermission> userPermissions = repo.findAll(Example.of(UserPermission.ofUserId(userId)));

        if (userPermissions.isEmpty()) {
            return Collections.emptyList();
        }

        // 组合ID，因为下面使用的是并行流进行的填值，所以这里使用并行集合
        Set<String> roleIdSet = Sets.newConcurrentHashSet();
        Set<String> dataRangeIdSet = Sets.newConcurrentHashSet();

        userPermissions.parallelStream().forEach(entity -> {
            if (!StringUtils.isEmpty(entity.getRoleId())) {
                roleIdSet.add(entity.getRoleId());
            }
            if (!StringUtils.isEmpty(entity.getDataRangeId())) {
                dataRangeIdSet.add(entity.getDataRangeId());
            }
        });

        // 获取ID名称映射关系
        Map<String, String> roleNameMap = roleService.getRoleNameMap(roleIdSet);
        Map<String, String> dataRangeNameMap = dataRangeService.getDataRangeNameMap(dataRangeIdSet);

        // 将 entity 转换 dto 后返回
        return userPermissions.stream().map(entity -> this.toDto(entity, roleNameMap, dataRangeNameMap)).collect(Collectors.toList());
    }


    // UserPermission 转 UserPermissionDto
    private UserPermissionDto toDto(UserPermission entity, Map<String, String> roleNameMap, Map<String, String> dataRangeNameMap) {
        String roleId = entity.getRoleId();
        String dataRangeId = entity.getDataRangeId();
        return UserPermissionDto.of(entity.getId(), entity.getUserId(), roleId, roleNameMap.get(roleId), dataRangeId, dataRangeNameMap.get(dataRangeId));
    }

    /**
     * 重写用户权限
     *
     * @param userId
     * @param batchDto
     */
    @Validated
    @Transactional
    public void batchUserPermissions(@NotEmpty String userId, @NotNull BatchDto<UserPermissionDto> batchDto) {
        if (batchDto != null) {
            if (!batchDto.isEmptyDels()) { // 删除数据
                repo.deleteByIdIn(batchDto.getDels());
            }

            if (!batchDto.isEmptyAdds()) { // 添加数据
                repo.saveAll(batchDto.getAdds().stream().map(dto -> UserPermission.of(userId, dto.getRoleId(), dto.getDataRangeId())).collect(Collectors.toList()));
            }

            if (!batchDto.isEmptyUpds()) { // 修改数据
                repo.saveAll(batchDto.getUpds().stream().map(dto -> UserPermission.of(dto.getId(), userId, dto.getRoleId(), dto.getDataRangeId())).collect(Collectors.toList()));
            }
        }
    }

    /**
     * TODO:考虑是否需要根据邮箱/电话来登录
     */
    @Override
    public UserPrincipal userDetails(String identifier, String password) throws AuthenticationException {
        User user;
        try {
            user = userService.findOneByNameOrMailOrMobilePhone(identifier);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new AuthenticationException("用户标识 [" + identifier + "] 存在冲突，请联系管理员");
        }

        if (user == null) {
            throw new AuthenticationException("不存在的用户");
        }

        if (!StringUtil.isEmpty(password)) {
            if (!password.equals(user.getPassword())) {
                throw new PasswordInvalidException();
            }
        }

        if (!Objects.equals(1, user.getStatus())) {
            throw new AuthenticationException("禁止使用的用户，状态码：" + user.getStatus());
        }

        UserVO userVO = new UserVO();
        userVO.setUid(user.getId());
        return userVO;
    }


    @Override
    public UserPrincipal findByUid(String uid) throws AuthenticationException {
        User user = userService.findOneByNameOrMailOrMobilePhone(uid);
        if (user == null) {
            throw new AuthenticationException("用户不存在， id is " + uid);
        }

        UserVO userVO = new UserVO();
        userVO.setUid(user.getId());
        // 获取当前用户角色
        String activeRoleId = user.getActiveRoleId();
        // 当用户当前活动的角色为空时，用户可用角色中的第一条，并将角色更新到用户活动角色中
        RoleResultDto role = null;
        if (StringUtils.isEmpty(activeRoleId)) {
            Optional<UserPermission> firstUserPermission = repo.findFirstByUserId(user.getId());
            if (firstUserPermission.isPresent()) {
                role = roleService.findOne(firstUserPermission.get().getRoleId());
            } else {
                role = roleService.getGuestRole();
            }
            // 设置默认角色
        } else {
            role = roleService.getGuestRole();
        }

        RoleVO roleVO = new RoleVO();
        roleVO.setId(role.getId());
        roleVO.setCode(role.getCode());
        roleVO.setPermissionCodeSet(rolePermissionService.findPermissionCodeSetByRoleId(role.getId()));
        userVO.setCurrentRole(roleVO);
        return userVO;
    }
}
