package com.sjtu.is.mobili.utils;
//源代码的下载分析
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetHtmlSrc 
{
	@SuppressWarnings("finally")
	public String getHtml(String url)
	{
		
		String temp = "";
		String html = "";
		try 
		{  
			URL aURL = new URL(url);
			HttpURLConnection conn= (HttpURLConnection)aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			 
			 
			while((temp = reader.readLine()) != null)
			{
				html = html + temp + "\n";
			}
			
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			html = "Wrong!";
		}
		finally
		{
			return html;
		}
	}
	

}
