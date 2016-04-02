package com.itheima52.mobilesafe.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.android.internal.telephony.ITelephony;
import com.itheima52.mobilesafe.db.BlackNumberDao;
import com.itheima52.mobilesafe.entity.BlackNumberInfo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

public class BlackService extends Service {

	private TelephonyManager tm;
	private SharedPreferences spf;
	private MyListener listener;
	private BlackNumberDao dao;
	private SmsReceiver receiver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		listener = new MyListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		dao = new BlackNumberDao(this);

		receiver = new SmsReceiver();
		IntentFilter intentFilter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		intentFilter.setPriority(Integer.MAX_VALUE);
		registerReceiver(receiver, intentFilter);
	}

	class MyListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				boolean isRefuse = searchIsRefuse(incomingNumber);
				if (isRefuse) {
					/**
					 * 添加到拦截记录
					 */
						
					// 取消来电
					endCall();
					
					deleteCallLog(incomingNumber);
				}
				break;
			}
		}
	}
	private void endCall() {
		try {
			//通过类加载器加载
			Class<?> clazz = getClassLoader().loadClass("android.os.ServiceManager");
			Method method = clazz.getDeclaredMethod("getService",String.class);
			IBinder  iBinder = (IBinder) method.invoke(null,TELEPHONY_SERVICE);
		
			ITelephony telephony = ITelephony.Stub.asInterface(iBinder); 
			telephony.endCall();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void deleteCallLog(String icString){
		Uri uri=Uri.parse("content://call_log/calls");
		getContentResolver().delete(uri, "number=?", new String[]{icString});
	}
	
	private boolean searchIsRefuse(String incomingNumber) {
		String query = dao.query(incomingNumber);
		if (query.equals("0") || query.equals("1"))
			return true;
		return false;
	}

	class SmsReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			for (Object object : objects) {
				SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
				// 短信来源号码
				String originatingAddress = message.getOriginatingAddress();
				// 短信内容
				String messageBody = message.getMessageBody();
				String query = dao.query(originatingAddress);
				if (query.equals("0") || query.equals(2)) {
					/**
					 * 拦截
					 */
					abortBroadcast();
					/**
					 * 添加到拦截记录
					 */
				}
			}
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		unregisterReceiver(receiver);
		if(dao!=null)
			dao.closeDb();
	}
}
