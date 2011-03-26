package com.sjtu.is.mobili;


import java.util.HashMap;
import java.util.Map;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AnimePage extends ListActivity {
	private static final String[] menu = {"随便看看", "MAD.AMV", "MMD.3D", "二次元鬼畜", "期刊.其他", "Homepage", };
	private Map<String, Object> actions = new HashMap<String, Object>();



	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		Intent rec = new Intent(this, MListView.class);   
		rec.putExtra("url", "http://www.bilibili.us/video/douga.html"); 
		rec.putExtra("type", "main");
		actions.put(menu[0], rec);

		Intent madamy = new Intent(this, MListView.class);   
		madamy.putExtra("url", "http://www.bilibili.us/video/douga-mad-1.html"); 
		madamy.putExtra("type", "sub");
		actions.put(menu[1], madamy);

		Intent mm3d = new Intent(this, MListView.class);   
		mm3d.putExtra("url", "http://www.bilibili.us/video/douga-mmd-1.html"); 
		mm3d.putExtra("type", "sub");
		actions.put(menu[2], mm3d);

		Intent oni = new Intent(this, MListView.class);   
		oni.putExtra("url", "http://www.bilibili.us/video/douga-kichiku-1.html"); 
		oni.putExtra("type", "sub");
		actions.put(menu[3], oni);

		Intent periodical = new Intent(this, MListView.class);   
		periodical.putExtra("url", "http://www.bilibili.us/video/douga-else-1.html"); 
		periodical.putExtra("type", "sub");
		actions.put(menu[4], periodical);


		actions.put(menu[5], new Intent(this, HomePage.class));



		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				menu));

	}


	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.layout.menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.exit:
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;		   
		case R.id.about:
			new AlertDialog.Builder(AnimePage.this)
			.setTitle("About").setMessage("Mobili\nVersion:0.1\nCopyRight Sjtu.IS.Mobili Group\n")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			})
			.show();
			return true;		   
		case R.id.help:
			new AlertDialog.Builder(AnimePage.this)
			.setTitle("指南").setMessage("1、请使用Android 2.2系统，并安装Flash，否则视频无法播放\n2、请使用Wifi、3G或在网络流畅的地方使用本软件\n3、部分功能尚未实现，请见谅")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			})
			.show();
			return true;	   
		case R.id.Login:
			Toast.makeText(getApplicationContext(), "该功能尚未实现，请等待下一版本！", Toast.LENGTH_SHORT).show();
			return true;	
		case R.id.favourite:
			Toast.makeText(getApplicationContext(), "该功能尚未实现，请等待下一版本！", Toast.LENGTH_SHORT).show();
			return true;	
		}
		return true;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String text = (String) l.getItemAtPosition(position);
		startActivity((Intent) actions.get(text));
	}

}
