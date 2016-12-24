package com.motivator.model;

public class CalendarPOJO {
	private String data_type;
	private String yesterday;
	private String today;
	private String last_week;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return data_type+",\t"+yesterday+",\t"+today+",\t"+last_week;
	}
	
	public String getData_type() {
		return data_type;
	}
	public String getYesterday() {
		return yesterday;
	}
	public String getToday() {
		return today;
	}
	public String getLast_week() {
		return last_week;
	}
	
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public void setYesterday(String yesterday) {
		this.yesterday = yesterday;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public void setLast_week(String last_week) {
		this.last_week = last_week;
	}
}
