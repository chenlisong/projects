package com.yinuo.view;

import com.yinuo.bean.Classes;
import com.yinuo.bean.Manager;
import com.yinuo.bean.ManagerClass;
import com.yinuo.util.DateTool;

import java.util.HashMap;

public class ManagerClassView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ManagerClassView(ManagerClass managerClass, Manager manager) {
		if(managerClass == null) {
			return;
		}
		
		put("id", managerClass.getId());
		put("managerId", managerClass.getManagerId());
		put("schoolId", managerClass.getSchoolId());
		put("classId", managerClass.getClassId());
		put("role", managerClass.getRole());
		put("createId", managerClass.getCreateId());
		put("manager", manager);

		if(managerClass.getCreateTime() != null) {
			put("createTime", DateTool.standardSdf.format(managerClass.getCreateTime()));
		}
	}
}
