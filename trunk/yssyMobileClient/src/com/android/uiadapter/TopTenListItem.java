package com.bbs.uiadapter;
/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bbs.yssy.R;

public class TopTenListItem extends LinearLayout{
	Context context ;
	private TextView titleTextView;
	private TextView boardTextView;
	private TextView authoridTextView;
	
	public TopTenListItem(Context context) {
		super(context);
		this.context = context;
		View view = LayoutInflater.from(this.context).inflate(R.layout.toptenlistitem,null);  
	    titleTextView = (TextView) view.findViewById(R.id.title);  
	    boardTextView = (TextView) view.findViewById(R.id.board);
	    authoridTextView = (TextView) view.findViewById(R.id.authorid); 
	    addView(view);
		
	}
	
	public void updateview (Map<String, String>data) 
	{
		authoridTextView.setText(data.get("AuthorID"));
		boardTextView.setText(data.get("Board"));
		titleTextView.setText(data.get("Title"));
	}
	
}
