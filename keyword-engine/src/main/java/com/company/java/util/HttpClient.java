package com.company.java.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClient {

	/**
	 * 将map转换成key-value封装进body
	 * @param url
	 * @param paramsMap
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url,Map<String,String> paramsMap) throws ClientProtocolException, IOException{
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();  
		HttpPost httpPost = null ;
		try{
			httpPost = new HttpPost(url);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if(paramsMap != null && paramsMap.size() > 0){
				for(String str:paramsMap.keySet()){
					params.add(new BasicNameValuePair(str, paramsMap.get(str)));
				}
			}
			UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(params,"UTF-8");
			uefe.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(uefe);
			
			HttpResponse resp = httpclient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			if(resp.getStatusLine().getStatusCode() == 200){
            	result = EntityUtils.toString(entity);//取出应答字符串  
			}
		}catch(Exception e){
			result = e.getMessage();
		}finally{
			if(httpPost != null){
				httpPost.abort();
			}
		}
		return result;
	}
	/**
	 * 直接将json串赋值到body中。
	 * @param url
	 * @param jsonString
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String post(String url,String jsonString) throws ClientProtocolException, IOException{
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();  
		HttpPost httpPost = null ;
		try{
			httpPost = new HttpPost(url);
			StringEntity se = new StringEntity(jsonString,"UTF-8");
			httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
			httpPost.setEntity(se);
			
			HttpResponse resp = httpclient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			if(resp.getStatusLine().getStatusCode() == 200){
            	result = EntityUtils.toString(entity);//取出应答字符串  
			}
		}catch(Exception e){
			result = e.getMessage();
		}finally{
			if(httpPost != null){
				httpPost.abort();
			}
		}
		return result;
	}
	public static String get(String url){
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();  
		HttpGet httpGet = null ;
		try{
			httpGet = new HttpGet(url);
			httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
			
			HttpResponse resp = httpclient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			if(resp.getStatusLine().getStatusCode() == 200){
            	result = EntityUtils.toString(entity);//取出应答字符串  
			}
		}catch(Exception e){
			result = e.getMessage();
		}finally{
			if(httpGet != null){
				httpGet.abort();
			}
		}
		return result;
	}
	public static void main(String[] args) {
		String url = "http://127.0.0.1:8322/malformedword?text=%E5%B0%91%E6%95%B0%E6%84%8F%E8%A7%81++hd%E9%AB%98%E6%B8%85+%E7%A8%8D%E5%90%8E%E8%A1%A5%E5%85%85%E8%A7%86%E9%A2%91%E7%AE%80%E4%BB%8B+%E6%97%B6%E5%B0%9A%E7%94%9F%E6%B4%BB%2C%E9%A2%84%E5%91%8A%E7%89%87%2C%E7%BE%8E%E5%A5%B3%E6%98%8E%E6%98%9F%2C%E9%AB%98%E6%B8%85%E8%A7%86%E9%A2%91%2C%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1%2C%E5%BD%B1%E8%A7%86%E5%A8%B1%E4%B9%90%2C%E7%94%B5%E5%BD%B1%E8%A7%86%E9%A2%91youkurrterfrmb20";
		try {
			for(int i=0;i<10000;i++) {
				System.out.println(HttpClient.get(url));
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
