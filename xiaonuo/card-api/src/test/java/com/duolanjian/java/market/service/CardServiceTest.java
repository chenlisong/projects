package com.duolanjian.java.market.service;

import com.yinuo.service.CardService;
import com.yinuo.util.Config;
import com.yinuo.util.MD5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class CardServiceTest {
	//在属性中使用 @Autowired 注释来除去 setter 方法
	@Autowired
	private CardService service;
	
	@Autowired
	private MD5Util md5Util;

	@Autowired
	private Config config;
	
	private Log log = LogFactory.getLog(CardServiceTest.class);

	@Test
	public void draw() {

		service.draw(1);
		System.out.println(String.format("user card: %s", service.selectByUserId(1)));

	}

	@Test
	public void countToday() {
		int userId = 1;

		System.out.println(String.format("user today times is: %d", service.countDrawToday(userId)));

		service.addDrawToday(userId);
		System.out.println(String.format("user today times is: %d", service.countDrawToday(userId)));

		service.addDrawToday(userId);
		System.out.println(String.format("user today times is: %d", service.countDrawToday(userId)));

	}

	@Test
	public void algorCard() {
		int totalCount = 10000;

		Map<Integer, Double> countMap = new HashMap<Integer, Double>();
		for(int i=0;i<totalCount;i++) {
			int card = service.algorCard();
			if(countMap.get(card) == null) {
				countMap.put(card, 1.0d);
			}else {
				countMap.put(card, countMap.get(card) + 1);
			}
		}

		for(Integer key: countMap.keySet()) {
			countMap.put(key, countMap.get(key)*config.sumHunmanJson/totalCount);
		}

		System.out.println("json: " + config.hunmanJson);
		System.out.println("countMap: " + countMap);
	}

}
