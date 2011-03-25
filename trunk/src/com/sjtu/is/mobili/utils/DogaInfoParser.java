package com.sjtu.is.mobili.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.sjtu.is.mobili.user.UserData;

public class DogaInfoParser 
{
	private String htmlSrc;
	private String url;
	
	public DogaInfoParser(String url){
		this.url = url;
		GetHtmlSrc ghs = new GetHtmlSrc();
		htmlSrc = ghs.getHtml(url);
	}
	
	
	public String getVid()
	{
		
		Pattern p = Pattern.compile("vid=[0-9]*");//匹配视频
	    Matcher m = p.matcher(htmlSrc);
	    String temp = "";
	    if (m.find())
	    {
	    	temp = m.group();
	    	temp.replaceAll("vid=", "");
	    	
	    }
		return temp;
		
	}
	
	public String getAid()
	{

		String _url = url.replace("http://", "").replace("www.bilibili.us/video/av", "").replace("www.bilibili.us//video/av", "");
		Log.v("parser", _url);
		return _url.replace("/", "");
	}
	
	public UserData getAuthor(){
		
		String regex_name = "\"usname\"><a href='http://www.bilibili.us/member/index.php\\?mid=(\\d+)' target='_blank'>(.*?)<";
		Pattern p_name = Pattern.compile(regex_name, Pattern.DOTALL);
		Matcher m_name = p_name.matcher(htmlSrc);
		UserData ud = null;
		if (m_name.find()){
			String userId = m_name.group(1);
			String userName = m_name.group(2);
			ud = new UserData(userName, userId);
		}
		return ud;
	}
	
	public String getDescription(){
		String regex = "<div class=\"intro\">(.*?)</div>";
		Pattern p = Pattern.compile(regex, Pattern.DOTALL);
		Matcher m = p.matcher(htmlSrc);
		if (m.find()){
			return m.group(1);
		}
		return "";
	}
	
	public String getTitle(){
		String regex = "id=\"titles\">(.*?)<";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(htmlSrc);
		if (m.find()){
			return m.group(1);
		}
		return "";
	}
}
