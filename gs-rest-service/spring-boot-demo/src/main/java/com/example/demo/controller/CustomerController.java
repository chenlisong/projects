package com.example.demo.controller;

import com.example.demo.bean.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("")
    public Customer get(@RequestParam("name") String name, @RequestParam("telephone") String telephone) {

        return customerService.selectByName(name, telephone);
    }

    @GetMapping("/cache")
    public Object getCache() {

        redisTemplate.opsForValue().set("spring-boot-demo-redis-test-info", "hello world", 5, TimeUnit.SECONDS);
        return redisTemplate.opsForValue().get("spring-boot-demo-redis-test-info");
    }

}
