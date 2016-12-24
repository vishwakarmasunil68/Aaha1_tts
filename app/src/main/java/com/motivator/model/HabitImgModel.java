package com.motivator.model;

import com.motivator.database.TableAttributes;

public class HabitImgModel {
	
	
	/*private static HabitImgModel instance;
	
	public static synchronized HabitImgModel getInstance()
	{
		if(instance == null)
		{
			instance = new HabitImgModel();
		}
		return instance;
	}*/
	
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String url;

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	private String referral_destinations;

	public String getRefDestination() {
		return this.referral_destinations;
	}

	public void setRefDestination(String referral_destinations) {
		this.referral_destinations = referral_destinations;
	}
}
