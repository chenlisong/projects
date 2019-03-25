package com.yinuo.service;

import com.yinuo.bean.*;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.mapper.ServerMapper;
import com.yinuo.mapper.ServiceTypeMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.view.ServerView;
import com.yinuo.view.ServiceTypeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ServiceTypeService {
	
	@Autowired
	private ServiceTypeMapper mapper;

	@Autowired
	private ShopService shopService;

	public ServiceType selectById(int id) {
		return mapper.selectOne(id);
	}

	public List<ServiceType> selectByIds(List<Integer> ids) {
		return mapper.selectListByIds(ids);
	}
	
	public int insert(User loginUser, ServiceType serviceType) {

		if(shopService.selectById(serviceType.getShopId()) == null) {
			throw new InvalidArgumentException("找不到的店铺");
		}

		CommonUtil.setDefaultValue(serviceType);
		mapper.insert(serviceType);
		return serviceType.getId();
	}

	public void update(User loginUser, ServiceType serviceType) {

		mapper.update(serviceType);
	}

	public List<ServiceType> selectByShopId(int shopId) {
		return mapper.selectByShopId(shopId);
	}

	public void delete(User loginUser, int id) {
		mapper.delete(id);
	}

	public List<ServiceTypeView> convert(List<ServiceType> list) {
		List<ServiceTypeView> views = new ArrayList<ServiceTypeView>();

		if(list == null || list.isEmpty()) {
			return views;
		}

		List<Integer> shopIds = CommonUtil.entity(list, "shopId", Integer.class);

		List<Shop> shops = shopService.selectByIds(shopIds);

		Map<Integer, Shop> shopMap = CommonUtil.entityMap(shops, "id", Integer.class);

		for(ServiceType object: list) {
			views.add(new ServiceTypeView(
					object, shopMap.get(object.getShopId())
			));
		}

		return views;
	}

}
