package com.yinuo.view;

import com.yinuo.bean.*;
import com.yinuo.util.DateTool;

import java.util.HashMap;
import java.util.Map;

public class UserChildView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public UserChildView(UserChild object, User parent, User child, Shop shop) {

		if(object == null) {
			return;
		}

		put("id", object.getId());
		put("parentId", object.getParentId());
		put("childId", object.getChildId());

		if(object.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(object.getCreateTime()));
		}

		if(parent != null) {
			put("parentUser", new UserView(parent));
		}

		if(child != null) {
			put("childUser", new UserView(child));
		}

		if(shop != null) {
			put("shop", new ShopView(shop));
		}
	}
}
