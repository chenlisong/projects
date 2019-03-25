package com.yinuo.controller;

import com.yinuo.bean.ServiceType;
import com.yinuo.bean.Shop;
import com.yinuo.bean.User;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.service.ServiceTypeService;
import com.yinuo.service.ShopService;
import com.yinuo.util.JedisUtil;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ServiceTypeController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private ServiceTypeService service;

	@NeedLogin
	@RequestMapping(value="/serviceTypes", method=RequestMethod.GET)
	public Object get(User loginUser, @RequestParam(name="shopId", defaultValue = "0") int shopId){
		Map<String, Object> result=new HashMap<String, Object>();

		List<ServiceType> list = null;
		if(shopId > 0) {
			list = service.selectByShopId(shopId);
		}else {
			throw new InvalidArgumentException("参数异常");
		}

		result.put("data", service.convert(list));
		result.put("count", list.size());

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/serviceTypes", method=RequestMethod.POST)
	public Object post(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		ServiceType object = validation.getObject(body, ServiceType.class, new String[] {"name"});
		int id = service.insert(loginUser, object);
		result.put("id", id);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/serviceTypes", method=RequestMethod.PUT)
	public Object put(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		ServiceType object = validation.getObject(body, ServiceType.class, new String[] {"id"});
		service.update(loginUser, object);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/serviceTypes", method=RequestMethod.DELETE)
	public Object delete(User loginUser, @RequestParam(name="id") int id){
		Map<String, Object> result=new HashMap<String, Object>();

		service.delete(loginUser, id);
		return result;
	}
}
