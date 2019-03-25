package com.yinuo.controller;

import com.yinuo.bean.Card;
import com.yinuo.bean.User;
import com.yinuo.service.CardService;
import com.yinuo.util.JedisUtil;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CardController {

	@Autowired
	private Validation validation;

	@Autowired
	private JedisUtil jedisUtil;

	@Autowired
	private CardService service;

	@NeedLogin
	@RequestMapping(value="/cards", method= RequestMethod.GET)
	public Object get(User loginUser){
		Map<String, Object> result=new HashMap<String, Object>();

		result.put("data", service.selectByUserId(loginUser.getId()));

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/cards/draw", method=RequestMethod.POST)
	public Object post(User loginUser){
		Map<String, Object> result=new HashMap<String, Object>();

		int card = service.draw(loginUser.getId());

		result.put("card", card);
		return result;
	}
}
