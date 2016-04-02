package com.itheima52.mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.adapter.FunctionsListAdapter;
import com.itheima52.mobilesafe.db.BlackNumberDao;
import com.itheima52.mobilesafe.service.RocketService;
import com.itheima52.mobilesafe.utils.GetMd5;
import com.itheima52.mobilesafe.utils.Toasts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	private GridView gv_functions;
	private EditText et_psw, et_confirm_psw;
	private TextView tv_ok, tv_cancle;
	private List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
	private String[] strs = new String[] { "�ֻ�����", "ͨѶ��ʿ", "�������", "���̹���",
			"����ͳ��", "�ֻ�ɱ��", "��������", "�߼�����", "��������" };
	private int[] imgs = new int[] { R.drawable.home_safe,
			R.drawable.home_callmsgsafe, R.drawable.home_apps,
			R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize,
			R.drawable.home_tools, R.drawable.home_settings };
	private FunctionsListAdapter adapter;
	private long lastTime=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		
		initRockets();
		
	}

	private void initRockets() {
			startService(new Intent(HomeActivity.this,RocketService.class));
	}

	private void initView() {
		gv_functions = (GridView) this.findViewById(R.id.gv_functions);
		/**
		 * �������
		 */
		for (int i = 0; i < strs.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("img", imgs[i]);
			map.put("text", strs[i]);
			datas.add(map);
		}
		// ��ʼ��gridview
		adapter = new FunctionsListAdapter(this, datas);
		gv_functions.setAdapter(adapter);
		gv_functions.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					// �����ֻ�����ҳ��
					showSetPasswordDialog();
					break;
				case 1:
					// ͨѶ��ʿҳ��
					startActivity(new Intent(HomeActivity.this,CallSafeActivity.class));
					break;
				case 2:
					// �������ҳ��
					startActivity(new Intent(HomeActivity.this,AppManagerActivity.class));
					break;
				case 3:
					// ���̹���ҳ��
					startActivity(new Intent(HomeActivity.this,ProcessActivity.class));
					
					break;
				case 4:
					// ����ͳ��ҳ��
					startActivity(new Intent(HomeActivity.this,TrafficManagerActivity.class));
					break;
				case 5:
					// �ֻ�ɱ��ҳ��
					startActivity(new Intent(HomeActivity.this,AntivirusActivity.class));
					break;
				case 6:
					// ��������ҳ��
					startActivity(new Intent(HomeActivity.this,CleanCacheActivity.class));
					break;
				case 7:
					// �߼�����ҳ��
					Intent atoolsIntent = new Intent(HomeActivity.this,
							AToolsActivity.class);
					startActivity(atoolsIntent);
					break;
				case 8:
					// ��������ҳ��
					Intent settingsIntent = new Intent(HomeActivity.this,
							SettingsActivity.class);
					startActivity(settingsIntent);
					break;
				}
			}
		});
	}

	private void showSetPasswordDialog() {
		final String password=getSharedPreferences("config", MODE_PRIVATE).getString("password", null);
		
		View view = View.inflate(this, R.layout.dialog_set_password, null);
		et_psw = (EditText) view.findViewById(R.id.et_passwrod);
		et_confirm_psw = (EditText) view.findViewById(R.id.et_refirmpsw);
		tv_ok = (TextView) view.findViewById(R.id.tv_ok);
		tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
		
		//�ڶ��ν���ֱ��ȷ������
		if(password!=null){
			et_psw.setText(password);
			et_psw.setEnabled(false);
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		dialog.setView(view);
		dialog.show();
		tv_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String first = et_psw.getText().toString();
				String second = et_confirm_psw.getText().toString();
				if ((!TextUtils.isEmpty(first)) && (!TextUtils.isEmpty(second))) {
					if(password==null){
						if (first.equals(second)) {
								/**
								 * ��������
								 */
								SharedPreferences sharedPreferences = getSharedPreferences(
										"config", MODE_PRIVATE);
								String md5=GetMd5.getMd5(second)+getPackageName();
								sharedPreferences.edit().putString("password", md5).commit();
							enterTo();
							dialog.dismiss();
						} else {
							Toast.makeText(HomeActivity.this, "ȷ����������һ��", 1).show();
						}
					}else{
						String md5=GetMd5.getMd5(second)+getPackageName();
						if(md5.equals(password)){
							enterTo();
							dialog.dismiss();
						}else{
							Toast.makeText(HomeActivity.this, "�������", 1).show();
						}
					}
				} else {
					Toast.makeText(HomeActivity.this, "���벻��ȷ", 1).show();
				}
			}
		});
		tv_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dialog.isShowing())
					dialog.dismiss();
			}
		});
	}
	private void enterTo() {
		Intent findIntent=new Intent(HomeActivity.this,FindLostActivity.class);
		startActivity(findIntent);
	}
	@Override
	public void onBackPressed() {
		if(lastTime==0){
			lastTime=System.currentTimeMillis();
			return ;
		}
		long cureentTime=System.currentTimeMillis();
		if(cureentTime-lastTime<1000){
			super.onBackPressed();
		}else{
			Toasts.show(HomeActivity.this,"˫���˳�");
			lastTime=cureentTime;
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		stopService(new Intent(HomeActivity.this,RocketService.class));
	}
}
