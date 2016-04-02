package com.itheima52.mobilesafe.entity;

import android.graphics.drawable.Drawable;

public class CacheInfo {
	Drawable icon;
	String appName;
	long cacheSize;

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(long cacheSize) {
		this.cacheSize = cacheSize;
	}

	public CacheInfo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CacheInfo [icon=" + icon + ", appName=" + appName
				+ ", cacheSize=" + cacheSize + "]";
	}

	public CacheInfo(Drawable icon, String appName, long cacheSize) {
		super();
		this.icon = icon;
		this.appName = appName;
		this.cacheSize = cacheSize;
	}

}
