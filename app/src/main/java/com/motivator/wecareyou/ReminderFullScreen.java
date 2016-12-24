package com.motivator.wecareyou;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.alarm.AlarmReceiver;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.model.HabitModel;

public class ReminderFullScreen extends Activity implements OnClickListener{
	
	boolean isSnoozeOptionVisible = false;
	String userName, selectedRitual;
	TextView tvTitle, /*tvDesc,*/ tvList, tvSnooze, tvSnooze1, tvSnooze2, tvSnooze3;;
	ImageView imvPlay, imvClose;
	LinearLayout /*llReminder ,*/llReminderBg;
	GetData getData;
	RelativeLayout rllSnooze;
	ArrayList<HabitModel> userHabitList = new ArrayList<HabitModel>();
	
	MediaPlayer player;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getData = new GetData(ReminderFullScreen.this);
        setContentView(R.layout.reminder_full_screen);
        setBackgroundMusic();
        
        userName = getIntent().getExtras().getString(AppsConstant.user_name); 
        //userName = GeneralUtility.getPreferences(ReminderFullScreen.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        
        initializeUIViews();      
		
        
        userHabitList = getData.getUserHabits(userName, selectedRitual);
        if(userHabitList!=null && userHabitList.size()>0)
        	Reminder.createNotification(ReminderFullScreen.this, selectedRitual, userName);
        
        //UserRitualModel userReminderSetting  = getData.getRitualsDetails(userName, selectedRitual);

        
        if(selectedRitual.equalsIgnoreCase(AppsConstant.MORNING_RITUAL))
        {
        	llReminderBg.setBackgroundResource(R.drawable.feel_energized_bg);
        }
        else if(selectedRitual.equalsIgnoreCase(AppsConstant.LUNCH_RITUAL))
        {
        	llReminderBg.setBackgroundResource(R.drawable.enjoing_healthy_eating_bg);
        }
        else if(selectedRitual.equalsIgnoreCase(AppsConstant.EVENING_RITUAL))
        {
        	llReminderBg.setBackgroundResource(R.drawable.pleasant_night_bg);
        }
        
        tvTitle.setText(userName+", time for your "+selectedRitual);
        
	}

	
	private void initializeUIViews() 
	{
        llReminderBg = (LinearLayout)findViewById(R.id.ll_reminder_bg);
       // llReminder = (LinearLayout)findViewById(R.id.ll_reminder);
        tvTitle= (TextView)findViewById(R.id.tv_reminder_title);
       // tvDesc= (TextView)findViewById(R.id.tv_reminder_desc);
        tvSnooze= (TextView)findViewById(R.id.tv_reminder_snooze);
        imvPlay =  (ImageView)findViewById(R.id.imv_reminder_play);
        imvClose =  (ImageView)findViewById(R.id.imv_close);
        tvList = (TextView)findViewById(R.id.tv_reminder_list);
        
        rllSnooze = (RelativeLayout)findViewById(R.id.rll_snooze);
        tvSnooze1 = (TextView)findViewById(R.id.tv_snooze1);
        tvSnooze2 = (TextView)findViewById(R.id.tv_snooze2);
        tvSnooze3 = (TextView)findViewById(R.id.tv_snooze3);
        
        tvTitle.setTypeface(GeneralUtility.setTypeFace(ReminderFullScreen.this));
       // tvDesc.setTypeface(GeneralUtility.setTypeFace(Reminder.this));
		
        tvList.setTypeface(GeneralUtility.setTypeFace(ReminderFullScreen.this));
		tvSnooze.setTypeface(GeneralUtility.setTypeFace(ReminderFullScreen.this));
		
		//llReminder.setOnClickListener(this);
		tvList.setOnClickListener(this);
		imvPlay.setOnClickListener(this);
		tvSnooze.setOnClickListener(this);
		
		tvSnooze1.setOnClickListener(this);
		tvSnooze2.setOnClickListener(this);
		tvSnooze3.setOnClickListener(this);
		imvClose.setOnClickListener(this);	
	}


	@Override
	public void onClick(View v) 
	{
		Intent i;
		switch (v.getId()) {
		case R.id.imv_close:
			onBackPressed();
			break;
			
		case R.id.rll_reminder:
			stopBackgroundMusic();
			Reminder.showHabitList(ReminderFullScreen.this, selectedRitual);
			break;
			
		case R.id.tv_reminder_snooze:
			stopBackgroundMusic();
			if(!isSnoozeOptionVisible)
			{
				isSnoozeOptionVisible =  true;
				rllSnooze.setVisibility(View.VISIBLE);
			}
			else
			{
				isSnoozeOptionVisible =  false;
				rllSnooze.setVisibility(View.GONE);
			}	
			break;
		
		case R.id.tv_snooze1:
			Reminder.setSnoozeTime(ReminderFullScreen.this, selectedRitual, userName, 1);
			finish();
			Toast.makeText(ReminderFullScreen.this, "Reminder snoozed for next 5 minutes.", 5).show();
			break;
		case R.id.tv_snooze2:
			Reminder.setSnoozeTime(ReminderFullScreen.this, selectedRitual, userName, 2);
			finish();
			Toast.makeText(ReminderFullScreen.this, "Reminder snoozed for next 10 minutes.", 5).show();
			break;
		case R.id.tv_snooze3:
			Reminder.setSnoozeTime(ReminderFullScreen.this, selectedRitual, userName, 3);
			finish();
			Toast.makeText(ReminderFullScreen.this, "Reminder snoozed for next 15 minutes.", 5).show();
			break;

		case R.id.tv_reminder_list:
			stopBackgroundMusic();
			Reminder.showHabitList(ReminderFullScreen.this, selectedRitual);
			break;
		
		case R.id.imv_reminder_play:
			stopBackgroundMusic();
			if(userHabitList!=null && userHabitList.size()>0)
				Reminder.callAVScreen(ReminderFullScreen.this, selectedRitual);
			else
				Reminder.showHabitList(ReminderFullScreen.this, selectedRitual);
			break;
	
		}
		
	}
	
	 private void setBackgroundMusic() 
	 {
		 try {
			 String musicFile = "music/reminder_sound.mp3";
			 // Read the music file from the asset folder
			 AssetFileDescriptor afd = getAssets().openFd(musicFile);
			 //afd = getAssets().openFd(AppsConstant.getHabitMusic(habitId));
			 // Creation of new media player;
			 player = new MediaPlayer();
			 // Set the player music source.
			 player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),afd.getLength());
			 // Set the looping and play the music.
			 player.setLooping(false);
			 player.prepare();
			 player.start();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	 
	 void stopBackgroundMusic()
	 {
		 if(player!=null)
		 {
			 player.stop();
			 player = null;
		 }
	 }
	 
	 @Override
	 protected void onPause() 
	 {
		 super.onPause();
		 if(player!=null && player.isPlaying())
			 player.pause();
	 }
	 
	 @Override
	 protected void onResume() 
	 {
		 super.onResume();
		 if(player!=null)
			 player.start();
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
	 
	 @Override
	public void onBackPressed() {
		super.onBackPressed();
		stopBackgroundMusic();		
	}
	 

}

