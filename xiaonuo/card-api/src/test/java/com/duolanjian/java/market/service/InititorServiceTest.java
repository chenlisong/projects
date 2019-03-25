package com.duolanjian.java.market.service;

import com.yinuo.bean.Constant;
import com.yinuo.bean.Inititor;
import com.yinuo.service.InitItorService;
import com.yinuo.service.TransferService;
import com.yinuo.util.MD5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class InititorServiceTest {
	//在属性中使用 @Autowired 注释来除去 setter 方法
	@Autowired
	private InitItorService service;
	
	@Autowired
	private MD5Util md5Util;
	
	private Log log = LogFactory.getLog(InititorServiceTest.class);

	@Test
	public void call() {

		Inititor inititor = service.call(1, null);

		service.help(inititor.getId(), 2, "");
		service.help(inititor.getId(), 3, "");

		System.out.println("list: " + service.selectByUserId(1));
		System.out.println("list2: " + service.selectByUserId(1));
	}

}
