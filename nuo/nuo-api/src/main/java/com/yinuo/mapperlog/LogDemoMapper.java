package com.yinuo.mapperlog;

import org.springframework.stereotype.Component;

import com.yinuo.bean.Student;
import com.yinuo.mapper.MapperI;

@Component
public interface LogDemoMapper extends MapperI<Student>{
	
}