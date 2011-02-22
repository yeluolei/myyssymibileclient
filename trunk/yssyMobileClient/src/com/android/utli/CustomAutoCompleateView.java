package com.android.utli;

import com.android.yssy.PostListActivity;
import com.android.yssy.R;
import com.android.yssy.TopicPostListActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

public class CustomAutoCompleateView extends LinearLayout{
	private final Button button;
	private final AutoCompleteTextView autoCompleteTextView;
	private final Context context;
	public CustomAutoCompleateView(Context c) {
		super(c);
		this.context = c;
		View view = View.inflate(context, R.layout.autocompleate, null);
		addView(view);
		autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.boads);
		button = (Button)findViewById(R.id.gotoboard); 
		String[] boardnameStrings = getResources().getStringArray(R.array.boards);
		ArrayAdapter<String> boadersadapter = new ArrayAdapter<String>(c,
				android.R.layout.simple_dropdown_item_1line,boardnameStrings);
		autoCompleteTextView.setAdapter(boadersadapter);
		button.setOnClickListener(gotoboards);
	}
	
	public OnClickListener gotoboards = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			Intent intent= new Intent();
			Bundle bundle= new Bundle();
			bundle.putString("url", autoCompleteTextView.getText().toString());
			if (utli.topicmode == true)
			{
				intent.setClass(context, TopicPostListActivity.class);
			}
			else {
				intent.setClass(context,PostListActivity.class);
			}
			intent.putExtras(bundle);
			context.startActivity(intent);
			((Activity)context).finish();
		}
	};
	

}
