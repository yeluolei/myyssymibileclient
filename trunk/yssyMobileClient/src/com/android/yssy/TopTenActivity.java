package com.android.yssy;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.paraser.TopTenParser;
import com.android.uiadapter.TopTenListAdapter;
import com.android.utli.utli;

public class TopTenActivity extends Activity {
	
	//所有的view控件在这里声明
	private ListView toptenlist;
	private AutoCompleteTextView boards;
	private TextView titleTextView;
	private Button gotoButton;
	private ImageButton areadownButton;
	private RelativeLayout progressLayout; 
	private RelativeLayout boardsarealLayout;
	
	private TopTenParser parser;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topten);
		//view控件在这里定义
		toptenlist = (ListView)findViewById(R.id.toptenlist);
		boards = (AutoCompleteTextView)findViewById(R.id.boads);
		titleTextView = (TextView)findViewById(R.id.activitytitle);
		gotoButton = (Button)findViewById(R.id.gotoboard);
		areadownButton = (ImageButton)findViewById(R.id.openarea);
		progressLayout = (RelativeLayout)findViewById(R.id.progresslayout);
		boardsarealLayout = (RelativeLayout)findViewById(R.id.boardarea);
		
		titleTextView.setText("本日十大");
		
		ListLoad listLoad = new ListLoad();
		listLoad.execute();
		
		String[] boardnameStrings = getResources().getStringArray(R.array.boards);
		ArrayAdapter<String> boadersadapter = new ArrayAdapter<String>(TopTenActivity.this,
				android.R.layout.simple_dropdown_item_1line,boardnameStrings);
		boards.setAdapter(boadersadapter);
		
		toptenlist.setOnItemClickListener(gototopicListener);
		gotoButton.setOnClickListener(gotoboards);
		areadownButton.setOnClickListener(areadownClickListener);
	}
	
	public OnClickListener areadownClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if (boardsarealLayout.getVisibility() == View.GONE) 
			{
				boardsarealLayout.setVisibility(View.VISIBLE);
			}
			else {
				boardsarealLayout.setVisibility(View.GONE);
			}
		}
	};
	
	private class ListLoad extends AsyncTask<Void,Integer,List<Map<String, String>>>
	{
		@Override
		protected List<Map<String, String>> doInBackground(Void... arg0) {
			parser = new TopTenParser();
			List<Map<String, String>> postList;
			try {
				 postList = parser.parser().getPostItems();
			} catch (Exception e) {
				return null;
			}
			return postList;
		}

		@Override
		protected void onPostExecute(List<Map<String, String>> result) {
			super.onPostExecute(result);
			TopTenListAdapter adapter = new TopTenListAdapter(TopTenActivity.this,result);
			toptenlist.setAdapter(adapter);
			progressLayout.setVisibility(View.GONE);
		}
	}
	
	
	public OnItemClickListener gototopicListener = new OnItemClickListener() 
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		{
			@SuppressWarnings("unchecked")
			String Link = ((Map<String,String>)toptenlist.getItemAtPosition(position)).get("LinkURL");
			Intent intent = new Intent(TopTenActivity.this,ThemeArticleActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("url","http://bbs.sjtu.edu.cn"+Link);
			intent.putExtras(bundle);
			startActivity(intent);
			TopTenActivity.this.finish();
		};
	};
	
	public OnItemLongClickListener longClickListener = new  OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
                    int position, long id) 
		{
			return true;
		}
	};
	
	public OnClickListener gotoboards= new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			Intent intent= new Intent();
			Bundle bundle= new Bundle();
			bundle.putString("url", boards.getText().toString());
			if (utli.topicmode == true)
			{
				intent.setClass(TopTenActivity.this, TopicPostListActivity.class);
			}
			else {
				intent.setClass(TopTenActivity.this,PostListActivity.class);
			}
			intent.putExtras(bundle);
			startActivity(intent);
			TopTenActivity.this.finish();
		}
	};
}
