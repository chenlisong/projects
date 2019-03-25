package com.yinuo.view;

import com.yinuo.bean.Server;
import com.yinuo.bean.Shop;
import com.yinuo.bean.User;
import com.yinuo.util.DateTool;

import java.util.HashMap;

public class ServerView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ServerView(Server object, Shop shop, User user) {
		if(shop == null) {
			return;
		}
		
		put("id", object.getId());
		put("name", object.getName());
		put("pic", object.getPic());
		put("role", object.getRole());
		put("workFlag", object.getWorkFlag());

		if(shop != null) {
			put("shop", new ShopView(shop));
		}

		if(user != null) {
			put("user", new UserView(user));
		}

		if(object.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(object.getCreateTime()));
		}
	}
	
}
