package com.motivator.wecareyou;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.GeneralUtility;
import com.motivator.common.AppsConstant;
import com.motivator.database.GetData;
import com.motivator.database.PutData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;

public class Habit extends Activity implements OnClickListener {
	
	String userName;
	int selectedHabit;
	String selectedRitual;
	boolean isRitualAdded = false;
	
	boolean isHabitAdded;
	int habitId;
	String habit, habitDescription="", habitText="", habitTime;
	
	LinearLayout llHabitTop;
	RelativeLayout rllInfo;
	ImageView imvAddHabit;
	ImageView /*imvClick,*/ imvBack;
	TextView tvHabitDesc, tvWhy, tvHabitText, tvHabitInfo;
	
	GetData getData;
	PutData putData ;
	UpdateData updateData;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getData = new GetData(Habit.this);
    	putData= new PutData(Habit.this);
    	updateData = new UpdateData(Habit.this);
    	
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.habit);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
      //SetUPUIVIEWS
  		intializeUIViews();
        userName = GeneralUtility.getPreferences(Habit.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        
        selectedHabit = getIntent().getExtras().getInt("selected");
        
        //Get DAta from the database
        habitId = AppsConstant.habitList.get(selectedHabit).getHabitId();
        habit = AppsConstant.habitList.get(selectedHabit).getHabit();
        habitDescription = AppsConstant.habitList.get(selectedHabit).getHabitDescription();
        habitText = AppsConstant.habitList.get(selectedHabit).getHabitBenefits();
        habitTime = AppsConstant.habitList.get(selectedHabit).getHabitTime();
        isHabitAdded = AppsConstant.habitList.get(selectedHabit).isHabitAdded();
        
        //Check is user coming first time in the app (by cheking isRitualAdded in database)
//  		isRitualAdded = getData.isValueAdded(userName, TableAttributes.IS_RITUAL_ADDED);
  		isRitualAdded = GeneralUtility.getPreferencesBoolean(Habit.this, AppsConstant.IS_RITUAL_ADDED);
  		//SetVisibility of views 
        if(isRitualAdded)
		{
        	rllInfo.setVisibility(View.GONE); 
        	//imvClick.setVisibility(View.GONE);
        	tvHabitInfo.setVisibility(View.GONE);
		}
        else
        {
        	rllInfo.setVisibility(View.VISIBLE);
        	//imvClick.setVisibility(View.VISIBLE);
        	tvHabitInfo.setVisibility(View.VISIBLE);
            rllInfo.setAlpha(0.6f);
        }
        
        //imvHabitIcon.set
        llHabitTop.setBackgroundResource(AppsConstant.getHabitTop(habitId));
        tvHabitDesc.setText(habitDescription);
        tvHabitText.setText(habitText);
        if(isHabitAdded)
        {
        	imvAddHabit.setBackgroundResource(R.drawable.circle_green);
        	imvAddHabit.setImageResource(R.drawable.right);
        }
  	
	}

	private void intializeUIViews() 
	{
		llHabitTop = (LinearLayout)findViewById(R.id.ll_habit_top);
        tvHabitDesc= (TextView)findViewById(R.id.tv_habit_description);
        tvWhy = (TextView)findViewById(R.id.tv_why);
        tvHabitText = (TextView)findViewById(R.id.tv_habit_text);
        
        imvAddHabit = (ImageView)findViewById(R.id.imv_add_habit);        
        imvBack= (ImageView)findViewById(R.id.imv_back);
        
        rllInfo = (RelativeLayout)findViewById(R.id.rll_info);
        //imvClick = (ImageView)findViewById(R.id.imv_click);
        tvHabitInfo = (TextView)findViewById(R.id.tv_habit_info);
        
        tvHabitDesc.setTypeface(GeneralUtility.setTypeFace(Habit.this));
        tvWhy.setTypeface(GeneralUtility.setTypeFace(Habit.this));
        tvHabitText.setTypeface(GeneralUtility.setTypeFace(Habit.this));
        tvHabitInfo.setTypeface(GeneralUtility.setTypeFace(Habit.this));
        
        imvAddHabit.setOnClickListener(this);
        imvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.imv_add_habit:
			if(!isHabitAdded)
			{
				long row = putData.addUserHabit(userName, habitId, selectedRitual, habitTime);
				if(row >0)
				{
					if(!isRitualAdded)
						//updateData.updateUserDetail(userName, TableAttributes.IS_RITUAL_ADDED);
						GeneralUtility.setPreferencesBoolean(Habit.this, AppsConstant.IS_RITUAL_ADDED, true);
					
					Toast.makeText(Habit.this, "Habit Added!", 5).show();
					AppsConstant.habitList.get(selectedHabit).setSelection(true);
					
					Intent i = new Intent();
					i.putExtra("opted", true);
					setResult(HabitList.ADD_HABIT,i); 
				}
				else if(row == -10)
					Toast.makeText(Habit.this, R.string.max_habit, 5).show();
			}
			finish();
			overridePendingTransition(R.anim.buttom_to_top, R.anim.slide_in_bottom);
			break;

		case R.id.imv_back:
			if(isRitualAdded)
			{
				finish();
				overridePendingTransition(R.anim.slide_in_top, R.anim.slide_in_bottom);
			}
			break;
		}
		
	}
	
}

