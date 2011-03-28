package com.sjtu.is.mobili.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sjtu.is.mobili.R;
import com.sjtu.is.mobili.http.HttpRequest;


public class CommentDialog extends Dialog implements OnClickListener{
	
	private Context context;
	private String aid;
	
	private final String url = "http://www.bilibili.us/plus/feedback_tg.php";
	
	public CommentDialog(Context context, String aid) {
		super(context);
		
		if(!UserSession.isLogin()){
			Log.e("commentDialog", "用户没有登陆");
			Toast.makeText(context, "您没有登陆", Toast.LENGTH_SHORT).show();
			return;
		}
		this.context = context;
		this.aid = aid;
		
		setContentView(R.layout.comment_dialog);
		setTitle("用户登陆");
		
		Button login_button = (Button)findViewById(R.id.comment_confirm);
		Button cancel_button = (Button)findViewById(R.id.comment_cancel);
		
		login_button.setOnClickListener(this);
		cancel_button.setOnClickListener(this);
		Log.v("login", "drawed dialog");
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.comment_confirm: doComment(); break;
		case R.id.comment_cancel: cancel(); break;
		}
		
	}
	
	public void doComment(){
		EditText cc = (EditText)findViewById(R.id.comment_text);
		final String ccs = cc.getText().toString();
		if(!UserSession.isLogin()){
			Log.e("commentDialog", "用户没有登陆");
			Toast.makeText(context, "您没有登陆", Toast.LENGTH_SHORT).show();
			cancel();
			return;
		}

		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("action", "send"));
        nvps.add(new BasicNameValuePair("aid", aid));
        nvps.add(new BasicNameValuePair("isconfirm", "yes"));
        nvps.add(new BasicNameValuePair("msg", ccs));
        nvps.add(new BasicNameValuePair("comtype", "comments"));
        nvps.add(new BasicNameValuePair("username", UserSession.getUserName()));
        
		try{
			HttpRequest hr = new HttpRequest();
			String re = hr.postData(url, nvps);
			if (re.indexOf("成功发表评论")>=0){
				Log.v("send_comment","发表成功");
				Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
			}else{
				Log.d("send_comment", re);
				Toast.makeText(context, "评论失败", Toast.LENGTH_SHORT).show();
			}
		}catch(Exception e){
			Log.e("send_comment",e.toString());
			Toast.makeText(context, "提交出错", Toast.LENGTH_SHORT).show();
			cancel();
			return;
		}
		dismiss();
		
	}

}
