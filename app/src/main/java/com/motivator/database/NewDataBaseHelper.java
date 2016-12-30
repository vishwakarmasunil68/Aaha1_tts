package com.motivator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.motivator.model.CustomHabitPOJO;
import com.motivator.model.DataEntryPOJO;
import com.motivator.model.HabitTimelinePOJO;
import com.motivator.model.JourneyHabitPojo;
import com.motivator.model.JourneyPOJO;
import com.motivator.model.MusicPOJO;
import com.motivator.model.ReminderDescPOJO;
import com.motivator.model.ReminderPOJO;
import com.motivator.model.UserHabitTable;

import java.util.ArrayList;
import java.util.List;

public class NewDataBaseHelper{

	DataBaseHelper1 helper;
	public NewDataBaseHelper(Context context) {
		// TODO Auto-generated constructor stub
		helper=new DataBaseHelper1(context);
	}

	public long insertcustomData(CustomHabitPOJO dep){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues=new ContentValues();

		contentValues.put(DataBaseHelper1.custom_habit_id, dep.getCustom_habit_id()+"");
		contentValues.put(DataBaseHelper1.custom_habit_user_id, dep.getCustom_habit_user_id()+"");
		contentValues.put(DataBaseHelper1.custom_h_id, dep.getCustom_h_id()+"");
		contentValues.put(DataBaseHelper1.custom_habit, dep.getCustom_habit()+"");
		contentValues.put(DataBaseHelper1.custom_description, dep.getCustom_description()+"");
		contentValues.put(DataBaseHelper1.custom_benefits, dep.getCustom_benefits()+"");
		contentValues.put(DataBaseHelper1.custom_habit_time, dep.getCustom_habit_time()+"");
		contentValues.put(DataBaseHelper1.custom_rem_des1, dep.getCustom_rem_des1()+"");
		contentValues.put(DataBaseHelper1.custom_rem_des2, dep.getCustom_rem_des2()+"");
		contentValues.put(DataBaseHelper1.custom_rem_des3, dep.getCustom_rem_des3()+"");
		contentValues.put(DataBaseHelper1.custom_rem_des4, dep.getCustom_rem_des4()+"");
		contentValues.put(DataBaseHelper1.custom_rem_des5, dep.getCustom_rem_des5()+"");
		contentValues.put(DataBaseHelper1.custom_rem_des6, dep.getCustom_rem_des6()+"");


		long id=db.insert(DataBaseHelper1.TABLE_CUSTOM_HABIT, null, contentValues);
		return id;
	}


	public List<CustomHabitPOJO> getAllCustomHabits(){
		SQLiteDatabase db=helper.getWritableDatabase();
		List<CustomHabitPOJO> lst=new ArrayList<>();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.custom_habit_id,
				DataBaseHelper1.custom_habit_user_id,
				DataBaseHelper1.custom_h_id,
				DataBaseHelper1.custom_habit,
				DataBaseHelper1.custom_description,
				DataBaseHelper1.custom_benefits,
				DataBaseHelper1.custom_habit_time,
				DataBaseHelper1.custom_rem_des1,
				DataBaseHelper1.custom_rem_des2,
				DataBaseHelper1.custom_rem_des3,
				DataBaseHelper1.custom_rem_des4,
				DataBaseHelper1.custom_rem_des5,
				DataBaseHelper1.custom_rem_des6
		};
		Cursor cursor=db.query(DataBaseHelper1.TABLE_CUSTOM_HABIT, columns, null, null, null, null, null);

