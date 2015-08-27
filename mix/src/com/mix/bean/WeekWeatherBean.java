package com.mix.bean;

import java.util.Arrays;

public class WeekWeatherBean {
	private String city;
	private String date_1[];
	private String temp_1[];
	private String weather_1[];
	private String wind_1[];
	private String fl_1[];
	private int img_1[];
	private int img_2[];
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String[] getDate_1() {
		return date_1;
	}
	public void setDate_1(String[] date_1) {
		this.date_1 = date_1;
	}
	public String[] getTemp_1() {
		return temp_1;
	}
	public void setTemp_1(String[] temp_1) {
		this.temp_1 = temp_1;
	}
	public String[] getWeather_1() {
		return weather_1;
	}
	public void setWeather_1(String[] weather_1) {
		this.weather_1 = weather_1;
	}
	public String[] getWind_1() {
		return wind_1;
	}
	public void setWind_1(String[] wind_1) {
		this.wind_1 = wind_1;
	}
	public String[] getFl_1() {
		return fl_1;
	}
	public void setFl_1(String[] fl_1) {
		this.fl_1 = fl_1;
	}
	public int[] getImg_1() {
		return img_1;
	}
	public void setImg_1(int[] img_1) {
		this.img_1 = img_1;
	}
	public int[] getImg_2() {
		return img_2;
	}
	public void setImg_2(int[] img_2) {
		this.img_2 = img_2;
	}
	@Override
	public String toString() {
		return "WeekWeatherBean [city=" + city + ", date_1="
				+ Arrays.toString(date_1) + ", temp_1="
				+ Arrays.toString(temp_1) + ", weather_1="
				+ Arrays.toString(weather_1) + ", wind_1="
				+ Arrays.toString(wind_1) + ", fl_1=" + Arrays.toString(fl_1)
				+ ", img_1=" + Arrays.toString(img_1) + ", img_2="
				+ Arrays.toString(img_2) + "]";
	}
}
