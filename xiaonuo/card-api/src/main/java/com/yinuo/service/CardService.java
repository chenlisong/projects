package com.yinuo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yinuo.bean.Card;
import com.yinuo.bean.Constant;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.mapper.CardMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.Config;
import com.yinuo.util.DateTool;
import com.yinuo.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Transactional(rollbackFor = Exception.class)
@Service
public class CardService {
	
	@Autowired
	private CardMapper mapper;

	@Autowired
	private Config config;

	@Autowired
	private JedisUtil jedisUtil;

	//抽奖
	public int draw(int userId) {

		//今天如果抽过就算了
		int todayTimes = countDrawToday(userId);
		if(todayTimes >= config.drawTimes) {
			throw new InvalidArgumentException(String.format("每天最多抽奖%d次，明天重置", config.drawTimes));
		}

		int card = algorCard();
		int updateTime = mapper.updateNumber(userId, card, 1);
		if(updateTime <= 0) {
			mapper.insert(new Card(userId, card, 1));
		}
		addDrawToday(userId);
		return card;
	}

	public void insert(int userId, int card) {
		int updateTime = mapper.updateNumber(userId, card, 1);
		if(updateTime <= 0) {
			mapper.insert(new Card(userId, card, 1));
		}
	}

	public int updateNumber(int userId, int card, int number) {
		return mapper.updateNumber(userId, card, number);
	}


	public List<Card> selectByUserId(int userId) {
		return mapper.selectByUserId(userId);
	}

	public int selectByUserCardId(int userId, int card) {
		return mapper.selectByUserCardId(userId, card);
	}

	public int algorCard() {
		int validate = CommonUtil.random.nextInt(config.sumHunmanJson);

		int sum = 0;
		for(int i=0; i<config.hunmanArray.length; i++) {
			if(validate >= sum && validate < sum+config.hunmanArray[i]) {
				return i+1;
			}
			sum += config.hunmanArray[i];
		}
		return -1;
	}

	public void addDrawToday(int userId) {
		String ymd = DateTool.standardYmd.format(new Date());

		String key = Constant.JedisNames.Project + Constant.JedisNames.Split + userId + Constant.JedisNames.Split + ymd;

		Integer value = jedisUtil.get(key, Integer.class);

		if(value == null) {
			jedisUtil.set(key, "1", Constant.JedisNames.OneDay);
		}else {
			jedisUtil.set(key, value.intValue() + 1 + "", Constant.JedisNames.OneDay);
		}
	}

	public int countDrawToday(int userId) {

		String ymd = DateTool.standardYmd.format(new Date());

		String key = Constant.JedisNames.Project + Constant.JedisNames.Split + userId + Constant.JedisNames.Split + ymd;

		Integer value = jedisUtil.get(key, Integer.class);

		if(value == null) {
			return 0;
		}
		return value.intValue();
	}

}
