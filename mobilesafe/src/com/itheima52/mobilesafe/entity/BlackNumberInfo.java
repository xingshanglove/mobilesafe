package com.itheima52.mobilesafe.entity;
/**
 * 
 * @author root
 *
 */
public class BlackNumberInfo {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String number;
	/**
	 * 0 。全部拦截
	 * 1. 电话拦截
	 * 2.短信拦截
	 */
	private String mode;
	public BlackNumberInfo() {
	}
	@Override
	public String toString() {
		return "BlackNumberInfo [number=" + number + ", mode=" + mode + "]";
	}

	public BlackNumberInfo(String number, String mode) {
		super();
		this.number = number;
		this.mode = mode;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
