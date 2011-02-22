package com.android.utli;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yssy.R;

public class CustomerToast {
	private Toast toast;
	private TextView mesg;
	public CustomerToast(Activity a) {
		LayoutInflater inflater = a.getLayoutInflater();
		View layout = inflater.inflate(R.layout.process_dialog,
				(ViewGroup) a.findViewById(R.id.processdialog));
		mesg = (TextView) layout.findViewById(R.id.processText);
		Toast toast = new Toast(a.getApplicationContext());
		toast.setView(layout);
	}
	
	
	public CustomerToast setDuration(int d) {
		toast.setDuration(d);
		return this;
	}
	public CustomerToast setText(String message)
	{
		mesg.setText(message);
		return this;
	}
	public void show() 
	{
		toast.show();
	}
}
