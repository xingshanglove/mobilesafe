package com.itheima52.mobilesafe.activity.setup;

import com.itheima52.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SetUp1Activity extends BaseSetupActivity{
	private Button btn_next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_1);
		btn_next=(Button) this.findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showNext();
			}
		});
	}
	@Override
	public void showPrevious() {
	}
	@Override
	public void showNext() {
		startActivity(new Intent(SetUp1Activity.this,SetUp2Activity.class));
		SetUp1Activity.this.finish();
		overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
	}
}
