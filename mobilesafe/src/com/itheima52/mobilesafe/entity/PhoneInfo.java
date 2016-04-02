package com.itheima52.mobilesafe.entity;

import java.io.Serializable;

public class PhoneInfo implements Serializable{
	private String name;
	private String number;
	
	public PhoneInfo(String name,String number) {
		setNumber(number);
		setName(name);
	}
	public PhoneInfo() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	

}
