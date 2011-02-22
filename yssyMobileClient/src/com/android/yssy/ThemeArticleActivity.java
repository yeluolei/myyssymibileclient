package com.android.yssy;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.paraser.ThemeArticleParser;
import com.android.utli.Net;

public class ThemeArticleActivity extends Activity {

	
	private ListView contentListView;
	
	private final int MENU_REPLY = 10;
	private final int MENU_VIEW = 11;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.themearticle);
		
		Bundle bundle = this.getIntent().getExtras();
		String temp;
		try {
			temp = Net.getInstance().get(bundle.getString("url"));
			//text = (TextView)findViewById(R.id.TextView01);
			ThemeArticleParser parser = new ThemeArticleParser(temp);
			contentListView = (ListView) findViewById(R.id.articlelist);
			SimpleAdapter adapter = new SimpleAdapter(ThemeArticleActivity.this,parser.Parse(), 
					R.layout.themearticlelistitem, 
					new String[]{"author","time","content"},
					new int[]{R.id.author, R.id.time, R.id.Content});
			contentListView.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//text.setText("test");
	}
	
	
	// 为List添加弹出菜单
	public OnCreateContextMenuListener listmenu = new OnCreateContextMenuListener() {
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuinfo) {
			
			menu.add(Menu.NONE,MENU_REPLY,0,R.string.reply).setOnMenuItemClickListener(replyItemClickListener);
			menu.add(Menu.NONE,MENU_VIEW,1,R.string.view).setOnMenuItemClickListener(viewItemClickListener);
		}
	};
	
	protected OnMenuItemClickListener replyItemClickListener= new OnMenuItemClickListener() {
		
		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			// TODO 回复参数
			return false;
		}
	};
	
	protected OnMenuItemClickListener viewItemClickListener = new OnMenuItemClickListener() {
		
		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			// TODO 查看参数
			return false;
		}
	};
	
}
