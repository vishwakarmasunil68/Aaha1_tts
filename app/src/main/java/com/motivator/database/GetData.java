package com.motivator.database;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.model.HabitModel;
import com.motivator.model.JourneyHabitModel;
import com.motivator.model.JourneyModel;
import com.motivator.model.ReminderDescModel;
import com.motivator.model.ReminderModel;
import com.motivator.model.TimeLineModel;
import com.motivator.model.UserRitualModel;
import com.motivator.wecareyou.MyHabits;

public class GetData 
{
	SQLiteDatabase _database;
	
	DatabaseHelper databaseHelper;
	Context mContext;
	
	public GetData(Context context) 
	{
		mContext = context;
		databaseHelper = DatabaseHelper.getInstance(context);
	}
	
	/*private static GetData instance;
	public static synchronized GetData getInstance(Context context)
	{
		if(instance == null)
		{
			instance = new GetData(context);
		}
		return instance;
	}*/
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public ArrayList<UserRitualModel> getUserRituals(String userName)
	{
		ArrayList<UserRitualModel> userRituals = new ArrayList<UserRitualModel>();
		String query = "Select * from "+ TableAttributes.TABLE_USER_RITUALS + " where "+ TableAttributes.USER_NAME+ " = '"+userName+"'" ;
		
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					UserRitualModel userRitualObj = new UserRitualModel();
					userRitualObj.setUserName(c.getString(0));
					userRitualObj.setRitualImg(c.getInt(1));
					userRitualObj.setRitualName(c.getString(2));
					userRitualObj.setRitualTime(c.getString(3));
					
					userRitualObj.setNotificationStyle(c.getInt(4));
					userRitualObj.setUrgencySwipe(c.getInt(5));
					userRitualObj.setAnnounceFirst(c.getInt(6));
					userRitualObj.setRingInSilent(c.getInt(7));
					userRitualObj.setRitualDisplayName(c.getString(8));
					userRitualObj.setRitualRemID(c.getInt(9));
					userRituals.add(userRitualObj);
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			Log.i("Error in getUserRituals() data  ", e.toString());
		}
		c.close();	
		_database.close();
		
