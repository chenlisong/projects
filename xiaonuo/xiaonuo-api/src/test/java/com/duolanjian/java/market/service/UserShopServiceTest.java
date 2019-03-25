package com.duolanjian.java.market.service;

import com.yinuo.bean.UserShop;
import com.yinuo.service.UserShopService;
import com.yinuo.util.MD5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class UserShopServiceTest {
	//在属性中使用 @Autowired 注释来除去 setter 方法
	@Autowired
	private UserShopService service;
	
	@Autowired
	private MD5Util md5Util;
	
	private Log log = LogFactory.getLog(UserShopServiceTest.class);

	@Test
	public void insert() {
		Random random = new Random();
		UserShop userShop = new UserShop();
		userShop.setUserId(random.nextInt(1000));
		userShop.setShopId(random.nextInt(1000));
		service.insert(null, userShop);
	}

	@Test
	public void selectByUserid() {
		List<UserShop> userShopList = service.selectByUserid(178);
		System.out.println("userShops: " + userShopList);
	}
}
