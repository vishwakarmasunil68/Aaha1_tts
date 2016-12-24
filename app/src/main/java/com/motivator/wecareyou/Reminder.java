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
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.alarm.AlarmReceiver;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.model.HabitModel;

public class Reminder extends Activity implements OnClickListener{
	
	boolean isSnoozeOptionVisible = false;
	String userName, selectedRitual;
	TextView tvTitle, /*tvDesc,*/ tvList, tvSnooze, tvSnooze1, tvSnooze2, tvSnooze3;
	ImageView imvPlay;
	LinearLayout llReminder;
	RelativeLayout rllSnooze;
	GetData getData;
	ArrayList<HabitModel> userHabitList = new ArrayList<HabitModel>();
	
	MediaPlayer player;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getData = new GetData(Reminder.this);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.reminder);
		getWindow().getAttributes().gravity = Gravity.TOP;
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        setBackgroundMusic();
        
        userName = getIntent().getExtras().getString(AppsConstant.user_name); 
        		//GeneralUtility.getPreferences(Reminder.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        
        initializeUIViews();       
       
        
        userHabitList = getData.getUserHabits(userName, selectedRitual);
        if(userHabitList!=null && userHabitList.size()>0)
        	createNotification(Reminder.this, selectedRitual, userName);
        //UserRitualModel userReminderSetting  = getData.getRitualsDetails(userName, selectedRitual);
