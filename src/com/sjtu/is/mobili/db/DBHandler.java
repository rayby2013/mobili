package com.sjtu.is.mobili.db;


import com.sjtu.is.mobili.db.DBHelper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHandler {

	private DBHelper dbHelper = null;
	private SQLiteDatabase db = null;
	private static String db_name = "mobili_db"; 

	public DBHandler(DBHelper dbh,
					 String db_name)
	{
		DBHandler.db_name = db_name;
		dbHelper = dbh;
		OpenDb();
	}


	public DBHandler(DBHelper dbh)
	{
		dbHelper = dbh;
		OpenDb();
	}

	/**
     * 执行SQL命令 
     */
	public void execSQL(String sql){
		db = dbHelper.getWritableDatabase();
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			Log.d("err", e.toString());
		}
	}


	/**
     * 打开数据库
     */
	public void OpenDb(){
		db = dbHelper.getWritableDatabase();
	}

	/**
     * 关闭数据库
     */
	public void CloseDb(){
		dbHelper.close();
	}
	
	public static String getDb_name() {
		return db_name;
	}
	
	protected void onDestroy() {
		if(db!=null){
			db.close();
		}
		if(dbHelper!=null){
			dbHelper.close();
		}
	}

}
