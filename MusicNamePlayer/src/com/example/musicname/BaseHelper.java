/**
 * 
 */
package com.example.musicname;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Title: BaseHelper.java
 * Description: 
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015-8-31 下午1:46:31 
 * @author liuwenxing
 * @version 1.0
 */
public class BaseHelper extends SQLiteOpenHelper {
	private static String name = "musicplayer";
	private static int version = 1;
	private static String creatTable = "CREATE TABLE IF NOT EXISTS music (musicid integer primary key autoincrement, musicname varchar(60), musicpath varchar(100),musicplayer varchar(100),musictime INTEGER)";
	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public BaseHelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
		
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(creatTable);
		Log.d("music","成功");
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
