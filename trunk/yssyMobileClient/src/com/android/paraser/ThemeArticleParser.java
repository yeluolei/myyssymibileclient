package com.bbs.paraser;

/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.Html;
import android.util.Log;

import com.bbs.util.Net;


public class ThemeArticleParser {
	private String content;
	private List<String>pages;
	public List<String> getPages() {
		return pages;
	}

	public List<Map<String, Object>> getArticles() {
		return articles;
	}

	private List<Map<String,Object>> articles;
	public ThemeArticleParser() 
	{
		pages = new ArrayList<String>();
		articles = new ArrayList<Map<String,Object>>();
	}
/*	public String GetPre(int cnt){
		int headpos = 0;
		int tailpos = 0;
		String result;
		for(int i = 1; i <= cnt; ++i){
			headpos = content.indexOf("<pre>",tailpos+1);
			tailpos = content.indexOf("</pre>",tailpos+1);
		}
		result = content.substring(headpos+5, tailpos);
		return result;
		
	}*/
	
	// time , author , content , link
	public void parser(String URL){
		try {
			this.content = Net.getInstance().get(URL);
		} catch (Exception e) {
			Log.e("ThemeArticleParser","获取源码错误");
			
		}
		int headpos= 0;
		int tailpos = 0;
		int temppos = 0;
		
		headpos = content.indexOf("<hr>")+4;
		tailpos = content.indexOf("<pre>",headpos);
		
		
		while (headpos<tailpos) {
			headpos = content.indexOf("href=",headpos)+6;
			temppos = content.indexOf("'>",headpos);
			if (temppos > tailpos) 
			{
				temppos = content.indexOf("\">",headpos);
			}
			String Link = content.substring(headpos,temppos);
			headpos = temppos + 2;
			temppos = content.indexOf("</a>",headpos);
			String linktitle = content.substring(headpos,temppos);
			if (!(linktitle.equals("上一页")||linktitle.equals("下一页")||linktitle.equals("全部显示")))
			{
				pages.add(Link);
			}
			headpos = temppos + 4;
		}
		
		tailpos = tailpos - 1;
		while( (headpos = content.indexOf("<pre>",tailpos+1)) != -1){
			Map<String,Object> temp = new HashMap<String,Object> ();
			tailpos = content.indexOf("</pre>",headpos);
			String article = content.substring(headpos+5, tailpos);
			
			temp = GetInformation(article);
			articles.add(temp);
		}
	}
	
	private Map<String,Object> GetInformation(String articleString){
		Map<String,Object> result = new HashMap<String,Object> ();
		int headpos = 0;
		int tailpos = 0;
		headpos = articleString.indexOf("<a",tailpos);
		tailpos = articleString.indexOf("'>",headpos);
		headpos = articleString.indexOf("href=",headpos);
		String Link = articleString.substring(headpos + 6, tailpos);
		headpos = articleString.indexOf(" ",tailpos);
		tailpos = articleString.indexOf(" ",headpos + 1);
		String author = articleString.substring(headpos, tailpos);
		headpos = tailpos + 1;
		if((tailpos = articleString.indexOf("Re:",headpos)) == -1){
			tailpos = articleString.indexOf("○",headpos);
		}
		
		String time = articleString.substring(headpos, tailpos);
		headpos = tailpos;
		tailpos = articleString.indexOf("<font color=\"808080\">",headpos);
		if(tailpos == -1) {
			tailpos = articleString.length();
		}
		else{
			for(int i = 0; i < 4; ++i){
				tailpos = articleString.indexOf("\n",tailpos+1);
				if(tailpos == -1){
					tailpos = articleString.length();
					break;
				}
			}
		}
		result.put("link",Link);
		result.put("time", time);
		result.put("author", author);
		result.put("content", Html.fromHtml( articleString.substring(headpos,tailpos).replace("\n", "<br>")));
		return result;
	}
}
