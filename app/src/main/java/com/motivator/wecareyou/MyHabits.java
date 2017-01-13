package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.database.GetData;
import com.motivator.database.PutData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.HabitModel;
import com.motivator.model.ReminderDescModel;
import com.motivator.model.UserRitualModel;
import com.motivator.support.DynamicListViewHabits;
import com.motivator.support.FileUtils;
import com.motivator.support.StringUtils;
import com.motivator.wecareyou.fragment.HomeFragment;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class MyHabits extends Activity implements OnClickListener, TimePickerDialog.OnTimeSetListener {

    Date dateObj;
    String ritualTime;
    String selectedRitual;
    boolean isRitualAdded;
    //isResume;
    RelativeLayout rllInfo;
    String userName;
    Button btnAddHabit;
    TextView tvClock1, tvClock2;
    TextView tvHabitCount, tvHabitDay, tvHabitInfo, tvAddHabit;
    DynamicListViewHabits lvAddedHabit;
    LinearLayout llAddHabit1;
    TextView tvNoHabit, tvRecommendedHabit;
    Button btnAddHabit1;
    LinearLayout llTopImg;

    //	MediaPlayer player;
//	AssetFileDescriptor afd;
    public static int totalTime = 0;
    PutData putData;
    GetData getData;
    UpdateData updateData;
    UserRitualModel userInfo = new UserRitualModel();
    ArrayList<HabitModel> userHabit = new ArrayList<HabitModel>();
    boolean isReminderCall;
    String speakingString = "";
    String myhabitfirstspeak = "";
    MediaPlayer mPlayer;


    //variables for logic for voice
    boolean morning_flag;
    boolean afternoon_flag;
    boolean evening_flag;
    StringBuilder counter_string = new StringBuilder();

    public static boolean habit_flag = false;
//    static int[] tap_ids={R.raw.click_tap_1,R.raw.click_tap_2,R.raw.click_tap_3,R.raw.click_tap_4,R.raw.click_tap_5};

    List<Integer> lst_int=new ArrayList<>();
    MediaPlayer mPlayer1;
    private final String TAG="myhabits";
    MenuItem switchbtn;
    boolean isSoundOn;
    boolean first_time=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        putData = new PutData(MyHabits.this);
        getData = new GetData(MyHabits.this);
        updateData = new UpdateData(MyHabits.this);

//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.setStatusBarColor(getResources().getColor(R.color.white));

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        //getDAta from shared prefrence
        userName = GeneralUtility.getPreferences(MyHabits.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        isReminderCall = getIntent().getExtras().getBoolean("is_reminder_call");

        userInfo = new UserRitualModel();
        userInfo = getData.getRitualsDetails(userName, selectedRitual);
        Log.d("sunil", "selected Ritual:-" + selectedRitual);


        setContentView(R.layout.my_habits);


        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sky_blue)/*Color.parseColor("#330000ff")*/));
        // actionBar.setTitle(userInfo.getRitualDisplayName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        SpannableString s = new SpannableString(userInfo.getRitualDisplayName().toLowerCase());
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        speakingString = s.toString().replace("routine", "");
        speakingString = "this is where you add stuffs to do in the " + speakingString;
        actionBar.setTitle(s);

        intializeUIView();
        SharedPreferences sp = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        myhabitfirstspeak = sp.getString("myhabitfirstspeak", "");

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            first_time=bundle.getBoolean("first_time");
        }

