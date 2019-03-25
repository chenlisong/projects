package com.yinuo.service;

import com.yinuo.bean.ScoreBatch;
import com.yinuo.bean.User;
import com.yinuo.mapper.ScoreBatchMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.DateTool;
import com.yinuo.util.MergerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional(rollbackFor = Exception.class)
@Service
public class ScoreBatchService {
	
	@Autowired
	private ScoreBatchMapper scoreBatchMapper;
	
	public long insert(User loginUser, ScoreBatch scoreBatch) {
		CommonUtil.setDefaultValue(scoreBatch);

		scoreBatch.setCreateTime(DateTool.standardSdf.format(new Date()));
		scoreBatchMapper.insert(scoreBatch);
		return scoreBatch.getId();
	}
	
	public ScoreBatch selectOne(long id) {
		return scoreBatchMapper.selectOne(id);
	}
	
	public void delete (User loginUser, long id) {
		scoreBatchMapper.delete(id);
	}
	
	public void update(User loginUser, ScoreBatch scoreBatch) {
		ScoreBatch src = selectOne(scoreBatch.getId());

		scoreBatch = (ScoreBatch) MergerUtil.merger(src, scoreBatch);
		scoreBatchMapper.update(scoreBatch);
	}
}
