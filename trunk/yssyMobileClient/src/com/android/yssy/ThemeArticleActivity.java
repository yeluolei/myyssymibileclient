package com.android.yssy;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ThemeArticleActivity extends Activity {

	
	private TextView text;
	private ListView content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.themearticle);
		
		String temp;
		try {
			temp = Net.getInstance().get("http://bbs.sjtu.edu.cn/bbswaptcon?board=SJTUNews&reid=1297183692");
			//text = (TextView)findViewById(R.id.TextView01);
			ThemeArticleParser parser = new ThemeArticleParser(temp);
			content = (ListView) findViewById(R.id.articlelist);
			SimpleAdapter adapter = new SimpleAdapter(ThemeArticleActivity.this,parser.Parse(), R.layout.themearticlelistitem, new String[]{"author","time","content"},new int[]{R.id.author, R.id.time, R.id.Content});
			content.setAdapter(adapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//text.setText("test");
	}
	
	
}
