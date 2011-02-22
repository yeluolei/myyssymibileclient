package com.android.yssy;

import com.android.paraser.PostParser;
import com.android.paraser.TopicPostListParser;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class TopicPostListActivity extends Activity{
	private ListView postlistview;
	private LinearLayout nextpage,prepage,topicmode,more;
	
	private PostParser parser;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postlist);
		postlistview = (ListView)findViewById(R.id.postlist);
		nextpage = (LinearLayout)findViewById(R.id.nextpage);
		prepage = (LinearLayout)findViewById(R.id.prepage);
		topicmode = (LinearLayout)findViewById(R.id.topicmode);
		more = (LinearLayout)findViewById(R.id.more);
		
		Bundle bundle = this.getIntent().getExtras();
		init("http://bbs.sjtu.edu.cn/bbswaptdoc,board,"+bundle.getString("url")+".html");
	}
	
	private void init(String URL) 
	{	
		parser = new TopicPostListParser();
		try {
		parser.parser(URL);
		}catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		SimpleAdapter adapter = new SimpleAdapter(TopicPostListActivity.this,parser.getPostItems(),
				R.layout.postlistitem,
				new String[]{"PostIndex","type","title","authorID","postTime"},
				new int[] {R.id.postindex,R.id.posttype,R.id.posttitle,R.id.postauthorid,R.id.posttime});
		postlistview.setAdapter(adapter);
	}
}
