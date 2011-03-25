package com.sjtu.is.mobili;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sjtu.is.mobili.http.HttpRequest;
import com.sjtu.is.mobili.utils.DataSet;
import com.sjtu.is.mobili.utils.MSimpleAdapter;

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

public class CommentPage extends ListActivity{
	private Map<Integer, String> actions;
	private List<Map<String, Object>> list;

	private String avid;
	private ProgressDialog pDialog=null;
	private CommentHandler ch=null;
	private String userId=null;
	
	class CommentHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			Log.v("comment", "数据装入");
			DataSet ds = (DataSet)msg.obj;
			if(ds.equals(null))
			{
				Toast.makeText(getApplicationContext(), "网络异常！", Toast.LENGTH_SHORT).show();
			}else{
				Log.v("comment", "载入评论"+ ds.list.size()+ "条");
				list = ds.list;
				actions = ds.actions;
				MSimpleAdapter adapter;
				adapter = new MSimpleAdapter(CommentPage.this,
											 list,
											 R.layout.comment,
											 new String[]{"userFaceUrl","userName","commentContent", "commentDate"},
											 new int[]{R.id.comment_avatar,
													   R.id.comment_user,
													   R.id.comment_content,
													   R.id.comment_time});
				setListAdapter(adapter);
			}
			pDialog.dismiss();
		}
		
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		System.out.print(position);
		userId = actions.get(position);
		Log.v("comment", "clicked on "+userId);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("comment", "启动评论");
		Intent intent = getIntent();  
		avid = intent.getStringExtra("avid");
		
		pDialog = ProgressDialog.show(this, "", "载入评论中 …", true, true);
		ch = new CommentHandler();
		Log.v("comment", "创建下载进程");
		Thread t = new Thread(new Runnable() 
		{
			public void run() 
			{
				
				CommentParser cp = new CommentParser(CommentPage.this.avid);
				Message toMain = new Message();
				toMain.obj = cp.parse();
				CommentPage.this.ch.sendMessage(toMain);

			}
		});
		t.start();
		
	}
}

class CommentParser{
	
	private final String url = "http://www.bilibili.us/plus/feedback_tg.php";
	private String avid="";
	private String content;
	private HttpRequest hr = new HttpRequest();
	
	public CommentParser(String avid){
		this.avid = avid;
		Log.v("comment", "comment by"+avid);
		content = hr.sendGetRequest(url, "aid="+this.avid);
	}
	
	public DataSet parse(){
		Log.v("comment", "start parse");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<Integer, String> actions = new HashMap<Integer, String>();
		
		final String _regex = "<div class=\"userface\">.*?</div><div class=\"tgbf_title\">.*?</div>";
		final String _re_face = "<img src=\"([^\"]+)\"";
		final String _re_name = "<a href=\"http://www.bilibili.us/member/"+
						  		"index.php\\?mid=(\\d+)\"\\s+target=\"_blank\">([^<]+)</a>";
		final String _re_content = "<div class=\"tgbf_content\">(.*)</div>";
		final String _re_date = "<span class=\"date\">([^<]+)</span>";
		
		
		Pattern p = Pattern.compile(_regex, Pattern.DOTALL);
		Pattern p_face = Pattern.compile(_re_face);
		Pattern p_name = Pattern.compile(_re_name);
		Pattern p_content = Pattern.compile(_re_content, Pattern.DOTALL);
		Pattern p_date = Pattern.compile(_re_date, Pattern.DOTALL);
		
		Matcher m = p.matcher(content);
		for (int i = 0; m.find(); i++) {
			String matched = m.group();
			String userFaceUrl, userName, userId, commentContent, commentDate;
			System.out.println(matched);
			
			userFaceUrl = doParse(p_face, matched).get(0);
			ArrayList<String> user = doParse(p_name, matched);
			if (user.size()==0) continue;
			userId = user.get(0);
			userName = user.get(1);
			commentContent = doParse(p_content, matched).get(0);
			commentDate = doParse(p_date, matched).get(0);
			
			Log.v("comment",userName);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userFaceUrl", userFaceUrl);
			map.put("userName", userName);
			map.put("commentContent", commentContent);
			map.put("commentDate", commentDate);
			
			list.add(map);
			actions.put(i, userId);
		}
		
		DataSet ds = new DataSet();
		ds.list = list;
		ds.actions = actions;
		return ds;
	}
	private ArrayList<String> doParse(Pattern p, String org){
		ArrayList<String> al = new ArrayList<String>();
		Matcher m = p.matcher(org);
		if (m.find()){
			for (int i=1; i<=m.groupCount();i++)
				al.add(m.group(i));
		}
		return al;
	}
	
}


