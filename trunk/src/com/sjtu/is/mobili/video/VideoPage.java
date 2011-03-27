package com.sjtu.is.mobili.video;

import com.sjtu.is.mobili.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VideoPage extends Activity{
	
	private String vid;
	private WebView wv = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();   
		vid = intent.getStringExtra("vid");
		
		setContentView(R.layout.webviewplayer);

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
					"flashvars=\""+vid+"\""+ 
					"src=\"file:///android_assets/play.swf\""+ 
					"type=\"application/x-shockwave-flash\""+ 
					"allowfullscreen=\"true\""+ 
					"quality=\"high\">"+
				"</embed>"+
				"</body>"+
			"</html>"; 
		
		wv = (WebView)findViewById(R.id.webViewPlayer);
		wv.setWebViewClient(new WebViewClient(){
            @Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
		});
		
	    wv.setInitialScale(25);
	    wv.getSettings().setJavaScriptEnabled(true);
	    wv.getSettings().setPluginsEnabled(true);
	    wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
		wv.getSettings().setAllowFileAccess(true);
		wv.getSettings().setBuiltInZoomControls(true);
		//调用远端的flash播放器
		wv.loadUrl("http://www.bilibili.us/play.swf?"+vid);
		//本地播放器部分，暂时无法使用
		//wv.loadData(html_data, "text/html", "utf-8");
	}
	
	protected void onPause() {
		wv.loadData("", "text/html", "utf-8");
		super.onPause();
	}
	
	protected void onResume() {
		wv.loadUrl("http://www.bilibili.us/play.swf?"+vid);
		super.onResume();
	}
	
}
