package com.motivator.wecareyou;

import java.text.DecimalFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.model.TimeLineModel;

public class TodayTimeLine extends Activity {
	
	String userName;
	String selectedRitual;
	
	TextView tvDay1,tvDay2, tvDay3;
	TextView tvRitual, tvDayValue1,tvDayValue2, tvDayValue3, tvStatus1, tvStatus2, tvStatus3;
	double status = 0.0;
	GetData getData;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
      //getDAta from shared prefrence
        userName = GeneralUtility.getPreferences(TodayTimeLine.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        setContentView(R.layout.today_timeline);
        
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setTitle("timeline");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        
        SpannableString s = new SpannableString("Timeline");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        actionBar.setTitle(s);
        
        //SetUPUIVIEWS
        tvRitual = (TextView)findViewById(R.id.tv_ritual);
        tvDay1 = (TextView)findViewById(R.id.tv_day1);
		tvDay2 = (TextView)findViewById(R.id.tv_day2);
		tvDay3 = (TextView)findViewById(R.id.tv_day3);
		tvDayValue1 = (TextView)findViewById(R.id.tv_day_value1);
		tvDayValue2 = (TextView)findViewById(R.id.tv_day_value2);
		tvDayValue3 = (TextView)findViewById(R.id.tv_day_value3);
		tvStatus1 = (TextView)findViewById(R.id.tv_status1);
		tvStatus2 = (TextView)findViewById(R.id.tv_status2);
		tvStatus3 = (TextView)findViewById(R.id.tv_status3);
		
		
		tvRitual.setTypeface(GeneralUtility.setTypeFace(TodayTimeLine.this));
		tvDay1.setTypeface(GeneralUtility.setTypeFace(TodayTimeLine.this));
		tvDay2.setTypeface(GeneralUtility.setTypeFace(TodayTimeLine.this));
		tvDay3.setTypeface(GeneralUtility.setTypeFace(TodayTimeLine.this));
		tvDayValue1.setTypeface(GeneralUtility.setTypeFace(TodayTimeLine.this));
		tvDayValue2.setTypeface(GeneralUtility.setTypeFace(TodayTimeLine.this));
		tvDayValue3.setTypeface(GeneralUtility.setTypeFace(TodayTimeLine.this));
		tvStatus1.setTypeface(GeneralUtility.setTypeFace(TodayTimeLine.this));
		tvStatus2.setTypeface(GeneralUtility.setTypeFace(TodayTimeLine.this));
		tvStatus3.setTypeface(GeneralUtility.setTypeFace(TodayTimeLine.this));
		
		tvRitual.setText(selectedRitual);  
        
  		getData = new GetData(TodayTimeLine.this);
  		
  		Date d = new Date();
  		String theDate = DateUtility.formateDate(d, "E MMM dd yyyy");   
  		getStatus(theDate);
  		String date = DateUtility.formateDate(d, "dd"); 
		String month = DateUtility.formateDate(d, "MMM"); 
		tvDay1.setText("Today");
		tvDayValue1.setText(date+"\n"+month);
		tvStatus1.setText(new DecimalFormat("#.#").format(status)+"%");
	
  		Date prvsDate = DateUtility.getPreviousDate(-1, "E MMM dd yyyy");
  		theDate = DateUtility.formateDate(prvsDate, "E MMM dd yyyy");
  		getStatus(theDate);
  		date = DateUtility.formateDate(prvsDate,  "dd"); 
		month = DateUtility.formateDate(prvsDate, "MMM"); 
		String day = DateUtility.formateDate(prvsDate, "E");
  		tvDay2.setText(day);
    	tvDayValue2.setText(date+"\n"+month);
    	tvStatus2.setText(new DecimalFormat("#.#").format(status)+"%");
    	
  		
		prvsDate = DateUtility.getPreviousDate(-2, "E MMM dd yyyy");
		theDate = DateUtility.formateDate(prvsDate, "E MMM dd yyyy");
		getStatus(theDate);
		day = DateUtility.formateDate(prvsDate, "E");
		date = DateUtility.formateDate(prvsDate,  "dd"); 
		month = DateUtility.formateDate(prvsDate, "MMM"); 
		tvDay3.setText(day);
    	tvDayValue3.setText(date+"\n"+month);
    	tvStatus3.setText(new DecimalFormat("#.#").format(status)+"%");
	}

	
	private void getStatus(String theDate)
    {
    	TimeLineModel timeLine = getData.getTimeline(userName, selectedRitual, theDate);
        if(timeLine!=null)//Table TImeLine does not contains the row of the date
        {
        	double total = timeLine.getTotalHabit();
        	double completed = timeLine.getHabitCompleted();
        	if(total !=0)
        	{
        		status = (completed/total)*100;
        	}
        	else {
				status = 0.0;
			}
        	
        }
        else
        	status = 0.0;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}

