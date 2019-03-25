package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @RequestMapping("/hello")
    public String hello(@RequestParam String name) {
        System.out.println("computer-server1 execute name: " + name);
        return "hello "+name+"，this is first messge";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}