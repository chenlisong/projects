package com.yinuo.controller;

import com.yinuo.bean.Manager;
import com.yinuo.bean.User;
import com.yinuo.service.ManagerService;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ManagerController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private ManagerService managerService;
	
	@NeedLogin
	@RequestMapping(value="/managers", method=RequestMethod.PUT)
    public Object put(User loginUser, @RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		Manager manager = validation.getObject(body, Manager.class, new String[]{"id"});
		
		managerService.update(loginUser, manager);
		result.put("id", manager.getId());
		return result;
	}
	
	@NeedLogin
	@RequestMapping(value="/managers", method=RequestMethod.POST)
    public Object get(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		Manager manager = validation.getObject(body, Manager.class, new String[]{});
		managerService.insert(loginUser, manager);
		result.put("id", manager.getId());
        return result;
    }
}
