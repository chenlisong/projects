package com.yinuo.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.yinuo.bean.Constant;
import com.yinuo.exception.NeedAuthorizationException;
import com.yinuo.util.JedisUtil;
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

	@Autowired
	private JedisUtil jedis;
	
	public long insert(User user) {
		CommonUtil.setDefaultValue(user);
		userMapper.insert(user);
		return user.getId();
	}
	
	public User selectByOpenid(String openid) {
		User user = jedis.get(Constant.JedisNames.UserOpenid + openid, User.class);
		if(user == null) {
			user = userMapper.selectByOpenid(openid);
			if(user == null) {
				throw new NeedAuthorizationException(Constant.MessageTip.Permisson);
			}
			jedis.set(Constant.JedisNames.UserOpenid + openid, JSON.toJSONString(user), Constant.JedisNames.LOGIN_TIME);
		}
		return user;
	}

	public User selectByNickName(String nickName) {
		return userMapper.selectByNickName(nickName);
	}

	public List<User> selectListByPage(int page, int pageSize) {
		return userMapper.selectListByPage(pageSize, (page-1)*pageSize);
	}

	public List<User> selectListByIds(List<Integer> ids) {
		return userMapper.selectListByIds(ids);
	}
	
	public User selectOne(long id) {
		return userMapper.selectOne(id);
	}
	
	public void delete (long id) {
		User user = selectOne(id);
		userMapper.delete(id);

		jedis.del(Constant.JedisNames.UserOpenid + user.getWechatOpenid());
	}
	
	//只能自己修改自己的数据场景
	public void update(User user) {
		userMapper.update(user);

		jedis.del(Constant.JedisNames.UserOpenid + user.getWechatOpenid());
	}
	
}
