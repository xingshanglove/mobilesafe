package com.itheima52.mobilesafe.activity.function8;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.db.AddressDao;
import com.itheima52.mobilesafe.utils.Toasts;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddressActivity extends Activity implements OnClickListener {
	private EditText et_input_number;
	private Button btn_query;
	private TextView tv_query_result;
	private ImageView iv_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		initView();
	}

	private void initView() {
		et_input_number = (EditText) this.findViewById(R.id.et_input_number);
		et_input_number.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String number = et_input_number.getText().toString();
				String address = AddressDao.getAddress(number);
				if (!TextUtils.isEmpty(address))
					tv_query_result.setText(address);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		btn_query = (Button) this.findViewById(R.id.btn_query);
		btn_query.setOnClickListener(this);
		tv_query_result = (TextView) this.findViewById(R.id.tv_query_result);
		iv_back=(ImageView) this.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddressActivity.this.finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		String number = et_input_number.getText().toString();
		String address = AddressDao.getAddress(number);
		if (!TextUtils.isEmpty(address)){
			tv_query_result.setText(address);
		}else{
			Animation animation=AnimationUtils.loadAnimation(AddressActivity.this, R.anim.shake);
			AddressActivity.this.findViewById(R.id.et_input_number).startAnimation(animation);
			vibrate();
		}
	}
	private void vibrate(){
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(new long[]{500,500,500,500},-1);
	}
}
