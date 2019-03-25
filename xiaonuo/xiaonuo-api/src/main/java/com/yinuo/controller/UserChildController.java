package com.yinuo.controller;

import com.yinuo.bean.User;
import com.yinuo.bean.UserChild;
import com.yinuo.bean.UserShop;
import com.yinuo.service.UserChildService;
import com.yinuo.service.UserShopService;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserChildController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private UserChildService service;

	@NeedLogin
	@RequestMapping(value="/userChilds", method=RequestMethod.GET)
	public Object get(User loginUser, @RequestParam(name="shopId", defaultValue = "0") int shopId, @RequestParam(name="parentId", defaultValue = "0") int parentId,
					  @RequestParam(name="childId", defaultValue = "0") int childId){

		Map<String, Object> result=new HashMap<String, Object>();
		List<UserChild> userChilds = service.selectByShopId(shopId, parentId, childId);
		result.put("data", service.convert(userChilds));
		result.put("count", userChilds.size());
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/userChilds", method=RequestMethod.POST)
	public Object post(User loginUser, @RequestBody String body){

		Map<String, Object> result = new HashMap<String, Object>();

		UserChild userShop = validation.getObject(body, UserChild.class, new String[]{"childId", "shopId"});

		int id = service.insert(loginUser, userShop);
		result.put("id", id);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/userChilds", method=RequestMethod.DELETE)
	public Object delete(User loginUser, @RequestParam("id") int id){

		Map<String, Object> result=new HashMap<String, Object>();

		service.delete(loginUser, id);

		return result;
	}
}
