package com.itheima52.mobilesafe.activity;

import java.util.ArrayList;
import java.util.List;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.db.AntivirusDao;
import com.itheima52.mobilesafe.engine.AppInfos;
import com.itheima52.mobilesafe.entity.AppInfo;
import com.itheima52.mobilesafe.utils.DbCopy;
import com.itheima52.mobilesafe.utils.GetMd5;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AntivirusActivity extends Activity {

	protected static final int SCANEND = 0x110;
	protected static final int BENGIN = 0x111;
	protected static final int SCANING = 0x113;
	private ImageView iv_act_scanning;
	private TextView tv_init_virus;
	private ProgressBar pb_progress;
	private double percent;
	private ListView lv_scaninfo,lv_viurs;
	private List<String> apps=new ArrayList<String>();
	private ArrayAdapter<String> appAdapter,virusAdapter;
	private TextView tv_varius;
	private List<String> virus=new ArrayList<String>();
	private int viursCount=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_antivirus);
		initView();
		initData();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case BENGIN:
				tv_init_virus.setText("开始扫描...");
				break;
			case SCANING:
				tv_init_virus.setText("扫描中...");
				pb_progress.setProgress((int) (100 * percent));
				
				ScanInfo info=(ScanInfo) msg.obj;
				if(info.desc){
					viursCount++;
					tv_varius.setText("当前共发现"+viursCount+"条病毒");
					virus.add(info.appName+"    "+info.virus);
					virusAdapter.notifyDataSetChanged();
				}
				apps.add(0,info.appName);
				appAdapter.notifyDataSetChanged();
				lv_scaninfo.setSelection(0);
				break;
			case SCANEND:
				tv_init_virus.setText("扫描结束...");
				iv_act_scanning.clearAnimation();
				break;
			}
		};
	};

	private void initData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = Message.obtain();
				msg.what = BENGIN;
				handler.sendMessage(msg);

				int total, index = 1;
				PackageManager packageManager = getPackageManager();
				List<PackageInfo> installedPackages = packageManager
						.getInstalledPackages(0);
				total = installedPackages.size();
				for (PackageInfo info : installedPackages) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ScanInfo scanInfo = new ScanInfo();
					String appName = info.applicationInfo.loadLabel(
							packageManager).toString();
					scanInfo.appName = appName;
					scanInfo.packageName = info.packageName;
					String sourceDir = info.applicationInfo.sourceDir;
					// 获取到文件的MD5
					String md5 = GetMd5.getFileMd5(sourceDir);
					
					System.out.println(appName+"/"+info.packageName+"/"+md5);
					
					String desc = AntivirusDao.checkFileVirus(md5);
					// 如果不为空 就有病毒
					if (desc != null) {
						scanInfo.desc = true;
						scanInfo.virus=desc;
					} else {
						scanInfo.desc = false;
					}
					percent= index / (total*1.0);
					msg = handler.obtainMessage();
					msg.obj = scanInfo;
					msg.what = SCANING;
					handler.sendMessage(msg);
					index++;
				}
				msg = Message.obtain();
				msg.what = SCANEND;
				handler.sendMessage(msg);
			}
		}).start();

	}

	private void initView() {
		iv_act_scanning = (ImageView) this.findViewById(R.id.iv_act_scanning);
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(4000);
		rotate.setRepeatCount(Animation.INFINITE);
		iv_act_scanning.startAnimation(rotate);
		tv_init_virus = (TextView) this.findViewById(R.id.tv_init_virus);
		pb_progress = (ProgressBar) this.findViewById(R.id.pb_progress);
		pb_progress.setMax(100);
		lv_scaninfo=(ListView) this.findViewById(R.id.lv_scaninfo);
		appAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,apps);
		lv_scaninfo.setAdapter(appAdapter);
		tv_varius=(TextView) this.findViewById(R.id.tv_varius);
		lv_viurs=(ListView) this.findViewById(R.id.lv_viurs);
		virusAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, virus);
		lv_viurs.setAdapter(virusAdapter);
	}

	static class ScanInfo {
		boolean desc;
		String virus;
		String appName;
		String packageName;
	}
}
