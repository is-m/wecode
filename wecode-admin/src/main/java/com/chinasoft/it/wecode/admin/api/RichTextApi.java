package com.chinasoft.it.wecode.admin.api;

import com.chinasoft.it.wecode.admin.dto.RichTextCreateDto;
import com.chinasoft.it.wecode.admin.dto.RichTextDto;
import com.chinasoft.it.wecode.admin.dto.RichTextQueryDto;
import com.chinasoft.it.wecode.admin.dto.RichTextUpdateDto;
import com.chinasoft.it.wecode.admin.service.impl.RichTextService;
import com.chinasoft.it.wecode.common.util.PageConstants;
import com.chinasoft.it.wecode.common.util.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "富文本API", tags = {"系统管理"})
@RestController
@RequestMapping("/services/base/text")
public class RichTextApi {

    @Autowired
    private RichTextService service;

    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping
    public RichTextDto create(@RequestBody RichTextCreateDto dto) {
        return service.create(dto);
    }

    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping("/{id}")
    public RichTextDto update(@PathVariable("id") String id, @RequestBody RichTextUpdateDto dto) {
        return service.update(id, dto);
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = PageConstants.PARAM_PAGE, value = "当前页", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = PageConstants.PARAM_SIZE, value = "页大小", paramType = "query", dataType = "Long"),})
    @GetMapping
    public Page<RichTextDto> findByPage(HttpServletRequest request, RichTextQueryDto condition) {
        return service.findByPage(WebUtils.getPageable(request), condition);
    }


}
