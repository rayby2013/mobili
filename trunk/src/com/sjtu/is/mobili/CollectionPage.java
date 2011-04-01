package com.sjtu.is.mobili;


import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class CollectionPage extends MListActivity {
	private static final String[] menu = {"随便看看", "专辑二次元", "专辑三次元", "Homepage"};
	//private Map<String, Object> actions = new HashMap<String, Object>();



	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		actions = new HashMap<String, Object>();
		Intent rec = new Intent(this, MListView.class);   
		rec.putExtra("url", "http://www.bilibili.us/video/part.html"); 
		rec.putExtra("type", "main");
		actions.put(menu[0], rec);

		Intent zhuan2 = new Intent(this, MListView.class);   
		zhuan2.putExtra("url", "http://www.bilibili.us/video/part-twoelement-1.html"); 
		zhuan2.putExtra("type", "sub");
		actions.put(menu[1], zhuan2);

		Intent zhuan3 = new Intent(this, MListView.class);   
		zhuan3.putExtra("url", "http://www.bilibili.us/video/part-coordinate-1.html"); 
		zhuan3.putExtra("type", "sub");
		actions.put(menu[2], zhuan3);

		actions.put(menu[3], new Intent(this, HomePage.class));


		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				menu));

	}
}
