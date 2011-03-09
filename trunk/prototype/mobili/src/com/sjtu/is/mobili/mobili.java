package com.sjtu.is.mobili;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.*;

public class mobili extends Activity {
    WebView test;
    
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        test = (WebView)findViewById(R.id.webView1);
        
        test.setWebViewClient(new WebViewClient(){
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
        });
        test.setVisibility(25);
        test.getSettings().setJavaScriptEnabled(true);
        test.getSettings().setPluginsEnabled(true);
        test.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
    }
    
    public void openPage(View theButton) {
        test.loadUrl("http://www.bilibili.us/play.swf?vid=47658897");
    }
    
    public void zoomin(View theButton) {
    	test.getSettings().setBuiltInZoomControls(true);
    }
}