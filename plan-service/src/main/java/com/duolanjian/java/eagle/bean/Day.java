package com.duolanjian.java.eagle.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.duolanjian.java.eagle.exception.InvalidHttpArgumentException;
import com.duolanjian.java.eagle.validation.IsString;
import com.fasterxml.jackson.annotation.JsonFormat;


public class Day {

	private Long id;
	
	private Integer score;
	
	@IsString(minLength=1,maxLength=256)
	private String check;
	
	@IsString(minLength=1,maxLength=256)
	private String summary;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date time;
	
	@IsString(minLength=1,maxLength=16)
	private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.time = sdf.parse(time);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException("invalid params.beginTime parse error.");
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String toString() {
		try {
			return JSON.toJSONString(this);
		} catch (Exception e) {
		}
		return "json parse failed";
	}
}
