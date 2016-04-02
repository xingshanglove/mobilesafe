package com.itheima52.mobilesafe.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 归属地查询
 * 
 * @author root
 * 
 */
public class AddressDao {

	private static final String PATH = "data/data/com.itheima52.mobilesafe/files/address.db";// 注意,该路径必须是data/data目录的文件,否则数据库访问不到

	public static String getAddress(String number) {
		String address = "未知号码";
		SQLiteDatabase database = SQLiteDatabase.openDatabase(PATH, null,
				SQLiteDatabase.OPEN_READONLY);
		// 匹配手机号
		if (number.matches("^1[3-8]\\d{9}$")) {
			Cursor rawQuery = database
					.rawQuery(
							"select location from data2 where id=(select outkey from data1 where id=?)",
							new String[] { number.substring(0, 7) });
			if (rawQuery.moveToNext()) {
				address = rawQuery.getString(0);
			}
			database.close();
		} else if (number.matches("^\\d+$")) {
			switch (number.length()) {
			case 3:
				address = "报警电话";
				break;
			case 4:
				address = "模拟器";
				break;
			case 5:
				address = "客服电话";
				break;
			case 7:
			case 8:
				address = "本地号码";
				break;
			default:
				if (number.startsWith("0") && number.length() > 10) {// 有可能是长途电话
					// 有些区号是4位,有些区号是3位(包括0)
					// 先查询4位区号
					Cursor cursor = database.rawQuery(
							"select location from data2 where area =?",
							new String[] { number.substring(1, 4) });
					if (cursor.moveToNext()) {
						address = cursor.getString(0);
					} else {
						cursor.close();
						// 查询3位区号
						cursor = database.rawQuery(
								"select location from data2 where area =?",
								new String[] { number.substring(1, 3) });
						if (cursor.moveToNext()) {
							address = cursor.getString(0);
						}
						cursor.close();
					}
				}
				break;
			}
		}
		return address;
	}
}
