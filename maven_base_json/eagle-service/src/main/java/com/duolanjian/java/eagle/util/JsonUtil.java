package com.duolanjian.java.eagle.util;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonUtil {

	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 将字符串转化为对象
	 * @param s 源字符串
	 * @param classType 需要获取的对象类型
	 * @param type 需要验证的数据种类
	 * @return
	 */
	public <T> T getObject(String s, Class<T> classType) {
		T t = null;
		try {
			t = objectMapper.readValue(s, classType); 
		} catch (Exception e) {
			throw new RuntimeException("parse object from string failed", e);
		}
		return t;
	}

	public Map<String, Object> getMap(String s) throws JsonParseException, JsonMappingException, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = objectMapper.readValue(s, Map.class);
		return map;
	}
	
	public JsonNode getJson(String result) {
		try {
			return objectMapper.readTree(result);
		} catch (Exception e) {
			throw new RuntimeException("get json failed, json string: " + result, e);
		}
	}
	
	public String getString(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("write object to string failed", e);
		}
	}
	
	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
}
