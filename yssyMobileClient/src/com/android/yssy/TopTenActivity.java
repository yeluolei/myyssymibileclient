package com.android.yssy;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TopTenActivity extends Activity {
	//所有的view控件在这里声明
	private Button getTopTen ;
	private TextView toptenTextView;
	private ListView toptenlist;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topten);
		//view控件在这里定义
		getTopTen = (Button)findViewById(R.id.gettopten);
		toptenTextView = (TextView)findViewById(R.id.toptentext);
		toptenlist = (ListView)findViewById(R.id.toptenlist);
		
		TopTenParser parser = new TopTenParser();
		List<Map<String, String>>postItems = parser.Parser();
		SimpleAdapter adapter = new SimpleAdapter(TopTenActivity.this,postItems,R.layout.toptenlistitem,
				                 new String[]{"Title","Board","AuthorID"},
				                 new int[]{R.id.title ,R.id.board,R.id.authorid});
		toptenlist.setAdapter(adapter);
	}
}