		return userRituals;
	}
	
	/**
	 * 
	 * @param userName
	 * @param ritual
	 * @return
	 */
	public UserRitualModel getRitualsDetails(String userName, String ritual)
	{
		UserRitualModel userRitualObj = new UserRitualModel();
		
		String query = "Select * from "+ TableAttributes.TABLE_USER_RITUALS + " where "+ TableAttributes.USER_NAME
				+ " = '"+userName+"' and "+TableAttributes.RITUAL_NAME+" = '"+ritual+"'" ;
		
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					userRitualObj.setUserName(c.getString(0));
					userRitualObj.setRitualImg(c.getInt(1));
					userRitualObj.setRitualName(c.getString(2));
					userRitualObj.setRitualTime(c.getString(3));
					
					userRitualObj.setNotificationStyle(c.getInt(4));
					userRitualObj.setUrgencySwipe(c.getInt(5));
					userRitualObj.setAnnounceFirst(c.getInt(6));
					userRitualObj.setRingInSilent(c.getInt(7));
					userRitualObj.setRitualDisplayName(c.getString(8));
					userRitualObj.setRitualRemID(c.getInt(9));
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			Log.i("Error in getUserRituals() data  ", e.toString());
		}
		c.close();	
		_database.close();
		
		return userRitualObj;
	}
	
	/**
	 * 
	 * @param userName
	 * @return
	 */
	public ArrayList<String> getRitualsList(String userName)
	{
		ArrayList<String> ritualArr= new ArrayList<String>();
		
		String query = "Select "+TableAttributes.RITUAL_NAME+" from "+ TableAttributes.TABLE_USER_RITUALS + " where "
					+ TableAttributes.USER_NAME	+ " = '"+userName+"'";
		
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					ritualArr.add(c.getString(0));
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			Log.i("Error in getUserRituals() data  ", e.toString());
		}
		c.close();	
		_database.close();
		
		return ritualArr;
	}
	
	/**
	 * 
	 * @param userName
	 * @param jName
	 * @return
	 */
	public JourneyModel getJourneyInfo(String userName, String jName)
	{
		JourneyModel journeyObj = new JourneyModel();
		String query = "Select * from "+ TableAttributes.TABLE_JOURNEY + " where "+ TableAttributes.USER_NAME+ " = '"+userName+
				"' and "+TableAttributes.JOURNEY_NAME+" = '"+jName+"'";
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					journeyObj.setUserName(c.getString(0));
					journeyObj.setJourneyName(c.getString(1));
					journeyObj.setTotalEvents(c.getInt(2));
					journeyObj.setEventsAchieved(c.getInt(3));
					journeyObj.setStepStatus1(c.getInt(4));
					journeyObj.setStepStatus2(c.getInt(5));
					journeyObj.setStepStatus3(c.getInt(6));
					journeyObj.setStepStatus4(c.getInt(7));
					journeyObj.setStepStatus5(c.getInt(8));
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			Log.i("Error in getUserJourneyInfo() data  ", e.toString());
		}
		c.close();	
		_database.close();
		
		return journeyObj;
	}
	
	/**
	 * 
	 * @param userName
	 * @param jName
	 * @return
	 */
	public ArrayList<JourneyHabitModel> getJourneyDetails(String userName, String jName)
	{
		ArrayList<JourneyHabitModel> journeyDetails = new ArrayList<JourneyHabitModel>();
		String query = "Select * from "+ TableAttributes.TABLE_JOURNEY_HABIT + " where "+ TableAttributes.USER_NAME+ " = '"+userName+
				"' and "+TableAttributes.JOURNEY_NAME+" = '"+jName+"'";
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					JourneyHabitModel journeyObj = new JourneyHabitModel();
					journeyObj.setUserName(c.getString(0));
					journeyObj.setJourneyName(c.getString(1));
					journeyObj.setHabitId(c.getInt(2));
					journeyObj.setLetterStatus(c.getInt(3));
					journeyObj.setChallengeStatus(c.getInt(4));
					journeyObj.setGoalStatus(c.getInt(5));
					journeyObj.setActionStatus(c.getInt(6));
					journeyObj.setMotivationStatus(c.getInt(7));
					journeyObj.setGoldenChallenge(c.getInt(8));
					journeyObj.setGoldenStatus(c.getInt(9));
					journeyDetails.add(journeyObj);
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			Log.i("Error in getJourneyHabit() data  ", e.toString());
		}
		c.close();	
		_database.close();
		
		return journeyDetails;
	}
	
	/**
	 * 
	 * @param userName
	 * @param habitId
	 * @return
	 */
	public JourneyHabitModel getJourneyHabit(String userName, int habitId)
	{
		JourneyHabitModel journeyObj = new JourneyHabitModel();
		String query = "Select * from "+ TableAttributes.TABLE_JOURNEY_HABIT + " where "+ TableAttributes.USER_NAME+ " = '"+userName+
				"' and "+TableAttributes.H_ID+" = '"+habitId+"'";
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					journeyObj.setUserName(c.getString(0));
					journeyObj.setJourneyName(c.getString(1));
					journeyObj.setHabitId(c.getInt(2));
					journeyObj.setLetterStatus(c.getInt(3));
					journeyObj.setChallengeStatus(c.getInt(4));
					journeyObj.setGoalStatus(c.getInt(5));
					journeyObj.setActionStatus(c.getInt(6));
					journeyObj.setMotivationStatus(c.getInt(7));
					journeyObj.setGoldenChallenge(c.getInt(8));
					journeyObj.setGoldenStatus(c.getInt(9));
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			Log.i("Error in getJourneyHabit() data  ", e.toString());
		}
		c.close();	
		_database.close();
		
		return journeyObj;
	}
	
	/**
	 * 
	 * @param userName
	 * @param habitId
	 * @return
	 */
	public int getJourneyGoldenStatus(String userName, int habitId)
	{
		int status = 0;
		String query = "Select "+TableAttributes.GOLDEN_GOAL_STATUS+" from "+ TableAttributes.TABLE_JOURNEY_HABIT 
				+ " where "+ TableAttributes.USER_NAME+ " = '"+userName+"' and "+TableAttributes.H_ID+" = '"+habitId+"'";
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				status=c.getInt(0);
			}
		 }	
		catch (Exception e)
		{
			Log.i("Error in getJourneyHabit() data  ", e.toString());
		}
		c.close();	
		_database.close();
		
		return status;
	}
	
	/**
	 * 
	 * @param userName
	 * @param h_id
	 * @return
	 */
	public String getCompletionTime(String userName,int h_id)
	{
		String completedOn="";
		String s = "Select "+TableAttributes.HABIT_COMPLETED_ON+" from "+ TableAttributes.TABLE_USER_HABIT + " where "+
    			TableAttributes.USER_NAME+ " = '"+userName+"' and "+TableAttributes.H_ID+" = '"+h_id+"'" ;
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(s, null);
		if (c.moveToFirst()) {
			
			completedOn = c.getString(0);
			c.close();
		} 
		_database.close();
		return completedOn;
	}
	
	/**
	 * 
	 * @param userName
	 * @param colomnId
	 * @return
	 */
	/*public Boolean isValueAdded(String userName, String colomnId)
	{
		UserModel user = new UserModel();
		String query = "Select "+colomnId+" from "+ TableAttributes.TABLE_USER + " where "+ TableAttributes.USER_NAME+ " = '"+userName+"'" ;
		
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					if(c.getString(0).equalsIgnoreCase("true"))
					{
						c.close();
						_database.close();
						return true;
					}
					else
					{
						c.close();
						_database.close();
						return false;
					}
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			Log.i("Error in isValueAdded() data  ", e.toString());
			return false;
		}
		c.close();	
		_database.close();
		
		return false;
	}*/
	
	
	/*public ArrayList<ReminderModel> getUserReminder(String userName, String ritual)
	{
		ArrayList<ReminderModel> reminderList = new ArrayList<ReminderModel>();
		_database = databaseHelper.openDataBase();
		
		String query = TableAttributes.GET_USER_REMINDER+ " where "+ TableAttributes.USER_NAME+ " = '"+userName
				+"' and "+TableAttributes.RITUAL_TYPE +" = '"+ritual+"'";
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					
					ReminderModel reminder = new ReminderModel();
					reminder.setReminderId(c.getInt(0));
					reminder.setUserName(c.getString(1));
					reminder.setRemindertime(c.getString(2));
					reminder.setRitualType(c.getString(3));
					reminder.setSnoozetime(c.getString(4));
//					reminder.setReminder_sun(c.getInt(5));
//					reminder.setReminder_mon(c.getInt(6));
//					reminder.setReminder_tue(c.getInt(7));
//					reminder.setReminder_wed(c.getInt(8));
//					reminder.setReminder_thu(c.getInt(9));
//					reminder.setReminder_fri(c.getInt(10));
//					reminder.setReminder_sat(c.getInt(11));
					reminderList.add(reminder);
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		c.close();			
		_database.close();
		return reminderList;
	}*/
	
	
	
	public ArrayList<ReminderModel> getUserReminder(String userName, String ritual)
	{
		ArrayList<ReminderModel> reminderList = new ArrayList<ReminderModel>();
		_database = databaseHelper.openDataBase();
		String query = TableAttributes.GET_USER_REMINDER+ " where "+ TableAttributes.USER_NAME+ " = '"+userName
				+"' and "+TableAttributes.RITUAL_TYPE +" = '"+ritual+"'";
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					
					ReminderModel reminder = new ReminderModel();
					int r_id = c.getInt(0);
					reminder.setReminderId(r_id);
					reminder.setUserName(c.getString(1));
					reminder.setRemindertime(c.getString(2));
					reminder.setRitualType(c.getString(3));
					reminder.setSnoozetime(c.getString(4));
					ReminderDescModel remDesc = getReminderDesc(r_id,false);
					reminder.setReminderDesc(remDesc);
					reminderList.add(reminder);
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		c.close();			
		_database.close();
		return reminderList;
	}
	
	public ArrayList<ReminderModel> getUserReminder(String userName)
	{
		ArrayList<ReminderModel> reminderList = new ArrayList<ReminderModel>();
		_database = databaseHelper.openDataBase();
		String query = TableAttributes.GET_USER_REMINDER+ " where "+ TableAttributes.USER_NAME+ " = '"+userName+"'";
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					
					ReminderModel reminder = new ReminderModel();
					int r_id = c.getInt(0);
					reminder.setReminderId(r_id);
					reminder.setUserName(c.getString(1));
					reminder.setRemindertime(c.getString(2));
					reminder.setRitualType(c.getString(3));
					reminder.setSnoozetime(c.getString(4));
					ReminderDescModel remDesc = getReminderDesc(r_id,false);
					reminder.setReminderDesc(remDesc);
					reminderList.add(reminder);
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		c.close();			
		_database.close();
		return reminderList;
	}
	
	public ReminderDescModel getReminderDesc(int r_id, boolean isActivityCall)
	{
		ReminderDescModel reminderDesc = new ReminderDescModel();
		final String GET_REMINDER_DESC = "SELECT * FROM "+TableAttributes.TABLE_REMINDER_DESC;
		
		String query = GET_REMINDER_DESC+ " where "+ TableAttributes.REMINDER_ID+ " = '"+r_id+"'";
		if(isActivityCall)
			_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
			
		try{
			if (c.moveToFirst()) {
				do {
					reminderDesc.setReminderId(c.getInt(0));
					reminderDesc.setUserName(c.getString(1));
					reminderDesc.setRemindertime(c.getString(2));
					//reminderDesc.setReminderDay(c.getString(3));
					String day = c.getString(3);
					int day_oN_Off = c.getInt(4);
					int stamp = c.getInt(5);
					if(day.equalsIgnoreCase(TableAttributes.REMINDER_SUN))
					{
						reminderDesc.setReminder_sun(day_oN_Off);
						reminderDesc.setStampSun(stamp);
					}
					else if(day.equalsIgnoreCase(TableAttributes.REMINDER_MON))
					{
						reminderDesc.setReminder_mon(day_oN_Off);
						reminderDesc.setStampMon(stamp);
					}
					else if(day.equalsIgnoreCase(TableAttributes.REMINDER_TUE))
					{
						reminderDesc.setReminder_tue(day_oN_Off);
						reminderDesc.setStampTue(stamp);
					}
					else if(day.equalsIgnoreCase(TableAttributes.REMINDER_WED))
					{
						reminderDesc.setReminder_wed(day_oN_Off);
						reminderDesc.setStampWed(stamp);
					}
					else if(day.equalsIgnoreCase(TableAttributes.REMINDER_THU))
					{
						reminderDesc.setReminder_thu(day_oN_Off);
						reminderDesc.setStampThu(stamp);
					}
					else if(day.equalsIgnoreCase(TableAttributes.REMINDER_FRI))
					{
						reminderDesc.setReminder_fri(day_oN_Off);
						reminderDesc.setStampFri(stamp);
					}
					else if(day.equalsIgnoreCase(TableAttributes.REMINDER_SAT))
					{
						reminderDesc.setReminder_sat(day_oN_Off);
						reminderDesc.setStampSat(stamp);
					}
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		c.close();		
		if(isActivityCall)
			_database.close();
		return reminderDesc;
	}
	
	/**
	 * Get All Habit List
	 * @param user
	 * @param ritual
	 * @return
	 */
	public ArrayList<HabitModel> getAllHabits()
	{
		ArrayList<HabitModel> habitList = new ArrayList<HabitModel>();
		AppsConstant.savedHabitList = new ArrayList<HabitModel>();
		_database = databaseHelper.openDataBase();
		
		Cursor c = _database.rawQuery(TableAttributes.GET_ALL_HABIT, null);
		try{
			if (c.moveToFirst()) {
				do {
					
					HabitModel habit = new HabitModel();
					habit.setHabitId(c.getInt(0));
					habit.setHabit(c.getString(1));
					habit.setHabitDescription(c.getString(2));
					habit.setHabitBenefits(c.getString(3));
					String time = c.getString(4);
					if(time!=null && time.trim().length()>0)
						habit.setHabitTime(time);
					else
						habit.setHabitTime("5");
					
					habit.setReminderDesc1(c.getString(5));
					habit.setReminderDesc2(c.getString(6));
					habit.setReminderDesc3(c.getString(7));
					habit.setReminderDesc4(c.getString(8));
					habit.setReminderDesc5(c.getString(9));
					habit.setReminderDesc6(c.getString(10));
					habit.setSelection(false);
					
					habitList.add(habit);
					AppsConstant.savedHabitList.add(habit);
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		c.close();			
		_database.close();
		return habitList;
	}
	
	public ArrayList<HabitModel> getAllHabitsUnionUser(String user, String ritual)
	{
		ArrayList<HabitModel> habitList = new ArrayList<HabitModel>();
		AppsConstant.savedHabitList = new ArrayList<HabitModel>();
		_database = databaseHelper.openDataBase();
		
		String GET_HABIT = "SELECT * FROM "+TableAttributes.TABLE_HABIT+" where "+TableAttributes.H_ID+
							" not in (select "+TableAttributes.H_ID+" from "+TableAttributes.TABLE_USER_HABIT+
							" where "+TableAttributes.RITUAL_TYPE+" != '"+ritual+"'  AND "+TableAttributes.USER_NAME+" = '"+user+"')";
		
		Cursor c = _database.rawQuery(GET_HABIT, null);
		try{
			if (c.moveToFirst()) {
				do {
					
					HabitModel habit = new HabitModel();
					habit.setHabitId(c.getInt(0));
					habit.setHabit(c.getString(1));
					habit.setHabitDescription(c.getString(2));
					habit.setHabitBenefits(c.getString(3));
					String time = c.getString(4);
					if(time!=null && time.trim().length()>0)
						habit.setHabitTime(time);
					else
						habit.setHabitTime("5");
					
					habit.setReminderDesc1(c.getString(5));
					habit.setReminderDesc2(c.getString(6));
					habit.setReminderDesc3(c.getString(7));
					habit.setReminderDesc4(c.getString(8));
					habit.setReminderDesc5(c.getString(9));
					habit.setReminderDesc6(c.getString(10));
					
					String query = "Select * from "+ TableAttributes.TABLE_USER_HABIT + " where "+
									TableAttributes.USER_NAME+ " = '"+user+"' and "+TableAttributes.H_ID+" = '"+c.getString(0)+"'" ;
					
					Cursor c2 = _database.rawQuery(query, null);
					try{
						if (c2.moveToFirst()) {
							habit.setSelection(true);
						}
						else
							habit.setSelection(false);
					 }	
					catch (Exception e) {
						habit.setSelection(false);
						e.printStackTrace();
					}
					c2.close();
					
					habitList.add(habit);
					AppsConstant.savedHabitList.add(habit);
					
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		c.close();			
		_database.close();
		return habitList;
	}

	public ArrayList<HabitModel> getUserHabits(String userName, String ritual)
	{
		ArrayList<HabitModel> userHabitList = new ArrayList<HabitModel>();
		String query = TableAttributes.GET_USER_HABIT+ "'"+userName+"' and "
				+TableAttributes.RITUAL_TYPE+" = '"+ritual+"'" +" ORDER BY B."+TableAttributes.HABIT_PRORIOTY;
		//B is alias name of table_user_habit in the query
				
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do {
					HabitModel habit = new HabitModel();
					habit.setHabitId(c.getInt(0));
					habit.setHabit(c.getString(1));
					habit.setHabitDescription(c.getString(2));
					habit.setHabitBenefits(c.getString(3));
					
					habit.setReminderDesc1(c.getString(4));
					habit.setReminderDesc2(c.getString(5));
					habit.setReminderDesc3(c.getString(6));
					habit.setReminderDesc4(c.getString(7));
					habit.setReminderDesc5(c.getString(8));
					habit.setReminderDesc6(c.getString(9));
					
					habit.setHabitTime(c.getString(10));//user habit time
					habit.setStatus(c.getInt(11));
					habit.setHabitPriority(c.getInt(12));
					habit.setHabitCompletionTime(c.getString(13));
					habit.setRemNextDesc(c.getInt(14));
					habit.setHabitAddedON(c.getString(15));
					
					int t = Integer.parseInt(c.getString(10));
					MyHabits.totalTime = MyHabits.totalTime +t;
					userHabitList.add(habit);
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		c.close();			
		_database.close();
		return userHabitList;
	}
	
		
	public TimeLineModel getTimeline(String uName, String ritual, String date)
	{
		TimeLineModel timeLine = null;
		String query = "select *  from "+ TableAttributes.TABLE_TIMELINE
				+ " where "+TableAttributes.USER_NAME+" = '"+uName+"' and "+TableAttributes.RITUAL_TYPE+" ='"+ritual
				+"' and "+TableAttributes.DATE_OF_STATUS+" ='"+date+"'";
				
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				timeLine = new TimeLineModel();
				timeLine.setUserName(c.getString(0));
				timeLine.setRitualType(c.getString(1));
				timeLine.setDateOfStatus(c.getString(2));
				timeLine.setTotalHabit(c.getInt(3));
				timeLine.setHabitCompleted(c.getInt(4));
			} 
		 }	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		c.close();			
		_database.close();
		return timeLine;
	}
	
	
	public ArrayList<String> getHabitTimeLine(String user_name, int h_id)
	{
		ArrayList<String> habitCompletedOn = new ArrayList<String>();
		//SELECT * from Table_timeline where h_id = '1' and COMPLETION_DATE= 'Mon Apr 12 2016'
		String query = "select "+TableAttributes.COMPLETION_DATE+" from "+ TableAttributes.TABLE_HBIT_TIMELINE
					+ " where "+TableAttributes.H_ID+" = '"+h_id+"' and "+TableAttributes.USER_NAME+" ='"+user_name+"'";
				
		//select COMPLETION_DATE from TABLE_TIMELINE where H_ID = '5' order by INDEX_ORDER Desc limit 3
//		String query = "select * from "+ TableAttributes.TABLE_RITUAL_TIMELINE
//				+ " where "+TableAttributes.H_ID+" = '"+h_id+"' order by "+TableAttributes.INDEX_ORDER+" Desc limit 3";
						
				
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				do{
				habitCompletedOn.add(c.getString(0));
				}
				while (c.moveToNext());
			}
		 }	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		c.close();			
		_database.close();
		return habitCompletedOn;
	}
	
	public boolean isHabitCompletedOn(String user_name, int h_id, String date)
	{
		boolean isRecordExist = false;
		//SELECT * from Table_timeline where h_id = '1' and COMPLETION_DATE= 'Mon Apr 12 2016'
		String query = "select "+TableAttributes.COMPLETION_DATE+" from "+ TableAttributes.TABLE_HBIT_TIMELINE
					+ " where "+TableAttributes.H_ID+" = '"+h_id+"' and "+TableAttributes.USER_NAME+" ='"+user_name
					+"' and "+TableAttributes.COMPLETION_DATE+" ='"+date+"'";
				
		//select COMPLETION_DATE from TABLE_TIMELINE where H_ID = '5' order by INDEX_ORDER Desc limit 3
//		String query = "select * from "+ TableAttributes.TABLE_RITUAL_TIMELINE
//				+ " where "+TableAttributes.H_ID+" = '"+h_id+"' order by "+TableAttributes.INDEX_ORDER+" Desc limit 3";
						
				
		_database = databaseHelper.openDataBase();
		Cursor c = _database.rawQuery(query, null);
		try{
			if (c.moveToFirst()) {
				isRecordExist = true;
			}
		 }	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		c.close();			
		_database.close();
		return isRecordExist;
	}
	
//		public byte[] toByteArray (Object obj)
//		{
//		  byte[] bytes = null;
//		  ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		  try {
//		    ObjectOutputStream oos = new ObjectOutputStream(bos); 
//		    oos.writeObject(obj);
//		    oos.flush(); 
//		    oos.close(); 
//		    bos.close();
//		    bytes = bos.toByteArray ();
//		  }
//		  catch (IOException ex) {
//		    //TODO: Handle the exception
//		  }
//		  return bytes;
//		}
	public void GetTableJourney_Rows(String table_name){
		_database=databaseHelper.openDataBase();
		SQLiteDatabase db = _database;

		Cursor cur = db.rawQuery("SELECT * FROM " + table_name, null);

		if(cur.getCount() != 0){
			cur.moveToFirst();

			do{
				String row_values = "";

				for(int i = 0 ; i < cur.getColumnCount(); i++){
					row_values = row_values + " || " + cur.getString(i);
				}

				Log.d("database", row_values);

			}while (cur.moveToNext());
		}
	}
	
}
