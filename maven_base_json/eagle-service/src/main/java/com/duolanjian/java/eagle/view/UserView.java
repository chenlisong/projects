package com.duolanjian.java.eagle.view;

import java.util.HashMap;

import com.duolanjian.java.eagle.bean.User;
import com.duolanjian.java.eagle.util.DateTool;

public class UserView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public UserView(User user) {
		put("id", user.getId());
		put("mobile", user.getMobile());
		put("createTime", DateTool.standardSdf.format(user.getCreateTime()));
	}
	
}
