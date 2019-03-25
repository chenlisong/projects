package com.company.java.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.company.java.util.CommonUtil;
import com.company.java.util.Constant.OpCode;
import com.company.java.util.Lang;

@Component
public class DefaultExceptionHandlers {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandlers.class);
	
	@Autowired
	private Lang lang;
	
	@ExceptionHandler({
		InvalidArgumentException.class,
		InvalidHttpArgumentException.class})
	public ExceptionResponse handleII(Exception e) {
		return new ExceptionResponse(OpCode.INVALID_PARAMS, lang.getLang(e.getMessage() == null ? e.getClass().getName() : e.getMessage()));
    }
	
	@ExceptionHandler(ResourceNotExistException.class)
	public ExceptionResponse handleRNFE(Exception e) {
		return new ExceptionResponse(OpCode.RESOURCE_NOT_FOUND, lang.getLang(e.getMessage() == null ? e.getClass().getName() : e.getMessage()));
    }
	
	@ExceptionHandler(OperationNotAllowedException.class)
	public ExceptionResponse handleONAE(Exception e) {
		return new ExceptionResponse(OpCode.NOT_PERMITED, lang.getLang(e.getMessage() == null ? e.getClass().getName() : e.getMessage()));
    }
	
	@ExceptionHandler(NeedAuthorizationException.class)
	public Map<String, Object> handleNAE(NeedAuthorizationException e) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("code", OpCode.NEED_AUTHORIZATION);
		response.put("desc", lang.getLang("NEED_LOGIN"));
		response.put("loginUrl", e.getLoginUrl());
		return response;
    }
	
	@ExceptionHandler(Exception.class)
	public ExceptionResponse handleE(Exception e) {
		log.error("operation failed, " + "exception:\n" + CommonUtil.getStackTrace(e));
		return new ExceptionResponse(OpCode.INTERNAL_EXCEPTION, lang.getLang("INTERNAL_EXCEPTION") 
		        + ":" + lang.getLang(e.getMessage() == null ? e.getClass().getName() : e.getMessage()));
    }
	
}











