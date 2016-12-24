package com.motivator.model;

import com.motivator.database.TableAttributes;

public class HabitModel 
{
	int habitId, habitPriority;
	String habit,habitDescription, habitBenefits, habitTime, habitCompletedON, habitAddedOn;
	String reminderDesc1, reminderDesc2, reminderDesc3, reminderDesc4, reminderDesc5, reminderDesc6;
	boolean isAdded = false;
	int isCompleted = TableAttributes.NOT_COMPLETED; 
	int remNextDesc = 0;

	@Override
	public String toString() {
		return "habitid:-"+habitId+"::"+"habit:-"+habit+"::"+"habit description:-"+habitDescription;
	}

	// get method
	public int getHabitId() {
		return this.habitId;
	}
	
	
	public String getHabit() {
		return this.habit;
	}

	public String getHabitDescription() {
		return this.habitDescription;
	}

	public String getHabitBenefits() {
		return this.habitBenefits;
	}
	public String getHabitTime() {
		return this.habitTime;
	}
	
	public String getReminderDesc1() {
		return this.reminderDesc1;
	}
	public String getReminderDesc2() {
		return this.reminderDesc2;
	}
	public String getReminderDesc3() {
		return this.reminderDesc3;
	}
	public String getReminderDesc4() {
		return this.reminderDesc4;
	}
	public String getReminderDesc5() {
		return this.reminderDesc5;
	}
	public String getReminderDesc6() {
		return this.reminderDesc6;
	}
	
	public int isHabitCompleted() {
		return this.isCompleted;
	}
	
	public boolean isHabitAdded() {
		return this.isAdded;
	}

	public int getHabitPriority() {
		return this.habitPriority;
	}

	public int getReminderNextDesc() {
		return this.remNextDesc;
	}
	
	public String getHabitCompletedON() {
		return this.habitCompletedON;
	}

	public String getHabitAddedON() {
		return this.habitAddedOn;
	}
	// set method
	
	public void setHabitId(int h_id) {
		this.habitId = h_id;
	}

	public void setHabit(String habit) {
		this.habit = habit;
	}
	
	public void setHabitDescription(String h_desc) {
		this.habitDescription = h_desc;
	}

	public void setHabitBenefits(String why) {
		this.habitBenefits = why;
	}
	public void setHabitTime(String time) {
		this.habitTime = time;
	}
	
	public void setReminderDesc1(String r_desc) {
		this.reminderDesc1 = r_desc;
	}
	public void setReminderDesc2(String r_desc) {
		this.reminderDesc2 = r_desc;
	}
	public void setReminderDesc3(String r_desc) {
		this.reminderDesc3 = r_desc;
	}
	public void setReminderDesc4(String r_desc) {
		this.reminderDesc4 = r_desc;
	}
	public void setReminderDesc5(String r_desc) {
		this.reminderDesc5 = r_desc;
	}
	public void setReminderDesc6(String r_desc) {
		this.reminderDesc6 = r_desc;
	}
	
	public void setStatus(int completed) {
		this.isCompleted = completed;
	}
	public void setSelection(boolean selected) {
		this.isAdded = selected;
	}
	
	public void setHabitPriority(int priority) {
		this.habitPriority = priority;
	}
	
	public void setRemNextDesc(int nextDesc) {
		this.remNextDesc = nextDesc;
	}
	
	public void setHabitCompletionTime(String time) {
		this.habitCompletedON = time;
	}
	
	public void setHabitAddedON(String time) {
		 this.habitAddedOn = time;
	}
}
