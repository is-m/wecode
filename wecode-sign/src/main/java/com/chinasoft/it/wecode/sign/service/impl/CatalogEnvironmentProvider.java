package com.chinasoft.it.wecode.sign.service.impl;

import com.chinasoft.it.wecode.common.service.impl.EnvironmentProvider;
import com.chinasoft.it.wecode.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogEnvironmentProvider implements EnvironmentProvider {

    @Autowired
    private RoleCatalogService roleCatalogService;

    @Override
    public String key() {
        return "menus";
    }


    @Override
    public Object value(UserPrincipal user) {
        return roleCatalogService.findMenusByRole(user.getCurrentRole());
    }

}
