package com.itheima52.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima52.mobilesafe.R;

/**
 * �޸Ĺ�������ʾλ��
 * 
 * @author Kevin
 * 
 */
public class DragViewActivity extends Activity {

	private TextView tvTop;

	private ImageView ivDrag,iv_back;

	private int startX;
	private int startY;
	private SharedPreferences mPref;
	private int winWidth;

	long[] mHits = new long[2];// ���鳤�ȱ�ʾҪ����Ĵ���
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_view);
		winWidth=getWindowManager().getDefaultDisplay().getWidth();
		
		mPref = getSharedPreferences("config", MODE_PRIVATE);

		tvTop = (TextView) findViewById(R.id.tv_bottom);
		ivDrag = (ImageView) findViewById(R.id.iv_drag);
		
		iv_back=(ImageView) this.findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				DragViewActivity.this.finish();
			}
		});
		
		int lastX = mPref.getInt("lastX", 0);
		int lastY = mPref.getInt("lastY", 0);

		// onMeasure(����view), onLayout(����λ��), onDraw(����)
		// ivDrag.layout(lastX, lastY, lastX + ivDrag.getWidth(),
		// lastY + ivDrag.getHeight());//�������������,��Ϊ��û�в������,�Ͳ��ܰ���λ��

		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivDrag
				.getLayoutParams();// ��ȡ���ֶ���
		layoutParams.leftMargin = lastX;// ������߾�
		layoutParams.topMargin = lastY;// ����top�߾�

		ivDrag.setLayoutParams(layoutParams);// ��������λ��
		
		ivDrag.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
				mHits[mHits.length - 1] = SystemClock.uptimeMillis();// ������ʼ�����ʱ��
				if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
					ivDrag.layout(winWidth/2-ivDrag.getWidth()/2, ivDrag.getTop(),winWidth/2+ivDrag.getWidth()/2, ivDrag.getBottom());
				}
			}
		});

		// ���ô�������
		ivDrag.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					// ��ʼ���������
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					int endX = (int) event.getRawX();
					int endY = (int) event.getRawY();

					// �����ƶ�ƫ����
					int dx = endX - startX;
					int dy = endY - startY;

					// �����������¾���
					int l = ivDrag.getLeft() + dx;
					int r = ivDrag.getRight() + dx;

					int t = ivDrag.getTop() + dy;
					int b = ivDrag.getBottom() + dy;

					// ���½���
					ivDrag.layout(l, t, r, b);

					// ���³�ʼ���������
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					// ��¼�����
					Editor edit = mPref.edit();
					edit.putInt("lastX", ivDrag.getLeft());
					edit.putInt("lastY", ivDrag.getTop());
					edit.commit();
					break;

				default:
					break;
				}
				return false;
			}
		});
	}
}
