package com.sjtu.is.mobili;


import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class EntertainmentPage extends MListActivity {
	private static final String[] menu = {"随便看看", "生活娱乐", "舞蹈", "三次元鬼畜", "影视", "Homepage"};
    //private Map<String, Object> actions = new HashMap<String, Object>();
	
 

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		actions = new HashMap<String, Object>();
		Intent rec = new Intent(this, MListView.class);   
		rec.putExtra("url", "http://www.bilibili.us/video/ent.html"); 
		rec.putExtra("type", "main");
		actions.put(menu[0], rec);
		
		Intent life = new Intent(this, MListView.class);   
		life.putExtra("url", "http://www.bilibili.us/video/ent-life-1.html");
		life.putExtra("type", "sub");
		actions.put(menu[1], life);
		
		Intent dance = new Intent(this, MListView.class);   
		dance.putExtra("url", "http://www.bilibili.us/video/ent-dance-1.html");
		dance.putExtra("type", "sub");
		actions.put(menu[2], dance);
		
		Intent sanciyuan = new Intent(this, MListView.class);   
		sanciyuan.putExtra("url", "http://www.bilibili.us/video/ent-Kichiku-1.html");
		sanciyuan.putExtra("type", "sub");
		actions.put(menu[3], sanciyuan);
		
		Intent video = new Intent(this, MListView.class);   
		video.putExtra("url", "http://www.bilibili.us/video/ent-telvisn-1.html"); 
		video.putExtra("type", "sub");
		actions.put(menu[4], video);


		actions.put(menu[5], new Intent(this, HomePage.class));

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				menu));
		
	}
}
