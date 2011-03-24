package com.sjtu.is.mobili.video;

import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;


public class VideoParser {
	private String vid;
	private InputStream v_content;
	private String v_url="http://v.iask.com/v_play.php?vid=";
	private Integer timelength = 0;
	private Integer framecount = 0;
	private String vname = "";
	private ArrayList<VideoSource> v_list;
	
	public VideoParser(String _vid){
		this.vid = _vid;
		String url = this.v_url + this.vid;
		try{
			this.v_content = getInputStreamFromUrl(url);
		} catch(Exception e){
			e.printStackTrace();
		}
		this.v_list = new ArrayList<VideoSource>();
	}
	
	public void parser(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(this.v_content);
			parseDocument(dom);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public static InputStream getInputStreamFromUrl(String url){
        InputStream contentStream = null;
     
        try{
          HttpClient httpclient = new DefaultHttpClient();
          HttpResponse response = httpclient.execute(new HttpGet(url));
          contentStream = response.getEntity().getContent();
          
        } catch(Exception e){
           e.printStackTrace();
        }
        return contentStream;
     }
    
    private void parseDocument(Document dom){
		//get the root element
		Element docEle = dom.getDocumentElement();

		this.timelength = getIntValue(docEle, "timelength");
		this.framecount = getIntValue(docEle, "framecount");
		this.vname = getTextValue(docEle, "vname");
		
		
		NodeList nl_durl = docEle.getElementsByTagName("durl");
		if(nl_durl != null && nl_durl.getLength() > 0) {
			for(int i = 0 ; i < nl_durl.getLength();i++) {
				Element el = (Element)nl_durl.item(i);
		
				Integer _order = getIntValue(el, "order");
				Integer _length = getIntValue(el, "length");
				String _url = getTextValue(el, "url");
				_url = _url.replace("<![CDATA[", "").replace("]]", "");
				Log.v("parser", _url);
				VideoSource vs = new VideoSource(_order, _length, _url);
				this.v_list.add(vs);
			}
		}
	}
    
    private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}


	/**
	 * Calls getTextValue and returns a int value
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}

	public String getVid() {
		return vid;
	}

	public InputStream getV_content() {
		return v_content;
	}

	public String getV_url() {
		return v_url;
	}

	public Integer getTimelength() {
		return timelength;
	}

	public Integer getFramecount() {
		return framecount;
	}

	public String getVname() {
		return vname;
	}

	public ArrayList<VideoSource> getV_list() {
		return v_list;
	}
}
