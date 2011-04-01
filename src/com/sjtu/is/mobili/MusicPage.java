package com.sjtu.is.mobili;


import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MusicPage extends MListActivity {
	private static final String[] menu = {"随便看看", "音乐视频", "三次元音乐", "VOCALOID相关", "翻唱", "Homepage"};
	//private Map<String, Object> actions = new HashMap<String, Object>();
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actions = new HashMap<String, Object>();
		Intent rec = new Intent(this, MListView.class);   
		rec.putExtra("url", "http://www.bilibili.us/video/music.html"); 
		rec.putExtra("type", "main");
		actions.put(menu[0], rec);

		Intent musicvideo = new Intent(this, MListView.class);   
		musicvideo.putExtra("url", "http://www.bilibili.us/video/music-video-1.html"); 
		musicvideo.putExtra("type", "sub");
		actions.put(menu[1], musicvideo);

		Intent sanciyuan = new Intent(this, MListView.class);   
		sanciyuan.putExtra("url", "http://www.bilibili.us/video/music-coordinate-1.html"); 
		sanciyuan.putExtra("type", "sub");
		actions.put(menu[2], sanciyuan);

		Intent vocaloid = new Intent(this, MListView.class);   
		vocaloid.putExtra("url", "http://www.bilibili.us/video/music-vocaloid-1.html");
		vocaloid.putExtra("type", "sub");
		actions.put(menu[3], vocaloid);

		Intent reproducedversion = new Intent(this, MListView.class);   
		reproducedversion.putExtra("url", "http://www.bilibili.us/video/music-Cover-1.html");
		reproducedversion.putExtra("type", "sub");
		actions.put(menu[4], reproducedversion);


		actions.put(menu[5], new Intent(this, HomePage.class));

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				menu));

	}

}
