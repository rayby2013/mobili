package com.sjtu.is.mobili;


import java.util.HashMap;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class SerializationPage extends MListActivity {
	private static final String[] menu = {"随便看看", "新番二次元", "新番三次元", "Homepage"};
	//private Map<String, Object> actions = new HashMap<String, Object>();
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actions = new HashMap<String, Object>();
		Intent rec = new Intent(this, MListView.class);   
		rec.putExtra("url", "http://www.bilibili.us/video/bangumi.html");
		rec.putExtra("type", "main");
		actions.put(menu[0], rec);

		Intent new2 = new Intent(this, MListView.class);   
		new2.putExtra("url", "http://www.bilibili.us/video/bangumi-two-1.html"); 
		new2.putExtra("type", "sub");
		actions.put(menu[1], new2);

		Intent new3 = new Intent(this, MListView.class);   
		new3.putExtra("url", "http://www.bilibili.us/video/bangumi-three-1.html");
		new3.putExtra("type", "sub");
		actions.put(menu[2], new3);

		actions.put(menu[3], new Intent(this, HomePage.class));


		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				menu));

	}

}
