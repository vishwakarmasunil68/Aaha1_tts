package com.motivator.model;

import com.motivator.database.TableAttributes;

public class TimeLineModel 
{
	String userName;
	String ritualType, date_of_status;
	
	int total_habit, habit_completed;
	
	// get method
	public String getUserName() {
		return this.userName;
	}
	public String getRitualType() {
		return this.ritualType;
	}
	public String getDateOfStatus() {
		return this.date_of_status;
	}
	public int getTotalHabit() {
		return this.total_habit;
	}
	public int getHabitCompleted() {
		return this.habit_completed;
	}
	
	// set method
	public void setUserName(String user_name) {
		this.userName = user_name;
	}
	public void setRitualType(String type) {
		this.ritualType = type;
	}
	public void setDateOfStatus(String date) {
		this.date_of_status = date;
	}
	
	public void setTotalHabit(int total) {
		this.total_habit = total;
	}
	public void setHabitCompleted(int completed) {
		this.habit_completed = completed;
	}
}
