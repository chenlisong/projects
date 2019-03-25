package com.duolanjian.java.eagle.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(value={"classpath:plan.properties"})
public class Config {

	@Value("${redis.ip_port}")
	public String redisIpPort;

	@Value("${redis.use}")
	public boolean redisUse;

	@Value("${login.url}")
	public String loginUrl;

	@Value("${login.time}")
	public int loginTime;

	@Value("${share.num}")
	public int shareNum;
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
}
