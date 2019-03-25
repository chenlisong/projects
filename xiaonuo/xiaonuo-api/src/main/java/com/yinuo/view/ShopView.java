package com.yinuo.view;

import com.yinuo.bean.Shop;
import com.yinuo.bean.User;
import com.yinuo.util.DateTool;

import java.util.HashMap;

public class ShopView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ShopView(Shop object) {
		if(object == null) {
			return;
		}
		
		put("id", object.getId());
		put("name", object.getName());

		if(object.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(object.getCreateTime()));
		}
	}
	
}
