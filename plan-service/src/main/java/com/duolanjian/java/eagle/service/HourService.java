package com.duolanjian.java.eagle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duolanjian.java.eagle.bean.Hour;
import com.duolanjian.java.eagle.exception.InvalidArgumentException;
import com.duolanjian.java.eagle.mapper.HourMapper;
import com.duolanjian.java.eagle.util.CommonUtil;
import com.duolanjian.java.eagle.util.MergerUtil;

@Service("hourService")
public class HourService {

	@Autowired
	private HourMapper hourMapper;
	
	public long insert(Hour hour) {
		checkInsert(hour);
		hour.setComplete(1);
		CommonUtil.setDefaultValue(hour);
		hourMapper.insert(hour);
		return hour.getId();
	}
	
	public long update(Hour update) {
		checkUpdate(update);
		Hour hourDB = hourMapper.selectOne(update.getId());
		MergerUtil.merger(hourDB, update);
		hourMapper.update(update);
		return update.getId();
	}
	
	public Hour selectOne(long id) {
		return hourMapper.selectOne(id);
	}

	public List<Hour> selectByDayId(long dayId) {
		return hourMapper.selectByDayId(dayId);
	}
	
	private void checkInsert(Hour insert) {
		if(insert == null) {
			throw new InvalidArgumentException("插入无效的时间计划");
		}
		
		if(insert.getBeginTime() == null || insert.getEndTime() == null) {
			throw new InvalidArgumentException("需要填写时间");
		}
		
		if(insert.getDayId() == null || insert.getDayId().longValue() <= 0) {
			throw new InvalidArgumentException("需要填写属于哪个天计划");
		}
		
		if(insert.getId() != null && insert.getId().longValue() > 0) {
			throw new InvalidArgumentException("已存在的小时计划");
		}
		
		
		
	}

	private void checkUpdate(Hour update) {
		if(update == null) {
			throw new InvalidArgumentException("修改无效的小时计划");
		}
		
		if(update.getId() == null || update.getId().longValue() <= 0) {
			throw new InvalidArgumentException("修改时需要填写主键");
		}
		
	}
	
}
