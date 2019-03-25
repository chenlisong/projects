package com.yinuo.mapper;

import com.yinuo.bean.Transfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TransferMapper extends MapperI<Transfer>{

    int fix(@Param("id") int id, @Param("targetId")int targetId, @Param("state") int state, @Param("formIdTwo") String formIdTwo);

    List<Transfer> selectBySrcId(@Param("srcId")int srcId, @Param("type")int type, @Param("card")int card, @Param("state")int state);

    List<Transfer> selectByTargetId(@Param("targetId")int targetId);

}
 