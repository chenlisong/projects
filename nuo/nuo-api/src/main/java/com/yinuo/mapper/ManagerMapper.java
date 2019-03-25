package com.yinuo.mapper;

import com.yinuo.bean.Manager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ManagerMapper extends MapperI<Manager>{

    public Manager selectByUserid(@Param("userId") long userId);

}
 