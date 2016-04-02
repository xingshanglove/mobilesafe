package com.itheima52.mobilesafe.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.R.id;
import com.itheima52.mobilesafe.R.layout;
import com.itheima52.mobilesafe.R.menu;
import com.itheima52.mobilesafe.entity.VersionInfo;
import com.itheima52.mobilesafe.utils.DbCopy;
import com.itheima52.mobilesafe.utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

	private static final int CODE_UPDATE_DIALOG = 0x111;
	private static final int CODE_URL_EXCEPTION = 0x112;
	private static final int CODE_NET_EXCEPTION = 0x113;
	protected static final int CODE_IS_LATEST = 0x114;
	private TextView tv_version;
	private ProgressBar pb_load;
	private RelativeLayout rl_root;
	private TextView tv_download;
	private int versionCode;
	private String versionName;
	VersionInfo latestsVersionInfo;

	private SharedPreferences mPref;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CODE_UPDATE_DIALOG:
				showUpdateDialog();
				break;
			case CODE_URL_EXCEPTION:
				Toast.makeText(SplashActivity.this, "url错误", 1).show();
				enterHome();
				break;
			case CODE_NET_EXCEPTION:
				Toast.makeText(SplashActivity.this, "网络错误", 1).show();
				enterHome();
				break;
			case CODE_IS_LATEST:
				enterHome();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		initView();
		/**
		 * 创建快捷方式
		 */
		
		createShortCut();
	}

	private void createShortCut() {
		Intent intent=new Intent();
		/**
		 * 干什么事情
		 * 叫什么名字
		 * 长什么样子
		 * 
		 */
		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		//为true可以创建多个
		intent.putExtra("duplicate", false);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON,R.drawable.ic_launcher);
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,"安全卫士");
		
		
		//干什么！！不能使用显示意图 必须使用隐士意图
		Intent cut_intent=new Intent();
		cut_intent.setAction("short.cut.in");
		cut_intent.addCategory("android.intent.category.DEFAULT");
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, cut_intent);
		sendBroadcast(intent);
	}

	private void initData() {
		DbCopy.copyDb(getApplicationContext(), "address.db");
		DbCopy.copyDb(getApplicationContext(),"antivirus.db");
	}

	private void initView() {
		tv_version = (TextView) this.findViewById(R.id.tv_version);
		pb_load = (ProgressBar) this.findViewById(R.id.pb_load);
		tv_download = (TextView) this.findViewById(R.id.tv_download);

		// 动态现在版本信息
		getVersion();
		tv_version.setText("版本号:" + versionName);

		mPref = getSharedPreferences("config", MODE_PRIVATE);
		boolean auto_refresh = mPref.getBoolean("auto_refresh", true);
		if (auto_refresh) {
			checkVersion();
		} else {
			handler.sendEmptyMessageDelayed(CODE_IS_LATEST, 2000);
		}

		rl_root = (RelativeLayout) this.findViewById(R.id.rl_root);

		AlphaAnimation anim = new AlphaAnimation(0.3f, 1.0f);
		anim.setDuration(2000);
		rl_root.startAnimation(anim);
	}

	private void getVersion() {
		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			versionName = packageInfo.versionName;
			versionCode = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// 没有找到包名
			e.printStackTrace();
		}
	}

	/**
	 * 从服务器获取信息 进行校验
	 */
	private void checkVersion() {
		final long enterTime = System.currentTimeMillis();
		// 启动子线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = Message.obtain();
				HttpURLConnection conn = null;
				try {
					URL url = new URL(
							"http://mirrors.tianfeiyu.com/update.json");
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");// 请求方式
					conn.setConnectTimeout(5000);// 超时
					conn.setReadTimeout(5000);// 设置响应超时,连接上了 但是无服务不给数据
					conn.connect();// 连接服务器
					int responseCode = conn.getResponseCode();
					if (responseCode == 200) {
						InputStream inputStream = conn.getInputStream();
						String result = StreamUtils.readFromString(inputStream);
						JSONObject jo = new JSONObject(result);
						String versionName = jo.getString("versionName");
						int lversionCode = jo.getInt("versionCode");
						String description = jo.getString("description");
						String apkUrl = jo.getString("downloadUrl");
						latestsVersionInfo = new VersionInfo(versionName,
								lversionCode, description, apkUrl);
						if (latestsVersionInfo.getVersionCode() > versionCode) {
							// 有更新,弹出升级对话框
							msg.what = CODE_UPDATE_DIALOG;
						} else {
							msg.what = CODE_IS_LATEST;
						}
					}
				} catch (MalformedURLException e) {
					// url 异常
					e.printStackTrace();
					msg.what = CODE_URL_EXCEPTION;
				} catch (IOException e) {
					// 网络错误
					msg.what = CODE_NET_EXCEPTION;
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {
					long useTime = System.currentTimeMillis() - enterTime;
					if (useTime < 2000) {
						try {
							Thread.sleep(2000 - useTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					handler.sendMessage(msg);
					if (conn != null)
						conn.disconnect();
				}
			}
		}).start();
	}

	/**
	 * 更新提示框
	 */
	private void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("最新版本是:" + latestsVersionInfo.getVersionName());
		builder.setMessage(latestsVersionInfo.getDescription());
		builder.setPositiveButton("立即更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				downLoadApk();
			}
		});
		builder.setNegativeButton("暂不更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
			}
		});
		builder.show();
	}

	private void enterHome() {
		Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
		startActivity(intent);
		SplashActivity.this.finish();
	}

	private void downLoadApk() {
		String target = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			tv_download.setVisibility(View.VISIBLE);
			target = Environment.getExternalStorageDirectory() + "/update.apk";
			HttpUtils http = new HttpUtils();
			Log.v("url", latestsVersionInfo.getApkUrl());
			http.download(latestsVersionInfo.getApkUrl(), target,
					new RequestCallBack<File>() {
						@Override
						public void onSuccess(ResponseInfo<File> arg0) {
							Log.v("--->", "success");
							InstallApk();
						}

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							Log.v("--->", arg1);
							tv_download.setVisibility(View.GONE);
						}

						// 文件的下载进度
						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
							super.onLoading(total, current, isUploading);
							tv_download.setText("下载进度:"
									+ (current * 100 / +total) + "%");
							Log.v("-->", current + "/" + total);
						}
					});
		} else {
			Toast.makeText(SplashActivity.this, "没有SD卡", 1).show();
		}
	}

	private void InstallApk() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setDataAndType(
				Uri.fromFile(new File(Environment.getExternalStorageDirectory()
						+ "/update.apk")),
				"application/vnd.android.package-archive");
		startActivityForResult(intent, 0);

	}

	// 没有点击安装 回掉
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		enterHome();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
}
