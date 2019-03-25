package com.yinuo.controller;

import com.yinuo.bean.Constant.UserStudentRelationship;
import com.yinuo.bean.User;
import com.yinuo.bean.UserStudent;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.service.UserStudentService;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserStudentController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private UserStudentService userStudentService;

	@NeedLogin
	@RequestMapping(value="/userStudents", method=RequestMethod.POST)
	public Object get(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		UserStudent userStudent = validation.getObject(body, UserStudent.class, new String[]{"studentId", "relationship"});
		userStudent.setUserId(loginUser.getId());

		userStudentService.insert(loginUser, userStudent);
		result.put("id", userStudent.getId());
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/userStudents", method=RequestMethod.DELETE)
	public Object delete(User loginUser, @RequestParam(name="id", defaultValue = "0")long id){
		Map<String, Object> result=new HashMap<String, Object>();

		userStudentService.delete(loginUser, id);
		result.put("id", id);
		return result;
	}
}
