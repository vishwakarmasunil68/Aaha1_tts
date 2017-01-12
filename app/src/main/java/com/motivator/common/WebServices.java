package com.motivator.common;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.motivator.database.NewDataBaseHelper;
import com.motivator.database.PrefData;
import com.motivator.model.CustomHabitPOJO;
import com.motivator.model.HabitImgModel;
import com.motivator.model.HabitTimelinePOJO;
import com.motivator.model.JourneyHabitPojo;
import com.motivator.model.JourneyPOJO;
import com.motivator.model.MusicPOJO;
import com.motivator.model.ReminderDescPOJO;
import com.motivator.model.ReminderPOJO;
import com.motivator.model.UserHabitTable;
import com.motivator.support.FileUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.motivator.common.GeneralUtility.mContext;

public class WebServices {

    public static final String gettyImageApiKey = "arzdfap2c7gu4mw59d45yyyt";
    public static final String ACCESS_LOGIN = "http://oldmaker.com/healthapp/hooks/accessLogin.php";
    public static final String NEW_ACCESS_LOGIN = "http://www.funhabits.co/aaha/profile.php";
    public static final String CUSTOM_HABIT_IMG = "https://api.gettyimages.com/v3/search/images?fields=id,title,thumb,referral_destinations&page_size=6&sort_order=most_popular&orientations=Vertical&phrase=";//phrase=sunny
    //"http://oldmaker.com/healthapp/hooks/getImages.php";
    public static final String Custom_habit_img_full = "https://api.gettyimages.com/v3/search/images?fields=id,title,comp,referral_destinations&page_size=6&sort_order=most_popular&orientations=Vertical&phrase=";
    public static final String MUSIC_FILES_URL = "http://www.funhabits.co/aaha/getvideocategory.php";


    public static final String HABIT_SAVE_URL = "http://www.funhabits.co/aaha/tableuserhabits.php";
    public static final String UPDATE_HABIT_URL = "http://www.funhabits.co/aaha/updateusertablehabits.php";
    public static final String DELETE_HABIT_URL = "http://www.funhabits.co/aaha/deletetableuserhabits.php";


    public static final String ADD_HABIT_TIMELINE_URL = "http://www.funhabits.co/aaha/addtimeline.php";
    public static final String UPDATE_HABIT_TIMELINE_URL = "http://www.funhabits.co/aaha/updatetimeline.php";

    public static final String ADD_CUSTOM_HABIT_URL = "http://www.funhabits.co/aaha/customhabits.php";
    public static final String UPDATE_CUSTOM_HABIT_URL = "http://www.funhabits.co/aaha/update_custom_habit.php";

    public static final String INSERT_USER_RITUAL = "http://www.funhabits.co/aaha/user_ritual_crud.php";
    public static final String ADD_REMINDER_DESC_URL = "http://www.funhabits.co/aaha/add_reminder_desc.php";
    public static final String UPDATE_REMINDER_DESC_URL = "http://www.funhabits.co/aaha/updatedescreminder.php";
    public static final String ADD_REMINDER_URL = "http://www.funhabits.co/aaha/add_reminder.php";
    public static final String UPDATE_REMINDER_URL = "http://www.funhabits.co/aaha/updatereminder.php";
    public static final String DELETE_REMINDER_URL = "http://www.funhabits.co/aaha/deletereminder.php";
    public static final String ADD_JOURNEY_URL = "http://www.funhabits.co/aaha/add_journey.php";
    public static final String UPDATE_JOURNEY_URL = "http://www.funhabits.co/aaha/update_journey.php";
    public static final String ADD_JOURNEY_HABIT_URL = "http://www.funhabits.co/aaha/add_journey_habits.php";
    public static final String UPDATE_JOURNEY_HABIT_URL = "http://www.funhabits.co/aaha/update_journey_habits.php";
    public static final String GET_ALL_INFO_URL = "http://www.funhabits.co/aaha/login.php";


    static HttpClient httpClient;
    static HttpPost httppost;
    static HttpResponse response;
    JSONObject jsonOBJ = null;
    JSONObject merge_vars = null;

    static BufferedReader bufferedReader;
    InputStream is;


