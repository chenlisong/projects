package com.yinuo.controller;

import com.yinuo.bean.MoneyRecord;
import com.yinuo.bean.ServiceBind;
import com.yinuo.bean.User;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.service.MoneyRecordService;
import com.yinuo.service.ServiceBindService;
import com.yinuo.util.DateTool;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MoneyRecordController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private MoneyRecordService service;

	@NeedLogin
	@RequestMapping(value="/moneyRecords", method=RequestMethod.GET)
	public Object get(User loginUser, @RequestParam(name="shopId", defaultValue = "0") int shopId, @RequestParam(name="serverId", defaultValue = "0") int serverId,
					  @RequestParam(name="userId", defaultValue = "0") int userId, @RequestParam(name="type", defaultValue = "0") int type){
		Map<String, Object> result=new HashMap<String, Object>();

		List<MoneyRecord> list = null;
		if(shopId > 0) {
			list = service.selectByShopId(shopId, serverId, type);
		}else if(userId > 0) {
			list = service.selectByUserId(userId, type);
		}else {
			throw new InvalidArgumentException("参数异常");
		}

		result.put("data", service.convert(list));
		result.put("count", list.size());

		return result;
	}


	@NeedLogin
	@RequestMapping(value="/moneyRecords/count", method=RequestMethod.GET)
	public Object counts(User loginUser, @RequestParam(name="shopId") int shopId, @RequestParam(name="serverId") int serverId,
						 @RequestParam(name="type") int type, @RequestParam(name="begin")String dateBegin,
						 @RequestParam(name="end")String dateEnd){
		Map<String, Object> result=new HashMap<String, Object>();

		Date begin, end = null;
		try {
			begin = DateTool.standardSdf.parse(dateBegin);
			end = DateTool.standardSdf.parse(dateEnd);
		}catch (Exception e) {
			throw new InvalidArgumentException(String.format("传入时间格式错误。要求：yyyy-MM-dd HH:mm:ss，传入值begin: %s, end:%s", dateBegin, dateEnd));
		}

		double sumMoney = service.sumMoneyByShopId(shopId, serverId, type, begin, end);
		double sumGiftMoney = service.sumGiftMoneyByShopId(shopId, serverId, type, begin, end);

		result.put("sumMoney", sumMoney);
		result.put("sumGiftMoney", sumGiftMoney);

		return result;
	}

}
