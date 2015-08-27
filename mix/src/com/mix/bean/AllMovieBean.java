package com.mix.bean;

import java.io.Serializable;

public class AllMovieBean implements Serializable {
	private String movie_id;
	private String movie_name;
	private String movie_type;
	private String movie_release_date;
	private String movie_nation;
	private String movie_staring;
	private String movie_length;
	private String movie_picture;
	private float movie_score;
	private String movie_direction;
	private String movie_tags;
	private String movie_message;
	private int is_imax;//是否是IMAX类型
	private int is_new;//是否最新放映
	private String movies_wd;//影片关键词
	public String getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(String movie_id) {
		this.movie_id = movie_id;
	}
	public String getMovie_name() {
		return movie_name;
	}
	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}
	public String getMovie_type() {
		return movie_type;
	}
	public void setMovie_type(String movie_type) {
		this.movie_type = movie_type;
	}
	public String getMovie_release_date() {
		return movie_release_date;
	}
	public void setMovie_release_date(String movie_release_date) {
		this.movie_release_date = movie_release_date;
	}
	public String getMovie_nation() {
		return movie_nation;
	}
	public void setMovie_nation(String movie_nation) {
		this.movie_nation = movie_nation;
	}
	public String getMovie_staring() {
		return movie_staring;
	}
	public void setMovie_staring(String movie_staring) {
		this.movie_staring = movie_staring;
	}
	public String getMovie_length() {
		return movie_length;
	}
	public void setMovie_length(String movie_length) {
		this.movie_length = movie_length;
	}
	public String getMovie_picture() {
		return movie_picture;
	}
	public void setMovie_picture(String movie_picture) {
		this.movie_picture = movie_picture;
	}
	public float getMovie_score() {
		return movie_score;
	}
	public void setMovie_score(float movie_score) {
		this.movie_score = movie_score;
	}
	public String getMovie_direction() {
		return movie_direction;
	}
	public void setMovie_direction(String movie_direction) {
		this.movie_direction = movie_direction;
	}
	public String getMovie_tags() {
		return movie_tags;
	}
	public void setMovie_tags(String movie_tags) {
		this.movie_tags = movie_tags;
	}
	public String getMovie_message() {
		return movie_message;
	}
	public void setMovie_message(String movie_message) {
		this.movie_message = movie_message;
	}
	public int getIs_imax() {
		return is_imax;
	}
	public void setIs_imax(int is_imax) {
		this.is_imax = is_imax;
	}
	public int getIs_new() {
		return is_new;
	}
	public void setIs_new(int is_new) {
		this.is_new = is_new;
	}
	public String getMovies_wd() {
		return movies_wd;
	}
	public void setMovies_wd(String movies_wd) {
		this.movies_wd = movies_wd;
	}
	@Override
	public String toString() {
		return "AllMovieBean [movie_id=" + movie_id + ", movie_name="
				+ movie_name + ", movie_type=" + movie_type
				+ ", movie_release_date=" + movie_release_date
				+ ", movie_nation=" + movie_nation + ", movie_staring="
				+ movie_staring + ", movie_length=" + movie_length
				+ ", movie_picture=" + movie_picture + ", movie_score="
				+ movie_score + ", movie_direction=" + movie_direction
				+ ", movie_tags=" + movie_tags + ", movie_message="
				+ movie_message + ", is_imax=" + is_imax + ", is_new=" + is_new
				+ ", movies_wd=" + movies_wd + "]";
	}
	
}
