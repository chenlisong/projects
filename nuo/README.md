# nuo


接口设计

环境ip：47.100.24.40 端口：80
base url: www.kehue.com/nuo/

wechat登陆
	request/get: /wechatLogin?code=${res.code}

		{
			"id": "e4911dba-e7f7-4e08-a59c-4d243355098d",
			"loginUser": {
				"birthday": "1970-01-01 08:00:00",
				"createTime": "2017-11-08 20:48:18",
				"id": 1,
				"sex": 0,
				"wechatNickname": "",
				"wechatOpenid": "onIX60AX6xG8RsqnPr1b",
				"avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/loVyFiaRialvJRVMTqMWSxnGVj4ibSEAgiasLRicIGFlcoibA5mYGqabHzzoPJH4w3HZDsrXRiaQ1QBNYUfHSEywwmtQQ/0",
				"city": "",
				"country": "China",
				"province": "Beijing",
				"role":1,
				"schoolId":1,
				"classId":1
			}
		}
	
		以后每次请求，在header中带上，key:ticket, value:${id}

用户信息

	更新：request/put: /users

		{
			"id": 1,
			"birthday": "1970-01-01 08:00:00",
			"sex": 1,	//1男，2女
			"wechatNickname": "微信昵称",
			"avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/loVyFiaRialvJRVMTqMWSxnGVj4ibSEAgiasLRicIGFlcoibA5mYGqabHzzoPJH4w3HZDsrXRiaQ1QBNYUfHSEywwmtQQ/0",
			"city": "",
			"country": "China",
			"province": "Beijing",
			"role":1,
			"schoolId":1,
			"classId":1
		}

	response:
		{
			"id": 1
		}

	查询：request/get: /users

		{
		    "code": 200,
		    "cost": 7.699,
		    "loginUser": {
		        "id": 1,
		        "createTime": "2017-11-08 20:48:18",
		        "birthday": 0,
		        "wechatNickname": "chenlisongabc",
		        "sex": 0,
		        "wechatOpenid": "onIX60AX6xG8RsqnPr1b-z9MGyzY",
				"avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/loVyFiaRialvJRVMTqMWSxnGVj4ibSEAgiasLRicIGFlcoibA5mYGqabHzzoPJH4w3HZDsrXRiaQ1QBNYUfHSEywwmtQQ/0",
				"city": "",
				"country": "China",
				"province": "Beijing",
				"role":1,
				"schoolId":1,
				"classId":1
		    }
		}

学生信息

	创建：request/post: /students

		{
			"name": "student abc",
			"birthday": "1993-08-11 00:00:00",
			"sex": 1,
			"pic": "www.pic.com/pic.jpg",
			"classId": 0
		}

	更新：request/put: /students
		{
			"id": 1,
			"name": "student abc",
			"birthday": "1993-08-11 00:00:00",
			"sex": 1,
			"pic": "www.pic.com/pic.jpg",
			"classId": 0,
			"state":2	//0未知，1尚未入学，2已入学
		}

	删除：request/delete: /students?id=1

	查询：request/get: /students?page=1&pageLength=10	/查询当前登录者的信息
						/students?name=student abc222	/按照学生名查询

		response: 
		{
		    "cost": 0.038,
		    "code": 200,
		    "data": [
		        {
		            "birthday": "2017-11-30 21:01:47",
		            "userStudent": {
		                "id": 15,
		                "studentId": 2,
		                "userId": 1,
		                "relationship": 2,	//关系，1自己，2父亲，3母亲，4爷爷，5奶奶，6姑姑，7姑父，8姨，9姨父, 10创建者
		                "createTime": "2017-12-23 21:09:47"
		            },
		            "classId": 0,
		            "createTime": "2017-11-30 21:01:47",
		            "sex": 1,
		            "name": "abc",
		            "id": 2,
		            "pic": "http://www.qqzhi.com/uploadpic/2015-01-22/022222987.jpg",
		            "state": 2	//0未知，1尚未入学，2已入学,
		        }
		    ],
		    "count": 1
		}

绑定学生信息
	
	绑定：request/post: /userStudents

		{
			"studentId": 2,
			"relationship": 2	//关系，1自己，2父亲，3母亲，4爷爷，5奶奶，6姑姑，7姑父，8姨，9姨父, 10创建者
		}
	
	解绑：request/delete: /userStudents?id=1


