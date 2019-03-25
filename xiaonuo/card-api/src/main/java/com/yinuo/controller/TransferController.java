package com.yinuo.controller;

import com.yinuo.bean.Constant;
import com.yinuo.bean.Inititor;
import com.yinuo.bean.Transfer;
import com.yinuo.bean.User;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.service.CardService;
import com.yinuo.service.TransferService;
import com.yinuo.util.JedisUtil;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TransferController {

	@Autowired
	private TransferService service;

	@NeedLogin
	@RequestMapping(value="/transfers", method= RequestMethod.GET)
	public Object get(User loginUser, @RequestParam("type") int type){
		Map<String, Object> result=new HashMap<String, Object>();

		if(type != Constant.TransferType.Get && type != Constant.TransferType.Send) {
			throw new InvalidArgumentException("只能查询赠送或者索要记录");
		}

		result.put("data", service.selectByUserId(loginUser.getId(), type));
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/transfers", method=RequestMethod.POST)
	public Object send(User loginUser, @RequestParam("type")int type, @RequestParam("card")int card
			, @RequestParam(name="formId", defaultValue = "")String formId){

		Map<String, Object> result=new HashMap<String, Object>();

		int id = 0;
		if(type == Constant.TransferType.Send) {
			id = service.transfer(loginUser.getId(), Constant.TransferType.Send, card, formId);
		}else if(type == Constant.TransferType.Get) {
			id = service.transfer(loginUser.getId(), Constant.TransferType.Get, card, formId);
		}else {
			throw new InvalidArgumentException("您传入的类型异常");
		}

		result.put("id", id);
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/transfers/fix", method=RequestMethod.POST)
	public Object fix(User loginUser, @RequestParam("id")int id, @RequestParam(name="formId", defaultValue = "")String formId){

		Map<String, Object> result=new HashMap<String, Object>();

		service.fix(id, loginUser.getId(), formId);

		return result;
	}
}
