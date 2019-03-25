package com.duolanjian.java.market.service;

import com.yinuo.bean.Constant;
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
public class TransferServiceTest {
	//在属性中使用 @Autowired 注释来除去 setter 方法
	@Autowired
	private TransferService service;
	
	@Autowired
	private MD5Util md5Util;
	
	private Log log = LogFactory.getLog(TransferServiceTest.class);

	@Test
	public void transfer() {

		//service.transfer(2, Constant.TransferType.Send, 1);

		//1赠送给2卡片1
//		int id = service.transfer(1, Constant.TransferType.Send, 7, null);
//
//		service.fix(id, 2, "");

		int id = service.transfer(4, Constant.TransferType.Get, 2, null);

		//service.fix(id, 2, null);

	}

}
