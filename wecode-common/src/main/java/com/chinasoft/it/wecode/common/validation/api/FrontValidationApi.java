package com.chinasoft.it.wecode.common.validation.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinasoft.it.wecode.common.validation.service.impl.FieldValidationBean;
import com.chinasoft.it.wecode.common.validation.service.impl.ValidationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"validation"})
@RestController
@RequestMapping("/validation/front")
public class FrontValidationApi {

  @Autowired
  private ValidationService validationService;

  @ApiOperation("获取对象属性校验集合")
  @GetMapping("/{target}")
  public List<FieldValidationBean> getFieldValidations(@PathVariable String target,
      @RequestParam(name = "group", defaultValue = "", required = false) String group) {
    return validationService.getFrontValidationMapper(target, group);
  }
}
