package com.duolanjian.java.eagle.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duolanjian.java.eagle.bean.Hour;
import com.duolanjian.java.eagle.util.DateTool;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class HourServiceTest {

	@Autowired
	private HourService hourService;
	
	private Log log = LogFactory.getLog(HourServiceTest.class);
	
	@Test
	public void insert() {
		
		Hour hour = new Hour();
		hour.setBeginTime(DateTool.standardSdf.format(new Date()));
		hour.setComplete(1);
		hour.setContent("看书");
		hour.setDayId(0l);
		hour.setEndTime(DateTool.standardSdf.format(new Date()));
		long id = hourService.insert(hour);
		log.info("id:"+id);
		
		log.info("hour:"+hourService.selectOne(id));
		
	}
	
}
