package com.sjtu.is.mobili.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetVid 
{
	
	
	
	public String gotVid(String url)
	{
		GetHtmlSrc ghs = new GetHtmlSrc();
		String html = ghs.getHtml(url);
		Pattern p = Pattern.compile("vid=[0-9]*");//匹配视频
	    Matcher m = p.matcher(html);
	    String temp = "";
	    if (m.find())
	    {
	    	temp = m.group();
	    	temp.replaceAll("vid=", "");
	    	
	    }
		return temp;
		
	}
}
