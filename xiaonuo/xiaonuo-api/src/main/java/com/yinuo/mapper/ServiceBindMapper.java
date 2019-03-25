package com.yinuo.mapper;

import com.yinuo.bean.ServiceBind;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ServiceBindMapper extends MapperI<ServiceBind>{

    List<ServiceBind> selectByShopId(@Param("shopId") int shopId, @Param("serviceTypeId") int serviceTypeId);

    List<ServiceBind> selectByServerId(@Param("serverId") int serverId);
}
 