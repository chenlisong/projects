package com.yinuo.service;

import com.yinuo.bean.ManagerClass;
import com.yinuo.bean.User;
import com.yinuo.mapper.ManagerClassMapper;
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
public class ManagerClassService {
	
	@Autowired
	private ManagerClassMapper managerClassMapper;
	
	public long insert(User loginUser, ManagerClass managerClass) {
		CommonUtil.setDefaultValue(managerClass);
		
		managerClass.setCreateTime(DateTool.standardSdf.format(new Date()));
		managerClassMapper.insert(managerClass);
		
		return managerClass.getId();
	}
	
	public ManagerClass selectOne(long id) {
		return managerClassMapper.selectOne(id);
	}
	
	public void delete (User loginUser, long id) {
		managerClassMapper.delete(id);
	}
	
	public void update(User loginUser, ManagerClass managerClass) {
		ManagerClass src = selectOne(managerClass.getId());
		
		managerClass = (ManagerClass) MergerUtil.merger(src, managerClass);
		managerClassMapper.update(managerClass);
	}

	public List<ManagerClass> selectListByIds(List<Long> ids) {
		return managerClassMapper.selectListByIds(ids);
	}

	public List<ManagerClass> selectByRole(int role, long schoolId, long classId, int page, int pageSize) {
		return managerClassMapper.selectByRole(role, schoolId, classId, (page-1)*pageSize, pageSize);
	}

	public int countByRole(int role, long schoolId, long classId) {
		return managerClassMapper.countByRole(role, schoolId, classId);
	}

	public List<ManagerClass> selectByManagerId(long managerId) {
		return managerClassMapper.selectByManagerId(managerId);
	}

}
