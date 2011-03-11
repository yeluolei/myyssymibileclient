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

import com.bbs.util.Net;


/*
 * Board,LinkURL,AuthorID,Title
 * 
 */
public class TopTenParser  {
	private List<Map<String, String>>postItems;
	public TopTenParser() {
		postItems = new ArrayList<Map<String, String>>();
	}

	public TopTenParser parser() throws Exception {
        try{
            String sourceString = Net.getInstance().get("http://bbs.sjtu.edu.cn/file/bbs/mobile/top100.html");
    		int pastpos;
    		int prepos = 0;
    		for(int i= 0;i<10;i++)
    		{
    			pastpos  = sourceString.indexOf("target",prepos);
    			Map<String, String>postItem = new HashMap<String, String>();
    			prepos  = sourceString.indexOf('>',pastpos);
    			pastpos = sourceString.indexOf("</",prepos);
    			postItem.put("Board",sourceString.substring(prepos+1, pastpos).trim());
    			prepos = sourceString.indexOf("href=\"",pastpos);
    			pastpos = sourceString.indexOf("\"",prepos+6);
    			postItem.put("LinkURL",sourceString.substring(prepos+6, pastpos));
    			prepos  = sourceString.indexOf('>',pastpos);
    			pastpos = sourceString.indexOf("</",prepos);
    			postItem.put("Title",sourceString.substring(prepos+1, pastpos).trim());
    			prepos  = sourceString.indexOf('>',pastpos);
    			pastpos = sourceString.indexOf('<',prepos);
    			postItem.put("AuthorID",sourceString.substring(prepos+1, pastpos).trim());
    			postItems.add(postItem);
    		}
        }catch(Exception e)
        {
        	throw e;
        }
		return this;
	}
	
	public List<Map<String, String>> getPostItems() {
		return postItems;
	}

}
