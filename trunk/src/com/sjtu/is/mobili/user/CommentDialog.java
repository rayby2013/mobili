package com.sjtu.is.mobili.user;

import com.sjtu.is.mobili.R;
import com.sjtu.is.mobili.http.HttpRequest;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;
import java.net.URLEncoder;


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
		String data = "action=send&aid="+aid+
			"&isconfirm=yes&msg="+ URLEncoder.encode(ccs)+
			"&comtype=comments&username="+ URLEncoder.encode(UserSession.getUserName());
		try{
			HttpRequest hr = new HttpRequest();
			String re = hr.postData(data, new URL(url));
			if (re.indexOf("成功发表评论")>=0){
				Log.v("send_comment","发表成功");
				Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
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
