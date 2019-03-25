package com.company.java.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class BaseService{

	public Log log = LogFactory.getLog(this.getClass());
	
}
