package com.company.java.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends BaseController{

	@RequestMapping(value="/users", method=RequestMethod.POST)
    @ResponseBody
    public Object post(){
		Map<String,Object> result = new HashMap<String, Object>();

		return result;
    }

}
