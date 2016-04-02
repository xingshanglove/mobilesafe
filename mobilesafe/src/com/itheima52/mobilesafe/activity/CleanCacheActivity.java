package com.itheima52.mobilesafe.activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.entity.CacheInfo;

import android.app.Activity;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2015
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2015-3-7 下午5:02:37
 * 
 * 描 述 ：
 * 
 * 缓存清理 修订历史 ：
 * 
 * ============================================================
 **/
public class CleanCacheActivity extends Activity {

	private PackageManager packageManager;
	ArrayList<CacheInfo> cacheInfos = new ArrayList<CacheInfo>();
	ListView list_view;
	CacheListAdapter adapter;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			adapter.notifyDataSetChanged();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initUI();
	}

	private void initUI() {
		setContentView(R.layout.activity_clean_cache);
		packageManager = getPackageManager();
		list_view = (ListView) this.findViewById(R.id.list_view);
		adapter = new CacheListAdapter();
		list_view.setAdapter(adapter);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 安装到手机上面所有的应用程序
				List<PackageInfo> installedPackages = packageManager
						.getInstalledPackages(0);

				for (PackageInfo packageInfo : installedPackages) {
					getCacheSize(packageInfo);
				}
			}
		}).start();

	}

	/**
	 * 清楚所有缓存
	 * 
	 * @param v
	 */
	public void clearAll(View v) {
		Method[] methods = PackageManager.class.getMethods();
		for (Method method : methods) {
			if (method.getName().equals("freeStorageAndNotify")) {
				try {
					method.invoke(packageManager, Integer.MAX_VALUE,
							new MyIPackageDataObserver());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					System.out.println("error1");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					System.out.println("error2");
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					System.out.println("error3");
				}
			}
		}
	}

	private class MyIPackageDataObserver extends IPackageDataObserver.Stub {
		@Override
		public void onRemoveComplete( String packageName, boolean succeeded)
				throws RemoteException {
				System.out.println(packageName);
		}
	}

	private void getCacheSize(PackageInfo packageInfo) {
		try {
			// Class<?> clazz = getClassLoader().loadClass("packageManager");
			// 通过反射获取到当前的方法
			Method method = PackageManager.class.getDeclaredMethod(
					"getPackageSizeInfo", String.class,
					IPackageStatsObserver.class);
			method.invoke(packageManager, packageInfo.packageName,
					new MyIPackageStatsObserver(packageInfo));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private class MyIPackageStatsObserver extends IPackageStatsObserver.Stub {

		PackageInfo info;

		public MyIPackageStatsObserver(PackageInfo packageInfo) {
			this.info = packageInfo;
		}

		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			// TODO Auto-@Override
			long cacheSize = pStats.cacheSize;
			if (cacheSize > 0) {
				CacheInfo cacheInfo = new CacheInfo();
				cacheInfo
						.setIcon(info.applicationInfo.loadIcon(packageManager));
				cacheInfo.setAppName(info.applicationInfo.loadLabel(
						packageManager).toString());
				cacheInfo.setCacheSize(cacheSize);
				cacheInfos.add(cacheInfo);
				handler.sendEmptyMessage(0x111);
			}
		}
	}

	class CacheListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cacheInfos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return cacheInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(CleanCacheActivity.this,
						R.layout.cache_item, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.icon.setImageDrawable(cacheInfos.get(position).getIcon());
			holder.appName.setText(cacheInfos.get(position).getAppName());
			holder.size.setText(Formatter.formatFileSize(
					CleanCacheActivity.this, cacheInfos.get(position)
							.getCacheSize()));
			holder.clear.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

				}
			});
			return convertView;
		}
	}

	class ViewHolder {
		ImageView icon;
		TextView appName;
		TextView size;
		TextView clear;

		public ViewHolder(View view) {
			icon = (ImageView) view.findViewById(R.id.iv_appicon);
			appName = (TextView) view.findViewById(R.id.tv_appName);
			size = (TextView) view.findViewById(R.id.tv_cachesize);
			clear = (TextView) view.findViewById(R.id.tv_clear);
		}
	}
}
