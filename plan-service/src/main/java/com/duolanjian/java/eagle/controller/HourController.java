package com.duolanjian.java.eagle.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.duolanjian.java.eagle.bean.Hour;
import com.duolanjian.java.eagle.util.DateTool;

@RestController
public class HourController extends BaseController{

	@RequestMapping(value="/{userName}/hours", method=RequestMethod.POST)
    @ResponseBody
    public Object post(@PathVariable String userName,
    		@RequestBody String body){
		Map<String, Object> result = new HashMap<String, Object>();
		
		Hour insert = validation.getObject(body, Hour.class, new String[]{"beginTime", "dayId"});
		insert.setEndTime(DateTool.standardSdf.format(DateTool.addHours(insert.getBeginTime(), 1)));
		
		long id = hourService.insert(insert);
		result.put("id", id);
		return result;
    }

	@RequestMapping(value="/{userName}/hours", method=RequestMethod.PUT)
    @ResponseBody
    public Object put(@PathVariable String userName,
    		@RequestBody String body){
		Map<String, Object> result = new HashMap<String, Object>();
		
		Hour update = validation.getObject(body, Hour.class, new String[]{"id"});

		long id = hourService.update(update);
		result.put("id", id);
		return result;
    }
	
	@RequestMapping(value="/{userName}/hours", method=RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable String userName,
    		@RequestParam(value="dayId", defaultValue="0") long dayId){
		Map<String, Object> result = new HashMap<String, Object>();
		List<Hour> hours = new ArrayList<Hour>();
		if(dayId > 0) {
			hours = hourService.selectByDayId(dayId);
		}
		
		result.put("days", hours);
		return result;
    }
}
