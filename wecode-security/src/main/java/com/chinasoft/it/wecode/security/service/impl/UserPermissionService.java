package com.chinasoft.it.wecode.security.service.impl;

import com.chinasoft.it.wecode.security.domain.UserPermission;
import com.chinasoft.it.wecode.security.dto.UserPermissionDto;
import com.chinasoft.it.wecode.security.dto.UserPermissionRewriteDto;
import com.chinasoft.it.wecode.security.repository.UserPermissionRepository;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户权限服务类
 *
 * @author Administrator
 */
@Service
public class UserPermissionService {

    @Autowired
    private UserPermissionRepository repo;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DataRangeService dataRangeService;

    /**
     * 获取用户权限列表
     *
     * @param userId
     * @return
     */
    @Validated
    public List<UserPermissionDto> findUserPermissions(@NotEmpty(message = "用户ID不能为空") String userId) {
        List<UserPermission> userPermissions = repo.findAll(Example.of(UserPermission.of(userId)));

        if(userPermissions.isEmpty()){
            return Collections.emptyList();
        }

        // 组合ID，因为下面使用的是并行流进行的填值，所以这里使用并行集合
        Set<String> roleIdSet = Sets.newConcurrentHashSet();
        Set<String> dataRangeIdSet = Sets.newConcurrentHashSet();

        userPermissions.parallelStream().forEach(entity -> {
            roleIdSet.add(entity.getRoleId());
            dataRangeIdSet.add(entity.getDataRangeId());
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
        return UserPermissionDto.of(entity.getUserId(), roleId, roleNameMap.get(roleId), dataRangeId, dataRangeNameMap.get(dataRangeId));
    }

    /**
     * 重写用户权限
     *
     * @param userId
     * @param rewrites
     */
    @Validated
    @Transactional
    public void rewriteUserPermissions(@NotEmpty String userId,List<UserPermissionRewriteDto> rewrites) {
        // 先清空用户权限数据
        repo.deleteByUserId(userId);

        // 当重写的用户权限不为空时，将 dto 转 entity 后保存
        if(!CollectionUtils.isEmpty(rewrites)) {
            repo.saveAll(rewrites.stream().map(dto -> UserPermission.of(userId, dto.getRoleId(), dto.getDataRangeId())).collect(Collectors.toList()));
        }
    }
}
