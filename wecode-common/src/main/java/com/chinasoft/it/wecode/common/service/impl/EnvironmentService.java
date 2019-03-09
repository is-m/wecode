package com.chinasoft.it.wecode.common.service.impl;

import com.chinasoft.it.wecode.common.constant.CacheConstants;
import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.common.util.Assert;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.MapUtils;
import org.slf4j.Logger;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 环境服务，用于获取当前环境
 */
@Component
public class EnvironmentService {

    private static Logger log = LogUtils.getLogger();

    private CacheManager cacheManager;

    @PostConstruct
    public void init() {
        cacheManager = ApplicationUtils.getBean(CacheManager.class);
    }

    private String getCacheKey(String itemKey, String uid) {
        return CacheConstants.CACHE_KEY_USER_ENV_PREFIX + itemKey + "_" + uid;
    }

    /**
     * 获取当前用户环境信息
     *
     * @param uid
     * @return
     */
    public Map<String, Serializable> getEnvironment(String uid) {
        if (StringUtils.isEmpty(uid)) return MapUtils.unmodifyEmptyMap();

        Collection<EnvironmentProvider> beans = ApplicationUtils.getBeansOfType(EnvironmentProvider.class);
        Map<String, Serializable> result = new HashMap<>(beans.size());
        Cache cache = cacheManager.getCache(CacheConstants.CACHE_NAME_SYS);
        for (EnvironmentProvider bean : beans) {
            String key = Objects.requireNonNull(bean.key(), "environment key is null or empty of class " + bean.getClass());
            Assert.isTrue(!result.containsKey(key), "environment key " + key + " is conflict");
            String cacheKey = getCacheKey(key, uid);
            Cache.ValueWrapper valueWrapper = cache.get(cacheKey);
            Serializable cachedValue;
            // 找到了缓存则使用缓存值
            if (valueWrapper != null) {
                cachedValue = (Serializable) valueWrapper.get();
            } else {
                // 否则通过接口获取值，并且当值不为空时进行缓存后返回
                cachedValue = bean.value(uid);
                if (cachedValue != null) {
                    log.info("get and put env cached for key {}", cacheKey);
                    cacheManager.getCache(CacheConstants.CACHE_NAME_SYS).put(cacheKey, cachedValue);
                } else {
                    log.info("get env value is null not cached for key {}", cacheKey);
                }
            }
            result.put(key, cachedValue);
        }
        return result;
    }

    /**
     * 刷新环境上下文信息
     *
     * @param uid
     * @param keys
     */
    public void refreshEnvironment(String uid, String... keys) {
        if (keys == null || keys.length == 0) {
            Collection<EnvironmentProvider> beans = ApplicationUtils.getBeansOfType(EnvironmentProvider.class);
            keys = new String[beans.size()];
            int keyIndex = 0;
            for (EnvironmentProvider bean : beans) {
                keys[keyIndex++] = bean.key();
            }
        }

        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                String cacheKey = getCacheKey(key, uid);
                log.info("remove env cache key {} ", cacheKey);
                cacheManager.getCache(CacheConstants.CACHE_NAME_SYS).evict(cacheKey);
            }
        }
    }

}
