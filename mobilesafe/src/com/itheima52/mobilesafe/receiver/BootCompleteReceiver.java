package com.itheima52.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
/**
 * 监听手机启动的广播
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
				//获取现在的信息
				TelephonyManager tm=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				String currentSim=tm.getSimSerialNumber();
				if(number.equals(currentSim)){
					//手机安全
					
				}else{
					//不安全,发送报警短信
					String securityNumber=sharedPreferences.getString("safephonenumber", "");
					SmsManager manager=SmsManager.getDefault();
					manager.sendTextMessage(securityNumber, null,"SIM car changed", null, null);
				}
			}
		}
		
		
	}

}
