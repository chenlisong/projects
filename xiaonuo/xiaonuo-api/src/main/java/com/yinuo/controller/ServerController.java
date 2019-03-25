package com.yinuo.controller;

import com.yinuo.bean.Server;
import com.yinuo.bean.Shop;
import com.yinuo.bean.User;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.service.ServerService;
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
public class ServerController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private ServerService service;

	@NeedLogin
	@RequestMapping(value="/servers", method=RequestMethod.GET)
	public Object get(User loginUser, @RequestParam(name="shopId", defaultValue = "0") int shopId, @RequestParam(name="workFlag", defaultValue = "0") int workFlag){
		Map<String, Object> result=new HashMap<String, Object>();

		List<Server> servers = null;

		if(shopId > 0) {
			servers = service.selectByShopId(shopId, workFlag);
		}else {
			throw new InvalidArgumentException("查询条件找不到匹配");
		}

		result.put("data", service.convert(servers));
		result.put("count", servers.size());
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/servers", method=RequestMethod.POST)
	public Object post(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		Server server = validation.getObject(body, Server.class, new String[] {"name", "role"});
		int id = service.insert(loginUser, server);
		result.put("id", id);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/servers", method=RequestMethod.PUT)
	public Object put(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		Server server = validation.getObject(body, Server.class, new String[] {"id"});

		service.update(loginUser, server);
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/servers", method=RequestMethod.DELETE)
	public Object put(User loginUser, @RequestParam("id") int id){
		Map<String, Object> result=new HashMap<String, Object>();

		if(id > 0) {
			service.delete(loginUser, id);
		}

		return result;
	}
}
