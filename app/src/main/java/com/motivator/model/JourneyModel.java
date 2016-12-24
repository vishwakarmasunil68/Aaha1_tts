package com.motivator.model;

import com.motivator.database.TableAttributes;

public class JourneyModel 
{
	String userName, journeyName;
	
	int total_events = 5;
	int total_events_achieved = 0;
	int step1_started = TableAttributes.STATUS_VALUE_0;
	int status_step2 = TableAttributes.STATUS_VALUE_0, status_step3 = TableAttributes.STATUS_VALUE_0;
	int status_step4 = TableAttributes.STATUS_VALUE_0 , status_step5 = TableAttributes.STATUS_VALUE_0 ;
	
	//int letterRead = TableAttributes.NOT_COMPLETED, GoalCompleted = TableAttributes.NOT_COMPLETED, ActionDone = TableAttributes.NOT_COMPLETED ;
	
	// get method
		
	public String getUserName() {
		return this.journeyName;
	}
	public String getJourneyName() {
		return this.journeyName;
	}
	
	public int getTotalEvents() {
		return this.total_events;
	}
	public int getEventsAchieved() {
		return this.total_events_achieved;
	}
	public int getStatusStep1() {
		return this.step1_started;
	}
	public int getStatusStep2() {
		return this.status_step2;
	}
	
	public int getStatusStep3() {
		return this.status_step3;
	}
	public int getStatusStep4() {
		return this.status_step4;
	}
	public int getStatusStep5() {
		return this.status_step5;
	}
	
//	public int isLetterRead() {
//		return this.letterRead;
//	}
//	
//	public int isGoalCompleted() {
//		return this.GoalCompleted;
//	}
//	public int isActionDone() {
//		return this.ActionDone;
//	}
	// set method
	
	public void setUserName(String user) {
		this.userName = user;
	}

	public void setJourneyName(String j_name) {
		this.journeyName = j_name;
	}

	public void setTotalEvents(int total) {
		this.total_events = total;
	}
	public void setEventsAchieved(int achieved) {
		this.total_events_achieved = achieved;
	}
	public void setStepStatus1(int step1) {
		this.step1_started = step1;
	}	
	public void setStepStatus2(int step2) {
		this.status_step2 = step2;
	}
	public void setStepStatus3(int step3) {
		this.status_step3 = step3;
	}
	public void setStepStatus4(int step4) {
		this.status_step4 = step4;
	}
	public void setStepStatus5(int step5) {
		this.status_step5 = step5;
	}
	
//	public void setLetterStatus(int status) {
//		this.letterRead = status;
//	}
//	public void setGoalStatus(int status) {
//		this.GoalCompleted = status;
//	}
//	public void setActionStatus(int status) {
//		this.ActionDone = status;
//	}
	
}
