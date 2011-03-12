package com.bbs.yssy;
/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bbs.paraser.TopTenParser;
import com.bbs.uiadapter.TopTenListAdapter;

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
	private TopTenListAdapter adapter ;

	private final int MENU_REFLASH = 0;
	private final int MENU_FAVLIST = 1;

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

	private class ListLoad extends AsyncTask<Void,Integer,Void>
	{
		@Override
		protected void onPreExecute() {
			progressLayout.setVisibility(View.VISIBLE);
			if(adapter != null) {
				adapter.clear();
				adapter.notifyDataSetChanged();
			}
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			parser = new TopTenParser();
			try {
				parser.parser();
			} catch (Exception e) {
				Log.e("TopTen",e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			adapter = new TopTenListAdapter(TopTenActivity.this,parser.getPostItems());
			toptenlist.setAdapter(adapter);
			progressLayout.setVisibility(View.GONE);
			super.onPostExecute(result);
		}
	}


	// 跳转到十大条目阅读
	public OnItemClickListener gototopicListener = new OnItemClickListener() 
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		{
			progressLayout.setVisibility(View.GONE);
			@SuppressWarnings("unchecked")
			Map<String,String>Item = ((Map<String,String>)toptenlist.getItemAtPosition(position));
			String Link = Item.get("LinkURL");
			Intent intent = new Intent(TopTenActivity.this,ThemeArticleActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("url","http://bbs.sjtu.edu.cn"+Link);
			bundle.putString("title",Item.get("Title"));
			intent.putExtras(bundle);
			startActivity(intent);
			//TopTenActivity.this.finish();
		};
	};


	public OnClickListener gotoboards= new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			progressLayout.setVisibility(View.GONE);
			boardsarealLayout.setVisibility(View.GONE);
			Intent intent= new Intent();
			Bundle bundle= new Bundle();
			bundle.putString("url", boards.getText().toString());
			intent.setClass(TopTenActivity.this,PostListActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			//TopTenActivity.this.finish();
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE,MENU_REFLASH,0,"刷新").setIcon(R.drawable.menu_refresh);
		menu.add(Menu.NONE,MENU_FAVLIST,1,"收藏夹").setIcon(R.drawable.menu_favlist);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case MENU_REFLASH:
			ListLoad listLoad = new ListLoad();
			listLoad.execute();
			break;
		case MENU_FAVLIST:
			Intent intent = new Intent(TopTenActivity.this,FavListActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
