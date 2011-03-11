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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.uiadapter.FavListAdapter;
import com.bbs.util.FavListEdit;
import com.bbs.util.Utli;

public class FavListActivity extends Activity{
	private TextView activityTitleTextView;
	private ListView favlistView;
	private FavListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favlist);
		activityTitleTextView = (TextView)findViewById(R.id.activitytitle);
		favlistView = (ListView)findViewById((R.id.favlist));
		
		activityTitleTextView.setText("收藏夹");
		
		adapter = new FavListAdapter(FavListActivity.this);
		favlistView.setAdapter(adapter);
		
		favlistView.setOnItemClickListener(gotoboard);
		favlistView.setOnItemLongClickListener(deleteboard);
	}
	
	protected OnItemClickListener gotoboard = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(FavListActivity.this,PostListActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("url",Utli.favlist.get(arg2));
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
	
	protected OnItemLongClickListener deleteboard = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			final int id = arg2;
			new AlertDialog.Builder(FavListActivity.this)
			.setTitle("删除")
			.setMessage("确认将"+Utli.favlist.get(arg2)+"从收藏中删除?")
			.setPositiveButton("确认",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
						Utli.favlist.remove(id);
						FavListEdit favListEdit = new FavListEdit(getApplicationContext());
						try {
							favListEdit.save();
							adapter.notifyDataSetChanged();
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
						}
				}
			})
			.setNegativeButton("取消",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				}
			}).create().show();
			return false;
		}
	};
}
