package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.PutData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.HabitModel;
import com.motivator.support.InteractiveScrollView;
import com.motivator.wecareyou.fragment.AlarmFragment;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class NewRitual extends Activity implements OnClickListener,  TimePickerDialog.OnTimeSetListener{
	
	LinearLayout frameAlarm;
	Fragment fragmentAlarm;
	TextView tvAddAlarm;
	ImageView imvFullScreen, imvSimple, imvFullSelect, imvSimpleSelect;
	CheckBox /*chkUrgencySwipe,*/ chkAnnounce, chkRing;
	EditText edtRitualName;
	ImageView /*imvUrgencySwipe,*/ imvAnnounce, imvRing, imvRitualImg;
	
	int isFullScreen = TableAttributes.OFF;
	String userName;
	int ritualImg = 1;
	String newRitualName = "";
	String time = "";
	int remID = 0;
	PutData putData;
	UpdateData updateData;
	ArrayList<HabitModel> userHabit = new ArrayList<HabitModel>();
	InteractiveScrollView scroll;
	FloatingActionButton fab;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        putData = new PutData(NewRitual.this);
        updateData = new UpdateData(NewRitual.this);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        
        setContentView(R.layout.new_ritual);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setTitle("New Ritual");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        
        SpannableString s = new SpannableString("new ritual");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        actionBar.setTitle(s);
        
      //getDAta from shared prefrence
        userName = GeneralUtility.getPreferences(NewRitual.this, AppsConstant.user_name);
        
        intializeUIViews();


       
	}

	private void intializeUIViews() 
	{
		frameAlarm = (LinearLayout)findViewById(R.id.frame_alarm);
		
		tvAddAlarm = (TextView)findViewById(R.id.tv_add_alarm);
        imvFullScreen= (ImageView)findViewById(R.id.imv_full_screen);
        imvSimple= (ImageView)findViewById(R.id.imv_simple);
        imvFullSelect= (ImageView)findViewById(R.id.imv_full_select);
        imvSimpleSelect= (ImageView)findViewById(R.id.imv_simple_select);
        
        //imvUrgencySwipe= (ImageView)findViewById(R.id.imv_urgency_swipe);
        imvAnnounce= (ImageView)findViewById(R.id.imv_announce_first_habit);
        imvRing= (ImageView)findViewById(R.id.imv_ring_in_silent);
        
        edtRitualName = (EditText)findViewById(R.id.edt_ritual_name);
        imvRitualImg = (ImageView)findViewById(R.id.imv_ritual_image);
        
        //chkUrgencySwipe = (CheckBox)findViewById(R.id.chk_urgency_swipe);
        chkAnnounce = (CheckBox)findViewById(R.id.chk_announce_first_habit);
        chkRing = (CheckBox)findViewById(R.id.chk_ring_in_silent);

		scroll= (InteractiveScrollView) findViewById(R.id.scroll);
		fab= (FloatingActionButton) findViewById(R.id.fab);
        
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
        
	}

	private void addAlarmFragment() 
	{
		fragmentAlarm = new AlarmFragment();
		Bundle args = new Bundle();
		//args.putString(AlarmFragment.ALARM_ARG, time);
		args.putString(AppsConstant.SELECTED_RITUAL, newRitualName);
		fragmentAlarm.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.frame_alarm, fragmentAlarm).commit();
		
	}
	
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.top_menu, menu);
//        MenuItem switchButton = menu.findItem(R.id.action);
//        switchButton.setIcon(R.drawable.right_green);
//        return true;
//    }

	@Override
	protected void onResume() {
		super.onResume();
		fab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				newRitualName = edtRitualName.getText().toString();
				if(newRitualName!=null && newRitualName.length()>0)
				{
					int announce = TableAttributes.OFF, ring =TableAttributes.OFF;
					if(chkAnnounce.isChecked())
						announce = TableAttributes.ON;
					if(chkRing.isChecked())
						ring = TableAttributes.ON;
					if(time.length()<=0)
					{
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
							time = sdf.format(new Date());
						}
						catch (Exception e) {
							time= "11:00 AM";
							e.printStackTrace();
						}
					}

					long row = putData.addUserRitual(userName, ritualImg, newRitualName,newRitualName,
							time, isFullScreen,announce,ring, remID);
					if(row>0)
					{
//						finish();
						Toast.makeText(NewRitual.this, "New Ritual Added", 5).show();

						Intent returnIntent = new Intent();
						returnIntent.putExtra("result","added");
						setResult(Activity.RESULT_OK,returnIntent);
						finish();
					}
					else{
						Intent returnIntent = new Intent();
						returnIntent.putExtra("result","notadded");
						setResult(Activity.RESULT_OK,returnIntent);
						finish();
					}
				}
				else
				{
					Toast.makeText(NewRitual.this, R.string.enter_ritual_name, 5).show();
				}
			}
		});
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	switch (item.getItemId()) 
        {
        	case android.R.id.home:
        		onBackPressed();
        		return true;
            
        	case R.id.action:
        		newRitualName = edtRitualName.getText().toString();
    			if(newRitualName!=null && newRitualName.length()>0)
    			{
    				int announce = TableAttributes.OFF, ring =TableAttributes.OFF;
            		if(chkAnnounce.isChecked())
            			announce = TableAttributes.ON;
            		if(chkRing.isChecked())
            			ring = TableAttributes.ON;
            		if(time.length()<=0)
            		{
            			try {
               			 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a"); 
               			 time = sdf.format(new Date());
               		 } 
               		 catch (Exception e) {
               			time= "11:00 AM";
               			e.printStackTrace();
               		 }
            		}
            		
            		long row = putData.addUserRitual(userName, ritualImg, newRitualName,newRitualName,
            					time, isFullScreen,announce,ring, remID);
            		if(row>0)
            		{
            			finish();
            			Toast.makeText(NewRitual.this, "New Ritual Added", 5).show();
            		}
    			}
    			else
    			{
    				Toast.makeText(NewRitual.this, R.string.enter_ritual_name, 5).show();
    			}
    			return true;
        }

    	return super.onOptionsItemSelected(item);
    }
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.tv_add_alarm:
			newRitualName = edtRitualName.getText().toString();
			if(newRitualName!=null && newRitualName.length()>0)
			{
				Calendar cal = Calendar.getInstance();
		        TimePickerDialog tpd = TimePickerDialog.newInstance(NewRitual.this,
		                cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE), false);
		        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
		            @Override
		            public void onCancel(DialogInterface dialogInterface) {
		                Log.d("TimePicker", "Dialog was cancelled");
		            }
		        });
		        tpd.show(getFragmentManager(), "Timepickerdialog");
			}
			else
				Toast.makeText(NewRitual.this, R.string.enter_ritual_name, 5).show();
			break;
		case R.id.imv_full_screen:
			setFullScreenNotification();
			break;

		case R.id.imv_simple:
			setSimpleScreenNotification();
			break;

		case R.id.imv_full_select:
			setFullScreenNotification();
			break;

		case R.id.imv_simple_select:
			setSimpleScreenNotification();
			break;

