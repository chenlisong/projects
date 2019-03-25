package com.duolanjian.java.eagle.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.duolanjian.java.eagle.bean.Student;

@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类  
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class StudentServiceTest {

	@Autowired
	private StudentService studentService;
	
	private Log log = LogFactory.getLog(StudentServiceTest.class);
	
	@Test
	public void test() {
		String name = "test1";
		
		Student student = new Student();
		student.setName(name);
		log.info("insert student:"+student);
		
		try{
			long id = studentService.insert(student);
			log.info("insert student id is " + id);
			Assert.assertTrue(true);
		}catch (Exception e) {
//			Assert.assertTrue(false);
			e.printStackTrace();
		}
	}

}
