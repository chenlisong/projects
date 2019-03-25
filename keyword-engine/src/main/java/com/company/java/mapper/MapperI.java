package com.company.java.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;



public interface MapperI<T> {
	
	T selectOne(@Param("id") long id);
	
	List<T> selectListByPage(@Param("limit") int limit, @Param("offset") int offset);
	
	List<T> selectListByIds(@Param("ids")List<Long> ids);
	
	int countByIds(@Param("ids") List<Long> ids);

	void insert(T t);
	
	void update(T t);

	void delete(long id);
	
	void deleteAll();
	
}
