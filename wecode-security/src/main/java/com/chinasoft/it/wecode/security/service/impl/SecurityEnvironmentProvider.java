package com.chinasoft.it.wecode.security.service.impl;

import com.chinasoft.it.wecode.common.service.impl.EnvironmentProvider;
import com.chinasoft.it.wecode.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityEnvironmentProvider implements EnvironmentProvider {

    @Autowired
    private UserService userService;

    @Override
    public String key() {
        return "user";
    }

    @Override
    public Object value(UserPrincipal user) {
        return userService.findWorkspaceUser(user.getUid() + "");
    }

}
