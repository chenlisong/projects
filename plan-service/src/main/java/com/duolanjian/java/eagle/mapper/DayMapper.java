package com.duolanjian.java.eagle.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.duolanjian.java.eagle.bean.Day;

@Component
public interface DayMapper extends MapperI<Day>{
	
	public List<Day> selectByTime(@Param("beginTime")Date beginTime, @Param("endTime")Date endTime, 
			@Param("userName")String userName);
}
