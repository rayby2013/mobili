package com.sjtu.is.mobili;

import java.util.HashMap;
import java.util.Map;

import com.sjtu.is.mobili.user.LoginDialog;
import com.sjtu.is.mobili.utils.GetHtmlSrc;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomePage extends ListActivity {
	private static final String[] menu = {"最热视频", "Anime", "Music", "Game", "Entertainment", "合集 ", "新番连载", "comment"};
	private Map<String, Object> actions = new HashMap<String, Object>();
	MyHandler myHandler;



	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Toast.makeText(getApplicationContext(), "网络异常！", Toast.LENGTH_LONG).show();
		//Intent rec = new Intent(this, MListView.class);   
		//rec.putExtra("url", "http://www.bilibili.us/"); 
		//rec.putExtra("type", "main");
		Intent d = new Intent(this, CommentPage.class);   
		d.putExtra("avid", "72757");
		actions.put(menu[0], new Intent(this,AnimePage.class));
		actions.put(menu[1], new Intent(this,AnimePage.class));
		actions.put(menu[2], new Intent(this,MusicPage.class));
		actions.put(menu[3], new Intent(this,GamePage.class));
		actions.put(menu[4], new Intent(this,EntertainmentPage .class));
		actions.put(menu[5], new Intent(this,CollectionPage.class));
		actions.put(menu[6], new Intent(this,SerializationPage.class));
		actions.put(menu[7], d);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				menu));
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

	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.layout.menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.exit:
			Log.v("menu", "exit");
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;		   
		case R.id.about:
			Log.v("menu", "about");
			new AlertDialog.Builder(HomePage.this)
			.setTitle("About").setMessage("Mobili\nVersion:0.1\nCopyRight Sjtu.IS.Mobili Group\n")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			})
			.show();
			return true;		   
		case R.id.help:
			Log.v("menu", "help");
			new AlertDialog.Builder(HomePage.this)
			.setTitle("指南").setMessage("1、请使用Android 2.2系统，并安装Flash，否则视频无法播放\n2、请使用Wifi、3G或在网络流畅的地方使用本软件\n3、部分功能尚未实现，请见谅")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			})
			.show();
			return true;	   
		case R.id.Login:
			Log.v("menu", "login");
			callLogin();
			return true;	
		case R.id.favourite:
			Log.v("menu", "bookmarks");
			Toast.makeText(getApplicationContext(), "该功能尚未实现，请等待下一版本！", Toast.LENGTH_SHORT).show();
			return true;	
		}
		return true;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		System.out.print(position);
		String text = (String) l.getItemAtPosition(position);
		startActivity((Intent) actions.get(text));
	}

	private void callLogin(){
		LoginDialog login_dialog = new LoginDialog(this);
		login_dialog.show();
	}
}
