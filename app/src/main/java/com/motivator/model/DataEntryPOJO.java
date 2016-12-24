package com.motivator.model;

public class DataEntryPOJO {
	private int activity_minutes;
	private int glucose_no;
	private String glucose_time;
	private String food_name;
	private int food_quantity;
	private String medicine_name;
	private int medicine_quantity;
	private String medicine_type;
	private String time;
	private String date;
	private String ritual;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return activity_minutes+",\t"+glucose_no+",\t"+glucose_time+",\t"+food_name+",\t"+food_quantity+",\t"+medicine_name
				+",\t"+medicine_quantity+",\t"+medicine_type+",\t"+time+",\t"+date+",\t"+ritual;
	}
	
	public void setRitual(String ritual) {
		this.ritual = ritual;
	}
	
	public String getRitual() {
		return ritual;
	}
	
	public String getDate() {
		return date;
	}
	public String getTime() {
		return time;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public int getActivity_minutes() {
		return activity_minutes;
	}
	public String getFood_name() {
		return food_name;
	}
	public int getGlucose_no() {
		return glucose_no;
	}
	public String getGlucose_time() {
		return glucose_time;
	}
	public String getMedicine_name() {
		return medicine_name;
	}
	public int getMedicine_quantity() {
		return medicine_quantity;
	}
	public String getMedicine_type() {
		return medicine_type;
	}
	public int getFood_quantity() {
		return food_quantity;
	}
	public void setActivity_minutes(int activity_minutes) {
		this.activity_minutes = activity_minutes;
	}
	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}
	public void setGlucose_no(int glucose_no) {
		this.glucose_no = glucose_no;
	}
	public void setGlucose_time(String glucose_time) {
		this.glucose_time = glucose_time;
	}
	public void setMedicine_name(String medicine_name) {
		this.medicine_name = medicine_name;
	}
	public void setMedicine_quantity(int medicine_quantity) {
		this.medicine_quantity = medicine_quantity;
	}
	public void setMedicine_type(String medicine_type) {
		this.medicine_type = medicine_type;
	}
	public void setFood_quantity(int food_quantity) {
		this.food_quantity = food_quantity;
	}
}
