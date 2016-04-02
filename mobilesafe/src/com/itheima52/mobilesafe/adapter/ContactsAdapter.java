package com.itheima52.mobilesafe.adapter;

import java.util.List;

import com.itheima52.mobilesafe.R;
import com.itheima52.mobilesafe.entity.PhoneInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactsAdapter extends BaseAdapter{
	Context context;
	List<PhoneInfo> infos;
	LayoutInflater inflater;
	public ContactsAdapter(Context context,List<PhoneInfo> infos) {
		this.context=context;
		this.infos=infos;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.contacts_item, null);
			holder=new Holder(convertView);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
		}
		holder.tv_name.setText(infos.get(position).getName());
		holder.tv_phone.setText(infos.get(position).getNumber());
		return convertView;
	}
	class Holder {
		TextView tv_name;
		TextView tv_phone;
		public Holder(View view) {
			tv_name=(TextView) view.findViewById(R.id.tv_name);
			tv_phone=(TextView) view.findViewById(R.id.tv_phone);
		}
	}
}
