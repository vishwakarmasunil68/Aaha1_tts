package com.motivator.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.motivator.model.UserModel;

public class Database extends SQLiteOpenHelper {

	private static final int VERSION = 1;
	private static final String DATABASE = "USER_DATABASE";
	private static final String TABLE_USER = "USER";
	private static final String TABLE_USER_INFO = "USER_INFO";

	private static String USER_ID="USER_ID";
	private static String USER_NAME="USER_NAME";
	private static String USER_EMAIL="USER_EMAIL";
	private static String WAKE_UP_TIME="WAKE_UP_TIME";
	private static String LUNCH_TIME="LUNCH_TIME";
	private static String SLEEP_TIME="SLEEP_TIME";

	
	public Database(Context context) {
		super(context, DATABASE, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		String CREATE_USER = "create table " + TABLE_USER + " (" + USER_ID + " text, " +USER_NAME
				+ " text, " +USER_EMAIL +" text " +")";
		String CREATE_USER_INFO = "create table " + TABLE_USER_INFO + " (" + USER_ID + " text, " +USER_NAME
				+ " text, " +WAKE_UP_TIME+ " text, " +LUNCH_TIME+ " text, " +SLEEP_TIME +" text " +")";
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_USER_INFO);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		onCreate(db);
	}


	public void showChatData() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.query(TABLE_USER, null, null,null, null, null, null);
		
		if (c.moveToFirst()) {
			do {
				Log.e("show", "Line Separated");
				Log.d("show", (c.getString(0)));
				Log.d("show", (c.getString(1)));
				Log.d("show", (c.getString(2)));
				Log.d("show", (c.getString(3)));
				Log.d("show", (c.getString(4)));
				Log.d("show", (c.getString(5)));
				Log.d("show", (c.getString(6)));
				Log.d("show", (c.getString(7)));
				Log.d("show", (c.getString(8)));
				Log.d("show", (c.getString(9)));
				Log.d("show", (c.getString(10)));
				Log.d("show", (c.getString(11)));
				Log.d("show", (c.getString(12)));
				Log.d("show", (c.getString(13)));
				Log.d("show", (c.getString(14)));
				Log.d("show", (c.getString(15)));
				Log.d("show", (c.getString(16)));
				Log.d("show", (c.getString(17)));
				Log.d("show", (c.getString(18)));
			} while (c.moveToNext());
		}
		c.close();
		db.close();
	}

	public ArrayList<UserModel> getUsertList(String user_Id)
	{
		ArrayList<UserModel> userList = new ArrayList<UserModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String s = "Select * from "+ TABLE_USER + " where ( "+ USER_ID+ " = '"+user_Id+"' ) " ;
		Cursor c = db.rawQuery(s, null);
		
		if (c.moveToFirst()) {
			do {
				UserModel user = new UserModel();
				user.setUserId(c.getString(0));
				user.setUserName(c.getString(1));
				user.setUserEmailId(c.getString(2));
				
				userList.add(user);
			}
			while (c.moveToNext());
		}
		c.close();
		db.close();

		return userList;
	}
	
	public UserModel getUserInfo(String userName)
	{
		UserModel user = new UserModel();
		SQLiteDatabase db = this.getReadableDatabase();
		
		String s = "Select * from "+ TABLE_USER_INFO + " where ( "+ USER_NAME+ " = '"+userName+"' ) " ;
		Cursor c = db.rawQuery(s, null);
		
		if (c.moveToFirst()) {
			do {
				user.setUserId(c.getString(0));
				user.setUserName(c.getString(1));
				//user.setUserEmailId(c.getString(2));
				user.setWakeUpTime(c.getString(2));
				user.setLunchTime(c.getString(3));
				user.setSleepTime(c.getString(4));
			}
			while (c.moveToNext());
		}
		c.close();
		db.close();

		return user;
	}
	
	public boolean insertUserData(String userid, String username, String userEmail) 
	{
		SQLiteDatabase db = this.getReadableDatabase();
		String s = "Select * from "+ TABLE_USER + " where ( "+ USER_NAME+ " = '"+username+"' ) " ;
		Cursor c = db.rawQuery(s, null);
		if (c.moveToFirst()) {
			c.close();
			db.close(); // Closing database connection
			return false;
		} 
		else 
		{
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(USER_ID, userid); 
			values.put(USER_NAME, username); 
			values.put(USER_EMAIL, userEmail);
			
			// Inserting Row
			try{
			db.insert(TABLE_USER, null, values);
			}catch(Exception e){
				e.printStackTrace();
			}
			db.close(); // Closing database connection
			return true;
		}
		
	}
	
	public boolean insertUserInfo(String userId, String userName, String wakeup, String lunch, String sleep) 
	{
		SQLiteDatabase db = this.getReadableDatabase();
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(USER_ID, userId); 
		values.put(USER_NAME, userName); 
		values.put(WAKE_UP_TIME, wakeup); 
		values.put(LUNCH_TIME, lunch); 
		values.put(SLEEP_TIME, sleep);
		
		// Inserting Row
		try{
		db.insert(TABLE_USER_INFO, null, values);
		}catch(Exception e){
			e.printStackTrace();
		}
		db.close(); // Closing database connection
		return true;
		
		
	}
	
	public void deleteUserData(String userId) {

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USER, USER_ID+" = ? " ,
				new String[] { String.valueOf(userId)});
		db.close();
	}
	
	//
	public void updateCount(String userid, String userName, String userEmail) 
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues values = new ContentValues();
	      values.put(USER_ID, userid); 
			values.put(USER_NAME, userName); 
			values.put(USER_EMAIL, userEmail);
	      db.update(TABLE_USER, values,  USER_ID + " = ? AND "+USER_NAME+" = ? ", new String[] { userid , userName} );
	   }

	
	public void deleteUser(String userId){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_USER+" where ( "+USER_ID+" = "+userId+")");
		}
	
	public void deleteUserTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_USER);
		}

	
	
}
