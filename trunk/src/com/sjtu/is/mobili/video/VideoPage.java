package com.sjtu.is.mobili.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class VideoPage extends Activity{
	
	private String vid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();   
		vid = intent.getStringExtra("vid");

		String html_data = "<html>"+
			"<head>"+
				"<meta http-equiv=\"Content-type\" content=\"text/html; charset=utf-8\">"+
				"<title>player</title>"+
			"</head>"+
				"<body id=\"player\">"+
				"<embed height=\"482\""+ 
					"width=\"950\""+ 
					"pluginspage=\"http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash\""+ 
					"rel=\"noreferrer\""+ 
					"flashvars=\"vid="+vid+"\""+ 
					"src=\"file:///android_assets/play.swf\""+ 
					"type=\"application/x-shockwave-flash\""+ 
					"allowfullscreen=\"true\""+ 
					"quality=\"high\">"+
				"</embed>"+
				"</body>"+
			"</html>"; 
		WebView wv = new WebView(this);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setAllowFileAccess(true);
		wv.getSettings().setPluginsEnabled(true);

		wv.loadData(html_data, "text/html", "utf-8");
		setContentView(wv);
	}
}
