package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.activity.function8.AddressActivity;
import com.itheima52.mobilesafe.utils.SmsCopy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 高级工具	
 * @author root
 *
 */
public class AToolsActivity extends Activity{
	private ImageView iv_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
		initView();
	}
	private void initView() {
		iv_back=(ImageView) this.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AToolsActivity.this.finish();
			}
		});
	}
	/**
	 * 
	 * 电话号码查询
	 * @param v
	 */
	public void numberAddressQuery(View v){
		startActivity(new Intent(AToolsActivity.this,AddressActivity.class));
	}
	public void numberCopy(View v){
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean result=SmsCopy.copy(AToolsActivity.this);
				if(result){
					Looper.prepare();
					Toast.makeText(AToolsActivity.this,"备份成功", 1).show();
					Looper.loop();
				}else{
					Looper.prepare();
					Toast.makeText(AToolsActivity.this,"备份失败", 1).show();
					Looper.loop();
				}
			}
		}).start();
	}
	public void lockpro(View v){
		startActivity(new Intent(AToolsActivity.this,AppLockActivity.class));
	}
}