		while(cursor.moveToNext()){

			String custom_habit_id=cursor.getString(1);
			String custom_habit_user_id=cursor.getString(2);
			String custom_h_id=cursor.getString(3);
			String custom_habit=cursor.getString(4);
			String custom_description=cursor.getString(5);
			String custom_benefits=cursor.getString(6);
			String custom_habit_time=cursor.getString(7);
			String custom_rem_des1=cursor.getString(8);
			String custom_rem_des2=cursor.getString(9);
			String custom_rem_des3=cursor.getString(10);
			String custom_rem_des4=cursor.getString(11);
			String custom_rem_des5=cursor.getString(12);
			String custom_rem_des6=cursor.getString(13);


			CustomHabitPOJO pojo=new CustomHabitPOJO();
			pojo.setCustom_habit_id(custom_habit_id);
			pojo.setCustom_habit_user_id(custom_habit_user_id);
			pojo.setCustom_h_id(custom_h_id);
			pojo.setCustom_habit(custom_habit);
			pojo.setCustom_description(custom_description);
			pojo.setCustom_benefits(custom_benefits);
			pojo.setCustom_habit_time(custom_habit_time);
			pojo.setCustom_rem_des1(custom_rem_des1);
			pojo.setCustom_rem_des2(custom_rem_des2);
			pojo.setCustom_rem_des3(custom_rem_des3);
			pojo.setCustom_rem_des4(custom_rem_des4);
			pojo.setCustom_rem_des5(custom_rem_des5);
			pojo.setCustom_rem_des6(custom_rem_des6);

			lst.add(pojo);
		}
		return lst;
	}

	public CustomHabitPOJO getCustomHabitData(String habit_id1){
		SQLiteDatabase db=helper.getWritableDatabase();

		CustomHabitPOJO pojo= null;
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.custom_habit_id,
				DataBaseHelper1.custom_habit_user_id,
				DataBaseHelper1.custom_h_id,
				DataBaseHelper1.custom_habit,
				DataBaseHelper1.custom_description,
				DataBaseHelper1.custom_benefits,
				DataBaseHelper1.custom_habit_time,
				DataBaseHelper1.custom_rem_des1,
				DataBaseHelper1.custom_rem_des2,
				DataBaseHelper1.custom_rem_des3,
				DataBaseHelper1.custom_rem_des4,
				DataBaseHelper1.custom_rem_des5,
				DataBaseHelper1.custom_rem_des6
		};
		String WHERE =  DataBaseHelper1.custom_h_id+"='"+habit_id1+"'";
		Cursor cursor=db.query(DataBaseHelper1.TABLE_CUSTOM_HABIT, columns, WHERE, null, null, null, null);

		while(cursor.moveToNext()){
			String custom_habit_id=cursor.getString(1);
			String custom_habit_user_id=cursor.getString(2);
			String custom_h_id=cursor.getString(3);
			String custom_habit=cursor.getString(4);
			String custom_description=cursor.getString(5);
			String custom_benefits=cursor.getString(6);
			String custom_habit_time=cursor.getString(7);
			String custom_rem_des1=cursor.getString(8);
			String custom_rem_des2=cursor.getString(9);
			String custom_rem_des3=cursor.getString(10);
			String custom_rem_des4=cursor.getString(11);
			String custom_rem_des5=cursor.getString(12);
			String custom_rem_des6=cursor.getString(13);


			pojo=new CustomHabitPOJO();
			pojo.setCustom_habit_id(custom_habit_id);
			pojo.setCustom_habit_user_id(custom_habit_user_id);
			pojo.setCustom_h_id(custom_h_id);
			pojo.setCustom_habit(custom_habit);
			pojo.setCustom_description(custom_description);
			pojo.setCustom_benefits(custom_benefits);
			pojo.setCustom_habit_time(custom_habit_time);
			pojo.setCustom_rem_des1(custom_rem_des1);
			pojo.setCustom_rem_des2(custom_rem_des2);
			pojo.setCustom_rem_des3(custom_rem_des3);
			pojo.setCustom_rem_des4(custom_rem_des4);
			pojo.setCustom_rem_des5(custom_rem_des5);
			pojo.setCustom_rem_des6(custom_rem_des6);


		}
		return pojo;
	}


	public int deleteCustomHabitDATA(String habit_id){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] whereArgs={habit_id};
		int count=db.delete(DataBaseHelper1.TABLE_USER_HABIT, DataBaseHelper1.table_user_habits_habit_id+"=?", whereArgs);
		return count;
	}


	/*---------------------------------------------------------------------------------------------
	===============================================================================================
	===============================================================================================
	 */

	public long insertUserHabitData(UserHabitTable dep){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues=new ContentValues();


		contentValues.put(DataBaseHelper1.table_user_habits_id, dep.getTable_user_habits_id()+"");
		contentValues.put(DataBaseHelper1.table_user_habits_user_id, dep.getTable_user_habits_user_id()+"");
		contentValues.put(DataBaseHelper1.table_user_habits_user_name, dep.getTable_user_habits_user_name()+"");
		contentValues.put(DataBaseHelper1.table_user_habits_habit_id, dep.getTable_user_habits_habit_id()+"");
		contentValues.put(DataBaseHelper1.table_user_habits_ritual_type, dep.getTable_user_habits_ritual_type()+"");
		contentValues.put(DataBaseHelper1.table_user_habits_userhabit_time, dep.getTable_user_habits_userhabit_time()+"");
		contentValues.put(DataBaseHelper1.table_user_habits_is_habit_completed, dep.getTable_user_habits_is_habit_completed()+"");
		contentValues.put(DataBaseHelper1.table_user_habits_habit_priority, dep.getTable_user_habits_habit_priority()+"");
		contentValues.put(DataBaseHelper1.table_user_habits_habit_completed_on, dep.getTable_user_habits_habit_completed_on()+"");
		contentValues.put(DataBaseHelper1.table_user_habits_reminder_next_desc, dep.getTable_user_habits_reminder_next_desc()+"");
		contentValues.put(DataBaseHelper1.table_user_habits_habit_added_on, dep.getTable_user_habits_habit_added_on()+"");


		long id=db.insert(DataBaseHelper1.TABLE_USER_HABIT, null, contentValues);
		return id;
	}


	public List<UserHabitTable> getAllUserHabitData(){
		SQLiteDatabase db=helper.getWritableDatabase();


		List<UserHabitTable> lst=new ArrayList<>();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.table_user_habits_id,
				DataBaseHelper1.table_user_habits_user_id,
				DataBaseHelper1.table_user_habits_user_name,
				DataBaseHelper1.table_user_habits_habit_id,
				DataBaseHelper1.table_user_habits_ritual_type,
				DataBaseHelper1.table_user_habits_userhabit_time,
				DataBaseHelper1.table_user_habits_is_habit_completed,
				DataBaseHelper1.table_user_habits_habit_priority,
				DataBaseHelper1.table_user_habits_habit_completed_on,
				DataBaseHelper1.table_user_habits_reminder_next_desc,
				DataBaseHelper1.table_user_habits_habit_added_on
		};
		Cursor cursor=db.query(DataBaseHelper1.TABLE_USER_HABIT, columns, null, null, null, null, null);

		while(cursor.moveToNext()){

			String table_user_habits_id=cursor.getString(1);
			String table_user_habits_user_id=cursor.getString(2);
			String table_user_habits_user_name=cursor.getString(3);
			String table_user_habits_habit_id=cursor.getString(4);
			String table_user_habits_ritual_type=cursor.getString(5);
			String table_user_habits_userhabit_time=cursor.getString(6);
			String table_user_habits_is_habit_completed=cursor.getString(7);
			String table_user_habits_habit_priority=cursor.getString(8);
			String table_user_habits_habit_completed_on=cursor.getString(9);
			String table_user_habits_reminder_next_desc=cursor.getString(10);
			String table_user_habits_habit_added_on=cursor.getString(11);


			UserHabitTable userHabitTable=new UserHabitTable();
			userHabitTable.setTable_user_habits_id(table_user_habits_id);
			userHabitTable.setTable_user_habits_user_id(table_user_habits_user_id);
			userHabitTable.setTable_user_habits_user_name(table_user_habits_user_name);
			userHabitTable.setTable_user_habits_habit_id(table_user_habits_habit_id);
			userHabitTable.setTable_user_habits_ritual_type(table_user_habits_ritual_type);
			userHabitTable.setTable_user_habits_userhabit_time(table_user_habits_userhabit_time);
			userHabitTable.setTable_user_habits_habit_completed_on(table_user_habits_is_habit_completed);
			userHabitTable.setTable_user_habits_habit_priority(table_user_habits_habit_priority);
			userHabitTable.setTable_user_habits_habit_completed_on(table_user_habits_habit_completed_on);
			userHabitTable.setTable_user_habits_reminder_next_desc(table_user_habits_reminder_next_desc);
			userHabitTable.setTable_user_habits_habit_added_on(table_user_habits_habit_added_on);

			lst.add(userHabitTable);
		}
		return lst;
	}

	public UserHabitTable getUserHabitData(String habit_id1){
		SQLiteDatabase db=helper.getWritableDatabase();

		UserHabitTable userHabitTable = null;
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.table_user_habits_id,
				DataBaseHelper1.table_user_habits_user_id,
				DataBaseHelper1.table_user_habits_user_name,
				DataBaseHelper1.table_user_habits_habit_id,
				DataBaseHelper1.table_user_habits_ritual_type,
				DataBaseHelper1.table_user_habits_userhabit_time,
				DataBaseHelper1.table_user_habits_is_habit_completed,
				DataBaseHelper1.table_user_habits_habit_priority,
				DataBaseHelper1.table_user_habits_habit_completed_on,
				DataBaseHelper1.table_user_habits_reminder_next_desc,
				DataBaseHelper1.table_user_habits_habit_added_on
		};
		String WHERE =  DataBaseHelper1.table_user_habits_habit_id+"='"+habit_id1+"'";
		Cursor cursor=db.query(DataBaseHelper1.TABLE_USER_HABIT, columns, WHERE, null, null, null, null);

		while(cursor.moveToNext()){
			String table_user_habits_id=cursor.getString(1);
			String table_user_habits_user_id=cursor.getString(2);
			String table_user_habits_user_name=cursor.getString(3);
			String table_user_habits_habit_id=cursor.getString(4);
			String table_user_habits_ritual_type=cursor.getString(5);
			String table_user_habits_userhabit_time=cursor.getString(6);
			String table_user_habits_is_habit_completed=cursor.getString(7);
			String table_user_habits_habit_priority=cursor.getString(8);
			String table_user_habits_habit_completed_on=cursor.getString(9);
			String table_user_habits_reminder_next_desc=cursor.getString(10);
			String table_user_habits_habit_added_on=cursor.getString(11);

			userHabitTable=new UserHabitTable();
			userHabitTable.setTable_user_habits_id(table_user_habits_id);
			userHabitTable.setTable_user_habits_user_id(table_user_habits_user_id);
			userHabitTable.setTable_user_habits_user_name(table_user_habits_user_name);
			userHabitTable.setTable_user_habits_habit_id(table_user_habits_habit_id);
			userHabitTable.setTable_user_habits_ritual_type(table_user_habits_ritual_type);
			userHabitTable.setTable_user_habits_userhabit_time(table_user_habits_userhabit_time);
			userHabitTable.setTable_user_habits_habit_completed_on(table_user_habits_is_habit_completed);
			userHabitTable.setTable_user_habits_habit_priority(table_user_habits_habit_priority);
			userHabitTable.setTable_user_habits_habit_completed_on(table_user_habits_habit_completed_on);
			userHabitTable.setTable_user_habits_reminder_next_desc(table_user_habits_reminder_next_desc);
			userHabitTable.setTable_user_habits_habit_added_on(table_user_habits_habit_added_on);

		}
		return userHabitTable;
	}


	public int deleteUserHabitDATA(String habit_id){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] whereArgs={habit_id};
		int count=db.delete(DataBaseHelper1.TABLE_USER_HABIT, DataBaseHelper1.table_user_habits_habit_id+"=?", whereArgs);
		return count;
	}



	/*---------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	 */




	public long insertJourneyHabitData(JourneyHabitPojo dep){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues=new ContentValues();

		contentValues.put(DataBaseHelper1.j_h_id, dep.getJ_h_id()+"");
		contentValues.put(DataBaseHelper1.j_h_user_id, dep.getJ_h_user_id()+"");
		contentValues.put(DataBaseHelper1.j_h_user_name, dep.getJ_h_user_name()+"");
		contentValues.put(DataBaseHelper1.j_h_journey_name, dep.getJ_h_journey_name()+"");
		contentValues.put(DataBaseHelper1.j_h_hid, dep.getJ_h_hid()+"");
		contentValues.put(DataBaseHelper1.j_h_letter_reap, dep.getJ_h_letter_reap()+"");
		contentValues.put(DataBaseHelper1.j_h_challenge_acc, dep.getJ_h_challenge_acc()+"");
		contentValues.put(DataBaseHelper1.j_h_goal_completed, dep.getJ_h_goal_completed()+"");
		contentValues.put(DataBaseHelper1.j_h_action_done, dep.getJ_h_action_done()+"");
		contentValues.put(DataBaseHelper1.j_h_motivation, dep.getJ_h_motivation()+"");
		contentValues.put(DataBaseHelper1.j_h_golden_chllenge, dep.getJ_h_golden_chllenge()+"");


		long id=db.insert(DataBaseHelper1.TABLE_JOURNEY_HABIT, null, contentValues);
		return id;
	}


	public List<JourneyHabitPojo> getAllJourneyHabitData(){
		SQLiteDatabase db=helper.getWritableDatabase();


		List<JourneyHabitPojo> lst=new ArrayList<>();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.j_h_id,
				DataBaseHelper1.j_h_user_id,
				DataBaseHelper1.j_h_user_name,
				DataBaseHelper1.j_h_journey_name,
				DataBaseHelper1.j_h_hid,
				DataBaseHelper1.j_h_letter_reap,
				DataBaseHelper1.j_h_challenge_acc,
				DataBaseHelper1.j_h_goal_completed,
				DataBaseHelper1.j_h_action_done,
				DataBaseHelper1.j_h_motivation,
				DataBaseHelper1.j_h_golden_chllenge,
				DataBaseHelper1.j_golden_status
		};
		Cursor cursor=db.query(DataBaseHelper1.TABLE_JOURNEY_HABIT, columns, null, null, null, null, null);

		while(cursor.moveToNext()){
			String j_h_id=cursor.getString(0);
			String j_h_user_id=cursor.getString(1);
			String j_h_user_name=cursor.getString(2);
			String j_h_journey_name=cursor.getString(3);
			String j_h_hid=cursor.getString(4);
			String j_h_letter_reap=cursor.getString(5);
			String j_h_challenge_acc=cursor.getString(6);
			String j_h_goal_completed=cursor.getString(7);
			String j_h_action_done=cursor.getString(8);
			String j_h_motivation=cursor.getString(9);
			String j_h_golden_chllenge=cursor.getString(10);
			String j_golden_status=cursor.getString(11);


			JourneyHabitPojo pojo=new JourneyHabitPojo();
			pojo.setJ_h_id(j_h_id);
			pojo.setJ_h_user_id(j_h_user_id);
			pojo.setJ_h_user_name(j_h_user_name);
			pojo.setJ_h_journey_name(j_h_journey_name);
			pojo.setJ_h_hid(j_h_hid);
			pojo.setJ_h_letter_reap(j_h_letter_reap);
			pojo.setJ_h_challenge_acc(j_h_challenge_acc);
			pojo.setJ_h_goal_completed(j_h_goal_completed);
			pojo.setJ_h_action_done(j_h_action_done);
			pojo.setJ_h_motivation(j_h_motivation);
			pojo.setJ_h_golden_chllenge(j_h_golden_chllenge);
			pojo.setJ_golden_status(j_golden_status);

			lst.add(pojo);
		}
		return lst;
	}

	public JourneyHabitPojo getJourneyHabitData(String habit_id1,String journey_name1,String user_name1){
		SQLiteDatabase db=helper.getWritableDatabase();

		JourneyHabitPojo pojo=new JourneyHabitPojo();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.j_h_id,
				DataBaseHelper1.j_h_user_id,
				DataBaseHelper1.j_h_user_name,
				DataBaseHelper1.j_h_journey_name,
				DataBaseHelper1.j_h_hid,
				DataBaseHelper1.j_h_letter_reap,
				DataBaseHelper1.j_h_challenge_acc,
				DataBaseHelper1.j_h_goal_completed,
				DataBaseHelper1.j_h_action_done,
				DataBaseHelper1.j_h_motivation,
				DataBaseHelper1.j_h_golden_chllenge,
				DataBaseHelper1.j_golden_status
		};
		String WHERE =  DataBaseHelper1.j_h_user_name+"='"+user_name1+"' OR "+ DataBaseHelper1.j_h_journey_name+"='"+journey_name1+"' "
				+" OR "+ DataBaseHelper1.j_h_hid+"='"+habit_id1+"' ";
		Cursor cursor=db.query(DataBaseHelper1.TABLE_JOURNEY_HABIT, columns, WHERE, null, null, null, null);

		while(cursor.moveToNext()){
			String j_h_id=cursor.getString(0);
			String j_h_user_id=cursor.getString(1);
			String j_h_user_name=cursor.getString(2);
			String j_h_journey_name=cursor.getString(3);
			String j_h_hid=cursor.getString(4);
			String j_h_letter_reap=cursor.getString(5);
			String j_h_challenge_acc=cursor.getString(6);
			String j_h_goal_completed=cursor.getString(7);
			String j_h_action_done=cursor.getString(8);
			String j_h_motivation=cursor.getString(9);
			String j_h_golden_chllenge=cursor.getString(10);
			String j_golden_status=cursor.getString(11);



			pojo.setJ_h_id(j_h_id);
			pojo.setJ_h_user_id(j_h_user_id);
			pojo.setJ_h_user_name(j_h_user_name);
			pojo.setJ_h_journey_name(j_h_journey_name);
			pojo.setJ_h_hid(j_h_hid);
			pojo.setJ_h_letter_reap(j_h_letter_reap);
			pojo.setJ_h_challenge_acc(j_h_challenge_acc);
			pojo.setJ_h_goal_completed(j_h_goal_completed);
			pojo.setJ_h_action_done(j_h_action_done);
			pojo.setJ_h_motivation(j_h_motivation);
			pojo.setJ_h_golden_chllenge(j_h_golden_chllenge);
			pojo.setJ_golden_status(j_golden_status);

		}
		return pojo;
	}


	public int deleteJourneyHabitDATA(String reminder_id){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] whereArgs={reminder_id};
		int count=db.delete(DataBaseHelper1.TABLE_JOURNEY, DataBaseHelper1.TABLE_REMINDER+"=?", whereArgs);
		return count;
	}



	/*---------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	 */




	public long insertJourneyData(JourneyPOJO dep){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues=new ContentValues();

		contentValues.put(DataBaseHelper1.j_id, dep.getJ_id()+"");
		contentValues.put(DataBaseHelper1.j_user_id, dep.getJ_user_id()+"");
		contentValues.put(DataBaseHelper1.j_user_name, dep.getJ_user_name()+"");
		contentValues.put(DataBaseHelper1.j_journey_name, dep.getJ_journey_name()+"");
		contentValues.put(DataBaseHelper1.j_total_events, dep.getJ_total_events()+"");
		contentValues.put(DataBaseHelper1.j_total_events_achived, dep.getJ_total_events_achived()+"");
		contentValues.put(DataBaseHelper1.j_status_step1, dep.getJ_status_step1()+"");
		contentValues.put(DataBaseHelper1.j_status_step2, dep.getJ_status_step2()+"");
		contentValues.put(DataBaseHelper1.j_status_step3, dep.getJ_status_step3()+"");
		contentValues.put(DataBaseHelper1.j_status_step4, dep.getJ_status_step4()+"");
		contentValues.put(DataBaseHelper1.j_status_step5, dep.getJ_status_step5()+"");


		long id=db.insert(DataBaseHelper1.TABLE_JOURNEY, null, contentValues);
		return id;
	}


	public List<JourneyPOJO> getAllJourneyData(){
		SQLiteDatabase db=helper.getWritableDatabase();


		List<JourneyPOJO> lst=new ArrayList<>();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.j_id,
				DataBaseHelper1.j_user_id,
				DataBaseHelper1.j_user_name,
				DataBaseHelper1.j_journey_name,
				DataBaseHelper1.j_total_events,
				DataBaseHelper1.j_total_events_achived,
				DataBaseHelper1.j_status_step1,
				DataBaseHelper1.j_status_step2,
				DataBaseHelper1.j_status_step3,
				DataBaseHelper1.j_status_step4,
				DataBaseHelper1.j_status_step5
		};
		Cursor cursor=db.query(DataBaseHelper1.TABLE_JOURNEY, columns, null, null, null, null, null);

		while(cursor.moveToNext()){
			String j_id=cursor.getString(1);
			String j_user_id=cursor.getString(2);
			String j_user_name=cursor.getString(3);
			String j_journey_name=cursor.getString(4);
			String j_total_events=cursor.getString(5);
			String j_total_events_achived=cursor.getString(6);
			String j_status_step1=cursor.getString(7);
			String j_status_step2=cursor.getString(8);
			String j_status_step3=cursor.getString(9);
			String j_status_step4=cursor.getString(10);
			String j_status_step5=cursor.getString(11);


			JourneyPOJO obj=new JourneyPOJO();
			obj.setJ_id(j_id);
			obj.setJ_user_id(j_user_id);
			obj.setJ_user_name(j_user_name);
			obj.setJ_journey_name(j_journey_name);
			obj.setJ_total_events(j_total_events);
			obj.setJ_total_events_achived(j_total_events_achived);
			obj.setJ_status_step1(j_status_step1);
			obj.setJ_status_step2(j_status_step2);
			obj.setJ_status_step3(j_status_step3);
			obj.setJ_status_step4(j_status_step4);
			obj.setJ_status_step5(j_status_step5);

			lst.add(obj);
		}
		return lst;
	}

	public JourneyPOJO getJourneyData(String user_name1,String journey_name1){
		SQLiteDatabase db=helper.getWritableDatabase();

		JourneyPOJO obj=null;
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.j_id,
				DataBaseHelper1.j_user_id,
				DataBaseHelper1.j_user_name,
				DataBaseHelper1.j_journey_name,
				DataBaseHelper1.j_total_events,
				DataBaseHelper1.j_total_events_achived,
				DataBaseHelper1.j_status_step1,
				DataBaseHelper1.j_status_step2,
				DataBaseHelper1.j_status_step3,
				DataBaseHelper1.j_status_step4,
				DataBaseHelper1.j_status_step5
		};
		String WHERE =  DataBaseHelper1.j_user_name+"='"+user_name1+"' OR "+ DataBaseHelper1.j_journey_name+"='"+journey_name1+"' ";
		Cursor cursor=db.query(DataBaseHelper1.TABLE_JOURNEY, columns, WHERE, null, null, null, null);

		while(cursor.moveToNext()){
			String j_id=cursor.getString(1);
			String j_user_id=cursor.getString(2);
			String j_user_name=cursor.getString(3);
			String j_journey_name=cursor.getString(4);
			String j_total_events=cursor.getString(5);
			String j_total_events_achived=cursor.getString(6);
			String j_status_step1=cursor.getString(7);
			String j_status_step2=cursor.getString(8);
			String j_status_step3=cursor.getString(9);
			String j_status_step4=cursor.getString(10);
			String j_status_step5=cursor.getString(11);

			obj=new JourneyPOJO();
			obj.setJ_id(j_id);
			obj.setJ_user_id(j_user_id);
			obj.setJ_user_name(j_user_name);
			obj.setJ_journey_name(j_journey_name);
			obj.setJ_total_events(j_total_events);
			obj.setJ_total_events_achived(j_total_events_achived);
			obj.setJ_status_step1(j_status_step1);
			obj.setJ_status_step2(j_status_step2);
			obj.setJ_status_step3(j_status_step3);
			obj.setJ_status_step4(j_status_step4);
			obj.setJ_status_step5(j_status_step5);
		}
		return obj;
	}

