package com.mix.bean;

public class AllExpressageBean {
	private String name;
	private String com;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	@Override
	public String toString() {
		return "AllExpressage [name=" + name + ", com=" + com + "]";
	}
	
}
