package com.duolanjian.java.market.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yinuo.bean.Student;
import com.yinuo.service.StudentService;
import com.yinuo.util.CommonUtil;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class StudentServiceTest {
	//在属性中使用 @Autowired 注释来除去 setter 方法
	@Autowired
	private StudentService service;
	
	private Log log = LogFactory.getLog(StudentServiceTest.class);
	
	@Test
	public void insert() {
		Student object = new Student();
		CommonUtil.setDefaultValue(object);
		log.info("insert user:"+object);
		//如果存在先删除在增加
		try{
			//userService.deleteByMobile(mobile);
			long id = service.insert(null, object);
			log.info("insert user id is " + id);
			log.info("insert user id2 is " + object.getId());
			Assert.assertTrue(true);//查看运行结果是否为true。
		}catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	
}
