package com.bbs.util;
/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Net {
	private static Net net;
	private DefaultHttpClient client;

	private Net() 
	{
		client = new DefaultHttpClient();
	}
	public static Net getInstance() 
	{
		if (net == null) 
		{
			net = new Net();
		}
		return net;
	}

	public void clear() 
	{
		client = new DefaultHttpClient();
	}

	public String get(String URL) throws Exception 
	{
		String resultString;
		HttpGet sourceaddr= new HttpGet(URL);
		try {
			HttpResponse httpResponse = client.execute(sourceaddr);
			if (httpResponse.getStatusLine().getStatusCode()==200) 
			{
				resultString = readstream(httpResponse.getEntity().getContent());
			}
			else {
				throw new Exception("can't connect the network");
			}
			return resultString.toString();
		}catch (Exception e) {
			throw e;
		}
	}

	public String post(String URL,List <NameValuePair> params) throws Exception 
	{
		String resultString;
		try {
			HttpPost httpRequest = new HttpPost(URL);
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "GB2312"));  
			HttpResponse httpResponse = client.execute(httpRequest);
			if(httpResponse.getStatusLine().getStatusCode() == 200)    
			{   
				resultString= readstream(httpResponse.getEntity().getContent());
			}else {
				throw new Exception("can't connect the network");
			}
			return resultString;
		}catch (Exception e) {
			throw e;
		}
	}

	public boolean checknetwork(Context context) 
	{
		ConnectivityManager connectivity = (ConnectivityManager) context  
		.getSystemService(Context.CONNECTIVITY_SERVICE);  
		if (connectivity != null) {  
			NetworkInfo info = connectivity.getActiveNetworkInfo();  
			if (info != null) {  
				if (info.getState() == NetworkInfo.State.CONNECTED) {  
					return true;  
				}  
			}  
		}  
		return false;  
	}
	
	private String readstream(InputStream in) 
	{
		StringBuffer resultString = new StringBuffer() ;
		try {
			BufferedReader inbuff = new BufferedReader(new InputStreamReader(in,"GB2312"));
			String line = "";
			while ((line = inbuff.readLine()) != null){
				resultString.append('\n');
				resultString.append(line);
			}

		}catch (Exception e) {
		}
		return resultString.toString();
	}
}
