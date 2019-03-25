package com.yinuo.view;

import com.yinuo.bean.Student;
import com.yinuo.bean.UserStudent;
import com.yinuo.util.DateTool;

import java.util.HashMap;

public class StudentView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public StudentView(Student student, UserStudent us) {
		if(student == null) {
			return;
		}

		put("id", student.getId());
		put("name", student.getName());
		if(student.getBirthday() != null) {
			put("birthday", DateTool.standardSdf.format(student.getBirthday()));
		}
		put("sex", student.getSex());
		put("pic", student.getPic());
		put("classId", student.getClassId());
		put("managerId", student.getManagerId());
		put("schoolId", student.getSchoolId());

		if(student.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(student.getCreateTime()));
		}

		if(us != null) {
			put("userStudent", us);
		}
	}
	
}
