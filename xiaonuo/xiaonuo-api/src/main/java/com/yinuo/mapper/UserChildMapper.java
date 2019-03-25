package com.yinuo.mapper;

import com.yinuo.bean.UserChild;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserChildMapper extends MapperI<UserChild>{

    List<UserChild> selectByShopId(@Param("shopId") int shopId, @Param("parentId") int parentId, @Param("childId") int childId);

}
 