package com.motivator.wecareyou;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.model.HabitModel;
import com.motivator.model.HabitSuccessModel;

public class CompletionRate extends Activity {
	
	String userName;
	String selectedRitual;
	
	TextView tvTitle;
	ListView lvSuccessRate;
	GetData getData;
	ArrayList<HabitModel> userHabit;	
	ArrayList<HabitSuccessModel> successRateList;	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        getData = new GetData(CompletionRate.this);
      //getDAta from shared prefrence
        userName = GeneralUtility.getPreferences(CompletionRate.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        setContentView(R.layout.time_line_list);
        
        SpannableString s = new SpannableString("completion rate");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setTitle("completion rate");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(s);
        
        
        tvTitle = (TextView)findViewById(R.id.tv_title);
        lvSuccessRate = (ListView)findViewById(R.id.lv_success_list);
        
        tvTitle.setTypeface(GeneralUtility.setTypeFace(CompletionRate.this));
        
        successRateList = new ArrayList<HabitSuccessModel>();
        userHabit = getData.getUserHabits(userName, selectedRitual);
        
        Date d= new Date();
        int date = Integer.parseInt(DateUtility.formateDate(d, "dd"));
        date = date-1;
        for(int i=0; i<userHabit.size(); i++)
		{
        	double totalCount = 0.0, completed = 0.0; 
        	for(int dateCount = date; dateCount>=0; dateCount--)
    		{
        		String theDate = DateUtility.getPreviousDateString(-dateCount, "E MMM dd yyyy");
        		int added = DateUtility.compareTwoDate(userHabit.get(i).getHabitAddedON(),theDate, "E MMM dd yyyy");
        		if(added<=0)
        		{
        			totalCount++;
            		boolean isCompleted = getData.isHabitCompletedOn(userName, userHabit.get(i).getHabitId(), theDate);
            		if(isCompleted)
            		{
            			completed++;
            		}
        		}
        		
    		}
        	
        	double status = 0.0;
        	if(totalCount !=0)
        		status = (completed/totalCount)*100;
        	
        	HabitSuccessModel successModel = new HabitSuccessModel();
        	successModel.setHabitId(userHabit.get(i).getHabitId());
        	successModel.setHabit(userHabit.get(i).getHabit());
        	successModel.setSuccessRate(status);
        	successRateList.add(successModel);
		}
        
        
        lvSuccessRate.setAdapter(new HabitSuccessRate());
	}

	class HabitSuccessRate extends BaseAdapter
	{
		@Override
		public int getCount() 
		{
			return successRateList.size();
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
			convertView = inflater.inflate(R.layout.success_rate, null);
			
			ImageView imvHabitIcon = (ImageView)convertView.findViewById(R.id.imv_habit_icon);
			TextView tvHabit = (TextView)convertView.findViewById(R.id.tv_habit);
			TextView tvHabitStatus = (TextView)convertView.findViewById(R.id.tv_habit_status);
			
			tvHabit.setTypeface(GeneralUtility.setTypeFace(CompletionRate.this));
			
			tvHabit.setText(successRateList.get(position).getHabit());
			double status = successRateList.get(position).getSuccessRate();
			tvHabitStatus.setText(new DecimalFormat("#.##").format(status)+"%");
			if(userHabit.get(position).getHabitDescription().equalsIgnoreCase(TableAttributes.custom_habit))
			{
				if(userHabit.get(position).getReminderDesc2()!=null && userHabit.get(position).getReminderDesc2().length()>0)
				{
					GradientDrawable bgShape = (GradientDrawable)imvHabitIcon.getBackground();
					bgShape.setColor(Color.parseColor(userHabit.get(position).getReminderDesc2()));
				}
				else
				{
					imvHabitIcon.setBackgroundResource(R.drawable.habit_custom_icon);
				}
			}
			else
				imvHabitIcon.setBackgroundResource(AppsConstant.getHabitIcon(userHabit.get(position).getHabitId()));
			
			return convertView;
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

