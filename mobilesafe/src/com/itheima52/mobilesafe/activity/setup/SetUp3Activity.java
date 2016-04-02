package com.itheima52.mobilesafe.activity.setup;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.entity.PhoneInfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetUp3Activity extends BaseSetupActivity {

	private Button btn_next;
	private Button btn_previous;
	private EditText et_phonenumber;
	private Button btn_getphone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_3);
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

		String safePhoneNumber = spf.getString("safephonenumber", null);
		et_phonenumber = (EditText) this.findViewById(R.id.et_phonenumber);
		if (safePhoneNumber != null)
			et_phonenumber.setText(safePhoneNumber);
		btn_getphone = (Button) this.findViewById(R.id.btn_getphone);
		btn_getphone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(SetUp3Activity.this,
						ContactActivity.class), 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			PhoneInfo info = (PhoneInfo) data.getExtras().get("phone");
			et_phonenumber.setText(info.getNumber());
		}
	}

	@Override
	public void showPrevious() {
		String result = et_phonenumber.getText().toString();
		if (!TextUtils.isEmpty(result)) {
//			if (result.length() == 11) {
				spf.edit().putString("safephonenumber", result).commit();
//			}
		}
		startActivity(new Intent(SetUp3Activity.this, SetUp2Activity.class));
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);
		this.finish();
	}

	@Override
	public void showNext() {
		String result = et_phonenumber.getText().toString();
		if (!TextUtils.isEmpty(result)) {
//			if (result.length() == 11) {
				spf.edit().putString("safephonenumber", result).commit();
//			}
		}
		startActivity(new Intent(SetUp3Activity.this, SetUp4Activity.class));
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		this.finish();
	}

	@Override
	public void onBackPressed() {
		showPrevious();
	}
}
