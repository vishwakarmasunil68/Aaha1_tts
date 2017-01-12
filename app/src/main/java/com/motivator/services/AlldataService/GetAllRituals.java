package com.motivator.services.AlldataService;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
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

public class GetAllRituals {
    private final static String TAG="getallrituals";
    private final static String RITUAL_DETAILS = "http://www.funhabits.co/aaha/get_user_ritual_detail.php";
    Activity activity;
    String user_id;
    NewDataBaseHelper helper;
    PutData putData;

    public GetAllRituals(Activity activity, String user_id) {
        this.user_id = user_id;
        this.activity = activity;
        helper = new NewDataBaseHelper(activity);
        putData=new PutData(activity);
    }


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

            try {
                Log.d(TAG, aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {
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

                            if(row>0){
                                GeneralUtility.setPreferencesBoolean(activity, AppsConstant.IS_RITUAL_ADDED, true);
                            }
                        }
                        catch (Exception e){
                            Log.d(TAG,"ritual add error:-"+e.toString());
                        }
                    }

                    new GetAllCustomHabit(activity,user_id).new GettingCustomHABITS().execute();
                } else {
                    new GetAllCustomHabit(activity,user_id).new GettingCustomHABITS().execute();

//                    Toast.makeText(mContext, "Failed to get the user Habits", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }
}
