package com.yinuo.service;

import com.alibaba.fastjson.JSON;
import com.yinuo.bean.Constant;
import com.yinuo.bean.Shop;
import com.yinuo.bean.User;
import com.yinuo.mapper.ShopMapper;
import com.yinuo.mapper.UserMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class ShopService {
	
	@Autowired
	private ShopMapper mapper;
	
	public int insert(User loginUser, Shop shop) {
		CommonUtil.setDefaultValue(shop);
		mapper.insert(shop);
		return shop.getId();
	}

	public void update(User loginUser, Shop shop) {
		mapper.update(shop);
	}

	public List<Shop> selectByIds(List<Integer> ids) {
		return mapper.selectListByIds(ids);
	}

	public Shop selectByName(String name) {
		return mapper.selectByName(name);
	}

	public List<Shop> selectByLikeName(String name, int limit) {
		return mapper.selectByLikeName(name, limit);
	}

	public Shop selectById(int id) {
		return mapper.selectOne(id);
	}

}
