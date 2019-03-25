package com.duolanjian.java.eagle.bean;

import com.alibaba.fastjson.JSON;
import com.duolanjian.java.eagle.validation.IsString;


public class Student {

	private Long id;
	
	@IsString(minLength=11,maxLength=11)
	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		try {
			return JSON.toJSONString(this);
		} catch (Exception e) {
		}
		return "json parse failed";
	}
}
