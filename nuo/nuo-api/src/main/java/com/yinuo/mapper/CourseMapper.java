package com.yinuo.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.yinuo.bean.Course;

@Component
public interface CourseMapper extends MapperI<Course>{

	public List<Course> selectByClassId(@Param("classId")long classId, @Param("beginTime")Date beginTime, @Param("endTime")Date endTime);

	public int countByClassId(@Param("classId")long classId, @Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
	
}
 