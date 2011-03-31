package com.sjtu.is.mobili.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sjtu.is.mobili.user.UserData;


public class DBHelper extends OrmLiteSqliteOpenHelper {

	public DBHelper(Context context, String databaseName,
			CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
		
	}

	private static final String DATABASE_NAME = "mobili.db";
	private static final int DATABASE_VERSION = 6;

	// the DAO object we use to access the SimpleData table
	private Dao<UserData, String> userDao = null;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DBHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, UserData.class);
		} catch (SQLException e) {
			Log.e(DBHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DBHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, UserData.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DBHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}


	public Dao<UserData, String> getUserDataDao() throws SQLException {
		if (userDao == null) {
			userDao = getDao(UserData.class);
		}
		return userDao;
	}

	@Override
	public void close() {
		super.close();
		userDao = null;
	}
}
