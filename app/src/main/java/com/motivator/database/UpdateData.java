package com.motivator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.motivator.common.DateUtility;
import com.motivator.common.WebServices;
import com.motivator.model.JourneyHabitPojo;
import com.motivator.model.JourneyPOJO;
import com.motivator.model.ReminderDescPOJO;
import com.motivator.model.ReminderPOJO;
import com.motivator.model.UserHabitTable;

import java.util.Date;

import static com.motivator.common.AppsConstant.h_id;
import static com.motivator.common.AppsConstant.user_name;

public class UpdateData 
{
	
	SQLiteDatabase _database;
	
	DatabaseHelper databaseHelper;
	Context mContext;
	
	public UpdateData(Context context) 
	{
		mContext = context;
		databaseHelper = DatabaseHelper.getInstance(context);
	}
	
	public int updateUserEmail(String userName, String userEmail)
    {
    	int res;
    	 _database = databaseHelper.openDataBase();
    	ContentValues values = new ContentValues();
		values.put(TableAttributes.USER_EMAIL, userEmail);
		
    	
    	try {
    		res = _database.update(TableAttributes.TABLE_USER, values, TableAttributes.USER_NAME+"=?",new String[] {userName});
    	} 
    	catch (Exception e) {
    		res = 0;
    	}
    	_database.close();
    	return res;
    }
	
	/**
	 * 
	 * @param userName
	 * @param r_name
	 * @param time
	 * @param ritual_rID
	 * @return
	 */
	
	public int updateUserRitualTime(String userName, String r_name, String time, int ritual_rID)
    {
    	int res;
    	 _database = databaseHelper.openDataBase();
    	ContentValues values = new ContentValues();
		values.put(TableAttributes.RITUAL_NAME, r_name); 
		values.put(TableAttributes.RITUAL_TIME, time);
		values.put(TableAttributes.RITUAL_REMINDER_ID, ritual_rID);
    	
    	try {
    		res = _database.update(TableAttributes.TABLE_USER_RITUALS, values, TableAttributes.USER_NAME+"=? AND "
    				+TableAttributes.RITUAL_NAME+" =? ",new String[] {userName, r_name});

			if(res>0){
				String query="Select * from "+ TableAttributes.TABLE_USER_RITUALS+ " where "+ TableAttributes.USER_NAME+ " = '"+userName
						+"' , "+TableAttributes.RITUAL_NAME+" = '"+r_name+"'" ;
				Cursor query_cursor= _database.rawQuery(query, null);
				if (query_cursor.moveToFirst()) {
					String user_name= query_cursor.getString(0)+"";
					String ritual_img= query_cursor.getString(1)+"";
					String ritual_name= query_cursor.getString(2)+"";
					String ritual_time= query_cursor.getString(3)+"";
					String notification_style= query_cursor.getString(4)+"";
					String urgency_swipe= query_cursor.getString(5)+"";
					String announce_first= query_cursor.getString(6)+"";
					String ringinsilient= query_cursor.getString(7)+"";
					String ritual_display_name= query_cursor.getString(8)+"";
					String ritual_reminder_id= query_cursor.getString(9)+"";

					Log.d(TAG,"from fourth:-");
					Log.d(TAG,"user_name"+":-"+user_name);
					Log.d(TAG,"ritual_img"+":-"+ritual_img);
					Log.d(TAG,"ritual_name"+":-"+ritual_name);
					Log.d(TAG,"ritual_time"+":-"+ritual_time);
					Log.d(TAG,"notification_style"+":-"+notification_style);
					Log.d(TAG,"urgency_swipe"+":-"+urgency_swipe);
					Log.d(TAG,"announce_first"+":-"+announce_first);
					Log.d(TAG,"ringinsilient"+":-"+ringinsilient);
					Log.d(TAG,"ritual_display_name"+":-"+ritual_display_name);
					Log.d(TAG,"ritual_reminder_id"+":-"+ritual_reminder_id);
//				query_cursor.close();
				}
			}
    	} 
    	catch (Exception e) {
    		res = 0;
    	}
    	_database.close();
    	return res;
    }
	
