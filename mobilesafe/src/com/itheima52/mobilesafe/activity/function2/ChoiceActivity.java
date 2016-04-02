package com.itheima52.mobilesafe.activity.function2;

import com.itheima52.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ChoiceActivity extends Activity implements OnClickListener {
	private TextView tv_cancle;
	private TextView tv_choice_c;
	private TextView tv_choice_input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);
		tv_choice_input = (TextView) this.findViewById(R.id.tv_choice_input);
		tv_choice_input.setOnClickListener(this);
		tv_choice_c = (TextView) this.findViewById(R.id.tv_choice_c);
		tv_choice_c.setOnClickListener(this);
		tv_cancle = (TextView) this.findViewById(R.id.tv_cancle);
		tv_cancle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.tv_choice_input:
			intent.putExtra("type","input");
			setResult(0, intent);
			ChoiceActivity.this.finish();
			break;
		case R.id.tv_choice_c:
			intent.putExtra("type","choice");
			setResult(0, intent);
			ChoiceActivity.this.finish();
			break;
		case R.id.tv_cancle:
			ChoiceActivity.this.finish();
			break;
		}
		
	}
}
