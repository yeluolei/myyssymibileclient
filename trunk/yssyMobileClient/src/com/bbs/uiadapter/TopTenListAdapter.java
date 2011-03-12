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
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TopTenListAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, String>>datas;

	public TopTenListAdapter(Context context , List<Map<String, String>> dataList) {
		this.context = context;
		this.datas = dataList;
	}

	public void clear() 
	{
		datas.clear();
	}
	
	@Override
	public int getCount() {
		if (datas == null) 
		{
			return 0;
		}
		return datas.size();
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		if(datas == null) 
		{
			return null;
		}
		else if (convertView == null) {
			TopTenListItem topTenListItem = new TopTenListItem(context);
			topTenListItem.updateview(datas.get(position));
			convertView = topTenListItem;
		}else {
			((TopTenListItem)convertView).updateview(datas.get(position));
		}
		return convertView;
	}
	//		ViewHolder viewHolder;
	//		if (datas == null) {
	//			return null;
	//		}else if (convertView == null) {
	//			viewHolder = new ViewHolder();
	//			convertView = LayoutInflater.from(context).inflate(R.layout.toptenlistitem, null);
	//			viewHolder.titleTextView = (TextView)convertView.findViewById(R.id.title);
	//			viewHolder.boardTextView = (TextView)convertView.findViewById(R.id.board);
	//			viewHolder.authoridTextView = (TextView)convertView.findViewById(R.id.authorid);
	//			convertView.setTag(viewHolder);
	//		}else {
	//			viewHolder = (ViewHolder)convertView.getTag();
	//		}
	//		viewHolder.authoridTextView.setText(datas.get(position).get("AuthorID"));
	//		viewHolder.boardTextView.setText(datas.get(position).get("Board"));
	//		viewHolder.titleTextView.setText(datas.get(position).get("Title"));
	//		
	//		return convertView;
	//	}
	public static class ViewHolder
	{
		public TextView titleTextView;
		public TextView boardTextView;
		public TextView authoridTextView;
	}
}
