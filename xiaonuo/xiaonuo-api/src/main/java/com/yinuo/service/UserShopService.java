package com.yinuo.service;

import com.yinuo.bean.*;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.mapper.ShopMapper;
import com.yinuo.mapper.UserShopMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.JedisUtil;
import com.yinuo.view.UserShopView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserShopService {
	
	@Autowired
	private UserShopMapper mapper;

	@Autowired
	private ShopService shopService;

	@Autowired
	private ServerService serverService;

	@Autowired
	private MoneyRecordService moneyRecordService;

	@Autowired
	private JedisUtil jedis;
	
	public int insert(User loginUser, UserShop userShop) {
		userShop.setUserId(loginUser.getId());
		CommonUtil.setDefaultValue(userShop);

		//校验shopId是否存在
		if(shopService.selectById(userShop.getShopId()) == null) {
			throw new InvalidArgumentException("找不到该店铺");
		}

		if(mapper.countByUserShopId(userShop.getUserId(), userShop.getShopId()) > 0) {
			throw new InvalidArgumentException("已绑定该店铺");
		}

		mapper.insert(userShop);
		return userShop.getId();
	}

	public void delete(User loginUser, int id) {
		UserShop userShop = mapper.selectOne(id);
		if(loginUser.getId().intValue() != userShop.getUserId().intValue()) {
			throw new InvalidArgumentException("你无权限删除店铺关注记录");
		}

		mapper.delete(id);
	}

	public List<UserShop> selectByUserid(int userId) {
		return mapper.selectByUserShopId(userId, 0);
	}

	public List<UserShop> selectByShopId(int shopId, int id, int limit) {
		return mapper.selectByShopId(shopId, id, limit);
	}

	public int updateMoney(int shopId, int userId, double updateMoney) {
		return mapper.updateMoney(shopId, userId, updateMoney);
	}

	public List<UserShopView> convert(List<UserShop> userShops) {
		List<UserShopView> views = new ArrayList<UserShopView>();

		if(userShops == null || userShops.isEmpty()) {
			return views;
		}

		List<Integer> shopIds = CommonUtil.entity(userShops, "shopId", Integer.class);

		List<Integer> serverIds = CommonUtil.entity(userShops, "lastServerId", Integer.class);

		Map<Integer, Shop> shopMap = CommonUtil.entityMap(shopService.selectByIds(shopIds), "id", Integer.class);

		Map<Integer, Server> serverMap = CommonUtil.entityMap(serverService.selectByIds(serverIds), "id", Integer.class);

		for(UserShop userShop: userShops) {
			views.add(new UserShopView(
					userShop, shopMap.get(userShop.getShopId()), serverMap.get(userShop.getLastServerId())
			));
		}

		return views;
	}

}
