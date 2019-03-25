package com.yinuo.view;

import com.yinuo.bean.*;
import com.yinuo.util.DateTool;

import java.util.HashMap;

public class OrderView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public OrderView(Order object, Shop shop, Server server, ServiceType serviceType, User user) {
		if(object == null) {
			return;
		}
		
		put("id", object.getId());
		put("shopId", object.getShopId());
		put("userId", object.getUserId());
		put("serverId", object.getServerId());
		put("serviceTypeId", object.getServiceTypeId());

		put("payMoney", object.getPayMoney());
		put("payState", object.getPayState());
		put("state", object.getState());

		if(shop != null) {
			put("shop", new ShopView(shop));
		}

		if(server != null) {
			put("server", new ServerView(server, null, null));
		}

		if(user != null) {
			put("user", new UserView(user));
		}

		if(serviceType != null) {
			put("serviceType", new ServiceTypeView(serviceType, null));
		}

		if(object.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(object.getCreateTime()));
		}
		if(object.getServerBeginTime() != null) {
			put("serverBeginTime", DateTool.standardSdf.format(object.getServerBeginTime()));
		}
		if(object.getServerEndTime() != null) {
			put("serverEndTime", DateTool.standardSdf.format(object.getServerEndTime()));
		}
		if(object.getCancelTime() != null) {
			put("cancelTime", DateTool.standardSdf.format(object.getCancelTime()));
		}
	}
	
}
