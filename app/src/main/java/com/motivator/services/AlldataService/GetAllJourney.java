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

public class GetAllJourney {
    private final static String TAG="getallrituals";
    private final static String JOURNEY_DETAILS = "http://www.funhabits.co/aaha/get_journey_detail.php";
    Activity activity;
    String user_id;
    NewDataBaseHelper helper;
    PutData putData;

    public GetAllJourney(Activity activity, String user_id) {
        this.user_id = user_id;
        this.activity = activity;
        helper = new NewDataBaseHelper(activity);
        putData=new PutData(activity);
    }


    public class GettingUserJourney extends AsyncTask<String, Void, String> {
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
                                long row=putData.addJourneyFromGet(j_user_name,
                                        j_journey_name,
                                        j_total_events,
                                        j_total_events_achived,
                                        j_status_step1,
                                        j_status_step2,
                                        j_status_step3,
                                        j_status_step4,
                                        j_status_step5);
                            }

                    new GetAllJourneyHabit(activity,user_id).new GettingUserJourneyHABITS().execute();

                    }else {
                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }
}
