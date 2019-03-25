package com.yinuo.service;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import com.yinuo.bean.Constant;
import com.yinuo.bean.Inititor;
import com.yinuo.bean.Transfer;
import com.yinuo.bean.User;
import com.yinuo.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Transactional(rollbackFor = Exception.class)
@Service
public class WechatService {
	
	//private static final String appid= "wxcd8029fb06a42b7d";
	//private static final String appid= "wx254e077517572b8a";
	private static final String appid = "wx41226d6532cca43b";

	//private static final String appsecret = "610f818e9c230bc9745764e1de26513d";
	//private static final String appsecret = "22baf57d6b8872580fa47ba8245846ae";
	private static final String appsecret = "6e092ef0971283771027e557e5141ced";

	private Logger logger = LoggerFactory.getLogger(WechatService.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private JedisUtil jedisUtil;

	@Autowired
	private Config config;
	
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

	public String getQrCode(String path) {
	    String result = "";
        String url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=" + getAccessToken();
        List<String> headers = Arrays.asList(new String[] {"Content-Type", "application/json"});

        Map<String, Object> contentMap = new HashMap<String, Object>();
        contentMap.put("path", path);

        try{
			return HttpTinyClient.httpPostDownloadFile(url, headers, JSON.toJSONString(contentMap), 3000);
        }catch (Exception e) {
            logger.error("download qrcode error:", e);
        }

	    return result;
    }

	public String getAccessToken() {
		String accessToken = jedisUtil.get(Constant.JedisNames.AccessToken, String.class);

		if(accessToken != null) {
			return accessToken;
		}

		String url = "https://api.weixin.qq.com/cgi-bin/token";
		String appid = "wx41226d6532cca43b";
		String secret = "6e092ef0971283771027e557e5141ced";

		List<Object> params = Arrays.asList(new Object[]{"grant_type", "client_credential", "appid", appid, "secret", secret});

		try{
			HttpTinyClient.HttpResult resp = HttpTinyClient.httpGet(url, null, params, 3000);

			if(resp != null && resp.content != null) {
				accessToken = JSON.parseObject(resp.content).getString("access_token");
				int expires_in = JSON.parseObject(resp.content).getIntValue("expires_in");
				jedisUtil.set(Constant.JedisNames.AccessToken, accessToken, expires_in);
			}
		}catch (Exception e) {
			logger.error("get accesToken error:", e);
		}

		return accessToken;
	}

	public void transferWechatMsg(Transfer transfer) {
		if(transfer == null) {
			return;
		}

		User src = userService.selectOne(transfer.getSrcId());
		User target = userService.selectOne(transfer.getTargetId());

		String keyword1 = String.format("向好友%s卡片成功", transfer.getType() == Constant.TransferType.Send ? "赠送": "索要");
		String keyword2 =String.format("卡片%s", transfer.getType() == Constant.TransferType.Send ? "赠送": "索要");
		String keyword3 = "积齐一个队伍卡片兑换大奖，最高吃鸡高配台式机，最低得话费";
		String keyword4 = String.format("%s卡片：%s", transfer.getType() == Constant.TransferType.Send ? "失去": "得到", config.humanName[transfer.getCard()-1]);

		sendWechatMsg(src.getWechatOpenid(), transfer.getFormIdOne(),"kuLtMpq8dMunBFqBGR8JgF-iw2iKTVBprCLEtufk-JQ", keyword1, keyword2, keyword3, keyword4);

		keyword1 = String.format("好友向您%s卡片成功", transfer.getType() == Constant.TransferType.Send ? "赠送": "索要");
		keyword2 =String.format("卡片%s", transfer.getType() == Constant.TransferType.Send ? "赠送": "索要");
		keyword3 = "积齐一个队伍卡片兑换大奖，最高吃鸡高配台式机，最低得话费";
		keyword4 = String.format("%s卡片：%s", transfer.getType() == Constant.TransferType.Send ? "得到": "失去", config.humanName[transfer.getCard()-1]);

		sendWechatMsg(target.getWechatOpenid(), transfer.getFormIdTwo(), "kuLtMpq8dMunBFqBGR8JgF-iw2iKTVBprCLEtufk-JQ", keyword1, keyword2, keyword3, keyword4);
	}

	public void inititorWechatMsg(List<Inititor> inititors) {
		if(inititors == null || inititors.isEmpty()) {
			return;
		}

		Date beginTime = null;
		Date fixTime = new Date();
		for(Inititor inititor: inititors) {
			if(inititor.getParentId().intValue() == 0) {
				beginTime = inititor.getCreateTime();
			}else {
				if(fixTime == null || fixTime.getTime() < inititor.getCreateTime().getTime()) {
					fixTime = inititor.getCreateTime();
				}
			}
		}

		for(Inititor inititor: inititors) {
			String keyword1 = String.format("%s一起拆卡包", inititor.getParentId().intValue() == 0 ? "开团" : "参团");
			String keyword2 = "组团卡包";
			String keyword3 = "积齐一个队伍卡片兑换大奖，最高吃鸡高配台式机，最低得话费";
			String keyword4 = String.format("得到卡片：%s", config.humanName[inititor.getCard()-1]);

			User user = userService.selectOne(inititor.getUserId());

			sendWechatMsg(user.getWechatOpenid(), inititor.getFormId(), "kuLtMpq8dMunBFqBGR8JgF-iw2iKTVBprCLEtufk-JQ", keyword1, keyword2,
					keyword3, keyword4);
		}
	}

	public void sendWechatMsg(String wechatOpenId, String formId, String templateId, String... msgs) {
		if(wechatOpenId == null || wechatOpenId.isEmpty() || formId == null || formId.isEmpty()) {
			logger.info("send wechat msg fail, 找不到openId或者formId，formId:{}, templateId:{}, msgs:{}", formId, templateId, msgs);
			return;
		}

		String accessToken = getAccessToken();
		String url = String.format("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s", accessToken);

		//先发送src
		Map<String, Object> contentMap = new HashMap<String, Object>(16);
		contentMap.put("touser", wechatOpenId);
		contentMap.put("template_id", templateId);
		contentMap.put("form_id", formId);
		contentMap.put("emphasis_keyword", "keyword1.DATA");

		Map<String, Object> data = new HashMap<String, Object>();

		for(int i=0; i<msgs.length; i++) {
			Map<String, Object> keyword = new HashMap<String, Object>();
			keyword.put("value", msgs[i]);
			data.put("keyword"+(i+1), keyword);
		}

		contentMap.put("data", data);

		List<String> headers = Arrays.asList(new String[] {"Content-Type", "application/json"});

		StringBuffer sb = new StringBuffer();
		try {
		    sb.append("send wechat msg suc, formId: " +formId);
			sb.append(", content: ").append(JSON.toJSONString(contentMap));
			HttpTinyClient.HttpResult result = HttpTinyClient.httpPost(url, headers, JSON.toJSONString(contentMap), 3000);
			sb.append(", result: ").append(result.content);
		}catch (Exception e) {
			logger.error("send wechat module msg error.", e);
		}finally {
			logger.info(sb.toString());
		}
	}
}
