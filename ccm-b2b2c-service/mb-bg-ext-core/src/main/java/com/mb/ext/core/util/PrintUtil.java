package com.mb.ext.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.mb.framework.util.log.LogHelper;

@Component
public class PrintUtil {
	
	private final static LogHelper logger = LogHelper.getInstance(PrintUtil.class.getName());
	
    
    /**使用飞鹅打印机打印小票
     * @param user - 飞鹅云后台注册用户名
     * @param uKey - 飞鹅云后台注册账号后生成的UKEY
     * @param sn - 	打印机编号
     * @param content - 打印内容
     * @return
     */
    public static String feiePrintMsg(String user, String uKey, String sn, String content) {
    	//通过POST请求，发送打印信息到服务器
		RequestConfig requestConfig = RequestConfig.custom()  
	            .setSocketTimeout(30000)//读取超时  
	            .setConnectTimeout(30000)//连接超时
	            .build();
		
		CloseableHttpClient httpClient = HttpClients.custom()
				 .setDefaultRequestConfig(requestConfig)
				 .build();	
		
	    HttpPost post = new HttpPost("http://api.feieyun.cn/Api/Open/");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("user",user));
		String STIME = String.valueOf(System.currentTimeMillis()/1000);
		nvps.add(new BasicNameValuePair("stime",STIME));
		nvps.add(new BasicNameValuePair("sig",signature(user,uKey,STIME)));
		nvps.add(new BasicNameValuePair("apiname","Open_printMsg"));//固定值,不需要修改
		nvps.add(new BasicNameValuePair("sn",sn));
		nvps.add(new BasicNameValuePair("content",content));
		nvps.add(new BasicNameValuePair("times","1"));//打印联数
		
		CloseableHttpResponse response = null;
		String result = null;
        try
        {
    	   post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
    	   logger.info("飞鹅云打印请求: "+content);
    	   response = httpClient.execute(post);
       	   int statecode = response.getStatusLine().getStatusCode();
       	   if(statecode == 200){
	        	HttpEntity httpentity = response.getEntity(); 
	            if (httpentity != null){
	            	//服务器返回的JSON字符串，建议要当做日志记录起来
	            	result = EntityUtils.toString(httpentity);
	            	logger.info("飞鹅云打印返回: "+result);
	            }
           }
       }
       catch (Exception e)
       {
    	   logger.error("飞鹅云打印方法异常: "+e.getMessage());
       }
       finally{
    	   try {
    		   if(response!=null){
    			   response.close();
    		   }
    	   } catch (IOException e) {
    		   e.printStackTrace();
    	   }
    	   try {
    		   post.abort();
    	   } catch (Exception e) {
    		   e.printStackTrace();
    	   }
    	   try {
    		   httpClient.close();
    	   } catch (IOException e) {
    		   e.printStackTrace();
    	   }
       }
       return result;
	  
	}
    //生成签名字符串
	private static String signature(String USER,String UKEY,String STIME){
		String s = DigestUtils.sha1Hex(USER+UKEY+STIME);
		return s;
	}
}
