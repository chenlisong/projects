package com.yinuo.mapper;

import com.yinuo.bean.Card;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CardMapper extends MapperI<Card>{

    List<Card> selectByUserId(@Param("userId")int userId);

    int selectByUserCardId(@Param("userId")int userId, @Param("card") int card);

    int updateNumber(@Param("userId")int userId, @Param("card") int card, @Param("number")int number);
}
 