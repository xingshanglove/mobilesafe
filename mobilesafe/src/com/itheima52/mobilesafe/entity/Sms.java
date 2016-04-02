package com.itheima52.mobilesafe.entity;

public class Sms {
	String address;
	String date;
	String type;
	String body;
	public Sms() {
		// TODO Auto-generated constructor stub
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "Sms [address=" + address + ", date=" + date + ", type=" + type
				+ ", body=" + body + "]";
	}
	
	
}
