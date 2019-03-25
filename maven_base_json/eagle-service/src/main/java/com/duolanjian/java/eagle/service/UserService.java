package com.duolanjian.java.eagle.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duolanjian.java.eagle.bean.User;
import com.duolanjian.java.eagle.exception.InvalidArgumentException;
import com.duolanjian.java.eagle.mapper.UserMapper;
import com.duolanjian.java.eagle.util.CommonUtil;
import com.duolanjian.java.eagle.util.DateTool;

@Service("userService")
public class UserService {

	@Resource
	private UserMapper userMapper;
	
	public long insert(User user) {
		User src = selectByMobile(user.getMobile());
		
		user.check();
		if(src != null) {
			throw new InvalidArgumentException("已存在的手机号");
		}
		
		String now = DateTool.standardSdf.format(new Date());
		CommonUtil.setDefaultValue(user);
		user.setCreateTime(now);
		userMapper.insert(user);
		return user.getId();
	}
	
	public void deleteByMobile(String mobile) {
		userMapper.deleteByMobile(mobile);
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
