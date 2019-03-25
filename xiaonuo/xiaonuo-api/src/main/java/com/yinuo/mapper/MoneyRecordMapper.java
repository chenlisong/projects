package com.yinuo.mapper;

import com.yinuo.bean.MoneyRecord;
import com.yinuo.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface MoneyRecordMapper extends MapperI<MoneyRecord>{

    List<MoneyRecord> selectByShopId(@Param("shopId") int shopId, @Param("serverId") int serverId, @Param("type") int type);

    List<MoneyRecord> selectByUserId(@Param("userId") int userId, @Param("type") int type);

    double sumMoneyByShopId(@Param("shopId") int shopId, @Param("serverId") int serverId, @Param("type") int type,
                            @Param("begin") Date begin, @Param("end") Date end);

    double sumGiftMoneyByShopId(@Param("shopId") int shopId, @Param("serverId") int serverId, @Param("type") int type,
                            @Param("begin") Date begin, @Param("end") Date end);

}
 