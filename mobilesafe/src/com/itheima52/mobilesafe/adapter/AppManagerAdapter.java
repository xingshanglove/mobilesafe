package com.itheima52.mobilesafe.adapter;

import java.util.ArrayList;
import java.util.List;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.activity.AppManagerActivity;
import com.itheima52.mobilesafe.entity.AppInfo;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppManagerAdapter extends BaseAdapter {
	ArrayList<AppInfo> sysApp;
	ArrayList<AppInfo> userApp;
	LayoutInflater inflater;
	Context context;
	int systemSize;
	int userSize;

	
	public AppManagerAdapter(Context context, ArrayList<AppInfo> userApp,
			ArrayList<AppInfo> sysApp) {
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.userApp = userApp;
		this.sysApp = sysApp;
		this.context = context;
		this.userSize = userApp.size();
		this.systemSize = sysApp.size();
	}

	@Override
	public int getCount() {
		return userSize + systemSize + 2;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (position == 0 || position == userSize + 1) {
			return null;
		}
		AppInfo appInfo;
		if (position < userSize + 1) {
			// 把多出来的特殊的条目减掉
			appInfo = userApp.get(position - 1);

		} else {
			int location = userSize + 2;
			appInfo = sysApp.get(position - location);
		}
		return appInfo;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position == 0) {
			/**
			 * 表示应用程序
			 */
			TextView tv = new TextView(context);
			tv.setText("用户程序(" + userSize + ")");
			tv.setTextSize(20);
			return tv;
		} else if (position == (userSize + 1)) {
			/**
			 * 系统程序
			 */
			TextView tv = new TextView(context);
			tv.setText("系统程序(" + systemSize + ")");
			tv.setTextSize(20);
			return tv;
		} else if (position > 0 && position < (userSize + 1)) {
			/**
			 * 用户程序列表
			 */
			ViewHolder holder;
			if (convertView != null && convertView instanceof LinearLayout) {
				holder = (ViewHolder) convertView.getTag();
			} else {
				convertView = inflater.inflate(R.layout.appinfo_item, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			holder.iv_app_icon.setImageDrawable(userApp.get(position - 1)
					.getApkIcon());
			holder.tv_app_name.setText(userApp.get(position - 1).getApkName());
			holder.tv_app_size.setText(Formatter.formatFileSize(context,
					userApp.get(position - 1).getApkSize()));
			if (userApp.get(position - 1).isRom()) {
				holder.tv_is_rom.setText("手机内存");
			} else {
				holder.tv_is_rom.setText("SD卡");
			}
			return convertView;
		} else if (position > (userSize + 1)) {
			int sysPosition = position - userSize - 2;
			ViewHolder holder;
			if (convertView != null && convertView instanceof LinearLayout) {
				holder = (ViewHolder) convertView.getTag();
			} else {
				convertView = inflater.inflate(R.layout.appinfo_item, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			holder.iv_app_icon.setImageDrawable(sysApp.get(sysPosition)
					.getApkIcon());
			holder.tv_app_name.setText(sysApp.get(sysPosition).getApkName());
			holder.tv_app_size.setText(Formatter.formatFileSize(context, sysApp
					.get(sysPosition).getApkSize()));
			if (sysApp.get(sysPosition).isRom()) {
				holder.tv_is_rom.setText("手机内存");
			} else {
				holder.tv_is_rom.setText("SD卡");
			}
			return convertView;
		}
		return null;

	}

	class ViewHolder {
		ImageView iv_app_icon;
		TextView tv_app_name;
		TextView tv_is_rom;
		TextView tv_app_size;

		public ViewHolder(View view) {
			iv_app_icon = (ImageView) view.findViewById(R.id.iv_app_icon);
			tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
			tv_is_rom = (TextView) view.findViewById(R.id.tv_is_rom);
			tv_app_size = (TextView) view.findViewById(R.id.tv_app_size);
		}
	}
}
