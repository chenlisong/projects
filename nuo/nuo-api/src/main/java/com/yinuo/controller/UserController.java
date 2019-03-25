package com.yinuo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.yinuo.bean.Constant;
import com.yinuo.service.ManagerClassService;
import com.yinuo.service.ManagerService;
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
import com.yinuo.service.UserService;
import com.yinuo.service.WechatService;
import com.yinuo.util.AesCbcUtil;
import com.yinuo.util.Constant.RedisNameSpace;
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
	private ManagerService managerService;

	@Autowired
	private ManagerClassService managerClassService;
	
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
			loginUser.setCreateTime(new SimpleDateFormat("yyyy-MM-hh HH:mm:ss").format(new Date()));
		}
		
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
		}else {
			userService.update(loginUser);
		}
		
		loginUser = userService.selectByOpenid(openid);
		//设置管理员信息
		loginUser.setManager(managerService.selectByUserid(loginUser.getId()));
		if(loginUser.getManager() != null && loginUser.getManager().getId() > 0) {
			loginUser.setManagerClassList(managerClassService.selectByManagerId(loginUser.getManager().getId()));
		}

		//获取昵称等信息
		logger.info("wechatUserInfo: " + JSON.toJSONString(wechatUserInfo));
		
		String ticket = UUID.randomUUID().toString();
		jedisUtil.set(RedisNameSpace.LOGIN + ticket, JSON.toJSONString(loginUser), RedisNameSpace.LOGIN_TIME);
		
		//清除之前的缓存
		String oldUuid = jedisUtil.get(openid);
		jedisUtil.del(RedisNameSpace.LOGIN + oldUuid);

		//设置新的缓存
		jedisUtil.set(openid, ticket, RedisNameSpace.LOGIN_TIME);
		
		logger.info("loginUser: " + JSON.toJSONString(loginUser));
		result.put("ticket", ticket);
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
	
	@NeedLogin
	@RequestMapping(value="/users", method=RequestMethod.GET)
    public Object get(User loginUser){
		
        Map<String, Object> result=new HashMap<String, Object>();
        result.put("loginUser", new UserView(loginUser));
        return result;
    }
	
	/*
	@RequestMapping(value="/users", method=RequestMethod.POST)
//    @ResponseBody
    public Object post(@RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		User user = validation.getObject(body, User.class, new String[]{"mobile","password"});
		user.setPassword(md5Util.string2MD5(user.getPassword()));
		long id = userService.insert(user);
		result.put("id", id);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/users", method=RequestMethod.PUT)
    public Object put(User loginUser, @RequestBody String body){
		
		loginUser.checkLevel(2);
		Map<String,Object> result = new HashMap<String, Object>();
		User user = validation.getObject(body, User.class, new String[]{"id"});
		User src = userService.selectOne(user.getId());
		if(src == null) {
			throw new InvalidArgumentException("不存在的ID。");
		}
		try{
			user = (User) MergerUtil.merger(src, user);
		} catch (Exception e) {
			throw new InvalidArgumentException(e.getMessage());
		}
		user.setPassword(md5Util.convertMD5(user.getPassword()));
		userService.update(user);
		return result;
    }

	@NeedLogin
	@RequestMapping(value="/users", method=RequestMethod.DELETE)
    public Object delete(User loginUser, @RequestParam Long id){
		
		loginUser.checkLevel(3);
        Map<String, Object> result=new HashMap<String, Object>();
        userService.delete(id);
        return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/users", method=RequestMethod.GET)
    public Object get(User loginUser, @RequestParam(defaultValue="false") boolean self,
    		@RequestParam(defaultValue="1") int page,
    		@RequestParam(defaultValue="20") int pageSize){
		
		List<UserView> users = new ArrayList<UserView>();
		if(self) {
			users.add(new UserView(loginUser));
		}else {
			loginUser.checkLevel(3);
			List<User> userList = userService.selectListByPage(page, pageSize);
			for(User user : userList) {
				users.add(new UserView(user));
			}
		}
		
        Map<String, Object> result=new HashMap<String, Object>();
        result.put("data", users);
        
        return result;
    }
	
	@RequestMapping(value="/users/login", method=RequestMethod.POST)
    public Object login(@RequestBody String body){
		User user = validation.getObject(body, User.class, new String[]{"mobile", "password"});
		
		User loginInfo = userService.selectByMobile(user.getMobile());
		String ticket = UUID.randomUUID().toString();
		if(loginInfo != null && loginInfo.getPassword().equals(md5Util.string2MD5(user.getPassword()))) {
			System.out.println("key: " + RedisNameSpace.LOGIN + ticket);
			jedisUtil.set(RedisNameSpace.LOGIN + ticket, JSON.toJSONString(loginInfo), RedisNameSpace.LOGIN_TIME);
		}else {
			throw new NeedAuthorizationException("账号或者密码错误");
		}
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("loginInfo", new UserView(loginInfo));
		result.put("ticket", ticket);
        return result;
    }*/
}
