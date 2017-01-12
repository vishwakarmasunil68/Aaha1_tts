package com.motivator.services.AlldataService;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by sunil on 26-12-2016.
 */

public class GetAllCustomHabit {
    private final static String TAG="getallcustomhabit";
    private final static String CUSTOM_HABIT_DETAILS = "http://www.funhabits.co/aaha/getcustom_habit_detail.php";
    Activity activity;
    String user_id;
    NewDataBaseHelper helper;
    PutData putData;

    public GetAllCustomHabit(Activity activity, String user_id) {
        this.user_id = user_id;
        this.activity = activity;
        helper = new NewDataBaseHelper(activity);
        putData=new PutData(activity);
    }


    public class GettingCustomHABITS extends AsyncTask<String, Void, String> {
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

            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {
                    JSONArray array = jsonObject.optJSONArray("custom_habit");
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.optJSONObject(i);
                        String custom_habit_id = object.optString("custom_habit_id");
                        String custom_habit_user_id = object.optString("custom_habit_user_id");
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


                        long row=putData.addCustomHabitFromGet(custom_h_id,
                                custom_habit,
                                custom_description,
                                custom_benefits,
                                custom_habit_time,
                                custom_rem_des1,
                                custom_rem_des2,
                                custom_rem_des3,
                                custom_rem_des4,
                                custom_rem_des5,
                                custom_rem_des6
                        );
                    }

                    new GetAllHabits(activity,user_id).new GettingUserHabitDetails().execute();

                }else {
                    new GetAllHabits(activity,user_id).new GettingUserHabitDetails().execute();

//                    Toast.makeText(mContext, "Failed to get the custom habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }
}
