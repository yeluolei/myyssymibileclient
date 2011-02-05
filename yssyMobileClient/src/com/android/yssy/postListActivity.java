package com.android.yssy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class postListActivity extends Activity{
	private ListView postlistview;
	private LinearLayout nextpage,prepage,topicmode,more;
	
	private List<Map<String, String>> postItems;
	List<String>BoardMasters;
	String prelink;
	String nextlink;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.v("进入", "post");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postlist);
		
		postlistview = (ListView)findViewById(R.id.postlist);
		nextpage = (LinearLayout)findViewById(R.id.nextpage);
		prepage = (LinearLayout)findViewById(R.id.prepage);
		topicmode = (LinearLayout)findViewById(R.id.topicmode);
		more = (LinearLayout)findViewById(R.id.more);
		
		Bundle bundle = this.getIntent().getExtras();
		init(bundle.getString("url"));
	}
	
	private void init(String URL) 
	{
		postItems = new ArrayList<Map<String,String>>();
		BoardMasters = new ArrayList<String>();
		prelink = "";
		nextlink = "";
		prase(URL);
		
		SimpleAdapter adapter = new SimpleAdapter(postListActivity.this,postItems,
				R.layout.postlistitem,
				new String[]{"PostIndex","type","title","authorID","postTime"},
				new int[] {R.id.postindex,R.id.posttype,R.id.posttitle,R.id.postauthorid,R.id.posttime});
		postlistview.setAdapter(adapter);
	}
	
	// PostIndex , authorID , postTime ,Link ,type ,title
	private void prase(String URL) 
	{
		try {
		String sourceString = StreamLoader.getInstance().load(URL);
		int pastpos1,pastpos2;
		int prepos = 0;
		
		// 获得板主名单
		pastpos1 = sourceString.indexOf("<hr>",prepos);
		pastpos2 = sourceString.indexOf("</br>",pastpos1);
		while((pastpos1 = sourceString.indexOf("<a",pastpos1))<pastpos2) 
		{
			prepos = sourceString.indexOf(">",pastpos1)+1;
			pastpos1 = sourceString.indexOf("</a>",pastpos1);
			BoardMasters.add(sourceString.substring(prepos,pastpos1));
		}
		
		// 获得上一页和下一页
		pastpos1 = pastpos2;
		pastpos2 = sourceString.indexOf("<i",pastpos2);
		while((pastpos1 = sourceString.indexOf("<a",pastpos1))<pastpos2) 
		{
			prepos = sourceString.indexOf("href=",pastpos1)+5;
			pastpos1 = sourceString.indexOf(">",prepos);
			if (sourceString.substring(pastpos1+1, sourceString.indexOf("</",pastpos1))== "上一页")
			{
				prelink = sourceString.substring(prepos,pastpos1);
			}
			else 
			{
				nextlink = sourceString.substring(prepos,pastpos1);
			}
		}
		
		// 获得文章列表
		prepos = sourceString.indexOf("<hr>",pastpos2)+4;
		pastpos2 = sourceString.indexOf("<hr/>",prepos);
		
		while(prepos < pastpos2) 
		{
			Map<String, String> map = new HashMap<String, String>();
			pastpos1 = sourceString.indexOf("<a",prepos);
			String PostIndex =sourceString.substring(prepos,pastpos1);
			if (PostIndex.startsWith("<font")) 
			{
				int p = PostIndex.indexOf(">",0)+1;
				int q = PostIndex.indexOf("</",p);
				PostIndex = PostIndex.substring(p+1,q-1);
			}
			else {
				PostIndex = PostIndex.replace("&nbsp;", " ").trim();
			}
			map.put("PostIndex", PostIndex);
			
			prepos = sourceString.indexOf(">",pastpos1)+1;
			pastpos1 = sourceString.indexOf("</",prepos);
			map.put("authorID",sourceString.substring(prepos,pastpos1));
			
			
			prepos = pastpos1 + 4;
			pastpos1 = sourceString.indexOf("</br>",prepos);
			String postTime = sourceString.substring(prepos,pastpos1);
			postTime = postTime.replaceAll("&nbsp;", " ").trim();
			map.put("postTime", postTime);
			
			prepos = sourceString.indexOf("href=",pastpos1)+5;
			pastpos1 = sourceString.indexOf(">",prepos);
			map.put("Link",sourceString.substring(prepos,pastpos1));
			
			
			prepos = pastpos1+1;
			pastpos1 = sourceString.indexOf("<",prepos);
			String title = sourceString.substring(prepos,pastpos1);
			String type;
			if (title.startsWith("Re:")) 
			{
				title = title.substring(4);
				type = "re";
			}
			else if (title.startsWith("○")) 
			{
				title = title.substring(1).trim();
				type = "new";
			}
			else 
			{
				type = "no";
			}
			
			map.put("type", type);
			map.put("title", title);
			postItems.add(map);
			
			prepos = sourceString.indexOf("<p>",pastpos1)+3;
		}
		}catch (Exception e) {
			Log.v("postlistview", e.getMessage());
		}
	}
}
