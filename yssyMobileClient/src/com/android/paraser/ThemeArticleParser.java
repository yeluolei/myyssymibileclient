package com.android.paraser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.Html;


public class ThemeArticleParser {
	String content;
	
	
	public ThemeArticleParser(String content) {
		this.content = content;
	}
	public String GetPre(int cnt){
		int headpos = 0;
		int tailpos = 0;
		String result;
		for(int i = 1; i <= cnt; ++i){
			headpos = content.indexOf("<pre>",tailpos+1);
			tailpos = content.indexOf("</pre>",tailpos+1);
		}
		result = content.substring(headpos+5, tailpos);
		return result;
		
	}
	
	public List<Map<String,Object>> Parse(){
		int headpos= 0;
		int tailpos = 0;
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		while( (headpos = content.indexOf("<pre>",tailpos+1)) != -1){
			Map<String,Object> temp = new HashMap<String,Object> ();
			tailpos = content.indexOf("</pre>",tailpos+1);
			String article = content.substring(headpos+5, tailpos);
			
			temp = GetInformation(article);
			//temp.put("time", "test");
			//temp.put("author", "test");
			//temp.put("content", Html.fromHtml( content.substring(headpos+5, tailpos).replace("\n", "<br>")));
			result.add(temp);
		}
		return result;
	}
	
	private Map<String,Object> GetInformation(String articleString){
		Map<String,Object> result = new HashMap<String,Object> ();
		int headpos = 0;
		int tailpos = 0;
		headpos = articleString.indexOf("<a",tailpos);
		tailpos = articleString.indexOf(">",headpos);
		headpos = articleString.indexOf("href=",headpos);
		String addr = articleString.substring(headpos + 6, tailpos - 1);
		headpos = articleString.indexOf(" ",tailpos);
		tailpos = articleString.indexOf(" ",headpos + 1);
		String author = articleString.substring(headpos, tailpos);
		headpos = tailpos + 1;
		if((tailpos = articleString.indexOf("Re:",headpos)) == -1){
			tailpos = articleString.indexOf("¡ð",headpos);
		}
		
		String time = articleString.substring(headpos, tailpos);
		headpos = tailpos;
		tailpos = articleString.indexOf("<font color=\"808080\">",headpos);
		if(tailpos == -1) {
			tailpos = articleString.length();
		}
		else{
			for(int i = 0; i < 4; ++i){
				tailpos = articleString.indexOf("\n",headpos);
				if(tailpos == -1){
					tailpos = articleString.length();
					break;
				}
			}
		}
		//articleString = articleString.substring(tailpos);
		result.put("time", time);
		result.put("author", author);
		result.put("content", Html.fromHtml( articleString.substring(headpos,tailpos).replace("\n", "<br>")));
		return result;
	}
}
