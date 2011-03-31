package com.sjtu.is.mobili.user;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.sjtu.is.mobili.R;
import com.sjtu.is.mobili.db.DBHelper;
import com.sjtu.is.mobili.http.HttpRequest;

public class LoginDialog extends Dialog implements OnClickListener{
	private final String PREFERENCES_NAME="mobili";
	private SharedPreferences settings;
	private ProgressDialog pDialog;
	private Context context=null;
	private String url_str="https://secure.bilibili.us/member/index_do.php";
	
	public  LoginDialog(Context context) {
		super(context);
		settings = context.getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		
		this.context = context;
		setContentView(R.layout.login_dialog);
		setTitle("用户登陆");
		
		
		Button login_button = (Button)findViewById(R.id.login);
		Button cancel_button = (Button)findViewById(R.id.cancel);
		EditText login_et = (EditText)findViewById(R.id.username); 
    	EditText passwd_et = (EditText)findViewById(R.id.password);
    	
    	if(settings.getBoolean("saveAuth", false)){
    		DBHelper dbh = new DBHelper(context);
				try{
					Dao<UserData, String> userDao = dbh.getUserDataDao();
					UserData ud = userDao.queryForId(settings.getString("lastUser", "test"));
					if (ud!=null){
						login_et.setText(ud.getUsername());
						passwd_et.setText(ud.getPassword());
					}
				}catch(Exception e){
					Log.e("login", e.toString());
				}
    	}
    	
		
		login_button.setOnClickListener(this);
		cancel_button.setOnClickListener(this);
		Log.v("login", "drawed dialog");

	}
	

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.login: login(); break;
		case R.id.cancel: cancel(); break;
		}
		
	}
	
	public void login(){
		
		EditText login_et = (EditText)findViewById(R.id.username); 
    	EditText passwd_et = (EditText)findViewById(R.id.password);
		final String username = login_et.getText().toString();
		final String password = passwd_et.getText().toString();
		
		login(username, password);
	}
	
	public void login(final String username, final String password){
		boolean is_login = false;
		pDialog = ProgressDialog.show(context, "", "用户信息载入中 …", true, true);
		
    	HttpRequest hq = new HttpRequest();
    	try{
    		String data_str = "fmdo=login&dopost=login&gourl=&keeptime=604800";
    		data_str+= "&userid=" + URLEncoder.encode(username, "UTF-8");
    		data_str+= "&pwd=" + URLEncoder.encode(password, "UTF-8");
    		Log.v("login", "post data:"+data_str);
    		
    		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("fmdo", "login"));
            nvps.add(new BasicNameValuePair("dopost", "login"));
            nvps.add(new BasicNameValuePair("gourl", ""));
            nvps.add(new BasicNameValuePair("keeptime", "604800"));
            nvps.add(new BasicNameValuePair("userid", username));
            nvps.add(new BasicNameValuePair("pwd", password));
    		
    		String output = hq.postData(url_str, nvps);
    		
    		is_login = checkLogin(output);
    		
    	} catch(Exception e){
    		Log.d("login",e.toString());
    	}finally{
    		new UserSession(username, "!");
    		if (!is_login){
    			Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show();
 				UserSession.setLogin(false);
 				
    		}else{
    			Toast.makeText(context, "登陆成功", Toast.LENGTH_SHORT).show();
    			UserSession.setLogin(true);
					DBHelper dbh = new DBHelper(context);
					try{
						Dao<UserData, String> userDao = dbh.getUserDataDao();
						UserData ud = userDao.queryForId(username);
						if (ud==null){
							ud = new UserData(username, "1", password);
							userDao.create(ud);
						} else{
							ud = new UserData(username, "1", password);
							userDao.update(ud);
						}
					}catch(Exception e){
						Log.e("login", e.toString());
					}
    		}
    		pDialog.dismiss();
    		dismiss();
    	}
	}
	
	private boolean checkLogin(String output){
		if (output.indexOf("成功")>=0) return true;
		return false;
	}

}
