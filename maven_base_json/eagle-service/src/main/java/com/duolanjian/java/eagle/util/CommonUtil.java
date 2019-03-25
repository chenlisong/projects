package com.duolanjian.java.eagle.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.duolanjian.java.eagle.exception.InvalidHttpArgumentException;

@Component
public class CommonUtil {
	
	/**
	 * get stack trace from throwable object
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
	
	/**
	 * get time String formated to "yyyy-MM-dd HH:mm:ss" from date object 
	 */
	public static String getTime(Date date) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * get time millis from time string
	 * @throws ParseException when time string is not formated as "yyyy-MM-dd HH:mm:ss"
	 */
	public static long getMillis(String dateString) throws ParseException {
		try {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(dateString);
			return date.getTime();
		} catch (ParseException e) {
			throw new RuntimeException("time format failed, date: " + dateString, e);
		}
	}


	/**
	 * 根据传入的对象集合，拿出对象中某一字段的集合
	 * @param list
	 * @param fieldName
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> entity(List<? extends Object> list,String fieldName,Class<? extends Object> t){
		List<T> result = new ArrayList<T>();
		if(list == null || list.size() <= 0){
			return result;
		}
		for(Object o : list){
			
			String getMethod = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			try {
				result.add((T) o.getClass().getDeclaredMethod(getMethod).invoke(o));
			} catch (Exception e) {
				throw new RuntimeException("CommonUtil entity error ."+e);
			}
		}
		return result;
	}
	
	public static void setDefaultValues(List<? extends Object> objs) {
		if(objs == null || objs.size() <= 0) {
			return ;
		}
		for(Object obj : objs) {
			setDefaultValue(obj);
		}
	}
	
	/**
	 * 传入对象，检测如果有field是null的，根据field类型自动设置默认值
	 * @param obj
	 */
	public static void setDefaultValue(Object obj){
		try{
			if(obj == null){
				return ;
			}
			Field[] fields = obj.getClass().getDeclaredFields();
			if(fields == null || fields.length <= 0){
				return ;
			}
			for(Field field : fields){
				String fieldName = field.getName();
				//"id"跳过
				if(!"id".equals(fieldName) || fieldName == null || fieldName.length() <= 0){
					String getMethodName = "get"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
					Method getMethod = obj.getClass().getDeclaredMethod(getMethodName);
					Object getObject = getMethod.invoke(obj);
					if(getObject == null){
						String setMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
						Method setMethod = null;
						
						if(field.getType() != Date.class){
							setMethod = obj.getClass().getDeclaredMethod(setMethodName,field.getType());
						}else{
							try{
								setMethod = obj.getClass().getDeclaredMethod(setMethodName,Date.class);
							}catch (Exception e) {
								setMethod = obj.getClass().getDeclaredMethod(setMethodName,String.class);
							}
						}
						
						if(field.getType() == String.class){
							setMethod.invoke(obj,"");	
						}else if(field.getType() == Integer.class){
							setMethod.invoke(obj,0);	
						}else if(field.getType() == Date.class){
							try{
								setMethod.invoke(obj,DateTool.standardSdf.parse("1970-01-01 08:00:00"));	
							}catch (Exception e) {
								setMethod.invoke(obj,"1970-01-01 08:00:00");	
							}
						}else if(field.getType() == Long.class){
							setMethod.invoke(obj,0l);	
						}
					}
				}
			}
		}catch(Exception e){
			throw new InvalidHttpArgumentException(e);
		}
	}
	
	/**
	 * 传入JSON对象转换成指定的对象实例
	 * @param json
	 * @param t
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T parseJson2Object(String json,Class<? extends Object> t) throws ClassNotFoundException,
						InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, 
						IllegalArgumentException, InvocationTargetException{
		JSONObject jsonObject = JSON.parseObject(json);
		Class resultClass = Class.forName(t.getName());
		Object obj = resultClass.newInstance();
		for(Field field : t.getDeclaredFields()){
			String setMethodName = "set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
			Method setMethod = null;
			
			if(jsonObject.get(field.getName()) == null){
				continue;
			}
			
			if(field.getType() == String.class){
				setMethod = obj.getClass().getDeclaredMethod(setMethodName,field.getType());
				setMethod.invoke(obj,jsonObject.getString(field.getName()));	
			}else if(field.getType() == Integer.class){
				setMethod = obj.getClass().getDeclaredMethod(setMethodName,field.getType());
				setMethod.invoke(obj,jsonObject.getIntValue(field.getName()));	
			}else if(field.getType() == Long.class){
				setMethod = obj.getClass().getDeclaredMethod(setMethodName,field.getType());
				setMethod.invoke(obj,jsonObject.getLongValue(field.getName()));	
			}else if(field.getType() == Date.class){
				try{
					setMethod = obj.getClass().getDeclaredMethod(setMethodName,Date.class);
					setMethod.invoke(obj,jsonObject.getDate(field.getName()));	
				}catch (Exception e) {
					setMethod = obj.getClass().getDeclaredMethod(setMethodName,String.class);
					setMethod.invoke(obj,DateTool.standardSdf.format(jsonObject.getDate(field.getName())));	
				}
			}
		}
		
		return (T)obj;
	}
	
	@SuppressWarnings({ "unchecked", "null" })
	public static <T> List<T> parseJson2List(String json,Class<? extends Object> t) throws ClassNotFoundException,
	InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, 
	IllegalArgumentException, InvocationTargetException{
		JSONArray jsonArray = JSON.parseArray(json);
		if(jsonArray == null && jsonArray.size() <= 0) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		for(int i=0 ; i < jsonArray.size() ;i++){
			list.add((T)parseJson2Object(jsonArray.getJSONObject(i).toJSONString(), t));
		}
		return list;
	}
	

}
