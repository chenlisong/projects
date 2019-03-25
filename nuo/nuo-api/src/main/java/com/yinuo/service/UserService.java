package com.yinuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinuo.bean.User;
import com.yinuo.mapper.UserMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.MergerUtil;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public long insert(User user) {
		CommonUtil.setDefaultValue(user);
		userMapper.insert(user);
		return user.getId();
	}
	
	public User selectByOpenid(String openid) {
		return userMapper.selectByOpenid(openid);
	}
	
	public List<User> selectListByPage(int page, int pageSize) {
		return userMapper.selectListByPage(pageSize, (page-1)*pageSize);
	}
	
	public User selectOne(long id) {
		return userMapper.selectOne(id);
	}
	
	public void delete (long id) {
		userMapper.delete(id);
	}
	
	//只能自己修改自己的数据场景
	public void update(User user) {
		User src = selectOne(user.getId());
		user = (User) MergerUtil.merger(src, user);
		userMapper.update(user);
	}
	
}
