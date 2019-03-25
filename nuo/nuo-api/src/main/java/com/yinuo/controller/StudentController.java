package com.yinuo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yinuo.bean.Constant;
import com.yinuo.validation.NeedRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yinuo.bean.Student;
import com.yinuo.bean.User;
import com.yinuo.bean.UserStudent;
import com.yinuo.service.StudentService;
import com.yinuo.service.UserStudentService;
import com.yinuo.util.CommonUtil;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;

@RestController
public class StudentController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private UserStudentService userStudentService;
	
	@NeedLogin
	@RequestMapping(value="/students", method=RequestMethod.GET)
    public Object post(User loginUser, @RequestParam(defaultValue="") String name, @RequestParam(defaultValue="1") int page,
    		@RequestParam(defaultValue="20") int pageSize){
		Map<String,Object> result = new HashMap<String, Object>();
		List<Student> students = null;
		Map<Long, UserStudent> ussMap = null;
		int count = 0;

		if(name != null && !name.isEmpty()) {
			students = studentService.selectByName(name);
			count = studentService.countByName(name);
		}else if(page > 0 && pageSize > 0){
			List<UserStudent> uss = userStudentService.selectListByUserid(loginUser.getId(), 0, page, pageSize);
			count = userStudentService.countListByUserid(loginUser.getId(), 0);

			if(uss != null && uss.size() > 0) {
				List<Long> studentIds = CommonUtil.entity(uss, "studentId", Long.class);
				ussMap = CommonUtil.entityMap(uss, "studentId", Long.class);
				students = studentService.selectListByIds(studentIds);
			}
		}

		if(students == null) {
			students = new ArrayList<Student>();
			count = 0;
		}

		result.put("data", studentService.convert2View(students, ussMap));
		result.put("count", count);
		return result;
    }
	
	@NeedRole(Constant.Role.Teacher)
	@RequestMapping(value="/students", method=RequestMethod.DELETE)
    public Object post(User loginUser, @RequestParam long id){
		Map<String,Object> result = new HashMap<String, Object>();
		studentService.delete(loginUser, id);
		result.put("id", id);
		return result;
    }

	@NeedRole(Constant.Role.Teacher)
	@RequestMapping(value="/students", method=RequestMethod.PUT)
    public Object put(User loginUser, @RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		Student student = validation.getObject(body, Student.class, new String[]{"id"});
		
		studentService.update(loginUser, student);
		result.put("id", student.getId());
		return result;
	}

	@NeedRole(Constant.Role.Teacher)
	@RequestMapping(value="/students", method=RequestMethod.POST)
	public Object get(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		Student student = validation.getObject(body, Student.class, new String[]{});
		studentService.insert(loginUser, student);
		result.put("id", student.getId());
        return result;
    }
}
