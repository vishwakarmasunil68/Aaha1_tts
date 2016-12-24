package com.motivator.model;

import com.motivator.database.TableAttributes;

public class ReminderDescModel 
{
	int reminder_id;
	String userName, reminder_time /*,reminder_day, snooze, ritual_type*/;
	
	int mon = TableAttributes.ON;
	int tue = TableAttributes.ON;
	int wed = TableAttributes.ON;
	int thu = TableAttributes.ON;
	int fri = TableAttributes.ON;
	int sat = TableAttributes.OFF;
	int sun = TableAttributes.OFF;
	
	int mon_stamp ;
	int tue_stamp;
	int wed_stamp ;
	int thu_stamp ;
	int fri_stamp ;
	int sat_stamp ;
	int sun_stamp ;
	
	
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
	
	/*public String getReminderDay() {
		return this.reminder_day;
	}*/

//	public String getSnoozeTime() {
//		return this.snooze;
//	}
//
//	public String getRitualType() {
//		return this.ritual_type;
//	}

	public int getReminder_mon() {
		return this.mon;
	}
	public int getReminder_tue() {
		return this.tue;
	}
	public int getReminder_wed() {
		return this.wed;
	}
	public int getReminder_thu() {
		return this.thu;
	}
	public int getReminder_fri() {
		return this.fri;
	}
	public int getReminder_sat() {
		return this.sat;
	}
	public int getReminder_sun() {
		return this.sun;
	}
	
	
	public int getStampMon() {
		return this.mon_stamp;
	}
	public int getStampTue() {
		return this.tue_stamp;
	}
	public int getStampWed() {
		return this.wed_stamp;
	}
	public int getStampThu() {
		return this.thu_stamp;
	}
	public int getStampFri() {
		return this.fri_stamp;
	}
	public int getStampSat() {
		return this.sat_stamp;
	}
	public int getStampSun() {
		return this.sun_stamp;
	}
	
	
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
	
	/*public void setReminderDay(String day) {
		this.reminder_day = day;
	}*/
	
//	public void setSnoozetime(String snooze) {
//		this.snooze = snooze;
//	}
//
//
//	public void setRitualType(String type) {
//		this.ritual_type = type;
//	}
	
	
	public void setReminder_mon(int value) {
		this.mon = value;
	}
	public void setReminder_tue(int value) {
		this.tue = value;
	}
	public void setReminder_wed(int value) {
		this.wed = value;
	}
	public void setReminder_thu(int value) {
		this.thu = value;
	}
	public void setReminder_fri(int value) {
		this.fri = value;
	}
	public void setReminder_sat(int value) {
		this.sat = value;
	}
	public void setReminder_sun(int value) {
		this.sun = value;
	}
	
	public void setStampMon(int value) {
		this.mon_stamp = value;
	}
	public void setStampTue(int value) {
		this.tue_stamp = value;
	}
	public void setStampWed(int value) {
		this.wed_stamp = value;
	}
	public void setStampThu(int value) {
		this.thu_stamp = value;
	}
	public void setStampFri(int value) {
		this.fri_stamp = value;
	}
	public void setStampSat(int value) {
		this.sat_stamp = value;
	}
	public void setStampSun(int value) {
		this.sun_stamp = value;
	}
}
