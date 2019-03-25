package com.yinuo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yinuo.bean.Constant;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.service.*;
import com.yinuo.util.Config;
import com.yinuo.util.DateTool;
import com.yinuo.validation.RoleShoper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yinuo.bean.User;
import com.yinuo.util.AesCbcUtil;
import com.yinuo.util.JedisUtil;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import com.yinuo.view.UserView;

@RestController
public class UserController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JedisUtil jedisUtil;
	
	@Autowired
	private WechatService wechatService;

	@Autowired
	private Config config;
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value="/wechatLogin", method=RequestMethod.GET)
    public Object post(@RequestParam(name="code") String code){
		Map<String,Object> result = new HashMap<String, Object>();
		
		JSONObject jsonObject = wechatService.getWechatInfo(code);
		String openid = jsonObject.getString("openid");

		User loginUser = userService.selectByOpenidNoEx(openid);

		if(loginUser == null) {
			loginUser = new User();
			loginUser.setWechatOpenid(openid);
			userService.insert(loginUser);

			loginUser = userService.selectByOpenid(openid);
		}

		jedisUtil.set(Constant.JedisNames.UserOpenid + openid, JSON.toJSONString(loginUser), Constant.JedisNames.LOGIN_TIME);

		result.put("openid", openid);
		result.put("loginUser", new UserView(loginUser, config.version));
		return result;
    }

	@NeedLogin
	@RequestMapping(value="/users", method=RequestMethod.PUT)
	public Object put(User loginUser, @RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		User user = validation.getObject(body, User.class, new String[]{"id"});
		userService.update(user);
		result.put("id", user.getId());
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public Object post(User loginUser, @RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		System.out.println(String.format("body: %s", body));
		return result;
	}


}
