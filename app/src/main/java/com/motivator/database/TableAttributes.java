package com.motivator.database;

public class TableAttributes {
	
	// Habit Id 
	public static final int drinkWaterId = 5;//habit Id of Drink Water in database
	public static final int greatBreakFastID = 18;//habit Id of Have aGrreat Breakfast in database
	public static final int danceYourWayID = 25;//habit Id of Dance in database
	
	//ForgoldenChallengeID -Combination of habitId of drink water, breakfast and dance. It shouldnot matched withe any habitid in database
	public static final int goldenChallengeID = 51825;
	public static final int SecretLetterID = 14; //habit Id of Celebration in database
	
	// TABLE NAME
	public static final String TABLE_USER = "TABLE_USER";
	//public static final String TABLE_USER_INFO = "TABLE_USER_INFO";
	public static final String TABLE_USER_RITUALS = "TABLE_USER_RITUALS";
	public static final String TABLE_HABIT = "TABLE_HABIT";
	public static final String TABLE_USER_HABIT = "TABLE_USER_HABIT";
	public static final String TABLE_REMINDER = "TABLE_REMINDER";
	public static final String TABLE_REMINDER_DESC = "TABLE_REMINDER_DESC";
	
	public static final String TABLE_JOURNEY = "TABLE_JOURNEY";
	public static final String TABLE_JOURNEY_HABIT = "TABLE_JOURNEY_HABIT";
	
	public static final String TABLE_HBIT_TIMELINE = "TABLE_HBIT_TIMELINE";
	public static final String TABLE_TIMELINE = "TABLE_TIMELINE";

	//COLOUMN NAME
	public static final String USER_ID = "USER_ID";
	public static final String USER_NAME = "USER_NAME";
	public static final String USER_EMAIL ="USER_EMAIL";
	//public static final String IS_RITUAL_ADDED ="IS_RITUAL_ADDED";
	
	
	public static final String RITUAL_IMG ="RITUAL_IMG";
	public static final String RITUAL_NAME ="RITUAL_NAME";
	public static final String RITUAL_TIME ="RITUAL_TIME";
	public static final String RITUAL_DISPLAY_NAME ="RITUAL_DISPLAY_NAME";
	public static final String RITUAL_REMINDER_ID ="RITUAL_REMINDER_ID";
	
	public static final String NOTIFICATION_STYLE ="NOTIFICATION_STYLE";
	//COLOUMN VALUE FOR NOTIFICATION STYLE
	public static final int FULL_SCREEN = 1;
	public static final int SIMPLE_SCREEN = 0;
	
	//COLOUMN NAME
	public static final String URGENCY_SWIPE = "URGENCY_SWIPE";
	public static final String ANNOUNCE_FIRST = "ANNOUNCE_FIRST";
	public static final String RING_IN_SILENT = "RING_IN_SILENT";
	
	
	//COLOUMN NAME
	public static final String JOURNEY_NAME = "JOURNEY_NAME";
	public static final String TOTAL_EVENTS ="TOTAL_EVENTS";
	public static final String TOTAL_EVENTS_ACHIEVED = "TOTAL_EVENTS_ACHIEVED";
	public static final String STATUS_STEP1 ="STATUS_STEP1";
	public static final String STATUS_STEP2 ="STATUS_STEP2";
	public static final String STATUS_STEP3 ="STATUS_STEP3";
	public static final String STATUS_STEP4 = "STATUS_STEP4";
	public static final String STATUS_STEP5 = "STATUS_STEP5";
	public static final String LETTER_READ ="LETTER_READ";
	public static final String CHALLENGE_ACCEPTED ="CHALLENGE_ACCEPTED";
	public static final String GOAL_COMPLETED ="GOAL_COMPLETED";
	public static final String ACTION_DONE ="ACTION_DONE";
	public static final String MOTIVATION ="MOTIVATION";
	public static final String GOLDEN_CHALLENGE ="GOLDEN_CHALLENGE";
	public static final String GOLDEN_GOAL_STATUS ="GOLDEN_STATUS";
	
	
	//COLOUMN NAME
	public static final String H_ID  ="H_ID";
	public static final String HABIT  ="HABIT";
	public static final String DESCRIPTION ="DESCRIPTION";
	public static final String BENEFITS ="BENEFITS";
	public static final String HABIT_TIME ="HABIT_TIME";
	public static final String REMINDER_DESC1 ="REMINDER_DESC1";
	public static final String REMINDER_DESC2 ="REMINDER_DESC2";
	public static final String REMINDER_DESC3 ="REMINDER_DESC3";
	public static final String REMINDER_DESC4 ="REMINDER_DESC4";
	public static final String REMINDER_DESC5 ="REMINDER_DESC5";
	public static final String REMINDER_DESC6 ="REMINDER_DESC6";
	
	
	public static final String RITUAL_TYPE ="RITUAL_TYPE";
	public static final String USER_HABIT_TIME ="USER_HABIT_TIME";
	public static final String IS_HABIT_COMPLETED ="IS_HABIT_COMPLETED";
	public static final String HABIT_PRORIOTY = "HABIT_PRORIOTY";
	public static final String HABIT_COMPLETED_ON ="HABIT_COMPLETED_ON";
	public static final String REMINDER_NEXT_DESC = "REMINDER_NEXT_DESC";
	public static final String HABIT_ADDED_ON = "HABIT_ADDED_ON";
	
