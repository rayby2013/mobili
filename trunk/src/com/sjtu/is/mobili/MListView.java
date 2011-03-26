package com.sjtu.is.mobili;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.sjtu.is.mobili.utils.*;

public class MListView extends ListActivity {
	DataSet temp;
	private Map<Integer, String> actions;
	List<Map<String, Object>> list;
	ImageView iv;
	GetHtmlSrc ghs = new GetHtmlSrc();
	AnalyzeHtml ah = new AnalyzeHtml();
	AnalyzeHtml2 ah2 = new AnalyzeHtml2();
	String url = "";
	String type = "";
	String html = "";
	String link = "";
	String vid = "";
	MSimpleAdapter adapter;
	MyHandler myHandler;
	ProgressDialog dialog;



	class MyHandler extends Handler {
		public MyHandler() {
		}
		public MyHandler(Looper L) {
			super(L);
		}
		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {   
			// 接收子线程的消息   
			html = (String) msg.obj;
			dialog.dismiss();
			if(html.equals("Wrong!"))
			{

				Toast.makeText(getApplicationContext(), "网络异常！", Toast.LENGTH_SHORT).show();
			}
			else
			{

				if(type.equals("main"))
				{
					temp = ah.getData(html);
					actions = temp.actions;
					list = temp.list;
					adapter = new MSimpleAdapter(MListView.this,list ,R.layout.item,
							new String[]{"title","info","img"},
							new int[]{R.id.title,R.id.info,R.id.img});
				}
				if(type.equals("sub"))
				{
					temp = ah2.getData(html);
					actions = temp.actions;
					list = temp.list;
					adapter = new MSimpleAdapter(MListView.this,list ,R.layout.item,
							new String[]{"title","info","img"},
							new int[]{R.id.title,R.id.info,R.id.img});
				}
				setListAdapter(adapter);

			}
		}   
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		System.out.print(position);
		link = actions.get(position);
		Intent video = new Intent(this, ShowVideo.class);   
		video.putExtra("url", link);
		video.putExtra("imgUrl", (String)list.get(position).get("img"));
		startActivity(video);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.layout.menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.exit:
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;		   
		case R.id.about:
			new AlertDialog.Builder(MListView.this)
			.setTitle("About").setMessage("Mobili\nVersion:0.1\nCopyRight Sjtu.IS.Mobili Group\n")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			})
			.show();
			return true;		   
		case R.id.help:
			new AlertDialog.Builder(MListView.this)
			.setTitle("指南").setMessage("1、请使用Android 2.2系统，并安装Flash，否则视频无法播放\n2、请使用Wifi、3G或在网络流畅的地方使用本软件\n3、部分功能尚未实现，请见谅")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
				}
			})
			.show();
			return true;	   
		case R.id.Login:
			Toast.makeText(getApplicationContext(), "该功能尚未实现，请等待下一版本！", Toast.LENGTH_SHORT).show();
			return true;	
		case R.id.favourite:
			Toast.makeText(getApplicationContext(), "该功能尚未实现，请等待下一版本！", Toast.LENGTH_SHORT).show();
			return true;	
		}
		return true;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();   
		url = intent.getStringExtra("url");
		type = intent.getStringExtra("type");

		dialog = ProgressDialog.show(this, "", "下载数据，请稍等 …", true, true);

		myHandler = new MyHandler() ;
		Thread t = new Thread(new Runnable() 
		{
			public void run() 
			{
				//获取网页代码
				GetHtmlSrc ghs = new GetHtmlSrc();
				String html = ghs.getHtml(url);
				//System.out.print(html);
				//传递代码给主线程
				Message toMain = new Message();
				toMain.obj = html;
				MListView.this.myHandler.sendMessage(toMain);


			}
		});
		t.start();		

	}

}














