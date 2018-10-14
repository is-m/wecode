package com.chinasoft.it.wecode.sign.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.chinasoft.it.wecode.annotations.security.Module;
import com.chinasoft.it.wecode.annotations.security.Operate;
import com.chinasoft.it.wecode.base.BaseService;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.sign.domain.Catelog;
import com.chinasoft.it.wecode.sign.dto.CatelogDto;
import com.chinasoft.it.wecode.sign.dto.CatelogMenuDto;
import com.chinasoft.it.wecode.sign.dto.CatelogQueryDto;
import com.chinasoft.it.wecode.sign.dto.CatelogResultDto;
import com.chinasoft.it.wecode.sign.mapper.CatelogMapper;
import com.chinasoft.it.wecode.sign.repository.CatelogRepository;
import com.chinasoft.it.wecode.sign.util.CatelogConstant;
import com.chinasoft.it.wecode.sign.util.CatelogHelper;

/**
 * 栏目管理
 * 
 * @author Administrator
 *
 */
@Service
@Module
public class CatelogService extends BaseService<Catelog, CatelogDto, CatelogResultDto> {

	public CatelogService(JpaRepository<Catelog, String> repository,
			BaseMapper<Catelog, CatelogDto, CatelogResultDto> mapper) {
		super(repository, mapper, Catelog.class);
	}

	@Autowired
	private CatelogRepository repo;

	@Autowired
	private CatelogMapper mapper;

	/**
	 * 获取管理界面列表
	 * 
	 * @param queryDto
	 * @return
	 */
	@Operate
	public List<CatelogResultDto> findManageList(CatelogQueryDto queryDto) {
		String name = queryDto.getName();
		List<Catelog> result = StringUtils.isEmpty(name) ? repo.findAll() : repo.findByNameLike(name);
		return mapper.from(result);
	}

	/**
	 * 获取菜单列表
	 * 
	 * @param permissionReturn 是否仅返回需要控制权限的树节点，为true时，返回包含权限控制的节点及父节点，为false时返回所有
	 * @return
	 */
	@Operate
	public List<CatelogResultDto> findTreeList( ) {
		List<Catelog> entities = repo.findByStatusOrderBySeqAsc(CatelogConstant.STATUS_ENABLED);
		return mapper.toTreeList(entities);
	} 
	
	/**
	 * 获取菜单列表[Simple]
	 * 
	 * @return
	 */
	@Operate
	public List<CatelogMenuDto> findMenuList() {
		List<Catelog> entities = repo.findByStatusOrderBySeqAsc(CatelogConstant.STATUS_ENABLED);
		return mapper.toMenuList(entities);
	}

	/**
	 * 新增
	 * 
	 * @param dto
	 * @return
	 */
	@Operate
	public CatelogResultDto create(@Validated CatelogDto dto) {
		Catelog entity = mapper.to(dto);
		this.setFullPath(entity);
		Catelog saved = repo.save(entity);
		return mapper.from(saved);
	}

	private void setFullPath(Catelog entity) {
		String path = "/"+entity.getPath();
		if (StringUtils.isEmpty(path)) {
			return;
		}

		// 如果路径是http请求，则直接为完整路径
		if (path.startsWith("http")) {
			entity.setFullPath(path);
		} else if (CatelogHelper.isRoot(entity)) {
			entity.setFullPath(path);
		} else {
			Catelog parentCatelog = repo.getOne(entity.getPid());
			Assert.notNull(parentCatelog, "parent catelog not found of " + entity.getId());
			entity.setFullPath(parentCatelog.getFullPath() + path);
		}
	}

	/**
	 * 修改
	 * 
	 * @param id
	 * @param dto
	 * @return
	 */
	@Operate
	public CatelogResultDto update(String id, @Validated CatelogDto dto) {
		Assert.hasText(id, "id cannot be null or empty");
		Catelog entity = mapper.to(dto);
		entity.setId(id);
		this.setFullPath(entity);
		Catelog saved = repo.save(entity);
		return mapper.from(saved);
	}
}
