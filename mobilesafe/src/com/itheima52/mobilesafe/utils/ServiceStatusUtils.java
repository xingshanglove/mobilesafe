package com.itheima52.mobilesafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

/**
 * 服务状态工具类
 * @author root
 *
 */
public class ServiceStatusUtils {
	/**
	 * 检测服务是否正在运行
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
