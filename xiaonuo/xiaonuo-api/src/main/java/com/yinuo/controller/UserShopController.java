package com.yinuo.controller;

import com.yinuo.bean.User;
import com.yinuo.bean.UserShop;
import com.yinuo.service.UserShopService;
import com.yinuo.util.JedisUtil;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserShopController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private UserShopService service;

	@NeedLogin
	@RequestMapping(value="/userShops", method=RequestMethod.GET)
	public Object get(User loginUser, @RequestParam(name="shopId", defaultValue = "0") int shopId, @RequestParam(name="id", defaultValue = "0") int id,
					  @RequestParam(name="limit", defaultValue = "0") int limit, @RequestParam(name="userId", defaultValue = "0") int userId){

		Map<String, Object> result=new HashMap<String, Object>();
		List<UserShop> userShops = null;
		//分页查询
		if(shopId > 0 && limit > 0) {
			userShops = service.selectByShopId(shopId, id, limit);
		}else if(userId > 0){
			//查看当前用户关注的店铺
			userShops = service.selectByUserid(userId);
		}
		result.put("data", service.convert(userShops));
		result.put("count", userShops.size());
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/userShops", method=RequestMethod.POST)
	public Object post(User loginUser, @RequestBody String body){

		Map<String, Object> result = new HashMap<String, Object>();

		UserShop userShop = validation.getObject(body, UserShop.class, new String[]{"shopId"});

		int id = service.insert(loginUser, userShop);
		result.put("id", id);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/userShops", method=RequestMethod.DELETE)
	public Object delete(User loginUser, @RequestParam("id") int id){

		Map<String, Object> result=new HashMap<String, Object>();

		service.delete(loginUser, id);

		return result;
	}
}
