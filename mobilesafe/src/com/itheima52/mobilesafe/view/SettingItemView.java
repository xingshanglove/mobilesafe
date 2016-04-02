package com.itheima52.mobilesafe.view;

import com.itheima52.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {

	public TextView tv_title;
	public TextView tv_desc;
	public CheckBox chk_choice;
	private String namespace = "http://schemas.android.com/apk/res/com.itheima52.mobilesafe";
	String mTitle, mDescOn, mDescOff;

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTitle = attrs.getAttributeValue(namespace, "title");
		mDescOn = attrs.getAttributeValue(namespace, "desc_on");
		mDescOff = attrs.getAttributeValue(namespace, "desc_off");
		initView();
	}

	/**
	 * 初始化布局
	 */
	private void initView() {
		// 将自定义布局给当前的SettingItemView
		View.inflate(getContext(), R.layout.view_setting_item, this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
		chk_choice = (CheckBox) this.findViewById(R.id.chk_choice);
		
		tv_title.setText(mTitle);
		if(!isChecked())
			this.tv_desc.setText(mDescOff);
		else
			this.tv_desc.setText(mDescOn);
	}

	public void setTitle(String s) {
		this.tv_title.setText(s);
	}

	public void setDesc(String s) {
		this.tv_desc.setText(s);
	}

	public boolean isChecked() {
		return chk_choice.isChecked();
	}
	public void setChecked(boolean checked){
		this.chk_choice.setChecked(checked);
		if(checked){
			this.tv_desc.setText(mDescOn);
		}else{
			this.tv_desc.setText(mDescOff);
		}
	}
}
