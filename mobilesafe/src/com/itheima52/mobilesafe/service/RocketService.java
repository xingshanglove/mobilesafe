package com.itheima52.mobilesafe.service;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.activity.SmokeActivity;
import com.itheima52.mobilesafe.utils.Toasts;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class RocketService extends Service {
	private WindowManager.LayoutParams params;
	private WindowManager wm;
	private int screenWidth, screenHeight;
	private View view;
	private int startX;
	private int startY;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		// 屏幕宽高
		screenHeight = wm.getDefaultDisplay().getHeight();
		screenWidth = wm.getDefaultDisplay().getWidth();
		/**
		 * params参数
		 */
		params = new WindowManager.LayoutParams();
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		/**
		 * 参数设置
		 */
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		params.gravity = Gravity.LEFT+Gravity.TOP;
		params.setTitle("Toast");

		/**
		 * 初始化小火煎
		 */
		view = View.inflate(this, R.layout.view_rocket, null);
		ImageView img = (ImageView) view.findViewById(R.id.iv_rocket);
		img.setBackgroundResource(R.anim.anim_rocket);
		AnimationDrawable anim = (AnimationDrawable) img.getBackground();
		anim.start();

		wm.addView(view, params);

		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					int recentX = (int) event.getRawX();
					int recentY = (int) event.getRawY();
					// 计算偏移
					int dx = recentX - startX;
					int dy = recentY - startY;
					params.x += dx;
					params.y += dy;
					if (params.x < 0)
						params.x = 0;
					if (params.x > screenWidth - view.getWidth())
						params.x = screenWidth - view.getWidth();
					if (params.y < 0)
						params.y = 0;
					if (params.y > screenHeight - view.getHeight())
						params.y = screenHeight - view.getHeight();
					/**
					 * 更新视图
					 */
					wm.updateViewLayout(view, params);
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					if ((params.x > screenWidth/2-100) &&( params.x < screenWidth/2+100)
							&& params.y > (screenHeight - 150-view.getHeight())) {
						sendRocket();
						Intent intent=new Intent(RocketService.this,SmokeActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
					}else if(params.y<(screenHeight - 150-view.getHeight())){
						if(params.x+view.getWidth()/2<=screenWidth/2){
							params.x=0;
							wm.updateViewLayout(view, params);
						}else{
							params.x=screenWidth-view.getWidth();
							wm.updateViewLayout(view, params);
						}
					}
					break;
				}
				return false;
			}
		});
	}
	private void sendRocket() {
		// 火箭居中
		params.x = (screenWidth - view.getWidth()) / 2;
		wm.updateViewLayout(view, params);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 移动距离
				int dis = screenHeight ;
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int currentY = params.y - dis / 10;
					Message message = handler.obtainMessage();
					message.arg1 = currentY;
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int cuttentY = msg.arg1;
			params.y = cuttentY;
			wm.updateViewLayout(view, params);
		};
	};
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (wm != null && view != null) {
			wm.removeView(view);
			view = null;
		}
	}

}