	public static final String INDEX_ORDER ="INDEX_ORDER";
	public static final String COMPLETION_DATE ="COMPLETION_DATE";
	public static final String DATE_OF_STATUS = "DATE_OF_STATUS";
	public static final String TOTAL_HABIT = "TOTAL_HABIT";
	public static final String HABIT_COMPLETED = "HABIT_COMPLETED";
	//public static final String SUCCESS_RATE ="SUCCESS_RATE";
	
	
	
	//COLOUMN VALUE
	public static final String custom_habit  ="custom_habit";
	public static final int COMPLETED = 1;
	public static final int NOT_COMPLETED = 0;
	
	// Journey Step Status
	public static final int STATUS_VALUE_0 = 0;
	public static final int STATUS_VALUE_1 = 1;
	public static final int STATUS_VALUE_2 = 2;
	
	//Journey Events Status
	public static final int LETTER_Unread = 0;
	public static final int LETTER_Opened = 1;
	public static final int LETTER_COMPLETED = 2;
	
	public static final int CHALLENCE_COMPLETED = 3;
	
	
	//COLOUMN NAME
	public static final String REMINDER_ID ="REMINDER_ID";
	public static final String REMINDER_TIME ="REMINDER_TIME";
	public static final String SNOOZE_TIME ="SNOOZE_TIME";
	
	public static final String REMINDER_DAY = "REMINDER_DAY";
	public static final String REMINDER_ON_OFF = "REMINDER_ON_OFF";
	public static final String REMINDER_STAMP = "REMINDER_STAMP";
	
	//COLOUMN VALUE
	public static final String REMINDER_SUN ="REMINDER_SUN";
	public static final String REMINDER_MON ="REMINDER_MON";
	public static final String REMINDER_TUE ="REMINDER_TUE";
	public static final String REMINDER_WED ="REMINDER_WED";
	public static final String REMINDER_THU = "REMINDER_THU";
	public static final String REMINDER_FRI = "REMINDER_FRI";
	public static final String REMINDER_SAT = "REMINDER_SAT";
	
	//COLOUMN VALUE
			public static final int ON = 1;
			public static final int OFF = 0;
	
	//QUERY
	public static final String GET_USER = "SELECT * FROM "+TABLE_USER;
	//public static final String GET_JOURNEY = "SELECT * FROM "+ TABLE_USER_INFO;
	
	public static final String GET_ALL_HABIT = "SELECT * FROM "+TABLE_HABIT;
	
	public static final String GET_USER_REMINDER = "SELECT * FROM "+TABLE_REMINDER;
	
	public static final String GET_USER_HABIT = "SELECT A.H_ID , A.HABIT,A.DESCRIPTION, A.BENEFITS," +
			" A.REMINDER_DESC1, A.REMINDER_DESC2, A.REMINDER_DESC3, A.REMINDER_DESC4, A.REMINDER_DESC5, A.REMINDER_DESC6," +
			" B.USER_HABIT_TIME, B.IS_HABIT_COMPLETED, B.HABIT_PRORIOTY, B.HABIT_COMPLETED_ON, B.REMINDER_NEXT_DESC, " +
			"B.HABIT_ADDED_ON from TABLE_HABIT  A  join TABLE_USER_HABIT B on A.H_ID =B.H_ID where B.USER_NAME= ";

	
//	public static final String GET_ALL_HABIT_UNION_USER = "SELECT Distinct A.H_ID , A.HABIT,A.DESCRIPTION, A.BENEFITS,  A.RITUAL_TYPE, B.USER_NAME " +
//														"from TABLE_HABIT  A  Left join TABLE_USER_HABIT B on A.H_ID=B.H_ID";

	
	
}
