package com.sjtu.is.mobili;


import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.j256.ormlite.dao.Dao;
import com.sjtu.is.mobili.db.DBHelper;
import com.sjtu.is.mobili.user.LoginDialog;
import com.sjtu.is.mobili.user.UserData;
import com.sjtu.is.mobili.utils.GetHtmlSrc;

public class HomePage extends MListActivity {
	private static final String[] menu = {"最热视频", "Anime", "Music", "Game", "Entertainment", "合集", "新番连载"};
	MyHandler myHandler;
	private final String PREFERENCES_NAME="mobili";
	private SharedPreferences settings;


	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
		//Toast.makeText(getApplicationContext(), "网络异常！", Toast.LENGTH_LONG).show();
		Intent rec = new Intent(this, MListView.class);   
		rec.putExtra("url", "http://www.bilibili.us/"); 
		rec.putExtra("type", "main");
		
		actions = new HashMap<String, Object>();
		actions.put(menu[0], rec);
		actions.put(menu[1], new Intent(this,AnimePage.class));
		actions.put(menu[2], new Intent(this,MusicPage.class));
		actions.put(menu[3], new Intent(this,GamePage.class));
		actions.put(menu[4], new Intent(this,EntertainmentPage .class));
		actions.put(menu[5], new Intent(this,CollectionPage.class));
		actions.put(menu[6], new Intent(this,SerializationPage.class));
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				menu));
		
    	if(settings.getBoolean("autoLogin", false)){
    		DBHelper dbh = new DBHelper(this);
				try{
					Dao<UserData, String> userDao = dbh.getUserDataDao();
					UserData ud = userDao.queryForId(settings.getString("lastUser", "test"));
					if (ud!=null){
						LoginDialog ld = new LoginDialog(this);
						ld.login(ud.getUsername(), ud.getPassword());
					}
				}catch(Exception e){
					Log.e("login", e.toString());
				}
    	}
		//测试网络
		myHandler = new MyHandler() ;
		Thread t = new Thread(new Runnable() 
		{
			public void run() 
			{
				boolean bool = testspeed();
				String temp;
				if(bool) temp = "true";
				else temp = "false";
				System.out.print(temp);
				Message toMain = new Message();
				toMain.obj = temp;
				HomePage.this.myHandler.sendMessage(toMain);
			}
		});
		t.start();	

	}


	class MyHandler extends Handler {
		public MyHandler() {
		}
		public MyHandler(Looper L) {
			super(L);
		}
		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {   
			// 接收子线程的消息   
			String bool = (String) msg.obj;
			if(bool.equals("false"))
			{
				new AlertDialog.Builder(HomePage.this)
				.setTitle("网络异常，是否退出？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).show();
			}
		}   
	}



	private boolean testspeed()
	{
		GetHtmlSrc ghs = new GetHtmlSrc();
		String temp = ghs.getHtml("http://www.baidu.com");
		if(temp.equals("Wrong!"))
			return false;
		else return true;
	}
	

}
