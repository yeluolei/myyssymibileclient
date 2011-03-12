package com.bbs.uiadapter;

/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbs.yssy.R;

public class PostListAdapter  extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>datas;

	public PostListAdapter(Context context) {
		this.context = context;
	}

	public PostListAdapter(Context context,List<Map<String, Object>>datas)
	{
		this.context = context;
		this.datas = datas;
	}

	public void clear() 
	{
		this.datas.clear();
	}
	@Override
	public int getCount() { 
		if (datas == null) {
			return 0;
		}
		else {
			return datas.size();
		}

	}  

	@Override
	public Object getItem(int location) {  
		return datas.get(location); 
	}  

	@Override
	public long getItemId(int location) {  
		return location;  
	}

	@Override
	public View getView(int location, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(datas == null) {  
			return null;  
		} else if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(this.context).inflate(R.layout.postlistitem, null);
			viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.posttitle);
			viewHolder.typeImageView = (ImageView)convertView.findViewById(R.id.posttype);
			viewHolder.postTimeTextView = (TextView)convertView.findViewById(R.id.posttime);
			viewHolder.postAuthorTextView = (TextView)convertView.findViewById(R.id.postauthorid);
			viewHolder.postIndexTextView=(TextView)convertView.findViewById(R.id.postindex);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.titleTextView.setText((CharSequence)datas.get(location).get("title"));
		viewHolder.typeImageView.setImageResource(Integer.parseInt(String.valueOf(datas.get(location).get("type"))));
		viewHolder.postTimeTextView.setText((CharSequence) datas.get(location).get("postTime"));
		viewHolder.postAuthorTextView.setText((CharSequence) datas.get(location).get("authorID"));
		viewHolder.postIndexTextView.setText((CharSequence) datas.get(location).get("PostIndex"));
		return convertView;
	}

	public static class ViewHolder
	{
		public TextView titleTextView;
		public ImageView typeImageView;
		public TextView postTimeTextView;
		public TextView postAuthorTextView;
		public TextView postIndexTextView;
	}
}
