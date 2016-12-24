package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.PutData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.HabitModel;
import com.motivator.model.UserRitualModel;
import com.motivator.support.FileUtils;
import com.motivator.wecareyou.fragment.AlarmFragment;
import com.motivator.wecareyou.fragment.HabitFragment;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class RitualSetting extends Activity implements OnClickListener, TimePickerDialog.OnTimeSetListener {
    MenuItem switchButton;
    Date dateObj;
    LinearLayout llTopImg, frameAlarm, frameHabitList;
    Fragment fragmentAlarm, habitFragment;
    TextView tvAddAlarm;
    ImageView imvFullScreen, imvSimple, imvFullSelect, imvSimpleSelect;
    CheckBox /*chkUrgencySwipe,*/ chkAnnounce, chkRing;
    EditText edtRitualName;
    ImageView /*imvUrgencySwipe,*/ imvAnnounce, imvRing, imvRitualImg;

    String selectedRitual;
    String userName, alarmTime;
    GetData getData;
    PutData putData;
    UpdateData updateData;
    UserRitualModel userRitualInfo = new UserRitualModel();
    ArrayList<HabitModel> userHabit = new ArrayList<HabitModel>();
    MediaPlayer mpPlayer;
    boolean isSoundOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData = new GetData(RitualSetting.this);
        putData = new PutData(RitualSetting.this);
        updateData = new UpdateData(RitualSetting.this);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        alarmTime = getIntent().getExtras().getString("time");

        //getDAta from shared prefrence
        userName = GeneralUtility.getPreferences(RitualSetting.this, AppsConstant.user_name);
        userRitualInfo = getData.getRitualsDetails(userName, selectedRitual);


        setContentView(R.layout.ritual_setting);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sky_blue)/*Color.parseColor("#330000ff")*/));
        //actionBar.setTitle(userRitualInfo.getRitualDisplayName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        SpannableString s = new SpannableString(userRitualInfo.getRitualDisplayName().toLowerCase());
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(s);

        intializeUIViews();
        //new DownloadMusicfromInternet().execute("https://drive.google.com/a/emobitechnologies.com/uc?id=0B3ex69v7mDnyU1JqZmRSWkpPTlk&export=download");

        edtRitualName.setText(userRitualInfo.getRitualDisplayName());
        imvRitualImg.setImageResource(AppsConstant.getRitualTopImg(userRitualInfo.getRitualImg()));
        llTopImg.setBackgroundResource(AppsConstant.getRitualTopImg(userRitualInfo.getRitualImg()));
        //add fragments
        dateObj = DateUtility.getDateObject(alarmTime, "hh:mm a");
        addAlarmFragment();
        addHabitFragment(alarmTime);

        int isfullScreen = userRitualInfo.getNotificationStyle();
        if (isfullScreen == TableAttributes.ON) {
            setFullScreenNotification();
        } else
            setSimpleScreenNotification();
