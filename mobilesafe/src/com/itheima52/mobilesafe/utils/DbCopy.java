package com.itheima52.mobilesafe.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class DbCopy {
	/**
	 * 拷贝数据库
	 * @param dbName
	 */
	public static  void copyDb(Context context,String dbName) {
		// getFilesDir 获取data/data/包名 目录
		File file = new File(context.getFilesDir(), dbName);
		if(file.exists()){
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		InputStream open =null;
		FileOutputStream out=null;
		try {
			open= context.getAssets().open(dbName);
			out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = open.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				open.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
