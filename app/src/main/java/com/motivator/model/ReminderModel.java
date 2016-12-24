package com.motivator.model;

import java.util.ArrayList;

import com.motivator.database.TableAttributes;

public class ReminderModel 
{
	int reminder_id;
	String userName, reminder_time, ritual_type,snooze;
	ReminderDescModel remDescObj;
	
//	int mon = TableAttributes.ON;
//	int tue = TableAttributes.ON;
//	int wed = TableAttributes.ON;
//	int thu = TableAttributes.ON;
//	int fri = TableAttributes.ON;
//	int sat = TableAttributes.OFF;
//	int sun = TableAttributes.OFF;
	
	
	// get method
	public int getReminderId() {
		return this.reminder_id;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public String getReminderTime() {
		return this.reminder_time;
	}

	public String getSnoozeTime() {
		return this.snooze;
	}

	public String getRitualType() {
		return this.ritual_type;
	}
	
	public ReminderDescModel getReminderDesc() {
		return this.remDescObj;
	}

//	public int getReminder_mon() {
//		return this.mon;
//	}
//	public int getReminder_tue() {
//		return this.tue;
//	}
//	public int getReminder_wed() {
//		return this.wed;
//	}
//	public int getReminder_thu() {
//		return this.thu;
//	}
//	public int getReminder_fri() {
//		return this.fri;
//	}
//	public int getReminder_sat() {
//		return this.sat;
//	}
//	public int getReminder_sun() {
//		return this.sun;
//	}
	
// set method	
	public void setReminderId(int r_id) {
		this.reminder_id = r_id;
	}
	public void setUserName(String user_name) {
		this.userName = user_name;
	}
	
	public void setRemindertime(String time) {
		this.reminder_time = time;
	}
	
	public void setSnoozetime(String snooze) {
		this.snooze = snooze;
	}


	public void setRitualType(String type) {
		this.ritual_type = type;
	}
	
	public void setReminderDesc(ReminderDescModel remDesc) {
		this.remDescObj = remDesc;
	}
	
	
//	public void setReminder_mon(int value) {
//		this.mon = value;
//	}
//	public void setReminder_tue(int value) {
//		this.tue = value;
//	}
//	public void setReminder_wed(int value) {
//		this.wed = value;
//	}
//	public void setReminder_thu(int value) {
//		this.thu = value;
//	}
//	public void setReminder_fri(int value) {
//		this.fri = value;
//	}
//	public void setReminder_sat(int value) {
//		this.sat = value;
//	}
//	public void setReminder_sun(int value) {
//		this.sun = value;
//	}
}
