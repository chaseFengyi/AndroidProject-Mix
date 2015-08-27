package com.mix.bean;

public class ExpressageBean {
	
	private String acceptTime;
	private String remark;
	public String getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "ExpressageBean [acceptTime=" + acceptTime + ", remark="
				+ remark + "]";
	}
	
	
}
