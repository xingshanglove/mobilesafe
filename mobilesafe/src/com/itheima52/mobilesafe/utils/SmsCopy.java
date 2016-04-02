package com.itheima52.mobilesafe.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xmlpull.v1.XmlSerializer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Xml;

/**
 * 短信备份工具类
 * 
 * @author root
 * 
 */
public class SmsCopy {
	public static boolean copy(Context context) {
		/**
		 * 是否有SD卡
		 */
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return false;
		}

		File file = new File(Environment.getExternalStorageDirectory(),
				"backup.xml");
		try {
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
				file.createNewFile();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileOutputStream os = null;
		XmlSerializer serializer;
		try {
			os = new FileOutputStream(file);
			/**
			 * android 里面解析xml都是pull解析
			 */
			serializer = Xml.newSerializer();
			serializer.setOutput(os, "utf-8");
			// 是否是独立文件
			serializer.startDocument("utf-8", true);
			// 开始节点
			serializer.startTag(null, "smss");
			/**
			 * 短信的读取
			 */
			Uri uri = Uri.parse("content://sms/");
			/**
			 * type 1接收短信 2.发送短信
			 */
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { "address", "date", "type", "body" }, null,
					null, null);
			while (cursor.moveToNext()) {
				serializer.startTag(null, "sms");
				// address
				serializer.startTag(null, "address");
				serializer.text(cursor.getString(0));
				serializer.endTag(null, "address");
				// date
				serializer.startTag(null, "date");
				serializer.text(cursor.getString(1));
				serializer.endTag(null, "date");
				// type
				serializer.startTag(null, "type");
				serializer.text(cursor.getString(2));
				serializer.endTag(null, "type");
				// body
				serializer.startTag(null, "body");
				serializer.text(Crypto.encrypt("123",cursor.getString(3)));
				serializer.endTag(null, "body");
				serializer.endTag(null, "sms");

			}
			cursor.close();
			serializer.endTag(null, "smss");
			serializer.endDocument();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
