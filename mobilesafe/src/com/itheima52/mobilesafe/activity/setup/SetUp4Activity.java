package com.itheima52.mobilesafe.activity.setup;

import com.itheima52.mobilesafe.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class SetUp4Activity extends BaseSetupActivity {

	private CheckBox chk_startsafe;
	private TextView tv_startsafe;
	private Button btn_previous,btn_next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_4);
		
		chk_startsafe=(CheckBox) this.findViewById(R.id.chk_startsafe);
		tv_startsafe=(TextView) this.findViewById(R.id.tv_startsafe);
		boolean isProtected=spf.getBoolean("isProtected", true);
		if(isProtected){
			chk_startsafe.setChecked(true);
			tv_startsafe.setText("您已经成功开启防盗保护");
		}else{
			chk_startsafe.setChecked(false);
			tv_startsafe.setText("您还没有开启防盗保护");
		}
		btn_previous=(Button) this.findViewById(R.id.btn_previous);
		btn_previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPrevious();
			}
		});
		btn_next=(Button) this.findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showNext();
			}
		});
	}

	@Override
	public void showPrevious() {
		// TODO Auto-generated method stub
		if(chk_startsafe.isChecked()){
			spf.edit().putBoolean("isProtected", true).commit();
		}else{
			spf.edit().putBoolean("isProtected", false).commit();
		}
		startActivity(new Intent(SetUp4Activity.this, SetUp3Activity.class));
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);
		this.finish();
	}

	@Override
	public void showNext() {
		if(chk_startsafe.isChecked()){
			spf.edit().putBoolean("isProtected", true).commit();
		}else{
			spf.edit().putBoolean("isProtected", false).commit();
		}
		startActivity(new Intent(SetUp4Activity.this, SetUp5Activity.class));
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		this.finish();
	}

}
