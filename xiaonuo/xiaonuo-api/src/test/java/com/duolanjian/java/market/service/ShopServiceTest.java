package com.duolanjian.java.market.service;

import com.yinuo.bean.Shop;
import com.yinuo.bean.UserShop;
import com.yinuo.mapper.ShopMapper;
import com.yinuo.service.ShopService;
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
public class ShopServiceTest {
	//在属性中使用 @Autowired 注释来除去 setter 方法
	@Autowired
	private ShopService service;
	
	@Autowired
	private MD5Util md5Util;
	
	private Log log = LogFactory.getLog(ShopServiceTest.class);

	@Test
	public void insert() {
		Shop shop = new Shop();
		shop.setName("私情发艺");
		service.insert(null, shop);
	}

	@Test
	public void selectByLikename() {
		System.out.println("shops: " + service.selectByLikeName("发", 10));
	}
}
