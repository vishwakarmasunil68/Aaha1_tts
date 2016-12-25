package com.motivator.services.AlldataService;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.motivator.common.WebServices;
import com.motivator.database.NewDataBaseHelper;
import com.motivator.database.PrefData;
import com.motivator.database.PutData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.motivator.common.GeneralUtility.mContext;

/**
 * Created by sunil on 25-12-2016.
 */

public class GetAllHabits {
    private final static String TAG="getallrituals";
    private final static String USER_HABIT_DETAILS = "http://www.funhabits.co/aaha/gettable_user_habit_detail.php";
    Activity activity;
    String user_id;
    NewDataBaseHelper helper;
    PutData putData;

    public GetAllHabits(Activity activity, String user_id) {
        this.user_id = user_id;
        this.activity = activity;
        helper = new NewDataBaseHelper(activity);
        putData=new PutData(activity);
    }


    public class GettingUserHabitDetails extends AsyncTask<String, Void, String> {
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
//
                        try {
                            long row=putData.addHabitFromGet(table_user_habits_user_name,
                                    table_user_habits_habit_id,table_user_habits_ritual_type,
                                    table_user_habits_userhabit_time,table_user_habits_is_habit_completed,
                                    table_user_habits_habit_priority,table_user_habits_habit_completed_on,
                                    table_user_habits_reminder_next_desc,table_user_habits_habit_added_on);

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
}
