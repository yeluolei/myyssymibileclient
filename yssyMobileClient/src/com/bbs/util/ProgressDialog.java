package com.bbs.util;
/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */
import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

import com.bbs.yssy.R;

public class ProgressDialog extends AlertDialog {
	private TextView mesgTextView;
	
	public ProgressDialog(Context context) {
		super(context);
		setContentView(R.layout.progress_dialog);
		mesgTextView = (TextView)findViewById(R.id.processText);
	}
	
	@Override
	public void setMessage(CharSequence message) 
	{
		mesgTextView.setText(message);
	}
	
	class Builder extends AlertDialog.Builder
	{
		ProgressDialog progressDialog;
		public Builder(Context arg0) {
			super(arg0);
			progressDialog = new ProgressDialog(arg0);
		}
		
		@Override
		public Builder setMessage(CharSequence message) 
		{
			progressDialog.setMessage(message);
			return this;
		}
		
		@Override
		public AlertDialog create() 
		{
			return progressDialog;
		}
	}
}
