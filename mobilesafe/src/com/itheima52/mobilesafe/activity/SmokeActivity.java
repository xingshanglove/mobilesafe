package com.itheima52.mobilesafe.activity;

import com.itheima52.mobilesafe.R;

import android.app.Activity;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
/**
 * ÑÌÎíµÄ±³¾°
 * @author root
 *
 */
public class SmokeActivity extends Activity{
	ImageView iv_top,iv_bottom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT>VERSION_CODES.KITKAT){
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		setContentView(R.layout.activity_smoke);
	
		iv_top=(ImageView) this.findViewById(R.id.iv_top);
		iv_bottom=(ImageView) this.findViewById(R.id.iv_bottom);
		
		AlphaAnimation animation=new AlphaAnimation(0, 1);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		
		iv_top.startAnimation(animation);
		iv_bottom.startAnimation(animation);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				SmokeActivity.this.finish();
			}
		},1000);
	}
}
