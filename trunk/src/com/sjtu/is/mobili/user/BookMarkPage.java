package com.sjtu.is.mobili.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.sjtu.is.mobili.R;
import com.sjtu.is.mobili.ShowVideo;
import com.sjtu.is.mobili.http.HttpRequest;
import com.sjtu.is.mobili.utils.MSimpleAdapter;



public class BookMarkPage extends ListActivity {
	
	private final String regex = "<a href='/video/av(\\d+)/' target='_blank'>\\s*([^<]+)</a>"+
		" </span>\\s*<div class=\"itemTip\">([^<]+)<";
	private final String url = "http://www.bilibili.us/member/mystow.php";
	private Map<Integer, String> actions = new HashMap<Integer, String>();
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
	private ProgressDialog pDialog=null;
	
	public static boolean setAsMark(String aid){
		String u = "http://www.bilibili.us/plus/stow.php?aid=" + aid;
		try{
			HttpRequest hr = new HttpRequest();
			String res = hr.sendGetRequest(u, "");
			if (res.indexOf("成功")>=0){
				return true;
			}else{
				Log.d("bookmark", aid);
				Log.d("bookmark", res);
			}
		}catch(Exception e){
			Log.e("bookmark", e.toString());
			return false;
		}
		return false;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		System.out.print(position);
		String link = actions.get(position);
		Intent video = new Intent(this, ShowVideo.class);   
		video.putExtra("url", link);
		startActivity(video);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (!UserSession.isLogin()) finishActivity(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		super.onCreate(savedInstanceState);
		
		Log.v("bookmark", "启动收藏夹");
				
		pDialog = ProgressDialog.show(this, "", "载入收藏夹中 …", true, true);
		
		final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
            	String html = (String)message.obj;
            	Pattern pattern = Pattern.compile(regex);
            	Matcher matcher = pattern.matcher(html);
            	
            	for (int i =0 ; matcher.find(); i++){
            		Map<String, Object> map = new HashMap<String, Object>();
            		String aid = matcher.group(1);
            		String title = matcher.group(2);
            		String date = matcher.group(3);
            		//map.put("aid", aid);
            		map.put("title", title);
            		map.put("date", date);
            		
            		list.add(map);
            		actions.put(i, "http://www.bilibili.us/video/av"+aid+"/");
            	}
            	
            	MSimpleAdapter adapter;
            	adapter = new MSimpleAdapter(BookMarkPage.this,
						 list,
						 R.layout.bookmark_page,
						 new String[]{"title","date"},
						 new int[]{R.id.bookmark_title,
								   R.id.bookmark_date});
            	
            	setListAdapter(adapter);	
            	pDialog.dismiss();
            }
		};
		
		
		Log.v("bookmark", "创建下载进程");
		Thread t = new Thread(new Runnable() 
		{
			public void run() 
			{
				try{
					HttpRequest hr = new HttpRequest();
					String html = hr.sendGetRequest(BookMarkPage.this.url, "");
					Message toMain = new Message();
					toMain.obj = html;
					handler.sendMessage(toMain);
				}catch (Exception e){
					Log.e("bookmark" ,e.toString());
					Toast.makeText(getApplicationContext(), "载入收藏夹", Toast.LENGTH_SHORT).show();
					finishActivity(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				}

			}
		});
		t.start();
		
	}
}