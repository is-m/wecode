package com.chinasoft.it.wecode.security.authorization.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.chinasoft.it.wecode.annotations.security.Module;
import com.chinasoft.it.wecode.annotations.security.Operate;
import com.chinasoft.it.wecode.common.util.ApplicationUtils;
import com.chinasoft.it.wecode.common.util.StringUtil;
import com.chinasoft.it.wecode.security.authorization.dto.OperationDto;
import com.chinasoft.it.wecode.security.authorization.dto.ResourceDto;

@Service
public class PermissionFinderService {

	/**
	 * 扫描项目中权限管理资源
	 * 
	 * @return
	 */
	public List<ResourceDto> scan() {
		Map<String, Object> beansWithAnnotation = ApplicationUtils.getBeansWithAnnotation(Module.class);
		List<ResourceDto> result = new ArrayList<>();
		for (Object moduleBean : beansWithAnnotation.values()) {
			Class<? extends Object> clz = AopUtils.getTargetClass(moduleBean);
			Module annModule = clz.getAnnotation(Module.class);

			ResourceDto resource = new ResourceDto(annModule.code(), annModule.desc(), clz.getName());

			
			// load operations
			Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(clz);
			
			for (Method method : allDeclaredMethods) {
				Operate annOperation = method.getAnnotation(Operate.class);
				if (annOperation == null) {
					continue;
				}

				OperationDto operation = new OperationDto();
				
				operation.setCode(StringUtil.getString(annOperation.code(), method.getName()));
				operation.setDesc(StringUtil.getString(annOperation.desc(), method.getName()));
				operation.setPolicy(annOperation.policy());

				resource.addOperation(operation);
			}
			result.add(resource);
		}
		return result;
	}
}
