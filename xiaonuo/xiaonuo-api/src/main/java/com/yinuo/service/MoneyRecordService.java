package com.yinuo.service;

import com.yinuo.bean.*;
import com.yinuo.mapper.MoneyRecordMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.JedisUtil;
import com.yinuo.view.MoneyRecordView;
import com.yinuo.view.ServiceBindView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class MoneyRecordService {
	
	@Autowired
	private MoneyRecordMapper mapper;

	@Autowired
	private UserService userService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private ServerService serverService;

	@Autowired
	private UserShopService userShopService;

	public long insert(MoneyRecord moneyRecord) {
		CommonUtil.setDefaultValue(moneyRecord);
		mapper.insert(moneyRecord);

		if(moneyRecord.getType() == Constant.MoneyRecordType.In) {
			userShopService.updateMoney(moneyRecord.getShopId(), moneyRecord.getUserId(), moneyRecord.getMoney());
		}else if(moneyRecord.getType() == Constant.MoneyRecordType.Out) {
			userShopService.updateMoney(moneyRecord.getShopId(), moneyRecord.getUserId(), moneyRecord.getMoney() * -1.0d);
		}
		return moneyRecord.getId();
	}
	
	public MoneyRecord selectById(long id) {
		return mapper.selectOne(id);
	}

	public List<MoneyRecord> selectByShopId(int shopId, int serverId, int type) {
		return mapper.selectByShopId(shopId, serverId, type);
	}

	public List<MoneyRecord> selectByUserId(int userId, int type) {
		return mapper.selectByUserId(userId, type);
	}

	public double sumMoneyByShopId(int shopId, int serverId, int type, Date begin, Date end) {
		return mapper.sumMoneyByShopId(shopId, serverId, type, begin, end);
	}

	public double sumGiftMoneyByShopId(int shopId, int serverId, int type, Date begin, Date end) {
		return mapper.sumGiftMoneyByShopId(shopId, serverId, type, begin, end);
	}
	
	public void delete (long id) {
		mapper.delete(id);
	}

	public List<MoneyRecordView> convert(List<MoneyRecord> list) {
		List<MoneyRecordView> views = new ArrayList<MoneyRecordView>();

		if(list == null || list.isEmpty()) {
			return views;
		}

		List<Integer> shopIds = CommonUtil.entity(list, "shopId", Integer.class);
		List<Integer> userIds = CommonUtil.entity(list, "userId", Integer.class);
		List<Integer> serverIds = CommonUtil.entity(list, "serverId", Integer.class);

		List<Shop> shops = shopService.selectByIds(shopIds);
		List<Server> servers = serverService.selectByIds(serverIds);
		List<User> users = userService.selectListByIds(userIds);

		Map<Integer, Shop> shopMap = CommonUtil.entityMap(shops, "id", Integer.class);
		Map<Integer, Server> serverMap = CommonUtil.entityMap(servers, "id", Integer.class);
		Map<Integer, User> userMap = CommonUtil.entityMap(users, "id", Integer.class);

		for(MoneyRecord object: list) {
			views.add(new MoneyRecordView(
					object, shopMap.get(object.getShopId()), serverMap.get(object.getServerId()),
					userMap.get(object.getUserId())
			));
		}

		return views;
	}
	
}
