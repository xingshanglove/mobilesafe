package com.itheima52.mobilesafe.receiver;

import java.io.IOException;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.service.LocationService;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.itheima52.mobilesafe.receiver.AdminReceiver;

/**
 * 短信拦截
 * 
 * @author root
 * 
 */
public class SmsReceiver extends BroadcastReceiver {

	private DevicePolicyManager mDPM;
	private ComponentName mDeviceAdminSample;
	private Context context;
	private SharedPreferences spf;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
		spf = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		boolean isProteced = spf.getBoolean("isProtected", false);
		/**
		 * 确定是否处于保护状态
		 */
		if (isProteced) {
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			for (Object object : objects) {
				SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
				// 短信来源号码
				String originatingAddress = message.getOriginatingAddress();
				// 短信内容
				String messageBody = message.getMessageBody();
				Log.v("----------->", originatingAddress + "/" + messageBody);
				if ("#*alarm*#".equals(messageBody)) {
					MediaPlayer player = MediaPlayer
							.create(context, R.raw.ylzs);
					player.setVolume(1f, 1f);
					player.setLooping(true);
					player.start();
					// 中断传递
					abortBroadcast();
				} else if ("#*location*#".equals(messageBody)) {
					// 启动service获取地址
					context.startService(new Intent(context,
							LocationService.class));
					// 从sharedpreference读取保存的地址
					SharedPreferences sp = context.getSharedPreferences(
							"config", Context.MODE_PRIVATE);
					String location = sp.getString("location", "");
					if (!TextUtils.isEmpty(location)) {
						// 将地址发送回安全号码
						String securityNumber = sp.getString("safephonenumber",
								"");
						if (!TextUtils.isEmpty(securityNumber)) {
							SmsManager manager = SmsManager.getDefault();
							manager.sendTextMessage(securityNumber, null,
									location, null, null);
						}
					}
					abortBroadcast();
				} else if ("#*wipedata*#".equals(messageBody)) {
					mDPM = (DevicePolicyManager) context
							.getSystemService(Context.DEVICE_POLICY_SERVICE);
					mDeviceAdminSample = new ComponentName(context,
							AdminReceiver.class);// 设备管理组件
					if (mDPM.isAdminActive(mDeviceAdminSample)) {
						mDPM.wipeData(0);// 清除数据,恢复出厂设置
					} else {
						activeAdmin();
					}
					abortBroadcast();
				} else if ("#*lockscreen*#".equals(messageBody)) {
					mDPM = (DevicePolicyManager) context
							.getSystemService(Context.DEVICE_POLICY_SERVICE);
					mDeviceAdminSample = new ComponentName(context,
							AdminReceiver.class);// 设备管理组件
					if (mDPM.isAdminActive(mDeviceAdminSample)) {
						mDPM.lockNow();
						mDPM.resetPassword("2222", 0);
					} else {
						activeAdmin();
					}
					abortBroadcast();
				}
			}
		}
	}

	public void activeAdmin() {
		Intent activeIntent = new Intent(
				DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		activeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activeIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
				mDeviceAdminSample);
		activeIntent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
				"开启手机防盗");
		context.startActivity(activeIntent);
	}
}
