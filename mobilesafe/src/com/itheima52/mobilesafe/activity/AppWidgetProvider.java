package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.utils.Toasts;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {
	/**
	 * 1.���嵥�ļ�����Ԫ����
	 * 2.����xml
	 * 3.����һ���㲥������
	 * 4.ʵ��һ��С����
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
