package com.mix.bean;

public class Weather_CityBean {
	private int id;
	private String province;
	private String city;
	private String district;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	@Override
	public String toString() {
		return "WeatherBean [id=" + id + ", province=" + province + ", city="
				+ city + ", district=" + district + "]";
	}
	
}
