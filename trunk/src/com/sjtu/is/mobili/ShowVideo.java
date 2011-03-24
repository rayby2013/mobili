package com.sjtu.is.mobili;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;





public class ShowVideo extends Activity {
	private TextView tv;
	private String vid;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showvideo);
		Intent intent = getIntent();   
		vid = intent.getStringExtra("vid");
        tv = (TextView) findViewById(R.id.title);
        tv.setText(vid);
	}
	
	
}