//        if(userReminderSetting.getUrgencySwipe()==TableAttributes.ON)
//        {
//        	chkUrgencySwipe.setChecked(true);
//        }
//        else
//        	chkUrgencySwipe.setChecked(false);
        int announceFirst = userRitualInfo.getAnnounceFirst();
        if (announceFirst == TableAttributes.ON) {
            chkAnnounce.setChecked(true);
        } else
            chkAnnounce.setChecked(false);

        int ringInSilent = userRitualInfo.getRingInSilent();
        if (ringInSilent == TableAttributes.ON) {
            chkRing.setChecked(true);
        } else
            chkRing.setChecked(false);

        chkAnnounce.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateData.updateReminderSetting(userName, selectedRitual, TableAttributes.ANNOUNCE_FIRST, TableAttributes.ON);
                } else
                    updateData.updateReminderSetting(userName, selectedRitual, TableAttributes.ANNOUNCE_FIRST, TableAttributes.OFF);
            }
        });
        chkRing.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateData.updateReminderSetting(userName, selectedRitual, TableAttributes.RING_IN_SILENT, TableAttributes.ON);
                } else
                    updateData.updateReminderSetting(userName, selectedRitual, TableAttributes.RING_IN_SILENT, TableAttributes.OFF);
            }
        });
    }

    private void intializeUIViews() {
        llTopImg = (LinearLayout) findViewById(R.id.ll_top_img);
        frameAlarm = (LinearLayout) findViewById(R.id.frame_alarm);

        tvAddAlarm = (TextView) findViewById(R.id.tv_add_alarm);
        imvFullScreen = (ImageView) findViewById(R.id.imv_full_screen);
        imvSimple = (ImageView) findViewById(R.id.imv_simple);
        imvFullSelect = (ImageView) findViewById(R.id.imv_full_select);
        imvSimpleSelect = (ImageView) findViewById(R.id.imv_simple_select);

        //imvUrgencySwipe= (ImageView)findViewById(R.id.imv_urgency_swipe);
        imvAnnounce = (ImageView) findViewById(R.id.imv_announce_first_habit);
        imvRing = (ImageView) findViewById(R.id.imv_ring_in_silent);

        edtRitualName = (EditText) findViewById(R.id.edt_ritual_name);
        imvRitualImg = (ImageView) findViewById(R.id.imv_ritual_image);

        //chkUrgencySwipe = (CheckBox)findViewById(R.id.chk_urgency_swipe);
        chkAnnounce = (CheckBox) findViewById(R.id.chk_announce_first_habit);
        chkRing = (CheckBox) findViewById(R.id.chk_ring_in_silent);

        frameHabitList = (LinearLayout) findViewById(R.id.frame_habit_list);

        //set onclick listener
        tvAddAlarm.setOnClickListener(this);
        imvFullScreen.setOnClickListener(this);
        imvSimple.setOnClickListener(this);
        imvFullSelect.setOnClickListener(this);
        imvSimpleSelect.setOnClickListener(this);
        //imvUrgencySwipe.setOnClickListener(this);
        imvAnnounce.setOnClickListener(this);
        imvRing.setOnClickListener(this);
        imvRitualImg.setOnClickListener(this);
        if (getValuePreference("ritual_setting_first")) {
            if (GeneralUtility.getPreferencesBoolean(RitualSetting.this, AppsConstant.AVS_SOUND)) {
                File soundFile = new File(FileUtils.RITUAL_SETTING_FILE_PATH);
                if (soundFile.exists()) {
                    mpPlayer = new MediaPlayer();
                    try {
                        mpPlayer.setDataSource(soundFile.toString());

                        mpPlayer.prepare();
                        if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                            mpPlayer.start();
                            setValuePreference("ritual_setting_first", false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }


    }

    public boolean getValuePreference(String key) {
        SharedPreferences sp = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        return sp.getBoolean(key, true);
    }

    public void setValuePreference(String key, boolean value) {
        SharedPreferences sp = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mpPlayer != null) {
            mpPlayer.stop();
        }
    }

    private void addAlarmFragment() {
        fragmentAlarm = new AlarmFragment();
        Bundle args = new Bundle();
        //args.putString(AlarmFragment.ALARM_ARG, time);
        args.putString(AppsConstant.SELECTED_RITUAL, selectedRitual);
        fragmentAlarm.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_alarm, fragmentAlarm).commit();

    }

    private void addHabitFragment(String time) {
        habitFragment = new HabitFragment();
        Bundle args = new Bundle();
        args.putString(AppsConstant.RITUAL_TIME, time);
        args.putString(AppsConstant.SELECTED_RITUAL, selectedRitual);
        habitFragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_habit_list, habitFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.journey_menu, menu);
        MenuItem switchButton1 = menu.findItem(R.id.done);
        switchButton1.setIcon(R.drawable.edit);
        switchButton1.setVisible(false);


        switchButton = menu.findItem(R.id.action);

        isSoundOn = GeneralUtility.getPreferencesBoolean(RitualSetting.this, AppsConstant.AVS_SOUND);
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

            case R.id.done:
                //Toast.makeText(MyHabits.this, "Hi", 5).show();
                return true;
            case R.id.action:
                isSoundOn = GeneralUtility.getPreferencesBoolean(RitualSetting.this, AppsConstant.AVS_SOUND);
                if (isSoundOn) {
                    isSoundOn = false;
                    switchButton.setIcon(R.drawable.voice_mute);
                    GeneralUtility.setPreferencesBoolean(RitualSetting.this, AppsConstant.AVS_SOUND, false);
                    try {
                        mpPlayer.pause();
                    } catch (Exception e) {

                    }
                } else {
                    isSoundOn = true;
                    switchButton.setIcon(R.drawable.voice);
                    GeneralUtility.setPreferencesBoolean(RitualSetting.this, AppsConstant.AVS_SOUND, true);
                    try {
                        mpPlayer.start();
                    } catch (Exception e) {

                    }
                }
                return true;
            case R.id.action_feedback:
                View rootView = findViewById(android.R.id.content).getRootView();
                rootView.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
                rootView.setDrawingCacheEnabled(false);

                Intent feedback = new Intent(RitualSetting.this, Feedback.class);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
                feedback.putExtra(AppsConstant.sreenshot, bs.toByteArray());
                //feedback.putExtra(AppsConstant.sreenshot, bitmap);
                startActivity(feedback);
                bitmap.recycle();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_add_alarm:
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateObj);
                TimePickerDialog tpd = TimePickerDialog.newInstance(RitualSetting.this,
                        cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");

                break;
            case R.id.imv_full_screen:
                setFullScreenNotification();
                updateData.updateReminderSetting(userName, selectedRitual, TableAttributes.NOTIFICATION_STYLE, TableAttributes.ON);
                break;

            case R.id.imv_simple:
                setSimpleScreenNotification();
                updateData.updateReminderSetting(userName, selectedRitual, TableAttributes.NOTIFICATION_STYLE, TableAttributes.OFF);
                break;

            case R.id.imv_full_select:
                setFullScreenNotification();
                updateData.updateReminderSetting(userName, selectedRitual, TableAttributes.NOTIFICATION_STYLE, TableAttributes.ON);
                break;

            case R.id.imv_simple_select:
                setSimpleScreenNotification();
                updateData.updateReminderSetting(userName, selectedRitual, TableAttributes.NOTIFICATION_STYLE, TableAttributes.OFF);
                break;

//		case R.id.imv_urgency_swipe:
//			GeneralUtility.PopUpInfo(RitualSetting.this, R.string.urgency_swipe, R.string.urgency_swipe_msg);
//			break;

            case R.id.imv_announce_first_habit:
                GeneralUtility.PopUpInfo(RitualSetting.this, R.string.announcement, R.string.announcement_msg);
                break;

            case R.id.imv_ring_in_silent:
                GeneralUtility.PopUpInfo(RitualSetting.this, R.string.ring_in_silent, R.string.ring_in_silent_msg);
                break;

            case R.id.imv_ritual_image:
                String rName = edtRitualName.getText().toString();
                Intent i = new Intent(RitualSetting.this, RitualImageUpdate.class);
                i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
                i.putExtra("new_name", rName);
                startActivityForResult(i, AppsConstant.RITUAL_IMG);
                break;
        }

    }

    private void setFullScreenNotification() {
        imvFullScreen.setImageResource(R.drawable.simple);
        imvFullSelect.setImageResource(R.drawable.selected);
        imvSimple.setImageResource(R.drawable.full_screen);
        imvSimpleSelect.setImageResource(R.drawable.not_selected);
    }

    private void setSimpleScreenNotification() {
        imvFullScreen.setImageResource(R.drawable.full_screen);
        imvFullSelect.setImageResource(R.drawable.not_selected);
        imvSimple.setImageResource(R.drawable.simple);
        imvSimpleSelect.setImageResource(R.drawable.selected);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = hourString + ":" + minuteString + "m";

        time = DateUtility.getTimeIn12Format(time);

        if (time.startsWith("0:"))
            time = time.replace("0:", "12:");
        addAlarmFragment();

        Long tsLong = System.currentTimeMillis() / 1000;
        int remID = tsLong.intValue();
        Random r = new Random();
        int timeStamp = tsLong.intValue() + r.nextInt(100);

        putData.addReminder(remID, userName, time, selectedRitual);
        updateData.updateUserRitualTime(userName, selectedRitual, time, remID);
        putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_SUN, TableAttributes.OFF, -1);
        putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_SAT, TableAttributes.OFF, -1);

        //seting alarm for repeating days from monday to friday
        //for(int i=4; i<=6; i++)	//Set alrm from monday(day of week 2)to fri(day of week 6)
        GeneralUtility.setAlarmTime(RitualSetting.this, userName, selectedRitual, Calendar.MONDAY, hourOfDay, minute, timeStamp, false);
        putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_MON, TableAttributes.ON, timeStamp);

        tsLong = System.currentTimeMillis() / 1000;
        timeStamp = tsLong.intValue() + r.nextInt(100);
        ;
        GeneralUtility.setAlarmTime(RitualSetting.this, userName, selectedRitual, Calendar.TUESDAY, hourOfDay, minute, timeStamp, false);
        putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_TUE, TableAttributes.ON, timeStamp);

        tsLong = System.currentTimeMillis() / 1000;
        timeStamp = tsLong.intValue() + r.nextInt(100);
        ;
        GeneralUtility.setAlarmTime(RitualSetting.this, userName, selectedRitual, Calendar.WEDNESDAY, hourOfDay, minute, timeStamp, false);
        putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_WED, TableAttributes.ON, timeStamp);


        tsLong = System.currentTimeMillis() / 1000;
        timeStamp = tsLong.intValue() + r.nextInt(100);
        ;
        GeneralUtility.setAlarmTime(RitualSetting.this, userName, selectedRitual, Calendar.THURSDAY, hourOfDay, minute, timeStamp, false);
        putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_THU, TableAttributes.ON, timeStamp);


        tsLong = System.currentTimeMillis() / 1000;
        timeStamp = tsLong.intValue() + r.nextInt(100);
        ;
        GeneralUtility.setAlarmTime(RitualSetting.this, userName, selectedRitual, Calendar.FRIDAY, hourOfDay, minute, timeStamp, false);
        putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_FRI, TableAttributes.ON, timeStamp);

    }


    // Async Task Class
    class DownloadMusicfromInternet extends AsyncTask<String, String, String> {

        // Show Progress bar before downloading Music
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
            // showDialog(progress_bar_type);
        }

        // Download Music File from Internet
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // Get Music file length
                int lenghtOfFile = conection.getContentLength();
                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 10 * 1024);
                // Output stream to write file in SD card
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/reminder.mp3");
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress which triggers onProgressUpdate method
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // Write data to file
                    output.write(data, 0, count);
                }
                // Flush output
                output.flush();
                // Close streams
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }

        // While Downloading Music File
        protected void onProgressUpdate(String... progress) {
            // Set progress percentage
            // prgDialog.setProgress(Integer.parseInt(progress[0]));
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
            // Dismiss the dialog after the Music file was downloaded
            // dismissDialog(progress_bar_type);
            Toast.makeText(getApplicationContext(), "Download complete, playing Music", Toast.LENGTH_LONG).show();
            // Play the music
            // playMusic();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == AppsConstant.RITUAL_IMG) {
                int ritualImg = data.getIntExtra("img_num", 1);
                imvRitualImg.setImageResource(AppsConstant.getRitualTopImg(ritualImg));
                llTopImg.setBackgroundResource(AppsConstant.getRitualTopImg(ritualImg));
            }
        }
    }


}

