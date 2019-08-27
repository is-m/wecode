package com.chinasoft.it.wecode.common.service.impl;

import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.common.util.Assert;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.MapUtils;
import com.chinasoft.it.wecode.security.UserPrincipal;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 环境服务，用于获取当前环境
 */
@Component
public class EnvironmentService {

    @SuppressWarnings("unused")
    private static Logger log = LogUtils.getLogger();

    /**
     * 获取当前用户环境信息
     *
     * @param user
     * @return
     */
    public Map<String, Object> getEnvironment(UserPrincipal user) {
        if (user == null || StringUtils.isEmpty(user.getUid())) {
            return Maps.newLinkedHashMapWithExpectedSize(0);
        }

        Collection<EnvironmentProvider> beans = ApplicationUtils.getBeansOfType(EnvironmentProvider.class);
        Map<String, Object> result = new HashMap<>(beans.size());

        for (EnvironmentProvider bean : beans) {
            String key = Objects.requireNonNull(bean.key(), "environment key is null or empty of class " + bean.getClass());
            Assert.isTrue(!result.containsKey(key), "environment key " + key + " is conflict");
            result.put(key, bean.value(user));
        }
        return result;
    }

}
