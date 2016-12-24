package com.motivator.wecareyou;

import java.io.ByteArrayOutputStream;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.alarm.ActionReminderReceiver;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.JourneyData;
import com.motivator.model.JourneyHabitModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class OneTimeAction extends Activity implements OnClickListener, 
	TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
	
	String userName, selectedStep;
	int habitId, actionNum;
	String callingFrag;
	ActionBar actionBar;
	LinearLayout llActionTop, llRemindMe;
	TextView tvActionTitle, tvActionCompleted;
	WebView webView;
	TextView tvRemindMe, tvRemindMeTime, tvRemindMEDate;
	ImageView imvActionImg, imvRemoveRemindMe;
	
	JourneyHabitModel userJourneyModel = new JourneyHabitModel();
	
	int COMPARE_WITH = TableAttributes.STATUS_VALUE_0;
	int actionStatus = TableAttributes.STATUS_VALUE_1;
	public static final String Action_Num = "Action_Num";
	
	String actionIHaveDoneThis = "I HAVE DONE THIS";
	String actionCompleted = "Action Completed!";
	String actionRemindMe = "REMIND ME";
	//String timeSelected, dateSelected;
	int selectedHour =-1, selectedMin;
	int selectedDate =-1, selectedMonth, selectedYear;
	
	GetData getData;
	UpdateData update;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getData = new GetData(OneTimeAction.this);
    	update = new UpdateData(OneTimeAction.this);
    	
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.one_time_action);
        
      //Set ACTION BAR
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("action");
        
        SpannableString s = new SpannableString("action");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        actionBar.setTitle(s);
        
      //get Data from shared prefrences and intent
		userName = GeneralUtility.getPreferences(OneTimeAction.this, AppsConstant.user_name);
		selectedStep = getIntent().getExtras().getString(AppsConstant.selected_journey_step);
		habitId = getIntent().getExtras().getInt(AppsConstant.h_id);
		actionNum = getIntent().getExtras().getInt(Action_Num);
		callingFrag = getIntent().getExtras().getString(AppsConstant.calling_frag);
		
		//Initializing UI VIews
		llActionTop = (LinearLayout)findViewById(R.id.ll_action_top);
		tvActionTitle = (TextView)findViewById(R.id.tv_action_title);
		imvActionImg = (ImageView)findViewById(R.id.imv_action_img);
		tvActionCompleted = (TextView)findViewById(R.id.tv_action_completed);
		
		webView = (WebView)findViewById(R.id.tv_action_txt);
		
		llRemindMe = (LinearLayout)findViewById(R.id.ll_remind_me);
		tvRemindMe = (TextView)findViewById(R.id.tv_remind_me);
		tvRemindMEDate = (TextView)findViewById(R.id.tv_remind_date);
		tvRemindMeTime = (TextView)findViewById(R.id.tv_remind_time);
		imvRemoveRemindMe = (ImageView)findViewById(R.id.imv_remove_remind);
		
		tvActionTitle.setTypeface(GeneralUtility.setTypeFace(OneTimeAction.this));
		tvActionCompleted.setTypeface(GeneralUtility.setTypeFace(OneTimeAction.this));
		tvRemindMe.setTypeface(GeneralUtility.setTypeFace(OneTimeAction.this));
		tvRemindMEDate.setTypeface(GeneralUtility.setTypeFace(OneTimeAction.this));
		tvRemindMeTime.setTypeface(GeneralUtility.setTypeFace(OneTimeAction.this));
		
		/// setText
		llActionTop.setBackgroundColor(Color.parseColor(JourneyData.getActionBackground(habitId)));
		tvActionTitle.setText(JourneyData.getActionTitle(habitId));
		//tvActionText.setText(text);
		imvActionImg.setImageResource(JourneyData.getActionImg(habitId));
		
		userJourneyModel = getData.getJourneyHabit(userName, habitId);
		actionStatus = userJourneyModel.getActionStatus();
		
		switch (actionNum) {
		case 1:
			COMPARE_WITH = TableAttributes.STATUS_VALUE_1;
			if(actionStatus>= COMPARE_WITH)
			{
				updateUIView(true);
			}
			break;

		case 2:
			COMPARE_WITH = TableAttributes.STATUS_VALUE_2;
			if(actionStatus>= COMPARE_WITH)
			{
				updateUIView(true);
			}
			break;
		}
		
		
		tvActionCompleted.setOnClickListener(this);
		tvRemindMe.setOnClickListener(this);
		tvRemindMEDate.setOnClickListener(this);
		tvRemindMeTime.setOnClickListener(this);
		imvRemoveRemindMe.setOnClickListener(this);	
			
		
		String fileName = getfileName();		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("file:///android_asset/"+fileName);
	}

	private void updateUIView(boolean isCompleted) 
	{
		if(isCompleted)
		{
			tvActionCompleted.setVisibility(View.GONE);
			tvRemindMe.setText(actionCompleted);
			tvRemindMe.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_white, 0, 0, 0);
		}
		else
		{
			tvActionCompleted.setVisibility(View.VISIBLE);
		}
		
	}
	
	
	private String getfileName() 
	{
		String fileName = "html_files/great_breakfast_motivator1.html";
		switch (habitId) {
		case TableAttributes.drinkWaterId:
			switch (actionNum) {
			case 1:
				fileName = "html_files/drink_water_action.html";
				break;
			case 2:
				fileName = "html_files/drink_water_action2.html";
				break;
			}
			break;

		case TableAttributes.greatBreakFastID:
			switch (actionNum) {
			case 1:
				fileName = "html_files/great_breakfast_action1.html";
				break;
			case 2:
				fileName = "html_files/great_breakfast_action2.html";
				break;
			}
			
			break;
			
		case TableAttributes.danceYourWayID:
			switch (actionNum) {
			case 1:
				fileName = "html_files/dance_action1.html";
				break;
			case 2:
				fileName = "html_files/dance_action2.html";
				break;
			}
			break;
		case TableAttributes.SecretLetterID:
			fileName = "html_files/secret_letter_action.html";
			break;
		}
		return fileName;
	}

	
	public void createNotification() 
	 {
		Calendar now = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, selectedDate);
		cal.set(Calendar.MONTH, selectedMonth);
		cal.set(Calendar.YEAR, selectedYear);
		cal.set(Calendar.HOUR_OF_DAY, selectedHour);
		cal.set(Calendar.MINUTE, selectedMin);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
       
		long alarmTime = cal.getTimeInMillis();
		if(cal.before(now))
        {
       	 	Toast.makeText(this, "Select a newer date and time", Toast.LENGTH_LONG).show();
        }
		else
		{
			Intent intent = new Intent(OneTimeAction.this, ActionReminderReceiver.class);
			intent.putExtra(AppsConstant.user_name, userName);
			intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
			intent.putExtra(AppsConstant.h_id, habitId);
			intent.putExtra(Action_Num, actionNum);
			PendingIntent activity = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
			AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, activity);
			finish();
		}
		
	 }
	 
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.journey_menu, menu);
        MenuItem switchButton = menu.findItem(R.id.done);
        switchButton.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        
        case android.R.id.home:
            onBackPressed();
            return true;
        
        case R.id.action_feedback:
			View rootView = findViewById(android.R.id.content).getRootView();
			rootView.setDrawingCacheEnabled(true);
			Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
			rootView.setDrawingCacheEnabled(false);
			
			Intent feedback = new Intent(OneTimeAction.this, Feedback.class);
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
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.tv_action_completed:
			String action = tvActionCompleted.getText().toString();
			if(action.equalsIgnoreCase(actionRemindMe))
			{
				if(selectedDate>-1 && selectedHour>-1)
				{
					createNotification();
				}
				else
					Toast.makeText(OneTimeAction.this, "Set reminder time first!", 5).show();
			}
			else
			{
				if(actionStatus< COMPARE_WITH)
	    		{
					++actionStatus;
	        		update.updateJourneyStatus(userName, AppsConstant.SELECTED_JOURNEY, selectedStep);
	    		}
				update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY, habitId, TableAttributes.ACTION_DONE, actionStatus);
				updateUIView(true);
				
			}
					
			break;

		case R.id.tv_remind_me:
			if(!(tvRemindMe.getText().toString().equalsIgnoreCase(actionCompleted)))
			{
				tvActionCompleted.setText(actionRemindMe);
				tvRemindMe.setVisibility(View.GONE);
				llRemindMe.setVisibility(View.VISIBLE);
			}
			break;
			
		case R.id.tv_remind_date:
			Calendar now = Calendar.getInstance();
			DatePickerDialog dpd = DatePickerDialog.newInstance(OneTimeAction.this,
					now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
			dpd.show(getFragmentManager(), "Datepickerdialog");
			break;
			
		case R.id.tv_remind_time:			
			now = Calendar.getInstance();
	        TimePickerDialog tpd = TimePickerDialog.newInstance(
	                OneTimeAction.this,
	                now.get(Calendar.HOUR_OF_DAY),
	                now.get(Calendar.MINUTE), false);
	        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
	            @Override
	            public void onCancel(DialogInterface dialogInterface) {
	                Log.d("TimePicker", "Dialog was cancelled");
	            }
	        });
	        tpd.show(getFragmentManager(), "Timepickerdialog");
			break;
			
		case R.id.imv_remove_remind:
			tvActionCompleted.setText(actionIHaveDoneThis);
			tvRemindMe.setVisibility(View.VISIBLE);
			llRemindMe.setVisibility(View.GONE);
			break;
		}
		
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute,int second) 
	{
		selectedHour = hourOfDay;
		selectedMin	= minute;
		String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
		String minuteString = minute < 10 ? "0"+minute : ""+minute;
		String timeSelected = hourString+":"+minuteString+"m";
		 	 
		 try {
			 final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
			 final Date dateObj = sdf.parse(timeSelected);
			 timeSelected = new SimpleDateFormat("K:mm a").format(dateObj);
		 } catch (final ParseException e) {
			 e.printStackTrace();
		 }	
		 if(timeSelected.startsWith("0:"))
			 timeSelected= timeSelected.replace("0:", "12:");
		 	
		tvRemindMeTime.setText(timeSelected);
	}

	@Override
	public void onDateSet(DatePickerDialog view, int year, int monthOfYear,	int dayOfMonth) 
	{
		// date picker starts month counting from zero
		selectedDate = dayOfMonth;
		selectedMonth = monthOfYear;
		selectedYear = year;
		String monthName = new DateFormatSymbols().getMonths()[monthOfYear];
		tvRemindMEDate.setText(monthName+"-"+dayOfMonth);
	}
    
}

