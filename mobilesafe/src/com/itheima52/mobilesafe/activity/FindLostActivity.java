package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.activity.setup.SetUp1Activity;
import com.itheima52.mobilesafe.activity.setup.SetUp5Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class FindLostActivity extends Activity{
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		boolean configed=sharedPreferences.getBoolean("isProtected", false);
		if(configed){
			startActivity(new Intent(FindLostActivity.this,SetUp5Activity.class));
			finish();
		}else{
			//跳转向导页面
			startActivity(new Intent(FindLostActivity.this,SetUp1Activity.class));
			finish();
		}
		
		
	}
}