//        if(userReminderSetting.getNotificationStyle()==TableAttributes.FULL_SCREEN)
//        {
//        	//LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
////        	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
////        			RelativeLayout.LayoutParams.MATCH_PARENT);
////        	//params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
////        	
////        	rllReminder.setLayoutParams(params);
//        	
//        	RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)rllReminder.getLayoutParams();
//        	params.height = LayoutParams.MATCH_PARENT;
//        	rllReminder.requestLayout();
//        }
        
        /*if(selectedRitual.equalsIgnoreCase(AppsContant.MORNING_RITUAL))
        {
        	llReminder.setBackgroundResource(R.drawable.morning_ritual);
        }
        else if(selectedRitual.equalsIgnoreCase(AppsContant.LUNCH_RITUAL))
        {
        	llReminder.setBackgroundResource(R.drawable.after_ritual);
        }
        else if(selectedRitual.equalsIgnoreCase(AppsContant.EVENING_RITUAL))
        {
        	llReminder.setBackgroundResource(R.drawable.evening_ritual);
        }*/
		
        tvTitle.setText(userName+", time for your "+selectedRitual);
        
	}

	 
	 
	private void initializeUIViews() 
	{
		llReminder = (LinearLayout)findViewById(R.id.ll_reminder);
		tvTitle= (TextView)findViewById(R.id.tv_reminder_title);
		// tvDesc= (TextView)findViewById(R.id.tv_reminder_desc);
		tvSnooze= (TextView)findViewById(R.id.tv_reminder_snooze);
		imvPlay =  (ImageView)findViewById(R.id.imv_reminder_play);
		tvList = (TextView)findViewById(R.id.tv_reminder_list);
		
		rllSnooze = (RelativeLayout)findViewById(R.id.rll_snooze);
		tvSnooze1 = (TextView)findViewById(R.id.tv_snooze1);
		tvSnooze2 = (TextView)findViewById(R.id.tv_snooze2);
		tvSnooze3 = (TextView)findViewById(R.id.tv_snooze3);
		
		tvTitle.setTypeface(GeneralUtility.setTypeFace(Reminder.this));
		// tvDesc.setTypeface(GeneralUtility.setTypeFace(Reminder.this));
			
		tvList.setTypeface(GeneralUtility.setTypeFace(Reminder.this));
		tvSnooze.setTypeface(GeneralUtility.setTypeFace(Reminder.this));
		
		llReminder.setOnClickListener(this);
		tvList.setOnClickListener(this);
		imvPlay.setOnClickListener(this);
		tvSnooze.setOnClickListener(this);
		tvSnooze1.setOnClickListener(this);
		tvSnooze2.setOnClickListener(this);
		tvSnooze3.setOnClickListener(this);
			
	}



	@Override
	public void onClick(View v) 
	{
		Intent i;
		switch (v.getId()) {
		case R.id.rll_reminder:
			stopBackgroundMusic();
			showHabitList(Reminder.this, selectedRitual);
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
			setSnoozeTime(Reminder.this, selectedRitual, userName, 1);
			finish();
			Toast.makeText(Reminder.this, "Reminder snoozed for next 5 minutes.", 5).show();
			break;
		case R.id.tv_snooze2:
			setSnoozeTime(Reminder.this, selectedRitual, userName, 2);
			finish();
			Toast.makeText(Reminder.this, "Reminder snoozed for next 10 minutes.", 5).show();
			break;
		case R.id.tv_snooze3:
			setSnoozeTime(Reminder.this, selectedRitual, userName, 1);
			finish();
			Toast.makeText(Reminder.this, "Reminder snoozed for next 15 minutes.", 5).show();
			break;

		case R.id.tv_reminder_list:
			stopBackgroundMusic();
			showHabitList(Reminder.this, selectedRitual);
			break;
		case R.id.imv_reminder_play:
			stopBackgroundMusic();
			if(userHabitList!=null && userHabitList.size()>0)
				callAVScreen(Reminder.this, selectedRitual);
			else
				showHabitList(Reminder.this, selectedRitual);
			break;
	
		}
		
	}

	public static void  showHabitList(Activity mActivity, String selectedRitual) 
	{
		Intent i = new Intent(mActivity, MyHabits.class);
		i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
		i.putExtra("is_reminder_call", true);
		mActivity.startActivity(i);
		mActivity.finish();
	}
	
	public static void  callAVScreen(Activity mActivity, String selectedRitual) 
	{
		Intent i = new Intent(mActivity, MyHabit_AVScreen.class);
		i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
		i.putExtra("isFirstWalkthrough", false);
		i.putExtra("is_reminder_call", true);
		i.putExtra("position", 0);
		mActivity.startActivity(i);
		mActivity.finish();
	}

	 /**
	  * set snooze time
	  * @param mContext
	  * @param selectedRitual
	  * @param userName
	  * @param snoozeTime
	  */
	 public static void setSnoozeTime(Context mContext, String selectedRitual, String userName,int snoozeTime) 
	 {
		 long alarmTime = System.currentTimeMillis();
		 switch (snoozeTime) {
		 case 1:
			 alarmTime = System.currentTimeMillis()+5*60*1000;
			 break;
		 case 2:
			 alarmTime = System.currentTimeMillis()+10*60*1000;
			 break;
		 case 3:
			 alarmTime = System.currentTimeMillis()+AlarmManager.INTERVAL_FIFTEEN_MINUTES;
			 break;

		 }
			
		 Intent intent= new Intent(mContext, AlarmReceiver.class);
		 intent.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
		 intent.putExtra(AppsConstant.user_name, userName);
	        
		 Long tsLong = System.currentTimeMillis()/1000;
		 int reqCode = tsLong.intValue();
		 PendingIntent alarmIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), reqCode, intent, 0);
		 AlarmManager alarmManager=(AlarmManager)mContext.getSystemService(mContext.ALARM_SERVICE);
		 alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent);
	 }
	 
	 /**
	  * Create notification on reminder
	  * @param mContext
	  * @param selectedRitual
	  * @param userName
	  */
	 public static void createNotification(Context mContext, String selectedRitual, String userName) 
	 {
		 Intent intent = new Intent(mContext, MyHabit_AVScreen.class);
		 intent.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
		 intent.putExtra("isFirstWalkthrough", false);
		 intent.putExtra("position", 0);
		 intent.putExtra("is_reminder_call", true);
		 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 Long tsLong = System.currentTimeMillis()/1000;
		 int timeStamp = tsLong.intValue();
		 PendingIntent pendingIntent = PendingIntent.getActivity(mContext, timeStamp, intent, PendingIntent.FLAG_ONE_SHOT);
		 
		 NotificationCompat.Builder mNotifyBuilder;
		 NotificationManager mNotificationManager;
		 mNotificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		 
		 mNotifyBuilder = new NotificationCompat.Builder(mContext)
		 	.setContentTitle(mContext.getResources().getString(R.string.app_name))
		 	.setContentText(userName+", time for your "+selectedRitual)
		 	.setSmallIcon(R.drawable.app_icon);
	        // Set pending intent
		 mNotifyBuilder.setContentIntent(pendingIntent);
	        
		 // Set Vibrate, Sound and Light	        
		 int defaults = 0;
		 defaults = defaults | Notification.DEFAULT_LIGHTS;
		 defaults = defaults | Notification.DEFAULT_VIBRATE;
		 defaults = defaults | Notification.DEFAULT_SOUND;
	        
		 mNotifyBuilder.setDefaults(defaults);
		// mNotifyBuilder.setContentText(msg);
		 // Set autocancel
		 mNotifyBuilder.setAutoCancel(true);
		 // Post a notification
		 tsLong = System.currentTimeMillis()/1000;
		 int notifyID = tsLong.intValue();
		 mNotificationManager.notify(notifyID, mNotifyBuilder.build());
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
	 
	 void stopBackgroundMusic()
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
	 
	 @Override
	public void onBackPressed() {
		super.onBackPressed();
		stopBackgroundMusic();		
	}
	 

}

