package com.yinuo.view;

import com.yinuo.bean.Classes;
import com.yinuo.bean.Teacher;
import com.yinuo.util.DateTool;

import java.util.HashMap;

public class ClassesView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ClassesView(Classes classes) {
		if(classes == null) {
			return;
		}
		
		put("id", classes.getId());

		put("grade", classes.getGrade());
		put("number", classes.getNumber());
		put("schoolId", classes.getSchoolId());
		
		if(classes.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(classes.getCreateTime()));
		}
	}
}
