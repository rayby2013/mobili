package com.sjtu.is.mobili.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;

	private String sql = "create table if not exists TestUsers"+
	"(id int primary key,name varchar,sex varchar)";

	
	public DBHelper(Context context,
					String name,
					CursorFactory factory,
					int version) 
	{
		super(context, name, factory, version);
	}

	public DBHelper(Context context,
					String name,
					int version) 
	{
		this(context,name,null,version);
	}

	public DBHelper(Context context,String name)
	{
		this(context,name,VERSION);
	}
	
	public DBHelper(Context context){
		this(context, DBHandler.getDb_name(), null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		
	} 
}