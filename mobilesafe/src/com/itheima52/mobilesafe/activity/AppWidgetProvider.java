package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.utils.Toasts;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {
	/**
	 * 1.在清单文件配置元数据
	 * 2.配置xml
	 * 3.配置一个广播接收着
	 * 4.实现一个小部件
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		Toasts.show(context, "receiver");
	}
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
}
