package com.yinuo.service;

import com.yinuo.bean.*;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.mapper.OrderMapper;
import com.yinuo.mapper.ServiceBindMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.view.OrderView;
import com.yinuo.view.ServiceBindView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class OrderService {
	
	@Autowired
	private OrderMapper mapper;

	@Autowired
	private ShopService shopService;

	@Autowired
	private ServerService serverService;

	@Autowired
	private ServiceTypeService serviceTypeService;

	@Autowired
	private UserService userService;
	
	public int insert(User loginUser, Order object) {

		if(shopService.selectById(object.getShopId()) == null) {
			throw new InvalidArgumentException("找不到的店铺");
		}

		if(serverService.selectById(object.getServerId()) == null) {
			throw new InvalidArgumentException("找不到的服务者");
		}

		ServiceType serviceType = serviceTypeService.selectById(object.getServiceTypeId());
		if(serviceType == null) {
			throw new InvalidArgumentException("找不到的服务类型");
		}

		object.setState(Constant.OrderState.Queue);
		object.setPayMoney(serviceType.getPrice());
		object.setPayState(Constant.OrderPayState.UnPay);

		CommonUtil.setDefaultValue(object);
		mapper.insert(object);
		return object.getId();
	}

	public List<Order> selectByUserid(int userId, int state) {
		return mapper.selectByUserid(userId, state);
	}

	public List<Order> selectByShopId(int shopId, int serverId, int state) {
		return mapper.selectByShopId(shopId, serverId, state);
	}

	public Order selectById(int id) {
		return mapper.selectOne(id);
	}

	public void update(User loginUser, Order object) {

		mapper.update(object);
	}

	public void delete(User loginUser, int id) {
		mapper.delete(id);
	}

	public List<OrderView> convert(List<Order> list) {
		List<OrderView> views = new ArrayList<OrderView>();

		if(list == null || list.isEmpty()) {
			return views;
		}

		List<Integer> userIds = CommonUtil.entity(list, "userId", Integer.class);
		List<Integer> shopIds = CommonUtil.entity(list, "shopId", Integer.class);
		List<Integer> serverIds = CommonUtil.entity(list, "serverId", Integer.class);
		List<Integer> serviceTypeIds = CommonUtil.entity(list, "serviceTypeId", Integer.class);

		List<User> users = userService.selectListByIds(userIds);
		List<Shop> shops = shopService.selectByIds(shopIds);
		List<Server> servers = serverService.selectByIds(serverIds);
		List<ServiceType> serviceTypes = serviceTypeService.selectByIds(serviceTypeIds);

		Map<Integer, User> userMap = CommonUtil.entityMap(users, "id", Integer.class);
		Map<Integer, Shop> shopMap = CommonUtil.entityMap(shops, "id", Integer.class);
		Map<Integer, Server> serverMap = CommonUtil.entityMap(servers, "id", Integer.class);
		Map<Integer, ServiceType> serviceTypeMap = CommonUtil.entityMap(serviceTypes, "id", Integer.class);


		for(Order object: list) {
			views.add(new OrderView(
					object, shopMap.get(object.getShopId()), serverMap.get(object.getServerId()),
					serviceTypeMap.get(object.getServiceTypeId()), userMap.get(object.getUserId())
			));
		}

		return views;
	}

}
