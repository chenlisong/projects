package com.yinuo.controller;

import com.yinuo.bean.User;
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

	private static String path = "/home/tomcat/apache-tomcat-default/webapps/ROOT/nuo/";

	//private static String path = "/opt/images/";

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

			String fileName = uuid + "." + imageName;
			try {
				file.transferTo(new File(path + fileName));
				result.put("url", "https://www.kehue.com/nuo/" + fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
    }
	
}
