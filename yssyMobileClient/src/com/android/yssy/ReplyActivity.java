package com.android.yssy;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReplyActivity extends Activity {

	EditText replyContent;
	EditText replyTitle;
	Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reply);
		
		replyTitle = (EditText)findViewById(R.id.replaytitle);
		replyContent = (EditText)findViewById(R.id.replycontent);
		submit = (Button) findViewById(R.id.replysubmit);
		
		submit.setOnClickListener(submitListener);
		
		try {
			String source = Net.getInstance().get("http://bbs.sjtu.edu.cn/bbswappst?board=SJTUNews&file=M.1297238966.A");
			ReplyParser parser = new ReplyParser(source);
			String temp = parser.GetTitle();
			replyTitle.setText(temp);
			replyContent.setText(parser.GetContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Button.OnClickListener submitListener = new Button.OnClickListener() {
		public void onClick(View v) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add( new BasicNameValuePair("title",replyTitle.getText().toString()));
			params.add( new BasicNameValuePair("text",replyContent.getText().toString()));
			params.add( new BasicNameValuePair("board","SJTUNews"));
			params.add( new BasicNameValuePair("file","M.1297238966.A"));
			params.add( new BasicNameValuePair("reidstr","1297238966"));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			params.add( new BasicNameValuePair("content",replyContent.getText().toString()));
			//ReplyActivity.this.finish();
		}
	};
	
}
