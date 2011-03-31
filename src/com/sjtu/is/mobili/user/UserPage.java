package com.sjtu.is.mobili.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjtu.is.mobili.R;
import com.sjtu.is.mobili.http.HttpRequest;
import com.sjtu.is.mobili.utils.DrawableManager;



public class UserPage extends Activity {
	
	private final String PREFERENCES_NAME="mobili";
	private ProgressDialog pDialog;
	private SharedPreferences settings;
	private String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (!UserSession.isLogin()) finishActivity(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		pDialog = ProgressDialog.show(this, "", "用户信息载入中 …", true, true);
		settings = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		
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
            	
            	username = up.getUserName();
            	iv.setImageDrawable(dm.fetchDrawable(up.getImageUrl()));
            	un.setText(up.getUserName());
            	ul.setText(up.getType());
            	us.setText(up.getScore());
            	
            	CheckBox saveAuth = (CheckBox)findViewById(R.id.save_auth);
            	CheckBox autoLogin = (CheckBox)findViewById(R.id.auto_login);
            	Button confirm = (Button)findViewById(R.id.user_confirm);
        	    
            	saveAuth.setChecked(settings.getBoolean("saveAuth", false));
            	autoLogin.setChecked(settings.getBoolean("autoLogin", false));
            	
        		OnClickListener saveAuthListener = new OnClickListener() {
        	        public void onClick(View v) {
        	        	CheckBox saveAuth = (CheckBox)v;
        	        	CheckBox autoLogin = (CheckBox)findViewById(R.id.auto_login);
        	        	
        	        	if (saveAuth.isChecked()){
        	        		autoLogin.setClickable(true);
        	        	}else{
        	        		autoLogin.setClickable(false);
        	        	}
        	        }
        	    };
        	    OnClickListener autoLoginListener = new OnClickListener() {
        	        public void onClick(View v) {
        	        	CheckBox saveAuth = (CheckBox)findViewById(R.id.save_auth);
        	        	CheckBox autoLogin = (CheckBox)v;
        	        	
        	        	if (autoLogin.isChecked()){
        	        		saveAuth.setChecked(true);
        	        		saveAuth.setClickable(false);
        	        	}else{
        	        		saveAuth.setClickable(true);
        	        	}
        	        }
        	    };
        	    
        	    OnClickListener confirmListener = new OnClickListener() {
        	        public void onClick(View v) {
        	        	CheckBox saveAuth = (CheckBox)findViewById(R.id.save_auth);
                    	CheckBox autoLogin = (CheckBox)findViewById(R.id.auto_login);
        	        	
                    	SharedPreferences.Editor editor = settings.edit();
                    	editor.putBoolean("saveAuth", saveAuth.isChecked());
                    	editor.putBoolean("autoLogin", autoLogin.isChecked());
                    	editor.putString("lastUser", username);
                    	
                    	Log.v("userPage", "保存用户数据");
                    	Log.d("userpage", "saveAuth: "+saveAuth.isChecked());
                    	Log.d("userpage", "autoLogin: "+autoLogin.isChecked());
                    	
                    	editor.commit();
                    	Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
        	        }
        	    };
        		
        		saveAuth.setOnClickListener(saveAuthListener);
        		autoLogin.setOnClickListener(autoLoginListener);
        		confirm.setOnClickListener(confirmListener);
            	
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
