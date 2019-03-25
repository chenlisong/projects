package com.duolanjian.java.eagle.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class EmailUtilTest {
	
	@Autowired
	private EmailUtil emailUtil;

	@Test
	public void sendEmail() {

		Assert.assertTrue(emailUtil.sendEmail("chenlisong2015@163.com", "test", "this is test."));
	}

}
