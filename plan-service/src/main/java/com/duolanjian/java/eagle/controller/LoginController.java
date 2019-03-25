package com.duolanjian.java.eagle.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.duolanjian.java.eagle.bean.User;
import com.duolanjian.java.eagle.exception.InvalidArgumentException;
import com.duolanjian.java.eagle.util.Constant.JedisNameSpace;
import com.duolanjian.java.eagle.view.UserView;

@RestController
public class LoginController extends BaseController{

	@RequestMapping(value="/login", method=RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		User user = validation.getObject(body, User.class, new String[]{"mobile","password"});

		User src = userService.selectByMobile(user.getMobile());
		String ticket = md5Util.string2MD5("" + Math.random() + config.shareNum + new Date().getTime());
		if(src != null && src.getPassword().equals(user.getPassword())) {
			jedisUtil.set(JedisNameSpace.PROJECT_NAME + JedisNameSpace.LOGIN + ticket, 
					JSON.toJSONString(src), config.loginTime);
			result.put("ticket", ticket);
			result.put("user", new UserView(src));
		}else {
			throw new InvalidArgumentException("账号或密码错误");
		}
		
		return result;
    }
	
}
