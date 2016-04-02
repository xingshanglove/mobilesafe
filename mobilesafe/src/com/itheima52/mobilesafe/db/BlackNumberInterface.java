package com.itheima52.mobilesafe.db;

import java.util.List;

import com.itheima52.mobilesafe.entity.BlackNumberInfo;

public interface BlackNumberInterface {
	public boolean add(String number,String mode,String name);
	public boolean delete(String number);
	public boolean change(String number,String mode);
	public String query(String number);
	public List<BlackNumberInfo> findAll();
}
