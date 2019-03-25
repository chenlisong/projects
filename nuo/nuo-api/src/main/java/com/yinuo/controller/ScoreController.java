package com.yinuo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yinuo.bean.Score;
import com.yinuo.bean.User;
import com.yinuo.service.ScoreService;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;

@RestController
public class ScoreController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private ScoreService service;
	
	@NeedLogin
	@RequestMapping(value="/scores", method=RequestMethod.GET)
    public Object post(User loginUser, @RequestParam(defaultValue="0") long id, @RequestParam(defaultValue="0") long studentId,
    		@RequestParam(defaultValue="0") int type, @RequestParam(defaultValue="1") int page, 
    		@RequestParam(defaultValue="20") int pageSize){
		Map<String,Object> result = new HashMap<String, Object>();
		
		List<Score> scores = new ArrayList<Score>();
		int count = 0;
		if(id > 0) {
			Score score = service.selectOne(id);
			scores.add(score);
			count = 1;
		}else if(studentId > 0 && page > 0 && pageSize > 0) {
			scores = service.selectByStudentId(studentId, type, page, pageSize);
			count = service.countByStudentId(studentId, type);
		}
		result.put("data", scores);
		result.put("count", count);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/scores", method=RequestMethod.DELETE)
    public Object post(User loginUser, @RequestParam long id){
		Map<String,Object> result = new HashMap<String, Object>();
		service.delete(loginUser, id);
		result.put("id", id);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/scores", method=RequestMethod.PUT)
    public Object put(User loginUser, @RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		Score score = validation.getObject(body, Score.class, new String[]{"id"});
		
		service.update(loginUser, score);
		result.put("id", score.getId());
		return result;
	}
	
	@NeedLogin
	@RequestMapping(value="/scores", method=RequestMethod.POST)
    public Object get(User loginUser, @RequestBody String body){
		Map<String, Object> result=new HashMap<String, Object>();
		Score score = validation.getObject(body, Score.class, new String[]{});
		service.insert(loginUser, score);
		result.put("id", score.getId());
        return result;
    }
}
