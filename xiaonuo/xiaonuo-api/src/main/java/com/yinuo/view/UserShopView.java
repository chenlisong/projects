package com.yinuo.view;

import com.yinuo.bean.Server;
import com.yinuo.bean.Shop;
import com.yinuo.bean.User;
import com.yinuo.bean.UserShop;
import com.yinuo.util.DateTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserShopView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public UserShopView(UserShop object, Shop shop, Server server) {
		if(object == null) {
			return;
		}

		put("id", object.getId());
		put("userId", object.getUserId());
		put("shopId", object.getShopId());
		put("money", object.getMoney());
		put("lastServerId", object.getLastServerId());

		if(shop != null) {
			put("shop", new ShopView(shop));
		}

		if(server != null) {
			put("server", new ServerView(server, null, null));
		}

		if(object.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(object.getCreateTime()));
		}

		if(object.getLastConsumeTime() != null) {
			put("lastConsumeTime", DateTool.standardSdf.format(object.getLastConsumeTime()));
		}

	}
	
}
