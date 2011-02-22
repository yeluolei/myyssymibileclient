package com.android.yssy;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.paraser.ReplyParser;
import com.android.utli.Net;

public class ReplyActivity extends Activity {

	EditText replyContentEditText;
	EditText replyTitleEditText;
	Button submitButton;
	Button cancelButton;
	
	String urlSource = "http://bbs.sjtu.edu.cn/bbswappst?board=love&file=M.1297309753.A";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reply);
		
		replyTitleEditText = (EditText)findViewById(R.id.replaytitle);
		replyContentEditText = (EditText)findViewById(R.id.replycontent);
		submitButton = (Button) findViewById(R.id.replysubmit);
		
		submitButton.setOnClickListener(submitListener);
		
		try {
			String source = Net.getInstance().get(urlSource);
			ReplyParser parser = new ReplyParser(source);
			String temp = parser.GetTitle();
			replyTitleEditText.setText(temp);
			replyContentEditText.setText(parser.GetContent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Button.OnClickListener submitListener = new Button.OnClickListener() {
		public void onClick(View v) {

			String board = GetUrlParams("board");
			String file = GetUrlParams("file");
			String reidstr = file.substring(2, file.length()-2);
			
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
//			params.add( new BasicNameValuePair("content",replyContent.getText().toString()));
			//ReplyActivity.this.finish();
		}
	};
	
	private String GetUrlParams(String name){
		int headpos = 0;
		int tailpos = 0;
		String result;
		tailpos = urlSource.indexOf("?",tailpos);
		tailpos = urlSource.indexOf(name + "=",tailpos);
		headpos = tailpos + name.length() + 1;
		if( (tailpos = urlSource.indexOf("&",headpos)) == -1){
			result = urlSource.substring(headpos);
		}
		else
			result = urlSource.substring(headpos,tailpos);
		return result;
	}
}
