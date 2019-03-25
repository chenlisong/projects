package com.yinuo.service;

import com.yinuo.bean.Manager;
import com.yinuo.bean.User;
import com.yinuo.mapper.ManagerMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.DateTool;
import com.yinuo.util.MergerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class ManagerService {
	
	@Autowired
	private ManagerMapper managerMapper;
	
	public long insert(User loginUser, Manager manager) {
		CommonUtil.setDefaultValue(manager);
		
		manager.setCreateTime(DateTool.standardSdf.format(new Date()));
		managerMapper.insert(manager);
		
		return manager.getId();
	}
	
	public Manager selectOne(long id) {
		return managerMapper.selectOne(id);
	}
	
	public void delete (User loginUser, long id) {
		managerMapper.delete(id);
	}
	
	public void update(User loginUser, Manager manager) {
		Manager src = selectOne(manager.getId());
		
		manager = (Manager) MergerUtil.merger(src, manager);
		managerMapper.update(manager);
	}

	public List<Manager> selectListByIds(List<Long> ids) {
		return managerMapper.selectListByIds(ids);
	}

	public Manager selectByUserid(long userId) {
		return managerMapper.selectByUserid(userId);
	}
}
