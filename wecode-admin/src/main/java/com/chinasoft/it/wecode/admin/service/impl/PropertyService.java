package com.chinasoft.it.wecode.admin.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.chinasoft.it.wecode.admin.domain.Property;
import com.chinasoft.it.wecode.admin.dto.PropertyDto;
import com.chinasoft.it.wecode.admin.dto.PropertyResultDto;
import com.chinasoft.it.wecode.admin.mapper.PropertyMapper;
import com.chinasoft.it.wecode.admin.repository.PropertyRepository;
import com.chinasoft.it.wecode.admin.util.PropertyConstant;
import com.chinasoft.it.wecode.base.BaseService;
import com.chinasoft.it.wecode.common.exception.ValidationException;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.common.util.MapperUtils;
import com.chinasoft.it.wecode.common.validation.ValidationProcessor;

/**
 * 系统属性服务类
 * 
 * @author Administrator
 *
 */
@Service
public class PropertyService extends BaseService<Property, PropertyDto, PropertyResultDto> {

	public PropertyService(JpaRepository<Property, String> repository,
			BaseMapper<Property, PropertyDto, PropertyResultDto> mapper) {
		super(repository, mapper, Property.class);
	}

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
		if (StringUtils.isEmpty(property.getValueType())) {
			property.setValueType(PropertyConstant.VALUE_TYPE_simple);
		}

		// 检查并修改父节点类型
		if (PropertyConstant.VALUE_TYPE_item.equals(property.getValueType())) {
			Property parentProperty = repo.findOne(dto.getPid());
			if (!PropertyConstant.VALUE_TYPE_blend.equals(parentProperty.getValueType())) {
				parentProperty.setValueType(PropertyConstant.VALUE_TYPE_blend);
				repo.save(parentProperty);
			}
		}

		return mapper.from(repo.save(property));
	}

	private String generatePath(Property property) {
		String pid = property.getPid();
		if (PropertyConstant.ROOT.equals(pid))
			return property.getName();

		Property parentProperty = repo.findOne(pid);
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
		} else {
			property.setPath(name);
		}
		property.setValue(dto.getValue());
		property.setRemark(dto.getRemark());
		property.setSeq(dto.getSeq());
		property.setStatus(dto.getStatus());

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
	 *            是否深查找，如果为true则表示遍历往下查找
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

	/**
	 * 查找树型数据
	 * 
	 * @return
	 */
	public List<PropertyResultDto> findPropertyTree() {
		List<Property> entities = repo.findByValueTypeIn(
				new String[] { PropertyConstant.VALUE_TYPE_simple, PropertyConstant.VALUE_TYPE_blend },
				new Sort("seq"));
		return mapper.toTreeList(entities);
	}

	@Transactional
	public void delete(String... ids) {
		// TODO:根据ID查找所有子节点
		Map<Property, Set<Property>> container = new HashMap<>();
		for (String pid : ids) {
			Property parent = new Property();
			parent.setId(pid);
			deepSearchChildren(parent, container);
		}

		Set<Property> delSet = new HashSet<>(container.keySet());
		container.values().forEach(set -> delSet.addAll(set));
		repo.delete(delSet);
	}

	/**
	 * 深查找子项
	 * 
	 * @param parent
	 *            父节点
	 * @param container
	 *            存放数据的容器
	 */
	private void deepSearchChildren(Property parent, Map<Property, Set<Property>> container) {
		if (parent == null)
			return;
		Set<Property> children = new HashSet<>(repo.findByPid(parent.getId()));
		container.put(parent, children);
		for (Property child : children) {
			deepSearchChildren(child, container);
		}
	}

	/**
	 * 获取数据字典项
	 * 
	 * @param pid
	 * @return
	 */
	public List<PropertyResultDto> findPropertyItem(String pid) {
		Assert.hasText(pid, "parent id cannot be null or empty");
		Property condition = new Property();
		condition.setPid(pid);
		condition.setValueType(PropertyConstant.VALUE_TYPE_item);
		List<Property> entityResult = repo.findAll(Example.of(condition));
		return mapper.toDtoList(entityResult);
	}

}
