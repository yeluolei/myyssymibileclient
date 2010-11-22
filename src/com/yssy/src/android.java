package com.yssy.src;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.security.KeyStore.LoadStoreParameter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.net.http.*;
import android.R.bool;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.webkit.*;

public class android extends Activity {

	private TextView tView ;
	private Button connectButton;
	private Button loginbutton;
	private EditText username;
	private EditText passwd;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tView = (TextView)findViewById(R.id.TextView);
		connectButton = (Button)findViewById(R.id.connect);
		loginbutton = (Button)findViewById(R.id.Button01);
		username = (EditText)findViewById(R.id.EditText01);
		passwd = (EditText)findViewById(R.id.EditText02);

		connectButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				load("http://bbs.sjtu.edu.cn/file/bbs/mobile/index.htm");
			}
		});

		loginbutton.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
			
				try{  
					String uriAPI = "http://bbs.sjtu.edu.cn/bbswaplogin";   
					HttpPost httpRequest = new HttpPost(uriAPI);
					DefaultHttpClient httpclient = new DefaultHttpClient(); 
					List <NameValuePair> params = new ArrayList <NameValuePair>();   
					params.add(new BasicNameValuePair("id", username.getText().toString()));   
					params.add(new BasicNameValuePair("pw", passwd.getText().toString())); 

					/* �������������������*/  
					httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));   
					/*�������󲢵ȴ���Ӧ*/  
					HttpResponse httpResponse = httpclient.execute(httpRequest);   
					/*��״̬��Ϊ200 ok*/  
					if(httpResponse.getStatusLine().getStatusCode() == 200)    
					{   
						/*����������*/  
						String strResult = EntityUtils.toString(httpResponse.getEntity());
						String newString = new String(strResult.getBytes(), "UTF-8");
						tView.setText(newString);
						
//						 ResponseHandler<String> responseHandler = new BasicResponseHandler();  
//					        String responseBody = "";  
//					        HttpGet  httpget= new HttpGet("http://bbs.sjtu.edu.cn/file/bbs/mobile/top100.html"); 
//					        try {  
//					            responseBody = httpclient.execute(httpget, responseHandler);  
//					            tView.setText(responseBody);
//					        } catch (Exception e) {  
//					            e.printStackTrace();  
//					            responseBody = null;  
//					        } finally {  
//					            httpget.abort();  
//					            httpclient.getConnectionManager().shutdown();  
//					        }  
						//load("http://bbs.sjtu.edu.cn/file/bbs/mobile/top100.htm");
					}   
					else   
					{   
						tView.setText("Error Response: "+httpResponse.getStatusLine().toString());   
					}   
				}   
				catch (Exception e) {
					tView.setText(e.getMessage().toString());   
					e.printStackTrace();    
				}   
				}
			}
		);
	}
	
	
	
	public void load (String urladr)
	{
		try{        		
			URL url = new URL(urladr);
			tView.setText(url.toString());
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");  
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK){
				InputStream in = connection.getInputStream(); 

				BufferedReader inbuff = new BufferedReader(new InputStreamReader(in,"GB2312"));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = inbuff.readLine()) != null){
					buffer.append(line);
				}
				tView.setText(buffer.toString());
			}
		}catch (Exception e) {
			tView.setText(e.getMessage().toString());
			e.printStackTrace();
		}
	}
}




	//		try{
	//		URL url=new URL("http://bbs.sjtu.edu.cn/bbswaplogin");
	//	    HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
	//	     
	//	     ////������������
	//	     httpConn.setDoOutput(true);//ʹ�� URL ���ӽ������
	//	     httpConn.setDoInput(true);//ʹ�� URL ���ӽ�������
	//	     httpConn.setUseCaches(false);//���Ի���
	//	     httpConn.setRequestMethod("POST");//����URL���󷽷�
	//	     String requestString = "�ͷ���Ҫ��������ʽ���͵�����˵�����...";
	//	     
	//	     //������������
	//	    //��������ֽ����ݣ������������ı��룬���������������˴����������ı���һ��
	//	          byte[] requestStringBytes = requestString.getBytes("GB2312");
	//	          httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
	//	          httpConn.setRequestProperty("origin", "http://bbs.sjtu.edu.cn");
	//	          httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	//	          httpConn.setRequestProperty("Accept", "application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
	//	 
	//	          httpConn.setRequestProperty("user_Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.10 (KHTML, like Gecko) Chrome/8.0.552.18 Safari/534.10");
	//	          httpConn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
	//	          httpConn.setRequestProperty("Connection", "Keep-Alive");// ά�ֳ�����
	//	          httpConn.setRequestProperty("Accept-Charset", "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.3");
	//	          //
	//	          httpConn.setRequestProperty("id", username.getText().toString());
	//	          httpConn.setRequestProperty("pw", passwd.getText().toString());
	//	          
	//	          //�������������д������
	//	          OutputStream outputStream = httpConn.getOutputStream();
	//	          outputStream.write(requestStringBytes);
	//	          outputStream.close();
	//	         //�����Ӧ״̬
	//	          int responseCode = httpConn.getResponseCode();
	//	          if(HttpURLConnection.HTTP_OK == responseCode){//���ӳɹ�
	//	           
	//	           //����ȷ��Ӧʱ��������
	//	           StringBuffer sb = new StringBuffer();
	//	              String readLine;
	//	              BufferedReader responseReader;
	//	             //������Ӧ�����������������Ӧ������ı���һ��
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