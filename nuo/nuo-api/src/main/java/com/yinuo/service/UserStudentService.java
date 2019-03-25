package com.yinuo.service;

import java.util.Date;
import java.util.List;

import com.yinuo.bean.Student;
import com.yinuo.bean.User;
import com.yinuo.exception.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinuo.bean.UserStudent;
import com.yinuo.mapper.UserStudentMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.DateTool;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserStudentService {
	
	@Autowired
	private UserStudentMapper userStudentMapper;

	@Autowired
	private StudentService studentService;
	
	public long insert(User loginUser, UserStudent userStudent) {

		int count = countListByUserid(loginUser.getId(), userStudent.getStudentId());
		if(count > 0) {
			throw new InvalidArgumentException("你已经绑定过该学生");
		}

		Student student = studentService.selectOne(userStudent.getStudentId());
		if(student == null) {
			throw new InvalidArgumentException("找不到的学生");
		}

		userStudent.setSchoolId(student.getSchoolId());
		userStudent.setClassId(student.getClassId());
		CommonUtil.setDefaultValue(userStudent);
		
		userStudent.setCreateTime(DateTool.standardSdf.format(new Date()));
		userStudentMapper.insert(userStudent);
		return userStudent.getId();
	}
	
	public List<UserStudent> selectListByPage(int page, int pageSize) {
		return userStudentMapper.selectListByPage(pageSize, (page-1)*pageSize);
	}
	
	public UserStudent selectOne(long id) {
		return userStudentMapper.selectOne(id);
	}
	
	public void delete (User loginUser, long id) {

		UserStudent userStudent = selectOne(id);
		if(userStudent.getUserId().longValue() != loginUser.getId().longValue()) {
			throw new InvalidArgumentException("无法删除不是自己的数据");
		}
		userStudentMapper.delete(id);
	}
	
	public List<UserStudent> selectListByUserid(long userId, long studentId, int page, int pageSize) {
		return userStudentMapper.selectListByUserid(userId, studentId, pageSize, (page-1)*pageSize);
	}
	
	
	public int countListByUserid(long userId, long studentId) {
		return userStudentMapper.countListByUserid(userId, studentId);
	}
	
}
