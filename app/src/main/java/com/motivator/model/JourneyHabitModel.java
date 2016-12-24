package com.motivator.model;

import com.motivator.database.TableAttributes;

public class JourneyHabitModel 
{
	String userName, journeyName;
	
	int h_id;
	int letterRead = TableAttributes.LETTER_Unread, GoalCompleted = TableAttributes.STATUS_VALUE_0;
	int challengeAccepted = TableAttributes.STATUS_VALUE_0, ActionDone = TableAttributes.STATUS_VALUE_0 ;
	int motivation = TableAttributes.STATUS_VALUE_0;
	int goldenChallenge = TableAttributes.STATUS_VALUE_0, goldenGoalStatus = TableAttributes.STATUS_VALUE_0 ;
	// get method
		
	public String getUserName() {
		return this.journeyName;
	}
	public String getJourneyName() {
		return this.journeyName;
	}
	
	public int getHabitId() {
		return this.h_id;
	}
	
	public int isLetterRead() {
		return this.letterRead;
	}
	
	public int isChallengeAccepted() {
		return this.challengeAccepted;
	}
	public int getGoalStatus() {
		return this.GoalCompleted;
	}
	public int getActionStatus() {
		return this.ActionDone;
	}
	
	public int getMotivationStatus() {
		return this.motivation;
	}
	public int getGoldenChalengeStatus() {
		return this.goldenChallenge;
	}
	public int getGoldenGoalStatus() {
		return this.goldenGoalStatus;
	}
	// set method
	
	public void setUserName(String user) {
		this.userName = user;
	}

	public void setJourneyName(String j_name) {
		this.journeyName = j_name;
	}

	public void setHabitId(int hId) {
		this.h_id = hId;
	}
	public void setLetterStatus(int status) {
		this.letterRead = status;
	}
	public void setChallengeStatus(int status) {
		this.challengeAccepted = status;
	}
	public void setGoalStatus(int status) {
		this.GoalCompleted = status;
	}
	public void setActionStatus(int status) {
		this.ActionDone = status;
	}
	
	public void setMotivationStatus(int status) {
		this.motivation = status;
	}
	public void setGoldenChallenge(int status) {
		this.goldenChallenge = status;
	}
	public void setGoldenStatus(int status) {
		this.goldenGoalStatus = status;
	}
	
}
