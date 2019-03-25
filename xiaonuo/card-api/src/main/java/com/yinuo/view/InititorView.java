package com.yinuo.view;

import com.yinuo.bean.Inititor;
import com.yinuo.util.DateTool;

import java.util.HashMap;

public class InititorView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public InititorView(Inititor object, Inititor parent) {
		if(object == null) {
			return;
		}
		
		put("id", object.getId());
		put("card", object.getCard());
		put("userId", object.getUserId());
		put("parentId", object.getParentId());
		put("formId", object.getFormId());
		put("readState", object.getReadState());
		put("state", object.getState());
		if(object.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(object.getCreateTime()));
		}

		if(parent != null) {
			put("parent", new InititorView(parent, null));
		}

	}
	
}
