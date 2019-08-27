package com.chinasoft.it.wecode.common.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.chinasoft.it.wecode.common.service.Query.Default;
import com.chinasoft.it.wecode.common.service.Query.Type;
import com.chinasoft.it.wecode.common.util.ArrayUtil;
import com.chinasoft.it.wecode.common.util.LogUtils;
import com.chinasoft.it.wecode.common.util.StringUtil;

/**
 * 查询解析器
 * 
 * @author Administrator
 *
 */
public class QueryResolver {

	private static final Logger log = LogUtils.getLogger();

	public static List<QueryItem> resolve(Object obj) {
		return resolve(obj, null, true);
	}

	public static List<QueryItem> resolve(Object obj, Class<?>[] groups) {
		return resolve(obj, groups, true);
	}

	/**
	 * 解析查询项
	 * 
	 * @param obj
	 * @param groups
	 * @param includeDefault
	 * @return
	 */
	public static List<QueryItem> resolve(Object obj, Class<?>[] groups, boolean includeDefault) {
		List<QueryItem> result = new ArrayList<>();

		// TODO:未考虑父类field情况
		if (obj != null) {
			Class<?> clz = obj.getClass();
			for (Field field : clz.getDeclaredFields()) {
				if ("serialVersionUID".equals(field.getName())) {
					continue;
				}

				QueryIgnore ignore = field.getAnnotation(QueryIgnore.class);
				if (ignore != null) {
					continue;
				}

				Query query = field.getAnnotation(Query.class);
				Object value = null;
				try {
					field.setAccessible(true);
					value = field.get(obj);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					log.error("get field value fail", e);

				}

				if (value == null) {
					continue;
				}

				if (query == null) {
					if (includeDefault) {
						result.add(new QueryItem(field.getName(), Type.EQ, value));
					}
				} else {
					String fieldName = StringUtil.getString(query.field(), field.getName());
					boolean isDefault = ArrayUtil.isEmpty(query.groups())
							|| ArrayUtil.contains(query.groups(), Default.class);
					if (isDefault) {
						if (includeDefault) {
							result.add(new QueryItem(fieldName, query.type(), value));
						}
					} else {
						result.add(new QueryItem(fieldName, query.type(), value));
					}
				}
			}
		}

		return result;
	}
}
