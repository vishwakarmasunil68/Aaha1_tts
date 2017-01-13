package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.database.GetData;
import com.motivator.database.PrefData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.HabitModel;
import com.motivator.model.UserRitualModel;
import com.motivator.support.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyHabit_AVScreen extends Activity implements OnClickListener/*, TextToSpeech.OnInitListener*/ {

    MenuItem switchButton;
    int selectedHabit;
    boolean isSoundOn, isHabitCompleted = false;
    String userName, selectedRitual;
    View viewConnetor3, viewConnetor2, viewConnector1;
    TextView tvTitle, tvDesc, tvDay3, tvDay2, tvDay1, tvToday, tvTime, tvSkip, tvSnooze;
    ImageView imvPause, imvComplete;
    LinearLayout llAVS_bg;
    ImageView imvHabitImg;


    MediaPlayer player, player2;

    GetData getData;
    UpdateData update;
    ArrayList<HabitModel> userHabitList = new ArrayList<HabitModel>();

    String onFirstHabit = "First habit ";
    String nextHabit = "Next habit ";
    String alrightNextHabit = "Alright, next one is ";

    String goodJob = "Good job ";
    String thatsGreat = "thatsGreat";
    String awesome = "awesome ";
    String iamGlad = "I am glad you could do it ";
    int announceFirst;
    CountDownTimer countDownTimer;
    long timeRemaining = 0;
    boolean isCounting = true;
    boolean isFirstHabit = true, isFirstWalkthough = false, isReminderCall;
    int day = 0;
    MediaPlayer mPlayer;

//    String[] tts_text;
    List<String> list_tts_text=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getData = new GetData(MyHabit_AVScreen.this);
        update = new UpdateData(MyHabit_AVScreen.this);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.myhabit_avs);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setTitle("");

        SpannableString s = new SpannableString("");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(s);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        list_tts_text.add("And next on our agenda is");
        list_tts_text.add("And next on your program is.");
        list_tts_text.add("And we are finished with this one. Next up.");
        list_tts_text.add("At least these are going good today, right?");
        list_tts_text.add("Boppin’ right along.");
        list_tts_text.add("cool...");
        list_tts_text.add("Don’t forget this next one too.");
        list_tts_text.add("I have something new for you to try.");
        list_tts_text.add("I love your ambition. Next up");
        list_tts_text.add("Let’s keep going.");
        list_tts_text.add("Nice work! You’re on a roll.");
        list_tts_text.add("No no, you’re not done yet. Next up.");
        list_tts_text.add("Okay, okay, you had your fun. Now let’s try the next one.");
        list_tts_text.add("You are quite the dedicated person.");
        list_tts_text.add("Thank you for doing this. Now let’s go to the next one");
        list_tts_text.add("This should get your blood pumping a bit.");
        list_tts_text.add("We’re not quite done yet. Moving along.");
        list_tts_text.add("You are doing a wonderful job "+ PrefData.getStringPref(getApplicationContext(),PrefData.NAME_KEY)+". Let’s move on to the next challenge, shall we?");
        list_tts_text.add("You’re doing good, all things considering. Next up");
        list_tts_text.add("You are getting better at this..lets look at next habit.");
        list_tts_text.add("You are on quite a roll. Fantastic. Next is");

        userName = GeneralUtility.getPreferences(MyHabit_AVScreen.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        selectedHabit = getIntent().getExtras().getInt("position");
        isFirstWalkthough = getIntent().getExtras().getBoolean("isFirstWalkthrough");
        isReminderCall = getIntent().getExtras().getBoolean("is_reminder_call");

        userHabitList = getData.getUserHabits(userName, selectedRitual);


        llAVS_bg = (LinearLayout) findViewById(R.id.rll_avs_bg);
        imvHabitImg = (ImageView) findViewById(R.id.imv_habit_img);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_habit_desc);
        tvToday = (TextView) findViewById(R.id.tv_today);
        tvDay3 = (TextView) findViewById(R.id.tv_day3);
        tvDay2 = (TextView) findViewById(R.id.tv_day2);
        tvDay1 = (TextView) findViewById(R.id.tv_day1);
        viewConnetor3 = (View) findViewById(R.id.connector3);
        viewConnetor2 = (View) findViewById(R.id.connector2);
        viewConnector1 = (View) findViewById(R.id.connector1);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvSkip = (TextView) findViewById(R.id.tv_skip);
        tvSnooze = (TextView) findViewById(R.id.tv_snooze);

        imvPause = (ImageView) findViewById(R.id.imv_pause);
        imvComplete = (ImageView) findViewById(R.id.imv_complete);

        tvTitle.setTypeface(GeneralUtility.setTypeFace(MyHabit_AVScreen.this));
        tvDesc.setTypeface(GeneralUtility.setTypeFace(MyHabit_AVScreen.this));
        tvToday.setTypeface(GeneralUtility.setTypeFace(MyHabit_AVScreen.this));

        tvTime.setTypeface(GeneralUtility.setTypeFace(MyHabit_AVScreen.this));
        tvSkip.setTypeface(GeneralUtility.setTypeFace(MyHabit_AVScreen.this));
        tvSnooze.setTypeface(GeneralUtility.setTypeFace(MyHabit_AVScreen.this));

        imvComplete.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
        tvSnooze.setOnClickListener(this);
        imvPause.setOnClickListener(this);

        isSoundOn = GeneralUtility.getPreferencesBoolean(MyHabit_AVScreen.this, AppsConstant.AVS_SOUND);

        //player2 = new MediaPlayer();
        setBackgroundMusic();
        setHabitAVS(selectedHabit);

    }

    private void setHabitAVS(final int position) {
        ArrayList<String> habitTimeLine = new ArrayList<String>();
        if (position < userHabitList.size()) {
            int habitId = userHabitList.get(position).getHabitId();
            final String habitName = userHabitList.get(position).getHabit();

            tvToday.setText("Today");
            tvDay1.setText(DateUtility.getPreviousDayOfweek(-1));
            tvDay2.setText(DateUtility.getPreviousDayOfweek(-2));
            tvDay3.setText(DateUtility.getPreviousDayOfweek(-3));
            tvToday.setBackgroundResource(R.drawable.today_bg);

            habitTimeLine = getData.getHabitTimeLine(userName, habitId);
            String prvsDate = DateUtility.getPreviousDateString(-1, "E MMM dd yyyy");
            int ishabitAddedOn = DateUtility.compareTwoDate(userHabitList.get(position).getHabitAddedON(), prvsDate, DateUtility.E_MMM_dd_yyyy);
            if (ishabitAddedOn <= 0) {
                tvDay1.setVisibility(View.VISIBLE);
                if (habitTimeLine.contains(prvsDate)) {
                    tvDay1.setBackgroundResource(R.drawable.circle_green_lite);
                    viewConnector1.setVisibility(View.VISIBLE);
                    viewConnector1.setBackgroundColor(getResources().getColor(R.color.green_lite2));
                } else {
                    tvDay1.setBackgroundResource(R.drawable.circle_gray);
                    viewConnector1.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                }

                //get 2days back status
                prvsDate = DateUtility.getPreviousDateString(-2, "E MMM dd yyyy");
                ishabitAddedOn = DateUtility.compareTwoDate(userHabitList.get(position).getHabitAddedON(), prvsDate, DateUtility.E_MMM_dd_yyyy);
                if (ishabitAddedOn <= 0) {
                    tvDay2.setVisibility(View.VISIBLE);
                    if (habitTimeLine.contains(prvsDate)) {
                        tvDay2.setBackgroundResource(R.drawable.circle_green_lite);
                        viewConnetor2.setVisibility(View.VISIBLE);
                        viewConnetor2.setBackgroundColor(getResources().getColor(R.color.green_lite2));
                    } else {
                        tvDay2.setBackgroundResource(R.drawable.circle_gray);
                        viewConnetor2.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                    }
                    //get 3days back status
                    prvsDate = DateUtility.getPreviousDateString(-3, DateUtility.E_MMM_dd_yyyy);
                    ishabitAddedOn = DateUtility.compareTwoDate(userHabitList.get(position).getHabitAddedON(), prvsDate, DateUtility.E_MMM_dd_yyyy);
                    if (ishabitAddedOn <= 0) {
                        tvDay3.setVisibility(View.VISIBLE);
                        if (habitTimeLine.contains(prvsDate)) {
                            tvDay3.setBackgroundResource(R.drawable.circle_green_lite);
                            viewConnetor3.setVisibility(View.VISIBLE);
                            viewConnetor3.setBackgroundColor(getResources().getColor(R.color.green_lite2));
                        } else {
                            tvDay3.setBackgroundResource(R.drawable.circle_gray);
                            viewConnetor3.setBackgroundColor(getResources().getColor(R.color.gray_dark));
                        }
                    } else {
                        tvDay3.setVisibility(View.GONE);
                        viewConnetor3.setVisibility(View.GONE);
                    }
                } else {
                    tvDay2.setVisibility(View.GONE);
                    tvDay3.setVisibility(View.GONE);
                    viewConnetor2.setVisibility(View.GONE);
                    viewConnetor3.setVisibility(View.GONE);
                }

            } else {
                tvDay1.setVisibility(View.GONE);
                tvDay2.setVisibility(View.GONE);
                tvDay3.setVisibility(View.GONE);
                viewConnector1.setVisibility(View.GONE);
                viewConnetor2.setVisibility(View.GONE);
                viewConnetor3.setVisibility(View.GONE);
            }


            tvTitle.setText(userHabitList.get(position).getHabit());

//			int color_code=userHabitList.get(position).get

            if (userHabitList.get(position).getHabitDescription().equalsIgnoreCase(TableAttributes.custom_habit)) {
                tvDesc.setVisibility(View.INVISIBLE);
                imvHabitImg.setBackgroundResource(AppsConstant.getHabitImg(habitId));
//				if(userHabitList.get(position).getReminderDesc2()!=null && userHabitList.get(position).getReminderDesc2().length()>0)
//				{
//					llAVS_bg.setBackgroundColor(Color.parseColor(userHabitList.get(position).getReminderDesc2()));
//				}
//				else
//					llAVS_bg.setBackgroundColor(getResources().getColor(R.color.custom_habit_color));
                if (userHabitList.get(position).getReminderDesc1() != null && userHabitList.get(position).getReminderDesc1().length() > 0) {
                    //Bitmap bmp = BitmapFactory.decodeFile();
                    Drawable d = Drawable.createFromPath(userHabitList.get(position).getReminderDesc1());
                    ;
                    if (d != null) {
                        llAVS_bg.setBackground(d);
                    } else
                        llAVS_bg.setBackgroundColor(getResources().getColor(R.color.custom_habit_color));
                } else {
                    imvHabitImg.setBackgroundResource(R.drawable.app_icon1);
                }
            } else {
                if (habitId == 14) {
                    llAVS_bg.setBackgroundResource(R.drawable.celebrate_bg);
                    imvHabitImg.setBackgroundResource(AppsConstant.getHabitImg(habitId));
                } else {
                    llAVS_bg.setBackgroundColor(Color.parseColor(AppsConstant.getHabitBackground(habitId)));
                    imvHabitImg.setBackgroundResource(AppsConstant.getHabitImg(habitId));
                }
                tvDesc.setVisibility(View.VISIBLE);
                setReminderText(habitId, position);
            }


            UserRitualModel userReminderSetting = getData.getRitualsDetails(userName, selectedRitual);
            announceFirst = userReminderSetting.getAnnounceFirst();

            setHabitMusic(habitId);
//			setTextToVoice(announceFirst, habitName);

            String time = userHabitList.get(position).getHabitTime();
            tvTime.setText(time + " min");
            try {
                Log.d("sunil", "color:-" + userHabitList.get(position).getReminderDesc2());
                llAVS_bg.setBackgroundColor(Color.parseColor(userHabitList.get(position).getReminderDesc2()));
            } catch (Exception e) {

            }
//			llAVS_bg.setBackgroundResource(Color);
            //setCounter for habits
            GetData getData = new GetData(MyHabit_AVScreen.this);
            String lastCompletionTime = getData.getCompletionTime(userName, habitId);
            if (lastCompletionTime == null || lastCompletionTime.length() <= 0) {
                Log.d("sunil", "changed");
                try {
                    timeRemaining = Integer.parseInt(time);
                    isCounting = true;
                    imvPause.setImageResource(R.drawable.pause_icon);
                } catch (Exception e) {
                    timeRemaining = 5;
                    isCounting = true;
                }
                if (isCounting) {
                    setTimeCountdown(time);
                }
            } else {
                int result = DateUtility.compareDateWithToday(lastCompletionTime, "E MMM dd yyyy");
                if (result > 0) {
                    Log.d("sunil", "changed 1");
                    try {
                        timeRemaining = Integer.parseInt(time);
                        isCounting = true;
                        imvPause.setImageResource(R.drawable.pause_icon);
                    } catch (Exception e) {
                        timeRemaining = 5;
                        isCounting = true;
                    }
                    if (isCounting) {
                        setTimeCountdown(time);
                    }
                } else {
                    tvTime.setText("Completed!");
                    isHabitCompleted = true;
                    tvToday.setText("");
                    tvToday.setBackgroundResource(R.drawable.today_completed_bg);
                }
            }
        } else {
            selectedHabit = --selectedHabit;
        }
    }

    private void setReminderText(int habitId, int position) {
        int desc = userHabitList.get(position).getReminderNextDesc();
        switch (desc) {
            case 0:
                tvDesc.setText(userHabitList.get(position).getHabitDescription());
                update.updateUserHabitInfo(userName, habitId, TableAttributes.REMINDER_NEXT_DESC, 1);
                break;
            case 1:
                tvDesc.setText(userHabitList.get(position).getReminderDesc1());
                update.updateUserHabitInfo(userName, habitId, TableAttributes.REMINDER_NEXT_DESC, 2);
                break;
            case 2:
                tvDesc.setText(userHabitList.get(position).getReminderDesc2());
                update.updateUserHabitInfo(userName, habitId, TableAttributes.REMINDER_NEXT_DESC, 3);
                break;
            case 3:
                tvDesc.setText(userHabitList.get(position).getReminderDesc3());
                update.updateUserHabitInfo(userName, habitId, TableAttributes.REMINDER_NEXT_DESC, 4);
                break;
            case 4:
                tvDesc.setText(userHabitList.get(position).getReminderDesc4());
                update.updateUserHabitInfo(userName, habitId, TableAttributes.REMINDER_NEXT_DESC, 5);
                break;
            case 5:
                tvDesc.setText(userHabitList.get(position).getReminderDesc5());
                update.updateUserHabitInfo(userName, habitId, TableAttributes.REMINDER_NEXT_DESC, 6);
                break;
            case 6:
                tvDesc.setText(userHabitList.get(position).getReminderDesc6());
                update.updateUserHabitInfo(userName, habitId, TableAttributes.REMINDER_NEXT_DESC, 0);
                break;

            default:
                tvDesc.setText(userHabitList.get(position).getHabitDescription());
                update.updateUserHabitInfo(userName, habitId, TableAttributes.REMINDER_NEXT_DESC, 1);
                break;
        }
//		tts = new TextToSpeech(getApplicationContext(), new OnInitListener() {
//			@Override
//			public void onInit(int status) {
//				// TODO Auto-generated method stub
//				convertTextToSpeech(status, "hello this is sunil kumar vishwakarma");
//			}
//		});

    }

    private void setBackgroundMusic() {
        try {
            String musicFile = "music/morning.mp3";
            if (selectedRitual.equalsIgnoreCase(AppsConstant.MORNING_RITUAL))
                musicFile = "music/morning.mp3";
            else if (selectedRitual.equalsIgnoreCase(AppsConstant.LUNCH_RITUAL))
                musicFile = "music/lunch.mp3";
            else if (selectedRitual.equalsIgnoreCase(AppsConstant.EVENING_RITUAL))
                musicFile = "music/evening.mp3";
            // Read the music file from the asset folder
            AssetFileDescriptor afd = getAssets().openFd(musicFile);
            //afd = getAssets().openFd(AppsConstant.getHabitMusic(habitId));
            // Creation of new media player;
            player = new MediaPlayer();
            // Set the player music source.
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            // Set the looping and play the music.
            player.setLooping(true);
            player.prepare();
            if (isSoundOn) {
                player.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHabitMusic(int habitId) {
        try {
            if (player != null)
                //player.pause();
                FadeOut(player, 2000);
            player2 = null;
            player2 = new MediaPlayer();
            // Read the music file from the asset folder
            AssetFileDescriptor afd = getAssets().openFd(AppsConstant.getHabitMusic(habitId));
            // Creation of new media player;
            // Set the player music source.
            player2.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            // Set the looping and play the music.
            player2.setLooping(false);
            player2.prepare();
            if (isSoundOn) {
                player2.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    float volume = 1;
    float speed = 0.05f;
    private MediaPlayer mpPlayer;

    public void FadeOut(MediaPlayer player, float deltaTime) {
        player.setVolume(volume, volume);
        volume -= speed * deltaTime;
    }

    public void FadeIn(MediaPlayer player, float deltaTime) {
        player.setVolume(volume, volume);
        volume += speed * deltaTime;
    }

    private void setTimeCountdown(String t) {
        isCounting = true;
        imvPause.setVisibility(View.VISIBLE);
        long time = Integer.parseInt(t);
        time = time * 60 * 1000;//convert min into millisec
        countDownTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                long sec = millisUntilFinished / 1000;
                long min = sec / 60;
                sec = sec % 60;
                tvTime.setText(min + "M: " + sec + "S");
            }

            public void onFinish() {
                tvTime.setText("Time Completed!");
                imvPause.setVisibility(View.GONE);
                tvToday.setText("");
                tvToday.setBackgroundResource(R.drawable.today_completed_bg);
                //int result = update.updateUserHabitStatus(userName, userHabitList.get(selectedHabit).getHabitId(),selectedRitual, TableAttributes.COMPLETED);
                if (!isHabitCompleted) {
                    int result = update.updateUserHabitStatus(userName, userHabitList.get(selectedHabit).getHabitId(), selectedRitual, userHabitList.size());
                }
//				tts = new TextToSpeech(getApplicationContext(), new OnInitListener() {
//					@Override
//					public void onInit(int status) {
//						// TODO Auto-generated method stub
//						tts.setPitch(1.0f);
//						convertTextToSpeech(status, ttsmsg(day));
//					}
//				});

            }

            public long getTimePassed() {
                return timeRemaining;
            }

        }.start();

    }

    private void setTimeCountdown(long time) {
        isCounting = true;
        imvPause.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                long sec = millisUntilFinished / 1000;
                long min = sec / 60;
                sec = sec % 60;
                tvTime.setText(min + "M: " + sec + "S");
            }

            public void onFinish() {
                tvTime.setText("Time Completed!");
                imvPause.setVisibility(View.GONE);
                tvToday.setText("");
                tvToday.setBackgroundResource(R.drawable.today_completed_bg);
                //int result = update.updateUserHabitStatus(userName, userHabitList.get(selectedHabit).getHabitId(),selectedRitual, TableAttributes.COMPLETED);
                if (!isHabitCompleted) {
                    int result = update.updateUserHabitStatus(userName, userHabitList.get(selectedHabit).getHabitId(), selectedRitual, userHabitList.size());
                }
            }

            public long getTimePassed() {
                return timeRemaining;
            }

        }.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);
        switchButton = menu.findItem(R.id.action);
        isSoundOn = GeneralUtility.getPreferencesBoolean(MyHabit_AVScreen.this, AppsConstant.AVS_SOUND);
        if (isSoundOn) {
            switchButton.setIcon(R.drawable.voice);
        } else {
            switchButton.setIcon(R.drawable.voice_mute);
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
                isSoundOn = GeneralUtility.getPreferencesBoolean(MyHabit_AVScreen.this, AppsConstant.AVS_SOUND);
                if (isSoundOn) {
                    isSoundOn = false;
                    switchButton.setIcon(R.drawable.voice_mute);
                    GeneralUtility.setPreferencesBoolean(MyHabit_AVScreen.this, AppsConstant.AVS_SOUND, false);

                    if (player != null)
                        player.pause();
                } else {
                    isSoundOn = true;
                    switchButton.setIcon(R.drawable.voice);
                    GeneralUtility.setPreferencesBoolean(MyHabit_AVScreen.this, AppsConstant.AVS_SOUND, true);
                    if (player != null) {
                        player.start();
                    } else {
                        setBackgroundMusic();
                    }
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
                skipHabit();
                break;

            case R.id.tv_snooze:
                skipHabit();
                break;

            case R.id.imv_pause:
                if (isCounting) {
                    isCounting = false;
                    countDownTimer.cancel();
                    imvPause.setImageResource(R.drawable.play_icon);
                } else {
                    isCounting = true;
                    setTimeCountdown(timeRemaining);
                    imvPause.setImageResource(R.drawable.pause_icon);
                }

                break;

            case R.id.imv_complete:

                //int result = update.updateUserHabitStatus(userName, userHabitList.get(selectedHabit).getHabitId(), selectedRitual, TableAttributes.COMPLETED);
                if (!isHabitCompleted)
                    update.updateUserHabitStatus(userName, userHabitList.get(selectedHabit).getHabitId(), selectedRitual, userHabitList.size());

                final boolean isSoundOn = GeneralUtility.getPreferencesBoolean(MyHabit_AVScreen.this, AppsConstant.AVS_SOUND);
                tvTime.setText("Completed!");

                //
                playMusic();
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                imvComplete.setEnabled(false);

                imvPause.setVisibility(View.GONE);
                tvToday.setText("");
                tvToday.setBackgroundResource(R.drawable.today_completed_bg);
                Log.d(TAG, "selected habit:-" + selectedHabit);
                Log.d(TAG, "user habit list:-" + userHabitList.size());
                ++selectedHabit;

                if (selectedHabit < userHabitList.size()) {
                    setHabitAVS(selectedHabit);
                } else {
                    --selectedHabit;
                    imvComplete.setEnabled(false);
                    Log.d(TAG,"on music completed");
                    if (mpPlayer != null && mpPlayer.isPlaying()) {
                        mpPlayer.setOnCompletionListener(new OnCompletionListener() {

                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                // TODO Auto-generated method stub
                                Intent i = new Intent(MyHabit_AVScreen.this, HabitCompletionActivity.class);
                                i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
                                if (isFirstWalkthough)
                                    i.putExtra("isFirstWalkthrough", true);
                                else
                                    i.putExtra("isFirstWalkthrough", false);

                                startActivity(i);
                                finish();
                            }
                        });
                    } else {
                        new CountDownTimer(3000,1000){

                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                Intent i = new Intent(MyHabit_AVScreen.this, HabitCompletionActivity.class);
                                i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
                                if (isFirstWalkthough)
                                    i.putExtra("isFirstWalkthrough", true);
                                else
                                    i.putExtra("isFirstWalkthrough", false);

                                startActivity(i);
                                finish();
                            }
                        }.start();

                    }
                }
                break;
        }
    }
    private void musicPlayingMethod() {
//        String[] list_strings= getResources().getStringArray(R.array.myhabitavscreenttstext);
        playmyhabitvoices(list_tts_text);
    }

    private final String TAG = "myhabitavscreen";

    public void playmyhabitvoices(List<String> list_files) {
        int val = Pref.getInteger(getApplicationContext(), StringUtils.MYHABIT_AV_SCREEN_VOICE, -1);
        Log.d(TAG, "pref for tis:-" + val);
        if (list_files.size() > 0) {
            if ((val + 1) >= list_files.size()) {
                val = 0;
            } else {
                val = val + 1;
            }
        }
        try {
            Log.d(TAG, "final val:-" + val);

            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if (isSoundOn && announceFirst == TableAttributes.ON) {
                    if (player != null) {
                        int MAX_VOLUME = 100;
                        final float volume = (float) (1 - (Math.log(MAX_VOLUME - 30) / Math.log(MAX_VOLUME)));
                        player.setVolume(volume, volume);
                    }
                    if (player2 != null) {
                        int MAX_VOLUME = 100;
                        final float volume = (float) (1 - (Math.log(MAX_VOLUME - 30) / Math.log(MAX_VOLUME)));
                        player2.setVolume(volume, volume);
                    }
                    Log.d(TAG, "music playing");
                    if(MyApplication.tts_initialized){
                        MyApplication.tts.speak(list_files.get(val), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    new CountDownTimer(3000, 100) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            imvComplete.setEnabled(true);
                            if (player != null) {
                                int MAX_VOLUME = 100;
                                final float volume = (float) (1 - (Math.log(MAX_VOLUME - 100) / Math.log(MAX_VOLUME)));
                                player.setVolume(1.0f, 1.0f);
                            }
                            if (player2 != null) {
                                int MAX_VOLUME = 100;
                                final float volume = (float) (1 - (Math.log(MAX_VOLUME - 100) / Math.log(MAX_VOLUME)));
                                player2.setVolume(1.0f, 1.0f);
                            }
                        }

                    }.start();
                }
            }

            Pref.setInteger(getApplicationContext(), StringUtils.MYHABIT_AV_SCREEN_VOICE, val);
            Log.d(TAG, "pref mood:-" + Pref.getInteger(getApplicationContext(), StringUtils.MYHABIT_AV_SCREEN_VOICE, -1));
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    public void playMusic() {

        musicPlayingMethod();

    }

    public void sleep() {

    }

    private void skipHabit() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        ++selectedHabit;
        if (selectedHabit < userHabitList.size()) {
            setHabitAVS(selectedHabit);
            playSkipMusic();

            if (mPlayer != null) {
                mPlayer.setOnCompletionListener(new OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        if (player != null) {
                            int MAX_VOLUME = 100;
                            final float volume = (float) (1 - (Math.log(MAX_VOLUME - 100) / Math.log(MAX_VOLUME)));
                            player.setVolume(1.0f, 1.0f);
                        }
                        if (player2 != null) {
                            int MAX_VOLUME = 100;
                            final float volume = (float) (1 - (Math.log(MAX_VOLUME - 100) / Math.log(MAX_VOLUME)));
                            player2.setVolume(1.0f, 1.0f);
                        }
                    }
                });
            }
        } else {
            --selectedHabit;
            if (isFirstWalkthough) {
                Intent i = new Intent(MyHabit_AVScreen.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            } else {
                finish();
                Toast.makeText(MyHabit_AVScreen.this, "No more habits in this ritual", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void playSkipMusic() {
        musicPlayingMethod();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && player.isPlaying())
            player.pause();
        if (player2 != null && player2.isPlaying())
            player2.pause();
        if (mpPlayer != null && mpPlayer.isPlaying()) {
            mpPlayer.stop();
        }
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        isSoundOn = GeneralUtility.getPreferencesBoolean(MyHabit_AVScreen.this, AppsConstant.AVS_SOUND);
        if (isSoundOn) {
            if (player != null)
                player.start();
            else {
                setBackgroundMusic();
            }
        }
    }


    void stopBackgroundMusic() {
        if (mPlayer != null) {
            mPlayer.stop();
        }
        if (player != null) {
            player.stop();
            player = null;
        }
        if (player2 != null) {
            player2.stop();
            player2 = null;
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
//        if (MyApplication.tts != null) {
//            MyApplication.tts.stop();
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        stopBackgroundMusic();
        if (isFirstWalkthough) {
            Intent i = new Intent(MyHabit_AVScreen.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        } else if (isReminderCall) {
            Intent i = new Intent(MyHabit_AVScreen.this, MyHabits.class);
            i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
            i.putExtra("is_reminder_call", true);
            startActivity(i);
            finish();
        } else
            finish();
    }
}

