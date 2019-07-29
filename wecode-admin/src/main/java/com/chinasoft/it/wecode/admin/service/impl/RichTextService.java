package com.chinasoft.it.wecode.admin.service.impl;

import com.chinasoft.it.wecode.admin.domain.RichText;
import com.chinasoft.it.wecode.admin.dto.RichTextCreateDto;
import com.chinasoft.it.wecode.admin.dto.RichTextDto;
import com.chinasoft.it.wecode.admin.dto.RichTextQueryDto;
import com.chinasoft.it.wecode.admin.dto.RichTextUpdateDto;
import com.chinasoft.it.wecode.admin.mapper.RichTextMapper;
import com.chinasoft.it.wecode.admin.repository.RichTextRepository;
import com.chinasoft.it.wecode.common.service.SpringDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.groups.Default;

@Validated
@Service
public class RichTextService {

    @Autowired
    private RichTextRepository repo;

    @Autowired
    private RichTextMapper mapper;

    /**
     * 创建
     *
     * @param dto
     * @return
     */
    @Validated({Default.class})
    public RichTextDto create(@Valid RichTextCreateDto dto) {
        RichText entity = mapper.toEntity(dto);
        RichText result = repo.save(entity);
        return mapper.toDto(result);
    }

    /**
     * 分页查询
     *
     * @param pageable
     * @param condition
     * @return
     */
    public Page<RichTextDto> findByPage(Pageable pageable, RichTextQueryDto condition) {
        return SpringDataUtils.findPagedData(repo, pageable, condition).map(mapper::toDto);
    }

    /**
     * 更新
     *
     * @param id
     * @param dto
     * @return
     */
    public RichTextDto update(String id, RichTextUpdateDto dto) {
        RichText entity = mapper.toEntity(dto);
        entity.setId(id);
        RichText save = repo.save(entity);
        return mapper.toDto(save);
    }
}
