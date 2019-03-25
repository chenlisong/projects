package com.test.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.test.bean.User;
import com.test.mapper.UserMapper;
import com.test.util.CommonUtil;
import com.test.util.DateTool;

@Service("userService")
public class UserService {

	@Resource
	private UserMapper userMapper;
	
	public long insert(User user) {
		String now = DateTool.standardSdf.format(new Date());
		CommonUtil.setDefaultValue(user);
		user.setCreateTime(now);
		userMapper.insert(user);
		return user.getId();
	}
	
	public User selectByMobile(String mobile) {
		return userMapper.selectByMobile(mobile);
	}
	
	public User selectOne(long id) {
		return userMapper.selectOne(id);
	}
	
	public void delete (long id) {
		userMapper.delete(id);
	}
	
}
