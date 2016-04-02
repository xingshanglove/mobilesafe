package com.itheima52.mobilesafe.entity;

import java.util.ArrayList;
import java.util.List;

import com.itheima52.mobilesafe.R;

import android.R.integer;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;
import android.text.format.Formatter;


public class TaskInfoParser {

	public static List<TaskInfo> getTaskInfos(Context context) {

		PackageManager packageManager = context.getPackageManager();

		List<TaskInfo> TaskInfos = new ArrayList<TaskInfo>();

		// ��ȡ�����̹�����
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(context.ACTIVITY_SERVICE);
		// ��ȡ���ֻ������������еĽ���
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();

		for (RunningAppProcessInfo runningAppProcessInfo : appProcesses) {

			TaskInfo taskInfo = new TaskInfo();

			// ��ȡ�����̵�����
			String processName = runningAppProcessInfo.processName;

			taskInfo.setPackageName(processName);

			try {
				// ��ȡ���ڴ������Ϣ
				/**
				 * �������һ��ֻ��һ������
				 */
				MemoryInfo[] memoryInfo = activityManager
						.getProcessMemoryInfo(new int[]{runningAppProcessInfo.pid});
				// DirtyŪ��
				// ��ȡ���ܹ�Ū������ڴ�(��ǰӦ�ó���ռ�ö����ڴ�)
				int totalPrivateDirty = memoryInfo[0].getTotalPrivateDirty() * 1024;
				
				
//				System.out.println("==========="+totalPrivateDirty);

				taskInfo.setMemorySize(totalPrivateDirty);

				PackageInfo packageInfo = packageManager.getPackageInfo(
						processName, 0);

				// /��ȡ��ͼƬ
				Drawable icon = packageInfo.applicationInfo
						.loadIcon(packageManager);

				taskInfo.setIcon(icon);
				// ��ȡ��Ӧ�õ�����
				String appName = packageInfo.applicationInfo.loadLabel(
						packageManager).toString();

				taskInfo.setAppName(appName);
				
				System.out.println("-------------------");
				System.out.println("processName="+processName);
				System.out.println("appName="+appName);
				//��ȡ����ǰӦ�ó���ı��
				//packageInfo.applicationInfo.flags ����д�Ĵ�
				//ApplicationInfo.FLAG_SYSTEM��ʾ��ʦ�ĸþ���
				int flags = packageInfo.applicationInfo.flags;
				//ApplicationInfo.FLAG_SYSTEM ��ʾϵͳӦ�ó���
				if((flags & ApplicationInfo.FLAG_SYSTEM) != 0 ){
					//ϵͳӦ��
					taskInfo.setUserApp(false);
				}else{
//					/�û�Ӧ��
					taskInfo.setUserApp(true);
					
				}
				
				
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// ϵͳ���Ŀ�������Щϵͳû��ͼ�ꡣ�����һ��Ĭ�ϵ�ͼ��

				taskInfo.setAppName(processName);
				taskInfo.setIcon(context.getResources().getDrawable(
						R.drawable.ic_launcher));
			}
			
			TaskInfos.add(taskInfo);
		}

		return TaskInfos;
	}

}
