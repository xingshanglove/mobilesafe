package com.itheima52.mobilesafe.entity;

public class VersionInfo {
	String versionName;
	int versionCode;
	String description;
	String apkUrl;
	public VersionInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public VersionInfo(String versionName, int versionCode, String description,
			String apkUrl) {
		super();
		this.versionName = versionName;
		this.versionCode = versionCode;
		this.description = description;
		this.apkUrl = apkUrl;
	}
	


	@Override
	public String toString() {
		return "VersionInfo [versionName=" + versionName + ", versionCode="
				+ versionCode + ", description=" + description + ", apkUrl="
				+ apkUrl + "]";
	}

	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	
}
