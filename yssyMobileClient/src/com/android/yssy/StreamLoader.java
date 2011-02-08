//package com.android.yssy;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//
///*
// * 单件
// * 载入url
// * 返回网页stream
// */
//public class StreamLoader {
//	public static StreamLoader sm;
//	public static StreamLoader getInstance()
//	{
//		if(sm == null)
//		{
//			sm = new StreamLoader();
//		}
//		return sm;
//	}
//	
//	public String load (String urladr) throws Exception
//	{
//		StringBuffer buffer = new StringBuffer();
//		try{        		
//			URL url = new URL(urladr);
//			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//			connection.setRequestMethod("GET");  
//			int responseCode = connection.getResponseCode();
//			if (responseCode == HttpURLConnection.HTTP_OK){
//				InputStream in = connection.getInputStream(); 
//				BufferedReader inbuff = new BufferedReader(new InputStreamReader(in,"GB2312"));
//				
//				String line = "";
//				while ((line = inbuff.readLine()) != null){
//					buffer.append(line);
//				}
//			}
//		}catch (Exception e) {
//			throw e;
//		}
//		return buffer.toString();
//	}
//}
