package com.motivator.model;

import com.motivator.database.TableAttributes;

public class UserRitualModel 
{
	String userName, ritualName, ritualTime, ritualDisplayName;
	int ritualImg = 1, ritualRemId;
	
	int notification_style = TableAttributes.SIMPLE_SCREEN;
	int urgency_swipe = TableAttributes.OFF, announce_first = TableAttributes.OFF , ring_in_silent = TableAttributes.OFF ;
	
	// get method
	public String getUserName() {
		return this.userName;
	}
	
	public int getRitualImg() {
		return this.ritualImg;
	}
	
	public int getRitualRemID() {
		return this.ritualRemId;
	}
	public String getRitualName() {
		return this.ritualName;
	}
	public String getRitualDisplayName() {
		return this.ritualDisplayName;
	}
	public String getRitualTime() {
		return this.ritualTime;
	}
	
	public int getNotificationStyle() {
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
	}
	// set method
	
	public void setUserName(String user_name) {
		this.userName = user_name;
	}


	
	public void setRitualImg(int r_img) {
		this.ritualImg = r_img;
	}
	
	public void setRitualRemID(int r_id) {
		this.ritualRemId = r_id;
	}
	public void setRitualName(String name) {
		this.ritualName = name;
	}
	public void setRitualDisplayName(String name) {
		this.ritualDisplayName = name;
	}
	public void setRitualTime(String time) {
		this.ritualTime = time;
	}
	
	
	public void setNotificationStyle(int notification) {
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
	}

	@Override
	public String toString() {
		return "UserRitualModel{" +
				"userName='" + userName + '\'' +
				", ritualName='" + ritualName + '\'' +
				", ritualTime='" + ritualTime + '\'' +
				", ritualDisplayName='" + ritualDisplayName + '\'' +
				", ritualImg=" + ritualImg +
				", ritualRemId=" + ritualRemId +
				", notification_style=" + notification_style +
				", urgency_swipe=" + urgency_swipe +
				", announce_first=" + announce_first +
				", ring_in_silent=" + ring_in_silent +
				'}';
	}
}
