package com.yinuo.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yinuo.mapper.UserMapper;
import com.yinuo.util.HttpUtil;

@Transactional(rollbackFor = Exception.class)
@Service
public class WechatService {
	
	//private static final String appid= "wxcd8029fb06a42b7d";
	private static final String appid= "wx254e077517572b8a";

	//private static final String appsecret = "610f818e9c230bc9745764e1de26513d";
	private static final String appsecret = "22baf57d6b8872580fa47ba8245846ae";

	private Logger logger = LoggerFactory.getLogger(WechatService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	public JSONObject getWechatInfo(String code) {
		String wechatUrl = "";
		try {
			wechatUrl = String.format("https://api.weixin.qq.com/sns/jscode2session"
					+ "?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", URLEncoder.encode(appid, "utf-8"), 
					URLEncoder.encode(appsecret, "utf-8"), URLEncoder.encode(code, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("wechat url: " + wechatUrl);
		String response = HttpUtil.sendGet(wechatUrl);
		logger.info("wechat response: " + response);
		
		JSONObject jsonObject = JSON.parseObject(response);
		return jsonObject;
	}
	
}
