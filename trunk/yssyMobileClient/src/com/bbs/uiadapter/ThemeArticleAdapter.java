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

import com.bbs.yssy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ThemeArticleAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>datas;
	public ThemeArticleAdapter(Context context) 
	{
		this.context = context;
	}
	public ThemeArticleAdapter(Context context,List<Map<String, Object>>datas)
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
		ViewHolder viewHolder = new ViewHolder();
		if (datas == null) {
			return null;
		}else {
			convertView = LayoutInflater.from(this.context).inflate(R.layout.themearticlelistitem, null);
			viewHolder.author = (TextView)convertView.findViewById(R.id.author);
			viewHolder.time = (TextView)convertView.findViewById(R.id.time);
			viewHolder.content = (TextView)convertView.findViewById(R.id.content);
		}
		viewHolder.author.setText(String.valueOf(datas.get(location).get("author")));
		viewHolder.time.setText(String.valueOf(datas.get(location).get("time")));
		viewHolder.content.setText(String.valueOf(datas.get(location).get("content")));
		return convertView;
	}
	
	public static class ViewHolder
	{
		TextView author;
		TextView time;
		TextView content;
	}
}
