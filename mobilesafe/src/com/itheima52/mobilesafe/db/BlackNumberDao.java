package com.itheima52.mobilesafe.db;

import java.util.ArrayList;
import java.util.List;

import com.itheima52.mobilesafe.entity.BlackNumberInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BlackNumberDao implements BlackNumberInterface {
	private BlackNumberOpenHelper openHelper;
	private SQLiteDatabase db;
	private String table = "blacknumber";

	public BlackNumberDao(Context context) {
		openHelper = new BlackNumberOpenHelper(context);
		db = openHelper.getWritableDatabase();
	}
	@Override
	public boolean add(String number, String mode,String name) {
		ContentValues cv = new ContentValues();
		cv.put("number", number);
		cv.put("mode", mode);
		cv.put("name", name);
		long insert = db.insert(table, null, cv);
		if (insert == -1) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 通过电话号码删除
	 */
	@Override
	public boolean delete(String number) {
		int delete = db.delete(table, "number=?", new String[] { number });
		if (delete == 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean change(String number, String mode) {
		ContentValues cv = new ContentValues();
		cv.put("mode", mode);
		int update = db.update(table, cv, "number=?", new String[] { number });
		if (update != 0)
			return true;
		return false;
	}

	@Override
	public String query(String number) {
		String mode = "";
		Cursor query = db.query(table, new String[] { "mode" }, "number=?",
				new String[] { number }, null, null, null);
		if (query.moveToNext()) {
			mode = query.getString(0);
		}
		return mode;
	}

	@Override
	public List<BlackNumberInfo> findAll() {
		List<BlackNumberInfo> numbers = new ArrayList<BlackNumberInfo>();
		Cursor query = db.query(table, new String[] { "number","mode" ,"name"}, null, null,
				null, null, null);
		while (query.moveToNext()) {
			BlackNumberInfo info = new BlackNumberInfo();
			info.setNumber(query.getString(0));
			info.setMode(query.getString(1));
			info.setName(query.getString(2));
			numbers.add(info);
		}
		return numbers;
	}
	public void closeDb(){
		if(db!=null)
			db.close();
	}
}
