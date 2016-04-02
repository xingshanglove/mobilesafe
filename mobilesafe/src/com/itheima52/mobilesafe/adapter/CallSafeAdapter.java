package com.itheima52.mobilesafe.adapter;

import java.util.List;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.entity.BlackNumberInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CallSafeAdapter extends BaseAdapter{
	List<BlackNumberInfo> infos;
	LayoutInflater inflater;
	
	setOnDeleteAction action;
	public interface setOnDeleteAction{
		public void onDelete(int id);
	}
	public void setOnDeleteAction(setOnDeleteAction action){
		this.action=action;
	}
	public CallSafeAdapter(Context context, List<BlackNumberInfo> infos) {
		this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.infos=infos;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.black_number_item, null);
			holder=new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_user.setText(infos.get(position).getName());
		holder.tv_number.setText(infos.get(position).getNumber());
		String type=infos.get(position).getMode();
		if(type.equals("0")){
			holder.tv_type.setText("拦截来电+短信");
		}else if(type.equals("1")){
			holder.tv_type.setText("拦截来电");
		}else if(type.equals("2")){
			holder.tv_type.setText("拦截短信");
		}
		holder.iv_clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				action.onDelete(position);
			}
		});
		return convertView;
	}
	class ViewHolder{
		TextView tv_user;
		TextView tv_number;
		ImageView iv_clear;
		TextView tv_type;
		public ViewHolder(View view) {
			tv_user=(TextView) view.findViewById(R.id.tv_user);
			tv_number=(TextView) view.findViewById(R.id.tv_number);
			iv_clear=(ImageView) view.findViewById(R.id.iv_clear);
			tv_type=(TextView) view.findViewById(R.id.tv_type);
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
