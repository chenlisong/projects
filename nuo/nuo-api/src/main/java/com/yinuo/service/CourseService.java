package com.yinuo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinuo.bean.Course;
import com.yinuo.bean.User;
import com.yinuo.mapper.CourseMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.DateTool;
import com.yinuo.util.MergerUtil;

@Transactional(rollbackFor = Exception.class)
@Service
public class CourseService {
	
	@Autowired
	private CourseMapper courseMapper;
	
	public long insert(User loginUser, Course course) {
		CommonUtil.setDefaultValue(course);
		
		course.setCreateTime(DateTool.standardSdf.format(new Date()));
		courseMapper.insert(course);
		return course.getId();
	}
	
	public Course selectOne(long id) {
		return courseMapper.selectOne(id);
	}
	
	public void delete (long id) {
		courseMapper.delete(id);
	}
	
	public void update(User loginUser, Course course) {
		Course src = selectOne(course.getId());
		
		course = (Course) MergerUtil.merger(src, course);
		courseMapper.update(course);
	}
	
	public List<Course> selectListByIds(List<Long> ids) {
		return courseMapper.selectListByIds(ids);
	}
	
	public List<Course> selectListByPage(int page, int pageLength) {
		return courseMapper.selectListByPage(pageLength, (page-1)*pageLength);
	}
	
	public List<Course> selectByClassId(long classId, Date beginTime, Date endTime) {
		return courseMapper.selectByClassId(classId, beginTime, endTime);
	}

	public int countByClassId(long classId, Date beginTime, Date endTime) {
		return courseMapper.countByClassId(classId, beginTime, endTime);
	}
	
}