//        switch (selectedRitual){
//            case "Morning Routine":
//                playMusicFile(R.raw.morning_ritual_1);
//                break;
//            case "Afternoon Routine":
//                playMusicFile(R.raw.afternoon_ritual_1);
//                break;
//            case "Evening Routine":
//                playMusicFile(R.raw.evening_ritual_1);
//                break;
//            default:
//                break;
//        }


        if (myhabitfirstspeak.equals("")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("myhabitfirstspeak", "done");
            editor.commit();
//        	tts = new TextToSpeech(getApplicationContext(), new OnInitListener() {
//
//    			@Override
//    			public void onInit(int status) {
//    				// TODO Auto-generated method stub
//    				convertTextToSpeech(status, "Everything you do is divided into rituals, or routines'... They are divided into the morning, afternoon, and evening time.It's my job to help you on your journey. I will help you organize your tasks, also known as rituals, into the three time blocks. Let's take a look.");
//    			}
//    		});
        }


        //Check is user coming first time in the app (by cheking isRitualAdded)
        //isRitualAdded = getData.isValueAdded(userName, TableAttributes.IS_RITUAL_ADDED);
        isRitualAdded = GeneralUtility.getPreferencesBoolean(MyHabits.this, AppsConstant.IS_RITUAL_ADDED);

        // Get and Set User Journey Timing
        // getJourneyTime();

        if (selectedRitual.equalsIgnoreCase(AppsConstant.MORNING_RITUAL)) {
            tvHabitInfo.setText("This is your Morning Ritual. What's one thing you can do in the morning to energize yourself?");
        } else if (selectedRitual.equalsIgnoreCase(AppsConstant.LUNCH_RITUAL)) {
            tvHabitInfo.setText("This is your Afternoon Ritual. What's one thing you can do to lose weight?");
        } else if (selectedRitual.equalsIgnoreCase(AppsConstant.EVENING_RITUAL)) {
            tvHabitInfo.setText("This is your Evening Ritual. What's one thing you can do to have a great night's sleep?");
        }

        setViewsVisibility();

        String times = sp.getString("habit_times", "");
        if (times.equals("")) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("habit_times", "val");
            edit.commit();
        } else {
//        	if(userHabit.size()>0){
////        		if(MainActivity.habit_time==0){
////        			MainActivity.habit_time=1;
//        			musicMethod();
////        		}
//        	}
            Log.d("msg", "msg1");
            playVoice();
        }
        ListFiles(new File(FileUtils.RITUALS_FILE_PATH));
    }

    public void ListFiles(File f){
        File[] files=f.listFiles();
        Log.d(TAG,"length:-"+files.length);
        int val= Pref.getInteger(getApplicationContext(), StringUtils.RITUALS,-1);
        Log.d(TAG,"pref val:-"+val);
        if(files.length>0){

            if((val+1)>=files.length){
                val=0;
            }
            else{
                val=val+1;
            }
        }
        try{
            Log.d(TAG,"final val:-"+val);
            File soundFile=files[val];
            mPlayer1 = new MediaPlayer();
            mPlayer1.setDataSource(soundFile.toString());
            mPlayer1.prepare();
            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                mPlayer1.start();
            }
            int MAX_VOLUME = 100;
            final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
            mPlayer1.setVolume(volume, volume);
            Pref.setInteger(getApplicationContext(),StringUtils.RITUALS,val);
            Log.d(TAG,"pref mood:-"+Pref.getInteger(getApplicationContext(),StringUtils.RITUALS,-1));
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
        }
    }


    public void playMusicFile(int id){
        mPlayer1= MediaPlayer.create(MyHabits.this, id);
        mPlayer1.start();
        int MAX_VOLUME = 100;
        final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
        mPlayer1.setVolume(volume, volume);

    }

    public boolean getValuePreference(String key) {
        SharedPreferences sp = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        return sp.getBoolean(key, true);
    }

    public void playVoice() {

        if (getValuePreference("ritual_page_first")) {
            String ritual_page="Here you can see the list and the sequence of habits for that ritual. You can come to this page to inform, preview habits and keep track of your habits everyday. While previewing a habit or doing a ritual you can always get back to this screen by tapping back button in the top of ritual alarm. But first, let's go ahead and add some habits by tapping at bottom of this screen. We have an exciting list for you.";
            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if(MyApplication.tts_initialized){
                    MyApplication.tts.speak(ritual_page, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
            storePref("ritual_page_first", false);
            HomeFragment.home_flag = false;
        } else {
            if (getValuePreference("after_habit_screen")) {
                String after_add_habit="Great. Now let's go ahead and set an alarm to make it easy cheesy for you to do the ritual. Tap the alarm.";
                if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                    if(MyApplication.tts_initialized){
                        MyApplication.tts.speak(after_add_habit, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                storePref("after_habit_screen", false);
            } else {
//				if(getValuePreference("after_add_alarm")){
//					MediaPlayer mpPlayer = MediaPlayer.create(MyHabits.this, R.raw.after_adding_alarm);
//					mpPlayer.start();
//					storePref("after_add_alarm", false);
//				}
//				else{
//					
//				}

                SharedPreferences sp1 = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);

                Log.d("sunil", "userhabit size:-" + userHabit.size());
                if (userHabit.size() == 0 || userHabit.size() == 1) {
                    if (!sp1.getBoolean("ask_user_first", false)) {
                        Log.d("sunil", "ask if");
//		    			edit.putBoolean("ask_user_first", true);
//		    			edit.commit();
                        storePref("ask_user_first", true);
                        //play add habit first time.
                        if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                            if(MyApplication.tts_initialized){
                                MyApplication.tts.speak("Add Habits by clicking Add Habit below.", TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }
                    } else {
                        Log.d("sunil", "ask else");
                    }
                } else {
                    if (!sp1.getBoolean("reminder_set", false)) {
                        if (!sp1.getBoolean("reminder_ask", false)) {
                            Log.d("sunil", "reminder if");
//		        			edit.putBoolean("reminder_ask", true);
//		        			edit.commit();
                            storePref("reminder_ask", true);
                            //reminder play
                            String reminder="you can set the reminder at the top.";
                            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                                if(MyApplication.tts_initialized){
                                    MyApplication.tts.speak(reminder, TextToSpeech.QUEUE_FLUSH, null);
                                }
                            }

                        } else {
                            Log.d("sunil", "reminder else");
                        }
                    } else {
                        if ((userHabit.size()) >= 2) {
                            if (!sp1.getBoolean("reordering_ask", false)) {
                                Log.d("sunil", "reordering_ask if");
//		    					edit.putBoolean("reordering_ask", true);
//		            			edit.commit();
                                storePref("reordering_ask", true);
                                //play reordering the bullet
                                String change_list="you can change the order of the list by simply dragging and dropping.";
                                if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                                    if(MyApplication.tts_initialized){
                                        MyApplication.tts.speak(change_list, TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                }
                            } else {
                                //play remaining bullets
                                Log.d("sunil", "reordering_ask else");
                                musicMethod();
                            }
                        }
                    }
                }
            }
        }
    }

    public void storePref(String key, boolean value) {
        SharedPreferences sp = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void musicMethod() {
        SharedPreferences sp = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        String stored_date = sp.getString("stored_date", "");
        SharedPreferences.Editor editor = sp.edit();
        Calendar c = Calendar.getInstance();
//		System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String current_Date = df.format(c.getTime());


        if (!(stored_date.equals(current_Date))) {
//			String counter=sp.getString("counter_string_habit", "");
            editor.putString("counter_string_habit", "");
            editor.putString("morning_ritual_done", "");
            editor.putString("afternoon_ritual_done", "");
            editor.putString("evening_ritual_done", "");
            editor.commit();

            if (counter_string.length() > 0) {
                counter_string.delete(0, counter_string.length());
            }

            Log.d("sunil", counter_string.toString());
            switch (selectedRitual) {
                case "Morning Routine":
                    if (!morning_flag) {
                        stored_date = current_Date;
                        editor.putString("stored_date", stored_date);
                        editor.putString("morning_ritual_done", "true");
                        editor.commit();
                        //generateRand
                        //playstring
                        musicPlayingMethod();

                    }
                    break;
                case "Afternoon Routine":
                    if (!afternoon_flag) {
                        stored_date = current_Date;
                        editor.putString("stored_date", stored_date);
                        editor.putString("afternoon_ritual_done", "true");
                        editor.commit();
                        musicPlayingMethod();

                    }
                    break;
                case "Evening Routine":
                    if (!evening_flag) {
                        stored_date = current_Date;
                        editor.putString("stored_date", stored_date);
                        editor.putString("evening_ritual_done", "true");
                        editor.commit();
                        musicPlayingMethod();
                    }
                    break;

                default:
                    break;
            }
        } else {
            switch (selectedRitual) {
                case "Morning Routine":
                    if (!morning_flag) {
                        String morning_ritual_done = sp.getString("morning_ritual_done", "");
                        if (!morning_ritual_done.equals("true")) {
                            //generateRand
                            //playstring
                            musicPlayingMethod();
                            editor.putString("morning_ritual_done", "true");
                            editor.commit();
                        }
                    }
                    break;
                case "Afternoon Routine":
                    if (!afternoon_flag) {

                        String afternoon_ritual_done = sp.getString("afternoon_ritual_done", "");
                        if (!afternoon_ritual_done.equals("true")) {
                            //generateRand
                            //playstring
                            musicPlayingMethod();
                            editor.putString("afternoon_ritual_done", "true");
                            editor.commit();
                        }

                    }
                    break;
                case "Evening Routine":
                    if (!evening_flag) {

                        String evening_ritual_done = sp.getString("evening_ritual_done", "");
                        if (!evening_ritual_done.equals("true")) {
                            //generateRand
                            //playstring
                            musicPlayingMethod();
                            editor.putString("evening_ritual_done", "true");
                            editor.commit();
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void musicPlayingMethod() {
//        String[] list_strings= getResources().getStringArray(R.array.reminder);
        String[] reminder_files={"I know you are busy. What time is good for you on this one?",
                "What would you like me to remind you about this week?",
                "Let's keep our eyes on the prize... When are we doing this one?",
                "Let me give you a hand with these.",
                "Are these times still good for you?"};
        playReminderFiles(reminder_files);
    }



    public void playReminderFiles(String[] list_files){
        int val= Pref.getInteger(getApplicationContext(),StringUtils.REMINDER_MY_FILES,-1);
        Log.d(TAG,"pref for tis:-"+val);
        if(list_files.length>0){
            if((val+1)>=list_files.length){
                val=0;
            }
            else{
                val=val+1;
            }
        }
        try{
            Log.d(TAG,"final val:-"+val);
            Log.d(TAG,"reminder:-"+list_files[val]);
            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if(MyApplication.tts_initialized){
                    MyApplication.tts.speak(list_files[val], TextToSpeech.QUEUE_FLUSH, null);
                }
            }
            Pref.setInteger(getApplicationContext(),StringUtils.REMINDER_MY_FILES,val);
            Log.d(TAG,"pref mood:-"+Pref.getInteger(getApplicationContext(),StringUtils.REMINDER_MY_FILES,-1));
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
        }
    }

    private void intializeUIView() {
        llTopImg = (LinearLayout) findViewById(R.id.ll_top);
        tvClock1 = (TextView) findViewById(R.id.tv_clock1);
        tvClock2 = (TextView) findViewById(R.id.tv_clock2);

        tvHabitCount = (TextView) findViewById(R.id.tv_habit_count);
        tvHabitDay = (TextView) findViewById(R.id.tv_habit_day);
        tvHabitInfo = (TextView) findViewById(R.id.tv_habit_info);
        lvAddedHabit = (DynamicListViewHabits) findViewById(R.id.lv_added_habit);
        tvAddHabit = (TextView) findViewById(R.id.tv_add_habit);
        btnAddHabit = (Button) findViewById(R.id.btn_add_habit);
        llAddHabit1 = (LinearLayout) findViewById(R.id.ll_add_first_habit);
        tvNoHabit = (TextView) findViewById(R.id.tv_no_habit);
        tvRecommendedHabit = (TextView) findViewById(R.id.tv_recommended_habit);
        btnAddHabit1 = (Button) findViewById(R.id.btn_add_habit1);

        tvHabitCount.setTypeface(GeneralUtility.setTypeFace(MyHabits.this));
        tvHabitDay.setTypeface(GeneralUtility.setTypeFace(MyHabits.this));
        tvHabitInfo.setTypeface(GeneralUtility.setTypeFace(MyHabits.this));
        tvClock1.setTypeface(GeneralUtility.setTypeFace(MyHabits.this));
        tvClock2.setTypeface(GeneralUtility.setTypeFace(MyHabits.this));
        tvNoHabit.setTypeface(GeneralUtility.setTypeFace(MyHabits.this));
        tvRecommendedHabit.setTypeface(GeneralUtility.setTypeFace(MyHabits.this));
        rllInfo = (RelativeLayout) findViewById(R.id.rll_info);


        tvAddHabit.setTypeface(GeneralUtility.setTypeFace(MyHabits.this));
        tvAddHabit.setOnClickListener(this);
        btnAddHabit.setOnClickListener(this);
        btnAddHabit1.setOnClickListener(this);
        tvClock1.setOnClickListener(this);

    }

    private void setViewsVisibility() {
        totalTime = 0;
        // Get User Selected Habits from Databse
        userHabit = getData.getUserHabits(userName, selectedRitual);
        userInfo = new UserRitualModel();
        userInfo = getData.getRitualsDetails(userName, selectedRitual);
        //Set Visibility of views
        if (isRitualAdded) {
            rllInfo.setVisibility(View.GONE);
            if (userHabit == null || userHabit.size() <= 0) {
                tvClock2.setText("No Habits");
                lvAddedHabit.setVisibility(View.GONE);
                llAddHabit1.setVisibility(View.VISIBLE);
                tvAddHabit.setVisibility(View.GONE);
            } else {
                if (totalTime > 60) {
                    int hours = totalTime / 60;
                    int minutes = totalTime % 60;
                    tvClock2.setText(hours + "H " + minutes + " Min");
                } else
                    tvClock2.setText(totalTime + " min");
                lvAddedHabit.setVisibility(View.VISIBLE);
                llAddHabit1.setVisibility(View.GONE);
                tvAddHabit.setVisibility(View.VISIBLE);
                lvAddedHabit.setArrayList(userHabit);
                lvAddedHabit.setAdapter(new HabitAdapter());
                Log.d("sunil", userHabit.size() + "");
//            	SharedPreferences sp=getSharedPreferences("", mode)
                if (selectedRitual.toLowerCase().contains("morn")) {
                    Log.d("sunil", userHabit.size() + "");
                    if (userHabit.size() > 1) {
                        Log.d("msg", "msg2");
                        playVoice();
                    }
                } else {
                    Log.d("sunil", userHabit.size() + "");
                    if (userHabit.size() > 0) {
                        Log.d("msg", "msg3");
                        playVoice();
                    }
                }
            }
        } else {
            if (!GeneralUtility.getPreferencesBoolean(getApplicationContext(), "login")) {
                tvClock2.setText("No Habits");
                rllInfo.setVisibility(View.VISIBLE);
                rllInfo.setAlpha(0.5f);
                btnAddHabit.setAlpha(1.0f);
                tvHabitInfo.setAlpha(1.0f);

                llAddHabit1.setVisibility(View.GONE);
            }
        }

        tvHabitCount.setText(userHabit.size() + " Habits");

        dateObj = DateUtility.getDateObject(userInfo.getRitualTime(), "hh:mm a");
        if (userInfo.getRitualRemID() == 0)
            tvClock1.setText("No Alarm");
        else
            tvClock1.setText(userInfo.getRitualTime());
        llTopImg.setBackgroundResource(AppsConstant.getRitualTopImg(userInfo.getRitualImg()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_my_habit, menu);
        MenuItem switchButton = menu.findItem(R.id.action);
        switchButton.setIcon(R.drawable.setting);

        switchbtn = menu.findItem(R.id.action1);

        isSoundOn = GeneralUtility.getPreferencesBoolean(MyHabits.this, AppsConstant.AVS_SOUND);
        if(isSoundOn)
        {
            switchbtn.setIcon(R.drawable.voice);
        }
        else
        {
            switchbtn.setIcon(R.drawable.voice_mute);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action:
                Intent i = new Intent(MyHabits.this, RitualSetting.class);
                i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
                i.putExtra("time", userInfo.getRitualTime());
                startActivity(i);
                return true;
            case R.id.action1:
                if (isSoundOn) {
                    isSoundOn = false;
                    switchbtn.setIcon(R.drawable.voice_mute);

                    if (mPlayer1 != null)
                        mPlayer1.pause();
                    if(mPlayer!=null){
                        mPlayer.pause();
                    }
                } else {
                    isSoundOn = true;
                    switchbtn.setIcon(R.drawable.voice);
                    if (mPlayer1 != null) {
                        mPlayer1.start();
                    }
                    if(mPlayer!=null){
                        mPlayer.start();
                    }
                }
        }

        return super.onOptionsItemSelected(item);
    }

    public class HabitAdapter extends BaseAdapter {
        final int INVALID_ID = -1;
        HashMap<HabitModel, Integer> mIdMap = new HashMap<HabitModel, Integer>();

        public HabitAdapter() {
            //super(context, textViewResourceId, objects);
            for (int i = 0; i < userHabit.size(); ++i) {
                mIdMap.put(userHabit.get(i), i);
            }
        }

        @Override
        public int getCount() {
            return userHabit.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (position < 0 || position >= mIdMap.size()) {
                return INVALID_ID;
            }
            HabitModel habit = userHabit.get(position);
            return mIdMap.get(habit);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            convertView = inflater.inflate(R.layout.user_habit_list_item, null);

            ImageView imvHabitIcon = (ImageView) convertView.findViewById(R.id.imv_user_habit_icon);
            TextView tvHabit = (TextView) convertView.findViewById(R.id.tv_user_habit);
            final ImageView checkbox = (ImageView) convertView.findViewById(R.id.imv_habit_remove);

            tvHabit.setTypeface(GeneralUtility.setTypeFace(MyHabits.this));
            tvHabit.setText(userHabit.get(position).getHabit());

            if (userHabit.get(position).getHabitDescription().equalsIgnoreCase(TableAttributes.custom_habit)) {
                if (userHabit.get(position).getReminderDesc2() != null && userHabit.get(position).getReminderDesc2().length() > 0) {
                    GradientDrawable bgShape = (GradientDrawable) imvHabitIcon.getBackground();
                    bgShape.setColor(Color.parseColor(userHabit.get(position).getReminderDesc2()));
                } else {
                    imvHabitIcon.setBackgroundResource(R.drawable.habit_custom_icon);
                }
            } else
                imvHabitIcon.setBackgroundResource(AppsConstant.getHabitIcon(userHabit.get(position).getHabitId()));

            String lastCompletionTime = userHabit.get(position).getHabitCompletedON();
            if (lastCompletionTime != null && lastCompletionTime.length() > 0
                    && !lastCompletionTime.equals("null")
                    && !lastCompletionTime.equals(null)) {
//                Log.d(TAG,"lastcompletiondate:-"+lastCompletionTime);
                int result = DateUtility.compareDateWithToday(lastCompletionTime, "E MMM dd yyyy");
                if (result == 0) {
                    checkbox.setImageResource(R.drawable.checked_chk);
                    checkbox.setTag(R.drawable.checked_chk);
                    checkbox.setEnabled(false);
                }
            } else {
                checkbox.setImageResource(R.drawable.uncheck_chk);
            }


            checkbox.setTag(position);
            convertView.setTag(position);
            imvHabitIcon.setTag(position);
            tvHabit.setTag(position);
            checkbox.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (((Integer) checkbox.getTag()) == R.drawable.checked_chk) {
                        checkbox.setImageResource(R.drawable.uncheck_chk);
                        checkbox.setTag(R.drawable.uncheck_chk);
                    } else {
                        checkbox.setImageResource(R.drawable.checked_chk);
                        checkbox.setTag(R.drawable.checked_chk);
                    }
                }
            });
            tvHabit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int click = (Integer) v.getTag();
                    Intent i = new Intent(MyHabits.this, MyHabit_AVScreen.class);
                    i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
                    i.putExtra("isFirstWalkthrough", false);
                    i.putExtra("position", click);
                    i.putExtra("is_reminder_call", false);
                    startActivity(i);
                }
            });
            imvHabitIcon.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int click = (Integer) v.getTag();
                    Intent i = new Intent(MyHabits.this, MyHabit_AVScreen.class);
                    i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
                    i.putExtra("isFirstWalkthrough", false);
                    i.putExtra("position", click);
                    i.putExtra("is_reminder_call", false);
                    startActivity(i);
                }
            });
            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_add_habit:
                intent = new Intent(MyHabits.this, HabitList.class);
                intent.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
                intent.putExtra(AppsConstant.RITUAL_TIME, ritualTime);
                intent.putExtra("first_time",true);
                Log.d(TAG,"first time:-"+first_time);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_add_habit1:
                intent = new Intent(MyHabits.this, HabitList.class);
                intent.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
                intent.putExtra(AppsConstant.RITUAL_TIME, ritualTime);
                startActivity(intent);
                break;

            case R.id.tv_add_habit:
                if (isRitualAdded) {
                    intent = new Intent(MyHabits.this, HabitList.class);
                    intent.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
                    intent.putExtra(AppsConstant.RITUAL_TIME, ritualTime);
                    startActivityForResult(intent, 1);
                }
                break;

            case R.id.tv_clock1:
                Calendar now = Calendar.getInstance();
                now.setTime(dateObj);


                TimePickerDialog tpd = TimePickerDialog.newInstance(MyHabits.this,
                        now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
                break;
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
        }
        if(mPlayer1!=null){
            mPlayer1.stop();
        }
    }

    private void setAlarm(String remTime) {
        String time24Format = DateUtility.getTimeIn24Format(remTime);

        String t[] = time24Format.split(":");
        int hourOfDay = Integer.parseInt(t[0]);
        int minute = Integer.parseInt(t[1]);

        int remID = userInfo.getRitualRemID();

        if (remID != 0)//update existing alarm
        {
            updateAlarm(remID, remTime, hourOfDay, minute);
        } else {
            Long tsLong = System.currentTimeMillis() / 1000;
            Random r = new Random();
            int timeStamp = tsLong.intValue() + r.nextInt(100);
            remID = tsLong.intValue();

            //Update ritual time
            updateData.updateUserRitualTime(userName, selectedRitual, remTime, remID);


            putData.addReminder(remID, userName, remTime, selectedRitual);
            putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_SUN, TableAttributes.OFF, -1);
            putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_SAT, TableAttributes.OFF, -1);
            //seting alarm for repeating days from monday to friday
            //for(int i=4; i<=6; i++)	//Set alrm from monday(day of week 2)to fri(day of week 6)
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.MONDAY, hourOfDay, minute, timeStamp, false);
            putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_MON, TableAttributes.ON, timeStamp);

            tsLong = System.currentTimeMillis() / 1000;
            timeStamp = tsLong.intValue() + r.nextInt(100);
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.TUESDAY, hourOfDay, minute, timeStamp, false);
            putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_TUE, TableAttributes.ON, timeStamp);

            tsLong = System.currentTimeMillis() / 1000;
            timeStamp = tsLong.intValue() + r.nextInt(100);
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.WEDNESDAY, hourOfDay, minute, timeStamp, false);
            putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_WED, TableAttributes.ON, timeStamp);

            tsLong = System.currentTimeMillis() / 1000;
            timeStamp = tsLong.intValue() + r.nextInt(100);
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.THURSDAY, hourOfDay, minute, timeStamp, false);
            putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_THU, TableAttributes.ON, timeStamp);

            tsLong = System.currentTimeMillis() / 1000;
            timeStamp = tsLong.intValue() + r.nextInt(100);
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.FRIDAY, hourOfDay, minute, timeStamp, false);
            putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_FRI, TableAttributes.ON, timeStamp);

        }
        SharedPreferences sp = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("reminder_set", true);
        editor.commit();

    }


    private void updateAlarm(int remID, String remTime, int hourOfDay, int minute) {
        //Update ritual time
        updateData.updateUserRitualTime(userName, selectedRitual, remTime, remID);
        updateData.updateReminderTime(remID, userName, remTime);

        ReminderDescModel remDesc = getData.getReminderDesc(remID, true);

        int rStamp = remDesc.getStampSun();
        if (rStamp != -1) {
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.SUNDAY, hourOfDay, minute, rStamp, true);
        }
        rStamp = remDesc.getStampMon();
        if (rStamp != -1) {
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.MONDAY, hourOfDay, minute, rStamp, true);
        }
        rStamp = remDesc.getStampTue();
        if (rStamp != -1) {
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.TUESDAY, hourOfDay, minute, rStamp, true);
        }
        rStamp = remDesc.getStampWed();
        if (rStamp != -1) {
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.WEDNESDAY, hourOfDay, minute, rStamp, true);
        }
        rStamp = remDesc.getStampThu();
        if (rStamp != -1) {
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.THURSDAY, hourOfDay, minute, rStamp, true);
        }
        rStamp = remDesc.getStampFri();
        if (rStamp != -1) {
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.FRIDAY, hourOfDay, minute, rStamp, true);
        }
        rStamp = remDesc.getStampSat();
        if (rStamp != -1) {
            GeneralUtility.setAlarmTime(MyHabits.this, userName, selectedRitual, Calendar.SATURDAY, hourOfDay, minute, rStamp, true);
        }

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = hourString + ":" + minuteString + "m";

        time = DateUtility.getTimeIn12Format(time);

        if (time.startsWith("0:"))
            time = time.replace("0:", "12:");
        tvClock1.setText(time);
        ritualTime = time;
        setAlarm(time);
        if (getValuePreference("after_add_alarm")) {
            String after_adding_habit_alarm="Hey, by the way, you can choose different different alarms for different days of the week. Tap the setting in top right";

            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if(MyApplication.tts_initialized){
                    MyApplication.tts.speak(after_adding_habit_alarm, TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            storePref("after_add_alarm", false);
        } else {
            if (selectedRitual.toLowerCase().contains("morn")) {
                Log.d("sunil", userHabit.size() + "");
                if (userHabit.size() > 1) {
                    playVoice();
                }
                else{
                    PlayMusicRandomly();
                }
            } else {
                Log.d("sunil", userHabit.size() + "");
                if (userHabit.size() > 0) {
                    playVoice();
                }
                else{
                    PlayMusicRandomly();
                }
            }
        }
    }
    public void PlayMusicRandomly(){
        Random rn = new Random();
		int range = 5 - 1 + 1;
		int randomNum =  rn.nextInt(range) +1;

        if(lst_int.size()>2){
            lst_int.clear();
        }

        if(lst_int.contains(randomNum)){
            PlayMusicRandomly();
        }
        else{
            lst_int.add(randomNum);
//            mPlayer = MediaPlayer.create(getApplicationContext(), randomNum);
//            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND))
//                mPlayer.start();
        }



    }


    @Override
    protected void onPause() {
        super.onPause();
//		if(player!=null && player.isPlaying())
//			player.pause();
        if (mPlayer1 != null)
            mPlayer1.pause();
        if(mPlayer!=null){
            mPlayer.pause();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //isResume = true;
        if (habit_flag) {
//			Random random=new Random();
//			int num=random.nextInt(2 - 1+ 1) + 1;
//			switch (num) {
//			case 1:
//				Toast.makeText(getApplicationContext(), "you can change the settings by using icon in top", Toast.LENGTH_LONG).show();
//	        	habit_flag=false;
//				break;
//			case 2:
//				mPlayer = MediaPlayer.create(MyHabits.this, R.raw.drag_and_drop_list);
//				mPlayer.start();
//				break;
//			default:
//				break;
//			}

        }
        setViewsVisibility();
        /*if(player!=null)
             player.start();
		 else
		 {
			 setBackgroundMusic();
		 }*/
    }
		    
	/*void stopBackgroundMusic()
	{
		if(player!=null)
		{
			player.stop();
			player = null;
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		stopBackgroundMusic();
	}
		 
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopBackgroundMusic();		 
	}
	*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isReminderCall) {
            Intent i = new Intent(MyHabits.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", "ok");
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            Log.d("sunil", "code");
            Log.d("msg", "msg4");
//	    	 playVoice();
//	        if(resultCode == Activity.RESULT_OK){
//	            String result=data.getStringExtra("result");
//	            Log.d("sunil",result);
//	            playVoice();
//	        }
//	        if (resultCode == Activity.RESULT_CANCELED) {
//	            //Write your code if there's no result
//	        }
        }
    }//onActivityResult
}

