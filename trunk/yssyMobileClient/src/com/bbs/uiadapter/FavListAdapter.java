package com.bbs.uiadapter;
/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bbs.yssy.R;
import com.bbs.util.Utli;

public class FavListAdapter extends BaseAdapter{
	private Context context;

	public FavListAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return Utli.favlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return Utli.favlist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (Utli.favlist == null) {
			return null;
		}
		else if (convertView==null) 
		{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.favlistitem,null);
			viewHolder.text = (TextView)convertView.findViewById(R.id.item);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		viewHolder.text.setText(Utli.favlist.get(arg0));
		return convertView;
	}

	public static class ViewHolder
	{
		TextView text;
	}

}
