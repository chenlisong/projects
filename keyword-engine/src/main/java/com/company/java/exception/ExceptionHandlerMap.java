package com.company.java.exception;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class ExceptionHandlerMap {
	
	@Autowired
	private DefaultExceptionHandlers defaultExceptionHandler;
	
	@Autowired(required=false)
	private ExceptionHandlers exceptionHandler;
	
	private Map<String, Method> exceptionMap = new HashMap<String, Method>();
	
	@PostConstruct
	public void init() {
		if (defaultExceptionHandler != null) {
			Method[] methods = defaultExceptionHandler.getClass().getDeclaredMethods();
			for (Method method: methods) {
				if (method.isAnnotationPresent(ExceptionHandler.class)) {
					Class<? extends Throwable>[] exceptions = method.getAnnotation(ExceptionHandler.class).value();
					for (Class<? extends Throwable> exception: exceptions) {
						exceptionMap.put(exception.getName(), method);
					}
				}
			}
		}
		
		if (exceptionHandler != null) {
			Method[] methods = exceptionHandler.getClass().getDeclaredMethods();
			for (Method method: methods) {
				if (method.isAnnotationPresent(ExceptionHandler.class)) {
					Class<? extends Throwable>[] exceptions = method.getAnnotation(ExceptionHandler.class).value();
					for (Class<? extends Throwable> exception: exceptions) {
						exceptionMap.put(exception.getName(), method);
					}
				}
			}
		}
	}
	
	public Method getMethod(Class<?> clazz) {
		Method method = null;
		while (clazz != null && method == null) {
			method = exceptionMap.get(clazz.getName());
			clazz = clazz.getSuperclass();
		}
		return method;
	}
	
	public Object getExceptionHandler(Method method) {
		if (method.getDeclaringClass() == DefaultExceptionHandlers.class) {
			return defaultExceptionHandler;
		} else {
			return exceptionHandler;
		}
	}

	public void setExceptionHandler(ExceptionHandlers exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
}











