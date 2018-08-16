package com.chinasoft.it.wecode.common.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JSONUtils {

	private final static ObjectMapper objectMapper;
	
	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	private static final Logger log = LogUtils.getLogger();

	private JSONUtils() {
	}

	public static ObjectMapper getInstance() {
		return objectMapper;
	}

	/**
	 * Object to json
	 * 
	 * @param obj
	 * @return
	 */
	public static String getJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("parse obj to json error", e);
			return "error";
		}
	}

	/**
	 * json to Object
	 * 
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T getObj(String json, Class<T> clz) {
		try {
			return objectMapper.readValue(json, clz);
		} catch (Exception e) {
			log.error("parse json to object error", e);
			return null;
		}
	}

	/**
	 * json string convert to map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String json) throws Exception {
		return getObj(json, Map.class);
	}

	/**
	 * json to generic
	 * 
	 * @param json
	 * @param type
	 *            泛型类型 new TypeReference<XXX>() { }
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getGeneric(String json, TypeReference<T> type) {
		try {
			return (T) objectMapper.readValue(json, type);
		} catch (Exception e) {
			log.error("parse json to object error", e);
			return null;
		}
	}

	/**
	 * json to list
	 * 
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> json2List(String json) throws Exception {
		return getGeneric(json, new TypeReference<List<T>>() {
		});
	}

	/**
	 * Map to Object
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T map2Obj(Object map, Class<T> clazz) {
		return objectMapper.convertValue(map, clazz);
	}

	/**
	 * Object to map
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static Map<String, Object> obj2Map(Object obj) {
		return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {
		});
	}
}