	public int updateRitualReminder(String userName, String r_name)
    {
    	int res;
    	 _database = databaseHelper.openDataBase();
    	ContentValues values = new ContentValues();
		values.put(TableAttributes.RITUAL_NAME, r_name); 
		values.put(TableAttributes.RITUAL_REMINDER_ID, 0);
    	
    	try {
    		res = _database.update(TableAttributes.TABLE_USER_RITUALS, values, TableAttributes.USER_NAME+"=? AND "
    				+TableAttributes.RITUAL_NAME+" =? ",new String[] {userName, r_name});
			if(res>0){
				String query="Select * from "+ TableAttributes.TABLE_USER_RITUALS+ " where "+ TableAttributes.USER_NAME+ " = '"+userName
						+"' OR "+TableAttributes.RITUAL_NAME+" = '"+r_name+"'" ;
				Cursor query_cursor= _database.rawQuery(query, null);
				if (query_cursor.moveToFirst()) {
					String user_name= query_cursor.getString(0)+"";
					String ritual_img= query_cursor.getString(1)+"";
					String ritual_name= query_cursor.getString(2)+"";
					String ritual_time= query_cursor.getString(3)+"";
					String notification_style= query_cursor.getString(4)+"";
					String urgency_swipe= query_cursor.getString(5)+"";
					String announce_first= query_cursor.getString(6)+"";
					String ringinsilient= query_cursor.getString(7)+"";
					String ritual_display_name= query_cursor.getString(8)+"";
					String ritual_reminder_id= query_cursor.getString(9)+"";

					Log.d(TAG,"from second:-");
					Log.d(TAG,"user_name"+":-"+user_name);
					Log.d(TAG,"ritual_img"+":-"+ritual_img);
					Log.d(TAG,"ritual_name"+":-"+ritual_name);
					Log.d(TAG,"ritual_time"+":-"+ritual_time);
					Log.d(TAG,"notification_style"+":-"+notification_style);
					Log.d(TAG,"urgency_swipe"+":-"+urgency_swipe);
					Log.d(TAG,"announce_first"+":-"+announce_first);
					Log.d(TAG,"ringinsilient"+":-"+ringinsilient);
					Log.d(TAG,"ritual_display_name"+":-"+ritual_display_name);
					Log.d(TAG,"ritual_reminder_id"+":-"+ritual_reminder_id);
//				query_cursor.close();
				}
			}
    	} 
    	catch (Exception e) {
    		res = 0;
    	}
    	_database.close();
    	return res;
    }
	
	public int updateUserRitualName(String userName, String r_name, String new_name, int r_img)
    {
    	int res;
    	 _database = databaseHelper.openDataBase();
    	ContentValues values = new ContentValues();
		values.put(TableAttributes.RITUAL_DISPLAY_NAME, new_name); 
		values.put(TableAttributes.RITUAL_IMG, r_img);

    	try {
    		res = _database.update(TableAttributes.TABLE_USER_RITUALS, values, TableAttributes.USER_NAME+"=? AND "
    				+TableAttributes.RITUAL_NAME+" =? ",new String[] {userName, r_name});

			if(res>0){
				String query="Select * from "+ TableAttributes.TABLE_USER_RITUALS+ " where "+ TableAttributes.USER_NAME+ " = '"+userName
						+"' OR "+TableAttributes.RITUAL_IMG+" = '"+r_img+"'" ;
				Cursor query_cursor= _database.rawQuery(query, null);
				if (query_cursor.moveToFirst()) {
					String user_name= query_cursor.getString(0)+"";
					String ritual_img= query_cursor.getString(1)+"";
					String ritual_name= query_cursor.getString(2)+"";
					String ritual_time= query_cursor.getString(3)+"";
					String notification_style= query_cursor.getString(4)+"";
					String urgency_swipe= query_cursor.getString(5)+"";
					String announce_first= query_cursor.getString(6)+"";
					String ringinsilient= query_cursor.getString(7)+"";
					String ritual_display_name= query_cursor.getString(8)+"";
					String ritual_reminder_id= query_cursor.getString(9)+"";

					Log.d(TAG,"from third:-");
					Log.d(TAG,"user_name"+":-"+user_name);
					Log.d(TAG,"ritual_img"+":-"+ritual_img);
					Log.d(TAG,"ritual_name"+":-"+ritual_name);
					Log.d(TAG,"ritual_time"+":-"+ritual_time);
					Log.d(TAG,"notification_style"+":-"+notification_style);
					Log.d(TAG,"urgency_swipe"+":-"+urgency_swipe);
					Log.d(TAG,"announce_first"+":-"+announce_first);
					Log.d(TAG,"ringinsilient"+":-"+ringinsilient);
					Log.d(TAG,"ritual_display_name"+":-"+ritual_display_name);
					Log.d(TAG,"ritual_reminder_id"+":-"+ritual_reminder_id);
//				query_cursor.close();
				}
			}
    	} 
    	catch (Exception e) {
    		res = 0;
    	}
    	_database.close();
    	return res;
    }
	
/**
 * 
 * @param userName
 * @param coloumnId
 * @param value
 * @return
 */
	public int updateReminderSetting(String userName, String ritual, String coloumnId, int value)
    {
    	int res;
    	 _database = databaseHelper.openDataBase();
    	ContentValues values = new ContentValues();
    	values.put(coloumnId, value); 
    	
    	try {
    		res = _database.update(TableAttributes.TABLE_USER_RITUALS, values, 
    				TableAttributes.USER_NAME+"=? AND "+TableAttributes.RITUAL_NAME +" =? ",new String[] {userName, ritual});
			if(res>0){
				String query="Select * from "+ TableAttributes.TABLE_USER_RITUALS+ " where "+ TableAttributes.USER_NAME+ " = '"+userName
						+"' OR "+TableAttributes.RITUAL_NAME+" = '"+ritual+"'" ;
				Cursor query_cursor= _database.rawQuery(query, null);
				if (query_cursor.moveToFirst()) {
					String user_name= query_cursor.getString(0)+"";
					String ritual_img= query_cursor.getString(1)+"";
					String ritual_name= query_cursor.getString(2)+"";
					String ritual_time= query_cursor.getString(3)+"";
					String notification_style= query_cursor.getString(4)+"";
					String urgency_swipe= query_cursor.getString(5)+"";
					String announce_first= query_cursor.getString(6)+"";
					String ringinsilient= query_cursor.getString(7)+"";
					String ritual_display_name= query_cursor.getString(8)+"";
					String ritual_reminder_id= query_cursor.getString(9)+"";

					Log.d(TAG,"from first:-");
					Log.d(TAG,"user_name"+":-"+user_name);
					Log.d(TAG,"ritual_img"+":-"+ritual_img);
					Log.d(TAG,"ritual_name"+":-"+ritual_name);
					Log.d(TAG,"ritual_time"+":-"+ritual_time);
					Log.d(TAG,"notification_style"+":-"+notification_style);
					Log.d(TAG,"urgency_swipe"+":-"+urgency_swipe);
					Log.d(TAG,"announce_first"+":-"+announce_first);
					Log.d(TAG,"ringinsilient"+":-"+ringinsilient);
					Log.d(TAG,"ritual_display_name"+":-"+ritual_display_name);
					Log.d(TAG,"ritual_reminder_id"+":-"+ritual_reminder_id);
//				query_cursor.close();
				}
			}
    	} 
    	catch (Exception e) {
    		res = 0;
    	}
    	_database.close();
    	return res;
    }
	
