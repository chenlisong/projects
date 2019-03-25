package com.duolanjian.java.market.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.yinuo.util.HttpUtil;

public class HttpClientTest {

	@Test
    public void testGet() {
        //百度天气的api
        //String url1 = "http://api.map.baidu.com/telematics/v3/weather?location=%E5%8C%97%E4%BA%AC&output=json&ak=W69oaDTCfuGwzNwmtVvgWfGH";
        //String url1 = "http://localhost:8080/wechat/test/view2/你好世界";
        String url1 = "https://api.weixin.qq.com/sns/jscode2session?appid=wx254e077517572b8a&secret=d21ea703e91c9cae25dff51d2aeb177b&js_code=1&grant_type=authorization_code";
        String result1= "";
        try{
        	result1 = HttpUtil.sendGet(url1);
        }catch(Exception e) {
        	e.printStackTrace();
        }
    	System.out.println(result1);
        //输出{"param":"你好世界"}
    }
	
	@Test
	public void printTime() {
		System.out.println(UUID.randomUUID().toString());
		
		Date date = new Date(0);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
	}
	
    @Test
    public void testPost() throws UnsupportedEncodingException{
        String url = "http://localhost:8080/wechat/test/view";
        Map<String,String> map = new HashMap<String,String>();
        map.put("param1", "你好世界");
        map.put("param2", "哈哈");
        String result = HttpUtil.sendPost(url, map);
        System.out.println(result);
        //输出结果{"param1":"你好世界","param2":"哈哈"}

    }

    @Test
    public void testPost1() throws UnsupportedEncodingException{
        String url = "http://localhost:8080/wechat/test/view3";
        String result = HttpUtil.sendPost(url);
        System.out.println(result);
        //输出结果{"status":"success"}

    }

}
