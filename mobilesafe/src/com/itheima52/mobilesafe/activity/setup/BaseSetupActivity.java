package com.itheima52.mobilesafe.activity.setup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public abstract class BaseSetupActivity extends Activity implements GestureDetector.OnGestureListener{
	private GestureDetector detector;
	public SharedPreferences spf;
	public abstract  void showPrevious();
	public abstract void showNext();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		detector=new GestureDetector(this, this);
		spf=getSharedPreferences("config", MODE_PRIVATE);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return detector.onTouchEvent(event);
	}
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int disX=(int)( e1.getX()-e2.getX());
		int disY=(int) (e1.getY()-e2.getY());
		if(Math.abs(disX)>Math.abs(disY)){
			if(disX<0){
				//前一个页面
				showPrevious();
			}else{
				//后一个页面
				showNext();
			}
		}
		return false;
	}
}