    private static String convertToString(HttpResponse response) {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String LineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + LineSeparator);
            }
            bufferedReader.close();
            return stringBuffer.toString();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    public static String httpCall(String url, ArrayList<NameValuePair> postParameters) {
        String result = "";
        try {
            httpClient = new DefaultHttpClient();
            httppost = new HttpPost(url);

            httppost.setEntity(new UrlEncodedFormEntity(postParameters));

            // Execute HTTP Post Request
            response = httpClient.execute(httppost);

            //converting response into string
            result = convertToString(response);
            return result;
        } catch (IOException e) {
            Log.i("Io", e.toString());

            return "";
        }
    }

    public static String httpGetCall(String url) {
        String result = "";
        try {
            HttpParams httpParameters = new BasicHttpParams();

            //Setup timeouts
            HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
            HttpConnectionParams.setSoTimeout(httpParameters, 15000);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);

            HttpGet httpget = new HttpGet(url);

            HttpResponse response = httpclient.execute(httpget);
            //converting response into string
            result = convertToString(response);
            return result;
        } catch (IOException e) {
            Log.i("Io", e.toString());

            return "";
        }
    }


    /**
     * @param theUrl
     * @return
     */
    public static String executeHttpGetWithHeader(String theUrl, String apiKey, String keyword) {
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl + keyword);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            urlConnection = (URLConnection) url.openConnection();
            urlConnection.setRequestProperty("Api-Key", apiKey);

            // wrap the urlconnection in a bufferedreader
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    System.gc();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }


    public static String httpCallforImage(String url, String action_setimage, String userid, String imgPath) {
        String result = "";
        try {
            httpClient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            //httppost.setEntity(new UrlEncodedFormEntity(postParameters));


            //MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			/* example for setting a HttpMultipartMode */
            //builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			/* example for adding an image part */
            //FileBody fileBody = new FileBody(new File(image)); //image should be a String
            //builder.addPart("my_file", fileBody);

            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            //MultipartEntity multipartEntity = new MultipartEntity();
//			File image1 = new File(imgPath);

//			 entity.addPart("upload_image", new FileBody(new File (imgPath)));

            //MultipartEntity entity = new MultipartEntity();

            entity.addPart("action", new StringBody(action_setimage));
            entity.addPart("user_id", new StringBody(userid));
            entity.addPart("upload_image", new FileBody(new File(imgPath)));
//			entity.addPart("upload_image", new FileBody(imgPath));
            httppost.setEntity(entity);


            // Execute HTTP Post Request
            response = httpClient.execute(httppost);

            //converting response into string
            result = convertToString(response);
            return result;
        } catch (IOException e) {
            Log.i("Io", e.toString());

            return "";
        }
    }


    public static ArrayList<HabitImgModel> parseHabitImg(String jResponse) {
        ArrayList<HabitImgModel> imgArr = new ArrayList<HabitImgModel>();
        try {
            JSONObject jsonResult = new JSONObject(jResponse);
            String result_count = jsonResult.getString("result_count");

            JSONArray imageResultArray = jsonResult.getJSONArray("images");

            for (int i = 0; i < imageResultArray.length(); i++) {
                HabitImgModel habitImgObj = new HabitImgModel();

                JSONObject jObj = imageResultArray.getJSONObject(i);

                String id = jObj.getString("id");
                String title = jObj.getString("title");

                habitImgObj.setId(id);
                habitImgObj.setName(title);

                JSONArray displayImg = jObj.getJSONArray("display_sizes");
                for (int j = 0; j < displayImg.length(); j++) {
                    JSONObject dObj = displayImg.getJSONObject(0);
                    habitImgObj.setUrl(dObj.getString("uri"));
                }

                JSONArray refDestArray = jObj.getJSONArray("referral_destinations");
                for (int j = 0; j < refDestArray.length(); j++) {
                    JSONObject refObj = refDestArray.getJSONObject(0);
                    habitImgObj.setRefDestination(refObj.getString("uri"));
                }

                imgArr.add(habitImgObj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imgArr;
    }

    public static long DownloadMusicFile(final Context context, final String url_string, final MusicPOJO pojo) {
        final long[] val = {-1};
        new AsyncTask<Void, Void, Void>() {
            //			String file_path="";
//			String category="";
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                FileUtils.CreateALlMoodMusicDirectories();
//				category=pojo.getMusic_category();
//				file_path=FileUtils.GetMusicDirectory(category)+File.separator+pojo.getMusic_name();
            }

            @Override
            protected Void doInBackground(Void... params) {
                int count;
                try {
                    if (!pojo.getMusic_category().equals("")) {
                        URL url = new URL(url_string);
                        Log.d("musicdownloadService", "download url:-" + url_string);
                        URLConnection conexion = url.openConnection();
                        conexion.connect();
//					int lenghtOfFile = conexion.getContentLength();
//					Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
                        InputStream input = new BufferedInputStream(url.openStream());
                        OutputStream output = new FileOutputStream(pojo.getMusic_file_path());
                        byte data[] = new byte[1024];
                        long total = 0;
                        while ((count = input.read(data)) != -1) {
                            total += count;
//						publishProgress(""+(int)((total*100)/lenghtOfFile));
                            output.write(data, 0, count);
                        }

                        output.flush();
                        output.close();
                        input.close();
                    }
                } catch (Exception e) {
                    Log.d("musicdownloadService", url_string + "::::" + e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                File f = new File(pojo.getMusic_file_path());
                if (f.exists()) {
                    Log.d("musicdownloadService", "download successfull file exist");
//					NewDataBaseHelper helper = new NewDataBaseHelper(context);
//					long result_val = helper.insertMusicData(pojo);
//					val[0] = result_val;
                    val[0] = 1;
                } else {
                    Log.d("musicdownloadService", "file not found");
                }
            }
        }.execute();
        return val[0];
    }

    public class SaveHabittoServer extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        //        ProgressDialog progressDialog;
        String user_name;
        String h_id;
        String ritual_type;
        String user_habit_time;
        String is_habit_completed;
        String habit_priority;
        String habit_completed_on;
        String reminder_next_desc;
        String habbit_added_on;

        public SaveHabittoServer(String user_name, String h_id, String ritual_type, String user_habit_time, String is_habitcompleted,
                                 String habit_priority, String habit_completed_on, String reminder_next_desc, String habit_added_on) {
            this.user_name = user_name;
            this.h_id = h_id;
            this.ritual_type = ritual_type;
            this.user_habit_time = user_habit_time;
            this.is_habit_completed = is_habitcompleted;
            this.habit_priority = habit_priority;
            this.habit_completed_on = habit_completed_on;
            this.reminder_next_desc = reminder_next_desc;
            this.habbit_added_on = habit_added_on;

            Log.d(TAG, this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_user_name", user_name));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_habit_id", h_id));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_ritual_type", ritual_type));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_userhabit_time", user_habit_time));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_is_habit_completed", is_habit_completed));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_habit_priority", habit_priority));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_habit_completed_on", habit_completed_on));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_reminder_next_desc", reminder_next_desc));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_habit_added_on", habbit_added_on));

            try {
                jResult = WebServices.httpCall(WebServices.HABIT_SAVE_URL, nameValuePairs);
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

                    JSONObject result = jsonObject.optJSONObject("result");

                    String table_user_habits_id = result.optString("table_user_habits_id");
                    String table_user_habits_user_id = result.optString("table_user_habits_user_id");
                    String table_user_habits_user_name = result.optString("table_user_habits_user_name");
                    String table_user_habits_habit_id = result.optString("table_user_habits_habit_id");
                    String table_user_habits_ritual_type = result.optString("table_user_habits_ritual_type");
                    String table_user_habits_userhabit_time = result.optString("table_user_habits_userhabit_time");
                    String table_user_habits_is_habit_completed = result.optString("table_user_habits_is_habit_completed");
                    String table_user_habits_habit_priority = result.optString("table_user_habits_habit_priority");
                    String table_user_habits_habit_completed_on = result.optString("table_user_habits_habit_completed_on");
                    String table_user_habits_reminder_next_desc = result.optString("table_user_habits_reminder_next_desc");
                    String table_user_habits_habit_added_on = result.optString("table_user_habits_habit_added_on");


                    UserHabitTable userHabitTable = new UserHabitTable();
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

                    NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
                    helper.insertUserHabitData(userHabitTable);

                } else {
                    //toast.maketext(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }

        @Override
        public String toString() {
            return "SaveHabittoServer{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
//                    ", progressDialog=" + progressDialog +
                    ", user_name='" + user_name + '\'' +
                    ", h_id='" + h_id + '\'' +
                    ", ritual_type='" + ritual_type + '\'' +
                    ", user_habit_time='" + user_habit_time + '\'' +
                    ", is_habit_completed='" + is_habit_completed + '\'' +
                    ", habit_priority='" + habit_priority + '\'' +
                    ", habit_completed_on='" + habit_completed_on + '\'' +
                    ", reminder_next_desc='" + reminder_next_desc + '\'' +
                    ", habbit_added_on='" + habbit_added_on + '\'' +
                    '}';
        }
    }


    //update habit to server

    public class UpdateHabitService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        //        ProgressDialog progressDialog;
        String table_user_habits_id;
        String table_user_habits_user_id;
        String table_user_habits_user_name;
        String table_user_habits_habit_id;
        String table_user_habits_ritual_type;
        String table_user_habits_userhabit_time;
        String table_user_habits_is_habit_completed;
        String table_user_habits_habit_priority;
        String table_user_habits_habit_completed_on;
        String table_user_habits_reminder_next_desc;
        String table_user_habits_habit_added_on;

        public UpdateHabitService(
                String table_user_habits_id,
                String table_user_habits_user_id,
                String table_user_habits_user_name,
                String table_user_habits_habit_id,
                String table_user_habits_ritual_type,
                String table_user_habits_userhabit_time,
                String table_user_habits_is_habit_completed,
                String table_user_habits_habit_priority,
                String table_user_habits_habit_completed_on,
                String table_user_habits_reminder_next_desc,
                String table_user_habits_habit_added_on
        ) {
            this.table_user_habits_id = table_user_habits_id;
            this.table_user_habits_user_id = table_user_habits_user_id;
            this.table_user_habits_user_name = table_user_habits_user_name;
            this.table_user_habits_habit_id = table_user_habits_habit_id;
            this.table_user_habits_ritual_type = table_user_habits_ritual_type;
            this.table_user_habits_userhabit_time = table_user_habits_userhabit_time;
            this.table_user_habits_is_habit_completed = table_user_habits_is_habit_completed;
            this.table_user_habits_habit_priority = table_user_habits_habit_priority;
            this.table_user_habits_habit_completed_on = table_user_habits_habit_completed_on;
            this.table_user_habits_reminder_next_desc = table_user_habits_reminder_next_desc;
            this.table_user_habits_habit_added_on = table_user_habits_habit_added_on;

            Log.d(TAG, this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("table_user_habits_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_user_name", table_user_habits_user_name));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_habit_id", table_user_habits_habit_id));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_ritual_type", table_user_habits_ritual_type));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_userhabit_time", table_user_habits_userhabit_time));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_is_habit_completed", table_user_habits_is_habit_completed));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_habit_priority", table_user_habits_habit_priority));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_habit_completed_on", table_user_habits_habit_completed_on));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_reminder_next_desc", table_user_habits_reminder_next_desc));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_habit_added_on", table_user_habits_habit_added_on));
            nameValuePairs.add(new BasicNameValuePair("table_user_habits_id", table_user_habits_id));

            try {
                jResult = WebServices.httpCall(WebServices.UPDATE_HABIT_URL, nameValuePairs);
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

                    JSONObject result = jsonObject.optJSONObject("result");


                } else {
                    //toast.maketext(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }

        @Override
        public String toString() {
            return "UpdateHabitService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", table_user_habits_id='" + table_user_habits_id + '\'' +
                    ", table_user_habits_user_id='" + table_user_habits_user_id + '\'' +
                    ", table_user_habits_user_name='" + table_user_habits_user_name + '\'' +
                    ", table_user_habits_habit_id='" + table_user_habits_habit_id + '\'' +
                    ", table_user_habits_ritual_type='" + table_user_habits_ritual_type + '\'' +
                    ", table_user_habits_userhabit_time='" + table_user_habits_userhabit_time + '\'' +
                    ", table_user_habits_is_habit_completed='" + table_user_habits_is_habit_completed + '\'' +
                    ", table_user_habits_habit_priority='" + table_user_habits_habit_priority + '\'' +
                    ", table_user_habits_habit_completed_on='" + table_user_habits_habit_completed_on + '\'' +
                    ", table_user_habits_reminder_next_desc='" + table_user_habits_reminder_next_desc + '\'' +
                    ", table_user_habits_habit_added_on='" + table_user_habits_habit_added_on + '\'' +
                    '}';
        }
    }


    //delete habit service


    public class DeleteHabitService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        //        ProgressDialog progressDialog;
        String table_user_habits_id;

        public DeleteHabitService(
                String table_user_habits_id
        ) {
            this.table_user_habits_id = table_user_habits_id;
            Log.d(TAG, this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("table_user_habits_id", table_user_habits_id));

            try {
                jResult = WebServices.httpCall(WebServices.DELETE_HABIT_URL, nameValuePairs);
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

                    JSONObject result = jsonObject.optJSONObject("result");


                } else {
                    //toast.maketext(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }

    //add habit to time line
    public class AddHabitToTimeLine extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        //        ProgressDialog progressDialog;
        String user_name;
        String ritual_type;
        String date_of_status;
        String total_habit;
        String habit_completed;


        public AddHabitToTimeLine(String user_name, String ritual_type, String date_of_status, String total_habit,
                                  String habit_completed) {
            this.user_name = user_name;
            this.ritual_type = ritual_type;
            this.date_of_status = date_of_status;
            this.total_habit = total_habit;
            this.habit_completed = habit_completed;

            Log.d(TAG, this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {
            nameValuePairs.add(new BasicNameValuePair("timeline_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("timeline_user_name", user_name));
            nameValuePairs.add(new BasicNameValuePair("timeline_ritual_type", ritual_type));
            nameValuePairs.add(new BasicNameValuePair("timeline_dateof_status", date_of_status));
            nameValuePairs.add(new BasicNameValuePair("timeline_total_habits", total_habit));
            nameValuePairs.add(new BasicNameValuePair("timeline_habitcompleted", habit_completed));

            try {
                jResult = WebServices.httpCall(WebServices.ADD_HABIT_TIMELINE_URL, nameValuePairs);
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
                    JSONObject result = jsonObject.optJSONObject("result");
                    String timeline_id = result.optString("timeline_id");
                    String timeline_user_id = result.optString("timeline_user_id");
                    String timeline_user_name = result.optString("timeline_user_name");
                    String timeline_ritual_type = result.optString("timeline_ritual_type");
                    String timeline_dateof_status = result.optString("timeline_dateof_status");
                    String timeline_total_habits = result.optString("timeline_total_habits");
                    String timeline_habitcompleted = result.optString("timeline_habitcompleted");

                    HabitTimelinePOJO obj = new HabitTimelinePOJO();
                    obj.setTimeline_id(timeline_id);
                    obj.setTimeline_user_id(timeline_user_id);
                    obj.setTimeline_user_name(timeline_user_name);
                    obj.setTimeline_ritual_type(timeline_ritual_type);
                    obj.setTimeline_date_ofstatus(timeline_dateof_status);
                    obj.setTimeline_total_habits(timeline_total_habits);
                    obj.setTimeline_habitcompleted(timeline_habitcompleted);
                    NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
                    helper.inserttimelineData(obj);

                } else {
                    //toast.maketext(mContext, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }


        @Override
        public String toString() {
            return "AddHabitToTimeLine{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
//                    ", progressDialog=" + progressDialog +
                    ", user_name='" + user_name + '\'' +
                    ", ritual_type='" + ritual_type + '\'' +
                    ", date_of_status='" + date_of_status + '\'' +
                    ", total_habit='" + total_habit + '\'' +
                    ", habit_completed='" + habit_completed + '\'' +
                    '}';
        }
    }


    public class UpdateHabitToTimeLine extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        //        ProgressDialog progressDialog;
        String user_name;
        String ritual_type;
        String date_of_status;
        String total_habit;
        String habit_completed;
        String timeline_id;


        public UpdateHabitToTimeLine(String timeline_id, String user_name, String ritual_type, String date_of_status, String total_habit,
                                     String habit_completed) {
            this.user_name = user_name;
            this.ritual_type = ritual_type;
            this.date_of_status = date_of_status;
            this.total_habit = total_habit;
            this.habit_completed = habit_completed;
            this.timeline_id = timeline_id;

            Log.d(TAG, "time line:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {
            nameValuePairs.add(new BasicNameValuePair("timeline_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("timeline_user_name", user_name));
            nameValuePairs.add(new BasicNameValuePair("timeline_ritual_type", ritual_type));
            nameValuePairs.add(new BasicNameValuePair("timeline_dateof_status", date_of_status));
            nameValuePairs.add(new BasicNameValuePair("timeline_total_habits", total_habit));
            nameValuePairs.add(new BasicNameValuePair("timeline_habitcompleted", habit_completed));
            nameValuePairs.add(new BasicNameValuePair("timeline_id", timeline_id));

            try {
                jResult = WebServices.httpCall(WebServices.UPDATE_HABIT_TIMELINE_URL, nameValuePairs);
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

                } else {
                    //toast.maketext(mContext, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }


        @Override
        public String toString() {
            return "AddHabitToTimeLine{" +
                    ", timeline_id=" + timeline_id +
                    ", user_name='" + user_name + '\'' +
                    ", ritual_type='" + ritual_type + '\'' +
                    ", date_of_status='" + date_of_status + '\'' +
                    ", total_habit='" + total_habit + '\'' +
                    ", habit_completed='" + habit_completed + '\'' +
                    '}';
        }
    }


    public class AddCustomHabitService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        //        ProgressDialog progressDialog;
        String h_id;
        String habit;
        String description;
        String benefits;
        String habit_time;
        String reminder_desc1;
        String reminder_desc2;
        String reminder_desc3;
        String reminder_desc4;
        String reminder_desc5;
        String reminder_desc6;


        public AddCustomHabitService(String h_id,
                                     String habit,
                                     String description,
                                     String benefits,
                                     String habit_time,
                                     String reminder_desc1,
                                     String reminder_desc2,
                                     String reminder_desc3,
                                     String reminder_desc4,
                                     String reminder_desc5,
                                     String reminder_desc6) {
            this.h_id = h_id;
            this.habit = habit;
            this.description = description;
            this.benefits = benefits;
            this.habit_time = habit_time;
            this.reminder_desc1 = reminder_desc1;
            this.reminder_desc2 = reminder_desc2;
            this.reminder_desc3 = reminder_desc3;
            this.reminder_desc4 = reminder_desc4;
            this.reminder_desc5 = reminder_desc5;
            this.reminder_desc6 = reminder_desc6;

            Log.d(TAG, "custom habit:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {
            nameValuePairs.add(new BasicNameValuePair("custom_habit_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("custom_h_id", h_id));
            nameValuePairs.add(new BasicNameValuePair("custom_habit", habit));
            nameValuePairs.add(new BasicNameValuePair("custom_description", description));
            nameValuePairs.add(new BasicNameValuePair("custom_benefits", benefits));
            nameValuePairs.add(new BasicNameValuePair("custom_habit_time", habit_time));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des1", reminder_desc1));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des2", reminder_desc2));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des3", reminder_desc3));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des4", reminder_desc4));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des5", reminder_desc5));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des6", reminder_desc6));

            try {
                jResult = WebServices.httpCall(WebServices.ADD_CUSTOM_HABIT_URL, nameValuePairs);
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
                    JSONObject object = jsonObject.optJSONObject("result");

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

                    CustomHabitPOJO pojo = new CustomHabitPOJO();
                    pojo.setCustom_habit_id(custom_habit_id);
                    pojo.setCustom_habit_user_id(custom_habit_user_id);
                    pojo.setCustom_h_id(custom_h_id);
                    pojo.setCustom_habit(custom_habit);
                    pojo.setCustom_description(custom_description);
                    pojo.setCustom_benefits(custom_benefits);
                    pojo.setCustom_habit_time(custom_habit_time);
                    pojo.setCustom_rem_des1(custom_rem_des1);
                    pojo.setCustom_rem_des2(custom_rem_des2);
                    pojo.setCustom_rem_des3(custom_rem_des3);
                    pojo.setCustom_rem_des4(custom_rem_des4);
                    pojo.setCustom_rem_des5(custom_rem_des5);
                    pojo.setCustom_rem_des6(custom_rem_des6);

                    NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
                    helper.insertcustomData(pojo);

                } else {
                    //toast.maketext(mContext, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }


        @Override
        public String toString() {
            return "AddCustomHabitService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", h_id='" + h_id + '\'' +
                    ", habit='" + habit + '\'' +
                    ", description='" + description + '\'' +
                    ", benefits='" + benefits + '\'' +
                    ", habit_time='" + habit_time + '\'' +
                    ", reminder_desc1='" + reminder_desc1 + '\'' +
                    ", reminder_desc2='" + reminder_desc2 + '\'' +
                    ", reminder_desc3='" + reminder_desc3 + '\'' +
                    ", reminder_desc4='" + reminder_desc4 + '\'' +
                    ", reminder_desc5='" + reminder_desc5 + '\'' +
                    ", reminder_desc6='" + reminder_desc6 + '\'' +
                    '}';
        }
    }


    //update CUstom Habit:-----


    public class UpdateCustomHabitService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        //        ProgressDialog progressDialog;

        String custom_habit_user_id;
        String custom_h_id;
        String custom_habit;
        String custom_description;
        String custom_benefits;
        String custom_habit_time;
        String custom_rem_des1;
        String custom_rem_des2;
        String custom_rem_des3;
        String custom_rem_des4;
        String custom_rem_des5;
        String custom_rem_des6;
        String custom_habit_id;

        public UpdateCustomHabitService(String custom_habit_user_id,
                                        String custom_h_id,
                                        String custom_habit,
                                        String custom_description,
                                        String custom_benefits,
                                        String custom_habit_time,
                                        String custom_rem_des1,
                                        String custom_rem_des2,
                                        String custom_rem_des3,
                                        String custom_rem_des4,
                                        String custom_rem_des5,
                                        String custom_rem_des6,
                                        String custom_habit_id) {
            this.custom_habit_user_id=custom_habit_user_id;
            this.custom_h_id=custom_h_id;
            this.custom_habit=custom_habit;
            this.custom_description=custom_description;
            this.custom_benefits=custom_benefits;
            this.custom_habit_time=custom_habit_time;
            this.custom_rem_des1=custom_rem_des1;
            this.custom_rem_des2=custom_rem_des2;
            this.custom_rem_des3=custom_rem_des3;
            this.custom_rem_des4=custom_rem_des4;
            this.custom_rem_des5=custom_rem_des5;
            this.custom_rem_des6=custom_rem_des6;
            this.custom_habit_id=custom_habit_id;

            Log.d(TAG, "custom habit update:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("custom_habit_user_id", custom_habit_user_id));
            nameValuePairs.add(new BasicNameValuePair("custom_h_id", custom_h_id));
            nameValuePairs.add(new BasicNameValuePair("custom_habit", custom_habit));
            nameValuePairs.add(new BasicNameValuePair("custom_description", custom_description));
            nameValuePairs.add(new BasicNameValuePair("custom_benefits", custom_benefits));
            nameValuePairs.add(new BasicNameValuePair("custom_habit_time", custom_habit_time));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des1", custom_rem_des1));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des2", custom_rem_des2));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des3", custom_rem_des3));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des4", custom_rem_des4));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des5", custom_rem_des5));
            nameValuePairs.add(new BasicNameValuePair("custom_rem_des6", custom_rem_des6));
            nameValuePairs.add(new BasicNameValuePair("custom_habit_id", custom_habit_id));

            try {
                jResult = WebServices.httpCall(WebServices.UPDATE_CUSTOM_HABIT_URL, nameValuePairs);
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

                } else {
                    //toast.maketext(mContext, "Custom habit updation Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }

        @Override
        public String toString() {
            return "UpdateCustomHabitService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", custom_habit_user_id='" + custom_habit_user_id + '\'' +
                    ", custom_h_id='" + custom_h_id + '\'' +
                    ", custom_habit='" + custom_habit + '\'' +
                    ", custom_description='" + custom_description + '\'' +
                    ", custom_benefits='" + custom_benefits + '\'' +
                    ", custom_habit_time='" + custom_habit_time + '\'' +
                    ", custom_rem_des1='" + custom_rem_des1 + '\'' +
                    ", custom_rem_des2='" + custom_rem_des2 + '\'' +
                    ", custom_rem_des3='" + custom_rem_des3 + '\'' +
                    ", custom_rem_des4='" + custom_rem_des4 + '\'' +
                    ", custom_rem_des5='" + custom_rem_des5 + '\'' +
                    ", custom_rem_des6='" + custom_rem_des6 + '\'' +
                    ", custom_habit_id='" + custom_habit_id + '\'' +
                    '}';
        }
    }


    //end update custom habit---


    public class AddUserRitual extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String user_name;
        String ritual_name;
        String ritual_image;
        String ritual_time;
        String notification_style;
        String urgency_swipe;
        String announce_first;
        String ring_in_silent;
        String ritual_display_name;
        String ritual_reminder;


        public AddUserRitual(String user_name,
                             String ritual_name,
                             String ritual_image,
                             String ritual_time,
                             String notification_style,
                             String urgency_swipe,
                             String announce_first,
                             String ring_in_silent,
                             String ritual_display_name,
                             String ritual_reminder) {
            this.user_name = user_name;
            this.ritual_name = ritual_name;
            this.ritual_image = ritual_image;
            this.ritual_time = ritual_time;
            this.notification_style = notification_style;
            this.urgency_swipe = urgency_swipe;
            this.announce_first = announce_first;
            this.ring_in_silent = ring_in_silent;
            this.ritual_display_name = ritual_display_name;
            this.ritual_reminder = ritual_reminder;

            Log.d(TAG, "custom habit:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("ritual_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("ritual_user_name", user_name));
            nameValuePairs.add(new BasicNameValuePair("ritual_img", ritual_image));
            nameValuePairs.add(new BasicNameValuePair("ritual_time", ritual_time));
            nameValuePairs.add(new BasicNameValuePair("ritual_notification_style", notification_style));
            nameValuePairs.add(new BasicNameValuePair("ritual_urgency_swipe", urgency_swipe));
            nameValuePairs.add(new BasicNameValuePair("ritual_announce_first", announce_first));
            nameValuePairs.add(new BasicNameValuePair("ritual_ringin_slient", ring_in_silent));
            nameValuePairs.add(new BasicNameValuePair("ritual_display_name", ritual_display_name));
            nameValuePairs.add(new BasicNameValuePair("ritual_reminder", ritual_reminder));

            try {
                jResult = WebServices.httpCall(WebServices.INSERT_USER_RITUAL, nameValuePairs);
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

                } else {
                    //toast.maketext(mContext, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }

        @Override
        public String toString() {
            return "AddUserRitual{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", ritual_name='" + ritual_name + '\'' +
                    ", ritual_image='" + ritual_image + '\'' +
                    ", ritual_time='" + ritual_time + '\'' +
                    ", notification_style='" + notification_style + '\'' +
                    ", urgency_swipe='" + urgency_swipe + '\'' +
                    ", announce_first='" + announce_first + '\'' +
                    ", ring_in_silent='" + ring_in_silent + '\'' +
                    ", ritual_display_name='" + ritual_display_name + '\'' +
                    ", ritual_reminder='" + ritual_reminder + '\'' +
                    '}';
        }
    }


    public class AddReminderService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String rem_user_id;
        String rem_rem_id;
        String rem_user_name;
        String rem_time;
        String rem_ritual_type;
        String rem_snooze_time;

        String response;

        public AddReminderService(String rem_user_id,
                                  String rem_rem_id,
                                  String rem_user_name,
                                  String rem_time,
                                  String rem_ritual_type,
                                  String rem_snooze_time) {
            this.rem_user_id = rem_user_id;
            this.rem_rem_id = rem_rem_id;
            this.rem_user_name = rem_user_name;
            this.rem_time = rem_time;
            this.rem_ritual_type = rem_ritual_type;
            this.rem_snooze_time = rem_snooze_time;


            Log.d(TAG, "Add reminder:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("rem_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("rem_rem_id", rem_rem_id));
            nameValuePairs.add(new BasicNameValuePair("rem_user_name", rem_user_name));
            nameValuePairs.add(new BasicNameValuePair("rem_time", rem_time));
            nameValuePairs.add(new BasicNameValuePair("rem_ritual_type", rem_ritual_type));
            nameValuePairs.add(new BasicNameValuePair("rem_snooze_time", rem_snooze_time));

            try {
                jResult = WebServices.httpCall(WebServices.ADD_REMINDER_URL, nameValuePairs);
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
                Log.d(TAG, "reminder response:-" + aVoid);
                JSONObject jsonObject = new JSONObject(aVoid);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {
                    JSONObject jsonObject1 = jsonObject.optJSONObject("result");

                    String rem_id = jsonObject1.optString("rem_id");
                    String rem_user_id = jsonObject1.optString("rem_user_id");
                    String rem_rem_id = jsonObject1.optString("rem_rem_id");
                    String rem_user_name = jsonObject1.optString("rem_user_name");
                    String rem_time = jsonObject1.optString("rem_time");
                    String rem_ritual_type = jsonObject1.optString("rem_ritual_type");
                    String rem_snooze_time = jsonObject1.optString("rem_snooze_time");

                    ReminderPOJO pojo = new ReminderPOJO();
                    pojo.setRem_id(rem_id);
                    pojo.setReminder_user_id(rem_user_id);
                    pojo.setReminder_id(rem_rem_id);
                    pojo.setReminder_user_name(rem_user_name);
                    pojo.setReminder_time(rem_time);
                    pojo.setReminder_ritual_type(rem_ritual_type);
                    pojo.setReminder_snooze_time(rem_snooze_time);

                    NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
                    long val = helper.insertreminderData(pojo);
                    Log.d(TAG, "reminder response:-" + val + "resp:-" + pojo.toString());
                    Log.d(TAG, "reminder response:-" + val + "resp:-" + helper.getAllReminderData());


                } else {
                    //toast.maketext(mContext, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }


        @Override
        public String toString() {
            return "AddReminderService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", rem_user_id='" + rem_user_id + '\'' +
                    ", rem_rem_id='" + rem_rem_id + '\'' +
                    ", rem_user_name='" + rem_user_name + '\'' +
                    ", rem_time='" + rem_time + '\'' +
                    ", rem_ritual_type='" + rem_ritual_type + '\'' +
                    ", rem_snooze_time='" + rem_snooze_time + '\'' +
                    '}';
        }
    }

    public class UpdateReminderService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String rem_user_id;
        String rem_rem_id;
        String rem_user_name;
        String rem_time;
        String rem_ritual_type;
        String rem_snooze_time;
        String rem_id;


        public UpdateReminderService(String rem_user_id,
                                     String rem_rem_id,
                                     String rem_user_name,
                                     String rem_time,
                                     String rem_ritual_type,
                                     String rem_snooze_time,
                                     String rem_id
        ) {
            this.rem_user_id = rem_user_id;
            this.rem_rem_id = rem_rem_id;
            this.rem_user_name = rem_user_name;
            this.rem_time = rem_time;
            this.rem_ritual_type = rem_ritual_type;
            this.rem_snooze_time = rem_snooze_time;
            this.rem_id = rem_id;


            Log.d(TAG, "update reminder:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("rem_user_id", rem_user_id));
            nameValuePairs.add(new BasicNameValuePair("rem_rem_id", rem_rem_id));
            nameValuePairs.add(new BasicNameValuePair("rem_user_name", rem_user_name));
            nameValuePairs.add(new BasicNameValuePair("rem_time", rem_time));
            nameValuePairs.add(new BasicNameValuePair("rem_ritual_type", rem_ritual_type));
            nameValuePairs.add(new BasicNameValuePair("rem_snooze_time", rem_snooze_time));
            nameValuePairs.add(new BasicNameValuePair("rem_id", rem_id));

            try {
                jResult = WebServices.httpCall(WebServices.UPDATE_REMINDER_URL, nameValuePairs);
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
                    Log.d(TAG, "update reminder service:-success:-" + jsonObject.toString());
                } else {
                    Log.d(TAG, "update reminder service:-failed");

                    //toast.maketext(mContext, "Reminder updation failed.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }

        @Override
        public String toString() {
            return "UpdateReminderService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", rem_user_id='" + rem_user_id + '\'' +
                    ", rem_rem_id='" + rem_rem_id + '\'' +
                    ", rem_user_name='" + rem_user_name + '\'' +
                    ", rem_time='" + rem_time + '\'' +
                    ", rem_ritual_type='" + rem_ritual_type + '\'' +
                    ", rem_snooze_time='" + rem_snooze_time + '\'' +
                    ", rem_id='" + rem_id + '\'' +
                    '}';
        }
    }
    //delete reminder

    public class DeleteReminderService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String rem_id;

        public DeleteReminderService(String rem_id) {
            this.rem_id = rem_id;


            Log.d(TAG, "update reminder:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("rem_id", rem_id));


            try {
                jResult = WebServices.httpCall(WebServices.DELETE_REMINDER_URL, nameValuePairs);
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
                    Log.d(TAG, "update reminder service:-success:-" + jsonObject.toString());
                } else {
                    Log.d(TAG, "update reminder service:-failed");

                    //toast.maketext(mContext, "Reminder Deletion failed.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }


    public class AddReminderDescService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String rem_desc_user_id;
        String rem_desc_user_name;
        String rem_desc_time;
        String rem_desc_day;
        String rem_desc_on_off;
        String rem_desc_stamp;
        String rem_desc_rem_id;

        public AddReminderDescService(String rem_desc_user_id,
                                      String rem_desc_user_name,
                                      String rem_desc_time,
                                      String rem_desc_day,
                                      String rem_desc_on_off,
                                      String rem_desc_stamp,
                                      String rem_desc_rem_id) {
            this.rem_desc_user_id = rem_desc_user_id;
            this.rem_desc_user_name = rem_desc_user_name;
            this.rem_desc_time = rem_desc_time;
            this.rem_desc_day = rem_desc_day;
            this.rem_desc_on_off = rem_desc_on_off;
            this.rem_desc_stamp = rem_desc_stamp;
            this.rem_desc_rem_id = rem_desc_rem_id;

            Log.d(TAG, "add reminder desc:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("rem_desc_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_user_name", rem_desc_user_name));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_time", rem_desc_time));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_day", rem_desc_day));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_on_off", rem_desc_on_off));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_stamp", rem_desc_stamp));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_rem_id", rem_desc_rem_id));
            try {
                jResult = WebServices.httpCall(WebServices.ADD_REMINDER_DESC_URL, nameValuePairs);
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
                    JSONObject jsonObject1 = jsonObject.optJSONObject("result");

                    String rem_desc_id = jsonObject1.optString("rem_desc_id");
                    String rem_desc_user_id = jsonObject1.optString("rem_desc_user_id");
                    String rem_desc_user_name = jsonObject1.optString("rem_desc_user_name");
                    String rem_desc_time = jsonObject1.optString("rem_desc_time");
                    String rem_desc_day = jsonObject1.optString("rem_desc_day");
                    String rem_desc_on_off = jsonObject1.optString("rem_desc_on_off");
                    String rem_desc_stamp = jsonObject1.optString("rem_desc_stamp");
                    String rem_desc_rem_id = jsonObject1.optString("rem_desc_rem_id");

                    ReminderDescPOJO pojo = new ReminderDescPOJO();
                    pojo.setRem_desc_id(rem_desc_id);
                    pojo.setRem_desc_user_id(rem_desc_user_id);
                    pojo.setRem_desc_user_name(rem_desc_user_name);
                    pojo.setRem_desc_time(rem_desc_time);
                    pojo.setRem_desc_day(rem_desc_day);
                    pojo.setRem_desc_on_off(rem_desc_on_off);
                    pojo.setRem_desc_stamp(rem_desc_stamp);
                    pojo.setRem_desc_rem_id(rem_desc_rem_id);

                    NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
                    helper.insertreminderDescData(pojo);

                } else {
                    //toast.maketext(mContext, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }
    }

    public class UpdateReminderDescService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String rem_desc_user_id;
        String rem_desc_user_name;
        String rem_desc_time;
        String rem_desc_day;
        String rem_desc_on_off;
        String rem_desc_stamp;
        String rem_desc_rem_id;
        String rem_desc_id;

        public UpdateReminderDescService(String rem_desc_user_id,
                                         String rem_desc_user_name,
                                         String rem_desc_time,
                                         String rem_desc_day,
                                         String rem_desc_on_off,
                                         String rem_desc_stamp,
                                         String rem_desc_rem_id,
                                         String rem_desc_id) {
            this.rem_desc_user_id = rem_desc_user_id;
            this.rem_desc_user_name = rem_desc_user_name;
            this.rem_desc_time = rem_desc_time;
            this.rem_desc_day = rem_desc_day;
            this.rem_desc_on_off = rem_desc_on_off;
            this.rem_desc_stamp = rem_desc_stamp;
            this.rem_desc_rem_id = rem_desc_rem_id;
            this.rem_desc_id = rem_desc_id;

            Log.d(TAG, "update reminder desc:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("rem_desc_user_id", rem_desc_user_id));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_user_name", rem_desc_user_name));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_time", rem_desc_time));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_day", rem_desc_day));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_on_off", rem_desc_on_off));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_stamp", rem_desc_stamp));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_rem_id", rem_desc_rem_id));
            nameValuePairs.add(new BasicNameValuePair("rem_desc_id", rem_desc_id));
            try {
                jResult = WebServices.httpCall(WebServices.UPDATE_REMINDER_DESC_URL, nameValuePairs);
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
                    JSONObject jsonObject1 = jsonObject.optJSONObject("result");
                } else {
                    //toast.maketext(mContext, "Reminder Desc Updation Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }

        @Override
        public String toString() {
            return "UpdateReminderDescService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", rem_desc_user_id='" + rem_desc_user_id + '\'' +
                    ", rem_desc_user_name='" + rem_desc_user_name + '\'' +
                    ", rem_desc_time='" + rem_desc_time + '\'' +
                    ", rem_desc_day='" + rem_desc_day + '\'' +
                    ", rem_desc_on_off='" + rem_desc_on_off + '\'' +
                    ", rem_desc_stamp='" + rem_desc_stamp + '\'' +
                    ", rem_desc_rem_id='" + rem_desc_rem_id + '\'' +
                    ", rem_desc_id='" + rem_desc_id + '\'' +
                    '}';
        }
    }


    public class AddJourneyService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String j_user_id;
        String j_user_name;
        String j_journey_name;
        String j_total_events;
        String j_total_events_achived;
        String j_status_step1;
        String j_status_step2;
        String j_status_step3;
        String j_status_step4;
        String j_status_step5;

        public AddJourneyService(String j_user_id,
                                 String j_user_name,
                                 String j_journey_name,
                                 String j_total_events,
                                 String j_total_events_achived,
                                 String j_status_step1,
                                 String j_status_step2,
                                 String j_status_step3,
                                 String j_status_step4,
                                 String j_status_step5) {
            this.j_user_id = j_user_id;
            this.j_user_name = j_user_name;
            this.j_journey_name = j_journey_name;
            this.j_total_events = j_total_events;
            this.j_total_events_achived = j_total_events_achived;
            this.j_status_step1 = j_status_step1;
            this.j_status_step2 = j_status_step2;
            this.j_status_step3 = j_status_step3;
            this.j_status_step4 = j_status_step4;
            this.j_status_step5 = j_status_step5;

            Log.d(TAG, "update reminder desc:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("j_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("j_user_name", j_user_name));
            nameValuePairs.add(new BasicNameValuePair("j_journey_name", j_journey_name));
            nameValuePairs.add(new BasicNameValuePair("j_total_events", j_total_events));
            nameValuePairs.add(new BasicNameValuePair("j_total_events_achived", j_total_events_achived));
            nameValuePairs.add(new BasicNameValuePair("j_status_step1", j_status_step1));
            nameValuePairs.add(new BasicNameValuePair("j_status_step2", j_status_step2));
            nameValuePairs.add(new BasicNameValuePair("j_status_step3", j_status_step3));
            nameValuePairs.add(new BasicNameValuePair("j_status_step4", j_status_step4));
            nameValuePairs.add(new BasicNameValuePair("j_status_step5", j_status_step5));
            try {
                jResult = WebServices.httpCall(WebServices.ADD_JOURNEY_URL, nameValuePairs);
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
                    JSONObject jsonObject1 = jsonObject.optJSONObject("result");
                    String j_id = jsonObject1.optString("j_id");
                    String j_user_id = jsonObject1.optString("j_user_id");
                    String j_user_name = jsonObject1.optString("j_user_name");
                    String j_journey_name = jsonObject1.optString("j_journey_name");
                    String j_total_events = jsonObject1.optString("j_total_events");
                    String j_total_events_achived = jsonObject1.optString("j_total_events_achived");
                    String j_status_step1 = jsonObject1.optString("j_status_step1");
                    String j_status_step2 = jsonObject1.optString("j_status_step2");
                    String j_status_step3 = jsonObject1.optString("j_status_step3");
                    String j_status_step4 = jsonObject1.optString("j_status_step4");
                    String j_status_step5 = jsonObject1.optString("j_status_step5");

                    JourneyPOJO obj = new JourneyPOJO();
                    obj.setJ_id(j_id);
                    obj.setJ_user_id(j_user_id);
                    obj.setJ_user_name(j_user_name);
                    obj.setJ_journey_name(j_journey_name);
                    obj.setJ_total_events(j_total_events);
                    obj.setJ_total_events_achived(j_total_events_achived);
                    obj.setJ_status_step1(j_status_step1);
                    obj.setJ_status_step2(j_status_step2);
                    obj.setJ_status_step3(j_status_step3);
                    obj.setJ_status_step4(j_status_step4);
                    obj.setJ_status_step5(j_status_step5);

                    NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
                    helper.insertJourneyData(obj);

                } else {
                    //toast.maketext(mContext, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }

        @Override
        public String toString() {
            return "AddJourneyService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", j_user_id='" + j_user_id + '\'' +
                    ", j_user_name='" + j_user_name + '\'' +
                    ", j_journey_name='" + j_journey_name + '\'' +
                    ", j_total_events='" + j_total_events + '\'' +
                    ", j_total_events_achived='" + j_total_events_achived + '\'' +
                    ", j_status_step1='" + j_status_step1 + '\'' +
                    ", j_status_step2='" + j_status_step2 + '\'' +
                    ", j_status_step3='" + j_status_step3 + '\'' +
                    ", j_status_step4='" + j_status_step4 + '\'' +
                    ", j_status_step5='" + j_status_step5 + '\'' +
                    '}';
        }
    }


    public class UpdateJourneyService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String j_id;
        String j_user_id;
        String j_user_name;
        String j_journey_name;
        String j_total_events;
        String j_total_events_achived;
        String j_status_step1;
        String j_status_step2;
        String j_status_step3;
        String j_status_step4;
        String j_status_step5;

        public UpdateJourneyService(String j_id,
                                    String j_user_id,
                                    String j_user_name,
                                    String j_journey_name,
                                    String j_total_events,
                                    String j_total_events_achived,
                                    String j_status_step1,
                                    String j_status_step2,
                                    String j_status_step3,
                                    String j_status_step4,
                                    String j_status_step5) {
            this.j_id = j_id;
            this.j_user_id = j_user_id;
            this.j_user_name = j_user_name;
            this.j_journey_name = j_journey_name;
            this.j_total_events = j_total_events;
            this.j_total_events_achived = j_total_events_achived;
            this.j_status_step1 = j_status_step1;
            this.j_status_step2 = j_status_step2;
            this.j_status_step3 = j_status_step3;
            this.j_status_step4 = j_status_step4;
            this.j_status_step5 = j_status_step5;

            Log.d("database", "update journey data:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("j_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("j_user_name", j_user_name));
            nameValuePairs.add(new BasicNameValuePair("j_journey_name", j_journey_name));
            nameValuePairs.add(new BasicNameValuePair("j_total_events", j_total_events));
            nameValuePairs.add(new BasicNameValuePair("j_total_events_achived", j_total_events_achived));
            nameValuePairs.add(new BasicNameValuePair("j_status_step1", j_status_step1));
            nameValuePairs.add(new BasicNameValuePair("j_status_step2", j_status_step2));
            nameValuePairs.add(new BasicNameValuePair("j_status_step3", j_status_step3));
            nameValuePairs.add(new BasicNameValuePair("j_status_step4", j_status_step4));
            nameValuePairs.add(new BasicNameValuePair("j_status_step5", j_status_step5));
            try {
                jResult = WebServices.httpCall(WebServices.UPDATE_JOURNEY_URL, nameValuePairs);
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
                    JSONObject jsonObject1 = jsonObject.optJSONObject("result");

                } else {
                    //toast.maketext(mContext, "Journey updation failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }

        @Override
        public String toString() {
            return "AddJourneyService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", j_user_id='" + j_user_id + '\'' +
                    ", j_user_name='" + j_user_name + '\'' +
                    ", j_journey_name='" + j_journey_name + '\'' +
                    ", j_total_events='" + j_total_events + '\'' +
                    ", j_total_events_achived='" + j_total_events_achived + '\'' +
                    ", j_status_step1='" + j_status_step1 + '\'' +
                    ", j_status_step2='" + j_status_step2 + '\'' +
                    ", j_status_step3='" + j_status_step3 + '\'' +
                    ", j_status_step4='" + j_status_step4 + '\'' +
                    ", j_status_step5='" + j_status_step5 + '\'' +
                    '}';
        }
    }


    public class AddJourneyHabitService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String j_h_user_id;
        String j_h_user_name;
        String j_h_journey_name;
        String j_h_hid;
        String j_h_letter_reap;
        String j_h_challenge_acc;
        String j_h_goal_completed;
        String j_h_action_done;
        String j_h_motivation;
        String j_h_golden_chllenge;
        String j_golden_status;

        public AddJourneyHabitService(String j_h_user_id,
                                      String j_h_user_name,
                                      String j_h_journey_name,
                                      String j_h_hid,
                                      String j_h_letter_reap,
                                      String j_h_challenge_acc,
                                      String j_h_goal_completed,
                                      String j_h_action_done,
                                      String j_h_motivation,
                                      String j_h_golden_chllenge,
                                      String j_golden_status) {
            this.j_h_user_id = j_h_user_id;
            this.j_h_user_name = j_h_user_name;
            this.j_h_journey_name = j_h_journey_name;
            this.j_h_hid = j_h_hid;
            this.j_h_letter_reap = j_h_letter_reap;
            this.j_h_challenge_acc = j_h_challenge_acc;
            this.j_h_goal_completed = j_h_goal_completed;
            this.j_h_action_done = j_h_action_done;
            this.j_h_motivation = j_h_motivation;
            this.j_h_golden_chllenge = j_h_golden_chllenge;
            this.j_golden_status = j_golden_status;

            Log.d(TAG, "update reminder desc:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("j_h_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("j_h_user_name", j_h_user_name));
            nameValuePairs.add(new BasicNameValuePair("j_h_journey_name", j_h_journey_name));
            nameValuePairs.add(new BasicNameValuePair("j_h_hid", j_h_hid));
            nameValuePairs.add(new BasicNameValuePair("j_h_letter_reap", j_h_letter_reap));
            nameValuePairs.add(new BasicNameValuePair("j_h_challenge_acc", j_h_challenge_acc));
            nameValuePairs.add(new BasicNameValuePair("j_h_goal_completed", j_h_goal_completed));
            nameValuePairs.add(new BasicNameValuePair("j_h_action_done", j_h_action_done));
            nameValuePairs.add(new BasicNameValuePair("j_h_motivation", j_h_motivation));
            nameValuePairs.add(new BasicNameValuePair("j_h_golden_chllenge", j_h_golden_chllenge));
            nameValuePairs.add(new BasicNameValuePair("j_golden_status", j_golden_status));
            try {
                jResult = WebServices.httpCall(WebServices.ADD_JOURNEY_HABIT_URL, nameValuePairs);
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

                    JSONObject jsonObject1 = jsonObject.optJSONObject("result");
                    String j_h_id = jsonObject1.optString("j_h_id");
                    String j_h_user_id = jsonObject1.optString("j_h_user_id");
                    String j_h_user_name = jsonObject1.optString("j_h_user_name");
                    String j_h_journey_name = jsonObject1.optString("j_h_journey_name");
                    String j_h_hid = jsonObject1.optString("j_h_hid");
                    String j_h_letter_reap = jsonObject1.optString("j_h_letter_reap");
                    String j_h_challenge_acc = jsonObject1.optString("j_h_challenge_acc");
                    String j_h_goal_completed = jsonObject1.optString("j_h_goal_completed");
                    String j_h_action_done = jsonObject1.optString("j_h_action_done");
                    String j_h_motivation = jsonObject1.optString("j_h_motivation");
                    String j_h_golden_chllenge = jsonObject1.optString("j_h_golden_chllenge");
                    String j_golden_status = jsonObject1.optString("j_golden_status");

                    JourneyHabitPojo pojo = new JourneyHabitPojo();
                    pojo.setJ_h_id(j_h_id);
                    pojo.setJ_h_user_id(j_h_user_id);
                    pojo.setJ_h_user_name(j_h_user_name);
                    pojo.setJ_h_journey_name(j_h_journey_name);
                    pojo.setJ_h_hid(j_h_hid);
                    pojo.setJ_h_letter_reap(j_h_letter_reap);
                    pojo.setJ_h_challenge_acc(j_h_challenge_acc);
                    pojo.setJ_h_goal_completed(j_h_goal_completed);
                    pojo.setJ_h_action_done(j_h_action_done);
                    pojo.setJ_h_motivation(j_h_motivation);
                    pojo.setJ_h_golden_chllenge(j_h_golden_chllenge);
                    pojo.setJ_golden_status(j_golden_status);

                    NewDataBaseHelper helper = new NewDataBaseHelper(mContext);
                    helper.insertJourneyHabitData(pojo);


                } else {
                    //toast.maketext(mContext, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }

        @Override
        public String toString() {
            return "AddJourneyHabitService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", j_h_user_id='" + j_h_user_id + '\'' +
                    ", j_h_user_name='" + j_h_user_name + '\'' +
                    ", j_h_journey_name='" + j_h_journey_name + '\'' +
                    ", j_h_hid='" + j_h_hid + '\'' +
                    ", j_h_letter_reap='" + j_h_letter_reap + '\'' +
                    ", j_h_challenge_acc='" + j_h_challenge_acc + '\'' +
                    ", j_h_goal_completed='" + j_h_goal_completed + '\'' +
                    ", j_h_action_done='" + j_h_action_done + '\'' +
                    ", j_h_motivation='" + j_h_motivation + '\'' +
                    ", j_h_golden_chllenge='" + j_h_golden_chllenge + '\'' +
                    ", j_golden_status='" + j_golden_status + '\'' +
                    '}';
        }
    }


    public class UpdateJourneyHabitService extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;

        String j_h_id;
        String j_h_user_id;
        String j_h_user_name;
        String j_h_journey_name;
        String j_h_hid;
        String j_h_letter_reap;
        String j_h_challenge_acc;
        String j_h_goal_completed;
        String j_h_action_done;
        String j_h_motivation;
        String j_h_golden_chllenge;
        String j_golden_status;

        public UpdateJourneyHabitService(String j_h_id,
                                         String j_h_user_id,
                                         String j_h_user_name,
                                         String j_h_journey_name,
                                         String j_h_hid,
                                         String j_h_letter_reap,
                                         String j_h_challenge_acc,
                                         String j_h_goal_completed,
                                         String j_h_action_done,
                                         String j_h_motivation,
                                         String j_h_golden_chllenge,
                                         String j_golden_status) {
            this.j_h_id = j_h_id;
            this.j_h_user_id = j_h_user_id;
            this.j_h_user_name = j_h_user_name;
            this.j_h_journey_name = j_h_journey_name;
            this.j_h_hid = j_h_hid;
            this.j_h_letter_reap = j_h_letter_reap;
            this.j_h_challenge_acc = j_h_challenge_acc;
            this.j_h_goal_completed = j_h_goal_completed;
            this.j_h_action_done = j_h_action_done;
            this.j_h_motivation = j_h_motivation;
            this.j_h_golden_chllenge = j_h_golden_chllenge;
            this.j_golden_status = j_golden_status;

            Log.d(TAG, "update reminder desc:-" + this.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = ProgressDialog.show(mContext, "Please wait...", "Adding Habit");
//            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("j_h_id", j_h_id));
            nameValuePairs.add(new BasicNameValuePair("j_h_user_id", PrefData.getStringPref(mContext, PrefData.USER_ID)));
            nameValuePairs.add(new BasicNameValuePair("j_h_user_name", j_h_user_name));
            nameValuePairs.add(new BasicNameValuePair("j_h_journey_name", j_h_journey_name));
            nameValuePairs.add(new BasicNameValuePair("j_h_hid", j_h_hid));
            nameValuePairs.add(new BasicNameValuePair("j_h_letter_reap", j_h_letter_reap));
            nameValuePairs.add(new BasicNameValuePair("j_h_challenge_acc", j_h_challenge_acc));
            nameValuePairs.add(new BasicNameValuePair("j_h_goal_completed", j_h_goal_completed));
            nameValuePairs.add(new BasicNameValuePair("j_h_action_done", j_h_action_done));
            nameValuePairs.add(new BasicNameValuePair("j_h_motivation", j_h_motivation));
            nameValuePairs.add(new BasicNameValuePair("j_h_golden_chllenge", j_h_golden_chllenge));
            nameValuePairs.add(new BasicNameValuePair("j_golden_status", j_golden_status));
            try {
                jResult = WebServices.httpCall(WebServices.UPDATE_JOURNEY_HABIT_URL, nameValuePairs);
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

                } else {
                    //toast.maketext(mContext, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
        }


        @Override
        public String toString() {
            return "UpdateJourneyHabitService{" +
                    "nameValuePairs=" + nameValuePairs +
                    ", jResult='" + jResult + '\'' +
                    ", j_h_id='" + j_h_id + '\'' +
                    ", j_h_user_id='" + j_h_user_id + '\'' +
                    ", j_h_user_name='" + j_h_user_name + '\'' +
                    ", j_h_journey_name='" + j_h_journey_name + '\'' +
                    ", j_h_hid='" + j_h_hid + '\'' +
                    ", j_h_letter_reap='" + j_h_letter_reap + '\'' +
                    ", j_h_challenge_acc='" + j_h_challenge_acc + '\'' +
                    ", j_h_goal_completed='" + j_h_goal_completed + '\'' +
                    ", j_h_action_done='" + j_h_action_done + '\'' +
                    ", j_h_motivation='" + j_h_motivation + '\'' +
                    ", j_h_golden_chllenge='" + j_h_golden_chllenge + '\'' +
                    ", j_golden_status='" + j_golden_status + '\'' +
                    '}';
        }
    }

    final static String TAG = "webservice";

}
