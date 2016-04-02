package com.itheima52.mobilesafe.receiver;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.db.AddressDao;
import com.itheima52.mobilesafe.utils.Toasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
/**
 * 监听去电的广播接收者
 * @author root
 *
 */
public class OutCallReceiver extends BroadcastReceiver{
	Context context;
	private WindowManager mWM;
	private View view;
	private SharedPreferences mPref;
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context=context;
		String number=getResultData();
		String address = AddressDao.getAddress(number);
		mPref = context.getSharedPreferences("config", context.MODE_PRIVATE);
		if(number.equals("18149184677"))
			address="老公来电";
		showToast( address);
	}
	/**
	 * 自定义浮窗
	 */
	public void showToast(String text){
		mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_TOAST;
		params.setTitle("Toast");

		view = View.inflate(context, R.layout.toast_address, null);

		int[] bgs = new int[] { R.drawable.call_locate_white,
				R.drawable.call_locate_orange, R.drawable.call_locate_blue,
				R.drawable.call_locate_gray, R.drawable.call_locate_green };
		int style = mPref.getInt("address_style", 0);

		view.setBackgroundResource(bgs[style]);// 根据存储的样式更新背景

		TextView tvText = (TextView) view.findViewById(R.id.tv_number);
		tvText.setText(text);

		mWM.addView(view, params);// 将view添加在屏幕上(Window)
	}
}
