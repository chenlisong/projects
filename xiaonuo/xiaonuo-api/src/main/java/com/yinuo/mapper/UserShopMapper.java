package com.yinuo.mapper;

import com.yinuo.bean.UserShop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserShopMapper extends MapperI<UserShop>{

    List<UserShop> selectByUserShopId(@Param("userId") int userId, @Param("shopId") int shopId);

    int countByUserShopId(@Param("userId") int userId, @Param("shopId") int shopId);

    List<UserShop> selectByShopId(@Param("shopId") int shopId, @Param("id") int id, @Param("limit") int limit);

    int updateMoney(@Param("shopId") int shopId, @Param("userId") int userId, @Param("updateMoney") double updateMoney);
}
 