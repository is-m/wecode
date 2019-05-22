package com.chinasoft.it.wecode.security.service.impl;

import com.chinasoft.it.wecode.common.exception.DataNotFoundException;
import com.chinasoft.it.wecode.security.repository.DataRangeRepository;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataRangeService {

    @Autowired
    private DataRangeRepository repo;

    /**
     * 获取数据范围ID与名称映射关系（集合）
     *
     * @param idList
     * @return
     * @throws DataNotFoundException
     */
    @Validated
    public Map<String, String> getDataRangeNameMap(@NotNull Collection<String> idList) throws DataNotFoundException {
        return repo.findAllById(idList).parallelStream().collect(Collectors.toMap(entity -> entity.getId(), entity -> entity.getName()));
    }

}
