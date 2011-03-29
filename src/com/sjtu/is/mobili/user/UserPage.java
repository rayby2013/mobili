package com.sjtu.is.mobili.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sjtu.is.mobili.R;
import com.sjtu.is.mobili.http.HttpRequest;
import com.sjtu.is.mobili.utils.DrawableManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;



public class UserPage extends Activity {
	
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (!UserSession.isLogin()) finishActivity(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		pDialog = ProgressDialog.show(this, "", "用户信息载入中 …", true, true);
		
		final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
            	String html = (String)message.obj;
            	UserParser up = new UserParser(html);
            	setContentView(R.layout.userpage);
            	DrawableManager dm = new DrawableManager();
            	
            	ImageView iv = (ImageView)findViewById(R.id.user_avatar);
            	TextView un = (TextView)findViewById(R.id.user_name);
            	TextView ul = (TextView)findViewById(R.id.user_level);
            	TextView us = (TextView)findViewById(R.id.user_score);
            	
            	iv.setImageDrawable(dm.fetchDrawable(up.getImageUrl()));
            	un.setText(up.getUserName());
            	ul.setText(up.getType());
            	us.setText(up.getScore());
            	
            	pDialog.dismiss();
            }
		};
		
		Thread thread = new Thread() {
	            @Override
	            public void run() {
	            	HttpRequest hr = new HttpRequest();
	            	try{
	            		String src = hr.sendGetRequest("http://www.bilibili.us/member/index.php", "");
	            		Message message = handler.obtainMessage(1, src);
	            		handler.sendMessage(message);
	            	} catch(Exception e){
	            		Log.e("userPage", "获取用户主页错误");
	            		finishActivity(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	            	}
	            }
	        };
	    
	     thread.start();
	}
}


class UserParser {
	private String src;
	
	public UserParser(String html){
		src = html;
	}
	
	public String getUserName(){
		
		String regex = "<h3 class=\"userName\">(.*?)<";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(src);
		if (m.find()) return m.group(1);
		
		return "";
	}
	
	public String getUserId(){
		String userId=null;
		return userId;
	}
	
	public String getType(){
		String regex = "你目前的身份是：.*?\\s";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(src);
		if (m.find()) return m.group();
		return "";
	}
	
	public String getScore(){
		String regex = "积分：\\d+ 分";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(src);
		if (m.find()) return m.group();
		return "";
	}
	
	public String getImageUrl(){
		String regex = "<img class=\"mB10\" src=\"([^\"]+)\" ";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(src);
		if (m.find()) return m.group(1);
		return "";
	}
}
