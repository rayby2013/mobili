package com.sjtu.is.mobili;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjtu.is.mobili.user.BookMarkPage;
import com.sjtu.is.mobili.user.UserData;
import com.sjtu.is.mobili.user.UserSession;
import com.sjtu.is.mobili.utils.DrawableManager;
import com.sjtu.is.mobili.video.VideoData;





public class ShowVideo extends Activity {
	
	private VideoData videoData = null;
	private String url;
	private String imgUrl;
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showvideo);
		Intent intent = getIntent();
		
		url = intent.getStringExtra("url");
		imgUrl = intent.getStringExtra("imgUrl");
        
		pDialog = ProgressDialog.show(this, "", "视频信息载入中 …", true, true);
		
		final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
            	videoData = (VideoData)message.obj;
            	TextView title = (TextView)findViewById(R.id.title);
        		ImageView di = (ImageView)findViewById(R.id.doga_img);
        		TextView un = (TextView)findViewById(R.id.uper_name);
        		TextView avnumber = (TextView)findViewById(R.id.av_number);
        		WebView dd = (WebView)findViewById(R.id.doga_description);
        		
        		DrawableManager dm = new DrawableManager();
                
        		UserData ud = videoData.getUper();
        		
        		if (ud!=null){
        			un.setText("UP主:"+ud.getUsername());
        		}
        		String dEncoded = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body>"
        						  +videoData.getDescription()+"</body></html> ";
        		//try{
        			//dEncoded = URLEncoder.encode(videoData.getDescription(),"utf-8");
        		//}catch(Exception e){
        			//e.printStackTrace();
        		//}
                title.setText(videoData.getTitle());
                dd.loadData(dEncoded, "text/html", "utf-8");
                if(videoData.getImgUrl()!=null)di.setImageDrawable(dm.fetchDrawable(videoData.getImgUrl()));
                else di.setVisibility(View.INVISIBLE);
                String avID = url.replaceAll("http://www.bilibili.us//video/", "");
                avID = avID.replaceAll("/", "");
                avnumber.setText("av号:"+avID);
                
                pDialog.dismiss();
            }
        };
        
        Thread thread = new Thread() {
            @Override
            public void run() {
                //TODO : set imageView to a "pending" image
                VideoData vd = new VideoData(ShowVideo.this.url, ShowVideo.this.imgUrl);
                Message message = handler.obtainMessage(1, vd);
                handler.sendMessage(message);
            }
        };
        thread.start();
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		
		MenuItem dm = menu.findItem(R.id.doga_mark);
		if (UserSession.isLogin()) {
			if(dm!=null) dm.setVisible(true);
		}
		else {
			if(dm!=null) dm.setVisible(false);
		}
		
		return super.onPrepareOptionsMenu(menu);   
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.doga_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.doga_comment:
			Intent comment = new Intent(this, CommentPage.class);   
			comment.putExtra("avid", videoData.getAid());
			startActivity(comment);
			return true;		   
		case R.id.doga_play:
			Intent video = new Intent(this, com.sjtu.is.mobili.video.VideoPage.class);
			video.putExtra("vid", videoData.getVid());
			startActivity(video);
			return true;
		case R.id.doga_mark:
			final Handler handler = new Handler() {
	            @Override
	            public void handleMessage(Message message) {
	            	pDialog.dismiss();
	            	if ((Boolean)message.obj){
	            		Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
	            	}
	            	else{
	            		Toast.makeText(getApplicationContext(), "收藏失败", Toast.LENGTH_SHORT).show();
	            	}
	            }
			};
			pDialog = ProgressDialog.show(this, "", "标记收藏 …", true, true);
	        Thread thread = new Thread() {
	            @Override
	            public void run() {
	                Message message = handler.obtainMessage(1, BookMarkPage.setAsMark(videoData.getAid()));
	                handler.sendMessage(message);
	            }
	        };
	        thread.start();
		}	
		return true;
	}
	
}
