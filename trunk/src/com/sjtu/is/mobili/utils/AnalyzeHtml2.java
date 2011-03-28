package com.sjtu.is.mobili.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//子菜单的html语言格式与主菜单不同
public class AnalyzeHtml2 
{
	
	
    //从html中提取每个视频的信息
    public DataSet getData(String htmlSrc)   
    {   
 
        String cc = htmlSrc;
        cc = cc.replaceAll("\n", "");
        //System.out.println(htmlSrc);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<Integer, String> actions = new HashMap<Integer, String>();
		
       
        //提取一项视频
        Pattern p = Pattern.compile("<div class=\"minbox\">.*?<div class=\"sinfo\">.*?</div>.*?</div>");//匹配视频
        Matcher m = p.matcher(cc);//开始编译
        for (int i = 0; m.find(); i++) {
        	String temp = m.group();
        	String title, pic, info, link;
        	
        	
        	Map<String, Object> map = new HashMap<String, Object>();
        	//System.out.println(temp);
        	
        	//提取题目
        	Pattern ptitle = Pattern.compile("class=\"title\">.*?/a>");
        	Matcher mtitle = ptitle.matcher(temp);
         	if(mtitle.find())
        	{
        		title = mtitle.group();
        		title = title.replaceAll("class=\"title\">", "");
        		title = title.replaceAll("</a>", "");
        		//title = title.replaceAll("播放.*", "");
        		//System.out.println(title);
        		map.put("title", title);
        	}       	
        	
        	//提取信息
        	Pattern pinfo = Pattern.compile("<div class=\"sinfo\"><b>.*?</div>");
         	Matcher minfo = pinfo.matcher(temp);
        	if(minfo.find())
        	{
        		info = minfo.group();
        		info = info.replaceAll("<div class=\"sinfo\"><b>", "");
        		info = info.replaceAll("</div>", "");
        		info = info.replaceAll("</b>", "");
        		//System.out.println(info);
        		map.put("info", info);
        	} 
        	
        	
        	//提取预览图片地址
        	Pattern ppic = Pattern.compile("<img src=\'http.*?(jpg|png|gif|jpeg)\'");
        	Matcher mpic = ppic.matcher(temp);
        	if(mpic.find())
        	{
        		pic = mpic.group();
        		//System.out.println(pic);
        		pic = pic.substring(9);
        		map.put("img", pic.replace("\'", ""));
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



