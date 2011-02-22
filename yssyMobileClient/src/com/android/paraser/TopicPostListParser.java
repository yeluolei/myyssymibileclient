package com.android.paraser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.android.utli.Net;
import com.android.yssy.R;

public class TopicPostListParser implements PostParser {
	private List<Map<String, Object>> postItems;
	private List<String>BoardMasters;
	private String prelink;
	private String nextlink;

	public TopicPostListParser() {
		postItems = new ArrayList<Map<String,Object>>();
		BoardMasters = new ArrayList<String>();
		prelink = null;
		nextlink = null;
	}

	// PostIndex , authorID , postTime ,Link ,type ,title
	/* (non-Javadoc)
	 * @see com.android.parase.PostParser#parser(java.lang.String)
	 */
	@Override
	public PostParser parser(String URL) throws Exception 
	{
		try {
			String sourceString = Net.getInstance().get(URL);
			int pastpos1,pastpos2;
			int prepos = 0;

			pastpos1 = sourceString.indexOf("</br>",prepos);
			pastpos2 = sourceString.indexOf("</br>",pastpos1+5);
			while((pastpos1 = sourceString.indexOf("<a",pastpos1))<pastpos2) 
			{
				prepos = sourceString.indexOf(">",pastpos1)+1;
				pastpos1 = sourceString.indexOf("</a>",pastpos1);
				BoardMasters.add(sourceString.substring(prepos,pastpos1));
			}

			// ��ȡ��һҳ����һҳ������
			pastpos1 = pastpos2;
			pastpos2 = sourceString.indexOf("<br>",pastpos2);
			while((pastpos1 = sourceString.indexOf("<a",pastpos1))<pastpos2) 
			{
				prepos = sourceString.indexOf("href=",pastpos1)+5;
				pastpos1 = sourceString.indexOf(">",prepos);
				if ( sourceString.substring(pastpos1+1, sourceString.indexOf("</",pastpos1)).equals("��һҳ"))
				{
					prelink = sourceString.substring(prepos,pastpos1);
				}
				else 
				{
					nextlink = sourceString.substring(prepos,pastpos1);
				}
			}

			prepos = sourceString.indexOf("<hr>",pastpos2)+4;
			pastpos2 = sourceString.indexOf("</table>",prepos);

			while(prepos < pastpos2) 
			{
				Map<String, Object> map = new HashMap<String, Object>();
				pastpos1 = sourceString.indexOf("<a",prepos);
				String PostIndex =sourceString.substring(prepos,pastpos1);
				if (PostIndex.startsWith("<font")) 
				{
					int p = PostIndex.indexOf(">",0)+1;
					int q = PostIndex.indexOf("</",p);
					PostIndex = PostIndex.substring(p+1,q-1);
				}
				else {
					PostIndex = PostIndex.replace("&nbsp;", " ").trim();
				}
				map.put("PostIndex", PostIndex);

				prepos = sourceString.indexOf(">",pastpos1)+1;
				pastpos1 = sourceString.indexOf("</",prepos);
				map.put("authorID",sourceString.substring(prepos,pastpos1));

				prepos = pastpos1 + 4;
				pastpos1 = sourceString.indexOf("</br>",prepos);
				String postTime = sourceString.substring(prepos,pastpos1);
				postTime = postTime.replaceAll("&nbsp;", " ").trim();
				map.put("postTime", postTime);

				prepos = sourceString.indexOf("href=",pastpos1)+5;
				pastpos1 = sourceString.indexOf(">",prepos);
				map.put("Link",sourceString.substring(prepos,pastpos1));

				prepos = pastpos1+1;
				pastpos1 = sourceString.indexOf("<",prepos);
				String title = sourceString.substring(prepos,pastpos1);
				int type;
				if (title.startsWith("��")) 
				{
					title = title.substring(1).trim();
					type = R.drawable.type1;
				}
				else 
				{
					type = R.drawable.type2;
				}

				map.put("type", type);
				map.put("title", title);
				postItems.add(map);

				prepos = sourceString.indexOf("<p>",pastpos1)+3;
			}
		}catch(Exception e) 
		{
			Log.v("postlistview", e.getMessage());
			throw e;
		}
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.android.parase.PostParser#getPostItems()
	 */
	@Override
	public List<Map<String, Object>> getPostItems() {
		return postItems;
	}

	/* (non-Javadoc)
	 * @see com.android.parase.PostParser#getBoardMasters()
	 */
	@Override
	public List<String> getBoardMasters() {
		return BoardMasters;
	}

	/* (non-Javadoc)
	 * @see com.android.parase.PostParser#getPrelink()
	 */
	@Override
	public String getPrelink() {
		return prelink;
	}

	/* (non-Javadoc)
	 * @see com.android.parase.PostParser#getNextlink()
	 */
	@Override
	public String getNextlink() {
		return nextlink;
	}
}
