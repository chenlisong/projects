package com.yinuo.service;

import com.yinuo.bean.Constant.UserStudentRelationship;
import com.yinuo.bean.Student;
import com.yinuo.bean.User;
import com.yinuo.bean.UserStudent;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.mapper.StudentMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.DateTool;
import com.yinuo.util.MergerUtil;
import com.yinuo.view.StudentView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(rollbackFor = Exception.class)
@Service
public class StudentService {
	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private UserStudentService userStudentService;
	
	public long insert(User loginUser, Student student) {
		CommonUtil.setDefaultValue(student);
		
		student.setCreateTime(DateTool.standardSdf.format(new Date()));
		student.setManagerId(loginUser.getManager().getId());
		studentMapper.insert(student);
		
		UserStudent us = new UserStudent(null, student.getId(), loginUser.getId(), UserStudentRelationship.create, new Date());
		userStudentService.insert(loginUser, us);
		
		return student.getId();
	}
	
	public Student selectOne(long id) {
		return studentMapper.selectOne(id);
	}
	
	public void delete (User loginUser, long id) {
		Student student = studentMapper.selectOne(id);
		
		List<UserStudent> uss = userStudentService.selectListByUserid(loginUser.getId(), id, 1, Integer.MAX_VALUE);
		boolean flag = true;
		if(uss != null && uss.size() > 0) {
			for(UserStudent us: uss) {
				if(us.getStudentId().longValue() == student.getId().longValue() 
						&& us.getRelationship().intValue() == UserStudentRelationship.create) {
					flag = false;
				}
			}
		}
		
		if(flag) {
			throw new InvalidArgumentException("不是创建人，不能删除学生信息");
		}
		studentMapper.delete(id);
	}
	
	//只能自己修改自己的数据场景
	public void update(User loginUser, Student student) {
		Student src = selectOne(student.getId());
		
		//check
		List<UserStudent> uss = userStudentService.selectListByUserid(loginUser.getId(), student.getId(), 1, Integer.MAX_VALUE);
		boolean flag = true;
		if(uss != null && uss.size() > 0) {
			for(UserStudent us: uss) {
				if(us.getStudentId().longValue() == student.getId().longValue() 
						&& us.getRelationship().intValue() == UserStudentRelationship.create) {
					flag = false;
				}
			}
		}
		if(flag) {
			throw new InvalidArgumentException("不是创建人，不能修改学生信息");
		}
		
		student = (Student) MergerUtil.merger(src, student);
		studentMapper.update(student);
	}

	public List<Student> selectListByIds(List<Long> ids) {
		return studentMapper.selectListByIds(ids);
	}


	public List<Student> selectByName(String name) {
		return studentMapper.selectByName(name);
	}

	public int countByName(String name) {
		return studentMapper.countByName(name);
	}

	public List<StudentView> convert2View(List<Student> students, Map<Long, UserStudent> ussMap) {
		if(ussMap == null) {
			ussMap = new HashMap<Long, UserStudent>();
		}
		List<StudentView> view = new ArrayList<StudentView>();
		if(students != null && !students.isEmpty()) {
			for(Student student: students) {
				view.add(new StudentView(student, ussMap.get(student.getId())));
			}
		}
		return view;
	}
}
