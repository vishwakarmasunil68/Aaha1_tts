package com.motivator.model;

import com.motivator.database.TableAttributes;

public class UserModel 
{
	String userId,userName,userEmailId, userLoginType,userImage;
	String wakeUpTime, lunchTime, sleepTime;
	
	//int notification_style = TableAttributes.SIMPLE_SCREEN;
	//int urgency_swipe = TableAttributes.OFF, announce_first = TableAttributes.OFF , ring_in_silent = TableAttributes.OFF ;
	
	// get method
	public String getUserId() {
		return this.userId;
	}
	
	public String getUserName() {
		return this.userName;
	}

	public String getEmailId() {
		return this.userEmailId;
	}

	public String getUserType() {
		return this.userLoginType;
	}

	public String getUserImage() {
		return this.userImage;
	}
	
	public String getWakeUpTime() {
		return this.wakeUpTime;
	}
	public String getLunchTime() {
		return this.lunchTime;
	}
	public String getSleepTime() {
		return this.sleepTime;
	}
	
	/*public int getNotificationStyle() {
		return this.notification_style;
	}
	public int getUrgencySwipe() {
		return this.urgency_swipe;
	}
	public int getAnnounceFirst() {
		return this.announce_first;
	}
	public int getRingInSilent() {
		return this.ring_in_silent;
	}*/
	// set method
	
	public void setUserId(String user_id) {
		this.userId = user_id;
	}

	public void setUserName(String user_name) {
		this.userName = user_name;
	}

	
	public void setUserEmailId(String email_id) {
		this.userEmailId = email_id;
	}


	public void setLoginType(String type) {
		this.userLoginType = type;
	}
	
	public void setUserImage(String img) {
		this.userImage = img;
	}
	
	public void setWakeUpTime(String wake_up) {
		this.wakeUpTime = wake_up;
	}
	public void setLunchTime(String lunch) {
		this.lunchTime = lunch;
	}
	public void setSleepTime(String sleep) {
		this.sleepTime = sleep;
	}
	
	
	/*public void setNotificationStyle(int notification) {
		this.notification_style = notification;
	}
	public void setUrgencySwipe(int urgency) {
		this.urgency_swipe = urgency;
	}
	public void setAnnounceFirst(int announce) {
		this.announce_first = announce;
	}
	public void setRingInSilent(int ring) {
		this.ring_in_silent = ring;
	}*/
}
