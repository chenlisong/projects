package com.duolanjian.java.market.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yinuo.bean.User;
import com.yinuo.service.UserService;
import com.yinuo.util.MD5Util;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class UserServiceTest {
	//在属性中使用 @Autowired 注释来除去 setter 方法
	@Autowired
	private UserService userService;
	
	@Autowired
	private MD5Util md5Util;
	
	private Log log = LogFactory.getLog(UserServiceTest.class);
	
	
	String mobile = "13011265819";
	String username = "chenlisong";
	String password = "chenlisong";
	String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	String qq = "291382932";
	int sex = 1;
	int role = 1;
	
//	@Test
	public void insert() {
		
		User user = new User();
		log.info("insert user:"+user);
		//如果存在先删除在增加
		try{
			//userService.deleteByMobile(mobile);
			long id = userService.insert(user);
			log.info("insert user id is " + id);
			log.info("insert user id2 is " + user.getId());
			Assert.assertTrue(true);//查看运行结果是否为true。
		}catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void update() {
		User user = new User();
		user.setId(1);
		user.setWechatNickname("chenlisongabc");
		userService.update(user);
	}
	
	
}
