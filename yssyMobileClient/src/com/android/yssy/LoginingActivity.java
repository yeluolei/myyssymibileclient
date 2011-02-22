package com.android.yssy;

import com.android.utli.Login;
import com.android.utli.utli;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class LoginingActivity extends Activity {
	private final int LOGIN_SUCESS = 0x001;
	private final int LOGIN_FAILED = 0x002;
	private TextView activityTitleTextView;
	private TextView progressMessageTextView;
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logning);

		activityTitleTextView = (TextView)findViewById(R.id.activitytitle);
		progressMessageTextView = (TextView)findViewById(R.id.progresstext);

		activityTitleTextView.setText("正在登入...");
		progressMessageTextView.setText("正在登入...");


		new Thread(loginRunnable).start();
	}

	private Runnable loginRunnable = new Runnable() {

		@Override
		public void run() {
			try{
				Login login = new Login();
				boolean sucess = login.login(utli.userName,
						utli.password);
				if (sucess == true)
				{
					Message message  = new Message();
					message.what = LOGIN_SUCESS;
					LoginingActivity.this.loginHandler.sendMessage(message);
				}
				else 
				{
					throw new Exception("用户名或密码错误！");
				}
			}catch (Exception e) {
				Bundle bundle = new Bundle();
				bundle.putString("exception", e.getMessage());
				Message m = new Message();
				m.setData(bundle);
				m.what = LOGIN_FAILED;
				LoginingActivity.this.loginHandler.sendMessage(m);
			}

		}
	};
	private Handler loginHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{
			switch (msg.what) 
			{
			case LOGIN_SUCESS:
			{
				SharedPreferences dataSharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
				SharedPreferences.Editor editor = dataSharedPreferences.edit();
				editor.putString("username", utli.userName);
				if (utli.remember) {
					editor.putString("password", utli.password);
				}
				else {
					editor.putString("password", null);
				}
				editor.putBoolean("remember", utli.remember);
				editor.putBoolean("auto", utli.auto);
				editor.commit();
				Intent intent = new Intent(LoginingActivity.this,TopTenActivity.class);
				startActivity(intent);
				LoginingActivity.this.finish();
				break;
			}
			case LOGIN_FAILED:
			{
				Toast.makeText(getApplicationContext(),msg.getData().getShort("exception"), Toast.LENGTH_SHORT).show();
				Intent intent= new Intent(LoginingActivity.this,LoginActivity.class);
				startActivity(intent);
				LoginingActivity.this.finish();
				break;
			}
			}
		}
	};
}
