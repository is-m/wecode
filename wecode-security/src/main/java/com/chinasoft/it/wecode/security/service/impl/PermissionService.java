package com.chinasoft.it.wecode.security.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.chinasoft.it.wecode.base.BaseService;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.common.util.CollectionUtils;
import com.chinasoft.it.wecode.security.authorization.dto.OperationDto;
import com.chinasoft.it.wecode.security.authorization.dto.ResourceDto;
import com.chinasoft.it.wecode.security.authorization.service.impl.PermissionFinderService;
import com.chinasoft.it.wecode.security.domain.Permission;
import com.chinasoft.it.wecode.security.dto.PermissionDto;
import com.chinasoft.it.wecode.security.dto.PermissionResultDto;
import com.chinasoft.it.wecode.security.repository.PermissionRepository;
import com.google.common.base.Objects;

@Service
public class PermissionService extends BaseService<Permission, PermissionDto, PermissionResultDto> {

	@Autowired
	private PermissionFinderService finderService;

	private static final String TYPE_M = "module";
	private static final String TYPE_O = "operate";

	private PermissionRepository repo = (PermissionRepository) super.repo;

	public PermissionService(JpaRepository<Permission, String> repository,
			BaseMapper<Permission, PermissionDto, PermissionResultDto> mapper) {
		super(repository, mapper, Permission.class);
	}

	/**
	 * 同步权限
	 */
	public void sync() {
		// TODO:微服务时，应该是扫描微服务各模块提供的专用权限发现URL
		List<ResourceDto> resources = finderService.scan();
		resources.parallelStream().forEach(resource -> {
			// 检查资源是否存在
			String code = resource.getPermissionCode(), note = resource.getDesc();
			Permission presource = repo.findOneByCodeAndType(code, TYPE_M);
			// 资源不存在时，先创建资源节点
			if (presource == null) {
				presource = new Permission(null, code, note, TYPE_M);
				presource = repo.save(presource);

				// 新增的资源节点，直接保存所有功能
				final String resourceId = presource.getId();
				List<Permission> collect = resource.getOperations().parallelStream()
						.map(o -> new Permission(resourceId, o.getPermissionCode(), o.getDesc()))
						.collect(Collectors.toList());
				repo.save(collect);
			} else {
				// 存在时，检查是否需要更新描述
				if (!Objects.equal(presource.getNote(), note)) {
					presource.setNote(note);
					repo.save(presource);
				}

				// 获取模块功能
				List<Permission> pOperates = repo.findByPidAndType(presource.getId(), TYPE_O);
				Map<String, Permission> list2Map = CollectionUtils.list2Map(pOperates, item -> item.getCode());
				// 新增的资源节点，直接保存所有功能
				if (!CollectionUtils.isEmpty(resource.getOperations())) {
					for (OperationDto operation : resource.getOperations()) {
						String ocode = operation.getPermissionCode(), onote = operation.getDesc();
						if (list2Map.containsKey(ocode)) {
							Permission pOperate = list2Map.get(ocode);
							if (!onote.equals(pOperate.getNote())) {
								pOperate.setNote(onote);
							}
						} else {
							Permission pOperate = new Permission(presource.getId(), ocode, onote, TYPE_O);
							list2Map.put(ocode, pOperate);
						}
					}
					repo.save(list2Map.values());
				}
			}

		});

	}

	/**
	 * 清除失效的权限点
	 */
	public void clearInvalid() {
		List<ResourceDto> resources = finderService.scan();

		// -- 清理不包含在发现的模块的节点
		List<String> moduleCodeList = resources.parallelStream().map(ResourceDto::getPermissionCode)
				.collect(Collectors.toList());
		// 找到所有未和系统权限匹配的的模块
		List<Permission> waitRemoveModules = repo.findByCodeNotInAndType(moduleCodeList, TYPE_M);
		List<String> parentIdList = waitRemoveModules.parallelStream().map(Permission::getId)
				.collect(Collectors.toList());
		// 清理权限，以及权限与角色的关系
		List<Permission> waitRemoveOperates = repo.findByPidIn(parentIdList);

		List<Permission> waitRemoveList = CollectionUtils.megre(waitRemoveModules, waitRemoveOperates);
		waitRemoveList.parallelStream().forEach(item -> item.setRoles(null));

		// 清理角色关系数据，与实际权限数据
		repo.save(waitRemoveList);
		repo.delete(waitRemoveList);

		// -- 清理包含模块但是已经失效的功能
		List<Permission> waitCheckModules = repo.findByCodeInAndType(moduleCodeList, TYPE_M);
		parentIdList = waitCheckModules.parallelStream().map(Permission::getId).collect(Collectors.toList());
		List<Permission> waitCheckOperates = repo.findByPidIn(parentIdList);

		Map<String, ResourceDto> list2Map = CollectionUtils.list2Map(resources, item -> item.getPermissionCode());
		for (Permission m : waitCheckModules) {
			ResourceDto resourceDto = list2Map.get(m.getCode());
			// 获取资源的权限
			Set<String> operateCodeSet = resourceDto.getOperations().parallelStream()
					.map(item -> item.getPermissionCode()).collect(Collectors.toSet());

			List<Permission> waitRemoveOperateList = waitCheckOperates.parallelStream()
					// 筛选当前模块所有功能
					.filter(item -> Objects.equal(m.getId(), item.getPid()))
					// 筛选未匹配上的功能
					.filter(item -> !operateCodeSet.contains(item.getCode())).collect(Collectors.toList());

			waitRemoveOperateList.parallelStream().forEach(item -> item.setRoles(null));
			repo.save(waitRemoveOperateList);
			repo.delete(waitRemoveOperateList);
		}
	}

}
