package com.itheima52.mobilesafe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;

import android.R.integer;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class SystemInfoUtils {
	/**
	 * �ж�һ�������Ƿ�������״̬
	 * 
	 * @param context
	 *            ������
	 * @return
	 */
	public static boolean isServiceRunning(Context context, String className) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> infos = am.getRunningServices(200);
		for (RunningServiceInfo info : infos) {
			String serviceClassName = info.service.getClassName();
			if (className.equals(serviceClassName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ���ؽ��̵��ܸ���
	 * 
	 * @param context
	 * @return
	 */
	public static int getProcessCount(Context context) {
		// �õ����̹�����
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(context.ACTIVITY_SERVICE);
		// ��ȡ����ǰ�ֻ������������еĽ���
		List<RunningAppProcessInfo> runningAppProcesses = activityManager
				.getRunningAppProcesses();

		// ��ȡ�ֻ�����һ���ж��ٸ�����
		return runningAppProcesses.size();
	}

	public static long getAvailMem(Context context) {
		// �õ����̹�����
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(context.ACTIVITY_SERVICE);
		MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();

		// ��ȡ���ڴ�Ļ�����Ϣ
		activityManager.getMemoryInfo(memoryInfo);
		// ��ȡ��ʣ���ڴ�
		return memoryInfo.availMem;
	}

	public static long getTotalMem(Context context) {
		// ��ȡ�����ڴ�
		/*
		 * ����ط�����ֱ���ܵ��Ͱ汾���ֻ����� MemTotal: 344740 kB "/proc/meminfo"
		 */
		try {
			// /proc/meminfo �����ļ���·��
			FileInputStream fis = new FileInputStream(new File("/proc/meminfo"));

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fis));

			String readLine = reader.readLine();

			StringBuffer sb = new StringBuffer();

			for (char c : readLine.toCharArray()) {
				if (c >= '0' && c <= '9') {
					sb.append(c);
				}
			}
			return Long.parseLong(sb.toString()) * 1024;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

}
