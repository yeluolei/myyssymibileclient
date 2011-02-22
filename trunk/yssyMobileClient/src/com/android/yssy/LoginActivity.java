package com.android.yssy;
import com.android.utli.utli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
/**
 * @author Csai
 *
 */
public class LoginActivity extends Activity {
	private TextView activitynameTextView;
	private EditText usernameEditText;
	private EditText passwordEditText;
	private CheckBox rememberpasswordCheckBox;
	private CheckBox automaticloginCheckBox;
	private Button   loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		activitynameTextView = (TextView) findViewById(R.id.activitytitle);
		usernameEditText = (EditText) findViewById(R.id.UserNameEditText);
		passwordEditText = (EditText)findViewById(R.id.PasswordEditText);
		rememberpasswordCheckBox = (CheckBox)findViewById(R.id.rememberpassword);
		automaticloginCheckBox = (CheckBox)findViewById(R.id.automaticlogin);
		loginButton = (Button)findViewById(R.id.LoginButton);


		rememberpasswordCheckBox.setChecked(utli.remember);
		automaticloginCheckBox.setChecked(utli.auto);
		
		activitynameTextView.setText("登入");
		usernameEditText.setText(utli.userName);
		passwordEditText.setText(utli.password);
		rememberpasswordCheckBox.setOnCheckedChangeListener(rememberListener);
		automaticloginCheckBox.setOnCheckedChangeListener(automaticClickListener);
		loginButton.setOnClickListener(loginListener);
	}

	public OnClickListener loginListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			utli.userName = usernameEditText.getText().toString();
			utli.password = passwordEditText.getText().toString();
			Intent intent = new Intent(LoginActivity.this,LoginingActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
		}
	};

	public OnCheckedChangeListener rememberListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			if (rememberpasswordCheckBox.isChecked()) 
			{
				utli.remember = true;
			}
			else {
				utli.remember= false;
			}
		}
	};

	public OnCheckedChangeListener automaticClickListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
			if (automaticloginCheckBox.isChecked()) 
			{
				utli.auto = true;
				rememberpasswordCheckBox.setChecked(true);
			}
			else {
				utli.auto = false;
			}
		}
	};



	//	private Button connectButton;
	//	private Button loginbutton;
	//	private EditText username;
	//	private EditText passwd;
	//	private Button popmusicButton;
	//	private Button topicmodeButton;
	//	private Button thememode;
	//	private Button replyButton;
	//	private TextView tView ;
	//	/** Called when the activity is first created. */
	//	@Override
	//	public void onCreate(Bundle savedInstanceState) {
	//		super.onCreate(savedInstanceState);
	//		setContentView(R.layout.main);
	//
	//		tView = (TextView)findViewById(R.id.TextView);
	//		connectButton = (Button)findViewById(R.id.connect);
	//		loginbutton = (Button)findViewById(R.id.Button01);
	//		username = (EditText)findViewById(R.id.EditText01);
	//		passwd = (EditText)findViewById(R.id.EditText02);
	//		popmusicButton = (Button)findViewById(R.id.popmusic);
	//		topicmodeButton = (Button)findViewById(R.id.topicmode);
	//		thememode = (Button)findViewById(R.id.theme);
	//		replyButton = (Button)findViewById(R.id.btreply);
	//		
	//		//控件的控制逻辑在这里定义
	//		//把逻辑new在外面，方便阅读
	//		connectButton.setOnClickListener(connect);
	//		loginbutton.setOnClickListener(login);
	//		thememode.setOnClickListener(themelisten);
	//		replyButton.setOnClickListener(replylisten);
	//		
	//		popmusicButton.setOnClickListener(popmusic);
	//		topicmodeButton.setOnClickListener(topic);
	//	}
	//	
	//	private Button.OnClickListener topic = new Button.OnClickListener() {
	//		public void onClick(View v) {
	//			Intent intent = new Intent();
	//			intent.setClass(LoginActivity.this,TopicPostListActivity.class);
	//			Bundle bundle = new Bundle();
	//			bundle.putString("url","http://bbs.sjtu.edu.cn/bbswaptdoc,board,PopMusic.html");
	//			intent.putExtras(bundle);
	//			startActivity(intent);
	//			LoginActivity.this.finish();
	//		}
	//	};
	//	
	//	private Button.OnClickListener popmusic = new Button.OnClickListener() {
	//		public void onClick(View v) {
	//			Intent intent = new Intent();
	//			intent.setClass(LoginActivity.this, postListActivity.class);
	//			Bundle bundle = new Bundle();
	//			bundle.putString("url","http://bbs.sjtu.edu.cn/bbswapdoc?board=PopMusic");
	//			intent.putExtras(bundle);
	//			startActivity(intent);
	//			LoginActivity.this.finish();
	//		}
	//	};
	//	
	//	//这里加点注释吧
	//	//connect按钮的控制逻辑
	//	private Button.OnClickListener connect = new Button.OnClickListener() {
	//		public void onClick(View v) {
	//			Intent intent = new Intent();
	//			intent.setClass(LoginActivity.this, TopTenActivity.class);
	//			startActivity(intent);
	//			LoginActivity.this.finish();
	//		}
	//	};
	//	
	//	private Button.OnClickListener themelisten = new Button.OnClickListener(){
	//		public void onClick(View v){
	//			Intent intent = new Intent();
	//			intent.setClass(LoginActivity.this, ThemeArticleActivity.class);
	//			startActivity(intent);
	//			LoginActivity.this.finish();
	//		}
	//	};
	//	
	//	private Button.OnClickListener replylisten = new Button.OnClickListener(){
	//		public void onClick(View v){
	//			Intent intent = new Intent();
	//			intent.setClass(LoginActivity.this, ReplyActivity.class);
	//			startActivity(intent);
	//			LoginActivity.this.finish();
	//		}
	//	};
	//	
	//	private Button.OnClickListener login = new Button.OnClickListener(){
	//		public void onClick(View v){
	//			try{  
	//				List <NameValuePair> params = new ArrayList <NameValuePair>();   
	//				params.add(new BasicNameValuePair("id", username.getText().toString()));   
	//				params.add(new BasicNameValuePair("pw", passwd.getText().toString())); 
	//				String sourceString = Net.getInstance().post("http://bbs.sjtu.edu.cn/bbswaplogin", params);
	//
	//				tView.setText(sourceString);
	//			}   
	//			catch (Exception e) {
	//				tView.setText(e.getMessage().toString());   
	//				e.printStackTrace();    
	//			}   
	//		}
	//	};

}