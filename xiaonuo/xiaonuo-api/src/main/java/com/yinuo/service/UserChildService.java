package com.yinuo.service;

import com.yinuo.bean.*;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.mapper.UserChildMapper;
import com.yinuo.mapper.UserShopMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.JedisUtil;
import com.yinuo.view.UserChildView;
import com.yinuo.view.UserShopView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserChildService {
	
	@Autowired
	private UserChildMapper mapper;

	@Autowired
	private UserService userService;

	@Autowired
	private ShopService shopService;
	
	public int insert(User loginUser, UserChild userChild) {

		userChild.setParentId(loginUser.getId());

		if(userService.selectOne(userChild.getChildId()) == null) {
			throw new InvalidArgumentException("找不到需要绑定的用户");
		}

		if(shopService.selectById(userChild.getShopId()) == null) {
			throw new InvalidArgumentException("找不到店铺ID");
		}

		CommonUtil.setDefaultValue(userChild);

		mapper.insert(userChild);

		return userChild.getId();
	}

	public List<UserChild> selectByShopId(int shopId, int parentId, int childId) {
		return mapper.selectByShopId(shopId, parentId, childId);
	}

	public void delete(User loginUser, int id) {
		UserChild userChild = mapper.selectOne(id);
		if(loginUser.getId().intValue() != userChild.getParentId().intValue()
				&& loginUser.getId().intValue() != userChild.getChildId().intValue()) {
			throw new InvalidArgumentException("你既不是主卡用户，也不是副卡用户，无权限删除");
		}

		mapper.delete(id);

	}

	public List<UserChildView> convert(List<UserChild> userChilds) {
		List<UserChildView> views = new ArrayList<UserChildView>();
		if(userChilds == null || userChilds.isEmpty()) {
			return views;
		}


		List<Integer> parentIds = CommonUtil.entity(userChilds,"parentId", Integer.class);
		List<Integer> childIds = CommonUtil.entity(userChilds,"childId", Integer.class);
		List<Integer> shopIds = CommonUtil.entity(userChilds,"shopId", Integer.class);

		List<User> parents = userService.selectListByIds(parentIds);
		List<User> childs = userService.selectListByIds(childIds);
		List<Shop> shops = shopService.selectByIds(shopIds);

		Map<Integer, User> parentMap = CommonUtil.entityMap(parents, "id", Integer.class);
		Map<Integer, User> childMap = CommonUtil.entityMap(childs, "id", Integer.class);
		Map<Integer, Shop> shopMap = CommonUtil.entityMap(shops, "id", Integer.class);

		for(UserChild userChild: userChilds) {
			views.add(
					new UserChildView(
							userChild,
							parentMap.get(userChild.getParentId()),
							childMap.get(userChild.getChildId()),
							shopMap.get(userChild.getShopId())
					));
		}

		return views;
	}

}
