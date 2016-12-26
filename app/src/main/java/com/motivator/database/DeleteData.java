package com.motivator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.motivator.common.DateUtility;
import com.motivator.common.WebServices;
import com.motivator.model.ReminderPOJO;
import com.motivator.model.UserHabitTable;

import java.util.Date;

public class DeleteData {

	SQLiteDatabase _database;
	DatabaseHelper databaseHelper;
	Context mContext;
	
	public DeleteData(Context context) 
	{
		mContext = context;
		databaseHelper = DatabaseHelper.getInstance(context);
	}

	public int removeHabit(int h_id, String uName, String type) 
	{
		int res = 0;
		_database = databaseHelper.openDataBase();
		try {
			res = _database.delete(TableAttributes.TABLE_USER_HABIT, TableAttributes.H_ID+"=? AND "+TableAttributes.USER_NAME+" =? ",
					new String[] {String.valueOf(h_id), uName});


			NewDataBaseHelper helper=new NewDataBaseHelper(mContext);
			UserHabitTable table=helper.getUserHabitData(h_id+"");
			if(table!=null){
				new WebServices().new DeleteHabitService(table.getTable_user_habits_id()).execute();
			}


			// Log.i("Number of rows deleted : ", "" + res);
			if(res>0) {
				removeHabitFromTimeLine(h_id, uName, type);

			}
		} catch (Exception e) {
			res = 0;
			Log.e("Error in while deleting", e.toString());
		}
		_database.close();
		return res;
	}
	
	private int removeHabitFromTimeLine(int h_id, String uName, String type) 
	{
		int res = 0;
		//Update in timeline
		String todaydateString = DateUtility.formateDate(new Date(), "E MMM dd yyyy");
		ContentValues values = new ContentValues();
		values.put(TableAttributes.USER_NAME, uName); 
		values.put(TableAttributes.RITUAL_TYPE, type);
		values.put(TableAttributes.DATE_OF_STATUS, todaydateString);
		int count = getTotalHabitCount(uName, type, todaydateString);
		if(count>0)
		{
			count = count-1;
			values.put(TableAttributes.TOTAL_HABIT, count);
			try{
				long row  = _database.update(TableAttributes.TABLE_TIMELINE, values,
						TableAttributes.USER_NAME+"=? AND " + TableAttributes.RITUAL_TYPE + " = ? AND "+TableAttributes.DATE_OF_STATUS + " = ?",
						new String[] {uName, type, todaydateString});
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		//Delete from habit timeline
		try {
			res = _database.delete(TableAttributes.TABLE_HBIT_TIMELINE, TableAttributes.H_ID+"=? AND "+TableAttributes.USER_NAME+" =? ",
					new String[] {String.valueOf(h_id), uName});
			// Log.i("Number of rows deleted : ", "" + res);
		} catch (Exception e) {
			res = 0;
			Log.e("Error in while deleting", e.toString());
		}
		return res;
	}
	
	
	 private int getTotalHabitCount(String username, String type, String todaydateString)
	 {
		 int count = -1;
		 String s = "Select "+TableAttributes.TOTAL_HABIT+" from "+ TableAttributes.TABLE_TIMELINE + " where "+
				 TableAttributes.USER_NAME+ " = '"+username+"' and "+TableAttributes.RITUAL_TYPE+" = '"+type
				 +"' and "+TableAttributes.DATE_OF_STATUS+" ='"+todaydateString+"'" ;
		 try{
			 Cursor c = _database.rawQuery(s, null);
			 if (c.moveToFirst()) 
			 {
				 count =  c.getInt(0);
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return count;		
	 }
	
	public int removeAlarm(int r_id) 
	{
		int res = 0;
		_database = databaseHelper.openDataBase();
		try {
			res = _database.delete(TableAttributes.TABLE_REMINDER, TableAttributes.REMINDER_ID+"=?",new String[] {String.valueOf(r_id)});
			// Log.i("Number of rows deleted : ", "" + res);
			NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
			ReminderPOJO pojo = helper.getReminderData(String.valueOf(r_id),PrefData.getStringPref(mContext, PrefData.NAME_KEY));
			if(pojo!=null) {
				new WebServices().new DeleteReminderService(pojo.getRem_id()).execute();
			}

		} catch (Exception e) {
			res = 0;
			Log.e("Error in while deleting", e.toString());
		}
		_database.close();
		return res;
	}


	public void DeleteAllDataFromDataBase(){
		SQLiteDatabase db=databaseHelper.getWritableDatabase();
		db.delete(TableAttributes.TABLE_HABIT, null, null);
//		db.delete(TableAttributes.TABLE_HBIT_TIMELINE, null, null);
		db.delete(TableAttributes.TABLE_JOURNEY, null, null);
		db.delete(TableAttributes.TABLE_JOURNEY_HABIT, null, null);
		db.delete(TableAttributes.TABLE_REMINDER, null, null);
		db.delete(TableAttributes.TABLE_REMINDER_DESC, null, null);
		db.delete(TableAttributes.TABLE_TIMELINE, null, null);
		db.delete(TableAttributes.TABLE_USER_HABIT, null, null);
		db.delete(TableAttributes.TABLE_USER_RITUALS, null, null);
	}
	
}