//	public List<JourneyPOJO> UpdateJourneyData(JourneyPOJO pojo){
//
////		//UPDATE TABLE SET Name='vav' where Name=?
////		SQLiteDatabase db=helper.getWritableDatabase();
////		ContentValues contentValues=new ContentValues();
////		contentValues.put(DataBaseHelper.STORE_START_TIME,start_time);
////		String[] whereArgs={store_id};
////		int count=db.update(DataBaseHelper.STORE_TABLE,contentValues,DataBaseHelper.STORE_ID+" =? ",whereArgs);
////		return count;
//
//
//		SQLiteDatabase db=helper.getWritableDatabase();
//
//
//		List<JourneyPOJO> lst=new ArrayList<>();
//		String[] columns={DataBaseHelper1.ID,
//				DataBaseHelper1.j_id,
//				DataBaseHelper1.j_user_id,
//				DataBaseHelper1.j_user_name,
//				DataBaseHelper1.j_journey_name,
//				DataBaseHelper1.j_total_events,
//				DataBaseHelper1.j_total_events_achived,
//				DataBaseHelper1.j_status_step1,
//				DataBaseHelper1.j_status_step2,
//				DataBaseHelper1.j_status_step3,
//				DataBaseHelper1.j_status_step4,
//				DataBaseHelper1.j_status_step5
//		};
//		Cursor cursor=db.query(DataBaseHelper1.TABLE_JOURNEY, columns, null, null, null, null, null);
//
//		while(cursor.moveToNext()){
//			String j_id=cursor.getString(1);
//			String j_user_id=cursor.getString(2);
//			String j_user_name=cursor.getString(3);
//			String j_journey_name=cursor.getString(4);
//			String j_total_events=cursor.getString(5);
//			String j_total_events_achived=cursor.getString(6);
//			String j_status_step1=cursor.getString(7);
//			String j_status_step2=cursor.getString(8);
//			String j_status_step3=cursor.getString(9);
//			String j_status_step4=cursor.getString(10);
//			String j_status_step5=cursor.getString(11);
//
//
//			JourneyPOJO obj=new JourneyPOJO();
//			obj.setJ_id(j_id);
//			obj.setJ_user_id(j_user_id);
//			obj.setJ_user_name(j_user_name);
//			obj.setJ_journey_name(j_journey_name);
//			obj.setJ_total_events(j_total_events);
//			obj.setJ_total_events_achived(j_total_events_achived);
//			obj.setJ_status_step1(j_status_step1);
//			obj.setJ_status_step2(j_status_step2);
//			obj.setJ_status_step3(j_status_step3);
//			obj.setJ_status_step4(j_status_step4);
//			obj.setJ_status_step5(j_status_step5);
//
//			lst.add(obj);
//		}
//		return lst;
//	}
//	public JourneyPOJO getJourneyData(String reminder_id1){
//		SQLiteDatabase db=helper.getWritableDatabase();
//		String[] columns={DataBaseHelper1.ID,
//				DataBaseHelper1.j_id,
//				DataBaseHelper1.j_user_id,
//				DataBaseHelper1.j_user_name,
//				DataBaseHelper1.j_journey_name,
//				DataBaseHelper1.j_total_events,
//				DataBaseHelper1.j_total_events_achived,
//				DataBaseHelper1.j_status_step1,
//				DataBaseHelper1.j_status_step2,
//				DataBaseHelper1.j_status_step3,
//				DataBaseHelper1.j_status_step4,
//				DataBaseHelper1.j_status_step5
//		};
//		Cursor cursor=db.query(DataBaseHelper1.TABLE_REMINDER_DESC, columns, REMINDER_DESC_ID+" = '"+reminder_id1+"'", null, null, null, null);
//		JourneyPOJO obj=new JourneyPOJO();
//		while(cursor.moveToNext()){
//			String j_id=cursor.getString(1);
//			String j_user_id=cursor.getString(2);
//			String j_user_name=cursor.getString(3);
//			String j_journey_name=cursor.getString(4);
//			String j_total_events=cursor.getString(5);
//			String j_total_events_achived=cursor.getString(6);
//			String j_status_step1=cursor.getString(7);
//			String j_status_step2=cursor.getString(8);
//			String j_status_step3=cursor.getString(9);
//			String j_status_step4=cursor.getString(10);
//			String j_status_step5=cursor.getString(11);
//
//
//			obj.setJ_id(j_id);
//			obj.setJ_user_id(j_user_id);
//			obj.setJ_user_name(j_user_name);
//			obj.setJ_journey_name(j_journey_name);
//			obj.setJ_total_events(j_total_events);
//			obj.setJ_total_events_achived(j_total_events_achived);
//			obj.setJ_status_step1(j_status_step1);
//			obj.setJ_status_step2(j_status_step2);
//			obj.setJ_status_step3(j_status_step3);
//			obj.setJ_status_step4(j_status_step4);
//			obj.setJ_status_step5(j_status_step5);
//		}
//		return obj;
//	}

