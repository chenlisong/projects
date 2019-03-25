package com.yinuo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yinuo.bean.Score;
import com.yinuo.bean.User;
import com.yinuo.mapper.ScoreMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.DateTool;
import com.yinuo.util.MergerUtil;

@Transactional(rollbackFor = Exception.class)
@Service
public class ScoreService {
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	public long insert(User loginUser, Score score) {
		CommonUtil.setDefaultValue(score);
		
		score.setCreateTime(DateTool.standardSdf.format(new Date()));
		scoreMapper.insert(score);
		return score.getId();
	}
	
	public Score selectOne(long id) {
		return scoreMapper.selectOne(id);
	}
	
	public void delete (User loginUser, long id) {
		scoreMapper.delete(id);
	}
	
	public void update(User loginUser, Score score) {
		Score src = selectOne(score.getId());
		
		score = (Score) MergerUtil.merger(src, score);
		scoreMapper.update(score);
	}
	
	public List<Score> selectListByIds(List<Long> ids) {
		return scoreMapper.selectListByIds(ids);
	}
	
	public List<Score> selectByStudentId(long studentId, int type, int page, int pageSize) {
		return scoreMapper.selectByStudentId(studentId, type, pageSize, (page-1) * pageSize);
	}
	
	public int countByStudentId(long studentId, int type) {
		return scoreMapper.countByStudentId(studentId, type);
	}
	
}
