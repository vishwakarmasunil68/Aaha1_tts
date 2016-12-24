package com.motivator.alarm;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.motivator.common.AppsConstant;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.model.UserRitualModel;
import com.motivator.wecareyou.Reminder;
import com.motivator.wecareyou.ReminderFullScreen;

public class AlarmReceiver extends BroadcastReceiver {

	String h_id, habit, habit_desc, habit_time;
	//public static Ringtone ringtone;
	
	@Override
	public void onReceive(Context mContext, Intent arg1) 
	{
		String selectedRitual = arg1.getExtras().getString(AppsConstant.SELECTED_RITUAL);
//		String alarmTime = arg1.getExtras().getString("alarmTime");
//		Toast.makeText(mContext, alarmTime, 3).show();
		String userName = arg1.getExtras().getString(AppsConstant.user_name);
		
		// Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(mContext, SampleBootReceiver.class);
        PackageManager pm = mContext.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP); 
        
        //get data from database		
		GetData getData = new GetData(mContext);
		UserRitualModel userReminderSetting  = getData.getRitualsDetails(userName, selectedRitual);
		int isfullScreen = userReminderSetting.getNotificationStyle();
		int ringInSilent = userReminderSetting.getRingInSilent();
	        
        if(ringInSilent ==TableAttributes.ON)
        {
        	final AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    	    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);

    		audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume,AudioManager.FLAG_ALLOW_RINGER_MODES); 
        }
        
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//		if (alarmUri == null) {
//			alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//		}
//		ringtone = RingtoneManager.getRingtone(mContext, alarmUri);
//		ringtone.play();
		
		
		Intent i;
        if(isfullScreen==TableAttributes.ON)
        {
	    	  i = new Intent(mContext, ReminderFullScreen.class);
        }
        else
        {
        	i = new Intent(mContext, Reminder.class);  
        }
       // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
        i.putExtra(AppsConstant.user_name, userName);
	       
        mContext.getApplicationContext().startActivity(i);
        
	}
	

}