package com.itheima52.mobilesafe.service;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.Time;
/**
 * ============================================================
 * 
 * �� Ȩ �� �������Ա�������� ��Ȩ���� (c) 2015
 * 
 * �� �� : ��ΰ��
 * 
 * �� �� �� 1.0
 * 
 * �������� �� 2015-3-4 ����11:35:09
 * 
 * �� �� ��
 * 
 *       �Զ��������
 * �޶���ʷ ��
 * 
 * ============================================================
 **/
public class KillProcessService extends Service {

	private LockScreenReceiver receiver;


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class LockScreenReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			//��ȡ�����̹�����
			ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
			//��ȡ���ֻ����������������еĽ���
			List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
			for (RunningAppProcessInfo runningAppProcessInfo : appProcesses) {
				activityManager.killBackgroundProcesses(runningAppProcessInfo.processName);
			}
			
			
		}
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		receiver = new LockScreenReceiver();
		//�����Ĺ�����
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		//ע��һ�������Ĺ㲥
		registerReceiver(receiver, filter);
		
//		Timer timer = new Timer();
//		
//		TimerTask task = new TimerTask() {
//			
//			@Override
//			public void run() {
//				// д���ǵ�ҵ���߼�
//				System.out.println("�ұ�������");
//			}
//		};
//		//���ж�ʱ����
//		/**
//		 * ��һ������  ��ʾ���Ǹ�����е���
//		 * 
//		 * �ڶ���������ʾʱ��
//		 */
//		timer.schedule(task, 0,1000);
		
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//��Ӧ�ó����Ƴ���ʱ����Ҫ�ѹ㲥��ע���
		unregisterReceiver(receiver);
		//�ֶ�����
		receiver = null;
	}

}
