package com.itheima52.mobilesafe.activity;

import java.util.List;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.activity.function2.ChoiceActivity;
import com.itheima52.mobilesafe.activity.setup.ContactActivity;
import com.itheima52.mobilesafe.adapter.CallSafeAdapter;
import com.itheima52.mobilesafe.adapter.CallSafeAdapter.setOnDeleteAction;
import com.itheima52.mobilesafe.db.BlackNumberDao;
import com.itheima52.mobilesafe.entity.BlackNumberInfo;
import com.itheima52.mobilesafe.entity.PhoneInfo;
import com.itheima52.mobilesafe.utils.Toasts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

public class CallSafeActivity extends Activity implements OnClickListener{
	private ListView lv_function;
	private CallSafeAdapter adapter;
	private List<BlackNumberInfo> blackNumbers;
	private ImageView iv_add,iv_back;
	private BlackNumberDao dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_callsafe);
		initView();
		initData();
	}
	private void initData() {
		// TODO Auto-generated method stub
		adapter=new CallSafeAdapter(this,blackNumbers);
		adapter.setOnDeleteAction(new setOnDeleteAction() {
			@Override
			public void onDelete(int id) {
				dao.delete(blackNumbers.get(id).getNumber());
				blackNumbers.remove(id);
				adapter.notifyDataSetChanged();
			}
		});
		lv_function.setAdapter(adapter);
		
	}
	private void initView() {
		//获取数据
		readBlackNumber();
		//填充数据
		lv_function=(ListView) this.findViewById(R.id.lv_function);
		
		iv_back=(ImageView) this.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		iv_add=(ImageView) this.findViewById(R.id.iv_add);
		iv_add.setOnClickListener(this);
	}
	public void readBlackNumber(){
		dao=new BlackNumberDao(this);
		blackNumbers=dao.findAll();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(dao!=null)
			dao.closeDb();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_add:
			showChoiceActivity();
			break;
		case R.id.iv_back:
			CallSafeActivity.this.finish();
			break;
		}
	}
	private void showChoiceActivity() {
		startActivityForResult(new Intent(CallSafeActivity.this, ChoiceActivity.class),0);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case 0:
			String type=data.getStringExtra("type");
			if(type.equals("choice")){
				startActivityForResult(new Intent(CallSafeActivity.this, ContactActivity.class),0);
			}
			break;
//		case 1:
//			PhoneInfo info=(PhoneInfo) data.getSerializableExtra("phone");
			
		}
	}
}
