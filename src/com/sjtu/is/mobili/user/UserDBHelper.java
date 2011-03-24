package com.sjtu.is.mobili.user;

import com.sjtu.is.mobili.db.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class UserDBHelper extends DBHelper {

	private String sql = "create table if not exists Accounts"+
	"(id int primary key,username varchar,password varchar)";
	
	public UserDBHelper(Context context, String name) {
		super(context, name);
	}

	public UserDBHelper(Context context) {
		super(context);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(sql);
	}

}
