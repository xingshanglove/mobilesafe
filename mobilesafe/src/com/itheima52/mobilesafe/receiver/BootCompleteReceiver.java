package com.itheima52.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
/**
 * �����ֻ������Ĺ㲥
 * @author root
 *
 */
public class BootCompleteReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		String number = sharedPreferences.getString("simNunber", null);
		boolean isProtected=sharedPreferences.getBoolean("isProtected", true);
		if(isProtected){
			if(!TextUtils.isEmpty(number)){
				//��ȡ���ڵ���Ϣ
				TelephonyManager tm=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				String currentSim=tm.getSimSerialNumber();
				if(number.equals(currentSim)){
					//�ֻ���ȫ
					
				}else{
					//����ȫ,���ͱ�������
					String securityNumber=sharedPreferences.getString("safephonenumber", "");
					SmsManager manager=SmsManager.getDefault();
					manager.sendTextMessage(securityNumber, null,"SIM car changed", null, null);
				}
			}
		}
		
		
	}

}
