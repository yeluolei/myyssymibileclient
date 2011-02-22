package com.android.paraser;

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