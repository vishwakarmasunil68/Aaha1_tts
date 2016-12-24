package com.motivator.alarm;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.model.JourneyData;
import com.motivator.model.UserModel;
import com.motivator.model.UserRitualModel;
import com.motivator.wecareyou.MyHabit_AVScreen;
import com.motivator.wecareyou.OneTimeAction;
import com.motivator.wecareyou.R;
import com.motivator.wecareyou.Reminder;
import com.motivator.wecareyou.ReminderFullScreen;
import com.motivator.wecareyou.RitualSetting;

public class ActionReminderReceiver extends BroadcastReceiver {

	String userName, selectedStep;
	int habitId, actionNum;
	public static Ringtone ringtone;
	
	@Override
	public void onReceive(Context mContext, Intent arg1) 
	{
		userName = arg1.getExtras().getString(AppsConstant.user_name);
		selectedStep = arg1.getExtras().getString(AppsConstant.selected_journey_step);
		habitId = arg1.getExtras().getInt(AppsConstant.h_id);
		actionNum = arg1.getExtras().getInt(OneTimeAction.Action_Num);
		
//        if(ringInSilent ==TableAttributes.ON)
//        {
//        	final AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
//    	    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
//
//    		audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume,AudioManager.FLAG_ALLOW_RINGER_MODES); 
//        }
//        
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//		if (alarmUri == null) {
//			alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//		}
//		ringtone = RingtoneManager.getRingtone(mContext, alarmUri);
//		ringtone.play();
		
		
		showNotification(mContext);
        
	}
	
	
	public void showNotification(Context context) 
	 {
		 Intent intent = new Intent(context, OneTimeAction.class);
		 intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
		 intent.putExtra(AppsConstant.h_id, habitId);
		 intent.putExtra(OneTimeAction.Action_Num, actionNum);
		 intent.putExtra(AppsConstant.calling_frag, "");
		 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		 
		 NotificationCompat.Builder mNotifyBuilder;
		 NotificationManager mNotificationManager;
		 mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		 
		 mNotifyBuilder = new NotificationCompat.Builder(context)
		 	.setContentTitle(JourneyData.getActionTitle(habitId))
		 	//.setContentText(userName+", time for your "+selectedRitual)
		 	.setSmallIcon(R.drawable.remind_me);
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
		 Long tsLong = System.currentTimeMillis()/1000;
		 int notifyID = tsLong.intValue();
		 mNotificationManager.notify(notifyID, mNotifyBuilder.build());
	 }

}