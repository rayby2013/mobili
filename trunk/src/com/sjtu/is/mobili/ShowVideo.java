package com.sjtu.is.mobili;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjtu.is.mobili.user.UserData;
import com.sjtu.is.mobili.utils.DrawableManager;
import com.sjtu.is.mobili.video.VideoData;
import com.sjtu.is.mobili.video.VideoPage;





public class ShowVideo extends Activity {
	
	private VideoData videoData = null;
	private String url;
	private String imgUrl;
	private ProgressDialog pDialog;
	
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
        		WebView dd = (WebView)findViewById(R.id.doga_description);
        		
        		DrawableManager dm = new DrawableManager();
                
        		UserData ud = videoData.getUper();
        		
        		if (ud!=null){
        			un.setText(ud.getUsername());
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
                di.setImageDrawable(dm.fetchDrawable(videoData.getImgUrl()));
                
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
			Intent video = new Intent(this, VideoPage.class);
			video.putExtra("vid", videoData.getVid());
			startActivity(video);
			return true;
		}
		return true;
	}
	
}
