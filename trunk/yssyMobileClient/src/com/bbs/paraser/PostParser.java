package com.bbs.paraser;
/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */

import java.util.List;
import java.util.Map;

public interface PostParser {

	// PostIndex , authorID , postTime ,Link ,type ,title
	public abstract PostParser parser(String URL) throws Exception;

	public abstract List<Map<String, Object>> getPostItems();

	public abstract List<String> getBoardMasters();

	public abstract String getPrelink();

	public abstract String getNextlink();

}