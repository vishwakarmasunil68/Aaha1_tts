package com.motivator.wecareyou.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.database.DeleteData;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.ReminderDescModel;
import com.motivator.model.ReminderModel;
import com.motivator.wecareyou.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class AlarmFragment extends Fragment implements OnClickListener, TimePickerDialog.OnTimeSetListener{
	
	
	Context mContext;
	com.motivator.support.NonScrollableListView lvFragment;
	public static final  String ALARM_ARG = "time_arg";
	String userName, selectedRitual;
	
	Random r;
	GetData getData;
	UpdateData update;
	ArrayList<ReminderModel> reminderList = new ArrayList<ReminderModel>();
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	
    	mContext = getActivity();
    	getData = new GetData(getActivity());
    	update = new UpdateData(getActivity());
    	
    	r  = new Random();
    	//getDAta from shared prefrence
        userName = GeneralUtility.getPreferences(getActivity(), AppsConstant.user_name);
        selectedRitual = getArguments().getString(AppsConstant.SELECTED_RITUAL);
        // Inflate the layout for this fragment
    	View view = inflater.inflate(R.layout.frag_list , container, false);

    	//time = getArguments().getString(ALARM_ARG);

    	lvFragment = (com.motivator.support.NonScrollableListView)view.findViewById(R.id.lv_frag);

    	reminderList  = getData.getUserReminder(userName, selectedRitual);

    	if(reminderList!=null && reminderList.size()>0)
        {
        	lvFragment.setAdapter(new ReminderAdapter());
        }

    	return view;
    }


    class ReminderAdapter extends BaseAdapter
    {
		@Override
		public int getCount() {
			return reminderList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = getActivity().getLayoutInflater();
			convertView = inflater.inflate(R.layout.alarm_frag, null);

			TextView tvAlarmTime;
			ImageView imvRemoveAlarm;
			TextView tvMon, tvTue, tvWed, tvThu, tvFri, tvSat, tvSun;

			tvAlarmTime = (TextView)convertView.findViewById(R.id.tv_alarm_time);
	    	imvRemoveAlarm = (ImageView)convertView.findViewById(R.id.imv_remove_alarm);

	    	tvMon = (TextView)convertView.findViewById(R.id.tv_mon);
	    	tvTue = (TextView)convertView.findViewById(R.id.tv_tue);
	    	tvWed = (TextView)convertView.findViewById(R.id.tv_wed);
	    	tvThu = (TextView)convertView.findViewById(R.id.tv_thu);
	    	tvFri = (TextView)convertView.findViewById(R.id.tv_fri);
	    	tvSat = (TextView)convertView.findViewById(R.id.tv_sat);
	    	tvSun = (TextView)convertView.findViewById(R.id.tv_sun);


	    	tvAlarmTime.setText(reminderList.get(position).getReminderTime());
	    	if(reminderList.get(position).getReminderDesc().getReminder_sun()== TableAttributes.ON)
	    		tvSun.setBackgroundResource(R.drawable.day_bg1);
	    	else
	    		tvSun.setBackgroundResource(R.drawable.day_unselected);
	    	if(reminderList.get(position).getReminderDesc().getReminder_mon()== TableAttributes.ON)
	    		tvMon.setBackgroundResource(R.drawable.day_bg1);
	    	else
	    		tvMon.setBackgroundResource(R.drawable.day_unselected);
	    	if(reminderList.get(position).getReminderDesc().getReminder_tue()== TableAttributes.ON)
	    		tvTue.setBackgroundResource(R.drawable.day_bg1);
	    	else
	    		tvTue.setBackgroundResource(R.drawable.day_unselected);

	    	if(reminderList.get(position).getReminderDesc().getReminder_wed()== TableAttributes.ON)
	    		tvWed.setBackgroundResource(R.drawable.day_bg1);
	    	else
	    		tvWed.setBackgroundResource(R.drawable.day_unselected);
	    	if(reminderList.get(position).getReminderDesc().getReminder_thu()== TableAttributes.ON)
	    		tvThu.setBackgroundResource(R.drawable.day_bg1);
	    	else
	    		tvThu.setBackgroundResource(R.drawable.day_unselected);

	    	if(reminderList.get(position).getReminderDesc().getReminder_fri()== TableAttributes.ON)
	    		tvFri.setBackgroundResource(R.drawable.day_bg1);
	    	else
	    		tvFri.setBackgroundResource(R.drawable.day_unselected);
	    	if(reminderList.get(position).getReminderDesc().getReminder_sat()== TableAttributes.ON)
	    		tvSat.setBackgroundResource(R.drawable.day_bg1);
	    	else
	    		tvSat.setBackgroundResource(R.drawable.day_unselected);

	    	imvRemoveAlarm.setTag(position);	    
			tvMon.setTag(position);	    	
			tvTue.setTag(position);	 
			tvWed.setTag(position);	 
			tvThu.setTag(position);	 
			tvFri.setTag(position);	 
			tvSat.setTag(position);	 
			tvSun.setTag(position);	 
			
			tvMon.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) 
				{
					int alarmMon = 0;
					int select = (Integer) v.getTag();
					int r_id = reminderList.get(select).getReminderId();
					int remONOFF = reminderList.get(select).getReminderDesc().getReminder_mon();
					int rStamp = reminderList.get(select).getReminderDesc().getStampMon();
					
					if (remONOFF == TableAttributes.OFF)
					{
						Long tsLong = System.currentTimeMillis()/1000;
						int timeStamp = tsLong.intValue()+r.nextInt(100);					
						alarmMon = update.updateReminder(r_id, timeStamp, TableAttributes.REMINDER_MON, TableAttributes.ON);
						updateAlarm(Calendar.MONDAY, timeStamp, reminderList.get(select).getReminderTime());
						if(alarmMon >0)
						{
							reminderList.get(select).getReminderDesc().setReminder_mon(TableAttributes.ON);
						}
					}
					else 
					{
						alarmMon = update.updateReminder(r_id, -1, TableAttributes.REMINDER_MON, TableAttributes.OFF);
						GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
						
						if(alarmMon >0)
						{
							reminderList.get(select).getReminderDesc().setReminder_mon(TableAttributes.OFF);
							Toast.makeText(getActivity(), "Alarm Removed", 5).show();	
						}
						
					}
					notifyDataSetChanged();
				}
			});
	 
			tvTue.setOnClickListener(new OnClickListener() {
							
				@Override
				public void onClick(View v) 
				{
					int alarmTue = 0;
					int select = (Integer) v.getTag();
					int r_id = reminderList.get(select).getReminderId();
					int remONOFF = reminderList.get(select).getReminderDesc().getReminder_tue();
					int rStamp = reminderList.get(select).getReminderDesc().getStampTue();
					
					if (remONOFF == TableAttributes.OFF)
					{
						Long tsLong = System.currentTimeMillis()/1000;
						int timeStamp = tsLong.intValue()+r.nextInt(100);
						alarmTue = update.updateReminder(r_id,timeStamp, TableAttributes.REMINDER_TUE, TableAttributes.ON);
						updateAlarm(Calendar.TUESDAY, timeStamp, reminderList.get(select).getReminderTime());
						if(alarmTue>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_tue(TableAttributes.ON);
						}
					}
					else 
					{
						alarmTue = update.updateReminder(r_id, -1, TableAttributes.REMINDER_TUE, TableAttributes.OFF);
						GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
						if(alarmTue>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_tue(TableAttributes.OFF);
							Toast.makeText(getActivity(), "Alarm Removed", 5).show();
						}
					}
					notifyDataSetChanged();
				}
			});
			tvWed.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					int alarmWed = 0;
					int select = (Integer) v.getTag();
					int r_id = reminderList.get(select).getReminderId();
					int remONOFF = reminderList.get(select).getReminderDesc().getReminder_wed();
					int rStamp = reminderList.get(select).getReminderDesc().getStampWed();
					
					if (remONOFF == TableAttributes.OFF)
					{
						Long tsLong = System.currentTimeMillis()/1000;
						int timeStamp = tsLong.intValue()+r.nextInt(100);
						alarmWed = update.updateReminder(r_id,timeStamp, TableAttributes.REMINDER_WED, TableAttributes.ON);
						updateAlarm(Calendar.WEDNESDAY, timeStamp, reminderList.get(select).getReminderTime());
						if(alarmWed>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_wed(TableAttributes.ON);
						}
					}
					else
					{						
						alarmWed = update.updateReminder(r_id, -1, TableAttributes.REMINDER_WED, TableAttributes.OFF);
						GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
						if(alarmWed>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_wed(TableAttributes.OFF);
							Toast.makeText(getActivity(), "Alarm Removed", 5).show();
						}
					}
					notifyDataSetChanged();
				}
			});
			tvThu.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					int alarmThu = 0;
					int select = (Integer) v.getTag();
					int r_id = reminderList.get(select).getReminderId();
					int remONOFF = reminderList.get(select).getReminderDesc().getReminder_thu();
					int rStamp = reminderList.get(select).getReminderDesc().getStampThu();
					
					if (remONOFF == TableAttributes.OFF)
					{
						Long tsLong = System.currentTimeMillis()/1000;
						int timeStamp = tsLong.intValue()+r.nextInt(100);
						
						alarmThu = update.updateReminder(r_id, timeStamp, TableAttributes.REMINDER_THU, TableAttributes.ON);
						updateAlarm(Calendar.THURSDAY, timeStamp, reminderList.get(select).getReminderTime());
						if(alarmThu>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_thu(TableAttributes.ON);
						}
					}
					else 
					{
						alarmThu = update.updateReminder(r_id, -1, TableAttributes.REMINDER_THU, TableAttributes.OFF);
						GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
						if(alarmThu>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_thu(TableAttributes.OFF);
							Toast.makeText(getActivity(), "Alarm Removed", 5).show();
						}
					}
					notifyDataSetChanged();
				}
			});
			tvFri.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					int alarmFri = 0;
					int select = (Integer) v.getTag();
					int r_id = reminderList.get(select).getReminderId();
					int remONOFF = reminderList.get(select).getReminderDesc().getReminder_fri();
					int rStamp = reminderList.get(select).getReminderDesc().getStampFri();
					
					if (remONOFF == TableAttributes.OFF)
					{
						Long tsLong = System.currentTimeMillis()/1000;
						int timeStamp = tsLong.intValue()+r.nextInt(100);
						alarmFri = update.updateReminder(r_id,timeStamp, TableAttributes.REMINDER_FRI, TableAttributes.ON);
						updateAlarm(Calendar.FRIDAY, timeStamp, reminderList.get(select).getReminderTime());
						
						if(alarmFri>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_fri(TableAttributes.ON);
						}
					}
					else 
					{
						alarmFri = update.updateReminder(r_id,-1, TableAttributes.REMINDER_FRI, TableAttributes.OFF);
						GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
						if(alarmFri>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_fri(TableAttributes.OFF);
							Toast.makeText(getActivity(), "Alarm Removed", 5).show();
						}
					}
					notifyDataSetChanged();
				}
			});
			tvSat.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					int alarmSat = 0;
					int select = (Integer) v.getTag();
					int r_id = reminderList.get(select).getReminderId();
					int remONOFF = reminderList.get(select).getReminderDesc().getReminder_sat();
					int rStamp = reminderList.get(select).getReminderDesc().getStampSat();
					
					if (remONOFF == TableAttributes.OFF)
					{
						Long tsLong = System.currentTimeMillis()/1000;
						int timeStamp = tsLong.intValue()+r.nextInt(100);
						alarmSat = update.updateReminder(r_id,timeStamp, TableAttributes.REMINDER_SAT, TableAttributes.ON);
						updateAlarm(Calendar.SATURDAY, timeStamp, reminderList.get(select).getReminderTime());
						if(alarmSat>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_sat(TableAttributes.ON);
						}
					}
					else 
					{
						alarmSat = update.updateReminder(r_id, -1, TableAttributes.REMINDER_SAT, TableAttributes.OFF);
						GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
						if(alarmSat>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_sat(TableAttributes.OFF);
							Toast.makeText(getActivity(), "Alarm Removed", 5).show();
						}
					}
					notifyDataSetChanged();
				}
			});
			
			tvSun.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) 
				{
					int alarmSun = 0;
					int select = (Integer) v.getTag();
					int r_id = reminderList.get(select).getReminderId();
					int remONOFF = reminderList.get(select).getReminderDesc().getReminder_sun();
					int rStamp = reminderList.get(select).getReminderDesc().getStampSun();
					
					if (remONOFF == TableAttributes.OFF)
					{
						Long tsLong = System.currentTimeMillis()/1000;
						int timeStamp = tsLong.intValue()+r.nextInt(100);
						alarmSun = update.updateReminder(r_id,timeStamp, TableAttributes.REMINDER_SUN, TableAttributes.ON);
						updateAlarm(Calendar.SUNDAY, timeStamp, reminderList.get(select).getReminderTime());
						if(alarmSun>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_sun(TableAttributes.ON);
						}
					}
					else 
					{
						alarmSun = update.updateReminder(r_id, -1, TableAttributes.REMINDER_SUN, TableAttributes.OFF);
						GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
						
						if(alarmSun>0)
						{
							reminderList.get(select).getReminderDesc().setReminder_sun(TableAttributes.OFF);
							Toast.makeText(getActivity(), "Alarm Removed", 5).show();
						}
					}
					notifyDataSetChanged();
				}
			});
	    	imvRemoveAlarm.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					int select = (Integer) v.getTag();
					DeleteData deleteData = new DeleteData(getActivity());
					int rem_id = reminderList.get(select).getReminderId();
					int del = deleteData.removeAlarm(rem_id);
					if(del>0)
					{
						deleteReminder(select);
						notifyDataSetChanged();
						Toast.makeText(getActivity(), "Reminder Removed", 5).show();	
					}
				}

			});
	    	
			return convertView;
		}

		private void deleteReminder(int select) 
		{
			ReminderDescModel remDesc = reminderList.get(select).getReminderDesc();
			int rStamp = remDesc.getStampSun();
			if(rStamp!= -1)
			{
				GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
			}
			rStamp = remDesc.getStampMon();
			if(rStamp!= -1)
			{
				GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
			}
			rStamp = remDesc.getStampTue();
			if(rStamp!= -1)
			{
				GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
			}
			rStamp = remDesc.getStampWed();
			if(rStamp!= -1)
			{
				GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
			}
			rStamp = remDesc.getStampThu();
			if(rStamp!= -1)
			{
				GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
			}
			rStamp = remDesc.getStampFri();
			if(rStamp!= -1)
			{
				GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
			}
			rStamp = remDesc.getStampSat();
			if(rStamp!= -1)
			{
				GeneralUtility.removeAlarmForADay(getActivity(), rStamp);
			}
			
			reminderList  = getData.getUserReminder(userName, selectedRitual);
			if(reminderList.size()>0)
			{
				int last = reminderList.size()-1;
				if(select >= last)
				{
					update.updateUserRitualTime(userName, selectedRitual, reminderList.get(last).getReminderTime(), reminderList.get(last).getReminderId());
				}
			}
			else
				update.updateRitualReminder(userName, selectedRitual);
			
			//Toast.makeText(getActivity(), "Alarm Removed", 5).show();
		}

		protected void updateAlarm(int dayOfWeek, int r_stamp, String remTime) 
		{
			remTime = DateUtility.getTimeIn24Format(remTime);
			
			String t[] = remTime.split(":");
			int hourOfDay = Integer.parseInt(t[0]);
			int minute = Integer.parseInt(t[1]);
			
			GeneralUtility.setAlarmTime(getActivity(), userName, selectedRitual,dayOfWeek,hourOfDay, minute,r_stamp, false);
			
		}
    	
    }
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		/*case R.id.tv_alarm_time:
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		    Date dateObj = null;
		    try {
		        dateObj = sdf.parse(time);
		    } catch (ParseException e) {
		    }
			Calendar now = Calendar.getInstance();
			now.setTime(dateObj);
	        TimePickerDialog tpd = TimePickerDialog.newInstance(this,
	                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE), false);
	        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
	            @Override
	            public void onCancel(DialogInterface dialogInterface) {
	                Log.d("TimePicker", "Dialog was cancelled");
	            }
	        });
	        tpd.show(getFragmentManager(), "Timepickerdialog");
			break;
		case R.id.imv_remove_alarm:
			
			break;
		*/
		}

	}
    
	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second)
	 {
		 String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
		 String minuteString = minute < 10 ? "0"+minute : ""+minute;
		 String time = hourString+":"+minuteString+"m";

		 time = DateUtility.getTimeIn12Format(time);
		 if(time.startsWith("0:"))
				time= time.replace("0:", "12:");
//		 addTimeToDatabase(time);
		 
	 }

}