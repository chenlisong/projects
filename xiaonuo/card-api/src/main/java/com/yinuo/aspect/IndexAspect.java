package com.yinuo.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yinuo.bean.Constant;
import com.yinuo.service.UserService;
import com.yinuo.validation.RoleShoper;
import com.yinuo.validation.RoleServer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.yinuo.bean.User;
import com.yinuo.exception.ExceptionHandlerMap;
import com.yinuo.exception.NeedAuthorizationException;
import com.yinuo.util.JedisUtil;
import com.yinuo.util.Constant.OpCode;
import com.yinuo.bean.Constant;
import com.yinuo.validation.NeedLogin;


//@Component
@Aspect
public class IndexAspect {

	@Autowired
	private ExceptionHandlerMap exceptionHandlerMap;
	
	@Autowired
	private JedisUtil jedisUtil;

	@Autowired
    private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(IndexAspect.class);

	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* com.yinuo.controller..*(..))")
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
		long startTime = System.currentTimeMillis();
		Object response = null;
        
        Object[] args = pjp.getArgs();
        Object[] argsModified = new Object[args.length];

        MethodSignature signature = (MethodSignature) pjp.getSignature(); 
        Method method = signature.getMethod();
        Class<?> clazz = method.getDeclaringClass();

        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            String[] paths = requestMapping.value();
            String pathString = "";
            for (String path: paths) {
                pathString += path + ",";
            }
            if ("".equals(pathString)) {
                pathString = "unknowPath";
            } else {
                pathString = pathString.substring(0, pathString.length() - 1);
            }

            RequestMethod[] httpMethods = requestMapping.method();
            String methodString = "";
            for (RequestMethod httpMethod: httpMethods) {
                methodString += httpMethod.name() + ",";
            }

            if ("".equals(methodString)) {
                methodString = "unknowMethod";
            } else {
                methodString = methodString.substring(0, methodString.length() - 1);
            }
        }

        //需要校验权限
        int needCheckRole = 0;
        if (clazz.isAnnotationPresent(RoleServer.class) || method.isAnnotationPresent(RoleServer.class)) {
            needCheckRole = Constant.Role.Server;
        }else if (clazz.isAnnotationPresent(RoleShoper.class) || method.isAnnotationPresent(RoleShoper.class)) {
            needCheckRole = Constant.Role.Shoper;
        }

        StringBuilder urlContent = new StringBuilder();

        try {
        if (clazz.isAnnotationPresent(NeedLogin.class) || method.isAnnotationPresent(NeedLogin.class)) {
            ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = sra.getRequest();
            urlContent.append("url: " + request.getRequestURL()).append(", args: " + JSON.toJSONString(args));

            User loginInfo = null;
                String openid = request.getHeader("openid");

            String sourceIp = request.getHeader("Request-Ip");

            if (sourceIp == null || "".equals(sourceIp)) {
                String forwardedIpString = request.getHeader("X-Forwarded-For");
                if (forwardedIpString != null) {
                    String[] forwardedIps = forwardedIpString.split(",");
                    if (forwardedIps.length > 0 && !"".equals(forwardedIps[0].trim())) {
                        sourceIp = forwardedIps[0].trim();
                    }
                }
            }

            if (sourceIp == null || "".equals(sourceIp)) {
                sourceIp = request.getHeader("X-Real-IP");
            }
            if (sourceIp == null || "".equals(sourceIp)) {
                sourceIp = request.getRemoteAddr();
            }
            if (openid != null && !"".equals(openid)) {
                loginInfo = userService.selectByOpenid(openid);
                urlContent.append(", userId: " + loginInfo.getId() + ", openId: " + loginInfo.getWechatOpenid());
            }

            if (loginInfo == null) {
            	 throw new NeedAuthorizationException(Constant.MessageTip.Permisson);
            }else {

                //校验权限
                if(needCheckRole > 0) {
                    //loginInfo.checkLevel(needCheckRole);
                }

            	//刷新时间
            	jedisUtil.set(Constant.JedisNames.UserOpenid + openid, JSON.toJSONString(loginInfo), Constant.JedisNames.LOGIN_TIME);
            }

            int i = 0;
            for (Object o: args) {
                if (o instanceof User) {
                    argsModified[i++] = loginInfo;
                } else {
                    argsModified[i++] =  o;
                }
            }
        } else {
            argsModified = args;
        }
		
			response = pjp.proceed(argsModified);
            urlContent.append(", response: " + JSON.toJSONString(response));
			Map<String, Object> castResponse;
			if (response == null) {
				response = new HashMap<String, Object>();
			}

			if (response instanceof Map) {
				castResponse = (Map<String, Object>) response;
				castResponse
						.put("cost",
								(double) (System.currentTimeMillis() - startTime) / 1000);
				castResponse.put("code", OpCode.SUCCESS);
			}
		} catch (Exception e) {
			Method methodE = exceptionHandlerMap.getMethod(e.getClass());
			if (methodE != null) {
				Object errorResponse = methodE.invoke(
						exceptionHandlerMap.getExceptionHandler(methodE), e);
                urlContent.append(", response: " + JSON.toJSONString(errorResponse));
				return errorResponse;
			}
		}finally {
            logger.info("request: " + urlContent.toString());
        }

        return response;
	}
	
}
