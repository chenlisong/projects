package com.duolanjian.java.eagle.view;

import java.util.HashMap;

import com.duolanjian.java.eagle.bean.User;
import com.duolanjian.java.eagle.util.DateTool;

public class UserView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public UserView(User user) {
		put("id", user.getId());
		put("userName", user.getUserName());
		put("mobile", user.getMobile());
		put("qq", user.getQq());
		put("weixin", user.getWeixin());
		put("birthday", user.getBirthday());
		put("sex", user.getSex());
		put("createTime", DateTool.standardSdf.format(user.getCreateTime()));
	}
	
}
