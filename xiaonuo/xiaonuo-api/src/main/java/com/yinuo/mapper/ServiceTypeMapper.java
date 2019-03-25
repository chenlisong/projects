package com.yinuo.mapper;

import com.yinuo.bean.ServiceType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ServiceTypeMapper extends MapperI<ServiceType>{

    List<ServiceType> selectByShopId(int shopId);
}
 