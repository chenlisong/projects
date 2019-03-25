package com.duolanjian.java.eagle.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duolanjian.java.eagle.bean.User;
import com.duolanjian.java.eagle.exception.InvalidArgumentException;
import com.duolanjian.java.eagle.service.UserService;
import com.duolanjian.java.eagle.util.MD5Util;
import com.duolanjian.java.eagle.util.MergerUtil;
import com.duolanjian.java.eagle.validation.Validation;
import com.duolanjian.java.eagle.view.UserView;

@Controller
public class UserController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MD5Util md5Util;
	
	@RequestMapping(value="/user", method=RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		User user = validation.getObject(body, User.class, new String[]{"mobile","password"});
		user.setPassword(md5Util.convertMD5(user.getPassword()));
		long id = userService.insert(user);
		result.put("id", id);
		return result;
    }
	
	@RequestMapping(value="/user", method=RequestMethod.PUT)
    @ResponseBody
    public Object put(@RequestBody String body){
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
		long id = userService.insert(user);
		result.put("id", id);
		return result;
    }

	
	@RequestMapping(value="/user", method=RequestMethod.DELETE)
    @ResponseBody
    public Object get(@RequestParam Long id){
        Map<String, Object> result=new HashMap<String, Object>();
        userService.delete(id);
        return result;
    }
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
    @ResponseBody
    public Object get(@RequestParam String mobile,
    		@RequestParam String password){
        Map<String, Object> result=new HashMap<String, Object>();
        User user = userService.selectByMobile(mobile);
        if(user == null) {
        	throw new InvalidArgumentException("找不到该手机号。");
        }
        if(!user.getPassword().equals(md5Util.convertMD5(password))) {
        	throw new InvalidArgumentException("密码错误。");
        }
        result.put("data", new UserView(user));
        
        return result;
    }
}
