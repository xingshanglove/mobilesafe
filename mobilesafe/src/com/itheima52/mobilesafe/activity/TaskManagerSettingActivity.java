package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.service.KillProcessService;
import com.itheima52.mobilesafe.utils.SystemInfoUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-4 上午10:47:25
 * 
 * 描 述 ：
 * 
 *       任务管理器的设置界面
 * 修订历史 ：
 * 
 * ============================================================
 **/
public class TaskManagerSettingActivity extends Activity {
	
	private SharedPreferences sp;
	private CheckBox cb_status_kill_process;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("config", MODE_PRIVATE);
		initUI();
		
	}

	private void initUI() {
		setContentView(R.layout.activity_task_manager_setting);
		CheckBox cb_status = (CheckBox) findViewById(R.id.cb_status);
		
		//设置是否选中
		cb_status.setChecked(sp.getBoolean("is_show_system", false));
		
		cb_status.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				sp.edit().putBoolean("is_show_system", isChecked).commit();
			}
		});
		
		//定时清理进程
		
		cb_status_kill_process = (CheckBox) findViewById(R.id.cb_status_kill_process);
		
		final Intent intent = new Intent(this,KillProcessService.class);
		
		cb_status_kill_process.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					startService(intent);
				}else{
					stopService(intent);
				}
			}
		});
	
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(SystemInfoUtils.isServiceRunning(TaskManagerSettingActivity.this, "com.itheima.mobileguard.services.KillProcessService")){
			cb_status_kill_process.setChecked(true);
		}else{
			cb_status_kill_process.setChecked(false);
		}
	}

}
