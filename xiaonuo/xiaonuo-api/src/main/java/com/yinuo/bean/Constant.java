package com.yinuo.bean;


public class Constant {

	public static class Role {

		public static final int Server = 1;

		public static final int Shoper = 2;
	}

	public static class ServerRole {

		public static final int Manager = 1;

		public static final int Server = 2;

	}

	public static class ServerWorkFlag {

		public static final int Work = 1;

		public static final int Sleep = 2;
	}

	public static class JedisNames {

		public static final int OneDay = 60 * 60 * 24;

		public static final String Project = "nuo-api";

		public static final String Split = "-";

		public static final String UserOpenid = Project + Split + "userOpenid" + Split;

		public static final int LOGIN_TIME = 60 * 60 * 3;

		public static final String LOGIN_URL = "http://www.kehue.com";
	}

	public static class MessageTip {

		public static final String Permisson = "不授权无法使用，如果已经拒绝，请删除小程序，重新添加";

	}

	public static class OrderState {

		public static final int Queue = 1;

		public static final int Work = 2;

		public static final int Done = 3;

		public static final int Cancel = 4;
	}

	public static class OrderPayState {

		public static final int UnPay = 1;

		public static final int Balance = 2;

		public static final int Cash = 3;

		public static final int Wechat = 4;

		public static final int AliPay = 5;
	}

	public static class MoneyRecordType {

		public static final int In = 1;

		public static final int Out = 2;

	}

}
