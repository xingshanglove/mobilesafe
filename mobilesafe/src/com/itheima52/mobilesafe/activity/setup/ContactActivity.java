package com.itheima52.mobilesafe.activity.setup;

import java.util.ArrayList;
import java.util.List;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.adapter.ContactsAdapter;
import com.itheima52.mobilesafe.entity.PhoneInfo;
import com.itheima52.mobilesafe.utils.GetNumber;
import com.itheima52.mobilesafe.utils.Toasts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ContactActivity extends Activity {
	ListView lv_contacts;
	ContactsAdapter adapter;
	List<PhoneInfo> infos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activty_contact);
		infos=GetNumber.readContact(this);
		lv_contacts=(ListView) this.findViewById(R.id.lv_contacts);
		adapter=new ContactsAdapter(this, infos);
		lv_contacts.setAdapter(adapter);
		lv_contacts.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				PhoneInfo phone=infos.get(position);
				Intent intent=new Intent();
				Bundle budle=new Bundle();
				budle.putSerializable("phone", phone);
				intent.putExtras(budle);
				setResult(1, intent);
				ContactActivity.this.finish();
			}
		});
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