//	public int updateName(String old_medicine_name,String new_medicine_name){
//		SQLiteDatabase db=helper.getWritableDatabase();
//		ContentValues contentValues=new ContentValues();
//		contentValues.put(DataBaseHelper1.MEDICINE_NAME, new_medicine_name);
//		String[] whereArgs={old_medicine_name};
//		int count=db.update(DataBaseHelper1.RITUAL_TIMELINE_TABLE, contentValues, DataBaseHelper1.MEDICINE_NAME+" =? ",whereArgs );
//		return count;
//	}

	public int deleteJourneyDATA(String reminder_id){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] whereArgs={reminder_id};
		int count=db.delete(DataBaseHelper1.TABLE_JOURNEY, DataBaseHelper1.TABLE_REMINDER+"=?", whereArgs);
		return count;
	}



	/*---------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	 */


	public long insertreminderDescData(ReminderDescPOJO dep){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues=new ContentValues();

		contentValues.put(DataBaseHelper1.REMINDER_DESC_ID, dep.getRem_desc_id()+"");
		contentValues.put(DataBaseHelper1.REMINDER_DESC_USER_ID, dep.getRem_desc_user_id()+"");
		contentValues.put(DataBaseHelper1.REMINDER_DESC_USER_NAME, dep.getRem_desc_user_name()+"");
		contentValues.put(DataBaseHelper1.REMINDER_DESC_TIME, dep.getRem_desc_time()+"");
		contentValues.put(DataBaseHelper1.REMINDER_DESC_DAY, dep.getRem_desc_day()+"");
		contentValues.put(DataBaseHelper1.REMINDER_DESC_ON_OFF, dep.getRem_desc_on_off()+"");
		contentValues.put(DataBaseHelper1.REMINDER_DESC_STAMP, dep.getRem_desc_stamp()+"");
		contentValues.put(DataBaseHelper1.REMINDER_DESC_REMINDER_ID, dep.getRem_desc_rem_id()+"");


		long id=db.insert(DataBaseHelper1.TABLE_REMINDER_DESC, null, contentValues);
		return id;
	}

	public List<ReminderDescPOJO> getAllReminderDescData(){
		SQLiteDatabase db=helper.getWritableDatabase();


		List<ReminderDescPOJO> lst=new ArrayList<>();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.REMINDER_DESC_ID,
				DataBaseHelper1.REMINDER_DESC_USER_ID,
				DataBaseHelper1.REMINDER_DESC_USER_NAME,
				DataBaseHelper1.REMINDER_DESC_TIME,
				DataBaseHelper1.REMINDER_DESC_DAY,
				DataBaseHelper1.REMINDER_DESC_ON_OFF,
				DataBaseHelper1.REMINDER_DESC_STAMP,
				DataBaseHelper1.REMINDER_DESC_REMINDER_ID
		};
		Cursor cursor=db.query(DataBaseHelper1.TABLE_REMINDER_DESC, columns, null, null, null, null, null);

		while(cursor.moveToNext()){
			String REMINDER_DESC_ID=cursor.getString(1);
			String REMINDER_DESC_USER_ID=cursor.getString(2);
			String REMINDER_DESC_USER_NAME=cursor.getString(3);
			String REMINDER_DESC_TIME=cursor.getString(4);
			String REMINDER_DESC_DAY=cursor.getString(5);
			String REMINDER_DESC_ON_OFF=cursor.getString(6);
			String REMINDER_DESC_STAMP=cursor.getString(7);
			String REMINDER_DESC_REMINDER_ID=cursor.getString(8);


			ReminderDescPOJO obj=new ReminderDescPOJO();
			obj.setRem_desc_id(REMINDER_DESC_ID);
			obj.setRem_desc_user_id(REMINDER_DESC_USER_ID);
			obj.setRem_desc_user_name(REMINDER_DESC_USER_NAME);
			obj.setRem_desc_time(REMINDER_DESC_TIME);
			obj.setRem_desc_day(REMINDER_DESC_DAY);
			obj.setRem_desc_on_off(REMINDER_DESC_ON_OFF);
			obj.setRem_desc_stamp(REMINDER_DESC_STAMP);
			obj.setRem_desc_rem_id(REMINDER_DESC_REMINDER_ID);

			lst.add(obj);
		}
		return lst;
	}
	public ReminderDescPOJO getReminderDescData(String reminder_id1){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.REMINDER_DESC_ID,
				DataBaseHelper1.REMINDER_DESC_USER_ID,
				DataBaseHelper1.REMINDER_DESC_USER_NAME,
				DataBaseHelper1.REMINDER_DESC_TIME,
				DataBaseHelper1.REMINDER_DESC_DAY,
				DataBaseHelper1.REMINDER_DESC_ON_OFF,
				DataBaseHelper1.REMINDER_DESC_STAMP,
				DataBaseHelper1.REMINDER_DESC_REMINDER_ID
		};
		Cursor cursor=db.query(DataBaseHelper1.TABLE_REMINDER_DESC, columns, DataBaseHelper1.REMINDER_DESC_REMINDER_ID+" = '"+reminder_id1+"'", null, null, null, null);
		ReminderDescPOJO obj=new ReminderDescPOJO();
		while(cursor.moveToNext()){
			String REMINDER_DESC_ID=cursor.getString(1);
			String REMINDER_DESC_USER_ID=cursor.getString(2);
			String REMINDER_DESC_USER_NAME=cursor.getString(3);
			String REMINDER_DESC_TIME=cursor.getString(4);
			String REMINDER_DESC_DAY=cursor.getString(5);
			String REMINDER_DESC_ON_OFF=cursor.getString(6);
			String REMINDER_DESC_STAMP=cursor.getString(7);
			String REMINDER_DESC_REMINDER_ID=cursor.getString(8);


			obj.setRem_desc_id(REMINDER_DESC_ID);
			obj.setRem_desc_user_id(REMINDER_DESC_USER_ID);
			obj.setRem_desc_user_name(REMINDER_DESC_USER_NAME);
			obj.setRem_desc_time(REMINDER_DESC_TIME);
			obj.setRem_desc_day(REMINDER_DESC_DAY);
			obj.setRem_desc_on_off(REMINDER_DESC_ON_OFF);
			obj.setRem_desc_stamp(REMINDER_DESC_STAMP);
			obj.setRem_desc_rem_id(REMINDER_DESC_REMINDER_ID);
		}
		return obj;
	}
	public ReminderDescPOJO getReminderDescData(String reminder_id1,String day){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.REMINDER_DESC_ID,
				DataBaseHelper1.REMINDER_DESC_USER_ID,
				DataBaseHelper1.REMINDER_DESC_USER_NAME,
				DataBaseHelper1.REMINDER_DESC_TIME,
				DataBaseHelper1.REMINDER_DESC_DAY,
				DataBaseHelper1.REMINDER_DESC_ON_OFF,
				DataBaseHelper1.REMINDER_DESC_STAMP,
				DataBaseHelper1.REMINDER_DESC_REMINDER_ID
		};
		String WHERE =  DataBaseHelper1.REMINDER_DESC_REMINDER_ID+"='"+reminder_id1+"' OR "+ DataBaseHelper1.REMINDER_DESC_DAY+"='"+day+"' ";
		Cursor cursor=db.query(DataBaseHelper1.TABLE_REMINDER_DESC, columns, WHERE, null, null, null, null);
		ReminderDescPOJO obj=new ReminderDescPOJO();
		while(cursor.moveToNext()){
			String REMINDER_DESC_ID=cursor.getString(1);
			String REMINDER_DESC_USER_ID=cursor.getString(2);
			String REMINDER_DESC_USER_NAME=cursor.getString(3);
			String REMINDER_DESC_TIME=cursor.getString(4);
			String REMINDER_DESC_DAY=cursor.getString(5);
			String REMINDER_DESC_ON_OFF=cursor.getString(6);
			String REMINDER_DESC_STAMP=cursor.getString(7);
			String REMINDER_DESC_REMINDER_ID=cursor.getString(8);


			obj.setRem_desc_id(REMINDER_DESC_ID);
			obj.setRem_desc_user_id(REMINDER_DESC_USER_ID);
			obj.setRem_desc_user_name(REMINDER_DESC_USER_NAME);
			obj.setRem_desc_time(REMINDER_DESC_TIME);
			obj.setRem_desc_day(REMINDER_DESC_DAY);
			obj.setRem_desc_on_off(REMINDER_DESC_ON_OFF);
			obj.setRem_desc_stamp(REMINDER_DESC_STAMP);
			obj.setRem_desc_rem_id(REMINDER_DESC_REMINDER_ID);
		}
		return obj;
	}
	public ReminderDescPOJO getReminderDescDataByurid(String user_name1,String reminder_id1){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.REMINDER_DESC_ID,
				DataBaseHelper1.REMINDER_DESC_USER_ID,
				DataBaseHelper1.REMINDER_DESC_USER_NAME,
				DataBaseHelper1.REMINDER_DESC_TIME,
				DataBaseHelper1.REMINDER_DESC_DAY,
				DataBaseHelper1.REMINDER_DESC_ON_OFF,
				DataBaseHelper1.REMINDER_DESC_STAMP,
				DataBaseHelper1.REMINDER_DESC_REMINDER_ID
		};
		String WHERE =  DataBaseHelper1.REMINDER_DESC_REMINDER_ID+"='"+reminder_id1+"' OR "+ DataBaseHelper1.REMINDER_DESC_USER_NAME+"='"+user_name1+"' ";
		Cursor cursor=db.query(DataBaseHelper1.TABLE_REMINDER_DESC, columns, WHERE, null, null, null, null);
		ReminderDescPOJO obj=new ReminderDescPOJO();
		while(cursor.moveToNext()){
			String REMINDER_DESC_ID=cursor.getString(1);
			String REMINDER_DESC_USER_ID=cursor.getString(2);
			String REMINDER_DESC_USER_NAME=cursor.getString(3);
			String REMINDER_DESC_TIME=cursor.getString(4);
			String REMINDER_DESC_DAY=cursor.getString(5);
			String REMINDER_DESC_ON_OFF=cursor.getString(6);
			String REMINDER_DESC_STAMP=cursor.getString(7);
			String REMINDER_DESC_REMINDER_ID=cursor.getString(8);


			obj.setRem_desc_id(REMINDER_DESC_ID);
			obj.setRem_desc_user_id(REMINDER_DESC_USER_ID);
			obj.setRem_desc_user_name(REMINDER_DESC_USER_NAME);
			obj.setRem_desc_time(REMINDER_DESC_TIME);
			obj.setRem_desc_day(REMINDER_DESC_DAY);
			obj.setRem_desc_on_off(REMINDER_DESC_ON_OFF);
			obj.setRem_desc_stamp(REMINDER_DESC_STAMP);
			obj.setRem_desc_rem_id(REMINDER_DESC_REMINDER_ID);
		}
		return obj;
	}


