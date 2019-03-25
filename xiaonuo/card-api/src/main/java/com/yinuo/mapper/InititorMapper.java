package com.yinuo.mapper;

import com.yinuo.bean.Inititor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InititorMapper extends MapperI<Inititor>{

    Inititor selectCallUndo(@Param("userId")int userId);

    List<Inititor> selectByParentId(@Param("parentId")int parentId);

    List<Inititor> selectByUserId(@Param("userId")int userId);
//
//    @Deprecated
//    int countByUserId(@Param("userId")int userId, @Param("state") int state);
//
//    @Deprecated
//    int countByParentId(@Param("parentId")int parentId);

    int fix(@Param("id")int id, @Param("state")int state);

    int countTodayHelp(@Param("userId") int userId);

    int updateReadState(@Param("ids")List<Integer> ids, @Param("readState")int readState);
}
 