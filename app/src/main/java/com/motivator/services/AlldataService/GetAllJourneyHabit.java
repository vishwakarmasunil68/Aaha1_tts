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
 * Created by sunil on 26-12-2016.
 */

public class GetAllJourneyHabit {
    private final static String TAG="getallrituals";
    private final static String JOURNEY_HABIT_DETAILS = "http://www.funhabits.co/aaha/getjourney_habits_detail.php";
    Activity activity;
    String user_id;
    NewDataBaseHelper helper;
    PutData putData;

    public GetAllJourneyHabit(Activity activity, String user_id) {
        this.user_id = user_id;
        this.activity = activity;
        helper = new NewDataBaseHelper(activity);
        putData=new PutData(activity);
    }


    public class GettingUserJourneyHABITS extends AsyncTask<String, Void, String> {
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
                jResult = WebServices.httpCall(JOURNEY_HABIT_DETAILS, nameValuePairs);
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
                    JSONArray array = jsonObject.optJSONArray("journey_habits");
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.optJSONObject(i);
                        String j_h_id = object.optString("j_h_id");
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


                        long row=putData.addJourneyHabitFromGet(j_h_user_name,
                                j_h_journey_name,
                                j_h_hid,
                                j_h_letter_reap,
                                j_h_challenge_acc,
                                j_h_goal_completed,
                                j_h_action_done,
                                j_h_motivation,
                                j_h_golden_chllenge,
                                j_golden_status
                                );
                    }

                    new GetAllReminder(activity,user_id).new GettingReminderDetails().execute();

                }else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }
}
