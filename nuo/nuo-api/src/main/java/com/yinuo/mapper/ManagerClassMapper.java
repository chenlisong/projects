package com.yinuo.mapper;

import com.yinuo.bean.ManagerClass;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ManagerClassMapper extends MapperI<ManagerClass>{

    public List<ManagerClass> selectByRole(@Param("role") int role, @Param("schoolId") long schoolId,
                                           @Param("classId") long classId, @Param("offset") int offset, @Param("limit") int limit);

    public List<ManagerClass> selectByManagerId(@Param("managerId") long managerId);

    public int countByRole(@Param("role") int role, @Param("schoolId") long schoolId,
                           @Param("classId") long classId);
}
 