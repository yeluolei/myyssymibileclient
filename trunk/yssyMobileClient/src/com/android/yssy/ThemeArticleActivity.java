package com.bbs.yssy;
/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.paraser.ThemeArticleParser;
import com.bbs.uiadapter.ThemeArticleAdapter;
import com.bbs.util.NumberPicker;

public class ThemeArticleActivity extends Activity {
	private ListView contentListView;
	private TextView activitytitle;
	private RelativeLayout prepage;
	private RelativeLayout nextpage;
	private RelativeLayout pages;
	private RelativeLayout processLayout;
	private TextView pagenumbers;
	private int currentpage;
	private int allpage;
	private ThemeArticleParser parser;
	private ThemeArticleAdapter adapter;
	private String articletitle;
	private String currenturl;
	private NumberPicker picker;

	private final int MENU_FREASH = 0;
	private final int MENU_FAVLIST = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.themearticle);

		contentListView = (ListView) findViewById(R.id.articlelist);
		activitytitle = (TextView)findViewById(R.id.activitytitle);
		prepage = (RelativeLayout)findViewById(R.id.prepage);
		nextpage = (RelativeLayout)findViewById(R.id.nextpage);
		pages = (RelativeLayout)findViewById(R.id.pages);
		pagenumbers = (TextView)findViewById(R.id.pagenumber);
		processLayout = (RelativeLayout)findViewById(R.id.progresslayout);


		currentpage = 1;
		Bundle bundle = this.getIntent().getExtras();
		articletitle = bundle.getString("title");
		activitytitle.setText(articletitle);
		currenturl = bundle.getString("url");

		Articleload articleload = new Articleload();
		articleload.execute(currenturl);
		prepage.setOnClickListener(prepageClickListener);
		nextpage.setOnClickListener(nextpageClickListener);
		pages.setOnClickListener(pagescClickListener);
		contentListView.setOnItemClickListener(contentClickListener);
	}

	protected OnClickListener prepageClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (currentpage==1) 
			{
				Toast.makeText(getApplicationContext(),"当前已经到达首页",Toast.LENGTH_SHORT).show();
			}
			else 
			{
				currentpage-=1;
				Articleload articleload = new Articleload();
				articleload.execute("http://bbs.sjtu.edu.cn/"+parser.getPages().get(currentpage-1));
			}
		}
	};

	protected OnClickListener nextpageClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (currentpage== allpage) 
			{
				Toast.makeText(getApplicationContext(),"当前已经到达末页",Toast.LENGTH_SHORT).show();
			}
			else {
				currentpage += 1;
				Articleload articleload = new Articleload();
				articleload.execute("http://bbs.sjtu.edu.cn/"+parser.getPages().get(currentpage-1));
			}

		}
	};

	protected OnClickListener pagescClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.numpicdialog,null);
			picker = (NumberPicker)view.findViewById(R.id.picker);
			picker.setRange(1,allpage);
			picker.setCurrent(currentpage);

			AlertDialog dialog = new AlertDialog.Builder(ThemeArticleActivity.this)
			.setTitle("请选择页面")
			.setView(view)
			.setPositiveButton("确认",dialogClickListener)
			.setNegativeButton("取消",dialogClickListener)
			.create();
			dialog.show();
		}
	};
	
	protected DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int which) {
			switch (which) {
			case Dialog.BUTTON_POSITIVE:
				int tempcurrentpage = picker.getCurrent();
				if (tempcurrentpage == currentpage) 
				{
					Toast.makeText(getApplicationContext(),"当前正在此页",Toast.LENGTH_SHORT).show();
				}
				else {
					currentpage = tempcurrentpage;
					Articleload articleload = new Articleload();
					articleload.execute("http://bbs.sjtu.edu.cn/"+parser.getPages().get(currentpage-1));
				}
				break;
			case Dialog.BUTTON_NEGATIVE:
				break;
			default:
				Toast.makeText(getApplicationContext(),"无此选项",Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	// 跳转到回复
	protected OnItemClickListener contentClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			    String URL = String.valueOf(parser.getArticles().get(position).get("link"));
				String board,file;
				int prepos=0;
				int pastpos=0;
				prepos = URL.indexOf("board=")+6;
				pastpos = URL.indexOf("&",prepos);
				board = URL.substring(prepos,pastpos);
				prepos = URL.indexOf("file=",pastpos)+5;
				file = URL.substring(prepos);
				
				Intent intent = new Intent(ThemeArticleActivity.this,ReplyActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("board",board);
				bundle.putString("file",file);
				bundle.putString("title","Re: "+ articletitle);
				bundle.putString("reidstr",file.substring(2,file.length()-2));
				intent.putExtras(bundle);
				startActivity(intent);
				//ThemeArticleActivity.this.finish();
		}
	};
	
	
	// 获取文章列表 异步执行类
	class Articleload extends AsyncTask<String,Integer,Void>
	{
		@Override
		protected void onPreExecute() {
			processLayout.setVisibility(View.VISIBLE);
			if (adapter!=null) {
				adapter.clear();
				adapter.notifyDataSetChanged();
			}
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... URL) {
			currenturl = URL[0];
			parser = new ThemeArticleParser();
			parser.parser(currenturl);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			processLayout.setVisibility(View.GONE);
			adapter = new ThemeArticleAdapter(ThemeArticleActivity.this,parser.getArticles());
			contentListView.setAdapter(adapter);
			allpage = parser.getPages().size();
			if (allpage == 0) 
			{
				allpage = 1;
			}
			pagenumbers.setText(currentpage+"/"+allpage);
			super.onPostExecute(result);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE,MENU_FREASH,0,"刷新").setIcon(R.drawable.menu_refresh);
		menu.add(Menu.NONE,MENU_FAVLIST,1,"收藏夹").setIcon(R.drawable.menu_favlist);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case MENU_FREASH:
			Articleload listLoad = new Articleload();
			listLoad.execute(currenturl);
			break;
		case MENU_FAVLIST:
			Intent intent = new Intent(ThemeArticleActivity.this,FavListActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
