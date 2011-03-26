package com.sjtu.is.mobili.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalyzeHtml 
{
	
	
    //从html中提取每个视频的信息
    public DataSet getData(String htmlSrc)   
    {   
 
        String cc = htmlSrc;
        //System.out.println(htmlSrc);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<Integer, String> actions = new HashMap<Integer, String>();
		
       
        //提取一项视频
        Pattern p = Pattern.compile("<a href=\"/video/av.*title.*</a>");//匹配视频
        Matcher m = p.matcher(cc);//开始编译
        for (int i = 0; m.find(); i++) {
        	String temp = m.group();
        	String title, pic, info, link;
        	
        	
        	Map<String, Object> map = new HashMap<String, Object>();
        	//System.out.println(temp);
        	
        	//提取题目
        	Pattern ptitle = Pattern.compile("title=\".*(视频|播放)");
        	Matcher mtitle = ptitle.matcher(temp);
         	if(mtitle.find())
        	{
        		title = mtitle.group();
        		title = title.replaceAll("title=\"", "");
        		title = title.replaceAll("视频", "");
        		title = title.replaceAll("播放.*", "");
        		//System.out.println(title);
        		map.put("title", title);
        	}       	
        	
        	//提取信息
        	Pattern pinfo = Pattern.compile("(视频长度|播放).*:[0-9]*[\\s]?\"");
         	Matcher minfo = pinfo.matcher(temp);
        	if(minfo.find())
        	{
        		info = minfo.group();
        		info = info.replaceAll("\"", "");
        		//System.out.println(info);
        		map.put("info", info);
        	} 
        	
        	
        	//提取预览图片地址
        	Pattern ppic = Pattern.compile("http.*?(jpg|png|gif|jpeg)");
        	Matcher mpic = ppic.matcher(temp);
        	if(mpic.find())
        	{
        		pic = mpic.group();
        		//System.out.println(pic);
        		map.put("img", pic);
        	}
        	
        	//提取链接地址
        	Pattern plink = Pattern.compile("href=\"/video/av[0-9]*/\"");
        	Matcher mlink = plink.matcher(temp);
        	if(mlink.find())
        	{
        		link = mlink.group();
        		link = link.replaceAll("\"", "");
        		link = link.replaceAll("href=", "");
        		link = "http://www.bilibili.us/" + link;
        		actions.put(i, link);
        		//System.out.println(link);
        	}       	
        	list.add(map);
        	//System.out.println("");

        }
        DataSet temp = new DataSet();
        temp.list = list;
        temp.actions = actions;
        return temp;
    }  

}  



