package com.duolanjian.java.market.util;


import com.yinuo.util.Constant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.yinuo.bean.User;
import com.yinuo.util.JedisUtil;
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class JedisUtilTest {
	
	@Autowired
	JedisUtil jedisUtil;

	@Test
	public void test() throws InterruptedException {
		try{
			jedisUtil.set("abc", "123", 5);
			System.out.println(jedisUtil.get("market_login_user_id_9244a423-ce0b-4648-b12e-12c0195bbe18"));

			System.out.println(JSON.parseObject(jedisUtil.get("market_login_user_id_9244a423-ce0b-4648-b12e-12c0195bbe18"), User.class));
			Thread.sleep(2000);
			System.out.println(jedisUtil.get("abc"));
			Thread.sleep(4000);
			System.out.println("value: " + jedisUtil.get("abc"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertBatch() throws InterruptedException {
		try{
			for(int i=0 ;i< 501; i++) {
				jedisUtil.set("abc" + i, "123", 60 * 10);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReadBatch() throws InterruptedException {
		try{
			System.out.println("redis: "+jedisUtil.get("abc1"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void get() {
		System.out.println("info: "+ jedisUtil.get(com.yinuo.bean.Constant.JedisNames.UserOpenid + "fc02019d-6ada-43b7-a8d6-f10c1dc403e7"));
	}

	@Test
	public void inte() {
		Integer sampleA = 1;
		Integer sampleB = 1;
		Integer sampleC = 129;
		Integer sampleD = 129;
		System.out.println(sampleA == sampleB);
		System.out.println(sampleC == sampleD);

		Long sampleE = 1L;
		Long sampleF = 1L;
		Long sampleG = 129L;
		Long sampleH = 129L;
		System.out.println(sampleE == sampleF);
		System.out.println(sampleG == sampleH);
	}
	
}