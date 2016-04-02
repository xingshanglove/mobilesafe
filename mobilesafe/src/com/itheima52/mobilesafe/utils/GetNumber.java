package com.itheima52.mobilesafe.utils;

import java.util.ArrayList;
import java.util.List;

import com.itheima52.mobilesafe.entity.PhoneInfo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class GetNumber {

	public static List<PhoneInfo> lists;

	public static List<PhoneInfo> getNumber(Context context) {
		lists = new ArrayList<PhoneInfo>();
		Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI,
				null, null, null, null);
		String phoneNumber;
		String phoneName;
		while (cursor.moveToNext()) {
			phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
			phoneName = cursor.getString(cursor
					.getColumnIndex(Phone.DISPLAY_NAME));
			PhoneInfo phoneInfo = new PhoneInfo(phoneName, phoneNumber);
			lists.add(phoneInfo);
		}
		cursor.close();
		return lists;
	}

	public static List<PhoneInfo> readContact(Context context) {
		// 首先,从raw_contacts中读取联系人的id("contact_id")
		// 其次, 根据contact_id从data表中查询出相应的电话号码和联系人名称
		// 然后,根据mimetype来区分哪个是联系人,哪个是电话号码
		lists = new ArrayList<PhoneInfo>();
		Uri rawContactsUri = Uri
				.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");

		// 从raw_contacts中读取联系人的id("contact_id")
		Cursor rawContactsCursor = context.getContentResolver()
				.query(rawContactsUri, new String[] { "contact_id" }, null,
						null, null);
		if (rawContactsCursor != null) {
			while (rawContactsCursor.moveToNext()) {
				PhoneInfo info = new PhoneInfo();
				String contactId = rawContactsCursor.getString(0);
				// System.out.println(contactId);

				// 根据contact_id从data表中查询出相应的电话号码和联系人名称, 实际上查询的是视图view_data
				Cursor dataCursor = context.getContentResolver().query(dataUri,
						new String[] { "data1", "mimetype" }, "contact_id=?",
						new String[] { contactId }, null);

				if (dataCursor != null) {
					while (dataCursor.moveToNext()) {

						String data1 = dataCursor.getString(0);
						String mimetype = dataCursor.getString(1);
						System.out.println(contactId + ";" + data1 + ";"
								+ mimetype);
						if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
							info.setNumber(data1);
						} else if ("vnd.android.cursor.item/name"
								.equals(mimetype)) {
							info.setName(data1);
						}
					}
					dataCursor.close();
				}
				lists.add(info);
			}
			rawContactsCursor.close();
		}
		return lists;
	}

}
