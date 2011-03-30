package com.sjtu.is.mobili;

import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.sjtu.is.mobili.user.BookMarkPage;
import com.sjtu.is.mobili.user.LoginDialog;
import com.sjtu.is.mobili.user.UserPage;
import com.sjtu.is.mobili.user.UserSession;

public class MListActivity extends ListActivity {
	public Map<String, Object> actions;
	

	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		
		MenuItem li = menu.findItem(R.id.Manage);
		MenuItem lo = menu.findItem(R.id.Login);
		if (UserSession.isLogin()) {
			if(li!=null) li.setVisible(true);
			if(lo!=null) lo.setVisible(false);
		}
		else {
			if(li!=null) li.setVisible(false);
			if(lo!=null) lo.setVisible(true);
		}
		
		return super.onPrepareOptionsMenu(menu);   
	}

	@Override
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
			new AlertDialog.Builder(this)
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
			new AlertDialog.Builder(this)
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
			if (!UserSession.isLogin())
				Toast.makeText(getApplicationContext(), "请先登陆", Toast.LENGTH_SHORT).show();
			else{
				Intent bp = new Intent(this, BookMarkPage.class);   
				startActivity(bp);
			}
			return true;
		
		case R.id.Manage:
			Log.v("menu", "manager");
			Intent up = new Intent(this, UserPage.class);   
			startActivity(up);
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
