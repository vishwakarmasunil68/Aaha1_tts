package com.motivator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.motivator.common.DateUtility;
import com.motivator.common.WebServices;
import com.motivator.model.HabitTimelinePOJO;
import com.motivator.model.UserHabitTable;

import java.util.Date;

public class PutData {
    SQLiteDatabase _database;
    DatabaseHelper databaseHelper;
    Context mContext;

    private static PutData instance;

    public static PutData getInstance(Context context) {
        if (instance == null)
            instance = new PutData(context);
        return instance;
    }

    public PutData(Context context) {
        mContext = context;
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    /**
     * Check if user added already, if not then add the new user
     *
     * @param username
     * @return
     */
    public long insertUserData(String username) {
        long res = -1;
        String s = "Select * from " + TableAttributes.TABLE_USER + " where " + TableAttributes.USER_NAME + " = '" + username + "'";
        _database = databaseHelper.openDataBase();
        Cursor c = _database.rawQuery(s, null);
        if (c.moveToFirst()) {
            c.close();
            _database.close(); // Closing database connection
            return res;
        } else {
            ContentValues values = new ContentValues();
            values.put(TableAttributes.USER_NAME, username);

            // Inserting Row
            try {
                res = _database.insert(TableAttributes.TABLE_USER, null, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
            _database.close(); // Closing database connection
            return res;
        }

    }

    /**
     * @param userName
     * @param r_img
     * @param r_name
     * @param r_displayName
     * @param time
     * @param fullScrren
     * @param announce
     * @param ring
     * @param ritual_rID
     * @return
     */
    public long addUserRitual(String userName, int r_img, String r_name, String r_displayName, String time, int fullScrren, int announce, int ring, int ritual_rID) {
        long res = -1;
        String s = "Select * from " + TableAttributes.TABLE_USER_RITUALS + " where "
                + TableAttributes.USER_NAME + " = '" + userName + "' and " + TableAttributes.RITUAL_NAME + " = '" + r_name + "'";

        _database = databaseHelper.openDataBase();
        Cursor c = _database.rawQuery(s, null);
        if (c.moveToFirst()) {
            c.close();
//			UpdateData up = new UpdateData();
//			int row = up.updateUserRitualTime(userName,r_name, time);
            _database.close(); // Closing database connection
            return res;
        }

        ContentValues values = new ContentValues();
        values.put(TableAttributes.USER_NAME, userName);
        values.put(TableAttributes.RITUAL_IMG, r_img);
        values.put(TableAttributes.RITUAL_NAME, r_name);
        values.put(TableAttributes.RITUAL_DISPLAY_NAME, r_displayName);
        values.put(TableAttributes.RITUAL_TIME, time);
        values.put(TableAttributes.NOTIFICATION_STYLE, fullScrren);
        values.put(TableAttributes.ANNOUNCE_FIRST, announce);
        values.put(TableAttributes.RING_IN_SILENT, ring);
        values.put(TableAttributes.RITUAL_REMINDER_ID, ritual_rID);

        // Inserting Row
        try {
            res = _database.insert(TableAttributes.TABLE_USER_RITUALS, null, values);
            if (res > 0) {
                new WebServices().new AddUserRitual(String.valueOf(userName), String.valueOf(r_name), String.valueOf(r_img), String.valueOf(time), String.valueOf(fullScrren),
                        "", String.valueOf(announce), String.valueOf(ring), String.valueOf(r_displayName), String.valueOf(ritual_rID)).execute();
            }

            //call here for the ritual add api.
        } catch (Exception e) {
            e.printStackTrace();
        }
        _database.close(); // Closing database connection
        return res;
    }

    public long addUserRitualfromGet(String userName, int r_img, String r_name, String r_displayName, String time, int fullScrren, int announce, int ring, int ritual_rID) {
        long res = -1;
        String s = "Select * from " + TableAttributes.TABLE_USER_RITUALS + " where "
                + TableAttributes.USER_NAME + " = '" + userName + "' and " + TableAttributes.RITUAL_NAME + " = '" + r_name + "'";

        _database = databaseHelper.openDataBase();
        Cursor c = _database.rawQuery(s, null);
        if (c.moveToFirst()) {
            c.close();
//			UpdateData up = new UpdateData();
//			int row = up.updateUserRitualTime(userName,r_name, time);
            _database.close(); // Closing database connection
            return res;
        }

        ContentValues values = new ContentValues();
        values.put(TableAttributes.USER_NAME, userName);
        values.put(TableAttributes.RITUAL_IMG, r_img);
        values.put(TableAttributes.RITUAL_NAME, r_name);
        values.put(TableAttributes.RITUAL_DISPLAY_NAME, r_displayName);
        values.put(TableAttributes.RITUAL_TIME, time);
        values.put(TableAttributes.NOTIFICATION_STYLE, fullScrren);
        values.put(TableAttributes.ANNOUNCE_FIRST, announce);
        values.put(TableAttributes.RING_IN_SILENT, ring);
        values.put(TableAttributes.RITUAL_REMINDER_ID, ritual_rID);

        // Inserting Row
        try {
            _database = databaseHelper.openDataBase();
            res = _database.insert(TableAttributes.TABLE_USER_RITUALS, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        _database.close(); // Closing database connection
        return res;
    }
    public long addCustomHabitToHabitTable(String h_id,String habit,String benefits,String habit_time,String rem_desc1
    ,String rem_desc2,String rem_desc3,String rem_desc4,String rem_desc5,String rem_desc6){
        long res = -1;
        _database = databaseHelper.openDataBase();
        ContentValues values = new ContentValues();
        values.put(TableAttributes.H_ID, h_id);
        values.put(TableAttributes.HABIT, habit);
        values.put(TableAttributes.DESCRIPTION, TableAttributes.custom_habit);
        values.put(TableAttributes.BENEFITS, benefits);
        values.put(TableAttributes.HABIT_TIME, habit_time);
        values.put(TableAttributes.REMINDER_DESC1, rem_desc1);
        values.put(TableAttributes.REMINDER_DESC2, rem_desc2);
        values.put(TableAttributes.REMINDER_DESC3, rem_desc3);
        values.put(TableAttributes.REMINDER_DESC4, rem_desc4);
        values.put(TableAttributes.REMINDER_DESC5, rem_desc5);
        values.put(TableAttributes.REMINDER_DESC6, rem_desc6);
        try {
            _database = databaseHelper.openDataBase();
            res = _database.insert(TableAttributes.TABLE_HABIT, null, values);
        }
        catch (Exception e){

        }
        return res;
    }


    public long addCustomHabit(String habitName, String hTime, String filePath, String iconColor) {
        long res = -1;
        _database = databaseHelper.openDataBase();
        ContentValues values = new ContentValues();
        values.put(TableAttributes.HABIT, habitName);
        values.put(TableAttributes.DESCRIPTION, TableAttributes.custom_habit);
        values.put(TableAttributes.REMINDER_DESC1, filePath);
        values.put(TableAttributes.REMINDER_DESC2, iconColor);
        values.put(TableAttributes.HABIT_TIME, hTime);

        // Inserting Row
        try {
            res = _database.insert(TableAttributes.TABLE_HABIT, null, values);
            if (res > 0) {
                String s = "Select * from " + TableAttributes.TABLE_HABIT + " where " +
                        TableAttributes.HABIT + " = '" + habitName + "'";
                try {
                    Cursor c = _database.rawQuery(s, null);
                    if (c.moveToFirst()) {
                        String h_id = c.getString(0);
                        String habit = c.getString(1);
                        String description = c.getString(2);
                        String benefits = c.getString(3);
                        String habit_time = c.getString(4);
                        String reminder_desc1 = c.getString(5);
                        String reminder_desc2 = c.getString(6);
                        String reminder_desc3 = c.getString(7);
                        String reminder_desc4 = c.getString(8);
                        String reminder_desc5 = c.getString(9);
                        String reminder_desc6 = c.getString(10);

//                        Log.d(TAG, "h_id:-" + h_id);
//                        Log.d(TAG, "habit:-" + habit);
//                        Log.d(TAG, "description:-" + description);
//                        Log.d(TAG, "benefits:-" + benefits);
//                        Log.d(TAG, "habit_time:-" + habit_time);
//                        Log.d(TAG, "reminder_desc1:-" + reminder_desc1);
//                        Log.d(TAG, "reminder_desc2:-" + reminder_desc2);
//                        Log.d(TAG, "reminder_desc3:-" + reminder_desc3);
//                        Log.d(TAG, "reminder_desc4:-" + reminder_desc4);
//                        Log.d(TAG, "reminder_desc5:-" + reminder_desc5);
//                        Log.d(TAG, "reminder_desc6:-" + reminder_desc6);

                        new WebServices().new AddCustomHabitService(h_id, habitName, description, benefits, habit_time,
                                reminder_desc1, reminder_desc2, reminder_desc3, reminder_desc4, reminder_desc5, reminder_desc6).execute();
                    }
                } catch (Exception e) {
                    Log.d("webservice", e.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        _database.close();
        return res;
    }


    /**
     * Add Habits of user for particular ritual type
     *
     * @param username
     * @param h_id
     * @param type
     * @param habit_time
     * @return
     */
    public long addUserHabit(String username, int h_id, String type, String habit_time) {
        long res = -1;
        int total_habit_count = 0;
        String s = "Select count(*) from " + TableAttributes.TABLE_USER_HABIT + " where " +
                TableAttributes.USER_NAME + " = '" + username + "' and " + TableAttributes.RITUAL_TYPE + " = '" + type + "'";

        _database = databaseHelper.openDataBase();
        Cursor c = _database.rawQuery(s, null);
        if (c.moveToFirst()) {
            total_habit_count = c.getInt(0);
            if (total_habit_count >= 10) {
                c.close();
                _database.close(); // Closing database connection
                res = -10;
                return res;
            } else {
                Date today = new Date();
                String addedOn = DateUtility.formateDate(today, "E MMM dd yyyy");
                res = addHabitinData(username, h_id, type, habit_time, total_habit_count);
                if (res > 0) {
                    new WebServices().new SaveHabittoServer(username, String.valueOf(h_id), String.valueOf(type), String.valueOf(habit_time), String.valueOf(TableAttributes.NOT_COMPLETED),
                            String.valueOf(total_habit_count), "null", String.valueOf(0), addedOn).execute();
                }
                return res;
            }
        } else {
            Date today = new Date();
            String addedOn = DateUtility.formateDate(today, "E MMM dd yyyy");
            res = addHabitinData(username, h_id, type, habit_time, total_habit_count);
            if (res > 0) {

                new WebServices().new SaveHabittoServer(username, String.valueOf(h_id), String.valueOf(type), String.valueOf(habit_time), String.valueOf(TableAttributes.NOT_COMPLETED),
                        String.valueOf(total_habit_count), "null", String.valueOf(0), addedOn).execute();
            }
            return res;
        }
    }

    public long addHabitFromGet(UserHabitTable habitTable){

        long res = -1;

        ContentValues values = new ContentValues();
        values.put(TableAttributes.USER_NAME, habitTable.getTable_user_habits_user_name());
        values.put(TableAttributes.H_ID, habitTable.getTable_user_habits_habit_id());
        values.put(TableAttributes.RITUAL_TYPE, habitTable.getTable_user_habits_ritual_type());
        values.put(TableAttributes.USER_HABIT_TIME, habitTable.getTable_user_habits_userhabit_time());
        values.put(TableAttributes.IS_HABIT_COMPLETED, habitTable.getTable_user_habits_is_habit_completed());
        values.put(TableAttributes.HABIT_PRORIOTY, habitTable.getTable_user_habits_habit_priority());
        values.put(TableAttributes.HABIT_COMPLETED_ON, habitTable.getTable_user_habits_habit_completed_on());
        values.put(TableAttributes.REMINDER_NEXT_DESC, habitTable.getTable_user_habits_reminder_next_desc());
        values.put(TableAttributes.HABIT_ADDED_ON, habitTable.getTable_user_habits_habit_added_on());

        try {
            _database = databaseHelper.openDataBase();

            res = _database.insert(TableAttributes.TABLE_USER_HABIT, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public long addHabitFromGet(String user_name, String h_id, String ritual_type, String user_habit_time,
                                String is_habit_completed, String habit_priority,
                                String habit_completed_on,
                                String reminder_next_desc, String habit_added_on){
        long res=-1;
        ContentValues values = new ContentValues();
        values.put(TableAttributes.USER_NAME, user_name);
        values.put(TableAttributes.H_ID, h_id);
        values.put(TableAttributes.RITUAL_TYPE, ritual_type);
        values.put(TableAttributes.USER_HABIT_TIME, user_habit_time);
        values.put(TableAttributes.IS_HABIT_COMPLETED, is_habit_completed);
        values.put(TableAttributes.HABIT_PRORIOTY, habit_priority);
        values.put(TableAttributes.HABIT_COMPLETED_ON, habit_completed_on);
        values.put(TableAttributes.REMINDER_NEXT_DESC, reminder_next_desc);
        values.put(TableAttributes.HABIT_ADDED_ON, habit_added_on);

        try {
            _database = databaseHelper.openDataBase();

            res = _database.insert(TableAttributes.TABLE_USER_HABIT, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private long addHabitinData(String username, int h_id, String type, String time, int habit_total) {
        Date today = new Date();
        String addedOn = DateUtility.formateDate(today, "E MMM dd yyyy");
        long res = -1;
        ContentValues values = new ContentValues();
        values.put(TableAttributes.USER_NAME, username);
        values.put(TableAttributes.H_ID, h_id);
        values.put(TableAttributes.RITUAL_TYPE, type);
        values.put(TableAttributes.USER_HABIT_TIME, time);
        values.put(TableAttributes.IS_HABIT_COMPLETED, TableAttributes.NOT_COMPLETED);
        values.put(TableAttributes.HABIT_PRORIOTY, habit_total);
        values.put(TableAttributes.HABIT_ADDED_ON, addedOn);
        // Inserting Row
        try {

            res = _database.insert(TableAttributes.TABLE_USER_HABIT, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // check status in TimeLine Table
        String todaydateString = DateUtility.formateDate(new Date(), "E MMM dd yyyy");
        values.clear();
        values = new ContentValues();
        values.put(TableAttributes.USER_NAME, username);
        values.put(TableAttributes.RITUAL_TYPE, type);
        values.put(TableAttributes.DATE_OF_STATUS, todaydateString);
        int count = getTotalHabitCount(username, type, todaydateString);
        if (count != -1) {
            values.put(TableAttributes.TOTAL_HABIT, count + 1);
            try {
                long row = _database.update(TableAttributes.TABLE_TIMELINE, values,
                        TableAttributes.USER_NAME + "=? AND " + TableAttributes.RITUAL_TYPE + " = ? AND " + TableAttributes.DATE_OF_STATUS + " = ?",
                        new String[]{username, type, todaydateString});
                Log.i("Row inserted no :", row + "");
                if (row > 0) {
//					Log.d(TAG,)\
                    String s = "Select * from " + TableAttributes.TABLE_TIMELINE + " where "
                            + TableAttributes.USER_NAME + " = '" + username + "' and " + TableAttributes.RITUAL_TYPE + " = '" + type + "' and "+TableAttributes.DATE_OF_STATUS+ " ='"+todaydateString+"'";
                    try {
                        Cursor c = _database.rawQuery(s, null);
                        if (c.moveToFirst()) {
                            String user_name = c.getString(0);
                            String ritual_type = c.getString(1);
                            String date_of_status= c.getString(2);
                            String total_habit= c.getString(3);
                            String habit_completed= c.getString(4);


                            Log.d(TAG, "user_name:-" + user_name);
                            Log.d(TAG, "ritual_type:-" + ritual_type);
                            Log.d(TAG, "date_of_status:-" + date_of_status);
                            Log.d(TAG, "total_habit:-" + total_habit);
                            Log.d(TAG, "habit_completed:-" + habit_completed);

                            NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
                            HabitTimelinePOJO pojo = helper.getHabitTimeLineData(type);
                            if (pojo != null) {
                                Log.d(TAG, pojo.toString());
                                new WebServices().new UpdateHabitToTimeLine(pojo.getTimeline_id(), user_name, ritual_type, date_of_status,
                                        total_habit, habit_completed).execute();
                            } else {
                                Log.d(TAG, "no record found");
                            }
                        }
                    } catch (Exception e) {
                        Log.d("webservice", e.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Add Row in TimeLine Table
            values.put(TableAttributes.TOTAL_HABIT, 1);
            // Inserting Row
            try {
                long row = _database.insert(TableAttributes.TABLE_TIMELINE, null, values);
                Log.i("Row inserted no :", row + "");
                if (row > 0) {
                    new WebServices().new AddHabitToTimeLine(username, type, todaydateString, String.valueOf("1"), "null").execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        _database.close(); // Closing database connection
        return res;
    }

    static final String TAG = "crud";

    private int getTotalHabitCount(String username, String type, String todaydateString) {
        int count = -1;
        String s = "Select " + TableAttributes.TOTAL_HABIT + " from " + TableAttributes.TABLE_TIMELINE + " where " +
                TableAttributes.USER_NAME + " = '" + username + "' and " + TableAttributes.RITUAL_TYPE + " = '" + type
                + "' and " + TableAttributes.DATE_OF_STATUS + " ='" + todaydateString + "'";
        try {
            Cursor c = _database.rawQuery(s, null);
            if (c.moveToFirst()) {
                // Update status in TimeLine Table
                count = c.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


    /**
     * @param r_id
     * @param username
     * @param time
     * @param type
     * @return
     */
    public long addReminder(int r_id, String username, String time, String type) {
        long res = -1;
        _database = databaseHelper.openDataBase();
        ContentValues values = new ContentValues();
        values.put(TableAttributes.REMINDER_ID, r_id);
        values.put(TableAttributes.USER_NAME, username);
        values.put(TableAttributes.REMINDER_TIME, time);
        values.put(TableAttributes.RITUAL_TYPE, type);
        values.put(TableAttributes.SNOOZE_TIME, "5");

        // Inserting Row
        try {
            res = _database.insert(TableAttributes.TABLE_REMINDER, null, values);
            if (res > 0) {
//				NewDataBaseHelper helper=new NewDataBaseHelper(mContext);
//				ReminderPOJO pojo=helper.getReminderData(type);
                new WebServices().new AddReminderService(PrefData.getStringPref(mContext, PrefData.USER_ID),
                        String.valueOf(r_id), username,
                        String.valueOf(time), type, "5").execute();

                Log.d(TAG, "reminder added");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        _database.close(); // Closing database connection
        return res;
    }

    public long addReminderDesc(int r_id, String username, String time, String day, int oN_off, int stamp) {
        long res = -1;
        _database = databaseHelper.openDataBase();
        ContentValues values = new ContentValues();
        values.put(TableAttributes.REMINDER_ID, r_id);
        values.put(TableAttributes.USER_NAME, username);
        values.put(TableAttributes.REMINDER_TIME, time);
        values.put(TableAttributes.REMINDER_DAY, day);
        values.put(TableAttributes.REMINDER_ON_OFF, oN_off);
        values.put(TableAttributes.REMINDER_STAMP, stamp);

        // Inserting Row
        try {
            res = _database.insert(TableAttributes.TABLE_REMINDER_DESC, null, values);
            if (res > 0) {
                new WebServices().new AddReminderDescService(PrefData.getStringPref(mContext, PrefData.USER_ID),
                        username, String.valueOf(time), String.valueOf(day), String.valueOf(oN_off),
                        String.valueOf(stamp), String.valueOf(r_id)).execute();
                Log.d(TAG, "reminder desc added");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        _database.close(); // Closing database connection
        return res;
    }

    /**
     * Add 3 Journeys for Each new User
     *
     * @param userName
     * @param jName
     * @param total_steps
     * @return
     */
    public long insertJourneyInfo(String userName, String jName, int total_steps) {
        long res = -1;
        String s = "Select * from " + TableAttributes.TABLE_JOURNEY + " where "
                + TableAttributes.USER_NAME + " = '" + userName + "' and " + TableAttributes.JOURNEY_NAME + " = '" + jName + "'";
        _database = databaseHelper.openDataBase();
        Cursor c = _database.rawQuery(s, null);
        if (c.moveToFirst()) {
            c.close();
            _database.close(); // Closing database connection
            res = -1;
            return res;
        } else {
            res = addJourney(userName, jName, total_steps);
            return res;
        }

    }

    private long addJourney(String userName, String jName, int totalSteps) {
        long res = -1;
        ContentValues values = new ContentValues();
        values.put(TableAttributes.USER_NAME, userName);
        values.put(TableAttributes.JOURNEY_NAME, jName);
        values.put(TableAttributes.TOTAL_EVENTS, totalSteps);

        // Inserting Row
        try {
            res = _database.insert(TableAttributes.TABLE_JOURNEY, null, values);
            if (res > 0) {

                new WebServices().new AddJourneyService(PrefData.getStringPref(mContext, PrefData.USER_ID),
                        userName, jName, String.valueOf(totalSteps), String.valueOf(0), String.valueOf(0), String.valueOf(0),
                        String.valueOf(0), String.valueOf(0), String.valueOf(0)).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        _database.close(); // Closing database connection
        return res;
    }


    /**
     * @param userName
     * @param jName
     * @param habit_id
     * @return
     */
    public long insertJourneyHabit(String userName, String jName, int habit_id) {
        long res = -1;
        _database = databaseHelper.openDataBase();
        ContentValues values = new ContentValues();
        values.put(TableAttributes.USER_NAME, userName);
        values.put(TableAttributes.JOURNEY_NAME, jName);
        values.put(TableAttributes.H_ID, habit_id);

        // Inserting Row
        try {
            res = _database.insert(TableAttributes.TABLE_JOURNEY_HABIT, null, values);
            if (res > 0) {

                new WebServices().new AddJourneyHabitService(PrefData.getStringPref(mContext, PrefData.USER_ID),
                        userName, jName, String.valueOf(habit_id), "0", "0", "0", "0", "0", "0", "0").execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        _database.close(); // Closing database connection
        return res;

    }

    public long addTimeLineFromGet(String timeline_user_name,String timeline_ritual_type,
            String timeline_dateof_status,String timeline_total_habits,String timeline_habitcompleted){

        long res = -1;

        ContentValues values = new ContentValues();
        values.put(TableAttributes.USER_NAME, timeline_user_name);
        values.put(TableAttributes.RITUAL_TYPE, timeline_ritual_type);
        values.put(TableAttributes.DATE_OF_STATUS, timeline_dateof_status);
        values.put(TableAttributes.TOTAL_HABIT, timeline_total_habits);
        values.put(TableAttributes.HABIT_COMPLETED, timeline_habitcompleted);

        try {
            _database = databaseHelper.openDataBase();
            res = _database.insert(TableAttributes.TABLE_TIMELINE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public long addReminderFromGet(String rem_rem_id,String rem_user_name,String rem_time,
                                   String rem_ritual_type,String rem_snooze_time){

        long res = -1;

        ContentValues values = new ContentValues();
        values.put(TableAttributes.REMINDER_ID, rem_rem_id);
        values.put(TableAttributes.USER_NAME, rem_user_name);
        values.put(TableAttributes.REMINDER_TIME, rem_time);
        values.put(TableAttributes.RITUAL_TYPE, rem_ritual_type);
        values.put(TableAttributes.SNOOZE_TIME, rem_snooze_time);

        try {
            _database = databaseHelper.openDataBase();

            res = _database.insert(TableAttributes.TABLE_REMINDER, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    public long addReminderDescFromGet(String rem_desc_user_name,String rem_desc_time,String rem_desc_day,
                                   String rem_desc_on_off,
                                   String rem_desc_stamp,String rem_desc_rem_id){

        long res = -1;

        ContentValues values = new ContentValues();
        values.put(TableAttributes.REMINDER_ID, rem_desc_rem_id);
        values.put(TableAttributes.USER_NAME, rem_desc_user_name);
        values.put(TableAttributes.REMINDER_TIME, rem_desc_time);
        values.put(TableAttributes.REMINDER_DAY, rem_desc_day);
        values.put(TableAttributes.REMINDER_ON_OFF, rem_desc_on_off);
        values.put(TableAttributes.REMINDER_STAMP, rem_desc_stamp);


        try {
            _database = databaseHelper.openDataBase();

            res = _database.insert(TableAttributes.TABLE_REMINDER_DESC, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public long addJourneyFromGet(String user_name,String journey_name,String total_events,
                                       String total_events_achieved,
                                       String status_step1,String status_step2
                                        ,String status_step3,String status_step4,
                                  String status_step5){

        long res = -1;

        ContentValues values = new ContentValues();
        values.put(TableAttributes.USER_NAME, user_name);
        values.put(TableAttributes.JOURNEY_NAME, journey_name);
        values.put(TableAttributes.TOTAL_EVENTS, total_events);
        values.put(TableAttributes.TOTAL_EVENTS_ACHIEVED, total_events_achieved);
        values.put(TableAttributes.STATUS_STEP1, status_step1);
        values.put(TableAttributes.STATUS_STEP2, status_step2);
        values.put(TableAttributes.STATUS_STEP3, status_step3);
        values.put(TableAttributes.STATUS_STEP4, status_step4);
        values.put(TableAttributes.STATUS_STEP5, status_step5);


        try {
            _database = databaseHelper.openDataBase();

            res = _database.insert(TableAttributes.TABLE_JOURNEY, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    public long addJourneyHabitFromGet(String user_name,String journey_name,String h_id,
                                  String letter_read,
                                  String challenge_accepted,String goal_completed
            ,String action_done,String motivation,
                                  String golden_challenge,String golden_status){

        long res = -1;

        ContentValues values = new ContentValues();
        values.put(TableAttributes.USER_NAME, user_name);
        values.put(TableAttributes.JOURNEY_NAME, journey_name);
        values.put(TableAttributes.H_ID, h_id);
        values.put(TableAttributes.LETTER_READ, letter_read);
        values.put(TableAttributes.CHALLENGE_ACCEPTED, challenge_accepted);
        values.put(TableAttributes.GOAL_COMPLETED, goal_completed);
        values.put(TableAttributes.ACTION_DONE, action_done);
        values.put(TableAttributes.MOTIVATION, motivation);
        values.put(TableAttributes.GOLDEN_CHALLENGE, golden_challenge);
        values.put(TableAttributes.GOLDEN_GOAL_STATUS, golden_status);


        try {
            _database = databaseHelper.openDataBase();

            res = _database.insert(TableAttributes.TABLE_JOURNEY_HABIT, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public long addCustomHabitFromGet(String h_id,String habit,String description,
                                       String benefits,
                                       String habit_time,
                                       String reminder_desc1
            ,String reminder_desc2,String reminder_desc3,
                                       String reminder_desc4,String reminder_desc5,
                                      String reminder_desc6){

        long res = -1;

        ContentValues values = new ContentValues();
        values.put(TableAttributes.H_ID, h_id);
        values.put(TableAttributes.HABIT, habit);
        values.put(TableAttributes.DESCRIPTION, description);
        values.put(TableAttributes.BENEFITS, benefits);
        values.put(TableAttributes.HABIT_TIME, habit_time);
        values.put(TableAttributes.REMINDER_DESC1, reminder_desc1);
        values.put(TableAttributes.REMINDER_DESC2, reminder_desc2);
        values.put(TableAttributes.REMINDER_DESC3, reminder_desc3);
        values.put(TableAttributes.REMINDER_DESC4, reminder_desc4);
        values.put(TableAttributes.REMINDER_DESC5, reminder_desc5);
        values.put(TableAttributes.REMINDER_DESC6, reminder_desc6);


        try {
            _database = databaseHelper.openDataBase();
            res = _database.insert(TableAttributes.TABLE_HABIT, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
