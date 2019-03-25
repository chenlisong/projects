package com.yinuo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yinuo.bean.Classes;
import com.yinuo.bean.User;
import com.yinuo.service.ClassesService;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;

@RestController
public class ClassesController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private ClassesService service;
	
	@NeedLogin
	@RequestMapping(value="/classes", method=RequestMethod.GET)
    public Object post(User loginUser, @RequestParam(defaultValue="0") long id,@RequestParam(defaultValue="1") int page,
    		@RequestParam(defaultValue="20") int pageSize){
		Map<String,Object> result = new HashMap<String, Object>();
		int count = 0;
		List<Classes> list = new ArrayList<Classes>();
		
		if(id > 0) {
			list.add(service.selectOne(id));
			count = 1;
		}else if(page > 0 && pageSize > 0){
			list = service.selectListByPage(page, pageSize);
			count = service.countListByPage();
		}
		
		result.put("data", list);
		result.put("count", count);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/classes", method=RequestMethod.DELETE)
    public Object post(User loginUser, @RequestParam long id){
		Map<String,Object> result = new HashMap<String, Object>();
		service.delete(id);
		result.put("id", id);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/classes", method=RequestMethod.PUT)
    public Object put(User loginUser, @RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		Classes student = validation.getObject(body, Classes.class, new String[]{"id"});
		
		service.update(loginUser, student);
		result.put("id", student.getId());
		return result;
	}
	
	@NeedLogin
	@RequestMapping(value="/classes", method=RequestMethod.POST)
    public Object get(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		Classes classes = validation.getObject(body, Classes.class, new String[]{"teacherId", "grade", "number", "center"});
		service.insert(loginUser, classes);
		result.put("id", classes.getId());
        return result;
    }
}
