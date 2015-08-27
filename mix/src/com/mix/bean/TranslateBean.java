package com.mix.bean;

public class TranslateBean {
	private String dst;
	private String src;
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	@Override
	public String toString() {
		return "TranslateBean [dst=" + dst + ", src=" + src + "]";
	}
	
}
