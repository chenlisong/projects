package com.duolanjian.java.eagle.util;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duolanjian.java.eagle.util.JedisUtil;
import com.duolanjian.java.eagle.util.MD5Util;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class JedisServiceTest {

	@Resource
	private JedisUtil jedisUtil;
	
	@Autowired
	private MD5Util md5Util;
	
	@Test
	public void get() throws InterruptedException {
		
		jedisUtil.set("foo", "345", 5);
		System.out.println(jedisUtil.get("foo"));
		Thread.sleep(5000);
		System.out.println(jedisUtil.get("foo"));
		
	}

}
