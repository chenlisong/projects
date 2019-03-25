package com.yinuo.service;

import com.yinuo.bean.*;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.mapper.ServiceBindMapper;
import com.yinuo.mapper.ServiceTypeMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.view.ServiceBindView;
import com.yinuo.view.ServiceTypeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ServiceBindService {
	
	@Autowired
	private ServiceBindMapper mapper;

	@Autowired
	private ShopService shopService;

	@Autowired
	private ServerService serverService;

	@Autowired
	private ServiceTypeService serviceTypeService;
	
	public int insert(User loginUser, ServiceBind object) {

		if(shopService.selectById(object.getShopId()) == null) {
			throw new InvalidArgumentException("找不到的店铺");
		}

		if(serverService.selectById(object.getServerId()) == null) {
			throw new InvalidArgumentException("找不到的服务者");
		}

		if(serviceTypeService.selectById(object.getServiceTypeId()) == null) {
			throw new InvalidArgumentException("找不到的服务类型");
		}

		CommonUtil.setDefaultValue(object);
		mapper.insert(object);
		return object.getId();
	}

	public void update(User loginUser, ServiceBind object) {

		mapper.update(object);
	}

	public List<ServiceBind> selectByShopId(int shopId, int serviceTypeId) {
		return mapper.selectByShopId(shopId, serviceTypeId);
	}

	public List<ServiceBind> selectByServerId(int serverId) {
		return mapper.selectByServerId(serverId);
	}

	public void delete(User loginUser, int id) {
		mapper.delete(id);
	}

	public List<ServiceBindView> convert(List<ServiceBind> list) {
		List<ServiceBindView> views = new ArrayList<ServiceBindView>();

		if(list == null || list.isEmpty()) {
			return views;
		}

		List<Integer> shopIds = CommonUtil.entity(list, "shopId", Integer.class);
		List<Integer> serverIds = CommonUtil.entity(list, "serverId", Integer.class);
		List<Integer> serviceTypeIds = CommonUtil.entity(list, "serviceTypeId", Integer.class);

		List<Shop> shops = shopService.selectByIds(shopIds);
		List<Server> servers = serverService.selectByIds(serverIds);
		List<ServiceType> serviceTypes = serviceTypeService.selectByIds(serviceTypeIds);

		Map<Integer, Shop> shopMap = CommonUtil.entityMap(shops, "id", Integer.class);
		Map<Integer, Server> serverMap = CommonUtil.entityMap(servers, "id", Integer.class);
		Map<Integer, ServiceType> serviceTypeMap = CommonUtil.entityMap(serviceTypes, "id", Integer.class);

		for(ServiceBind object: list) {
			views.add(new ServiceBindView(
					object, shopMap.get(object.getShopId()), serverMap.get(object.getServerId()),
					serviceTypeMap.get(object.getServiceTypeId())
			));
		}

		return views;
	}

}
