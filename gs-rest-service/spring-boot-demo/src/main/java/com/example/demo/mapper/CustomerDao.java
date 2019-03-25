package com.example.demo.mapper;

import com.example.demo.bean.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CustomerDao {

    @Select("SELECT * FROM customer WHERE name = #{name} and telephone = #{telephone}")
    Customer selectByName(@Param("name") String name, @Param("telephone") String telephone);


}
