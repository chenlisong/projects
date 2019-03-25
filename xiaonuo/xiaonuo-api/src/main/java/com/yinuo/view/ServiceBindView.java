package com.yinuo.view;

import com.yinuo.bean.Server;
import com.yinuo.bean.ServiceBind;
import com.yinuo.bean.ServiceType;
import com.yinuo.bean.Shop;
import com.yinuo.util.DateTool;

import java.util.HashMap;

public class ServiceBindView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ServiceBindView(ServiceBind object, Shop shop, Server server, ServiceType serviceType) {
		if(object == null) {
			return;
		}
		
		put("id", object.getId());
		put("shopId", object.getShopId());
		put("serverId", object.getServerId());
		put("serviceTypeId", object.getServiceTypeId());

		if(shop != null) {
			put("shop", new ShopView(shop));
		}

		if(server != null) {
			put("server", new ServerView(server, null, null));
		}

		if(serviceType != null) {
			put("serviceType", new ServiceTypeView(serviceType, null));
		}

		if(object.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(object.getCreateTime()));
		}
	}
	
}
