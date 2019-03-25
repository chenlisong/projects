package com.yinuo.view;

import com.yinuo.bean.ServiceType;
import com.yinuo.bean.Shop;
import com.yinuo.util.DateTool;

import java.util.HashMap;

public class ServiceTypeView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ServiceTypeView(ServiceType object, Shop shop) {
		if(shop == null) {
			return;
		}
		
		put("id", object.getId());
		put("name", object.getName());
		put("price", object.getPrice());

		if(shop != null) {
			put("shop", new ShopView(shop));
		}

		if(object.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(object.getCreateTime()));
		}
	}
	
}
