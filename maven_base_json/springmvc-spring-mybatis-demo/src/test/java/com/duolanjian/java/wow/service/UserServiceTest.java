package com.duolanjian.java.wow.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.bean.User;
import com.test.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class UserServiceTest {

	@Resource
	private UserService userService;
	
	@Test
	public void test() {
		System.out.println("begin");
		User user = new User();
		user.setMobile("13011265819");
		user.setPassword("chenlisong");
		System.out.println(user);
		try{
			long id = userService.insert(user);
			System.out.println(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
