package com.mix.bean;

public class Weather_CurBean {
	private String prov;
	private String district;
	private String dateTime;
	private String city;
	private String temp;
	private String minTemp;
	private String maxTemp;
	private String weather;
	private String windDirection;
	private String windForce;
	private String humidity;//湿度
	private String img_1;//	白天所对应的天气图标 气象图标下载
	private String img_2;//夜间所对应的天气图标 气象图标下载
	private String refreshTime;
	public String getProv() {
		return prov;
	}
	public void setProv(String prov) {
		this.prov = prov;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(String minTemp) {
		this.minTemp = minTemp;
	}
	public String getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(String maxTemp) {
		this.maxTemp = maxTemp;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	public String getWindForce() {
		return windForce;
	}
	public void setWindForce(String windForce) {
		this.windForce = windForce;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getImg_1() {
		return img_1;
	}
	public void setImg_1(String img_1) {
		this.img_1 = img_1;
	}
	public String getImg_2() {
		return img_2;
	}
	public void setImg_2(String img_2) {
		this.img_2 = img_2;
	}
	public String getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}
	@Override
	public String toString() {
		return "Weather_CurBean [prov=" + prov + ", district=" + district
				+ ", dateTime=" + dateTime + ", city=" + city + ", temp="
				+ temp + ", minTemp=" + minTemp + ", maxTemp=" + maxTemp
				+ ", weather=" + weather + ", windDirection=" + windDirection
				+ ", windForce=" + windForce + ", humidity=" + humidity
				+ ", img_1=" + img_1 + ", img_2=" + img_2 + ", refreshTime="
				+ refreshTime + "]";
	}
	
}
