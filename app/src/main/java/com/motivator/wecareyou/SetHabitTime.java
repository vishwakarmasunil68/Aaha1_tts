package com.motivator.wecareyou;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;

public class SetHabitTime extends Activity implements OnClickListener {
	
	int click = 0 ;
	boolean isNewHabit = false;
	String mins;
	String userName;
	String selectedRitual;
	
	int habitId;
	String habit, habitDescription="", habitTime;
	
	TextView  tvHabit, tvHr, tvMin, tvSet, tvCancel;
	GridView gvGrid;
	
	UpdateData updateData;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        updateData = new UpdateData(SetHabitTime.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.set_habit_time);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        //SetUPUIVIEWS
  		intializeUIViews();
        userName = GeneralUtility.getPreferences(SetHabitTime.this, AppsConstant.user_name);
        
        //Get DAta from the database
        isNewHabit = getIntent().getExtras().getBoolean("is_new_habit");
        habit = getIntent().getExtras().getString("habit");
        if(!isNewHabit)
        {
        	habitId = getIntent().getExtras().getInt("h_id");
            habitTime = getIntent().getExtras().getString("time");
        }
        
        tvHabit.setText(habit);
        gvGrid.setAdapter(new GridCustomAdapter());
        
	}

	private void intializeUIViews() 
	{
        tvHabit= (TextView)findViewById(R.id.tv_habit);
        tvHr = (TextView)findViewById(R.id.tv_hr);
        tvMin = (TextView)findViewById(R.id.tv_min);
        tvCancel = (TextView)findViewById(R.id.tv_cancel);
        tvSet = (TextView)findViewById(R.id.tv_set);
        gvGrid = (GridView)findViewById(R.id.grid);
        tvCancel.setOnClickListener(this);
        tvSet.setOnClickListener(this);
	}

	public class GridCustomAdapter extends BaseAdapter {

		private String[] cell_num = {"1","2","3","4","5","6","7","8","9"," ","0",""};
	
		    public int getCount() {
		        return cell_num.length;
		    }

		    public Object getItem(int position) {
		        return null;
		    }

		    public long getItemId(int position) {
		        return 0;
		    }

		public View getView(int position, View convertView, ViewGroup parent) 
		{
			TextView tv;
			if (convertView == null) 
			{
				tv = new TextView(SetHabitTime.this);
				tv.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
				tv.setPadding(10, 10, 10, 10);
				tv.setTextColor(Color.parseColor("#000000"));
				tv.setGravity(Gravity.CENTER);
				tv.setTextSize(35);
//				tv.setTypeface(null, Typeface.BOLD);
			} else {
				tv = (TextView) convertView;
			}

			tv.setText(cell_num[position]);
			tv.setTag(position);
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) 
				{
					int click = (Integer) v.getTag();
					if(click!= 9 || click!= 11)
						setTime(cell_num[click]);

				}

				
			});
			return tv;
		}
		
	}
	
	private void setTime(String value) {
		click++;
		switch (click) {
		case 1:
			mins = value;
			tvMin.setText("0"+mins);
			break;

		case 2:
			mins = mins+value;
			tvMin.setText(mins);
			break;
		case 3:
			tvHr.setText(value);
			break;
		}

	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.tv_set:
			int h = Integer.parseInt(tvHr.getText().toString());
			int m = Integer.parseInt(tvMin.getText().toString());
			int time = h*60+m;
			habitTime = time+"";
			if(isNewHabit)
			{
				Intent i = new Intent();
				i.putExtra("time_duration", habitTime);
				setResult(AddCustomHabit.RESULT_HABIT_TIME, i);
				finish();
			}
			else
			{
				updateData.updateUserHabitInfo(userName, habitId, TableAttributes.USER_HABIT_TIME, habitTime);
				finish();
			}
			
			break;

		case R.id.tv_cancel:
			finish();
			break;
		}
		
	}
	
}

