package com.yinuo.mapper;

import com.yinuo.bean.Server;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ServerMapper extends MapperI<Server>{

    List<Server> selectByShopId(@Param("shopId") int shopId, @Param("workFlag") int workFlag);

}
 