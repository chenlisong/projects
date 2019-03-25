package com.yinuo.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.yinuo.bean.User;

@Component
public interface UserMapper extends MapperI<User>{
	
	public User selectByOpenid(@Param("wechatOpenid")String openid);
	
}
 