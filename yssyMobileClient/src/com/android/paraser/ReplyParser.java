package com.android.paraser;

public class ReplyParser {
	String content;
	
	public ReplyParser(String content) {		
		this.content = content;
	}
	
	private String GetInputContent(String name){
		int headpos = 0;
		int tailpos = 0;
		int cnt = 0;
		while( (tailpos = content.indexOf("<input",tailpos)) != -1){
			++cnt;
			tailpos = content.indexOf("name",tailpos);
			tailpos = content.indexOf("=",tailpos);
			headpos = tailpos;
			tailpos = content.indexOf(" ",tailpos+2);
			String temp = content.substring(headpos + 2, tailpos - 1);
			if(cnt == 4){
				tailpos = content.indexOf("value",tailpos);
				tailpos = content.indexOf("=",tailpos);
				headpos = tailpos;
				tailpos = content.indexOf("'",tailpos + 2);
				return content.substring(headpos + 2, tailpos);
			}
			else
				continue;
		}
		return "-1";
	}
	private String GetTextareaContent(){
		int headpos = 0;
		int tailpos = 0;
		tailpos = content.indexOf("<textarea", tailpos);
		tailpos = content.indexOf(">", tailpos);
		headpos = tailpos + 1;
		tailpos = content.indexOf("</textarea>",headpos);
		return content.substring(headpos, tailpos);
	}
	public String GetTitle(){
		return GetInputContent("title");
	}
	
	public String GetContent(){
		return GetTextareaContent();
	}
	
}
