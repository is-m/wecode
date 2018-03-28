package com.chinasoft.it.wecode.admin.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.chinasoft.it.wecode.admin.domain.Property;
import com.chinasoft.it.wecode.admin.dto.PropertyDto;
import com.chinasoft.it.wecode.admin.dto.PropertyResultDto;
import com.chinasoft.it.wecode.admin.mapper.PropertyMapper;
import com.chinasoft.it.wecode.admin.repository.PropertyRepository;
import com.chinasoft.it.wecode.admin.util.PropertyConstant;
import com.chinasoft.it.wecode.common.exception.ValidationException;
import com.chinasoft.it.wecode.common.util.MapperUtils;
import com.chinasoft.it.wecode.common.validation.ValidationProcessor;

/**
 * 系统属性服务类
 * 
 * @author Administrator
 *
 */
@Service
public class PropertyService {

	@Autowired
	private PropertyRepository repo;

	@Autowired
	private PropertyMapper mapper;

	public PropertyResultDto save(PropertyDto dto) {
		ValidationProcessor.valid(dto);
		return mapper.from(repo.save(mapper.to(dto)));
	}

	public PropertyResultDto create(@Validated({}) PropertyDto dto) {
		ValidationProcessor.valid(dto);
		Property property = mapper.to(dto);
		if (StringUtils.isEmpty(property.getPid())) {
			property.setPid(PropertyConstant.ROOT);
		}
		// 如果非根节点，则检查根节点是否合法
		String path = this.generatePath(property);
		property.setPath(path);

		return mapper.from(repo.save(property));
	}

	private String generatePath(Property property) {
		String pid = property.getPid();
		if (PropertyConstant.ROOT.equals(pid))
			return property.getName();

		Property parentProperty = repo.findOneByPid(pid);
		Assert.notNull(parentProperty, "不存在的父节点，id=" + pid);
		return parentProperty.getPath() + "." + property.getName();
	}

	public PropertyResultDto update(String id, @Valid PropertyDto dto) {
		if (StringUtils.isEmpty(id))
			throw new ValidationException("{id:不能为空}");
		ValidationProcessor.valid(dto);

		Property property = repo.findOne(id);
		if (property == null) {
			throw new ValidationException("ID不存在," + id);
		}

		String name = dto.getName();
		// 如果名字不一样，则更新路径
		if (!StringUtils.isEmpty(name) && !property.getName().equals(name)) {
			property.setName(name);
		}

		// 旧数据的父节点和新数据的父节点不一致的话，则修改当前节点或当前节点的所有子节点路径
		String pid = dto.getPid();
		if (!StringUtils.isEmpty(pid) && !property.getPid().equals(pid)) {
			property.setPid(pid);
			String path = generatePath(property);
			// 更新旧数据
			repo.updateParentPath(property.getPath(), path);
			property.setPath(path);

		}

		return mapper.from(repo.save(property));
	}

	public Page<PropertyResultDto> findByPage(Pageable pageable, PropertyDto condition) {
		Page<Property> pageData = repo.findAll(Example.of(mapper.to(condition)), pageable);
		return MapperUtils.from(pageData, mapper);
	}

	public List<PropertyResultDto> findAll(PropertyDto condition) {
		List<Property> allData = repo.findAll(Example.of(mapper.to(condition)));
		return MapperUtils.from(allData, mapper);
	}

	/**
	 * 根据条件获取单个对象
	 * 
	 * @param condition
	 * @return
	 */
	public PropertyResultDto findOne(PropertyDto condition) {
		return mapper.from(repo.findOne(Example.of(mapper.to(condition))));
	}

	/**
	 * 根据路径获取子对象
	 * 
	 * @param parentPath
	 * @param deepSearch
	 * @return
	 */
	public List<PropertyResultDto> findChildrenByPath(String parentPath, boolean deepSearch) {
		if (StringUtils.isEmpty(parentPath)) {
			throw new ValidationException("parentPath cannot be null or empty");
		}

		Property parent = null;
		if (PropertyConstant.ROOT.equals(parentPath)) {
			parent = new Property();
			parent.setId(PropertyConstant.ROOT);
		} else if ((parent = repo.findByPath(parentPath)) == null) {
			throw new ValidationException("no found parent property of path = " + parentPath);
		}

		return MapperUtils.from(deepSearch ? repo.findByPathLike(parentPath + ".") : repo.findByPid(parent.getId()),
				mapper);
	}

}
