package com.yinuo.mapper;

import org.springframework.stereotype.Component;

@Component
public interface ClassesMapper extends MapperI<com.yinuo.bean.Classes>{
	
	public int countListByPage();
}
 