//	public int updateName(String old_medicine_name,String new_medicine_name){
//		SQLiteDatabase db=helper.getWritableDatabase();
//		ContentValues contentValues=new ContentValues();
//		contentValues.put(DataBaseHelper1.MEDICINE_NAME, new_medicine_name);
//		String[] whereArgs={old_medicine_name};
//		int count=db.update(DataBaseHelper1.RITUAL_TIMELINE_TABLE, contentValues, DataBaseHelper1.MEDICINE_NAME+" =? ",whereArgs );
//		return count;
//	}

	public int deleteReminderDescDATA(String reminder_id){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] whereArgs={reminder_id};
		int count=db.delete(DataBaseHelper1.TABLE_REMINDER_DESC, DataBaseHelper1.TABLE_REMINDER+"=?", whereArgs);
		return count;
	}



	/*---------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	 */


	public long insertreminderData(ReminderPOJO dep){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues=new ContentValues();




		contentValues.put(DataBaseHelper1.REM_ID, dep.getRem_id()+"");
		contentValues.put(DataBaseHelper1.REMINDER_USER_ID, dep.getReminder_user_id()+"");
		contentValues.put(DataBaseHelper1.REMINDER_REM_ID, dep.getReminder_id()+"");
		contentValues.put(DataBaseHelper1.REMINDER_USER_NAME, dep.getReminder_user_name()+"");
		contentValues.put(DataBaseHelper1.REMINDER_REM_TIME, dep.getReminder_time()+"");
		contentValues.put(DataBaseHelper1.REMINDER_REM_RITUAL_RITUAL_TYPE, dep.getReminder_ritual_type()+"");
		contentValues.put(DataBaseHelper1.REMINDER_REM_SNOOZE_TIME, dep.getReminder_snooze_time()+"");


		long id=db.insert(DataBaseHelper1.TABLE_REMINDER, null, contentValues);
		return id;
	}

	public List<ReminderPOJO> getAllReminderData(){
		SQLiteDatabase db=helper.getWritableDatabase();


		List<ReminderPOJO> lst=new ArrayList<>();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.REM_ID,
				DataBaseHelper1.REMINDER_USER_ID,
				DataBaseHelper1.REMINDER_REM_ID,
				DataBaseHelper1.REMINDER_USER_NAME,
				DataBaseHelper1.REMINDER_REM_TIME,
				DataBaseHelper1.REMINDER_REM_RITUAL_RITUAL_TYPE,
				DataBaseHelper1.REMINDER_REM_SNOOZE_TIME
		};
		Cursor cursor=db.query(DataBaseHelper1.TABLE_REMINDER, columns, null, null, null, null, null);

		while(cursor.moveToNext()){
			String rem_id=cursor.getString(1);
			String reminder_user_id=cursor.getString(2);
			String reminder_id=cursor.getString(3);
			String reminder_user_name=cursor.getString(4);
			String reminder_time=cursor.getString(5);
			String reminder_ritual_type=cursor.getString(6);
			String reminder_snooze_time=cursor.getString(7);


			ReminderPOJO obj=new ReminderPOJO();
			obj.setRem_id(rem_id);
			obj.setReminder_id(reminder_id);
			obj.setReminder_user_id(reminder_user_id);
			obj.setReminder_user_name(reminder_user_name);
			obj.setReminder_time(reminder_time);
			obj.setReminder_ritual_type(reminder_ritual_type);
			obj.setReminder_snooze_time(reminder_snooze_time);

			lst.add(obj);
		}
		return lst;
	}
	public ReminderPOJO getReminderData(String reminder_id1,String user_name1){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.REM_ID,
				DataBaseHelper1.REMINDER_USER_ID,
				DataBaseHelper1.REMINDER_REM_ID,
				DataBaseHelper1.REMINDER_USER_NAME,
				DataBaseHelper1.REMINDER_REM_TIME,
				DataBaseHelper1.REMINDER_REM_RITUAL_RITUAL_TYPE,
				DataBaseHelper1.REMINDER_REM_SNOOZE_TIME
		};
		String WHERE =  DataBaseHelper1.REMINDER_REM_ID+"='"+reminder_id1+"' OR "+DataBaseHelper1.REMINDER_USER_NAME+"='"+user_name1+"' ";
		Cursor cursor=db.query(DataBaseHelper1.TABLE_REMINDER, columns, WHERE, null, null, null, null);
		ReminderPOJO obj=new ReminderPOJO();
		while(cursor.moveToNext()){
			String rem_id=cursor.getString(1);
			String reminder_user_id=cursor.getString(2);
			String reminder_id=cursor.getString(3);
			String reminder_user_name=cursor.getString(4);
			String reminder_time=cursor.getString(5);
			String reminder_ritual_type=cursor.getString(6);
			String reminder_snooze_time=cursor.getString(7);


			obj.setRem_id(rem_id);
			obj.setReminder_id(reminder_id);
			obj.setReminder_user_id(reminder_user_id);
			obj.setReminder_user_name(reminder_user_name);
			obj.setReminder_time(reminder_time);
			obj.setReminder_ritual_type(reminder_ritual_type);
			obj.setReminder_snooze_time(reminder_snooze_time);

		}
		return obj;
	}

