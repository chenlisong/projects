package com.duolanjian.java.eagle.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.duolanjian.java.eagle.bean.Hour;

@Component
public interface HourMapper extends MapperI<Hour>{
	
	public List<Hour> selectByDayId(@Param("dayId")long dayId);
	
}