package com.yinuo.controller;

import com.yinuo.bean.ServiceBind;
import com.yinuo.bean.ServiceType;
import com.yinuo.bean.User;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.service.ServiceBindService;
import com.yinuo.service.ServiceTypeService;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ServiceBindController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private ServiceBindService service;

	@NeedLogin
	@RequestMapping(value="/serviceBinds", method=RequestMethod.GET)
	public Object get(User loginUser, @RequestParam(name="shopId", defaultValue = "0") int shopId, @RequestParam(name="serverId", defaultValue = "0") int serverId,
					  @RequestParam(name="serviceTypeId", defaultValue = "0") int serviceTypeId){
		Map<String, Object> result=new HashMap<String, Object>();

		List<ServiceBind> list = null;
		if(shopId > 0) {
			list = service.selectByShopId(shopId, serviceTypeId);
		}else if(serverId > 0) {
			list = service.selectByServerId(serverId);
		}else {
			throw new InvalidArgumentException("参数异常");
		}

		result.put("data", service.convert(list));
		result.put("count", list.size());

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/serviceBinds", method=RequestMethod.POST)
	public Object post(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		ServiceBind object = validation.getObject(body, ServiceBind.class, new String[] {});
		int id = service.insert(loginUser, object);
		result.put("id", id);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/serviceBinds", method=RequestMethod.PUT)
	public Object put(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		ServiceBind object = validation.getObject(body, ServiceBind.class, new String[] {"id"});
		service.update(loginUser, object);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/serviceBinds", method=RequestMethod.DELETE)
	public Object delete(User loginUser, @RequestParam(name="id") int id){
		Map<String, Object> result=new HashMap<String, Object>();

		service.delete(loginUser, id);
		return result;
	}
}
