package com.mix.bean;

import java.util.List;

public class HotFilmBean {
	private String cityName;
	private float[] location = new float[2];
	private List<Object> movie;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public float[] getLocation() {
		return location;
	}
	public void setLocation(float[] location) {
		this.location = location;
	}
	public List<Object> getMovie() {
		return movie;
	}
	public void setMovie(List<Object> movie) {
		this.movie = movie;
	}
	
}
