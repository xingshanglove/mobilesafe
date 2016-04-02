package com.itheima52.mobilesafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * ����״̬������
 * @author root
 *
 */
public class ServiceStatusUtils {
	/**
	 * �������Ƿ���������
	 * @return
	 */
	public static boolean isServiceRunning(Context context,String serviceName){
		ActivityManager am=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServices = am.getRunningServices(100);
		for(RunningServiceInfo info :runningServices){
			String className = info.service.getClassName();
			if(className.equals(serviceName)){
				return true;
			}
		}
		return false;
	}
}
