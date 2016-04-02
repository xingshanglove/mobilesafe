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
		// ����,��raw_contacts�ж�ȡ��ϵ�˵�id("contact_id")
		// ���, ����contact_id��data���в�ѯ����Ӧ�ĵ绰�������ϵ������
		// Ȼ��,����mimetype�������ĸ�����ϵ��,�ĸ��ǵ绰����
		lists = new ArrayList<PhoneInfo>();
		Uri rawContactsUri = Uri
				.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");

		// ��raw_contacts�ж�ȡ��ϵ�˵�id("contact_id")
		Cursor rawContactsCursor = context.getContentResolver()
				.query(rawContactsUri, new String[] { "contact_id" }, null,
						null, null);
		if (rawContactsCursor != null) {
			while (rawContactsCursor.moveToNext()) {
				PhoneInfo info = new PhoneInfo();
				String contactId = rawContactsCursor.getString(0);
				// System.out.println(contactId);

				// ����contact_id��data���в�ѯ����Ӧ�ĵ绰�������ϵ������, ʵ���ϲ�ѯ������ͼview_data
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
