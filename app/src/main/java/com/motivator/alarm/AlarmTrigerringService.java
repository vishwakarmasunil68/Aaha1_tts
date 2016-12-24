package com.motivator.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import com.motivator.common.AppsConstant;

public class AlarmTrigerringService extends Service {

	Intent myIntent;	
	@Override
	public IBinder onBind(Intent arg0) {
		
		myIntent = arg0;
		return null;
	}
	
	@Override
	public void onCreate() {
		
		Context context = AlarmTrigerringService.this;

		//Intent myIntent = getIntent(); // this getter is just for example purpose, can differ
		if (myIntent !=null && myIntent.getExtras()!=null)
		{
			String userName = myIntent.getExtras().getString("user_name");
			String selectedRitual = myIntent.getExtras().getString("selected_ritual");
			int dayOfWeek = myIntent.getExtras().getInt("alarm_day");
			int hourOfDay = myIntent.getExtras().getInt("alarm_hour");
			int minute = myIntent.getExtras().getInt("alarm_minute");
			int rStamp = myIntent.getExtras().getInt("time_stamp");
			boolean isUpdate = myIntent.getExtras().getBoolean("is_update");
			
			
			Calendar now = Calendar.getInstance();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
			cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
			cal.set(Calendar.MINUTE, minute);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
	       
			 long alarmTime = cal.getTimeInMillis();
			 
			
			Intent intent= new Intent(context, AlarmReceiver.class);
	        intent.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
	        intent.putExtra(AppsConstant.user_name, userName);
	        intent.putExtra("alarmTime", "day"+dayOfWeek+" hour"+hourOfDay+":"+minute);
	        PendingIntent alarmIntent;
	        if(!isUpdate)
	        	alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), rStamp, intent, 0/*PendingIntent.FLAG_UPDATE_CURRENT*/);
	        else
	        	alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), rStamp, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	        
	        AlarmManager alarmManager=(AlarmManager)context.getSystemService(context.ALARM_SERVICE);
	        
	      //check whether the time is earlier than current time. 
	        if(cal.before(now))
	        {
	        	alarmTime = cal.getTimeInMillis()+(alarmManager.INTERVAL_DAY * 7);
	        	// Toast.makeText(this, "Alarm Set for day"+dayOfWeek+"in next week :  at"+alarmTime+" MilliSeconds", Toast.LENGTH_LONG).show();
	        }
//	        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent);
	        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime,AlarmManager.INTERVAL_DAY * 7, alarmIntent);
	        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTime, AlarmManager.INTERVAL_DAY * 7, alarmIntent);
	       // Toast.makeText(this, "Alarm Set for day"+dayOfWeek+" at"+alarmTime+" MilliSeconds", Toast.LENGTH_LONG).show();
	        
			
		}
		
		
		
		
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "Music Services Is Stoped...", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Toast.makeText(this, "Music Services Is Started...", Toast.LENGTH_LONG).show();
		
	}
	
	

}
