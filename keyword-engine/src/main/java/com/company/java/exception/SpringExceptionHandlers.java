package com.company.java.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.java.util.CommonUtil;
import com.company.java.util.Constant.OpCode;
import com.company.java.util.Lang;

@ControllerAdvice
@Component
public class SpringExceptionHandlers {
	
	private static final Logger log = LoggerFactory.getLogger(SpringExceptionHandlers.class);
	
	@Autowired
	private Lang lang;
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ExceptionResponse handleE(HttpServletRequest request, Exception e) {
		log.error("spring exception:\n" + CommonUtil.getStackTrace(e));
		return new ExceptionResponse(OpCode.INVALID_PARAMS, lang.getLang("INVALID_PARAM") 
		        + ":" + getMessage(e.getMessage() == null ? e.getClass().getName() : e.getMessage()));
    }
	
	public String getMessage(String key) {
		String desc = lang.getLang(key);
		if (desc == null) {
			desc = key;
		}
		return desc;
	}
	
}











