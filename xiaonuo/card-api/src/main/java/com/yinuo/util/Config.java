package com.yinuo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource(value={"classpath:xiaonuo.properties"})
public class Config {

	@Value("${email.account}")
	public String emailAccount;

	@Value("${email.password}")
	public String emailPassword;

	@Value("${redis.ipport}")
	public String redisIpPort;

	@Value("${draw.times}")
	public int drawTimes;

	@Value("${help.count}")
	public int helpCount;

	@Value("${help.daily.time}")
	public int helpDailyTime;

	@Value("${version}")
	public int version;

	@Value("${hunman.json}")
	public String hunmanJson;

	public int sumHunmanJson;

	public int[] hunmanArray;

	public String[] humanName = new String[]{"OMGlionkk", "OMGsilentBT", "OMGxiaohaixxxx", "OMGxiaorong",
			"17_shou", "17_shox", "17_47Gamer", "17_qmcc", "4AMCptkk", "4AMGuCun", "4AMGodVzzZ", "4AMAluka"};

	@PostConstruct
	public void sumHunmanJson() {
		JSONArray array = JSON.parseArray(hunmanJson);
		hunmanArray = new int[array.size()];
		for(int i=0;i<array.size();i++) {
			sumHunmanJson += array.getIntValue(i);
			hunmanArray[i] = array.getIntValue(i);
		}
	}

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
}
