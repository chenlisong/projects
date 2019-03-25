package com.yinuo.controller;

import com.yinuo.bean.Inititor;
import com.yinuo.bean.User;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.service.CardService;
import com.yinuo.service.InitItorService;
import com.yinuo.service.WechatService;
import com.yinuo.util.HttpTinyClient;
import com.yinuo.util.JedisUtil;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class InititorController {

	@Autowired
	private Validation validation;

	@Autowired
	private JedisUtil jedisUtil;

	@Autowired
	private InitItorService service;

	@Autowired
	private WechatService wechatService;

	@NeedLogin
	@RequestMapping(value="/inititors", method= RequestMethod.GET)
	public Object get(User loginUser, @RequestParam(name="parentId", defaultValue = "0") int parentId){
		Map<String, Object> result=new HashMap<String, Object>();

		List<Inititor> list = null;
		if(parentId > 0) {
			list = service.selectByParentId(parentId);
		}else {
			list = service.selectByUserId(loginUser.getId());
		}

		result.put("data", service.convert(list));
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/inititors/call", method=RequestMethod.POST)
	public Object postCall(User loginUser, @RequestParam(name="formId", defaultValue = "")String formId){
		Map<String, Object> result=new HashMap<String, Object>();

		Inititor inititor = service.call(loginUser.getId(), formId);

		result.put("data", inititor);
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/inititors/help", method=RequestMethod.POST)
	public Object postHelp(User loginUser, @RequestParam("id")int id, @RequestParam(name="formId", defaultValue = "")String formId){
		Map<String, Object> result=new HashMap<String, Object>();

		service.help(id, loginUser.getId(), formId);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/inititors/qrcode", method=RequestMethod.GET)
	public Object qrcode(User loginUser, @RequestParam("id")int id, @RequestParam("path")String path){
		Map<String, Object> result=new HashMap<String, Object>();

		Inititor inititor = service.selectById(id);
		if(inititor == null) {
			throw new InvalidArgumentException("找不到的组团卡包");
		}

		if(inititor.getQrUrl() != null && !inititor.getQrUrl().isEmpty()) {
			result.put("qrCode", inititor.getQrUrl());
		}else {
			String qrCode = wechatService.getQrCode(path);

            result.put("qrCode", qrCode);

            Inititor update = new Inititor();
			update.setId(id);
			update.setQrUrl(qrCode);
			service.update(update);
		}
		return result;
	}
}
