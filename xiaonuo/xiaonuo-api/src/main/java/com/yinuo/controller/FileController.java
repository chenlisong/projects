package com.yinuo.controller;

import com.yinuo.bean.User;
import com.yinuo.util.MD5Util;
import com.yinuo.validation.NeedLogin;
import com.yinuo.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class FileController {

	@Autowired
	private Validation validation;

	//private static String path = "/home/tomcat/apache-tomcat-default/webapps/ROOT/";
	private static String path = "/opt/pic/";

	//private static String path = "/opt/images/";

	@RequestMapping(value="/api/get-token", method=RequestMethod.POST)
	public Object post1() {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("token", "12312312312312312312");
		return result;
	}

	@RequestMapping(value="/api/users", method=RequestMethod.POST)
	public Object post2() {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("flag", "ok");
		return result;
	}

	@NeedLogin
	@RequestMapping(value="/files", method=RequestMethod.POST)
    public Object post(User loginUser, @RequestParam(value="file",required=false) MultipartFile file){
		Map<String,Object> result = new HashMap<String, Object>();

		if(!file.isEmpty()) {
			String uuid = UUID.randomUUID().toString().replaceAll("-","");
			String contentType=file.getContentType();
			String imageName=contentType.substring(contentType.indexOf("/")+1);
			if(imageName != null && (imageName.contains("jpg") || imageName.contains("png") || imageName.contains("jpeg"))) {
			}else {
				return result;
			}

			String dir = Math.abs(uuid.hashCode()) % 100 + "/";

			String fileName = dir + uuid + "." + imageName;
			try {
				File target = new File(path + fileName);
				if(!target.getParentFile().exists()) {
					target.getParentFile().mkdir();
				}

				file.transferTo(target);
				result.put("url", "https://www.kehue.com/pic/" + fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
    }
	
}
