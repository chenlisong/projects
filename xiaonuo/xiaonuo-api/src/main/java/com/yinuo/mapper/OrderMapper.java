package com.yinuo.mapper;

import com.yinuo.bean.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderMapper extends MapperI<Order>{

    List<Order> selectByUserid(@Param("userId") int userId, @Param("state") int state);

    List<Order> selectByShopId(@Param("shopId") int shopId, @Param("serverId") int serverId, @Param("state") int state);

}
 