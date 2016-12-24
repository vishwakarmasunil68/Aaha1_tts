package com.motivator.services;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.motivator.common.WebServices;
import com.motivator.database.NewDataBaseHelper;
import com.motivator.database.PrefData;
import com.motivator.database.PutData;
import com.motivator.model.UserHabitTable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.motivator.common.GeneralUtility.mContext;

/**
 * Created by sunil on 23-12-2016.
 */

public class GettingAllDataService {
    private final static String TAG = "gettingalldata";
    private final static String JOURNEY_DETAILS = "http://www.funhabits.co/aaha/get_journey_detail.php";
    private final static String JOURNEY_HABITS_DETAILS = "http://www.funhabits.co/aaha/getjourney_habits_detail.php";
    private final static String CUSTOM_HABIT_DETAILS = "http://www.funhabits.co/aaha/getcustom_habit_detail.php";
    private final static String REMINDER_DETAILS = "http://www.funhabits.co/aaha/get_reminder_detail.php";
    private final static String REMINDER_DESC_DETAILS = "http://www.funhabits.co/aaha/getreminder_desc_detail.php";
    private final static String USER_HABIT_DETAILS = "http://www.funhabits.co/aaha/gettable_user_habit_detail.php";
    private final static String TIME_LINE_DETAILS = "http://www.funhabits.co/aaha/get_timline_detail.php";
    private final static String RITUAL_DETAILS = "http://www.funhabits.co/aaha/get_user_ritual_detail.php";
    String user_id;
    Activity activity;
    NewDataBaseHelper helper;
    PutData putData;
    public GettingAllDataService(Activity activity, String user_id) {
        this.user_id = user_id;
        this.activity = activity;
        helper = new NewDataBaseHelper(activity);
        putData=new PutData(activity);
    }


        //getting user ritual details:-

