package com.yinuo.controller;

import com.yinuo.bean.*;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.service.ManagerClassService;
import com.yinuo.service.ManagerService;
import com.yinuo.util.CommonUtil;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import com.yinuo.view.ManagerClassView;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ManagerClassController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private ManagerClassService managerClassService;
	
	@Autowired
	private ManagerService managerService;
	
	@NeedLogin
	@RequestMapping(value="/managerClass", method=RequestMethod.GET)
    public Object post(User loginUser, @RequestParam(defaultValue="0") long schoolId, @RequestParam(defaultValue="0") long classId,
					@RequestParam(defaultValue="0") int role, @RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="20") int pageSize){
		Map<String,Object> result = new HashMap<String, Object>();


		if(role <= 0) {
			throw new InvalidArgumentException("角色ID必传");
		}

		List<ManagerClass> list = managerClassService.selectByRole(role, schoolId, classId, page, pageSize);
		int count = managerClassService.countByRole(role, schoolId, classId);
		List<ManagerClassView> views = new ArrayList<ManagerClassView>();

		if (list!= null && list.size() > 0) {
			List<Long> managerIds = CommonUtil.entity(list, "managerId", Long.class);
			List<Manager> managers = managerService.selectListByIds(managerIds);
			Map<Long, Manager> managerMap = CommonUtil.entityMap(managers, "id", Long.class);
			for(ManagerClass unit : list) {
				views.add(new ManagerClassView(unit, managerMap.get(unit.getManagerId())));
			}
		}
		result.put("data", views);
		result.put("count", count);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/managerClass", method=RequestMethod.DELETE)
    public Object post(User loginUser, @RequestParam long id){
		Map<String,Object> result = new HashMap<String, Object>();
		managerClassService.delete(loginUser, id);
		result.put("id", id);
		return result;
    }

	@NeedLogin
	@RequestMapping(value="/managerClass", method=RequestMethod.POST)
	public Object get(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		ManagerClass managerClass = validation.getObject(body, ManagerClass.class, new String[]{});
		managerClassService.insert(loginUser, managerClass);
		result.put("id", managerClass.getId());
        return result;
    }
}
