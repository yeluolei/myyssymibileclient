package com.bbs.yssy;
/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.bbs.util.Login;
import com.bbs.util.Net;
import com.bbs.util.Utli;

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
				if (!Net.getInstance().checknetwork(getApplicationContext())) 
				{
					throw new Exception("登入失败,请检查网络连接");
				}
				else {
					Login login = new Login();
					boolean sucess = login.login(Utli.userName,
							Utli.password);
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
				editor.putString("username", Utli.userName);
				if (Utli.remember) {
					editor.putString("password", Utli.password);
				}
				else {
					editor.putString("password", null);
				}
				editor.putBoolean("remember", Utli.remember);
				editor.putBoolean("auto", Utli.auto);
				editor.commit();
				Intent intent = new Intent(LoginingActivity.this,TopTenActivity.class);
				startActivity(intent);
				LoginingActivity.this.finish();
				break;
			}
			case LOGIN_FAILED:
			{
				Toast.makeText(getApplicationContext(),msg.getData().getString("exception"), Toast.LENGTH_SHORT).show();
				Intent intent= new Intent(LoginingActivity.this,LoginActivity.class);
				startActivity(intent);
				LoginingActivity.this.finish();
				break;
			}
			}
		}
	};
}
