package com.duolanjian.java.eagle.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.duolanjian.java.eagle.auth.NeedLogin;
import com.duolanjian.java.eagle.bean.User;
import com.duolanjian.java.eagle.exception.ExceptionHandlerMap;
import com.duolanjian.java.eagle.exception.NeedAuthorizationException;
import com.duolanjian.java.eagle.util.Config;
import com.duolanjian.java.eagle.util.Constant.JedisNameSpace;
import com.duolanjian.java.eagle.util.Constant.OpCode;
import com.duolanjian.java.eagle.util.JedisUtil;


@Aspect
public class IndexAspect {

	@Autowired
	private ExceptionHandlerMap exceptionHandlerMap;
	
	@Autowired
	private JedisUtil jedisUtil;
	
	@Autowired
	private Config config;
	
	private Log log = LogFactory.getLog(IndexAspect.class);

	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* com.duolanjian.java.eagle.controller..*(..))")
	public void aspect(){
	}
	
	public IndexAspect(){
		System.out.println("aspect init...");
	}
	
	/*
	 * 配置前置通知,使用在方法aspect()上注册的切入点
	 * 同时接受JoinPoint切入点对象,可以没有该参数
	 */
	@SuppressWarnings("unchecked")
	@Around("aspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object response = null;

		try {
			Object[] args = pjp.getArgs();
			long startTime = System.currentTimeMillis();

			MethodSignature signature = (MethodSignature) pjp.getSignature();
			Method method = signature.getMethod();
			Class<?> clazz = method.getDeclaringClass();
			
			User login = null;
			Object[] argsModified = new Object[args.length];
			
			if(clazz.isAnnotationPresent(NeedLogin.class) || method.isAnnotationPresent(NeedLogin.class)) {
				ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = sra.getRequest();
                String ticket = request.getHeader("ticket");
                if(!StringUtils.isEmpty(ticket)) {
                	String userObject = jedisUtil.get(JedisNameSpace.PROJECT_NAME + JedisNameSpace.LOGIN + ticket);
                	if(!StringUtils.isEmpty(userObject)) {
                		jedisUtil.set(JedisNameSpace.PROJECT_NAME + JedisNameSpace.LOGIN + ticket, 
                				userObject, config.loginTime);
                		login = JSON.parseObject(userObject, User.class);
                	}
                }
                
                if(login == null) {
                	throw new NeedAuthorizationException(config.loginUrl);
                }
                
                int i = 0;
                for (Object o: args) {
                    if(o instanceof User) {
                        argsModified[i++] = login;
                    }else {
                        argsModified[i++] =  o;
                    }
                }
			} else {
                argsModified = args;
            }
			
			response = pjp.proceed(argsModified);
			
			Map<String, Object> castResponse;
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
		return response;
	}
	public static void main(String[] args) {
		User user = new User();
		user.setPassword("123");
		user.setCreateTime("1970-08-08 08:00:00");
		
		String json = JSON.toJSONString(user);
		System.out.println("json:"+json);
		User target = JSON.parseObject(json, User.class);
		System.out.println("target:"+target);
	}
}
