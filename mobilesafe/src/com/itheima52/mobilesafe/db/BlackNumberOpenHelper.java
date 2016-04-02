package com.itheima52.mobilesafe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ºÚÃûµ¥Dao
 * 
 * @author root
 * 
 */
public class BlackNumberOpenHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;
	private static final String daName = "safe.db";

	public BlackNumberOpenHelper(Context context) {
		super(context, daName, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table blacknumber (number varchar(20) primary key,mode varchar(2),name varchar(16))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
