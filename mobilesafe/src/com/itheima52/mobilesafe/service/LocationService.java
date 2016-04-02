package com.itheima52.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.location.LocationListener;

public class LocationService extends Service {

	private LocationManager lm;
	SharedPreferences sharedPreferences;
	private MyListener myListener;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);

		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(true);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String bestProvider = lm.getBestProvider(criteria, true);
		myListener = new MyListener();

		lm.requestLocationUpdates(bestProvider, 0, 0, myListener);
	}

	class MyListener implements LocationListener {

		// 位置发生变化
		@Override
		public void onLocationChanged(Location location) {
			System.out.println(location.getLatitude() + "/"
					+ location.getLongitude() + "/" + location.getAccuracy());
			sharedPreferences
					.edit()
					.putString(
							"location",
							"j:" + location.getLongitude() + ";w:"
									+ location.getLatitude()).commit();
			//停止service
			stopSelf();
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}
		// 用户关掉gps
		@Override
		public void onProviderEnabled(String provider) {

		}
		// 用户打开gps
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		lm.removeUpdates(myListener);
	}
}
