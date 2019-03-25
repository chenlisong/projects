package com.yinuo.mapper;

import com.yinuo.bean.Shop;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopMapper extends MapperI<Shop>{

    Shop selectByName(@Param("name")String name);

    List<Shop> selectByLikeName(@Param("name") String name, @Param("limit") int limit);

}
 