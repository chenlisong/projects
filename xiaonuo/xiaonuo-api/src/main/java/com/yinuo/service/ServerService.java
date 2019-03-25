package com.yinuo.service;

import com.yinuo.bean.Constant;
import com.yinuo.bean.Server;
import com.yinuo.bean.Shop;
import com.yinuo.bean.User;
import com.yinuo.exception.InvalidArgumentException;
import com.yinuo.mapper.ServerMapper;
import com.yinuo.mapper.ServerMapper;
import com.yinuo.util.CommonUtil;
import com.yinuo.util.JedisUtil;
import com.yinuo.view.ServerView;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class ServerService {
	
	@Autowired
	private ServerMapper mapper;

	@Autowired
	private UserService userService;

	@Autowired
	private ShopService shopService;
	
	public int insert(User loginUser, Server server) {

		if(userService.selectOne(server.getUserId()) == null) {
			throw new InvalidArgumentException("找不到的用户");
		}

		if(shopService.selectById(server.getShopId()) == null) {
			throw new InvalidArgumentException("找不到的店铺");
		}

		server.setWorkFlag(Constant.ServerWorkFlag.Sleep);

		//TODO 创建店长角色需要店长权限

		CommonUtil.setDefaultValue(server);
		mapper.insert(server);
		return server.getId();
	}

	public void update(User loginUser, Server server) {

		mapper.update(server);
	}

	public List<Server> selectByShopId(int shopId, int workFlag) {
		return mapper.selectByShopId(shopId, workFlag);
	}

	public List<Server> selectByIds(List<Integer> ids) {
		return mapper.selectListByIds(ids);
	}

	public Server selectById(int id) {
		return mapper.selectOne(id);
	}

	public void delete(User loginUser, int id) {
		mapper.delete(id);
	}

	public List<ServerView> convert(List<Server> servers) {
		List<ServerView> views = new ArrayList<ServerView>();

		if(servers == null || servers.isEmpty()) {
			return views;
		}

		List<Integer> shopIds = CommonUtil.entity(servers, "shopId", Integer.class);
		List<Integer> userIds = CommonUtil.entity(servers, "userId", Integer.class);

		List<Shop> shops = shopService.selectByIds(shopIds);
		List<User> users = userService.selectListByIds(userIds);

		Map<Integer, Shop> shopMap = CommonUtil.entityMap(shops, "id", Integer.class);
		Map<Integer, User> userMap = CommonUtil.entityMap(users, "id", Integer.class);

		for(Server server: servers) {
			views.add(new ServerView(
					server, shopMap.get(server.getShopId()), userMap.get(server.getUserId())
			));
		}

		return views;
	}

}