//		case R.id.imv_urgency_swipe:
//			GeneralUtility.PopUpInfo(NewRitual.this, R.string.urgency_swipe, R.string.urgency_swipe_msg);
//			break;

		case R.id.imv_announce_first_habit:
			GeneralUtility.PopUpInfo(NewRitual.this, R.string.announcement, R.string.announcement_msg);
			break;

		case R.id.imv_ring_in_silent:
			GeneralUtility.PopUpInfo(NewRitual.this, R.string.ring_in_silent, R.string.ring_in_silent_msg);
			break;
			
		case R.id.imv_ritual_image:
			newRitualName = edtRitualName.getText().toString();
			if(newRitualName!=null && newRitualName.length()>0)
			{
				Intent i = new Intent(NewRitual.this, RitualImageUpdate.class);
				i.putExtra(AppsConstant.SELECTED_RITUAL, newRitualName);
				i.putExtra("new_name", newRitualName);
				startActivityForResult(i, AppsConstant.RITUAL_IMG);
			}
			else
			{
				Toast.makeText(NewRitual.this, R.string.enter_ritual_name, 5).show();
			}
			break;
		}

	}

	private void setFullScreenNotification() 
	{
		isFullScreen = TableAttributes.ON;
		imvFullScreen.setImageResource(R.drawable.simple);
		imvFullSelect.setImageResource(R.drawable.selected);
		imvSimple.setImageResource(R.drawable.full_screen);
		imvSimpleSelect.setImageResource(R.drawable.not_selected);
	}
	
	private void setSimpleScreenNotification() 
	{
		isFullScreen = TableAttributes.OFF;
		imvFullScreen.setImageResource(R.drawable.full_screen);
		imvFullSelect.setImageResource(R.drawable.not_selected);
		imvSimple.setImageResource(R.drawable.simple);
		imvSimpleSelect.setImageResource(R.drawable.selected);
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute,int second) 
	{
		String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
		String minuteString = minute < 10 ? "0"+minute : ""+minute;
		time = hourString+":"+minuteString+"m";
		 	 
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
			final Date dateObj = sdf.parse(time);
			time = new SimpleDateFormat("K:mm a").format(dateObj);
		} catch (final ParseException e) {
			e.printStackTrace();
		}	
		if(time.startsWith("0:"))
			time= time.replace("0:", "12:");
		addAlarmFragment();
		 
		Long tsLong = System.currentTimeMillis()/1000;
		remID = tsLong.intValue();
		Random r  =new Random();
		int timeStamp = tsLong.intValue()+r.nextInt(100);
        
		putData.addReminder(remID, userName, time, newRitualName);
		
		updateData.updateUserRitualTime(userName, newRitualName, time, remID);
		putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_SUN,TableAttributes.OFF, -1);
		putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_SAT,TableAttributes.OFF, -1);
				
		//seting alarm for repeating days from monday to friday
		//for(int i=4; i<=6; i++)	//Set alrm from monday(day of week 2)to fri(day of week 6)
		GeneralUtility.setAlarmTime(NewRitual.this, userName, newRitualName,Calendar.MONDAY,hourOfDay, minute,timeStamp, false);
		putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_MON,TableAttributes.ON, timeStamp);
		
		tsLong = System.currentTimeMillis()/1000;
		timeStamp = tsLong.intValue()+r.nextInt(100);
		GeneralUtility.setAlarmTime(NewRitual.this, userName, newRitualName,Calendar.TUESDAY,hourOfDay, minute,timeStamp, false);
		putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_TUE,TableAttributes.ON, timeStamp);
		
		tsLong = System.currentTimeMillis()/1000;
		timeStamp = tsLong.intValue()+r.nextInt(100);
		GeneralUtility.setAlarmTime(NewRitual.this, userName, newRitualName,Calendar.WEDNESDAY,hourOfDay, minute,timeStamp, false);
		putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_WED,TableAttributes.ON, timeStamp);
		
	        
		tsLong = System.currentTimeMillis()/1000;
		timeStamp = tsLong.intValue()+r.nextInt(100);
		GeneralUtility.setAlarmTime(NewRitual.this, userName, newRitualName,Calendar.THURSDAY,hourOfDay, minute,timeStamp, false);
		putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_THU,TableAttributes.ON, timeStamp);
		
	        
		tsLong = System.currentTimeMillis()/1000;
		timeStamp = tsLong.intValue()+r.nextInt(100);
		GeneralUtility.setAlarmTime(NewRitual.this, userName, newRitualName,Calendar.FRIDAY,hourOfDay, minute,timeStamp, false);
		putData.addReminderDesc(remID, userName, time, TableAttributes.REMINDER_FRI,TableAttributes.ON, timeStamp);
		
	 }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			if (resultCode == AppsConstant.RITUAL_IMG)
			{
				ritualImg = data.getIntExtra("img_num", 1);
				imvRitualImg.setImageResource(AppsConstant.getRitualTopImg(ritualImg));
			}
		}
	}
	public void makeFloatColor(){
		Log.d("sunil","green");
		fab.setColorNormal(getResources().getColor(R.color.green));
	}
}

