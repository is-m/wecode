package com.chinasoft.it.wecode.sign.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.common.util.CollectionUtils;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.sign.domain.Catelog;
import com.chinasoft.it.wecode.sign.dto.CatelogDto;
import com.chinasoft.it.wecode.sign.dto.CatelogMenuDto;
import com.chinasoft.it.wecode.sign.dto.CatelogResultDto;

@Mapper(componentModel = "spring")
public interface CatelogMapper extends BaseMapper<Catelog, CatelogDto, CatelogResultDto> {

	Logger logger = LogUtils.getLogger();

	/**
	 * Entity 转 Result Dto
	 * 
	 * @param dto
	 * @return
	 */
	List<CatelogResultDto> from(List<Catelog> entities);

	default List<CatelogResultDto> toTreeList(List<Catelog> entities) {
		List<CatelogResultDto> result = new ArrayList<>();

		List<CatelogResultDto> from = from(entities);
		Map<String, CatelogResultDto> list2Map = CollectionUtils.list2Map(from, dto -> dto.getId());
		CollectionUtils.forEach(list2Map.keySet(), id -> {
			CatelogResultDto currDto = list2Map.get(id);
			String pid = currDto.getPid();
			// 如果是根节点则加入到结果集,否则找到父节点并加入
			if (StringUtils.isEmpty(pid) || "root".equals(pid)) {
				result.add(currDto);
			} else if (list2Map.containsKey(pid)) {
				CatelogResultDto parentDto = list2Map.get(pid);
				if (parentDto.getChildren() == null) {
					parentDto.setChildren(new ArrayList<>());
				}
				parentDto.getChildren().add(currDto);
			} else {
				logger.warn("catelog missing parent of {}", currDto);
			}
		});
		return result;
	}

	default List<CatelogMenuDto> toMenuList(List<Catelog> entities) {
		List<CatelogMenuDto> result = new ArrayList<>();

		List<CatelogResultDto> from = from(entities);
		Map<String, CatelogResultDto> list2Map = CollectionUtils.list2Map(from, dto -> dto.getId());
		Map<String, CatelogMenuDto> menus = new HashMap<>();
		CollectionUtils.forEach(from, item -> menus.put(item.getId(), new CatelogMenuDto(item)));

		CollectionUtils.forEach(list2Map.keySet(), id -> {
			CatelogResultDto currDto = list2Map.get(id);
			CatelogMenuDto curMenuDto = menus.get(id);

			String pid = currDto.getPid();
			// 如果是根节点则加入到结果集,否则找到父节点并加入
			if (StringUtils.isEmpty(pid) || "root".equals(pid)) {
				result.add(curMenuDto);
			} else if (menus.containsKey(pid)) {
				CatelogMenuDto parentDto = menus.get(pid);
				if (parentDto.getChildren() == null) {
					parentDto.setChildren(new ArrayList<>());
				}
				parentDto.getChildren().add(curMenuDto);
			} else {
				logger.warn("[menu]catelog missing parent of {}", currDto);
			}
		});
		return result;
	}
}
