package com.company.java.util;


/**
 * 系统配置，一经定义只能添加，不能修改！
 */
public class Constant {
	
	public static class OpCode {
		public static final short SUCCESS = 200;
		public static final short INVALID_PARAMS = 400;
		public static final short NEED_AUTHORIZATION = 401;
		public static final short NOT_PERMITED = 403;
		public static final short RESOURCE_NOT_FOUND = 404;
		public static final short INTERNAL_EXCEPTION = 500;
	}
	
	public static class Config {
		//开始小时
		public static final String BEGIN_TIME = " 00:00:01";
		//结束小时
		public static final String END_TIME = " 23:59:59";
		//每人最大次数
		public static final int COUNT_MAX = 5;

		//周几
		public static final int WEEK = 3;
		
		public static final int ONE_RATE = 3;
		public static final int TWO_RATE = 10;
		public static final double THREE_RATE = 0.001;
		
	}
	
}























