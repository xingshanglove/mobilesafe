package com.itheima52.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	/**
	 * 将输入流读取成String
	 * @param inputStream
	 * @return
	 * @throws IOException 
	 */
	public static String readFromString(InputStream inputStream) throws IOException{
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		int len=0;
		byte[] buffer=new byte[1024];
		while((len=inputStream.read(buffer))!=-1){
			out.write(buffer, 0, len);
		}
		String result=out.toString();
		inputStream.close();
		out.close();
		return result;
	}
}
