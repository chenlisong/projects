package com.company.java.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.java.exception.ExceptionHandlerMap;
import com.company.java.util.Constant.OpCode;


@Component
@Aspect
public class IndexAspect {

	@Autowired
	private ExceptionHandlerMap exceptionHandlerMap;
	
	private Log log = LogFactory.getLog(IndexAspect.class);

	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* com.prize.java.controller..*(..))")
	public void aspect(){
	}
	
	public IndexAspect(){
		log.info("aspect init...");
	}
	
	/*
	 * 配置前置通知,使用在方法aspect()上注册的切入点
	 * 同时接受JoinPoint切入点对象,可以没有该参数
	 */
	@SuppressWarnings({ "unchecked" })
	@Around("aspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object response = null;

		try {
			Object[] args = pjp.getArgs();
			log.info("args:"+args);
			long startTime = System.currentTimeMillis();

			response = pjp.proceed(args);
            
			Map<String, Object> castResponse = null;
			if (response == null) {
				response = new HashMap<String, Object>();
			}
			if (response instanceof Map) {
				castResponse = (Map<String, Object>) response;
				castResponse.put("cost", (double)(System.currentTimeMillis() - startTime) / 1000);
				castResponse.put("code", OpCode.SUCCESS);
			}
			
		} catch (Exception e) {
			Method method = exceptionHandlerMap.getMethod(e.getClass());
			if (method != null) {
				Object errorResponse = method.invoke(exceptionHandlerMap.getExceptionHandler(method), e);
				return errorResponse;
			}
		}
		log.info("response:"+response);
		return response;
	}
	
}
