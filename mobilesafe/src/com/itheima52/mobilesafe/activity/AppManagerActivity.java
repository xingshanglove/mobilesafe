package com.itheima52.mobilesafe.activity;

import java.util.ArrayList;
import java.util.List;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.adapter.AppManagerAdapter;
import com.itheima52.mobilesafe.engine.AppInfos;
import com.itheima52.mobilesafe.entity.AppInfo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class AppManagerActivity extends Activity implements OnClickListener {
	TextView tv_rom, tv_sd, tv_alarm;
	ListView lv_process;
	List<AppInfo> appInfos;
	AppManagerAdapter adapter;
	ArrayList<AppInfo> sysApp = new ArrayList<AppInfo>();
	ArrayList<AppInfo> userApp = new ArrayList<AppInfo>();
	PopupWindow popupWindow;
	AppInfo clickAppInfo;
	UninstallReceiver receiver;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adapter = new AppManagerAdapter(AppManagerActivity.this, userApp,
					sysApp);
			lv_process.setAdapter(adapter);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appmanager);
		initView();
		initData();
	}

	private void initData() {
		/**
		 * ��ȡ����ʣ��ռ�
		 */
		long rom_space = Environment.getDataDirectory().getFreeSpace();
		// sd �ڴ�
		long sd_space = Environment.getExternalStorageDirectory()
				.getFreeSpace();

		tv_rom.setText("�ڴ����:" + Formatter.formatFileSize(this, rom_space));
		tv_sd.setText("SD������:" + Formatter.formatFileSize(this, sd_space));
		/**
		 * ��װ������Ϣ
		 */
		new Thread(new Runnable() {
			@Override
			public void run() {
				appInfos = AppInfos.getAppInfos(AppManagerActivity.this);

				sysApp = new ArrayList<AppInfo>();
				userApp = new ArrayList<AppInfo>();

				for (AppInfo info : appInfos) {
					if (info.isUserApp()) {
						userApp.add(info);
					} else {
						sysApp.add(info);
					}
				}
				handler.sendEmptyMessage(0x111);
			}
		}).start();

		lv_process.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				popupWindowDismiss();
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem < userApp.size() + 1) {
					tv_alarm.setText("�û�����(" + userApp.size() + ")");
				}
				if (firstVisibleItem > userApp.size() + 1) {
					tv_alarm.setText("ϵͳ����(" + sysApp.size() + ")");
				}
			}
		});
		lv_process.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_process.getItemAtPosition(position);
				if (obj != null && obj instanceof AppInfo) {
					clickAppInfo = (AppInfo) obj;
					View popView = View.inflate(AppManagerActivity.this,
							R.layout.app_manager_pop_view, null);

					LinearLayout ll_uninstall = (LinearLayout) popView
							.findViewById(R.id.ll_uninstall);

					LinearLayout ll_share = (LinearLayout) popView
							.findViewById(R.id.ll_share);

					LinearLayout ll_start = (LinearLayout) popView
							.findViewById(R.id.ll_start);

					ll_uninstall.setOnClickListener(AppManagerActivity.this);

					ll_share.setOnClickListener(AppManagerActivity.this);

					ll_start.setOnClickListener(AppManagerActivity.this);

					popupWindowDismiss();
					// -2��ʾ��������
					popupWindow = new PopupWindow(popView, -2, -2);
					// ��Ҫע�⣺ʹ��PopupWindow �������ñ�������Ȼû�ж���
					popupWindow.setBackgroundDrawable(new ColorDrawable(
							Color.TRANSPARENT));

					int[] location = new int[2];
					// ��ȡviewչʾ�����������λ��
					view.getLocationInWindow(location);

					popupWindow.showAtLocation(parent, Gravity.LEFT
							+ Gravity.TOP, 100, location[1]);

					ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 1.0f,
							1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
							Animation.RELATIVE_TO_SELF, 0.5f);

					sa.setDuration(500);
					
					AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
					alphaAnimation.setDuration(500);
					popView.startAnimation(sa);
					popView.startAnimation(alphaAnimation);

				} else {
					popupWindowDismiss();
				}
			}

		});
		
		/**
		 * ע��ж�ع㲥
		 */
		receiver=new UninstallReceiver();
		IntentFilter filter=new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
		filter.addDataScheme("package");
		registerReceiver(receiver, filter);
	}
	private class UninstallReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			System.out.println("instakk");
		}
		
	}

	private void popupWindowDismiss() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
		}
	}

	private void initView() {
		tv_rom = (TextView) this.findViewById(R.id.tv_rom);
		tv_sd = (TextView) this.findViewById(R.id.tv_sd);
		lv_process = (ListView) this.findViewById(R.id.lv_process);
		tv_alarm = (TextView) this.findViewById(R.id.tv_alarm);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_share:
			popupWindowDismiss();
			/**
			 * ����˷���
			 */
			Intent shareIntent=new Intent("android.intent.action.SEND");
			shareIntent.setType("text/plain");
			shareIntent.putExtra("android.intent.extra.SUBJECT", "����");
			shareIntent.putExtra("android.intent.extra.TEXT","Hi,�Ƽ���ʹ�����"+clickAppInfo.getApkName()+"���ص�ַ:"+"https://play.google.com/store/apps/details?id="+clickAppInfo.getApkPackageName());
			this.startActivity(Intent.createChooser(shareIntent, "����"));
			break;
		case R.id.ll_start:
			popupWindowDismiss();
			/**
			 * ����
			 */
			Intent startIntent=this.getPackageManager().getLaunchIntentForPackage(clickAppInfo.getApkPackageName());
			this.startActivity(startIntent);
			break;
		case R.id.ll_uninstall:
			popupWindowDismiss();
			/**
			 * ж��
			 */
			Intent uninstallIntent=new Intent("android.intent.action.DELETE",Uri.parse("package:"+clickAppInfo.getApkPackageName()));
			this.startActivity(uninstallIntent);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		popupWindowDismiss();
		if(receiver!=null)
			unregisterReceiver(receiver);
	}
}
