package com.itheima52.mobilesafe.adapter;


import java.util.List;
import java.util.Map;

import com.itheima52.mobilesafe.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ServiceCast") 
public class FunctionsListAdapter extends BaseAdapter{

	Context context;
	LayoutInflater inflater;
	 List<Map<String,Object>> datas;
	public FunctionsListAdapter(Context context, List<Map<String,Object>> datas) {
		this.context=context;
		this.datas=datas;
		this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
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
		ViewHolder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.home_list_item,null);
			holder=new ViewHolder(convertView);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.img.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),(Integer) datas.get(position).get("img")));
		holder.text.setText((String) datas.get(position).get("text"));
		
		return convertView;
	}
	class ViewHolder{
		ImageView img;
		TextView text;
		public ViewHolder(View view) {
			img=(ImageView) view.findViewById(R.id.item_iv);
			text=(TextView) view.findViewById(R.id.item_tv);
		}
	}
}
