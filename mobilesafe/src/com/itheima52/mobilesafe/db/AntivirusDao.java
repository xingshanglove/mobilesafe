package com.itheima52.mobilesafe.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AntivirusDao {
	private static final String PATH = "data/data/com.itheima52.mobilesafe/files/antivirus.db";// ע��,��·��������data/dataĿ¼���ļ�,�������ݿ���ʲ���
	public static String checkFileVirus(String md5){
		String desc=null;
		SQLiteDatabase database = SQLiteDatabase.openDatabase(PATH, null,
				SQLiteDatabase.OPEN_READONLY);
		String sql="select desc from datable where md5=?";
		Cursor query = database.query("datable", new String[]{"desc"},"md5=?", new String[]{md5}, null, null, null);
		while(query.moveToNext()){
			desc=query.getString(0);
		}
		return desc;
	}
}
