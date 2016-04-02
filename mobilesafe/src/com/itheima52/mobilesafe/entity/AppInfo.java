package com.itheima52.mobilesafe.entity;

import android.graphics.drawable.Drawable;

public class AppInfo {
	/**
	 * 程序图标
	 */
	private Drawable apkIcon;
	/**
	 * 程序名称
	 */
	private String apkName;
	/**
	 * 占用内存大小
	 */
	private long apkSize;
	/**
	 * 
	 */
	private boolean userApp;
	/**
	 * 放置的位置
	 */
	private boolean isRom;
	/**
	 * 包名
	 */
	private String apkPackageName;
	
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public AppInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public Drawable getApkIcon() {
		return apkIcon;
	}
	public void setApkIcon(Drawable apkIcon) {
		this.apkIcon = apkIcon;
	}
	public String getApkName() {
		return apkName;
	}
	public void setApkName(String apkName) {
		this.apkName = apkName;
	}
	public long getApkSize() {
		return apkSize;
	}
	public void setApkSize(long apkSize) {
		this.apkSize = apkSize;
	}
	public boolean isUserApp() {
		return userApp;
	}
	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}
	public boolean isRom() {
		return isRom;
	}
	public void setRom(boolean isRom) {
		this.isRom = isRom;
	}
	public String getApkPackageName() {
		return apkPackageName;
	}
	public void setApkPackageName(String apkPackageName) {
		this.apkPackageName = apkPackageName;
	}
	@Override
	public String toString() {
		return "AppInfo [apkIcon=" + apkIcon + ", apkName=" + apkName
				+ ", apkSize=" + apkSize + ", userApp=" + userApp + ", isRom="
				+ isRom + ", apkPackageName=" + apkPackageName + "]";
	}
	public AppInfo(Drawable apkIcon, String apkName, long apkSize,
			boolean userApp, boolean isRom, String apkPackageName) {
		super();
		this.apkIcon = apkIcon;
		this.apkName = apkName;
		this.apkSize = apkSize;
		this.userApp = userApp;
		this.isRom = isRom;
		this.apkPackageName = apkPackageName;
	}
	
	
}
