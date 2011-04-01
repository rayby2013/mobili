package com.sjtu.is.mobili;


import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class AnimePage extends MListActivity {
	private static final String[] menu = {"随便看看", "MAD.AMV", "MMD.3D", "二次元鬼畜", "期刊.其他", "Homepage", };
	//private Map<String, Object> actions = new HashMap<String, Object>();
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actions = new HashMap<String, Object>();
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

}
