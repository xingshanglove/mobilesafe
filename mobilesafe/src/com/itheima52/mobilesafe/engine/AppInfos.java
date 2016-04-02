package com.itheima52.mobilesafe.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.itheima52.mobilesafe.entity.AppInfo;

public class AppInfos {
	public static List<AppInfo> getAppInfos(Context context){
		
		ArrayList<AppInfo> arrayList = new ArrayList<AppInfo>();
		
		PackageManager packageManager = context.getPackageManager();
		//��ȡ����װ��
		List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
		for(PackageInfo installedPackage:installedPackages){
			AppInfo info=new AppInfo();
			//Ӧ�ó���ͼ��
			Drawable loadIcon = installedPackage.applicationInfo.loadIcon(packageManager);
			info.setApkIcon(loadIcon);
			//������
			String loadLabel = installedPackage.applicationInfo.loadLabel(packageManager).toString();
			info.setApkName(loadLabel);
			//����
			String packageName = installedPackage.packageName;
			info.setApkPackageName(packageName);
			//��ȡ����Դ��·��
			String sourceDir = installedPackage.applicationInfo.sourceDir;
			File file = new File(sourceDir);
			//apk size
			long size=file.length();
			info.setApkSize(size);
			
			//data/data �������������·��
//			if(sourceDir.startsWith("/system")){
//				
//			}
			int flags=installedPackage.applicationInfo.flags;
			if((flags&ApplicationInfo.FLAG_SYSTEM)!=0){
				info.setUserApp(false);
			}else{
				info.setUserApp(true);
			}
			
			if((flags&ApplicationInfo.FLAG_EXTERNAL_STORAGE)!=0){
				info.setRom(false);
			}else{
				info.setRom(true);
			}
			arrayList.add(info);
		}
		return arrayList;
	}
}
