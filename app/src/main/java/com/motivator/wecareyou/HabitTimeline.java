package com.motivator.wecareyou;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.imanoweb.calendarview.CustomCalendarView;
import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;
import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.model.HabitModel;

public class HabitTimeline extends Activity {
	
	String userName;
	String selectedRitual;
	
	ListView lvSuccessRate;
	TextView tvTitle;
	GetData getData;
	ArrayList<HabitModel> userHabit;	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        getData = new GetData(HabitTimeline.this);
      //getDAta from shared prefrence
        userName = GeneralUtility.getPreferences(HabitTimeline.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        setContentView(R.layout.time_line_list);
        
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setTitle(selectedRitual);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        
        SpannableString s = new SpannableString(selectedRitual.toLowerCase());
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        actionBar.setTitle(s);
        
        tvTitle = (TextView)findViewById(R.id.tv_title);
        lvSuccessRate = (ListView)findViewById(R.id.lv_success_list);
        tvTitle.setTypeface(GeneralUtility.setTypeFace(HabitTimeline.this));
        
        userHabit = getData.getUserHabits(userName, selectedRitual);
                
        tvTitle.setText("Habit Timeline");
        lvSuccessRate.setAdapter(new HabitSuccessRate());
	}

	class HabitSuccessRate extends BaseAdapter
	{
		@Override
		public int getCount() 
		{
			return userHabit.size();
		}
	
		@Override
		public Object getItem(int position)
		{
			return null;
		}
	
		@Override
		public long getItemId(int position) 
		{
			return position;
		}
	
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) 
		{
			LayoutInflater inflater = getLayoutInflater();
			convertView = inflater.inflate(R.layout.habit_time_line_cell, null);
			
			TextView tvHabit = (TextView)convertView.findViewById(R.id.tv_habit_name);
			CustomCalendarView calendarView = (CustomCalendarView)convertView.findViewById(R.id.calendar_view);
			//Initialize calendar with date
			Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

	        //Show monday as first date of week
	        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

	        //Show/hide overflow days of a month
	        calendarView.setShowOverflowDate(false);

	        //call refreshCalendar to update calendar the view
	        calendarView.refreshCalendar(currentCalendar);
	        
			tvHabit.setTypeface(GeneralUtility.setTypeFace(HabitTimeline.this));
			
			int habitId = userHabit.get(position).getHabitId();
			tvHabit.setText(userHabit.get(position).getHabit());
			tvHabit.setCompoundDrawablesWithIntrinsicBounds(AppsConstant.getHabitIcon(habitId), 0, 0, 0);
			

	        //adding calendar day decorators
	        List<DayDecorator> decorators = new ArrayList<DayDecorator>();
	        decorators.add(new ColorDecorator(position));
	        calendarView.setDecorators(decorators);
	        calendarView.refreshCalendar(currentCalendar);	
	        
			return convertView;
		}
		
	}
	
	
	private class ColorDecorator implements DayDecorator {
		
		int pos = 0;
		public ColorDecorator(int position)
		{
			pos = position;
		}
		@Override
        public void decorate(DayView cell) {
    		
            Date d= cell.getDate();
    		String theDate = DateUtility.formateDate(d, "E MMM dd yyyy");     		
            
            boolean isHabitCompleted = getData.isHabitCompletedOn(userName, userHabit.get(pos).getHabitId(), theDate);
            if(isHabitCompleted)
            {
            	cell.setBackgroundResource(R.drawable.circle_completed);
            }
            else 
        		cell.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
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

