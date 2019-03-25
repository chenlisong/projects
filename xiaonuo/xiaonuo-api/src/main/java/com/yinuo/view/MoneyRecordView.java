package com.yinuo.view;

import com.yinuo.bean.*;
import com.yinuo.util.DateTool;

import java.util.HashMap;

public class MoneyRecordView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public MoneyRecordView(MoneyRecord object, Shop shop, Server server, User user) {
		if(object == null) {
			return;
		}
		
		put("id", object.getId());
		put("type", object.getType());
		put("money", object.getMoney());
		put("giftMoney", object.getGiftMoney());

		put("shopId", object.getShopId());
		put("userId", object.getUserId());
		put("serverId", object.getServerId());

		if(user != null) {
			put("user", new UserView(user));
		}

		if(shop != null) {
			put("shop", new ShopView(shop));
		}

		if(server != null) {
			put("server", new ServerView(server, null, null));
		}

		if(object.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(object.getCreateTime()));
		}
	}
	
}