//	public int updateName(String old_medicine_name,String new_medicine_name){
//		SQLiteDatabase db=helper.getWritableDatabase();
//		ContentValues contentValues=new ContentValues();
//		contentValues.put(DataBaseHelper1.MEDICINE_NAME, new_medicine_name);
//		String[] whereArgs={old_medicine_name};
//		int count=db.update(DataBaseHelper1.RITUAL_TIMELINE_TABLE, contentValues, DataBaseHelper1.MEDICINE_NAME+" =? ",whereArgs );
//		return count;
//	}

	public int deleteReminderDATA(String reminder_id){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] whereArgs={reminder_id};
		int count=db.delete(DataBaseHelper1.TABLE_NAME, DataBaseHelper1.TABLE_REMINDER+"=?", whereArgs);
		return count;
	}



	/*---------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	 */

	public long inserttimelineData(HabitTimelinePOJO dep){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues=new ContentValues();

		contentValues.put(DataBaseHelper1.TIMELINE_ID, dep.getTimeline_id()+"");
		contentValues.put(DataBaseHelper1.TIMELINE_USER_ID, dep.getTimeline_user_id()+"");
		contentValues.put(DataBaseHelper1.TIMELINE_USER_NAME, dep.getTimeline_user_name()+"");
		contentValues.put(DataBaseHelper1.TIMELINE_RITUAL_TYPE, dep.getTimeline_ritual_type()+"");
		contentValues.put(DataBaseHelper1.TIMELINE_DATE_OF_STATUS, dep.getTimeline_date_ofstatus()+"");
		contentValues.put(DataBaseHelper1.TIMELINE_TOTAL_HABITS, dep.getTimeline_total_habits()+"");
		contentValues.put(DataBaseHelper1.TIMELINE_HABITCOMPLETED, dep.getTimeline_habitcompleted()+"");


		long id=db.insert(DataBaseHelper1.RITUAL_TIMELINE_TABLE, null, contentValues);
		return id;
	}

	public List<HabitTimelinePOJO> getAlltimelinehabitData(){
		SQLiteDatabase db=helper.getWritableDatabase();


		List<HabitTimelinePOJO> lst=new ArrayList<>();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.TIMELINE_ID,
				DataBaseHelper1.TIMELINE_USER_ID,
				DataBaseHelper1.TIMELINE_USER_NAME,
				DataBaseHelper1.TIMELINE_RITUAL_TYPE,
				DataBaseHelper1.TIMELINE_DATE_OF_STATUS,
				DataBaseHelper1.TIMELINE_TOTAL_HABITS,
				DataBaseHelper1.TIMELINE_HABITCOMPLETED
		};
		Cursor cursor=db.query(DataBaseHelper1.RITUAL_TIMELINE_TABLE, columns, null, null, null, null, null);

		while(cursor.moveToNext()){
			String timeline_id=cursor.getString(1);
			String timeline_user_id=cursor.getString(2);
			String timeline_user_name=cursor.getString(3);
			String timeline_ritual_type=cursor.getString(4);
			String timelint_date_of_status=cursor.getString(5);
			String timeline_total_habits=cursor.getString(6);
			String timelint_habit_completed=cursor.getString(7);


			HabitTimelinePOJO obj=new HabitTimelinePOJO();
			obj.setTimeline_id(timeline_id);
			obj.setTimeline_user_id(timeline_user_id);
			obj.setTimeline_user_name(timeline_user_name);
			obj.setTimeline_ritual_type(timeline_ritual_type);
			obj.setTimeline_date_ofstatus(timelint_date_of_status);
			obj.setTimeline_total_habits(timelint_date_of_status);
			obj.setTimeline_habitcompleted(timelint_habit_completed);

			lst.add(obj);
		}
		return lst;
	}
	public HabitTimelinePOJO getHabitTimeLineData(String ritual_type1){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.TIMELINE_ID,
				DataBaseHelper1.TIMELINE_USER_ID,
				DataBaseHelper1.TIMELINE_USER_NAME,
				DataBaseHelper1.TIMELINE_RITUAL_TYPE,
				DataBaseHelper1.TIMELINE_DATE_OF_STATUS,
				DataBaseHelper1.TIMELINE_TOTAL_HABITS,
				DataBaseHelper1.TIMELINE_HABITCOMPLETED
		};
		Cursor cursor=db.query(DataBaseHelper1.RITUAL_TIMELINE_TABLE, columns, DataBaseHelper1.TIMELINE_RITUAL_TYPE+" = '"+ritual_type1+"'", null, null, null, null);
		HabitTimelinePOJO obj=new HabitTimelinePOJO();
		while(cursor.moveToNext()){
			String timeline_id=cursor.getString(1);
			String timeline_user_id=cursor.getString(2);
			String timeline_user_name=cursor.getString(3);
			String timeline_ritual_type=cursor.getString(4);
			String timelint_date_of_status=cursor.getString(5);
			String timeline_total_habits=cursor.getString(6);
			String timelint_habit_completed=cursor.getString(7);


			obj.setTimeline_id(timeline_id);
			obj.setTimeline_user_id(timeline_user_id);
			obj.setTimeline_user_name(timeline_user_name);
			obj.setTimeline_ritual_type(timeline_ritual_type);
			obj.setTimeline_date_ofstatus(timelint_date_of_status);
			obj.setTimeline_total_habits(timelint_date_of_status);
			obj.setTimeline_habitcompleted(timelint_habit_completed);
		}
		return obj;
	}

//	public int updateName(String old_medicine_name,String new_medicine_name){
//		SQLiteDatabase db=helper.getWritableDatabase();
//		ContentValues contentValues=new ContentValues();
//		contentValues.put(DataBaseHelper1.MEDICINE_NAME, new_medicine_name);
//		String[] whereArgs={old_medicine_name};
//		int count=db.update(DataBaseHelper1.RITUAL_TIMELINE_TABLE, contentValues, DataBaseHelper1.MEDICINE_NAME+" =? ",whereArgs );
//		return count;
//	}

	public int deletetimelinehabitDATA(String ritual_type){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] whereArgs={ritual_type};
		int count=db.delete(DataBaseHelper1.TABLE_NAME, DataBaseHelper1.TIMELINE_RITUAL_TYPE+"=?", whereArgs);
		return count;
	}



	/*---------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	-----------------------------------------------------------------------------------------------------
	 */
	public long insertData(DataEntryPOJO dep){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues=new ContentValues();
		contentValues.put(DataBaseHelper1.ACTIVITY_MINUTES, dep.getActivity_minutes()+"");
		contentValues.put(DataBaseHelper1.GLUCOSE_NUMBER, dep.getGlucose_no()+"");
		contentValues.put(DataBaseHelper1.GLUCOSE_TIME, dep.getGlucose_time()+"");
		contentValues.put(DataBaseHelper1.FOOD_NAME, dep.getFood_name()+"");
		contentValues.put(DataBaseHelper1.FOOD_QUANTITY, dep.getFood_quantity()+"");
		contentValues.put(DataBaseHelper1.MEDICINE_NAME, dep.getMedicine_name()+"");
		contentValues.put(DataBaseHelper1.MEDICINE_QUANTITIY, dep.getMedicine_quantity()+"");
		contentValues.put(DataBaseHelper1.MEDICINE_TYPE, dep.getMedicine_type()+"");
		contentValues.put(DataBaseHelper1.TIME, dep.getTime()+"");
		contentValues.put(DataBaseHelper1.DATE, dep.getDate()+"");
		contentValues.put(DataBaseHelper1.RITUAL, dep.getRitual()+"");
		
		
		long id=db.insert(DataBaseHelper1.TABLE_NAME, null, contentValues);
		return id;
	}
	public List<DataEntryPOJO> getData(String ritual1){
		List<DataEntryPOJO> lst=new ArrayList<>();
		SQLiteDatabase db=helper.getWritableDatabase(); 
		String[] columns={DataBaseHelper1.ID,DataBaseHelper1.ACTIVITY_MINUTES,DataBaseHelper1.GLUCOSE_NUMBER,
				DataBaseHelper1.GLUCOSE_TIME,DataBaseHelper1.FOOD_NAME,DataBaseHelper1.FOOD_QUANTITY,
				DataBaseHelper1.MEDICINE_NAME,DataBaseHelper1.MEDICINE_QUANTITIY,DataBaseHelper1.MEDICINE_TYPE
				,DataBaseHelper1.TIME,DataBaseHelper1.DATE,DataBaseHelper1.RITUAL};
		Cursor cursor=db.query(DataBaseHelper1.TABLE_NAME, columns, DataBaseHelper1.RITUAL+" = '"+ritual1+"'", null, null, null, null);
		while(cursor.moveToNext()){
			String activity_minutes=cursor.getString(1);
			String glucose_number=cursor.getString(2);
			String glucose_time=cursor.getString(3);
			String food_name=cursor.getString(4);
			String food_quantity=cursor.getString(5);
			String medicine_name=cursor.getString(6);
			String medicine_quantity=cursor.getString(7);
			String medicine_type=cursor.getString(8);
			String time1=cursor.getString(9);
			String date=cursor.getString(10);
			String ritual=cursor.getString(11);
			
			DataEntryPOJO obj=new DataEntryPOJO();
			obj.setActivity_minutes(Integer.parseInt(activity_minutes));
			obj.setFood_name(food_name);
			obj.setGlucose_no(Integer.parseInt(glucose_number));
			obj.setGlucose_time(glucose_time);
			obj.setMedicine_name(medicine_name);
			obj.setMedicine_quantity(Integer.parseInt(medicine_quantity));
			obj.setMedicine_type(medicine_type);
			obj.setFood_quantity(Integer.parseInt(food_quantity));
			obj.setTime(time1);
			obj.setDate(date);
			obj.setRitual(ritual);
			lst.add(obj);
		}
//		else{
//			return null;
//		}
		return lst;
		
	}
	
	public List<DataEntryPOJO> getAllData(){
		SQLiteDatabase db=helper.getWritableDatabase(); 
		
		
		List<DataEntryPOJO> lst=new ArrayList<>();
		String[] columns={DataBaseHelper1.ID,DataBaseHelper1.ACTIVITY_MINUTES,DataBaseHelper1.GLUCOSE_NUMBER,
							DataBaseHelper1.GLUCOSE_TIME,DataBaseHelper1.FOOD_NAME,DataBaseHelper1.FOOD_QUANTITY,
							DataBaseHelper1.MEDICINE_NAME,DataBaseHelper1.MEDICINE_QUANTITIY,DataBaseHelper1.MEDICINE_TYPE
							,DataBaseHelper1.TIME,DataBaseHelper1.DATE,DataBaseHelper1.RITUAL};
		Cursor cursor=db.query(DataBaseHelper1.TABLE_NAME, columns, null, null, null, null, null);
		
		while(cursor.moveToNext()){
			String activity_minutes=cursor.getString(1);
			String glucose_number=cursor.getString(2);
			String glucose_time=cursor.getString(3);
			String food_name=cursor.getString(4);
			String food_quantity=cursor.getString(5);
			String medicine_name=cursor.getString(6);
			String medicine_quantity=cursor.getString(7);
			String medicine_type=cursor.getString(8);
			String time=cursor.getString(9);
			String date=cursor.getString(10);
			String ritual=cursor.getString(11);
			
			DataEntryPOJO obj=new DataEntryPOJO();
			obj.setActivity_minutes(Integer.parseInt(activity_minutes));
			obj.setFood_name(food_name);
			obj.setGlucose_no(Integer.parseInt(glucose_number));
			obj.setGlucose_time(glucose_time);
			obj.setMedicine_name(medicine_name);
			obj.setMedicine_quantity(Integer.parseInt(medicine_quantity));
			obj.setMedicine_type(medicine_type);
			obj.setFood_quantity(Integer.parseInt(food_quantity));
			obj.setTime(time);
			obj.setDate(date);
			obj.setRitual(ritual);
			
			lst.add(obj);
		}
		return lst;
		
	}
	
	public int updateName(String old_medicine_name,String new_medicine_name){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues=new ContentValues();
		contentValues.put(DataBaseHelper1.MEDICINE_NAME, new_medicine_name);
		String[] whereArgs={old_medicine_name};
		int count=db.update(DataBaseHelper1.TABLE_NAME, contentValues, DataBaseHelper1.MEDICINE_NAME+" =? ",whereArgs );
		return count;
	}
	public int deleteName(String medicine_name){
		SQLiteDatabase db=helper.getWritableDatabase();
		String[] whereArgs={medicine_name};
		int count=db.delete(DataBaseHelper1.TABLE_NAME, DataBaseHelper1.MEDICINE_NAME+"=?", whereArgs);
		return count;
	}


	/*
	//music crud
	--------------------------------------------------------------------------------------------------
	*/





	public long insertMusicData(MusicPOJO dep){
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues contentValues=new ContentValues();

		contentValues.put(DataBaseHelper1.MUSIC_ID, dep.getMusic_id()+"");
		contentValues.put(DataBaseHelper1.MUSIC_TITLE, dep.getMusic_title()+"");
		contentValues.put(DataBaseHelper1.MUSIC_DESCRIPTION, dep.getMusic_description()+"");
		contentValues.put(DataBaseHelper1.MUSIC_NAME, dep.getMusic_name()+"");
		contentValues.put(DataBaseHelper1.MUSIC_DATE, dep.getMusic_date()+"");
		contentValues.put(DataBaseHelper1.MUSIC_CATEGORY, dep.getMusic_category()+"");
		contentValues.put(DataBaseHelper1.MUSIC_FILE_PATH, dep.getMusic_file_path()+"");


		long id=db.insert(DataBaseHelper1.MUSIC_TABLE, null, contentValues);
		return id;
	}


	public List<MusicPOJO> getAllMusicData(){
		SQLiteDatabase db=helper.getWritableDatabase();


		List<MusicPOJO> lst=new ArrayList<>();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.MUSIC_ID,
				DataBaseHelper1.MUSIC_TITLE,
				DataBaseHelper1.MUSIC_DESCRIPTION,
				DataBaseHelper1.MUSIC_NAME,
				DataBaseHelper1.MUSIC_DATE,
				DataBaseHelper1.MUSIC_CATEGORY,
				DataBaseHelper1.MUSIC_FILE_PATH};
		Cursor cursor=db.query(DataBaseHelper1.MUSIC_TABLE, columns, null, null, null, null, null);

		while(cursor.moveToNext()){
			String music_id=cursor.getString(1);
			String music_title=cursor.getString(2);
			String music_description=cursor.getString(3);
			String music_name=cursor.getString(4);
			String music_date=cursor.getString(5);
			String music_category=cursor.getString(6);
			String music_file_path=cursor.getString(7);

			MusicPOJO obj=new MusicPOJO();
			obj.setMusic_id(music_id);
			obj.setMusic_title(music_title);
			obj.setMusic_description(music_description);
			obj.setMusic_name(music_name);
			obj.setMusic_date(music_date);
			obj.setMusic_category(music_category);
			obj.setMusic_file_path(music_file_path);

			lst.add(obj);
		}
		return lst;

	}
	public List<MusicPOJO> getMusicDataByMusicFileName(String file_name){
		SQLiteDatabase db=helper.getWritableDatabase();


		List<MusicPOJO> lst=new ArrayList<>();
		String[] columns={DataBaseHelper1.ID,
				DataBaseHelper1.MUSIC_ID,
				DataBaseHelper1.MUSIC_TITLE,
				DataBaseHelper1.MUSIC_DESCRIPTION,
				DataBaseHelper1.MUSIC_NAME,
				DataBaseHelper1.MUSIC_DATE,
				DataBaseHelper1.MUSIC_CATEGORY,
				DataBaseHelper1.MUSIC_FILE_PATH};
		Cursor cursor=db.query(DataBaseHelper1.MUSIC_TABLE, columns, DataBaseHelper1.MUSIC_NAME+" = \""+file_name+"\"", null, null, null, null);

		while(cursor.moveToNext()){
			String music_id=cursor.getString(1);
			String music_title=cursor.getString(2);
			String music_description=cursor.getString(3);
			String music_name=cursor.getString(4);
			String music_date=cursor.getString(5);
			String music_category=cursor.getString(6);
			String music_file_path=cursor.getString(7);

			MusicPOJO obj=new MusicPOJO();
			obj.setMusic_id(music_id);
			obj.setMusic_title(music_title);
			obj.setMusic_description(music_description);
			obj.setMusic_name(music_name);
			obj.setMusic_date(music_date);
			obj.setMusic_category(music_category);
			obj.setMusic_file_path(music_file_path);

			lst.add(obj);
		}
		return lst;
	}

