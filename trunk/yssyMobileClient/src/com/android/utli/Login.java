package com.android.utli;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class Login {
	public boolean login(String UserName,String Password) throws Exception
	{
		List <NameValuePair> params = new ArrayList <NameValuePair>();   
		params.add(new BasicNameValuePair("id",UserName));   
		params.add(new BasicNameValuePair("pw", Password)); 
		String sourceString = Net.getInstance().post("http://bbs.sjtu.edu.cn/bbswaplogin", params);
		return checklogin(sourceString);
	}
	
	/**
	 * @param sourceString
	 * @return
	 */
	private boolean checklogin(String sourceString) 
	{
		return true;
	}
}
