package com.android.utli;

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
import org.apache.http.protocol.HTTP;

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
		httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
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
	
	private String readstream(InputStream in) 
	{
		StringBuffer resultString = new StringBuffer() ;
		try {
			BufferedReader inbuff = new BufferedReader(new InputStreamReader(in,"GB2312"));
			String line = "";
			while ((line = inbuff.readLine()) != null){
				resultString.append(line);
			}

		}catch (Exception e) {
		}
		return resultString.toString();
	}
		
}
