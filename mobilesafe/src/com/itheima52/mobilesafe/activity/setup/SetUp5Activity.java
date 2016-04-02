
package com.itheima52.mobilesafe.activity.setup;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.receiver.AdminReceiver;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SetUp5Activity extends BaseSetupActivity{

	TextView tv_scuritynumber,tv_enter_setup1,tv_back;
	ImageView iv_isprotected;
	private DevicePolicyManager mDPM;
	private ComponentName mDeviceAdminSample;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_5);
		
		mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mDeviceAdminSample = new ComponentName(this,
				AdminReceiver.class);// 设备管理组件
		
		String number=spf.getString("safephonenumber", "");
		
		boolean isProtected=spf.getBoolean("isProtected",true);
		
		tv_scuritynumber=(TextView) this.findViewById(R.id.tv_scuritynumber);
		tv_scuritynumber.setText(number);
		
		iv_isprotected=(ImageView) this.findViewById(R.id.iv_isprotected);
		if(isProtected){
			iv_isprotected.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lock));
			if(!mDPM.isAdminActive(mDeviceAdminSample)){
				activeAdmin();
			}
		}else{
			iv_isprotected.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.unlock));
		}
		tv_enter_setup1=(TextView) this.findViewById(R.id.tv_enter_setup1);
		tv_enter_setup1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SetUp5Activity.this,SetUp1Activity.class));
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
				SetUp5Activity.this.finish();
			}
		});
		tv_back=(TextView) this.findViewById(R.id.tv_back);
		tv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SetUp5Activity.this.finish();
			}
		});
	}
	private void activeAdmin() {
		Intent activeIntent = new Intent(
				DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		activeIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
				mDeviceAdminSample);
		activeIntent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
				"点击激活完成保护");
		startActivity(activeIntent);
	}
	@Override
	public void showPrevious() {
		// TODO Auto-generated method stub
		startActivity(new Intent(SetUp5Activity.this, SetUp4Activity.class));
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);
		this.finish();
	}

	@Override
	public void showNext() {
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		SetUp5Activity.this.finish();
	}
}
