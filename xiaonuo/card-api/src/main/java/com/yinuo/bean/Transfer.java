package com.yinuo.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yinuo.exception.InvalidHttpArgumentException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Transfer {

	private Integer id;

	private Integer srcId;

	private Integer targetId;

	/** @see com.yinuo.bean.Constant.TransferType*/
	private Integer type;

	private Integer card;

	/** @see com.yinuo.bean.Constant.TransferState*/
	private Integer state;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date fixTime;

	private String formIdOne;

	private String formIdTwo;

	public Transfer() {}

	public Transfer(Integer srcId, Integer type, Integer card, Integer state, String formIdOne) {
		this.srcId = srcId;
		this.type = type;
		this.card = card;
		this.state = state;
		this.formIdOne = formIdOne;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSrcId() {
		return srcId;
	}

	public void setSrcId(Integer srcId) {
		this.srcId = srcId;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCard() {
		return card;
	}

	public void setCard(Integer card) {
		this.card = card;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.createTime = sdf.parse(createTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException("invalid params.createTime parse error.");
		}
	}

	public String getFormIdOne() {
		return formIdOne;
	}

	public void setFormIdOne(String formIdOne) {
		this.formIdOne = formIdOne;
	}

	public String getFormIdTwo() {
		return formIdTwo;
	}

	public void setFormIdTwo(String formIdTwo) {
		this.formIdTwo = formIdTwo;
	}

	public Date getFixTime() {
		return fixTime;
	}

	public void setFixTime(String fixTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.fixTime = sdf.parse(fixTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException("invalid params.createTime parse error.");
		}
	}

	public String toString() {
		try {
			return JSON.toJSONString(this);
		} catch (Exception e) {
		}
		return "json parse failed";
	}
}
