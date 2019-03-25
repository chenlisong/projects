package com.duolanjian.java.eagle.util;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class Md5UtilTest {
	
	@Autowired
	private MD5Util md5Util;

	private Log log = LogFactory.getLog(MD5Util.class);
	
//	@Test
	public void encry() {
		String text = "chenlisong123456..---";
		log.info("text: "+text);
		String md5 = md5Util.string2MD5(text);
		log.info("md5: "+md5);
		String convert = md5Util.convertMD5(md5);
		log.info("convert md5: " + convert);
		
		Assert.assertTrue(text.equalsIgnoreCase(convert));
	}
	
	@Test
	public void ticket() {

		String ticket = md5Util.string2MD5("" + Math.random() + 1001 + new Date().getTime());
		System.out.println(ticket);
		System.out.println(ticket.length());
	}

}
