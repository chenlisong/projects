package com.yinuo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yinuo.bean.Student;
import com.yinuo.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yinuo.bean.Course;
import com.yinuo.bean.User;
import com.yinuo.service.CourseService;
import com.yinuo.util.DateTool;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;

@RestController
public class CourseController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private CourseService service;

	@Autowired
	private StudentService studentService;

	private Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	@NeedLogin
	@RequestMapping(value="/courses", method=RequestMethod.GET)
    public Object post(User loginUser, @RequestParam(defaultValue="0") long id, @RequestParam(defaultValue="0") long studentId,
					   @RequestParam(defaultValue="0") long classId,
    		@RequestParam(defaultValue="") String beginTime,@RequestParam(defaultValue="") String endTime,
    		@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="20") int pageSize){
		Map<String,Object> result = new HashMap<String, Object>();
		int count = 0;
		List<Course> list = new ArrayList<Course>();
		
		Date begin = null;
		Date end = null;
		try {
			begin = DateTool.standardSdf.parse(beginTime);
			end = DateTool.standardSdf.parse(endTime);
		}catch(Exception e) {
			logger.error("conver time error. ", e);
		}
		
		if(id > 0) {
			list.add(service.selectOne(id));
			count = 1;
		}else if(studentId > 0 && begin!=null && end!=null){
			Student student = studentService.selectOne(studentId);
			if(student.getClassId() > 0) {
				list = service.selectByClassId(student.getClassId(), begin, end);
				count = service.countByClassId(student.getClassId(), begin, end);
			}
		}else if(classId > 0 && begin!=null && end!=null){
			list = service.selectByClassId(classId, begin, end);
			count = service.countByClassId(classId, begin, end);
		}
		
		result.put("data", list);
		result.put("count", count);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/courses", method=RequestMethod.DELETE)
    public Object post(User loginUser, @RequestParam long id){
		Map<String,Object> result = new HashMap<String, Object>();
		service.delete(id);
		result.put("id", id);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/courses", method=RequestMethod.PUT)
    public Object put(User loginUser, @RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		Course course = validation.getObject(body, Course.class, new String[]{"id"});
		
		service.update(loginUser, course);
		result.put("id", course.getId());
		return result;
	}
	
	@NeedLogin
	@RequestMapping(value="/courses", method=RequestMethod.POST)
    public Object get(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		Course course = validation.getObject(body, Course.class, new String[]{"name", "classId", "teacherId"});
		service.insert(loginUser, course);
		result.put("id", course.getId());
        return result;
    }
}
