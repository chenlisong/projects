package com.example.demo.service;

import com.example.demo.bean.Customer;
import com.example.demo.mapper.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public Customer selectByName(String name, String telephone) {
        return customerDao.selectByName(name, telephone);
    }

    //如需事务，增加注解Transactional
    @Transactional
    public long insert(CustomerDao customerDao) {
        return -1;
    }

    public int delete(long id) {
        return -1;
    }

    public int update(CustomerDao customerDao) {
        return -1;
    }

}
