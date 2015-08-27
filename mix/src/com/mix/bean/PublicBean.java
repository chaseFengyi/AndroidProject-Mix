package com.mix.bean;

import java.util.List;

public class PublicBean {
	private int status;
	private String message;
	private List<Object> list;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PublicBean [status=" + status + ", message=" + message
				+ ", list=" + list + "]";
	}
	
}
