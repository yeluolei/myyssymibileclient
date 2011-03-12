package com.bbs.util;

/**
 * 
 * @author SJTU SE Ye Rurui ; Zhu Xinyu ; Peng Jianxiang
 * email:yeluolei@gmail.com zxykobezxy@gmail.com
 * No Business Use is Allowed
 * 2011-2-14
 */

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

public class FavListEdit {
	private Context context;
	private DocumentBuilderFactory docBuilderFactory = null;
	private DocumentBuilder docBuilder = null;
	private Document doc = null;

	public FavListEdit (Context context)
	{
		this.context = context;
	}

	public void save() throws Exception
	{
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter stringWriter=new StringWriter();
		try {
			serializer.setOutput(stringWriter);
			serializer.startDocument("UTF-8",true);
			serializer.startTag(null,"Lists");
			for (int i = 0 ; i < Utli.favlist.size() ; i++) 
			{
				serializer.startTag(null,"Item");
				serializer.text(Utli.favlist.get(i));
				serializer.endTag(null,"Item");
			}
			serializer.endTag(null,"Lists");
			serializer.endDocument();


			OutputStream os = context.openFileOutput("favlist.xml",Context.MODE_PRIVATE);
			OutputStreamWriter osw=new OutputStreamWriter(os);
			osw.write(stringWriter.toString());
			osw.close();
			os.close();
		} catch (Exception e) {
			throw e;
		}

	}

	public void read() throws Exception 
	{
		docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docBuilderFactory.newDocumentBuilder();
		InputStream in = context.openFileInput("favlist.xml");
		try {
			doc = docBuilder.parse(in);
			Element root = doc.getDocumentElement();
			NodeList items = root.getElementsByTagName("Item");
			for (int i = 0 ; i < items.getLength();i++) 
			{
				Node node = items.item(i);
				Utli.favlist.add(node.getFirstChild().getNodeValue());
			}
		}catch (FileNotFoundException e) {
			Log.e("Favlist","文件没有找到");
			
		}
		catch (Exception e) {
			throw e;
		}
		in.close();
	}
}
