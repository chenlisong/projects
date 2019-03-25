package com.yinuo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(value={"classpath:xiaonuo.properties"})
public class Config {

	@Value("${email.account}")
	public String emailAccount;

	@Value("${email.password}")
	public String emailPassword;

	@Value("${redis.ipport}")
	public String redisIpPort;

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
}
