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
		//��ȡ���ֻ����ص�����
		long mobileRxBytes=TrafficStats.getMobileRxBytes();
		//��ȡ���ֻ��ϴ�������
		long mobileTxBytes = TrafficStats.getMobileTxBytes();
		System.out.println(mobileRxBytes+"/"+mobileTxBytes);
	}
}
