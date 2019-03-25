package com.duolanjian.java.eagle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.duolanjian.java.eagle.service.DayService;
import com.duolanjian.java.eagle.service.HourService;
import com.duolanjian.java.eagle.service.UserService;
import com.duolanjian.java.eagle.util.Config;
import com.duolanjian.java.eagle.util.JedisUtil;
import com.duolanjian.java.eagle.util.MD5Util;
import com.duolanjian.java.eagle.validation.Validation;

@RestController
public class BaseController {

	@Autowired
	Validation validation;
	
	@Autowired
	JedisUtil jedisUtil;
	
	@Autowired
	MD5Util md5Util;
	
	@Autowired
	UserService userService;
	
	@Autowired
	Config config;
	
	@Autowired
	DayService dayService;
	
	@Autowired
	HourService hourService;
	
}
