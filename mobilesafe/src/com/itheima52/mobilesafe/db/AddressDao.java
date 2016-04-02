package com.itheima52.mobilesafe.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * �����ز�ѯ
 * 
 * @author root
 * 
 */
public class AddressDao {

	private static final String PATH = "data/data/com.itheima52.mobilesafe/files/address.db";// ע��,��·��������data/dataĿ¼���ļ�,�������ݿ���ʲ���

	public static String getAddress(String number) {
		String address = "δ֪����";
		SQLiteDatabase database = SQLiteDatabase.openDatabase(PATH, null,
				SQLiteDatabase.OPEN_READONLY);
		// ƥ���ֻ���
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
				address = "�����绰";
				break;
			case 4:
				address = "ģ����";
				break;
			case 5:
				address = "�ͷ��绰";
				break;
			case 7:
			case 8:
				address = "���غ���";
				break;
			default:
				if (number.startsWith("0") && number.length() > 10) {// �п����ǳ�;�绰
					// ��Щ������4λ,��Щ������3λ(����0)
					// �Ȳ�ѯ4λ����
					Cursor cursor = database.rawQuery(
							"select location from data2 where area =?",
							new String[] { number.substring(1, 4) });
					if (cursor.moveToNext()) {
						address = cursor.getString(0);
					} else {
						cursor.close();
						// ��ѯ3λ����
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
