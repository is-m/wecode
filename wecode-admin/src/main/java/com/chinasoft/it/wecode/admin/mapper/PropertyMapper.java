package com.chinasoft.it.wecode.admin.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import com.chinasoft.it.wecode.admin.domain.Property;
import com.chinasoft.it.wecode.admin.dto.PropertyDto;
import com.chinasoft.it.wecode.admin.dto.PropertyResultDto;
import com.chinasoft.it.wecode.common.mapper.BaseMapper;
import com.chinasoft.it.wecode.common.util.CollectionUtils;
import com.chinasoft.it.wecode.common.util.LogUtils; 

@Mapper(componentModel = "spring")
public interface PropertyMapper extends BaseMapper<Property, PropertyDto, PropertyResultDto> {
	
	Logger logger = LogUtils.getLogger();

	PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

	default List<PropertyResultDto> toTreeList(List<Property> entities){
		List<PropertyResultDto> result = new ArrayList<>();

		List<PropertyResultDto> from = toDtoList(entities);
		Map<String, PropertyResultDto> list2Map = CollectionUtils.list2Map(from, PropertyResultDto :: getId);
		CollectionUtils.forEach(list2Map.keySet(), id -> {
			PropertyResultDto currDto = list2Map.get(id);
			String pid = currDto.getPid();
			// 如果是根节点则加入到结果集,否则找到父节点并加入
			if (StringUtils.isEmpty(pid) || "root".equals(pid)) {
				result.add(currDto);
			} else if (list2Map.containsKey(pid)) {
				PropertyResultDto parentDto = list2Map.get(pid);
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
}
