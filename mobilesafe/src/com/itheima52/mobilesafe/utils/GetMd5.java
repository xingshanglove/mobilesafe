package com.itheima52.mobilesafe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetMd5 {
	public static String getMd5(String info){
		StringBuffer sb=new StringBuffer();
		try{
			MessageDigest digest=MessageDigest.getInstance("MD5");
			byte[] bytes=digest.digest(info.getBytes());
			for(byte b:bytes){
				int i=b&0xff;
				String hexString=Integer.toHexString(i);
				if(hexString.length()<2)
					hexString="0"+hexString;
				sb.append(hexString);
			}
			return sb.toString();
		}catch(Exception e){
			
		}
		return "";
	}

	public static String getFileMd5(String sourceDir) {
		File file=new File(sourceDir);
		StringBuffer sb=new StringBuffer();
		try {
			FileInputStream fis=new FileInputStream(file);
			byte []buffer=new byte[1024];
			
			int len=-1;
			MessageDigest digest=MessageDigest.getInstance("md5");
			while((len=fis.read(buffer))!=-1){
				digest.update(buffer, 0, len);
			}
			byte[] result=digest.digest();
			for(byte b:result){
				int i=b&0xff;
				String hexString=Integer.toHexString(i);
				if(hexString.length()<2)
					hexString="0"+hexString;
				sb.append(hexString);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
