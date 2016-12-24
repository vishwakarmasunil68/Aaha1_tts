package com.motivator.alarm;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.model.ReminderDescModel;
import com.motivator.model.ReminderModel;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in the
 * application's manifest file. When the user sets the alarm, the receiver is enabled.
 * When the user cancels the alarm, the receiver is disabled, so that rebooting the
 * device will not trigger this receiver.
 */
// BEGIN_INCLUDE(autostart)
public class SampleBootReceiver extends BroadcastReceiver 
{

	GetData getData;
	ArrayList<ReminderModel> reminderList = new ArrayList<ReminderModel>();
	
	@Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
        	//getDAta from shared prefrence
            String userName = GeneralUtility.getPreferences(context, AppsConstant.user_name);
            
            getData = new GetData(context);
        	reminderList  = getData.getUserReminder(userName);
        	
        	for(int i=0; i<reminderList.size(); i++)
        	{
        		String remTime = reminderList.get(i).getReminderTime();
        		ReminderDescModel remDesc = reminderList.get(i).getReminderDesc();
        		
        		remTime = DateUtility.getTimeIn24Format(remTime);
    			
    			String t[] = remTime.split(":");
    			int hourOfDay = Integer.parseInt(t[0]);
    			int minute = Integer.parseInt(t[1]);
    			
        		int rStamp = remDesc.getStampSun();
    			if(rStamp!= -1)
    			{
    				GeneralUtility.setAlarmTime(context, userName, reminderList.get(i).getRitualType(),Calendar.SUNDAY,hourOfDay, minute,rStamp, false);
    			}
    			rStamp = remDesc.getStampMon();
    			if(rStamp!= -1)
    			{
    				GeneralUtility.setAlarmTime(context, userName, reminderList.get(i).getRitualType(),Calendar.MONDAY,hourOfDay, minute,rStamp, false);
    			}
    			rStamp = remDesc.getStampTue();
    			if(rStamp!= -1)
    			{
    				GeneralUtility.setAlarmTime(context, userName, reminderList.get(i).getRitualType(),Calendar.TUESDAY,hourOfDay, minute,rStamp, false);
    			}
    			rStamp = remDesc.getStampWed();
    			if(rStamp!= -1)
    			{
    				GeneralUtility.setAlarmTime(context, userName, reminderList.get(i).getRitualType(),Calendar.WEDNESDAY,hourOfDay, minute,rStamp, false);
    			}
    			rStamp = remDesc.getStampThu();
    			if(rStamp!= -1)
    			{
    				GeneralUtility.setAlarmTime(context, userName, reminderList.get(i).getRitualType(),Calendar.THURSDAY,hourOfDay, minute,rStamp, false);
    			}
    			rStamp = remDesc.getStampFri();
    			if(rStamp!= -1)
    			{
    				GeneralUtility.setAlarmTime(context, userName, reminderList.get(i).getRitualType(),Calendar.FRIDAY,hourOfDay, minute,rStamp, false);
    			}
    			rStamp = remDesc.getStampSat();
    			if(rStamp!= -1)
    			{
    				GeneralUtility.setAlarmTime(context, userName, reminderList.get(i).getRitualType(),Calendar.SATURDAY,hourOfDay, minute,rStamp, false);
    			}
        	}
        }
    }
}
//END_INCLUDE(autostart)
