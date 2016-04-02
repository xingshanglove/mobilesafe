package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.R;

import android.app.Activity;
import android.net.TrafficStats;
import android.os.Bundle;

public class TrafficManagerActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic);
		//获取到手机下载的流量
		long mobileRxBytes=TrafficStats.getMobileRxBytes();
		//获取到手机上传的流量
		long mobileTxBytes = TrafficStats.getMobileTxBytes();
		System.out.println(mobileRxBytes+"/"+mobileTxBytes);
	}
}
