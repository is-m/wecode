package com.chinasoft.it.wecode.admin.mapper;

import org.junit.Test;

import com.chinasoft.it.wecode.admin.domain.Property;
import com.chinasoft.it.wecode.admin.dto.PropertyDto;

public class PropertyMapperTests {

	private PropertyMapper mapper = PropertyMapper.INSTANCE;

	@Test
	public void test() {
		PropertyDto dto = new PropertyDto();
		dto.setName("key");
		Property property = mapper.to(dto);
		System.out.println(property.getName());
	}
}