        public class GettingUserRitualDetails extends AsyncTask<String, Void, String> {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            String jResult;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                nameValuePairs.add(new BasicNameValuePair("user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
                try {
                    jResult = WebServices.httpCall(RITUAL_DETAILS, nameValuePairs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return jResult;
            }

            @Override
            protected void onPostExecute(String aVoid) {
                super.onPostExecute(aVoid);
//            if (progressDialog != null)
//                progressDialog.dismiss();
                try {
                    Log.d(TAG, aVoid);
                    JSONObject jsonObject = new JSONObject(aVoid);
                    String success = jsonObject.optString("success");
                    if (success.equals("true")) {
//                        long result = putData.addUserRitual(PrefData.getStringPref(activity,PrefData.NAME_KEY), 1, AppsConstant.MORNING_RITUAL, AppsConstant.MORNING_RITUAL,
//                                "7:30 AM", TableAttributes.OFF, TableAttributes.ON, TableAttributes.OFF, 0);
//                        result = putData.addUserRitual(PrefData.getStringPref(activity,PrefData.NAME_KEY), 2, AppsConstant.LUNCH_RITUAL, AppsConstant.LUNCH_RITUAL,
//                                "12:00 PM", TableAttributes.OFF, TableAttributes.ON, TableAttributes.OFF, 0);
//                        result = putData.addUserRitual(PrefData.getStringPref(activity,PrefData.NAME_KEY), 3, AppsConstant.EVENING_RITUAL, AppsConstant.EVENING_RITUAL,
//                                "10:00 PM", TableAttributes.OFF, TableAttributes.ON, TableAttributes.OFF, 0);

                        JSONArray array = jsonObject.optJSONArray("ritual_user");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            String ritual_user_id = object.optString("ritual_user_id");
                            String ritual_user_name = object.optString("ritual_user_name");
                            String ritual_img = object.optString("ritual_img");
                            String ritual_time = object.optString("ritual_time");
                            String ritual_notification_style = object.optString("ritual_notification_style");
                            String ritual_urgency_swipe = object.optString("ritual_urgency_swipe");
                            String ritual_announce_first = object.optString("ritual_announce_first");
                            String ritual_ringin_slient = object.optString("ritual_ringin_slient");
                            String ritual_display_name = object.optString("ritual_display_name");
                            String ritual_reminder = object.optString("ritual_reminder");

                            try {
                                long row=putData.addUserRitualfromGet(ritual_user_name, Integer.parseInt(ritual_img), ritual_display_name, ritual_display_name,
                                        ritual_time, Integer.parseInt(ritual_notification_style), Integer.parseInt(ritual_announce_first), Integer.parseInt(ritual_ringin_slient), Integer.parseInt(ritual_reminder));

                            }
                            catch (Exception e){
                                Log.d(TAG,"ritual add error:-"+e.toString());
                            }
                        }
                    } else {
                        Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }
        }


        /*=------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
         */

    //getting user ritual details:-

    public class GettingCustomHabits extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            try {
                jResult = WebServices.httpCall(CUSTOM_HABIT_DETAILS, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
//            if (progressDialog != null)
//                progressDialog.dismiss();
            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {

                    JSONArray array = jsonObject.optJSONArray("custom_habit");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        String custom_habit_id = object.optString("custom_habit_id");
                        String custom_h_id = object.optString("custom_h_id");
                        String custom_habit = object.optString("custom_habit");
                        String custom_description = object.optString("custom_description");
                        String custom_benefits = object.optString("custom_benefits");
                        String custom_habit_time = object.optString("custom_habit_time");
                        String custom_rem_des1 = object.optString("custom_rem_des1");
                        String custom_rem_des2 = object.optString("custom_rem_des2");
                        String custom_rem_des3 = object.optString("custom_rem_des3");
                        String custom_rem_des4 = object.optString("custom_rem_des4");
                        String custom_rem_des5 = object.optString("custom_rem_des5");
                        String custom_rem_des6 = object.optString("custom_rem_des6");

                        long val=putData.addCustomHabitToHabitTable(custom_h_id,custom_habit,custom_benefits,custom_habit_time,
                                custom_rem_des1,custom_rem_des2,custom_rem_des3,custom_rem_des4,custom_rem_des5,custom_rem_des6);
                    }
                } else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }


        /*=------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
         */


    //getting user habit details:-

    public class GettingUserHabits extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            try {
                jResult = WebServices.httpCall(USER_HABIT_DETAILS, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
//            if (progressDialog != null)
//                progressDialog.dismiss();
            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {

                    JSONArray array = jsonObject.optJSONArray("table_user_habits");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);


                        String table_user_habits_id = object.optString("table_user_habits_id");
                        String table_user_habits_user_id = object.optString("table_user_habits_user_id");
                        String table_user_habits_user_name = object.optString("table_user_habits_user_name");
                        String table_user_habits_habit_id = object.optString("table_user_habits_habit_id");
                        String table_user_habits_ritual_type = object.optString("table_user_habits_ritual_type");
                        String table_user_habits_userhabit_time = object.optString("table_user_habits_userhabit_time");
                        String table_user_habits_is_habit_completed = object.optString("table_user_habits_is_habit_completed");
                        String table_user_habits_habit_priority = object.optString("table_user_habits_habit_priority");
                        String table_user_habits_habit_completed_on = object.optString("table_user_habits_habit_completed_on");
                        String table_user_habits_reminder_next_desc = object.optString("table_user_habits_reminder_next_desc");
                        String table_user_habits_habit_added_on = object.optString("table_user_habits_habit_added_on");

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


                        long res=putData.addHabitFromGet(userHabitTable);
                    }
                } else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }


        /*=------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
         */

    //getting user timeline details:-

    public class GettingTimeLineDetails extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            try {
                jResult = WebServices.httpCall(TIME_LINE_DETAILS, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
//            if (progressDialog != null)
//                progressDialog.dismiss();
            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {

                    JSONArray array = jsonObject.optJSONArray("timeline");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);

                        String timeline_id = object.optString("timeline_id");
                        String timeline_user_id = object.optString("timeline_user_id");
                        String timeline_user_name = object.optString("timeline_user_name");
                        String timeline_ritual_type = object.optString("timeline_ritual_type");
                        String timeline_dateof_status = object.optString("timeline_dateof_status");
                        String timeline_total_habits = object.optString("timeline_total_habits");
                        String timeline_habitcompleted = object.optString("timeline_habitcompleted");
                    }
                } else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }


        /*=------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
         */


    //getting user Journey details:-

    public class GettingJourneyDetails extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            try {
                jResult = WebServices.httpCall(JOURNEY_DETAILS, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
//            if (progressDialog != null)
//                progressDialog.dismiss();
            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {

                    JSONArray array = jsonObject.optJSONArray("journey");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);


                        String j_id = object.optString("j_id");
                        String j_user_id = object.optString("j_user_id");
                        String j_user_name = object.optString("j_user_name");
                        String j_journey_name = object.optString("j_journey_name");
                        String j_total_events = object.optString("j_total_events");
                        String j_total_events_achived = object.optString("j_total_events_achived");
                        String j_status_step1 = object.optString("j_status_step1");
                        String j_status_step2 = object.optString("j_status_step2");
                        String j_status_step3 = object.optString("j_status_step3");
                        String j_status_step4 = object.optString("j_status_step4");
                        String j_status_step5 = object.optString("j_status_step5");
                    }
                } else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }


