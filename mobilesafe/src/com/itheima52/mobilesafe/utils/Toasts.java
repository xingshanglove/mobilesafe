package com.itheima52.mobilesafe.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class Toasts {
	public static void show(Context context,int i){
		show(context,i+"");
	}
	public static void show(Context context,String ss){
		Toast.makeText(context, ss, 1).show();
	}
	public static void Uishow(Context context,String ss){
		Looper.prepare();
		Toast.makeText(context, ss, 1).show();
		Looper.loop();
	}
}
