package com.android.yssy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;


/*
 * Board,LinkURL,AuthorID,Title
 * 
 */
public class TopTenParser  {
	public List<Map<String, String>> Parser() {
        List<Map<String, String>> PostItems = new ArrayList<Map<String, String>>();
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
    			PostItems.add(postItem);
    		}
        }catch(Exception e)
        {
        	Log.e("TopTenParser", e.getMessage());
        }
		return PostItems;
	}

}
