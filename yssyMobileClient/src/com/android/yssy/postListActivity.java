package com.android.yssy;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.paraser.PostListParser;
import com.android.paraser.PostParser;
import com.android.paraser.TopicPostListParser;
import com.android.uiadapter.PostListAdapter;
import com.android.utli.utli;

public class PostListActivity extends Activity{
	private ListView postlistview;
	private LinearLayout nextpage,prepage,topicmode,more;
	private AutoCompleteTextView boards;
	private Button gotoButton;
	private TextView activitytitleTextView;
	private RelativeLayout processLayout;
	private RelativeLayout boardsarealLayout;
	private ImageButton areadownButton;

	private PostParser parser;
	private PostListAdapter adapter;

	private String url;

	private final int MENU_REPLY = 10;
	private final int MENU_VIEW = 11;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.v("����", "post");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postlist);

		boards = (AutoCompleteTextView)findViewById(R.id.boads);
		gotoButton = (Button)findViewById(R.id.gotoboard);
		activitytitleTextView = (TextView)findViewById(R.id.activitytitle);
		postlistview = (ListView)findViewById(R.id.postlist);
		nextpage = (LinearLayout)findViewById(R.id.nextpage);
		prepage = (LinearLayout)findViewById(R.id.prepage);
		topicmode = (LinearLayout)findViewById(R.id.topicmode);
		more = (LinearLayout)findViewById(R.id.more);
		areadownButton = (ImageButton)findViewById(R.id.openarea);
		boardsarealLayout = (RelativeLayout)findViewById(R.id.boardarea);
		processLayout = (RelativeLayout)findViewById(R.id.progresslayout);

		postlistview.setOnItemClickListener(gotopost);
		postlistview.setOnCreateContextMenuListener(listmenu);
		prepage.setOnClickListener(prepageClickListener);
		nextpage.setOnClickListener(nextpageClickListener);
		topicmode.setOnClickListener(modeClickListener);
		areadownButton.setOnClickListener(areadownClickListener);
		gotoButton.setOnClickListener(gotoboards);

		String[] boardnameStrings = getResources().getStringArray(R.array.boards);
		ArrayAdapter<String> boadersadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,boardnameStrings);
		boards.setAdapter(boadersadapter);

		Bundle bundle = this.getIntent().getExtras();
		url = bundle.getString("url");
		init();
	}

	private void init() 
	{	
		String titleString = url;
		ListLoad listLoad = new ListLoad();
		if (utli.topicmode == true) 
		{
			titleString += "-����ģʽ";
			listLoad.execute("http://bbs.sjtu.edu.cn/bbswaptdoc,board,"+url+".html");
		}
		else {
			titleString += "-һ��ģʽ";
			listLoad.execute("http://bbs.sjtu.edu.cn/bbswapdoc?board="+url);
		}
		activitytitleTextView.setText(titleString);
	}

	// ǰ������������ʾ����
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

	// ģʽ�л�
	public OnClickListener modeClickListener = new OnClickListener() 
	{
		@Override
		public void onClick(View arg0) {
			utli.topicmode = !utli.topicmode;
			init();
			//			Intent intent = new Intent(PostListActivity.this,TopicPostListActivity.class);
			//			Bundle bundle = new Bundle();
			//			bundle.putString("url", url);
			//			intent.putExtras(bundle);
			//			startActivity(intent);
			//			PostListActivity.this.finish();
			//			Log.v("�л�Ϊ", String.valueOf(utli.topicmode));
		}
	};

	// ��һҳ
	public OnClickListener nextpageClickListener = new OnClickListener() 
	{
		@Override
		public void onClick(View arg0) {
			if (parser.getNextlink()==null) {
				Toast.makeText(getApplicationContext(),"�Ѿ�����ҳ!",Toast.LENGTH_SHORT).show();
			}
			else {
				ListLoad listLoad = new ListLoad();
				String Link = "http://bbs.sjtu.edu.cn/"+parser.getNextlink();
				listLoad.execute(Link);
				Log.v("��һҳ", Link);
			}
		}
	};

	// ��һҳ
	public OnClickListener prepageClickListener = new OnClickListener() 
	{
		@Override
		public void onClick(View arg0) {
			if (parser.getPrelink() ==null) {
				Toast.makeText(getApplicationContext(),"�Ѿ������һҳ!",Toast.LENGTH_SHORT).show();
			}
			else {
				ListLoad listLoad = new ListLoad();
				String Link = "http://bbs.sjtu.edu.cn/"+parser.getPrelink();
				listLoad.execute(Link);
				Log.v("��һҳ", Link);
			}
		}
	};

	// ������ת��Activity��û��
	public OnItemClickListener gotopost = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		{
			@SuppressWarnings("unchecked")
			String Link =String.valueOf(((Map<String, Object>)
					postlistview.getItemAtPosition(position)).get("Link"));
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			if(utli.topicmode==true) {
				intent.setClass(PostListActivity.this,ThemeArticleActivity.class);
				bundle.putString("url","http://bbs.sjtu.edu.cn"+Link);
			}else {
				// TODO ��ת��һ��ģʽ�����Ķ�
			}
			intent.putExtras(bundle);
			startActivity(intent);
			PostListActivity.this.finish();
		}
	};


	// ΪList��ӵ����˵�
	public OnCreateContextMenuListener listmenu = new OnCreateContextMenuListener() {
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuinfo) {
			menu.add(Menu.NONE,MENU_REPLY,0,R.string.reply).setOnMenuItemClickListener(replyItemClickListener);
			menu.add(Menu.NONE,MENU_VIEW,1,R.string.view).setOnMenuItemClickListener(viewItemClickListener);
		}
	};


	// ������ť
	// �ظ�����ѡ��
	public OnMenuItemClickListener replyItemClickListener = new OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem menuitem) {
			AdapterView.AdapterContextMenuInfo ifo = (AdapterView.AdapterContextMenuInfo)menuitem.getMenuInfo();

			return false;
		}
	};


	// ������ť
	// �鿴����ѡ��
	public OnMenuItemClickListener viewItemClickListener = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem menuitem) {
			AdapterView.AdapterContextMenuInfo ifo = (AdapterView.AdapterContextMenuInfo)menuitem.getMenuInfo();
			@SuppressWarnings("unchecked")
			String Link =String.valueOf(((Map<String, Object>)
					postlistview.getItemAtPosition(ifo.position)).get("Link"));
			Intent intent = new Intent(PostListActivity.this,ThemeArticleActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("url","http://bbs.sjtu.edu.cn"+Link);
			intent.putExtras(bundle);
			startActivity(intent);
			PostListActivity.this.finish();
			return false;
		}
	};


	// ��ȡ�����б�
	private class ListLoad extends AsyncTask<String,Integer,List<Map<String, Object>>>
	{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			processLayout.setVisibility(View.VISIBLE);
			if (adapter != null) {
				adapter.clear();
				adapter.notifyDataSetChanged();
			}
		}

		@Override
		protected List<Map<String, Object>> doInBackground(String... URL) {
			if (utli.topicmode == true) {
				parser = new TopicPostListParser();
			}
			else {
			    parser = new PostListParser();
			}
			List<Map<String, Object>>postlist;
			try {
				postlist = parser.parser(URL[0]).getPostItems();
			} catch (Exception e) {
				return null;
			}
			return postlist;
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			super.onPostExecute(result);
			adapter = new PostListAdapter(PostListActivity.this,result);
			postlistview.setAdapter(adapter);
			processLayout.setVisibility(View.GONE);
		}
	}


	// ǰ�����水ť������
	public OnClickListener gotoboards= new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			url=boards.getText().toString();
			init();
			//			if (utli.topicmode == true)
			//			{
			//				intent.setClass(PostListActivity.this, TopicPostListActivity.class);
			//			}
			//			else {
			//				intent.setClass(PostListActivity.this,PostListActivity.class);
			//			}
			//			intent.putExtras(bundle);
			//			startActivity(intent);
			//			PostListActivity.this.finish();
		}
	};
}
