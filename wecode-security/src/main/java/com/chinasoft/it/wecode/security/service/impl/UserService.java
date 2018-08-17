package com.chinasoft.it.wecode.security.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chinasoft.it.wecode.annotations.security.Module;
import com.chinasoft.it.wecode.annotations.security.Operate;
import com.chinasoft.it.wecode.base.BaseService;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.excel.service.IExcelExportService;
import com.chinasoft.it.wecode.excel.service.IExcelService;
import com.chinasoft.it.wecode.security.domain.User;
import com.chinasoft.it.wecode.security.dto.UserDto;
import com.chinasoft.it.wecode.security.dto.UserExportDto;
import com.chinasoft.it.wecode.security.dto.UserQueryDto;
import com.chinasoft.it.wecode.security.dto.UserResultDto;

//https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl
@Service
@Module(code = "user", desc = "user")
public class UserService extends BaseService<User, UserDto, UserResultDto> {

	@Autowired
	private IExcelService excelService;

	public UserService(JpaRepository<User, String> repository, BaseMapper<User, UserDto, UserResultDto> mapper) {
		super(repository, mapper, User.class);
	}

	public List<UserResultDto> findAll(List<String> ids) {
		return mapper.toDtoList(repo.findAll(ids));
	}

	public Page<UserResultDto> findPagedData(Pageable pageable, UserQueryDto queryDto) {
		// Specifications.where(new Specification<User>(){xxx}).and(new
		// Specification<User>(){xxx})
		@SuppressWarnings("unchecked")
		Page<User> pageData = ((JpaSpecificationExecutor<User>) repo).findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				String name = queryDto.getName();
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(name)) {
					predicates.add(cb.like(root.get("name"), "%" + name + "%"));
				}
				Integer status = queryDto.getStatus();
				if (status != null) {
					predicates.add(cb.equal(root.get("status"), status));
				}
				return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
			}
		}, pageable);
		return mapper.toResultDto(pageData);
	}

	/**
	 * TODO:导出时，可能还需要排序字段
	 * 
	 * @param exportDto
	 * @param output
	 */
	@Operate(code="export",desc="export")
	public void export(UserExportDto exportDto, OutputStream output) throws Exception {
		logger.info("call UserService.export {}", exportDto);
		// 找到excel.security.user的bean
		excelService.export("security.user", exportDto, output);
	}

	/**
	 * 导入
	 * 
	 * @param excelFile
	 * @param ruleConfigFilePath
	 * @throws Exception
	 */
	//@Operate(code="import",desc="import")
	public void imports(InputStream datafile) throws Exception {
		excelService.imports("security.user", datafile);
	}

}