班级信息
	
	新增：request/post: /classes

		{
			"grade": 1,		//年纪1-6
			"number": 2,	//班号01-99
			"schoolId": 1	//归属学校ID
		}

	删除：request/delete: /classes?id=1
	
	修改：request/put: /classes

		{
			"id": 1,
			"grade": 1,		//年纪1-6
			"number": 2,	//班号01-99
			"schoolId": 1	//归属学校ID
		}

	查询：request/get: /classes?id=1
						/classes?page=1&pageSize=10

		{
		    "count": 1,
		    "data": [
		        {
		            "id": 1,
		            "grade": 1,
		            "number": 2,
		            "schoolId": 1,
		            "createTime": "2017-12-07 20:46:07"
		        }
		    ],
		    "code": 200,
		    "cost": 5.054
		}

课程信息

	新增：request/post: /courses

		{
			"name": "语文课",	
			"beginTime": "2017-01-01 08:00:00",		
			"endTime": "2017-01-01 08:00:00",	
			"classId": 1,	
			"remark": "很厉害的外教课程"
		}

	删除：request/delete: /courses?id=1
	
	修改：request/put: /courses

		{
			"id": 1,
			"name": "语文课",	
			"beginTime": "2017-01-01 08:00:00",		
			"endTime": "2017-01-01 08:00:00",	
			"classId": 1,	
			"remark": "很厉害的外教课程"
		}

	查询：request/get: /courses

		按照班级时间查询 /courses?classId=1&beginTime=2017-01-01 00:00:00&endTime=2018-01-01 00:00:00
		按照ID查询 /courses?id=1
		按照学生id查询 /courses?studentId=1&beginTime=2017-01-01 00:00:00&endTime=2018-01-01 00:00:00

		{
		    "count": 2,
		    "data": [
		        {
		            "id": 1,
		            "name": "语文课",
		            "beginTime": "2017-01-01 08:00:00",
		            "endTime": "2017-01-01 08:00:00",
		            "classId": 1,
		            "teacherId": 1,
		            "remark": "很厉害的外教课程",
		            "createTime": "2017-12-08 20:42:14"
		        },
		        {
		            "id": 2,
		            "name": "语文课2",
		            "beginTime": "2017-01-01 08:00:00",
		            "endTime": "2017-01-01 08:00:00",
		            "classId": 1,
		            "teacherId": 1,
		            "remark": "很厉害的外教课程",
		            "createTime": "2017-12-08 20:42:21"
		        }
		    ],
		    "code": 200,
		    "cost": 1.648
		}

分数信息

	新增：request/post: /scores
		
		{
			"name": "期中考试语文20170812",	
			"type": 1,		
			"studentId": 1,	
			"score": 87,	
			"pic": "http://www.baidu.com/1.jpg"
		}

		type>1考试，2作业

	删除：request/delete: /scores?id=1

	修改：request/put: /scores

		{
			"id": 1,
			"name": "期中考试语文20170812",	
			"type": 1,		
			"studentId": 1,	
			"score": 87,	
			"pic": "http://www.baidu.com/1.jpg"
		}

	查询：request/get: /scores?studentId=1&type=1&page=1&pageSize=10
						/scores?id=1

		{
		    "count": 2,
		    "data": [
		        {
		            "id": 2,
		            "name": "期中考试数学1",
		            "type": 1,
		            "studentId": 1,
		            "score": 87,
		            "pic": "http://www.baidu.com/1.jpg",
		            "createTime": "2017-12-09 01:28:48"
		        },
		        {
		            "id": 3,
		            "name": "期中考试语文1",
		            "type": 1,
		            "studentId": 1,
		            "score": 87,
		            "pic": "http://www.baidu.com/1.jpg",
		            "createTime": "2017-12-09 01:28:51"
		        }
		    ],
		    "code": 200,
		    "cost": 0.359
		}

图片上传：
	
	新增：request/post: /files
		form-data
		file: xxx.jpg

		response:
		{
		    "cost": 0.182,
		    "code": 200,
		    "url": "https://www.kehue.com/nuo/19db8ea2af2d48dcb0accd4edffddba1.jpeg"
		}






