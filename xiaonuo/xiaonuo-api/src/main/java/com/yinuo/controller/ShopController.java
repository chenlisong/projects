package com.yinuo.controller;

import com.yinuo.bean.Shop;
import com.yinuo.bean.User;
import com.yinuo.bean.UserShop;
import com.yinuo.service.ShopService;
import com.yinuo.service.UserShopService;
import com.yinuo.util.JedisUtil;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ShopController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private ShopService service;
	
	@Autowired
	private JedisUtil jedisUtil;
	
	private Logger logger = LoggerFactory.getLogger(ShopController.class);

	@NeedLogin
	@RequestMapping(value="/shops", method=RequestMethod.GET)
	public Object get(User loginUser, @RequestParam(name="id", defaultValue = "0") int id, @RequestParam(name="name", defaultValue = "") String name,
		@RequestParam(name="likeName", defaultValue = "") String likeName){
		Map<String, Object> result=new HashMap<String, Object>();

		if(id > 0) {
			List<Shop> shops = new ArrayList<Shop>();
			Shop shop = service.selectById(id);
			if(shop != null) {
				shops.add(shop);
			}
			result.put("data", shops);
			result.put("count", shops.size());
		}else if(StringUtils.isNotEmpty(name)) {
			List<Shop> shops = new ArrayList<Shop>();
			Shop shop = service.selectByName(name);
			if(shop != null) {
				shops.add(shop);
			}
			result.put("data", shops);
			result.put("count", shops.size());
		}else if(StringUtils.isNotEmpty(likeName)) {
			List<Shop> shops = service.selectByLikeName(likeName, 10);
			result.put("data", shops);
			result.put("count", shops.size());
		}

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/shops", method=RequestMethod.POST)
	public Object post(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		Shop shop = validation.getObject(body, Shop.class, new String[] {"name", "type"});
		int id = service.insert(loginUser, shop);
		result.put("id", id);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/shops", method=RequestMethod.PUT)
	public Object put(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		Shop shop = validation.getObject(body, Shop.class, new String[] {"id"});
		service.update(loginUser, shop);

		return result;
	}
}
