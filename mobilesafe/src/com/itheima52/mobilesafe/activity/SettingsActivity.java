package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.service.AddressService;
import com.itheima52.mobilesafe.service.BlackService;
import com.itheima52.mobilesafe.service.WatchDogService;
import com.itheima52.mobilesafe.utils.ServiceStatusUtils;
import com.itheima52.mobilesafe.view.SettingClickView;
import com.itheima52.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SettingsActivity extends Activity {
	private com.itheima52.mobilesafe.view.SettingItemView auto_refresh;
	private com.itheima52.mobilesafe.view.SettingItemView siv_address;
	private com.itheima52.mobilesafe.view.SettingItemView siv_safecall;
	com.itheima52.mobilesafe.view.SettingItemView sv_watch_dog;
	private SettingClickView scvAddressStyle;// 修改风格
	private SettingClickView scvAddressLocation;// 修改归属地位置

	private ImageView iv_back;

	Intent watchDogIntent;
	SharedPreferences sharedPreferences;
	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
		initAudoView();
		initAddressView();
		initAddressStyle();
		initAddressLocation();
		initSafeCall();
		initWatchDog();
	}

	private void initSafeCall() {
		siv_safecall = (SettingItemView) this.findViewById(R.id.siv_safecall);
		boolean isSafeCall = ServiceStatusUtils.isServiceRunning(this,
				"com.itheima52.mobilesafe.service.BlackService");
		siv_safecall.setChecked(isSafeCall);
		siv_safecall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (siv_safecall.isChecked()) {
					siv_safecall.setChecked(false);
					stopService(new Intent(SettingsActivity.this,
							BlackService.class));
				} else {
					siv_safecall.setChecked(true);
					startService(new Intent(SettingsActivity.this,
							BlackService.class));
				}
			}
		});
	}

	private void initAddressView() {
		siv_address = (SettingItemView) this.findViewById(R.id.siv_address);
		boolean isRun = ServiceStatusUtils.isServiceRunning(this,
				"com.itheima52.mobilesafe.service.AddressService");
		siv_address.setChecked(isRun);
		siv_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (siv_address.isChecked()) {
					siv_address.setChecked(false);
					stopService(new Intent(SettingsActivity.this,
							AddressService.class));
				} else {
					siv_address.setChecked(true);
					startService(new Intent(SettingsActivity.this,
							AddressService.class));
				}
			}
		});
	}
	/**
	 * 看门狗
	 */
	public void initWatchDog(){
		sv_watch_dog = (SettingItemView) this.findViewById(R.id.sv_watch_dog);
		watchDogIntent = new Intent(this, WatchDogService.class);
		sv_watch_dog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sv_watch_dog.isChecked()) {
					sv_watch_dog.setChecked(false);
					// 停止拦截服务
					stopService(watchDogIntent);
				} else {
					sv_watch_dog.setChecked(true);
					// 开启拦截服务
					startService(watchDogIntent);
				}
			}
		});
	}
	

	private void initAudoView() {
		iv_back = (ImageView) this.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingsActivity.this.finish();
			}
		});
		auto_refresh = (SettingItemView) this.findViewById(R.id.auto_refresh);
		boolean autoRefresh = sharedPreferences
				.getBoolean("auto_refresh", true);
		if (autoRefresh) {
			auto_refresh.setChecked(true);
		} else {
			auto_refresh.setChecked(false);
		}
		auto_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editor = sharedPreferences.edit();
				// 判断当前勾选状态
				if (auto_refresh.isChecked()) {
					auto_refresh.setChecked(false);
					editor.putBoolean("auto_refresh", false);
				} else {
					auto_refresh.setChecked(true);
					editor.putBoolean("auto_refresh", true);
				}
				editor.commit();
			}
		});
	}

	final String[] items = new String[] { "半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿" };

	/**
	 * 修改提示框显示风格
	 */
	private void initAddressStyle() {
		scvAddressStyle = (SettingClickView) findViewById(R.id.scv_address_style);

		scvAddressStyle.setTitle("归属地提示框风格");

		int style = sharedPreferences.getInt("address_style", 0);// 读取保存的style
		scvAddressStyle.setDesc(items[style]);

		scvAddressStyle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSingleChooseDailog();
			}
		});
	}

	/**
	 * 弹出选择风格的单选框
	 */
	protected void showSingleChooseDailog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("归属地提示框风格");

		int style = sharedPreferences.getInt("address_style", 0);// 读取保存的style

		builder.setSingleChoiceItems(items, style,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						sharedPreferences.edit().putInt("address_style", which)
								.commit();// 保存选择的风格
						dialog.dismiss();// 让dialog消失

						scvAddressStyle.setDesc(items[which]);// 更新组合控件的描述信息
					}
				});

		builder.setNegativeButton("取消", null);
		builder.show();
	}

	/**
	 * 修改归属地显示位置
	 */
	private void initAddressLocation() {
		scvAddressLocation = (SettingClickView) findViewById(R.id.scv_address_location);
		scvAddressLocation.setTitle("归属地提示框显示位置");
		scvAddressLocation.setDesc("设置归属地提示框的显示位置");
		scvAddressLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SettingsActivity.this,
						DragViewActivity.class));
			}
		});
	}
}
