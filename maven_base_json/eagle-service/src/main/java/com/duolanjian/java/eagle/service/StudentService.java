package com.duolanjian.java.eagle.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duolanjian.java.eagle.bean.Student;
import com.duolanjian.java.eagle.mapperlog.StudentMapper;

@Service("studentService")
public class StudentService {

	@Resource
	private StudentMapper studentMapper;
	
	public long insert(Student student) {
		studentMapper.insert(student);
		studentMapper.delete(4l);
		System.out.println(1/0);
		
		return student.getId();
	}
	
}
