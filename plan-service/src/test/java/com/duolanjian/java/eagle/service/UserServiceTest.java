package com.duolanjian.java.eagle.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duolanjian.java.eagle.bean.User;
import com.duolanjian.java.eagle.service.UserService;
import com.duolanjian.java.eagle.util.MD5Util;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class UserServiceTest {

	@Resource
	private UserService userService;
	
	@Autowired
	private MD5Util md5Util;
	
	private Log log = LogFactory.getLog(UserServiceTest.class);
	
//	@Test
	public void test() {
		String mobile = "13011265819";
		String username = "chenlisong";
		String password = "chenlisong";
		
		User user = new User();
		user.setUserName(username);
		user.setMobile(mobile);
		user.setPassword(md5Util.string2MD5(password));
		log.info("insert user:"+user);
		
		try{
			userService.deleteByMobile(mobile);
			long id = userService.insert(user);
			log.info("insert user id is " + id);
			log.info("insert user id2 is " + user.getId());
			Assert.assertTrue(true);
		}catch (Exception e) {
			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}
	
	@Test
	public void print() {
		System.out.println("print right.");
	}
	

}