//	public int deleteName(String medicine_name){
//		SQLiteDatabase db=helper.getWritableDatabase();
//		String[] whereArgs={medicine_name};
//		int count=db.delete(DataBaseHelper1.TABLE_NAME, DataBaseHelper1.MEDICINE_NAME+"=?", whereArgs);
//		return count;
//	}






	/*
	//music crud ends
	------------------------------------------------------------------------------------------------
	*/

	static class DataBaseHelper1 extends SQLiteOpenHelper{
		private static final String DATABASE_NAME="data_entry_database";
		private static final String TABLE_NAME="DATAENTRY";
		private static final String MUSIC_TABLE="musictable";
		private static final String RITUAL_TIMELINE_TABLE="ritual_table";
		private static final String TABLE_REMINDER_DESC="reminder_description";
		private static final String TABLE_REMINDER="reminder_table";
		private static final String TABLE_JOURNEY="journey_table";
		private static final String TABLE_JOURNEY_HABIT="table_journey_habit";
		private static final String TABLE_USER_HABIT="table_user_habits";
		private static final String TABLE_CUSTOM_HABIT="table_custom_habit";

		private static final int DATABASE_VERSION=14;
		
		private static final String ID="_id";
		private static final String ACTIVITY_MINUTES="activity_minutes";
		private static final String GLUCOSE_NUMBER="glucose_no";
		private static final String GLUCOSE_TIME="glucose_time";
		private static final String FOOD_NAME="food_name";
		private static final String FOOD_QUANTITY="food_quantity";
		private static final String MEDICINE_NAME="medicine_name";
		private static final String MEDICINE_QUANTITIY="medicine_quantity";
		private static final String MEDICINE_TYPE="medicine_type";
		private static final String TIME="time";
		private static final String DATE="date";
		private static final String RITUAL="ritual";

		private static final String MUSIC_ID="music_id";
		private static final String MUSIC_TITLE="music_title";
		private static final String MUSIC_DESCRIPTION="music_description";
		private static final String MUSIC_NAME="music_name";
		private static final String MUSIC_DATE="music_date";
		private static final String MUSIC_CATEGORY="music_category";
		private static final String MUSIC_FILE_PATH="music_file_path";

		//timelinetable columns
		private static final String TIMELINE_ID="timeline_id";
		private static final String TIMELINE_USER_ID="timeline_user_id";
		private static final String TIMELINE_USER_NAME="timeline_user_name";
		private static final String TIMELINE_RITUAL_TYPE="timeline_ritual_type";
		private static final String TIMELINE_DATE_OF_STATUS="timeline_dateof_status";
		private static final String TIMELINE_TOTAL_HABITS="timeline_total_habits";
		private static final String TIMELINE_HABITCOMPLETED="timeline_habitcompleted";

		//remindertable columns
		private static final String REM_ID="rem_id";
		private static final String REMINDER_USER_ID="rem_user_id";
		private static final String REMINDER_REM_ID="rem_rem_id";
		private static final String REMINDER_USER_NAME="rem_user_name";
		private static final String REMINDER_REM_TIME="rem_time";
		private static final String REMINDER_REM_RITUAL_RITUAL_TYPE="rem_ritual_type";
		private static final String REMINDER_REM_SNOOZE_TIME="rem_snooze_time";


		//remindertable columns
		private static final String REMINDER_DESC_ID="rem_desc_id";
		private static final String REMINDER_DESC_USER_ID="rem_desc_user_id";
		private static final String REMINDER_DESC_USER_NAME="rem_desc_user_name";
		private static final String REMINDER_DESC_TIME="rem_desc_time";
		private static final String REMINDER_DESC_DAY="rem_desc_day";
		private static final String REMINDER_DESC_ON_OFF="rem_desc_on_off";
		private static final String REMINDER_DESC_STAMP="rem_desc_stamp";
		private static final String REMINDER_DESC_REMINDER_ID="rem_desc_rem_id";


		//journeytable columns
		private static final String j_id="j_id";
		private static final String j_user_id="j_user_id";
		private static final String j_user_name="j_user_name";
		private static final String j_journey_name="j_journey_name";
		private static final String j_total_events="j_total_events";
		private static final String j_total_events_achived="j_total_events_achived";
		private static final String j_status_step1="j_status_step1";
		private static final String j_status_step2="j_status_step2";
		private static final String j_status_step3="j_status_step3";
		private static final String j_status_step4="j_status_step4";
		private static final String j_status_step5="j_status_step5";


		//journey habit columns
		private static final String j_h_id="j_h_id";
		private static final String j_h_user_id="j_h_user_id";
		private static final String j_h_user_name="j_h_user_name";
		private static final String j_h_journey_name="j_h_journey_name";
		private static final String j_h_hid="j_h_hid";
		private static final String j_h_letter_reap="j_h_letter_reap";
		private static final String j_h_challenge_acc="j_h_challenge_acc";
		private static final String j_h_goal_completed="j_h_goal_completed";
		private static final String j_h_action_done="j_h_action_done";
		private static final String j_h_motivation="j_h_motivation";
		private static final String j_h_golden_chllenge="j_h_golden_chllenge";
		private static final String j_golden_status="j_golden_status";

		//user habit columns
		private static final String table_user_habits_id="table_user_habits_id";
		private static final String table_user_habits_user_id="table_user_habits_user_id";
		private static final String table_user_habits_user_name="table_user_habits_user_name";
		private static final String table_user_habits_habit_id="table_user_habits_habit_id";
		private static final String table_user_habits_ritual_type="table_user_habits_ritual_type";
		private static final String table_user_habits_userhabit_time="table_user_habits_userhabit_time";
		private static final String table_user_habits_is_habit_completed="table_user_habits_is_habit_completed";
		private static final String table_user_habits_habit_priority="table_user_habits_habit_priority";
		private static final String table_user_habits_habit_completed_on="table_user_habits_habit_completed_on";
		private static final String table_user_habits_reminder_next_desc="table_user_habits_reminder_next_desc";
		private static final String table_user_habits_habit_added_on="table_user_habits_habit_added_on";

		//custom user habit
		private static final String custom_habit_id="custom_habit_id";
		private static final String custom_habit_user_id="custom_habit_user_id";
		private static final String custom_h_id="custom_h_id";
		private static final String custom_habit="custom_habit";
		private static final String custom_description="custom_description";
		private static final String custom_benefits="custom_benefits";
		private static final String custom_habit_time="custom_habit_time";
		private static final String custom_rem_des1="custom_rem_des1";
		private static final String custom_rem_des2="custom_rem_des2";
		private static final String custom_rem_des3="custom_rem_des3";
		private static final String custom_rem_des4="custom_rem_des4";
		private static final String custom_rem_des5="custom_rem_des5";
		private static final String custom_rem_des6="custom_rem_des6";


		private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
									+ACTIVITY_MINUTES+" VARCHAR(255), "+GLUCOSE_NUMBER+" VARCHAR(255), "+GLUCOSE_TIME
									+" VARCHAR(255), "+FOOD_NAME+" VARCHAR(255), "+FOOD_QUANTITY+" VARCHAR(255), "
									+MEDICINE_NAME+" VARCHAR(255), "+MEDICINE_QUANTITIY+" VARCHAR(255), "+MEDICINE_TYPE+" VARCHAR(255)," +
											TIME+" VARCHAR(255),"+DATE+" VARCHAR(255),"+RITUAL+" VARCHAR(255));";

		private static final String CREATE_MUSIC_TABLE="CREATE TABLE "+MUSIC_TABLE+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+MUSIC_ID+" VARCHAR(255), "
				+MUSIC_TITLE+" VARCHAR(255), "
				+MUSIC_DESCRIPTION+" VARCHAR(255), "
				+MUSIC_NAME+" VARCHAR(255), "
				+MUSIC_DATE+" VARCHAR(255), "
				+MUSIC_CATEGORY+" VARCHAR(255), "
				+MUSIC_FILE_PATH+" VARCHAR(255));";



		private static final String CREATE_REMINDER_TABLE ="CREATE TABLE "+TABLE_REMINDER+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+REM_ID+" VARCHAR(255), "
				+REMINDER_USER_ID+" VARCHAR(255), "
				+REMINDER_REM_ID+" VARCHAR(255), "
				+REMINDER_USER_NAME+" VARCHAR(255), "
				+REMINDER_REM_TIME+" VARCHAR(255), "
				+REMINDER_REM_RITUAL_RITUAL_TYPE+" VARCHAR(255), "
				+REMINDER_REM_SNOOZE_TIME+" VARCHAR(255));";


		private static final String CREATE_RITUAL_TIMELINE_TABLE="CREATE TABLE "+RITUAL_TIMELINE_TABLE +" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+TIMELINE_ID+" VARCHAR(255), "
				+TIMELINE_USER_ID+" VARCHAR(255), "
				+TIMELINE_USER_NAME+" VARCHAR(255), "
				+TIMELINE_RITUAL_TYPE+" VARCHAR(255), "
				+TIMELINE_DATE_OF_STATUS+" VARCHAR(255), "
				+TIMELINE_TOTAL_HABITS+" VARCHAR(255), "
				+TIMELINE_HABITCOMPLETED+" VARCHAR(255));";


		private static final String CREATE_REMINDER_DESC_TABLE="CREATE TABLE "+TABLE_REMINDER_DESC+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+REMINDER_DESC_ID+" VARCHAR(255), "
				+REMINDER_DESC_USER_ID+" VARCHAR(255), "
				+REMINDER_DESC_USER_NAME+" VARCHAR(255), "
				+REMINDER_DESC_TIME+" VARCHAR(255), "
				+REMINDER_DESC_DAY+" VARCHAR(255), "
				+REMINDER_DESC_ON_OFF+" VARCHAR(255), "
				+REMINDER_DESC_STAMP+" VARCHAR(255), "
				+REMINDER_DESC_REMINDER_ID+" VARCHAR(255));";


		private static final String CREATE_JOURNEY_TABLE="CREATE TABLE "+TABLE_JOURNEY+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+j_id+" VARCHAR(255), "
				+j_user_id+" VARCHAR(255), "
				+j_user_name+" VARCHAR(255), "
				+j_journey_name+" VARCHAR(255), "
				+j_total_events+" VARCHAR(255), "
				+j_total_events_achived+" VARCHAR(255), "
				+j_status_step1+" VARCHAR(255), "
				+j_status_step2+" VARCHAR(255), "
				+j_status_step3+" VARCHAR(255), "
				+j_status_step4+" VARCHAR(255), "
				+j_status_step5+" VARCHAR(255));";



		private static final String CREATE_JOURNEY_HABIT_TABLE="CREATE TABLE "+TABLE_JOURNEY_HABIT+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+j_h_id+" VARCHAR(255), "
				+j_h_user_id+" VARCHAR(255), "
				+j_h_user_name+" VARCHAR(255), "
				+j_h_journey_name+" VARCHAR(255), "
				+j_h_hid+" VARCHAR(255), "
				+j_h_letter_reap+" VARCHAR(255), "
				+j_h_challenge_acc+" VARCHAR(255), "
				+j_h_goal_completed+" VARCHAR(255), "
				+j_h_action_done+" VARCHAR(255), "
				+j_h_motivation+" VARCHAR(255), "
				+j_h_golden_chllenge+" VARCHAR(255), "
				+j_golden_status+" VARCHAR(255));";



		private static final String CREATE_USE_HABIT_TABLE="CREATE TABLE "+TABLE_USER_HABIT+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+table_user_habits_id+" VARCHAR(255), "
				+table_user_habits_user_id+" VARCHAR(255), "
				+table_user_habits_user_name+" VARCHAR(255), "
				+table_user_habits_habit_id+" VARCHAR(255), "
				+table_user_habits_ritual_type+" VARCHAR(255), "
				+table_user_habits_userhabit_time+" VARCHAR(255), "
				+table_user_habits_is_habit_completed+" VARCHAR(255), "
				+table_user_habits_habit_priority+" VARCHAR(255), "
				+table_user_habits_habit_completed_on+" VARCHAR(255), "
				+table_user_habits_reminder_next_desc+" VARCHAR(255), "
				+table_user_habits_habit_added_on+" VARCHAR(255));";


		private static final String CREATE_CUSTOM_HABIT_TABLE="CREATE TABLE "+TABLE_CUSTOM_HABIT+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+custom_habit_id+" VARCHAR(255), "
				+custom_habit_user_id+" VARCHAR(255), "
				+custom_h_id+" VARCHAR(255), "
				+custom_habit+" VARCHAR(255), "
				+custom_description+" VARCHAR(255), "
				+custom_benefits+" VARCHAR(255), "
				+custom_habit_time+" VARCHAR(255), "
				+custom_rem_des1+" VARCHAR(255), "
				+custom_rem_des2+" VARCHAR(255), "
				+custom_rem_des3+" VARCHAR(255), "
				+custom_rem_des4+" VARCHAR(255), "
				+custom_rem_des5+" VARCHAR(255), "
				+custom_rem_des6+" VARCHAR(255));";


		private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
		private static final String DROP_MUSIC_TABLE="DROP TABLE IF EXISTS "+MUSIC_TABLE;
		private static final String DROP_RITUAL_TIMELINE_TABLE="DROP TABLE IF EXISTS "+RITUAL_TIMELINE_TABLE;
		private static final String DROP_REMINDER_TABLE="DROP TABLE IF EXISTS "+TABLE_REMINDER;
		private static final String DROP_REMINDER_DESC_TABLE="DROP TABLE IF EXISTS "+TABLE_REMINDER_DESC;
		private static final String DROP_JOURNEY_TABLE="DROP TABLE IF EXISTS "+TABLE_JOURNEY;
		private static final String DROP_JOURNEY_HABIT_TABLE="DROP TABLE IF EXISTS "+TABLE_JOURNEY_HABIT;
		private static final String DROP_USER_HABIT_TABLE="DROP TABLE IF EXISTS "+TABLE_USER_HABIT;
		private static final String DROP_CUSTOM_HABIT_TABLE="DROP TABLE IF EXISTS "+TABLE_CUSTOM_HABIT;
		private Context context;
		
		

		public DataBaseHelper1(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
			this.context=context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try{
				db.execSQL(CREATE_TABLE);
				db.execSQL(CREATE_MUSIC_TABLE);
				db.execSQL(CREATE_RITUAL_TIMELINE_TABLE);
				db.execSQL(CREATE_REMINDER_TABLE);
				db.execSQL(CREATE_REMINDER_DESC_TABLE);
				db.execSQL(CREATE_JOURNEY_TABLE);
				db.execSQL(CREATE_JOURNEY_HABIT_TABLE);
				db.execSQL(CREATE_USE_HABIT_TABLE);
				db.execSQL(CREATE_CUSTOM_HABIT_TABLE);
//				Toast.makeText(context, "database called", Toast.LENGTH_SHORT).show();
			}
			catch(Exception e){
				Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
				Log.d("sunil",e.toString());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			try{
				db.execSQL(DROP_TABLE);
				db.execSQL(DROP_MUSIC_TABLE);
				db.execSQL(DROP_RITUAL_TIMELINE_TABLE);
				db.execSQL(DROP_REMINDER_TABLE);
				db.execSQL(DROP_REMINDER_DESC_TABLE);
				db.execSQL(DROP_JOURNEY_TABLE);
				db.execSQL(DROP_JOURNEY_HABIT_TABLE);
				db.execSQL(DROP_USER_HABIT_TABLE);
				db.execSQL(DROP_CUSTOM_HABIT_TABLE);
				Toast.makeText(context, "database droped", Toast.LENGTH_SHORT).show();
				onCreate(db);
			}
			catch(Exception e){
				Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
				Log.d("sunil",e.toString());
			}
		}

	}
		
}
