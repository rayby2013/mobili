package com.sjtu.is.mobili.user;


import java.net.URL;
import java.net.URLEncoder;

import com.sjtu.is.mobili.R;
import com.sjtu.is.mobili.http.HttpRequest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class LoginDialog extends Dialog implements OnClickListener{

	private Context context=null;
	private String url_str="https://secure.bilibili.us/member/index_do.php";
	
	public  LoginDialog(Context context) {
		super(context);
		
		this.context = context;
		setContentView(R.layout.login_dialog);
		setTitle("用户登陆");
		
		
		Button login_button = (Button)findViewById(R.id.login);
		Button cancel_button = (Button)findViewById(R.id.cancel);
		
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
	
	private void login(){
		
		EditText login_et = (EditText)findViewById(R.id.username); 
    	EditText passwd_et = (EditText)findViewById(R.id.password);
		final String username = login_et.getText().toString();
		final String password = passwd_et.getText().toString();
		boolean is_login = false;
    	
    	HttpRequest hq = new HttpRequest();
    	try{
    		String data_str = "fmdo=login&dopost=login&gourl=&keeptime=604800";
    		data_str+= "&userid=" + URLEncoder.encode(username, "UTF-8");
    		data_str+= "&pwd=" + URLEncoder.encode(password, "UTF-8");
    		Log.v("login", "post data:"+data_str);
    		
    		URL url = new URL(url_str);
    		String output = hq.postData(data_str, url);
    		
    		is_login = checkLogin(output);
    		
    	} catch(Exception e){
    		Log.d("login",e.toString());
    	}finally{
    		new UserSession(username, "!");
    		if (!is_login){
    			 new AlertDialog.Builder(context)
 				.setTitle("错误").setMessage("登陆失败")
 				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
 					public void onClick(DialogInterface dialog, int whichButton) {
 						UserSession.setLogin(false);
 						dialog.dismiss();
 					}
 				})
 				.show();
    		}else{
    			 new AlertDialog.Builder(context)
 				.setTitle("成功").setMessage("登陆成功")
 				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
 					public void onClick(DialogInterface dialog, int whichButton) {
 						UserSession.setLogin(true);
 						//DBHelper dbh = new UserDBHelper(context);
 						//DBHandler dbhr = new DBHandler(dbh);
 						//String sql = "select id from Accounts where username="+username;
 						//Cursor cursor = dbhr.execSQL(sql);
 						
 						dialog.dismiss();
 						dismiss();
 					}
 				})
 				.show();
    		}
    	}
		
	}
	
	private boolean checkLogin(String output){
		if (output.indexOf("成功")>=0) return true;
		return false;
	}

}
