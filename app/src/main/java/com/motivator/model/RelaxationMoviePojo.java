package com.motivator.model;

import java.io.Serializable;

public class RelaxationMoviePojo implements Serializable{
	int image;
	String movie_name;
	String movie_url;


	public String getMovie_name() {
		return movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getMovie_url() {
		return movie_url;
	}

	public void setMovie_url(String movie_url) {
		this.movie_url = movie_url;
	}
}
