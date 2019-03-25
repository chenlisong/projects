package com.yinuo.view;

import java.util.HashMap;

import com.yinuo.bean.User;
import com.yinuo.util.DateTool;

public class UserView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public UserView(User user, int version) {
		if(user == null) {
			return;
		}
		
		put("id", user.getId());
		put("wechatOpenid", user.getWechatOpenid());


		put("version", version);
		if(user.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(user.getCreateTime()));
		}

	}
	
}
