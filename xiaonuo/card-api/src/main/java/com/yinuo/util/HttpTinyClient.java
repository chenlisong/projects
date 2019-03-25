/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yinuo.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpTinyClient {

    private static Logger logger = LoggerFactory.getLogger(HttpTinyClient.class);

    static final String DEFAULT_CHARSET = "UTF-8";

    static public HttpResult httpGet(String url, List<String> headers, List<Object> paramValues,
                                     long readTimeoutMs) throws IOException {
        String encodedContent = encodingParams(paramValues, DEFAULT_CHARSET);
        url += (null == encodedContent) ? "" : ("?" + encodedContent);

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout((int) readTimeoutMs);
            conn.setReadTimeout((int) readTimeoutMs);
            setHeaders(conn, headers, DEFAULT_CHARSET);

            conn.connect();
            int respCode = conn.getResponseCode();
            String resp = null;

            if (HttpURLConnection.HTTP_OK == respCode) {
                resp = IOTinyUtils.toString(conn.getInputStream(), DEFAULT_CHARSET);
            } else {
                resp = IOTinyUtils.toString(conn.getErrorStream(), DEFAULT_CHARSET);
            }
            return new HttpResult(respCode, resp);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return new HttpResult(400, "{}");
    }

    static private String encodingParams(List<Object> paramValues, String encoding)
            throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        if (null == paramValues) {
            return null;
        }

        for (Iterator<Object> iter = paramValues.iterator(); iter.hasNext(); ) {
            Object prefix = iter.next();
            Object after = iter.next();

            sb.append(prefix).append("=");
            if(prefix instanceof String && after instanceof String){
                sb.append(URLEncoder.encode(after.toString(), encoding));
            }else {
                sb.append(after);
            }
            if (iter.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    static private void setHeaders(HttpURLConnection conn, List<String> headers, String encoding) {
        if (null != headers) {
            for (Iterator<String> iter = headers.iterator(); iter.hasNext(); ) {
                conn.addRequestProperty(iter.next(), iter.next());
            }
        }
        //conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);

        //String ts = String.valueOf(System.currentTimeMillis());
        //conn.addRequestProperty("Metaq-Client-RequestTS", ts);
    }

    /**
     * @return the http response of given http post request
     */
    static public HttpResult httpPost(String url, List<String> headers, String encodedContent,
                                      long readTimeoutMs) throws IOException {
//        String encodedContent = encodingParams(paramValues, encoding);

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout((int) readTimeoutMs);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            setHeaders(conn, headers, DEFAULT_CHARSET);

            conn.getOutputStream().write(encodedContent.getBytes(DEFAULT_CHARSET));

            int respCode = conn.getResponseCode();
            String resp = null;

            if (HttpURLConnection.HTTP_OK == respCode) {
                resp = IOTinyUtils.toString(conn.getInputStream(), DEFAULT_CHARSET);
            } else {
                resp = IOTinyUtils.toString(conn.getErrorStream(), DEFAULT_CHARSET);
            }
            return new HttpResult(respCode, resp);
        } finally {
            if (null != conn) {
                conn.disconnect();
            }
        }
    }

    static public String httpPostDownloadFile(String url, List<String> headers, String encodedContent,
                                      long readTimeoutMs) throws IOException {

        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String filePath = "/opt/pic/qrcode/"+uuid+".png";

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout((int) readTimeoutMs);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            setHeaders(conn, headers, DEFAULT_CHARSET);

            conn.getOutputStream().write(encodedContent.getBytes(DEFAULT_CHARSET));

            int respCode = conn.getResponseCode();
            InputStream resp = null;

            if (HttpURLConnection.HTTP_OK == respCode) {
                resp = conn.getInputStream();

                file_put_contents(filePath, resp);

            } else {
                resp = conn.getErrorStream();
            }
            return "https://www.kehue.com/pic/qrcode/"+uuid+".png";
        } finally {
            if (null != conn) {
                conn.disconnect();
            }
        }
    }

    static public class HttpResult {
        final public int code;
        final public String content;

        public HttpResult(int code, String content) {
            this.code = code;
            this.content = content;
        }
    }


    public static void main(String[] args) throws Exception{

//        String url = "https://api.weixin.qq.com/cgi-bin/token";
//        String appid = "wx41226d6532cca43b";
//        String secret = "6e092ef0971283771027e557e5141ced";
//
//        List<Object> params = Arrays.asList(new Object[]{"grant_type", "client_credential", "appid", appid, "secret", secret});
//
//        HttpResult resp = HttpTinyClient.httpGet(url, null, params, 3000);
//        System.out.println(String.format("text: %s", resp.content));

        String url = "http://127.0.0.1:8082/users";
        String content = "{\"userId\":\"你好啊\"}";
//        List<String> headers = Arrays.asList(new String[]{"openid", "444", "Content-Type", "application/json"});
        List<String> headers = Arrays.asList(new String[]{"openid", "444", "Content-Type", "application/json"});
        HttpResult resp = HttpTinyClient.httpPost(url, headers, content, 300000);
        System.out.println(String.format("text: %s", resp.content));


	}

    public static void file_put_contents(String file_name,InputStream is){
        File file=new File(file_name);
        OutputStream os=null;
        try{
            os=new FileOutputStream(file);
            byte buffer[]=new byte[4*1024];

            int len = 0;
            while((len = is.read(buffer)) != -1){
                os.write(buffer,0,len);
            }

            os.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                os.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