        /*=------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
         */


    //getting user Journey Habit details:-

    public class GettingJourneyHabitDetails extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            try {
                jResult = WebServices.httpCall(JOURNEY_HABITS_DETAILS, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
//            if (progressDialog != null)
//                progressDialog.dismiss();
            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {

                    JSONArray array = jsonObject.optJSONArray("journey_habits");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);

                        String j_h_id = object.optString("j_h_id");
                        String j_h_user_id = object.optString("j_h_user_id");
                        String j_h_user_name = object.optString("j_h_user_name");
                        String j_h_journey_name = object.optString("j_h_journey_name");
                        String j_h_hid = object.optString("j_h_hid");
                        String j_h_letter_reap = object.optString("j_h_letter_reap");
                        String j_h_challenge_acc = object.optString("j_h_challenge_acc");
                        String j_h_goal_completed = object.optString("j_h_goal_completed");
                        String j_h_action_done = object.optString("j_h_action_done");
                        String j_h_motivation = object.optString("j_h_motivation");
                        String j_h_golden_chllenge = object.optString("j_h_golden_chllenge");
                        String j_golden_status = object.optString("j_golden_status");
                    }
                } else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }


        /*=------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
         */


    //getting user Reminder details:-

    public class GettingReminderDetails extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            try {
                jResult = WebServices.httpCall(REMINDER_DETAILS, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
//            if (progressDialog != null)
//                progressDialog.dismiss();
            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {

                    JSONArray array = jsonObject.optJSONArray("reminders");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);

                        String rem_id = object.optString("rem_id");
                        String rem_user_id = object.optString("rem_user_id");
                        String rem_rem_id = object.optString("rem_rem_id");
                        String rem_user_name = object.optString("rem_user_name");
                        String rem_time = object.optString("rem_time");
                        String rem_ritual_type = object.optString("rem_ritual_type");
                        String rem_snooze_time = object.optString("rem_snooze_time");
                    }
                } else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }


        /*=------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
         */

    //getting user Reminder Description details:-

    public class GettingReminderDescriptionDetails extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            try {
                jResult = WebServices.httpCall(REMINDER_DESC_DETAILS, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
//            if (progressDialog != null)
//                progressDialog.dismiss();
            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {

                    JSONArray array = jsonObject.optJSONArray("reminder_desc");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);

                        String rem_desc_id = object.optString("rem_desc_id");
                        String rem_desc_user_id = object.optString("rem_desc_user_id");
                        String rem_desc_user_name = object.optString("rem_desc_user_name");
                        String rem_desc_day = object.optString("rem_desc_day");
                        String rem_desc_on_off = object.optString("rem_desc_on_off");
                        String rem_desc_stamp = object.optString("rem_desc_stamp");
                        String rem_desc_rem_id = object.optString("rem_desc_rem_id");
                    }
                } else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }


        /*=------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
        ----------------------------------------------------------------------------------------------------------
         */




}