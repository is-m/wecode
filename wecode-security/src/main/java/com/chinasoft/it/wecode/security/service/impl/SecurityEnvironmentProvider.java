package com.chinasoft.it.wecode.security.service.impl;

import com.chinasoft.it.wecode.common.service.impl.EnvironmentProvider;
import com.chinasoft.it.wecode.security.dto.UserResultDto;
import com.chinasoft.it.wecode.security.dto.WorkspaceUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;

@Service
public class SecurityEnvironmentProvider implements EnvironmentProvider {

    @Autowired
    private UserService userService;

    @Override
    public String key() {
        return "user";
    }

    @Override
    public Serializable value(String uid) {
        // 根据ID查询用户信息
        HashMap<String, Object> result = new HashMap<>();

        // 查询设置用户信息
        WorkspaceUserDto user = userService.findWorkspaceUser(uid);
        result.put("user", user);

        // 查询设置用户当前角色

        // 查询设置用户当前权限

        // 查询设置用户所有生效的角色(只需要角色名称和角色ID)


        return result;
    }

}
