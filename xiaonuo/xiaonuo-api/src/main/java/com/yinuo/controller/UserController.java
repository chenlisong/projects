package com.yinuo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yinuo.bean.Constant;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.service.*;
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
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value="/wechatLogin", method=RequestMethod.GET)
    public Object post(@RequestParam(name="code") String code, @RequestParam(name="encryptedData") String encryptedData,
    		@RequestParam(name="iv") String iv){
		Map<String,Object> result = new HashMap<String, Object>();
		
		JSONObject jsonObject = wechatService.getWechatInfo(code);
		String openid = jsonObject.getString("openid");
		String session_key = jsonObject.getString("session_key");
		
		User loginUser = userService.selectByOpenid(openid);

		if(loginUser == null) {
			loginUser = new User();
			loginUser.setWechatOpenid(openid);
		}
		//更新上次登录时间
		loginUser.setLoginTime(DateTool.standardSdf.format(new Date()));

		//更新微信用户信息
		JSONObject userInfoJSON = null;
		String wechatUserInfo = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
		if (null != wechatUserInfo && session_key.length() > 0) {
			userInfoJSON = JSON.parseObject(wechatUserInfo);
        	loginUser.setAvatarUrl(userInfoJSON.getString("avatarUrl"));
        	loginUser.setCity(userInfoJSON.getString("city"));
        	loginUser.setCountry(userInfoJSON.getString("country"));
        	loginUser.setProvince(userInfoJSON.getString("province"));
        	loginUser.setSex(userInfoJSON.getIntValue("gender"));
        	loginUser.setWechatNickname(userInfoJSON.getString("nickName"));
        }
		
		if(loginUser.getId() == null || loginUser.getId().longValue() <= 0) {
			userService.insert(loginUser);
			loginUser = userService.selectByOpenid(openid);
		}else {
			//mqService.produce(Constant.JedisNames.UserUpdate, loginUser);
			userService.update(loginUser);
		}

		//获取昵称等信息
		//logger.info("wechat user info: " + JSON.toJSONString(wechatUserInfo));

		jedisUtil.set(Constant.JedisNames.UserOpenid + openid, JSON.toJSONString(loginUser), Constant.JedisNames.LOGIN_TIME);
		
		//logger.info("user login info: " + JSON.toJSONString(loginUser));
		result.put("openid", openid);
		result.put("loginUser", new UserView(loginUser));
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

	@RoleShoper
	@NeedLogin
	@RequestMapping(value="/users/nickName", method=RequestMethod.GET)
    public Object getByNickname(User loginUser, @RequestParam(name="nickName", defaultValue = "") String nickName){
		
        Map<String, Object> result=new HashMap<String, Object>();
        if(nickName != null && !"".equals(nickName)) {
        	User user = userService.selectByNickName(nickName);
        	if(user == null) {
        		throw new InvalidArgumentException("找不到的用户");
			}

        	result.put("user", new UserView(user));
		}else {
			result.put("loginUser", new UserView(loginUser));
		}
        return result;
    }
}
