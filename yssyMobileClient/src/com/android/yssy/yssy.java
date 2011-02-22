package com.android.yssy;

import com.android.utli.utli;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class yssy extends Activity{
	private final int delay = 2000;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		Handler handler = new Handler();
		handler.postDelayed(new starthandler(), delay);
	}
	
	class starthandler implements Runnable
	{
		@Override
		public void run() {
			SharedPreferences sharedPreferences  = getSharedPreferences("config", MODE_PRIVATE);
			utli.userName = sharedPreferences.getString("username",null);
			utli.password = sharedPreferences.getString("password",null);
			utli.remember = sharedPreferences.getBoolean("remember", false);
			utli.auto = sharedPreferences.getBoolean("auto", false);
			utli.topicmode = sharedPreferences.getBoolean("topicmode", false);
			Intent intent = new Intent();
			if(utli.auto == false) {
			     intent.setClass(yssy.this,LoginActivity.class);
			}else {
				intent.setClass(yssy.this,LoginingActivity.class);
			}
			startActivity(intent);
			yssy.this.finish();
		}
	}
}




	//		try{
	//		URL url=new URL("http://bbs.sjtu.edu.cn/bbswaplogin");
	//	    HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
	//	     
	//	     ////设置连接属性
	//	     httpConn.setDoOutput(true);//使用 URL 连接进行输出
	//	     httpConn.setDoInput(true);//使用 URL 连接进行输入
	//	     httpConn.setUseCaches(false);//忽略缓存
	//	     httpConn.setRequestMethod("POST");//设置URL请求方法
	//	     String requestString = "客服端要以以流方式发送到服务端的数据...";
	//	     
	//	     //设置请求属性
	//	    //获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
	//	          byte[] requestStringBytes = requestString.getBytes("GB2312");
	//	          httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
	//	          httpConn.setRequestProperty("origin", "http://bbs.sjtu.edu.cn");
	//	          httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	//	          httpConn.setRequestProperty("Accept", "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
	//	 
	//	          httpConn.setRequestProperty("user_Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.10 (KHTML, like Gecko) Chrome/8.0.552.18 Safari/534.10");
	//	          httpConn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
	//	          httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
	//	          httpConn.setRequestProperty("Accept-Charset", "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3");
	//	          //
	//	          httpConn.setRequestProperty("id", username.getText().toString());
	//	          httpConn.setRequestProperty("pw", passwd.getText().toString());
	//	          
	//	          //建立输出流，并写入数据
	//	          OutputStream outputStream = httpConn.getOutputStream();
	//	          outputStream.write(requestStringBytes);
	//	          outputStream.close();
	//	         //获得响应状态
	//	          int responseCode = httpConn.getResponseCode();
	//	          if(HttpURLConnection.HTTP_OK == responseCode){//连接成功
	//	           
	//	           //当正确响应时处理数据
	//	           StringBuffer sb = new StringBuffer();
	//	              String readLine;
	//	              BufferedReader responseReader;
	//	             //处理响应流，必须与服务器响应流输出的编码一致
	//	              responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"GB2312"));
	//	              while ((readLine = responseReader.readLine()) != null) {
	//	               sb.append(readLine).append("\n");
	//	              }
	//	              responseReader.close();
	//	              tView.setText(sb.toString());
	//	          }
	//	    }catch(Exception e){
	//	    	tView.setText(e.getMessage().toString());
	//	        e.printStackTrace();
	//	    }
	//	   }



