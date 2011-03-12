package com.bbs.yssy;

/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
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

import com.bbs.paraser.PostListParser;
import com.bbs.paraser.PostParser;
import com.bbs.paraser.TopicPostListParser;
import com.bbs.uiadapter.PostListAdapter;
import com.bbs.util.FavListEdit;
import com.bbs.util.Net;
import com.bbs.util.Utli;

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


	private final int MENU_CHANGEACCOUNT = 10;   // 切换帐号
	private final int MENU_NEWPOST = 11;         // 发表文章
	private final int MENU_RELOAD = 12;          // 刷新
	private final int MENU_RETURNTOPTEN = 13;    // 返回十大
	private final int MENU_FAV = 14 ;            // 收藏此版面
	private final int MENU_GOTOFAV = 15 ;        // 前往收藏夹
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.v("进入", "post");
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
		prepage.setOnClickListener(prepageClickListener);
		nextpage.setOnClickListener(nextpageClickListener);
		topicmode.setOnClickListener(modeClickListener);
		more.setOnCreateContextMenuListener(moremenu);
		more.setOnClickListener(moreClickListener);
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
		boardsarealLayout.setVisibility(View.GONE);
		String titleString = url;
		ListLoad listLoad = new ListLoad();
		if (Utli.topicmode == true) 
		{
			titleString += "-主题模式";
			listLoad.execute("http://bbs.sjtu.edu.cn/bbswaptdoc,board,"+url+".html");
		}
		else {
			titleString += "-一般模式";
			listLoad.execute("http://bbs.sjtu.edu.cn/bbswapdoc?board="+url);
		}
		activitytitleTextView.setText(titleString);
	}

	// 前往板面区域显示控制
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

	// 模式切换
	public OnClickListener modeClickListener = new OnClickListener() 
	{
		@Override
		public void onClick(View arg0) {
			Utli.topicmode = !Utli.topicmode;
			if (Utli.topicmode) {
				Toast.makeText(getApplicationContext(),"切换到主题模式",Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(getApplicationContext(),"切换到一般模式",Toast.LENGTH_SHORT).show();
			}
			init();
		}
	};

	// 下一页
	public OnClickListener nextpageClickListener = new OnClickListener() 
	{
		@Override
		public void onClick(View arg0) {
			if (parser.getNextlink()==null) {
				Toast.makeText(getApplicationContext(),"已经到首页!",Toast.LENGTH_SHORT).show();
			}
			else {
				ListLoad listLoad = new ListLoad();
				String Link = "http://bbs.sjtu.edu.cn/"+parser.getNextlink();
				listLoad.execute(Link);
				Log.v("下一页", Link);
			}
		}
	};

	// 上一页
	public OnClickListener prepageClickListener = new OnClickListener() 
	{
		@Override
		public void onClick(View arg0) {
			if (parser.getPrelink() ==null) {
				Toast.makeText(getApplicationContext(),"已经是最后一页!",Toast.LENGTH_SHORT).show();
			}
			else {
				ListLoad listLoad = new ListLoad();
				String Link = "http://bbs.sjtu.edu.cn/"+parser.getPrelink();
				listLoad.execute(Link);
				Log.v("上一页", Link);
			}
		}
	};

	// 跳转到文章阅读
	public OnItemClickListener gotopost = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		{
			@SuppressWarnings("unchecked")
			Map<String,Object> Item = (Map<String, Object>)postlistview.getItemAtPosition(position);
			String Link =String.valueOf(Item.get("Link"));
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			intent.setClass(PostListActivity.this,ThemeArticleActivity.class);
			bundle.putString("url","http://bbs.sjtu.edu.cn/"+Link);
			bundle.putString("title",String.valueOf(Item.get("title")));
			intent.putExtras(bundle);
			startActivity(intent);
			//PostListActivity.this.finish();
		}
	};

	// 点击弹出菜单
	protected OnClickListener moreClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			more.showContextMenu();
		}
	};

	// 为more添加弹出菜单
	public OnCreateContextMenuListener moremenu = new OnCreateContextMenuListener() {
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuinfo) {
			menu.add(Menu.NONE,MENU_NEWPOST,0,R.string.newpost).setOnMenuItemClickListener(newpostItemClickListener);
			menu.add(Menu.NONE,MENU_RELOAD,1,R.string.reload).setOnMenuItemClickListener(reloadItemClickListener);
			menu.add(Menu.NONE,MENU_CHANGEACCOUNT,2,R.string.changeaccount).setOnMenuItemClickListener(changeaccountItemClickListener);
			menu.add(Menu.NONE,MENU_RETURNTOPTEN,3,R.string.retop).setOnMenuItemClickListener(regotopItemClickListener);
			menu.add(Menu.NONE,MENU_FAV,4,R.string.fav).setOnMenuItemClickListener(addfavItemClickListener);
			menu.add(Menu.NONE,MENU_GOTOFAV,5,R.string.gotofav).setOnMenuItemClickListener(gotofavItemClickListener);
		}
	};

	// 前往收藏夹
	protected OnMenuItemClickListener gotofavItemClickListener = new OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			Intent intent = new Intent(PostListActivity.this,FavListActivity.class);
			startActivity(intent);
			return false;
		}
	};

	// 发表文章
	protected OnMenuItemClickListener newpostItemClickListener = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem menuitem) {
			Intent intent = new Intent(PostListActivity.this,ReplyActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("board",url);
			intent.putExtras(bundle);
			startActivity(intent);
			//PostListActivity.this.finish();
			return false;
		}
	};

	//回到十大
	protected  OnMenuItemClickListener regotopItemClickListener = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			Intent intent = new Intent(PostListActivity.this,TopTenActivity.class);
			startActivity(intent);
			return false;
		}
	};
	// 切换帐号
	protected OnMenuItemClickListener changeaccountItemClickListener = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem menuitem) {
			//			AdapterView.AdapterContextMenuInfo ifo = (AdapterView.AdapterContextMenuInfo)menuitem.getMenuInfo();
			//			@SuppressWarnings("unchecked")
			//			String Link =String.valueOf(((Map<String, Object>)
			//					postlistview.getItemAtPosition(ifo.position)).get("Link"));
			//			Intent intent = new Intent(PostListActivity.this,ThemeArticleActivity.class);
			//			Bundle bundle = new Bundle();
			//			bundle.putString("url","http://bbs.sjtu.edu.cn"+Link);
			//			intent.putExtras(bundle);
			//			startActivity(intent);
			//			PostListActivity.this.finish();
			Intent intent = new Intent(PostListActivity.this,LoginActivity.class);
			startActivity(intent);
			Net.getInstance().clear();
			//PostListActivity.this.finish();
			return false;
		}
	};
	// 添加收藏夹
	protected OnMenuItemClickListener addfavItemClickListener = new OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			if (Utli.favlist.contains(url)) 
			{
				Toast.makeText(getApplicationContext(),url+"已在收藏夹中",Toast.LENGTH_SHORT).show();
			}
			else if (Utli.favlist.add(url)) 
			{
				FavListEdit favListEdit = new FavListEdit(PostListActivity.this);
				try {
					favListEdit.save();
					Toast.makeText(getApplicationContext(),url+"添加进收藏夹成功",Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
			}
			return false;
		}
	};

	// 重新载入
	protected OnMenuItemClickListener reloadItemClickListener = new OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			init();
			return false;
		}
	};	
	// 获取文章列表 异步类	
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
			if (Utli.topicmode == true) {
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

	// 前往板面按钮的链接
	public OnClickListener gotoboards= new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			url=boards.getText().toString();
			init();
		}
	};
	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences dataSharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
		SharedPreferences.Editor editor = dataSharedPreferences.edit();
		editor.putBoolean("topicmode", Utli.topicmode);
		editor.commit();
	}
	
	
}
