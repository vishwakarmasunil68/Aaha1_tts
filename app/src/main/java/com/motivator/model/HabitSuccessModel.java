package com.motivator.model;

import com.motivator.database.TableAttributes;

public class HabitSuccessModel 
{
	int habitId;
	String habit;
	double success;

	// get method
	public int getHabitId() {
		return this.habitId;
	}
	
	
	public String getHabit() {
		return this.habit;
	}

	
	public double getSuccessRate() {
		return this.success;
	}
	

	// set method
	
	public void setHabitId(int h_id) {
		this.habitId = h_id;
	}

	public void setHabit(String habit) {
		this.habit = habit;
	}
	
	
	public void setSuccessRate(double success) {
		this.success = success;
	}
}
