package com.duolanjian.java.eagle.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duolanjian.java.eagle.bean.Day;
import com.duolanjian.java.eagle.exception.InvalidArgumentException;
import com.duolanjian.java.eagle.mapper.DayMapper;
import com.duolanjian.java.eagle.util.CommonUtil;
import com.duolanjian.java.eagle.util.DateTool;
import com.duolanjian.java.eagle.util.MergerUtil;

@Service("dayService")
public class DayService {

	@Autowired
	private DayMapper dayMapper;
	
	public long update(Day day) {
		//检查内容是否正确
		checkUpdate(day);
		CommonUtil.setDefaultValue(day);
		Day dayDb = dayMapper.selectOne(day.getId());
		day = (Day)MergerUtil.merger(dayDb, day);
		dayMapper.update(day);
		return day.getId();
	}
	
	public long insert(Day day) {
		checkInsert(day);
		CommonUtil.setDefaultValue(day);
		dayMapper.insert(day);
		return day.getId();
	}

	private void checkInsert(Day day) {
		if(day == null) {
			throw new InvalidArgumentException("插入无效的时间计划");
		}
		
		if(day.getTime() == null) {
			throw new InvalidArgumentException("需要填写时间");
		}
		
		if(day.getUserName() == null || day.getUserName().isEmpty()) {
			throw new InvalidArgumentException("需要填写用户名称");
		}
		
		Date beginTime = DateTool.getBegin(day.getTime());
		Date endTime = DateTool.getEnd(day.getTime());
		List<Day> days = selectByTime(beginTime, endTime, day.getUserName());
		if(days != null && days.size() > 0) {
			throw new InvalidArgumentException("已存在的数据");
		}
	}

	private void checkUpdate(Day update) {
		if(update == null) {
			throw new InvalidArgumentException("修改无效的时间计划");
		}
		
		if(update.getId() == null || update.getId().longValue() <= 0) {
			throw new InvalidArgumentException("修改时需要填写主键");
		}
		
	}
	
	public List<Day> selectByTime(Date beginTime, Date endTime, String userName) {
		return dayMapper.selectByTime(beginTime, endTime, userName);
	}
	
}
