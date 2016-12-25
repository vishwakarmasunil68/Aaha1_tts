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

public class GetAllReminder {
    private final static String TAG="getallreminder";
    private final static String REMINDER_DETAILS = "http://www.funhabits.co/aaha/get_reminder_detail.php";
    Activity activity;
    String user_id;
    NewDataBaseHelper helper;
    PutData putData;

    public GetAllReminder(Activity activity, String user_id) {
        this.user_id = user_id;
        this.activity = activity;
        helper = new NewDataBaseHelper(activity);
        putData=new PutData(activity);
    }


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


                        long val=putData.addReminderFromGet(rem_rem_id,rem_user_name,rem_time,
                                rem_snooze_time,rem_snooze_time);
                        }


                    }
                 else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }
}
