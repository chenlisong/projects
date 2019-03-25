package com.yinuo.bean;


public class Constant {

	public static class Role {

		public static final int Server = 1;

		public static final int Shoper = 2;
	}

	public static class JedisNames {

		public static final int OneDay = 60 * 60 * 24;

		public static final String Project = "card-api";

		public static final String Split = "-";

		public static final String UserOpenid = Project + Split + "userOpenid" + Split;

		public static final String UserId = Project + Split + "userId" + Split;

		public static final String AccessToken = Project + Split + "accessToken" + Split;

		public static final int LOGIN_TIME = 60 * 60 * 3;

		public static final String LOGIN_URL = "http://www.kehue.com";
	}

	public static class MessageTip {

		public static final String Permisson = "不授权无法使用，如果已经拒绝，请删除小程序，重新添加";

	}

	public static class CardSource {

		public static final int Draw = 1;

		public static final int Transfer = 2;

		public static final int Inititor = 3;

	}

	public static class InititorState {

		public static final int NeedTodo = 1;

		public static final int Fix = 2;
	}

	public static class TransferType {

		public static final int Send = 1;

		public static final int Get = 2;
	}

	public static class TransferState {

		public static final int NeedTodo = 1;

		public static final int Agree = 2;

		public static final int Refuse = 3;
	}

	public static class InititorReadState {

		public static final int Undo = 1;

		public static final int Done = 2;
	}


}
