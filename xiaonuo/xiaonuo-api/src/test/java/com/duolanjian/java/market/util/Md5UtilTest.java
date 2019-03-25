package com.duolanjian.java.market.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yinuo.util.MD5Util;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class Md5UtilTest {
	
	@Autowired
	private MD5Util md5Uti;

	private Log log = LogFactory.getLog(MD5Util.class);
	
	@Test
	public void encry() {
		String text = "chenlisong123456..---";
		log.info("text: "+text);
		String md5 = md5Uti.string2MD5(text);
		log.info("md5: "+md5);
		String convert = md5Uti.convertMD5(md5);
		log.info("convert md5: " + convert);
		
		Assert.assertTrue(text.equalsIgnoreCase(convert));
	}

}
