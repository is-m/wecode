package com.chinasoft.it.wecode.sign.service.impl;

import com.chinasoft.it.wecode.common.constant.CacheConstants;
import com.chinasoft.it.wecode.common.util.CollectionUtils;
import com.chinasoft.it.wecode.security.Role;
import com.chinasoft.it.wecode.security.service.impl.RoleService;
import com.chinasoft.it.wecode.sign.dto.CatelogMenuDto;
import com.chinasoft.it.wecode.sign.repository.RoleCatalogRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色菜单服务
 */
@Service
public class RoleCatalogService {

    @Autowired
    private CatelogService catelogService;

    @Autowired
    private RoleCatalogRepository repo;

    @Cacheable(cacheNames = {CacheConstants.CACHE_SYS}, key = "'$ADMIN_CATALOG_BY_ROLE_' + #role.id")
    public List<CatelogMenuDto> findMenusByRole(Role role) {
        if (role != null) {
            // 系统管理员不进行权限过滤
            if (role.isSysAdmin()) {
                return catelogService.findMenuList();
            }

            // 获取角色所有菜单
            List<String> catalogIdList = repo.findCatalogIdListByRoleId(role.getId());
            // 当角色有权限的菜单不为空时
            if (CollectionUtils.notEmpty(catalogIdList)) {
                // 获取所有菜单
                List<CatelogMenuDto> menus = catelogService.findMenuList();
                filterRoleMenus(menus, catalogIdList);
                return menus;
            }
        }
        return Lists.newArrayListWithCapacity(0);
    }

    private void filterRoleMenus(List<CatelogMenuDto> catalogItems, List<String> roleCatalogIdList) {
        if (CollectionUtils.notEmpty(catalogItems)) {
            // 反向遍历，用于在遍历过程中可以安全的删除项
            for (int i = catalogItems.size() - 1; i > -1; i--) {
                // 如果角色栏目中无当前节点权限，则移除当前栏目信息
                CatelogMenuDto catelogMenuDto = catalogItems.get(i);
                String menuId = catelogMenuDto.getId();
                if (!roleCatalogIdList.contains(menuId)) {
                    catalogItems.remove(i);
                    roleCatalogIdList.remove(menuId);
                } else if (CollectionUtils.notEmpty(catelogMenuDto.getChildren())) {
                    // 如果当前节点有权限则继续遍历子节点来处理权限
                    filterRoleMenus(catelogMenuDto.getChildren(), roleCatalogIdList);
                }
            }
        }
    }


}
