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

public class GetAllTimeLine {
    private final static String TAG="getalltimeline";
    private final static String USER_TIMELINE_DETAILS = "http://www.funhabits.co/aaha/get_timline_detail.php";
    Activity activity;
    String user_id;
    NewDataBaseHelper helper;
    PutData putData;

    public GetAllTimeLine(Activity activity, String user_id) {
        this.user_id = user_id;
        this.activity = activity;
        helper = new NewDataBaseHelper(activity);
        putData=new PutData(activity);
    }


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
                jResult = WebServices.httpCall(USER_TIMELINE_DETAILS, nameValuePairs);
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

                        long val=putData.addTimeLineFromGet(timeline_user_name,timeline_ritual_type,
                                timeline_dateof_status,timeline_total_habits,timeline_habitcompleted);

                    }

                    new GetAllJourney(activity,user_id).new GettingUserJourney().execute();
                } else {
                    new GetAllJourney(activity,user_id).new GettingUserJourney().execute();

//                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }

}
