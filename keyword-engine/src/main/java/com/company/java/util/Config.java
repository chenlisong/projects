package com.company.java.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

	@Value("${defaultFile}")
	public String defaultFile;

	@Value("${maxsize}")
	public int maxsize;
	
	public Config() {
		System.out.println("defaultFile:"+defaultFile);
	}

}
