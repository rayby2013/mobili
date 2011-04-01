package com.sjtu.is.mobili;


import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class GamePage extends MListActivity {
	private static final String[] menu = {"随便看看", "视频游戏", "视频攻略解说", "Mugen", "flash游戏", "Homepage"};
    //private Map<String, Object> actions = new HashMap<String, Object>();
	
 

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		actions = new HashMap<String, Object>();
		Intent rec = new Intent(this, MListView.class);   
		rec.putExtra("url", "http://www.bilibili.us/video/game.html"); 
		rec.putExtra("type", "main");
		actions.put(menu[0], rec);
		
		Intent videogame = new Intent(this, MListView.class);   
		videogame.putExtra("url", "http://www.bilibili.us/video/game-video-1.html");
		videogame.putExtra("type", "sub");
		actions.put(menu[1], videogame);
		
		Intent videostrategy = new Intent(this, MListView.class);   
		videostrategy.putExtra("url", "http://www.bilibili.us/video/game-ctary-1.html"); 
		videostrategy.putExtra("type", "sub");
		actions.put(menu[2], videostrategy);
		
		Intent mugen = new Intent(this, MListView.class);   
		mugen.putExtra("url", "http://www.bilibili.us/video/game-mugen-1.html");
		mugen.putExtra("type", "sub");
		actions.put(menu[3], mugen);
		
		Intent flash = new Intent(this, MListView.class);   
		flash.putExtra("url", "http://www.bilibili.us/video/game-flash-1.html"); 
		flash.putExtra("type", "sub");
		actions.put(menu[4], flash);


		actions.put(menu[5], new Intent(this, HomePage.class));
		
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1,
				menu));
		
	}
	
}
