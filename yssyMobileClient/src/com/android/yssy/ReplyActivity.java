package com.bbs.yssy;
/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bbs.util.Net;

public class ReplyActivity extends Activity {

	private EditText replyContentEditText;
	private EditText replyTitleEditText;
	private Button submitButton;
	private Button cancelButton;
	private AlertDialog dialog;

	private String title;
	private String board;
	private String file;
	private String reidstr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reply);

		replyTitleEditText = (EditText)findViewById(R.id.replaytitle);
		replyContentEditText = (EditText)findViewById(R.id.replycontent);
		submitButton = (Button) findViewById(R.id.replysubmit);
		cancelButton = (Button) findViewById(R.id.replycancel);

		Bundle bundle = getIntent().getExtras();
		title = bundle.getString("title");
		board = bundle.getString("board");
		reidstr = bundle.getString("reidstr");
		file = bundle.getString("file");

		replyTitleEditText.setText(title);
		submitButton.setOnClickListener(submitListener);
		cancelButton.setOnClickListener(cancelClickListener);
	}

	private Button.OnClickListener cancelClickListener = new Button.OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			ReplyActivity.this.finish();
		}
	};

	private Button.OnClickListener submitListener = new Button.OnClickListener() {
		public void onClick(View v) {
			View view = LayoutInflater.from(ReplyActivity.this).inflate(R.layout.progress_dialog,null);
			dialog = new AlertDialog.Builder(ReplyActivity.this).setView(view).create();
			dialog.show();
			DoPost doPost = new DoPost();
			doPost.execute();
		}
	};

	private class DoPost extends AsyncTask<Void,Integer,Void>
	{
		@Override
		protected Void doInBackground(Void... arg0) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add( new BasicNameValuePair("title",replyTitleEditText.getText().toString()));
			params.add( new BasicNameValuePair("text",replyContentEditText.getText().toString()));
			params.add( new BasicNameValuePair("board",board));
			params.add( new BasicNameValuePair("file",file));
			params.add( new BasicNameValuePair("reidstr",reidstr));
			params.add( new BasicNameValuePair("signature","1"));
			params.add( new BasicNameValuePair("autocr","1"));
			params.add( new BasicNameValuePair("live","180"));
			params.add( new BasicNameValuePair("level","0"));
			params.add( new BasicNameValuePair("exp",""));
			params.add( new BasicNameValuePair("MAX_FILE_SIZE","1048577"));
			params.add( new BasicNameValuePair("up",""));
			try {
				Net.getInstance().post("http://bbs.sjtu.edu.cn/bbswapsnd", params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			dialog.dismiss();
			Toast.makeText(getApplicationContext(),"发表成功",Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
			ReplyActivity.this.finish();
		}
	}
}
