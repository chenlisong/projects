package com.yinuo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.yinuo.bean.UserStudent;

@Component
public interface UserStudentMapper extends MapperI<UserStudent>{

	public List<UserStudent> selectListByUserid(@Param("userId")long userId, @Param("studentId")long studentId, @Param("limit")int limit, @Param("offset")int offset);

	public int countListByUserid(@Param("userId")long userId, @Param("studentId")long studentId);
	
}
 