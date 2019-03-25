package com.yinuo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.yinuo.bean.Score;

@Component
public interface ScoreMapper extends MapperI<Score>{
	
	public List<Score> selectByStudentId(@Param("studentId")long studentId, @Param("type")int type, 
			@Param("limit")int limit, @Param("offset")int offset);
	
	public int countByStudentId(@Param("studentId")long studentId, @Param("type")int type);
	
}
 