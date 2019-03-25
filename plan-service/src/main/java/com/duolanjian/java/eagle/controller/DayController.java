package com.duolanjian.java.eagle.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.duolanjian.java.eagle.bean.Day;
import com.duolanjian.java.eagle.exception.InvalidArgumentException;
import com.duolanjian.java.eagle.util.DateTool;

@RestController
public class DayController extends BaseController{

	@RequestMapping(value="/{userName}/days", method=RequestMethod.POST)
    @ResponseBody
    public Object post(@PathVariable String userName,
    		@RequestBody String body){
		Map<String, Object> result = new HashMap<String, Object>();
		
		Day update = validation.getObject(body, Day.class, new String[]{"time"});
		update.setUserName(userName);
		
		long id = dayService.insert(update);
		result.put("id", id);
		return result;
    }

	@RequestMapping(value="/{userName}/days", method=RequestMethod.PUT)
    @ResponseBody
    public Object put(@PathVariable String userName,
    		@RequestBody String body){
		Map<String, Object> result = new HashMap<String, Object>();
		
		Day update = validation.getObject(body, Day.class, new String[]{"id"});
		update.setUserName(userName);
		
		long id = dayService.update(update);
		result.put("id", id);
		return result;
    }
	
	@RequestMapping(value="/{userName}/days", method=RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable String userName,
    		@RequestParam(value="time", defaultValue="") String time,
    		@RequestParam(value="beginTime", defaultValue="") String beginTime,
    		@RequestParam(value="endTime", defaultValue="") String endTime){
		Map<String, Object> result = new HashMap<String, Object>();
		List<Day> days = new ArrayList<Day>();
		try{
			if(!time.isEmpty()) {
				Date begin = DateTool.standardSdf.parse(time + " 00:00:00");
				Date end = DateTool.standardSdf.parse(time + " 23:59:59");
				days = dayService.selectByTime(begin, end, userName);
			}else if(!beginTime.isEmpty() && !endTime.isEmpty()) {
				Date begin = DateTool.standardSdf.parse(beginTime + " 00:00:00");
				Date end = DateTool.standardSdf.parse(endTime + " 23:59:59");
				days = dayService.selectByTime(begin, end, userName);
			}
		}catch (Exception e){
			throw new InvalidArgumentException("exception:", e);
		}
		
		result.put("days", days);
		return result;
    }
}