	public int updateReminder(int r_id, int rStamp, String day, int oN_off) 
	{
		int res = -1;
		_database = databaseHelper.openDataBase();
		ContentValues values = new ContentValues();
		values.put(TableAttributes.REMINDER_ON_OFF, oN_off);
		values.put(TableAttributes.REMINDER_STAMP, rStamp);
		// updating Row
		try{
			res = _database.update(TableAttributes.TABLE_REMINDER_DESC, values,
					TableAttributes.REMINDER_DAY+"=? AND " + TableAttributes.REMINDER_ID + " = ?",new String[] {day, String.valueOf(r_id)});

			if(res>0) {
				NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
				ReminderDescPOJO pojo = helper.getReminderDescData(String.valueOf(r_id), day);
				if (pojo != null) {
					Log.d(TAG,"reminder update pojo:-"+pojo.toString());
					new WebServices().new UpdateReminderDescService(pojo.getRem_desc_user_id(), pojo.getRem_desc_user_name(),
							pojo.getRem_desc_time(), pojo.getRem_desc_day(), String.valueOf(oN_off), String.valueOf(rStamp),
							pojo.getRem_desc_rem_id(), pojo.getRem_desc_id()).execute();
				}
				Log.d(TAG,"reminder desc updated");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		_database.close(); // Closing database connection
		return res;
	}
	
	private final static String TAG="crud";
	public int updateReminderTime(int r_id, String user, String time) 
	{
		int res = -1;
		_database = databaseHelper.openDataBase();
		ContentValues values = new ContentValues();
		values.put(TableAttributes.REMINDER_TIME, time);
		// updating Row
		try{
			res = _database.update(TableAttributes.TABLE_REMINDER, values,
					TableAttributes.USER_NAME+"=? AND " + TableAttributes.REMINDER_ID + " = ?",new String[] {user, String.valueOf(r_id)});
			if(res>0){
				NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
				ReminderPOJO pojo = helper.getReminderData(String.valueOf(r_id),user_name);
				if (pojo != null) {
					Log.d(TAG,pojo.toString());
					new WebServices().new UpdateReminderService(pojo.getReminder_user_id(), pojo.getReminder_id(),
							pojo.getReminder_user_name(),time, pojo.getReminder_ritual_type(), pojo.getReminder_snooze_time(),
							pojo.getRem_id()).execute();
				}
				Log.d(TAG,"reminder time updated");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// also update reminder time in TABLE_REMINDER_DESC
		values = new ContentValues();
		values.put(TableAttributes.REMINDER_TIME, time);
		// updating Row
		try{
			res = _database.update(TableAttributes.TABLE_REMINDER_DESC, values,
					TableAttributes.USER_NAME+"=? AND " + TableAttributes.REMINDER_ID + " = ?",new String[] {user, String.valueOf(r_id)});

			if(res>0) {
				NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
				ReminderDescPOJO pojo = helper.getReminderDescDataByurid(user, String.valueOf(r_id));
				if (pojo != null) {
					Log.d(TAG,"reminder update pojo1:-"+pojo.toString());
					new WebServices().new UpdateReminderDescService(pojo.getRem_desc_user_id(), pojo.getRem_desc_user_name(),
							time, pojo.getRem_desc_day(), pojo.getRem_desc_on_off(), pojo.getRem_desc_stamp(),
							pojo.getRem_desc_rem_id(), pojo.getRem_desc_id()).execute();
				}
				Log.d(TAG,"reminder desc time updated");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		_database.close(); // Closing database connection
		return res;
	}
	

//	public int updateUserHabitStatus(String userName,int h_id, String ritual_type, int iscompleted)
//    {
//    	int res = 0;
//    	 _database = databaseHelper.openDataBase();
//    	 
//    	 GetData getData = new GetData(mContext);
//    	 String lastCompletionTime = getData.getCompletionTime(userName, h_id);
//    	 if(lastCompletionTime==null || lastCompletionTime.length()<=0)
//    	 {
//    		 res = updateHabitCompletionTime(userName, h_id, ritual_type, iscompleted);
//    	 }
//    	 else
//    	 {
//    		 int result = DateUtility.compareDateWithToday(lastCompletionTime, "E MMM dd yyyy");
//    		 if(result>0)
//    		 {
//    			 res = updateHabitCompletionTime(userName, h_id, ritual_type, iscompleted);
//    		 }
//    		 else
//    		 {
//    			 res = -1;
//    		 }
//    	 }
//    	_database.close();
//    	return res;
//    }
//	
//	private int updateHabitCompletionTime(String userName,int h_id, String ritual_type, int iscompleted) 
//	{
//		int res = 0;
//		
//		String todaydateString = DateUtility.formateDate(new Date(), "E MMM dd yyyy");
//		//put completion date in time line table
//		PutData putData = PutData.getInstance(mContext);
//		putData.addHabitTimeLine(userName, ritual_type, h_id, todaydateString);
//		 
//		//update last completion date in user_habit table
//		ContentValues values = new ContentValues();
//    	values.put(TableAttributes.IS_HABIT_COMPLETED, iscompleted); 
//    	values.put(TableAttributes.HABIT_COMPLETED_ON, todaydateString); 
//    	
//    	try {
//    		res = _database.update(TableAttributes.TABLE_USER_HABIT, values, TableAttributes.H_ID+"=?",new String[] {String.valueOf(h_id)});
//    	} 
//    	catch (Exception e) {
//    		res = 0;
//    	}
//    	
//    	if(res>0)
//    	{
//    		String s = "Select "+TableAttributes.JOURNEY_NAME+", "+TableAttributes.LETTER_READ+", "+TableAttributes.GOAL_COMPLETED
//    				+", "+TableAttributes.GOLDEN_CHALLENGE+", "+TableAttributes.GOLDEN_GOAL_STATUS+" from "+ TableAttributes.TABLE_JOURNEY_HABIT
//    				+ " where "+ TableAttributes.USER_NAME+ " = '"+userName+"' and "+TableAttributes.H_ID+" = '"+h_id+"'" ;
//    		Cursor c = _database.rawQuery(s, null);
//    		if (c.moveToFirst()) {
//    			String jName = c.getString(0);
//    			int challengeInProgress = c.getInt(1);
//    			int goalStatus = c.getInt(2);
//    			int goldenChallenge = c.getInt(3);
//    			int goldenGoalStatus = c.getInt(4);
//    			c.close();
//    			if(challengeInProgress > TableAttributes.STATUS_VALUE_0)
//    			{
//    				if(goalStatus == TableAttributes.STATUS_VALUE_2)
//    				{
//    					String selectedStep = getJourneyStep(userName, jName, h_id);
//    					updateJourneyStatus(userName, jName, selectedStep);
//    				}
//    				updateJourneyHabit(userName, jName, h_id, TableAttributes.GOAL_COMPLETED, goalStatus+1);
//    			}
//    			if(goldenChallenge > TableAttributes.STATUS_VALUE_0)
//    			{
//    				updateJourneyHabit(userName, jName, h_id, TableAttributes.GOLDEN_GOAL_STATUS, goldenGoalStatus+1);
//    			}
//    		} 
//    	}
//		
//    	return res;
//	}

	
	
	public int updateUserHabitStatus(String userName,int h_id, String ritual_type, int size)
    {
    	int res = 0;
    	_database = databaseHelper.openDataBase();
    	
    	String todaydateString = DateUtility.formateDate(new Date(), "E MMM dd yyyy");
		//put completion date in time line table
		addHabitTimeLine(userName, ritual_type, h_id, todaydateString);
    	 
		//update last completion date in user_habit table
		ContentValues values = new ContentValues();
    	values.put(TableAttributes.IS_HABIT_COMPLETED, TableAttributes.COMPLETED); 
    	values.put(TableAttributes.HABIT_COMPLETED_ON, todaydateString); 
    	
    	try {
    		res = _database.update(TableAttributes.TABLE_USER_HABIT, values, TableAttributes.H_ID+"=?",new String[] {String.valueOf(h_id)}); 	}
    	catch (Exception e) {
    		res = 0;
    	}
		
		
    	values.clear();
    	values = new ContentValues();
		values.put(TableAttributes.USER_NAME, userName); 
		values.put(TableAttributes.RITUAL_TYPE, ritual_type);
		values.put(TableAttributes.DATE_OF_STATUS, todaydateString);
		values.put(TableAttributes.TOTAL_HABIT, size);
		// check status in TimeLine Table
		int count = getTotalHabitCount(userName, ritual_type, todaydateString);
		if(count != -1)
		{
			int totalHabit = count+1;
			values.put(TableAttributes.HABIT_COMPLETED, totalHabit);
			try{
				long row  = _database.update(TableAttributes.TABLE_TIMELINE, values,
						TableAttributes.USER_NAME+"=? AND " + TableAttributes.RITUAL_TYPE + " = ? AND "+TableAttributes.DATE_OF_STATUS + " = ?",
						new String[] {userName, ritual_type, todaydateString});
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		else
		{
			// Add Row in TimeLine Table			
			values.put(TableAttributes.HABIT_COMPLETED, 1);
			// Inserting Row
			try{
			long row = _database.insert(TableAttributes.TABLE_TIMELINE, null, values);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		updateJourneyGoal(userName, h_id, ritual_type);
    	_database.close();
    	return res;
    }
	
	private int getTotalHabitCount(String username, String type, String todaydateString)
    {
    	int count = -1;
		String s = "Select "+TableAttributes.HABIT_COMPLETED+" from "+ TableAttributes.TABLE_TIMELINE + " where "+
				TableAttributes.USER_NAME+ " = '"+username+"' and "+TableAttributes.RITUAL_TYPE+" = '"+type
				+"' and "+TableAttributes.DATE_OF_STATUS+" ='"+todaydateString+"'" ;
		
		try{
			Cursor c = _database.rawQuery(s, null);
			if (c.moveToFirst()) 
			{
				// Update status in TimeLine Table
				count =  c.getInt(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;		
    }
	
	 private long addHabitTimeLine(String username, String ritual_type, int h_id, String completion_date)
	 {
		 long res = -1;
		 ContentValues values = new ContentValues();
		 values.put(TableAttributes.USER_NAME, username); 
		 values.put(TableAttributes.RITUAL_TYPE, ritual_type);
		 values.put(TableAttributes.H_ID, h_id);
		 values.put(TableAttributes.COMPLETION_DATE, completion_date);
		 
		 // Inserting Row
		 try{
			 res = _database.insert(TableAttributes.TABLE_HBIT_TIMELINE, null, values);
		 }	catch(Exception e){
			 e.printStackTrace();
		 }
		 return res;
	 }
	
	
	private int updateJourneyGoal(String userName,int h_id, String ritual_type) 
	{
		int res = 0;
	    	
		String s = "Select "+TableAttributes.JOURNEY_NAME+", "+TableAttributes.LETTER_READ+", "+TableAttributes.GOAL_COMPLETED
				+", "+TableAttributes.GOLDEN_CHALLENGE+", "+TableAttributes.GOLDEN_GOAL_STATUS+" from "+ TableAttributes.TABLE_JOURNEY_HABIT
				+ " where "+ TableAttributes.USER_NAME+ " = '"+userName+"' and "+TableAttributes.H_ID+" = '"+h_id+"'" ;
		Cursor c = _database.rawQuery(s, null);
		if (c.moveToFirst()) {
			String jName = c.getString(0);
			int challengeInProgress = c.getInt(1);
			int goalStatus = c.getInt(2);
			int goldenChallenge = c.getInt(3);
			int goldenGoalStatus = c.getInt(4);
			c.close();
			if(challengeInProgress > TableAttributes.STATUS_VALUE_0)
			{
				if(goalStatus == TableAttributes.STATUS_VALUE_2)
				{
					String selectedStep = getJourneyStep(userName, jName, h_id);
					updateJourneyStatus(userName, jName, selectedStep);
				}
				updateJourneyHabit(userName, jName, h_id, TableAttributes.GOAL_COMPLETED, goalStatus+1);
			}
			if(goldenChallenge > TableAttributes.STATUS_VALUE_0)
			{
				updateJourneyHabit(userName, jName, h_id, TableAttributes.GOLDEN_GOAL_STATUS, goldenGoalStatus+1);
			}
		} 
		
    	return res;
	}
	/**
	 * 
	 * @param userName
	 * @param jName
	 * @param h_id
	 * @param colomnId
	 * @param value
	 */
	public void updateJourneyHabit(String userName,String jName, int h_id, String colomnId, int value)
    {
    	 _database = databaseHelper.openDataBase();
    	
    	try {
    		String sql = "update "+TableAttributes.TABLE_JOURNEY_HABIT+" set "+colomnId+" = "+value+
    				" where "+TableAttributes.USER_NAME+" = '"+userName+"' and  "
    				+TableAttributes.JOURNEY_NAME+" = '"+jName+"' and "+TableAttributes.H_ID+" = '"+h_id+"'";
			_database.execSQL(sql);
			NewDataBaseHelper helper=new NewDataBaseHelper(mContext);
			JourneyHabitPojo pojo=helper.getJourneyHabitData(String.valueOf(h_id),jName,userName);
			if(colomnId.equals(TableAttributes.GOLDEN_GOAL_STATUS)){
				if(pojo!=null) {

					new WebServices().new UpdateJourneyHabitService(pojo.getJ_h_id(),pojo.getJ_h_user_id()
							,pojo.getJ_h_user_name(),pojo.getJ_h_journey_name(),pojo.getJ_h_hid(),pojo.getJ_h_letter_reap(),
							pojo.getJ_h_challenge_acc(),pojo.getJ_h_goal_completed(),pojo.getJ_h_action_done(),
							pojo.getJ_h_motivation(),pojo.getJ_h_golden_chllenge(),String.valueOf(value)).execute();
				}
			}else{
				if(colomnId.equals(TableAttributes.GOAL_COMPLETED)){
					if(pojo!=null) {

						new WebServices().new UpdateJourneyHabitService(pojo.getJ_h_id(),pojo.getJ_h_user_id()
								,pojo.getJ_h_user_name(),pojo.getJ_h_journey_name(),pojo.getJ_h_hid(),pojo.getJ_h_letter_reap(),
								pojo.getJ_h_challenge_acc(),String.valueOf(value),pojo.getJ_h_action_done(),
								pojo.getJ_h_motivation(),pojo.getJ_h_golden_chllenge(),pojo.getJ_golden_status()).execute();
					}
				}
			}

    	} 
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	_database.close();
    }
	public void updateJourneyGoldenChallenge(String userName,String jName, String colomnId, int value)
    {
    	 _database = databaseHelper.openDataBase();

    	try {
    		String sql = "update "+TableAttributes.TABLE_JOURNEY_HABIT+" set "+colomnId+" = "+value+
    				" where "+TableAttributes.USER_NAME+" = '"+userName+"' and  "
    				+TableAttributes.JOURNEY_NAME+" = '"+jName+"'";
			_database.execSQL(sql);

			NewDataBaseHelper helper=new NewDataBaseHelper(mContext);
			JourneyHabitPojo pojo=helper.getJourneyHabitData(String.valueOf(h_id),jName,userName);

				if(pojo!=null) {

					new WebServices().new UpdateJourneyHabitService(pojo.getJ_h_id(),pojo.getJ_h_user_id()
							,pojo.getJ_h_user_name(),pojo.getJ_h_journey_name(),pojo.getJ_h_hid(),pojo.getJ_h_letter_reap(),
							pojo.getJ_h_challenge_acc(),pojo.getJ_h_goal_completed(),pojo.getJ_h_action_done(),
							pojo.getJ_h_motivation(),String.valueOf(value),pojo.getJ_golden_status()).execute();
				}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	_database.close();
    }
	
	/**
	 * 
	 * @param userName
	 * @param jName
	 * @param colomnId
	 */
	public void updateJourneyStatus(String userName,String jName, String colomnId)
    {
		Log.d(TAG,"columns id journey:-"+colomnId);
		int totalAcheived=0 ,stepStatus=0;
    	 _database = databaseHelper.openDataBase();
    	 
    	 String s = "Select "+TableAttributes.TOTAL_EVENTS_ACHIEVED+", "+colomnId
 				+" from "+ TableAttributes.TABLE_JOURNEY+ " where "+ TableAttributes.USER_NAME+ " = '"+userName
 				+"' and "+TableAttributes.JOURNEY_NAME+" = '"+jName+"'" ;
 		Cursor c = _database.rawQuery(s, null);
 		if (c.moveToFirst()) {
 			totalAcheived = c.getInt(0);
 			stepStatus = c.getInt(1);
// 			c.close();
 		}


    	
 		try {
			int newStatus=0,newAchievement = 0;
			try {
				newStatus = stepStatus + 1;
				newAchievement = totalAcheived + 1;
				// brfore updating make sure that that it is not updating for the same step again by handling it in java class
				String sql = "update " + TableAttributes.TABLE_JOURNEY + " set " + colomnId + " = " + newStatus
						+ " , " + TableAttributes.TOTAL_EVENTS_ACHIEVED + " = " + newAchievement +
						" where " + TableAttributes.USER_NAME + " = '" + userName + "' and  " + TableAttributes.JOURNEY_NAME + " = '" + jName + "'";
				_database.execSQL(sql);
			}
			catch (Exception e){
				Log.d("database","error1:-"+e.toString());
			}
			try{
				String query="Select * from "+ TableAttributes.TABLE_JOURNEY+ " where "+ TableAttributes.USER_NAME+ " = '"+userName
						+"' OR "+TableAttributes.JOURNEY_NAME+" = '"+jName+"'" ;
				Cursor query_cursor= _database.rawQuery(query, null);
				String user_name="";
				String journey_name="";
				String total_events="";
				String total_events_achieved="";
				String status_step1="";
				String status_step2="";
				String status_step3="";
				String status_step4="";
				String status_step5="";

				if (query_cursor.moveToFirst()) {
					user_name= query_cursor.getInt(0)+"";
					journey_name= query_cursor.getInt(1)+"";
					total_events= query_cursor.getInt(2)+"";
					total_events_achieved= query_cursor.getInt(3)+"";
					status_step1= query_cursor.getInt(4)+"";
					status_step2= query_cursor.getInt(5)+"";
					status_step3= query_cursor.getInt(6)+"";
					status_step4= query_cursor.getInt(7)+"";
					status_step5= query_cursor.getInt(8)+"";
//				query_cursor.close();
				}

				NewDataBaseHelper helper=new NewDataBaseHelper(mContext);
				JourneyPOJO pojo=helper.getJourneyData(userName,jName);
				Log.d("database","journey data:-"+pojo.toString());
				if(pojo!=null) {
					Log.d("database","columns:-"+colomnId);
					switch (colomnId) {

						case TableAttributes.STATUS_STEP1:

							new WebServices().new UpdateJourneyService(pojo.getJ_id(),pojo.getJ_user_id(),pojo.getJ_journey_name(),
									user_name,total_events,
									String.valueOf(newAchievement),String.valueOf(newStatus),status_step2,status_step3,status_step4,status_step5).execute();
							break;
						case TableAttributes.STATUS_STEP2:
							new WebServices().new UpdateJourneyService(pojo.getJ_id(),pojo.getJ_user_id(),pojo.getJ_journey_name(),
									user_name,total_events,
									String.valueOf(newAchievement),status_step1,String.valueOf(newStatus),status_step3,status_step4,status_step5).execute();
							break;
						case TableAttributes.STATUS_STEP3:
							new WebServices().new UpdateJourneyService(pojo.getJ_id(),pojo.getJ_user_id(),pojo.getJ_journey_name(),
									user_name,total_events,
									String.valueOf(newAchievement),status_step1,status_step2,String.valueOf(newStatus),status_step4,status_step5).execute();
							break;
						case TableAttributes.STATUS_STEP4:
							new WebServices().new UpdateJourneyService(pojo.getJ_id(),pojo.getJ_user_id(),pojo.getJ_journey_name(),
									user_name,total_events,
									String.valueOf(newAchievement),status_step1,status_step2,status_step3,String.valueOf(newStatus),status_step5).execute();
							break;
						case TableAttributes.STATUS_STEP5:
							new WebServices().new UpdateJourneyService(pojo.getJ_id(),pojo.getJ_user_id(),pojo.getJ_journey_name(),
									user_name,total_events,
									String.valueOf(newAchievement),status_step1,status_step2,status_step3,status_step4,String.valueOf(newStatus)).execute();
							break;
					}
				}
			}
			catch (Exception e){
				Log.d("database","error2:-"+e.toString());
			}

    	} 
    	catch (Exception e) {
    		e.printStackTrace();
			Log.d("database","error:-"+e.toString());
    	}
    	_database.close();
    }
	
	public String getJourneyStep(String userName,String jName, int h_Id)
    {
		String selectedStep = TableAttributes.STATUS_STEP1;
		switch (h_Id) {
		case TableAttributes.drinkWaterId:
			selectedStep = TableAttributes.STATUS_STEP1;
			break;
		case TableAttributes.greatBreakFastID:
			selectedStep = TableAttributes.STATUS_STEP2;
			break;
		case TableAttributes.danceYourWayID:
			selectedStep = TableAttributes.STATUS_STEP3;
			break;
		case TableAttributes.goldenChallengeID:
			selectedStep = TableAttributes.STATUS_STEP4;
			break;
		case TableAttributes.SecretLetterID:
			selectedStep = TableAttributes.STATUS_STEP5;
			break;

		default:
			break;
		}
		return selectedStep;
    }
	/**
	 * 
	 * @param userName
	 * @param h_id
	 * @param colomnId
	 * @param value
	 * @return
	 */
	public int updateUserHabitInfo(String userName,int h_id, String colomnId, String value)
    {
    	int res;
    	 _database = databaseHelper.openDataBase();
    	ContentValues values = new ContentValues();
    	values.put(colomnId, value); 
    	
    	try {
    		res = _database.update(TableAttributes.TABLE_USER_HABIT, values, TableAttributes.H_ID+"=? AND "
    				+TableAttributes.USER_NAME+" =? ",new String[] {String.valueOf(h_id), userName});

			if(res>0){
				String query="Select * from "+ TableAttributes.TABLE_USER_HABIT+ " where "+ TableAttributes.H_ID+ " = '"+h_id+"'" ;
				Cursor query_cursor= _database.rawQuery(query, null);


				String t_user_name="";
				String t_h_id="";
				String t_ritual_type="";
				String t_user_habit_time="";
				String t_habit_completed="";
				String t_habit_priority="";
				String t_habit_completed_on="";
				String t_reminder_next="";
				String t_habit_added_on="";


				if (query_cursor.moveToFirst()) {
					t_user_name= query_cursor.getInt(0)+"";
					t_h_id= query_cursor.getInt(1)+"";
					t_ritual_type= query_cursor.getInt(2)+"";
					t_user_habit_time= query_cursor.getInt(3)+"";
					t_habit_completed= query_cursor.getInt(4)+"";
					t_habit_priority= query_cursor.getInt(5)+"";
					t_habit_completed_on= query_cursor.getInt(6)+"";
					t_reminder_next= query_cursor.getInt(7)+"";
					t_habit_added_on= query_cursor.getInt(8)+"";
					query_cursor.close();
				}

				Log.d(TAG,"h_id:-"+t_h_id);
				NewDataBaseHelper helper=new NewDataBaseHelper(mContext);
				UserHabitTable table=helper.getUserHabitData(t_h_id);

				new WebServices().new UpdateHabitService(table.getTable_user_habits_id(), PrefData.getStringPref(mContext,PrefData.USER_ID),
						t_user_name,t_h_id,t_ritual_type,t_user_habit_time,t_habit_completed,t_habit_priority,
						t_habit_completed_on,t_reminder_next,t_habit_added_on).execute();
			}
    	} 
    	catch (Exception e) {
    		res = 0;
    	}
    	_database.close();
    	return res;
    }
	public int updateUserHabitInfo(String userName,int h_id, String colomnId, int value)
    {
    	int res;
    	 _database = databaseHelper.openDataBase();
    	ContentValues values = new ContentValues();
    	values.put(colomnId, value); 
    	
    	try {
    		res = _database.update(TableAttributes.TABLE_USER_HABIT, values, TableAttributes.H_ID+"=? AND "
    				+TableAttributes.USER_NAME+" =? ",new String[] {String.valueOf(h_id), userName});
			if(res>0){
				String query="Select * from "+ TableAttributes.TABLE_USER_HABIT+ " where "+ TableAttributes.H_ID+ " = '"+h_id+"'" ;
				Cursor query_cursor= _database.rawQuery(query, null);


				String t_user_name="";
				String t_h_id="";
				String t_ritual_type="";
				String t_user_habit_time="";
				String t_habit_completed="";
				String t_habit_priority="";
				String t_habit_completed_on="";
				String t_reminder_next="";
				String t_habit_added_on="";


				if (query_cursor.moveToFirst()) {
					t_user_name= query_cursor.getInt(0)+"";
					t_h_id= query_cursor.getInt(1)+"";
					t_ritual_type= query_cursor.getInt(2)+"";
					t_user_habit_time= query_cursor.getInt(3)+"";
					t_habit_completed= query_cursor.getInt(4)+"";
					t_habit_priority= query_cursor.getInt(5)+"";
					t_habit_completed_on= query_cursor.getInt(6)+"";
					t_reminder_next= query_cursor.getInt(7)+"";
					t_habit_added_on= query_cursor.getInt(8)+"";
				query_cursor.close();
				}

				Log.d(TAG,"h_id:-"+t_h_id);
				NewDataBaseHelper helper=new NewDataBaseHelper(mContext);
				UserHabitTable table=helper.getUserHabitData(t_h_id);

				new WebServices().new UpdateHabitService(table.getTable_user_habits_id(), PrefData.getStringPref(mContext,PrefData.USER_ID),
						t_user_name,t_h_id,t_ritual_type,t_user_habit_time,t_habit_completed,t_habit_priority,
						t_habit_completed_on,t_reminder_next,t_habit_added_on).execute();
			}
    	} 
    	catch (Exception e) {
    		res = 0;
    	}
    	_database.close();
    	return res;
    }
}
