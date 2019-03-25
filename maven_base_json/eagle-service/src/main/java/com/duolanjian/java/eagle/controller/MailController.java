package com.duolanjian.java.eagle.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.duolanjian.java.eagle.util.Config;

@Controller
public class MailController {

	@Autowired
	private Config config;
	
	@RequestMapping(value="/mails", method=RequestMethod.GET)
    @ResponseBody
    public Object get(@RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		
		System.out.println(config.emailAccount);
		
		return result;
    }
	
}
