package com.motivator.careplan;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.database.DatabaseHelper;
import com.motivator.database.NewDataBaseHelper;
import com.motivator.database.PrefData;
import com.motivator.database.PutData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.CarePlanPOJO;
import com.motivator.model.CustomHabitPOJO;
import com.motivator.services.WebServiceBase;
import com.motivator.services.WebServicesCallBack;
import com.motivator.services.WebUrls;
import com.motivator.support.FileUtils;
import com.motivator.support.StringUtils;
import com.motivator.wecareyou.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class CarePlanHomeActivity extends AppCompatActivity implements CarePlanFragmentToActivtiy, View.OnClickListener, WebServicesCallBack {
    CarePlanPOJO pojo = new CarePlanPOJO();
    Fragment fragment;
    LinearLayout chat_layout, yes_layout, good_chat_layout, diet_change_layout, user_masala, care_manage_masala, care_anything_diet, user_diet_change, care_diet_added, care_exercise_suggestion, user_exercise,
            care_exercise_added, care_symptoms, user_symptoms, care_symptoms_manage, care_thanks, fragment_layout;
    ScrollView chat_scroll;
    TextView user_masala_tv, user_diet_change_tv, user_exercise_tv, user_symptoms_tv, user_activity_tv, good_chat_layout_tv,
            care_manage_masala_tv, care_diet_added_tv, user_exercise_added_tv;
    ProgressDialog progressDialog;
    TextView cloud1;
    MediaPlayer mplayer;
    Toolbar image_toolbar;
    ImageView voice_button;
    MenuItem switchButton;
    boolean isSoundOn = false;
    private final String TAG = "careplan";
    PutData putData;

    private String[] cell_num = {"#F0F8FF", "#FAEBD7", "#FA8072", "#FFA500", "#FFE4C4", "#BDB76B",
            "#FF1744", "#FF8A80", "#FF5252", "#FF69B4", "#E21C52", "#4B0082",
            "#4da6ff", "#4682B4", "#00cccc", "#1c7269", "#65c6bb", "#8A2BE2",
            "#6BBC8B", "#f2f2f2", "#CCCCCC", "#455A64", "#999999", "#CFCCDC",
            "#000000", "#FFFFFF"};


    SQLiteDatabase _database;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.care_plan_main_layout);
        initViews();

        putData = new PutData(this);

        databaseHelper = DatabaseHelper.getInstance(this);

        setSupportActionBar(image_toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        voice_button.setOnClickListener(this);

        isSoundOn = GeneralUtility.getPreferencesBoolean(CarePlanHomeActivity.this, AppsConstant.AVS_SOUND);
        if (isSoundOn) {
            voice_button.setImageResource(R.drawable.voice);
        } else {
            voice_button.setImageResource(R.drawable.voice_mute);
        }
        ListFiles(new File(FileUtils.CARE_PLAN_FILE_PATH));
    }


    public void ListFiles(File f) {
        File[] files = f.listFiles();
        Log.d(TAG, "length:-" + files.length);
        int val = Pref.getInteger(getApplicationContext(), StringUtils.CAREPLAN, -1);
        Log.d(TAG, "pref val:-" + val);
        if (files.length > 0) {

            if ((val + 1) >= files.length) {
                val = 0;
            } else {
                val = val + 1;
            }
        }
        try {
            Log.d(TAG, "final val:-" + val);
            File soundFile = files[val];
            mplayer = new MediaPlayer();
            mplayer.setDataSource(soundFile.toString());
            mplayer.prepare();
            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                mplayer.start();
            }
            int MAX_VOLUME = 100;
            final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
            mplayer.setVolume(volume, volume);
            Pref.setInteger(getApplicationContext(), StringUtils.CAREPLAN, val);
            Log.d(TAG, "pref mood:-" + Pref.getInteger(getApplicationContext(), StringUtils.CAREPLAN, -1));
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }
    }

    public void initViews() {
        image_toolbar = (Toolbar) findViewById(R.id.image_toolbar);
        chat_layout = (LinearLayout) findViewById(R.id.chat_layout);
        yes_layout = (LinearLayout) findViewById(R.id.yes_layout);
        good_chat_layout = (LinearLayout) findViewById(R.id.good_chat_layout);
        diet_change_layout = (LinearLayout) findViewById(R.id.diet_change_layout);
        user_masala = (LinearLayout) findViewById(R.id.user_masala);
        care_manage_masala = (LinearLayout) findViewById(R.id.care_manage_masala);
        care_anything_diet = (LinearLayout) findViewById(R.id.care_anything_diet);
        user_diet_change = (LinearLayout) findViewById(R.id.user_diet_change);
        care_diet_added = (LinearLayout) findViewById(R.id.care_diet_added);
        care_exercise_suggestion = (LinearLayout) findViewById(R.id.care_exercise_suggestion);
        user_exercise = (LinearLayout) findViewById(R.id.user_exercise);
        care_exercise_added = (LinearLayout) findViewById(R.id.care_exercise_added);
        care_symptoms = (LinearLayout) findViewById(R.id.care_symptoms);
        user_symptoms = (LinearLayout) findViewById(R.id.user_symptoms);

        care_symptoms_manage = (LinearLayout) findViewById(R.id.care_symptoms_manage);
        care_thanks = (LinearLayout) findViewById(R.id.care_thanks);
        fragment_layout = (LinearLayout) findViewById(R.id.fragment_layout);

        chat_scroll = (ScrollView) findViewById(R.id.chat_scroll);

        user_masala_tv = (TextView) findViewById(R.id.user_masala_tv);

        user_diet_change_tv = (TextView) findViewById(R.id.user_diet_change_tv);
        user_exercise_tv = (TextView) findViewById(R.id.user_exercise_tv);
        user_symptoms_tv = (TextView) findViewById(R.id.user_symptoms_tv);
        user_activity_tv = (TextView) findViewById(R.id.user_activity_tv);
        good_chat_layout_tv = (TextView) findViewById(R.id.good_chat_layout_tv);
        care_manage_masala_tv = (TextView) findViewById(R.id.care_manage_masala_tv);
        care_diet_added_tv = (TextView) findViewById(R.id.care_diet_added_tv);
        user_exercise_added_tv = (TextView) findViewById(R.id.user_exercise_added_tv);
        cloud1 = (TextView) findViewById(R.id.cloud1);

        voice_button = (ImageView) findViewById(R.id.voice_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name = PrefData.getStringPref(getApplicationContext(), PrefData.NAME_KEY);
//		cloud1.setText("Hi "+name+"\\nI can help turn your\\ncareplan into easy habits.\\nDid you meet your doctor recently?");
        cloud1.setText("Hi " + name + " \n I can help turn your\ncareplan into easy habits.\nDid you meet your doctor recently?");
    }

    public void replacingFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.output, fragment);
        transaction.commit();
    }


    @Override
    public void FragmentToActivity(String to, String msg) {
        Log.d("sunil", msg);
        // TODO Auto-generated method stub
        switch (to) {
            case "doctor":

                yes_layout.setVisibility(View.VISIBLE);
                good_chat_layout.setVisibility(View.VISIBLE);
//			chat_scroll.scrollTo(0, chat_scroll.getBottom());
                ScrollDown();
                pojo.setDoctor_visit(msg);
                if (msg.equals("yes")) {
                    chat_layout.setVisibility(View.VISIBLE);


                    diet_change_layout.setVisibility(View.VISIBLE);
                    user_activity_tv.setText("yes");
                    good_chat_layout_tv.setText("Good! Being regular with\ndoctor visits is the first\nstep forward building a\nhealthy family.");
                    fragment = new CarePlanMasalFragment();
                    replacingFragment(fragment);
                } else {
                    user_activity_tv.setText("no");
                    good_chat_layout_tv.setText("Oh OK. I can help turn your careplan into habits.The next time you visit your doctor let me know by selecting careplan in the menu.");
                    new CountDownTimer(1000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            finish();
                        }

                    }.start();
                }


                break;
            case "masala":

                care_manage_masala.setVisibility(View.VISIBLE);
                care_anything_diet.setVisibility(View.VISIBLE);
                if (msg.equals("skip")) {
                    care_manage_masala_tv.setText("Let's continue");
                } else {
                    user_masala.setVisibility(View.VISIBLE);
                    String[] s = msg.split(":");
                    String salt = s[0];
                    String sugar = s[1];
                    String oil = s[2];
                    pojo.setMasala(msg);
                    user_masala_tv.setText(salt + "\n" + sugar + "\n" + oil);
                }

                ScrollDown();

                fragment = new CarePlanDietScreen();
                replacingFragment(fragment);
                break;

            case "diet":

                care_diet_added.setVisibility(View.VISIBLE);
                care_exercise_suggestion.setVisibility(View.VISIBLE);
                if (msg.equals("skip")) {
                    care_diet_added_tv.setText("And...");
                } else {
                    user_diet_change.setVisibility(View.VISIBLE);
                    if (msg.contains(":")) {
                        String[] str = msg.split(":");
                        String msg1 = "Added ";
                        for (String s : str) {
                            msg1 += s + "\n";
                        }
                        user_diet_change_tv.setText(msg1);
                    } else {
                        user_diet_change_tv.setText("Added " + msg);
                    }

                    String str[]=msg.split(":");
                    String diet="";
                    for(int i=0;i<str.length;i++){
                        if((i+1)==str.length){
                            diet+=str[i];
                        }
                        else{
                            diet+=str[i]+",";
                        }
                    }
                    Log.d(TAG,"diet:-"+diet);
                    if(diet.length()>0) {
                        Random r = new Random();
                        final int i1 = r.nextInt(25 - 0) + 0;
                        addDietCustomHabit("Diet", diet, "", cell_num[i1],"Morning Routine");
                    }
                }

                ScrollDown();
                pojo.setDiet(msg);
                fragment = new CarePlanExerciseFragment();
                replacingFragment(fragment);
                break;

            case "exercise":

                care_exercise_added.setVisibility(View.VISIBLE);
                care_symptoms.setVisibility(View.VISIBLE);
                addHabitfromOutput(msg);
                if (msg.equals("skip")) {
                    user_exercise_added_tv.setText("It feels nice to stay active isn't it? I was also thinking...");
                } else {
                    user_exercise.setVisibility(View.VISIBLE);
                    if (msg.contains(":")) {
                        String[] str = msg.split(":");
                        String msg1 = "";
                        for (int i = 0; i < str.length; i++) {
                            if (str.length == (i + 1)) {
                                msg1 += str[i];
                            } else {
                                msg1 += str[i] + ",\n";
                            }
                        }
                        user_exercise_tv.setText(msg1);
                    } else {
                        user_exercise_tv.setText(msg);
                    }
                }

                ScrollDown();
                pojo.setExercise(msg);
                fragment = new CarePlanSymptomsFragment();
                replacingFragment(fragment);
                break;
            case "symptoms":
                user_symptoms.setVisibility(View.VISIBLE);
                care_symptoms_manage.setVisibility(View.VISIBLE);
                care_thanks.setVisibility(View.VISIBLE);
                fragment_layout.setVisibility(View.GONE);

                try {
                    String str[] = msg.split(":");
                    String msg1 = "";
                    for (int i = 0; i < str.length; i++) {
                        if ((i + 1) == str.length) {
                            msg1 += str[i] + ".";
                        } else {
                            msg1 += str[i] + ",";
                        }
                    }
                    user_symptoms_tv.setText(msg1);
                } catch (Exception e) {
                    user_symptoms_tv.setText(msg);
                }
                String str[]=msg.split(":");
                String symp="";
                for(int i=0;i<str.length;i++){
                    if((i+1)==str.length){
                        symp+=str[i]+".";
                    }
                    else{
                        symp+=str[i]+",";
                    }
                }
                Log.d(TAG,"symptoms:-"+symp);
                if(symp.length()>0){
                        Random r = new Random();
                        final int i1 = r.nextInt(25 - 0) + 0;
                    addDietCustomHabit("Symptoms",symp,"",cell_num[i1],"Afternoon Routine");
                }
                ScrollDown();
                LayoutParams params = new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

                params.weight = 10.5f;

                chat_scroll.setLayoutParams(params);
                pojo.setSymptoms(msg);
                Log.d("sunil", "symptome");

                int finishTime = 3; //10 secs
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        callService();
                    }
                }, finishTime * 1000);
                break;
            default:
                break;
        }
    }

    public void addHabitfromOutput(String output) {
        try {
            String str[] = output.split(":");
            for (String s : str) {
                CheckOutput(s);
                Log.d(TAG, s);
            }
        } catch (Exception e) {

        }
    }

    public void CheckOutput(String s) {
        Log.d(TAG, "string:-" + s);
        if (s.contains("Walk")) {
            final int time = Integer.parseInt(s.replaceAll("[\\D]", ""));
            Log.d(TAG, "output:-walk:-" + time);

            Random r = new Random();
            final int i1 = r.nextInt(25 - 0) + 0;
            addCustomHabit("Walk", time + "", "", cell_num[i1]+ "");
        } else {
            if (s.contains("Cardio")) {
                final int time = Integer.parseInt(s.replaceAll("[\\D]", ""));
                Log.d(TAG, "output:-Cardio:-" + time);

                Random r = new Random();
                final int i1 = r.nextInt(25 - 0) + 0;
                addCustomHabit("Cardio", time + "", "", cell_num[i1] + "");
            } else {
                if (s.contains("Yoga")) {
                    final int time = Integer.parseInt(s.replaceAll("[\\D]", ""));
                    Log.d(TAG, "output:-Yoga:-" + time);

                    Random r = new Random();
                    final int i1 = r.nextInt(25 - 0) + 0;
                    addCustomHabit("Yoga", time + "", "", cell_num[i1] + "");
                }
            }
        }
    }


    public void ScrollDown() {
        chat_scroll.post(new Runnable() {
            @Override
            public void run() {
                chat_scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void callService() {
        Log.d(TAG, pojo.toString());
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mplayer != null) {
            if (mplayer.isPlaying()) {
                mplayer.pause();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mplayer != null) {
            if (mplayer.isPlaying()) {
                mplayer.pause();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.voice_button:
                isSoundOn = GeneralUtility.getPreferencesBoolean(CarePlanHomeActivity.this, AppsConstant.AVS_SOUND);
                if (isSoundOn) {
                    isSoundOn = false;
                    voice_button.setImageResource(R.drawable.voice_mute);
                    GeneralUtility.setPreferencesBoolean(CarePlanHomeActivity.this, AppsConstant.AVS_SOUND, false);
                    try {
                        mplayer.pause();
                    } catch (Exception e) {

                    }
                } else {
                    isSoundOn = true;
                    voice_button.setImageResource(R.drawable.voice);
                    GeneralUtility.setPreferencesBoolean(CarePlanHomeActivity.this, AppsConstant.AVS_SOUND, true);
                    try {
                        mplayer.start();
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }

    public void addCustomHabit(String habit_name, String habit_time, String file_path, String iconColor) {
        Log.d(TAG, "habit_name:-" + habit_name + "  habit_time:-" + habit_time + " file_path:-" + file_path
                + " iconcolor:-" + iconColor);
        _database = databaseHelper.openDataBase();
        String table_query = "Select * from " + TableAttributes.TABLE_HABIT + " where "
                + TableAttributes.HABIT + " = '" + habit_name + "' ";
        Cursor cur_table_query = _database.rawQuery(table_query, null);
        if (cur_table_query.getCount() > 0) {
            if (cur_table_query.moveToFirst()) {
                String h_id = cur_table_query.getString(0);
                Log.d(TAG, "exist h_id:-" + h_id);


                String update_habit_sql = "update " + TableAttributes.TABLE_HABIT + " set " + TableAttributes.HABIT_TIME + " = " + habit_time +
                        " where " + TableAttributes.HABIT + " = '" + habit_name + "'";
                _database.execSQL(update_habit_sql);
//				UpdateCustomHabit();
                _database.close();
                _database = databaseHelper.openDataBase();
                cur_table_query.close();

                String str1 = "Select * from " + TableAttributes.TABLE_HABIT + " where "
                        + TableAttributes.HABIT + " = '" + habit_name + "' ";
                _database = databaseHelper.openDataBase();
                Cursor cur_str1 = _database.rawQuery(str1, null);
                if (cur_str1.moveToFirst()) {
                    String cus_h_id = cur_str1.getString(0);
                    String cus_habit = cur_str1.getString(1);
                    String cus_description = cur_str1.getString(2);
                    String cus_benefits = cur_str1.getString(3);
                    String cus_habit_time = cur_str1.getString(4);
                    String cus_reminder_desc1 = cur_str1.getString(5);
                    String cus_reminder_desc2 = cur_str1.getString(6);
                    String cus_reminder_desc3 = cur_str1.getString(7);
                    String cus_reminder_desc4 = cur_str1.getString(8);
                    String cus_reminder_desc5 = cur_str1.getString(9);
                    String cus_reminder_desc6 = cur_str1.getString(10);

                    UpdateData updateData=new UpdateData(this);
                    updateData.updateUserHabitInfo(PrefData.getStringPref(getApplicationContext(),PrefData.NAME_KEY),
                            Integer.parseInt(h_id), TableAttributes.USER_HABIT_TIME, habit_time);

                    NewDataBaseHelper helper = new NewDataBaseHelper(this);
                    CustomHabitPOJO pojo = helper.getCustomHabitData(cus_h_id);

                    String cus_habit_habit_id = pojo.getCustom_habit_id();
                    String cus_user_id = pojo.getCustom_habit_user_id();

                    UpdateCustomHabit(cus_user_id, cus_h_id, cus_habit, cus_description,
                            cus_benefits, habit_time, cus_reminder_desc1, cus_reminder_desc2,
                            cus_reminder_desc3, cus_reminder_desc4, cus_reminder_desc5, cus_reminder_desc6,
                            cus_habit_habit_id);
                }
                cur_str1.close();
                _database.close();
            } else {
                cur_table_query.close();
                _database.close();
            }
        } else {

            _database.close();
            long row = putData.addCustomHabit(habit_name, habit_time, file_path, iconColor);
            Log.d(TAG, "row:-" + row);
            if (row > 0) {
                String str1 = "Select * from " + TableAttributes.TABLE_HABIT + " where "
                        + TableAttributes.HABIT + " = '" + habit_name + "' ";
                _database = databaseHelper.openDataBase();
                Cursor cur_str1 = _database.rawQuery(str1, null);
                Log.d(TAG, "count:-" + cur_str1.getCount());
                if (cur_str1.moveToFirst()) {
                    String h_id = cur_str1.getString(0);
                    Log.d(TAG, "h_id1111:-" + h_id);
                    long val = putData.addUserHabit(PrefData.getStringPref(getApplicationContext(), PrefData.NAME_KEY),
                            Integer.parseInt(h_id), "Morning Routine", habit_time);
                    if (val > 0) {
                        Toast.makeText(getApplicationContext(), "habit added sccessfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    cur_str1.close();
                }
                _database.close();
            }
        }
    }

    public void UpdateCustomHabit(String custom_habit_user_id,
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
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
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
        new WebServiceBase(nameValuePairs, this, "api1").execute(WebUrls.UPDATE_CUSTOM_HABIT);
    }


    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];

        switch (apicall) {
            case "api1":
                break;
            case "api2":
                break;
        }
    }



    public void addDietCustomHabit(String habit_name, String habit_description, String file_path, String iconColor,String ritual_type) {
        Log.d(TAG, "habit_name:-" + habit_name + "  habit_description:-" + habit_description+ " file_path:-" + file_path
                + " iconcolor:-" + iconColor);
        _database = databaseHelper.openDataBase();
        String table_query = "Select * from " + TableAttributes.TABLE_HABIT + " where "
                + TableAttributes.HABIT + " = '" + habit_name + "' ";
        Cursor cur_table_query = _database.rawQuery(table_query, null);
        if (cur_table_query.getCount() > 0) {
            if (cur_table_query.moveToFirst()) {
                String h_id = cur_table_query.getString(0);
                Log.d(TAG, "exist h_id:-" + h_id);


                String update_habit_sql = "update " + TableAttributes.TABLE_HABIT + " set " + TableAttributes.DESCRIPTION + " = '" + habit_description +
                        "' where " + TableAttributes.HABIT + " = '" + habit_name + "'";
                _database.execSQL(update_habit_sql);
//				UpdateCustomHabit();
                _database.close();
                cur_table_query.close();
            } else {
                cur_table_query.close();
                _database.close();
            }
        } else {

            _database.close();
            long row = putData.addCustomHabit(habit_name, "5", file_path, iconColor);
            Log.d(TAG, "row:-" + row);
            if (row > 0) {
                String str1 = "Select * from " + TableAttributes.TABLE_HABIT + " where "
                        + TableAttributes.HABIT + " = '" + habit_name + "' ";
                _database = databaseHelper.openDataBase();
                Cursor cur_str1 = _database.rawQuery(str1, null);
                Log.d(TAG, "count:-" + cur_str1.getCount());
                if (cur_str1.moveToFirst()) {
                    String h_id = cur_str1.getString(0);
                    Log.d(TAG, "h_id1111:-" + h_id);
                    long val = putData.addUserHabit(PrefData.getStringPref(getApplicationContext(), PrefData.NAME_KEY),
                            Integer.parseInt(h_id), ritual_type, "5");
                    if (val > 0) {
                        Toast.makeText(getApplicationContext(), "habit added sccessfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    cur_str1.close();
                }
                _database.close();
            }
        }
    }

}
