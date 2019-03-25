package com.yinuo.controller;

import com.yinuo.bean.*;
import com.yinuo.service.MoneyRecordService;
import com.yinuo.service.OrderService;
import com.yinuo.service.UserShopService;
import com.yinuo.util.DateTool;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private OrderService service;

	@Autowired
	private MoneyRecordService moneyRecordService;

	@NeedLogin
	@RequestMapping(value="/orders", method=RequestMethod.GET)
	public Object get(User loginUser, @RequestParam(name="shopId", defaultValue = "0") int shopId, @RequestParam(name="userId", defaultValue = "0") int userId,
					  @RequestParam(name="serverId", defaultValue = "0") int serverId, @RequestParam(name="state", defaultValue = "0") int state){

		Map<String, Object> result=new HashMap<String, Object>();
		List<Order> orders = null;

		if(shopId > 0) {
			orders = service.selectByShopId(shopId, serverId, state);
		}else if(userId > 0){

			orders = service.selectByUserid(userId, state);
		}
		result.put("data", service.convert(orders));
		result.put("count", orders.size());
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/orders/create", method=RequestMethod.POST)
	public Object post(User loginUser, @RequestBody String body){

		Map<String, Object> result = new HashMap<String, Object>();

		Order object = validation.getObject(body, Order.class, new String[]{"shopId", "userId", "serviceTypeId"});

		int id = service.insert(loginUser, object);
		result.put("id", id);
		
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/orders/work", method=RequestMethod.POST)
	public Object postWork(User loginUser, @RequestBody String body){

		Map<String, Object> result = new HashMap<String, Object>();

		Order object = validation.getObject(body, Order.class, new String[]{"id", "serverId"});

		object.setState(Constant.OrderState.Work);
		object.setServerBeginTime(DateTool.standardSdf.format(new Date()));

		service.update(loginUser, object);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/orders/done", method=RequestMethod.POST)
	public Object postDone(User loginUser, @RequestBody String body){

		Map<String, Object> result = new HashMap<String, Object>();

		Order object = validation.getObject(body, Order.class, new String[]{"id", "payState"});

		object.setState(Constant.OrderState.Done);
		object.setServerEndTime(DateTool.standardSdf.format(new Date()));

		service.update(loginUser, object);

		//增加消费记录
		Order data = service.selectById(object.getId());
		MoneyRecord mr = new MoneyRecord();
		mr.setMoney(data.getPayMoney());
		mr.setServerId(data.getServerId());
		mr.setShopId(data.getShopId());
		mr.setType(Constant.MoneyRecordType.Out);
		mr.setUserId(data.getUserId());
		moneyRecordService.insert(mr);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/orders/cancel", method=RequestMethod.POST)
	public Object postCancel(User loginUser, @RequestBody String body){

		Map<String, Object> result = new HashMap<String, Object>();

		Order object = validation.getObject(body, Order.class, new String[]{"id", "serverId"});

		object.setState(Constant.OrderState.Cancel);

		String now = DateTool.standardSdf.format(new Date());
		object.setServerBeginTime(now);
		object.setServerEndTime(now);

		service.update(loginUser, object);

		return result;
	}

	@NeedLogin
	@RequestMapping(value="/orders/money", method=RequestMethod.POST)
	public Object postMoney(User loginUser, @RequestBody String body){

		Map<String, Object> result = new HashMap<String, Object>();

		Order object = validation.getObject(body, Order.class, new String[]{"id", "payMoney"});

		service.update(loginUser, object);

		return result;
	}

}
