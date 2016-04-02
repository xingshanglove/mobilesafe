package com.itheima52.mobilesafe.activity.setup;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.utils.Toasts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SetUp2Activity extends BaseSetupActivity {

	Button btn_next;
	Button btn_previous;
	TextView tv_tel;
	EditText et_tel;
	CheckBox chk_tel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_2);
		initView();
	}

	private void initView() {
		btn_next = (Button) this.findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showNext();
			}
		});
		btn_previous = (Button) this.findViewById(R.id.btn_previous);
		btn_previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPrevious();
			}
		});

		tv_tel = (TextView) this.findViewById(R.id.tv_tel);
		et_tel = (EditText) this.findViewById(R.id.et_tel);
		chk_tel = (CheckBox) this.findViewById(R.id.chk_tel);

		String simSerialNumber=spf.getString("simNumber", null);
		if(simSerialNumber!=null){
			chk_tel.setChecked(true);
			et_tel.setText("SIM卡成功绑定");
			tv_tel.setText("点击解除绑定");
		}
		tv_tel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (chk_tel.isChecked()) {
					chk_tel.setChecked(false);
					spf.edit().remove("simNumber").commit();
					et_tel.setText("SIM卡没有绑定");
					tv_tel.setText("点击绑定SIM卡");
				} else {
					TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					String simSerialNumber = tm.getSimSerialNumber();
					spf.edit().putString("simNumber", simSerialNumber).commit();
					chk_tel.setChecked(true);
					et_tel.setText("SIM卡成功绑定");
					tv_tel.setText("点击解除绑定");
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		showPrevious();
	}

	@Override
	public void showPrevious() {
		startActivity(new Intent(SetUp2Activity.this, SetUp1Activity.class));
		this.finish();
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);
	}

	@Override
	public void showNext() {
		if(!chk_tel.isChecked()){
			Toasts.show(SetUp2Activity.this,"必须绑定SIM卡才能下一步");
			return ;
		}
		startActivity(new Intent(SetUp2Activity.this, SetUp3Activity.class));
		this.finish();
		overridePendingTransition(R.anim.tran_in,
				R.anim.tran_out);
	}
}
