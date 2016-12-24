package com.motivator.wecareyou;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.model.JourneyHabitModel;
import com.motivator.model.JourneyData;

public class JourneyEvents extends Activity {
	
	String userName, selectedStep;
	int habitId, letterNumber;
	ActionBar actionBar;
	LinearLayout llJourneyTop;
	ImageView imvJourneyImg;
	ListView lvStep; 
	
	GetData getData;
	JourneyHabitModel userJourneyModel = new JourneyHabitModel();
	ArrayList<String> journeyStepsText = new ArrayList<String>();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getData = new GetData(JourneyEvents.this);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.journey_events);
        llJourneyTop = (LinearLayout)findViewById(R.id.ll_journey_top);
        imvJourneyImg = (ImageView)findViewById(R.id.imv_journey_img);
        lvStep = (ListView)findViewById(R.id.lv_step);
        
        //get Data from shared prefrences and intent
        userName = GeneralUtility.getPreferences(JourneyEvents.this, AppsConstant.user_name);
        selectedStep = getIntent().getExtras().getString(AppsConstant.selected_journey_step);
		habitId = getIntent().getExtras().getInt(AppsConstant.h_id);
		letterNumber = getIntent().getExtras().getInt(Letter.Letter_Num);
        
      //Set ACTION BAR
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
       // actionBar.setTitle(JourneyData.getJourneyTitle(habitId));
        
        SpannableString s = new SpannableString(JourneyData.getJourneyTitle(habitId).toLowerCase());
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        actionBar.setTitle(s);
        
        llJourneyTop.setBackgroundColor(Color.parseColor(JourneyData.getJourneyBackground(habitId)));
        imvJourneyImg.setImageResource(JourneyData.getJourneyImg(habitId));
        journeyStepsText = JourneyData.getJourneyStepsText(habitId);
		
        //get DAta from database
        userJourneyModel = getData.getJourneyHabit(userName, habitId);
		
		lvStep.setAdapter(new JourneyStepAdapter());		
		
		lvStep.setOnItemClickListener(new OnItemClickListener()
        {
			Intent intent;
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,int position, long id) {
				
				switch (position) {
				case 0:
					intent = new Intent(JourneyEvents.this, Letter.class);
					intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
					intent.putExtra(AppsConstant.h_id, habitId);
					intent.putExtra(Letter.Letter_Num, letterNumber);
					intent.putExtra(AppsConstant.calling_frag, "");
					startActivity(intent);
					break;

				case 1:
					if(userJourneyModel.isLetterRead()!=TableAttributes.STATUS_VALUE_0)
			    	{
						if(habitId==TableAttributes.goldenChallengeID)
							intent = new Intent(JourneyEvents.this, Goal_GoldenChallenge.class);
						else
							intent = new Intent(JourneyEvents.this, Goal.class);
						intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
						intent.putExtra(AppsConstant.h_id, habitId);
						intent.putExtra(AppsConstant.calling_frag, "");
						startActivity(intent);
			    	}
					else
						Toast.makeText(JourneyEvents.this, R.string.locked, 5).show();
					break;
					
				case 2:
					if(userJourneyModel.isChallengeAccepted()!=TableAttributes.STATUS_VALUE_0)
			    	{
						intent = new Intent(JourneyEvents.this, OneTimeAction.class);
						intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
						intent.putExtra(AppsConstant.h_id, habitId);
						intent.putExtra(OneTimeAction.Action_Num, 1);
						intent.putExtra(AppsConstant.calling_frag, "");
						startActivity(intent);
			    	}
					else
						Toast.makeText(JourneyEvents.this, R.string.locked, 5).show();
					break;
				case 3:
					if(userJourneyModel.getActionStatus()!=TableAttributes.STATUS_VALUE_0)
			    	{
						intent = new Intent(JourneyEvents.this, Motivator.class);
						intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
						intent.putExtra(AppsConstant.h_id, habitId);
						intent.putExtra(Motivator.Motivator_Num, 1);
						intent.putExtra(AppsConstant.calling_frag, "");
						startActivity(intent);
			    	}
					else
						Toast.makeText(JourneyEvents.this, R.string.locked, 5).show();
					break;
				case 4:
					if(userJourneyModel.getActionStatus()!=TableAttributes.STATUS_VALUE_0)
			    	{
						intent = new Intent(JourneyEvents.this, OneTimeAction.class);
						intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
						intent.putExtra(AppsConstant.h_id, habitId);
						intent.putExtra(OneTimeAction.Action_Num, 2);
						intent.putExtra(AppsConstant.calling_frag, "");
						startActivity(intent);
			    	}
					else
						Toast.makeText(JourneyEvents.this, R.string.locked, 5).show();
					break;
					
				case 5:
					if(userJourneyModel.getMotivationStatus()>=TableAttributes.STATUS_VALUE_1)
			    	{
						intent = new Intent(JourneyEvents.this, Motivator.class);
						intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
						intent.putExtra(AppsConstant.h_id, habitId);
						intent.putExtra(Motivator.Motivator_Num, 2);
						intent.putExtra(AppsConstant.calling_frag, "");
						startActivity(intent);
			    	}	
					else
						Toast.makeText(JourneyEvents.this, R.string.locked, 5).show();
					break;
					
				case 6:
					if(userJourneyModel.getMotivationStatus()>=TableAttributes.STATUS_VALUE_2)
			    	{
						intent = new Intent(JourneyEvents.this, Motivator.class);
						intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
						intent.putExtra(AppsConstant.h_id, habitId);
						intent.putExtra(Motivator.Motivator_Num, 3);
						intent.putExtra(AppsConstant.calling_frag, "");
						startActivity(intent);
			    	}	
					else
						Toast.makeText(JourneyEvents.this, R.string.locked, 5).show();
					break;
					
				case 7:
					if(userJourneyModel.getMotivationStatus()>= 3)
			    	{
						intent = new Intent(JourneyEvents.this, Motivator.class);
						intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
						intent.putExtra(AppsConstant.h_id, habitId);
						intent.putExtra(Motivator.Motivator_Num, 4);
						intent.putExtra(AppsConstant.calling_frag, "");
						startActivity(intent);
			    	}	
					else
						Toast.makeText(JourneyEvents.this, R.string.locked, 5).show();
					break;
					
				case 8:
					if(userJourneyModel.getMotivationStatus()>= 4)
			    	{
						intent = new Intent(JourneyEvents.this, Motivator.class);
						intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
						intent.putExtra(AppsConstant.h_id, habitId);
						intent.putExtra(Motivator.Motivator_Num, 5);
						intent.putExtra(AppsConstant.calling_frag, "");
						startActivity(intent);
			    	}
					else
						Toast.makeText(JourneyEvents.this, R.string.locked, 5).show();					
					break;
					
				case 9:
					if(userJourneyModel.getMotivationStatus()>= 5)
			    	{
						intent = new Intent(JourneyEvents.this, Motivator.class);
						intent.putExtra(AppsConstant.selected_journey_step, selectedStep);
						intent.putExtra(AppsConstant.h_id, habitId);
						intent.putExtra(Motivator.Motivator_Num, 6);
						intent.putExtra(AppsConstant.calling_frag, "");
						startActivity(intent);
			    	}	
					else
						Toast.makeText(JourneyEvents.this, R.string.locked, 5).show();
					break;
					
				}
			}

		});
		
	}
	
    class JourneyStepAdapter extends BaseAdapter
    {
		@Override
		public int getCount() {
			return journeyStepsText.size();
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
			LayoutInflater inflater = getLayoutInflater();
			convertView = inflater.inflate(R.layout.journey_step1_list, null);
			
			TextView tvCount = (TextView)convertView.findViewById(R.id.tv_count);
			TextView tvStepTitle = (TextView)convertView.findViewById(R.id.tv_step_title);
			TextView tvText = (TextView)convertView.findViewById(R.id.tv_step_text);
			TextView tvComplete = (TextView)convertView.findViewById(R.id.tv_step_complete);
	    	
			tvCount.setTypeface(GeneralUtility.setTypeFace(JourneyEvents.this));
			tvStepTitle.setTypeface(GeneralUtility.setTypeFace(JourneyEvents.this));
			tvText.setTypeface(GeneralUtility.setTypeFace(JourneyEvents.this));
			tvComplete.setTypeface(GeneralUtility.setTypeFace(JourneyEvents.this));
			
			tvText.setText(journeyStepsText.get(position));
			
			switch (position) {
			case 0:
				tvCount.setText("1/"+journeyStepsText.size());
		    	tvStepTitle.setText("Your Letter no. "+letterNumber);
		    	tvComplete.setText("READ LETTER");
	    		tvComplete.setTextColor(Color.parseColor("#088A08"));
		    	if(userJourneyModel.isLetterRead()>TableAttributes.STATUS_VALUE_1)
		    	{
		    		tvComplete.setText("COMPLETED");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));
		    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
		    		tvComplete.setCompoundDrawablePadding(5);
		    	}
		    	
				break;

			case 1:
				tvCount.setText("2/"+journeyStepsText.size());
		    	tvStepTitle.setText("Goal");
		    	if(userJourneyModel.isLetterRead()!=TableAttributes.STATUS_VALUE_0)
		    	{
		    		tvComplete.setText("START");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));		    		
		    	}
		    	else
		    	{
		    		tvComplete.setText("LOCKED");
		    		tvComplete.setTextColor(Color.parseColor("#d9d9d9"));	
		    	}
		    	if(userJourneyModel.isChallengeAccepted()==TableAttributes.STATUS_VALUE_1)
		    	{
		    		tvComplete.setText("IN PROGRESS");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));	
		    	}
		    	if(habitId==TableAttributes.goldenChallengeID)
		    	{
		    		if(userJourneyModel.getGoldenGoalStatus()>= TableAttributes.CHALLENCE_COMPLETED)
			    	{
			    		tvComplete.setText("COMPLETED");
			    		tvComplete.setTextColor(Color.parseColor("#088A08"));
			    		tvComplete.setCompoundDrawablePadding(5);
			    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
			    	}
		    	}
		    	else
		    	{
		    		if(userJourneyModel.getGoalStatus()>= TableAttributes.CHALLENCE_COMPLETED)
			    	{
			    		tvComplete.setText("COMPLETED");
			    		tvComplete.setTextColor(Color.parseColor("#088A08"));
			    		tvComplete.setCompoundDrawablePadding(5);
			    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
			    	}
		    	}
		    	
				break;
				
			case 2:
				tvCount.setText("3/"+journeyStepsText.size());
		    	tvStepTitle.setText("One-Time Action");
		    	if(userJourneyModel.isChallengeAccepted()==TableAttributes.STATUS_VALUE_1)
		    	{
		    		tvComplete.setText("START");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));		    		
		    	}
		    	else
		    	{
		    		tvComplete.setText("LOCKED");
		    		tvComplete.setTextColor(Color.parseColor("#d9d9d9"));	
		    	}
		    	if(userJourneyModel.getActionStatus()>=TableAttributes.STATUS_VALUE_1)
		    	{
		    		tvComplete.setText("COMPLETED");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));	
		    		tvComplete.setCompoundDrawablePadding(5);
		    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
		    	}
				break;
				
			case 3:
				tvCount.setText("4/"+journeyStepsText.size());
		    	tvStepTitle.setText("Motivator");
		    	tvComplete.setText("LOCKED");
		    	if(userJourneyModel.getActionStatus()!=TableAttributes.STATUS_VALUE_0)
		    	{
		    		tvComplete.setText("READ");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));
		    	}
		    	else
		    	{
		    		tvComplete.setText("LOCKED");
		    		tvComplete.setTextColor(Color.parseColor("#d9d9d9"));	
		    	}
		    	if(userJourneyModel.getMotivationStatus()>=TableAttributes.STATUS_VALUE_1)
		    	{
		    		tvComplete.setText("COMPLETED");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));	
		    		tvComplete.setCompoundDrawablePadding(5);
		    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
		    	}
		    
				break;
				
			case 4:
				tvCount.setText("5/"+journeyStepsText.size());
		    	tvStepTitle.setText("One-Time Action");
		    	tvComplete.setText("LOCKED");
		    	if(userJourneyModel.getActionStatus() ==TableAttributes.STATUS_VALUE_0)
		    	{
		    		tvComplete.setTextColor(Color.parseColor("#d9d9d9"));
		    		tvComplete.setText("LOCKED");
		    	}
		    	else if(userJourneyModel.getActionStatus() ==TableAttributes.STATUS_VALUE_1)
		    	{
		    		tvComplete.setText("START");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));
		    	}
		    	else if(userJourneyModel.getActionStatus() >TableAttributes.STATUS_VALUE_1)
		    	{
		    		tvComplete.setText("COMPLETED");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));	
		    		tvComplete.setCompoundDrawablePadding(5);
		    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
		    	}
				break;
				
			case 5:
				tvCount.setText("6/"+journeyStepsText.size());
		    	tvStepTitle.setText("Motivator");
		    	tvComplete.setText("LOCKED");
		    	if(userJourneyModel.getMotivationStatus() < TableAttributes.STATUS_VALUE_1)
		    	{
		    		tvComplete.setText("LOCKED");
		    		tvComplete.setTextColor(Color.parseColor("#d9d9d9"));	
		    	}
		    	else if(userJourneyModel.getMotivationStatus()==TableAttributes.STATUS_VALUE_1)
		    	{
		    		tvComplete.setText("READ");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));
		    	}
		    	else if(userJourneyModel.getMotivationStatus()>TableAttributes.STATUS_VALUE_1)
		    	{
		    		tvComplete.setText("COMPLETED");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));	
		    		tvComplete.setCompoundDrawablePadding(5);
		    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
		    	}
				break;
				
			case 6:
				tvCount.setText("7/"+journeyStepsText.size());
		    	tvStepTitle.setText("Motivator");
		    	tvComplete.setText("LOCKED");
		    	if(userJourneyModel.getMotivationStatus() < TableAttributes.STATUS_VALUE_2)
		    	{
		    		tvComplete.setText("LOCKED");
		    		tvComplete.setTextColor(Color.parseColor("#d9d9d9"));	
		    	}
		    	else if(userJourneyModel.getMotivationStatus()==TableAttributes.STATUS_VALUE_2)
		    	{
		    		tvComplete.setText("READ");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));
		    	}
		    	else if(userJourneyModel.getMotivationStatus()>TableAttributes.STATUS_VALUE_2)
		    	{
		    		tvComplete.setText("COMPLETED");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));	
		    		tvComplete.setCompoundDrawablePadding(5);
		    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
		    	}
				break;
				
			case 7:
				tvCount.setText("8/"+journeyStepsText.size());
		    	tvStepTitle.setText("Motivator");
		    	tvComplete.setText("LOCKED");
		    	if(userJourneyModel.getMotivationStatus() < 3)
		    	{
		    		tvComplete.setText("LOCKED");
		    		tvComplete.setTextColor(Color.parseColor("#d9d9d9"));	
		    	}
		    	else if(userJourneyModel.getMotivationStatus()==3)
		    	{
		    		tvComplete.setText("READ");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));
		    	}
		    	else if(userJourneyModel.getMotivationStatus()>3)
		    	{
		    		tvComplete.setText("COMPLETED");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));	
		    		tvComplete.setCompoundDrawablePadding(5);
		    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
		    	}
				break;
				
			case 8:
				tvCount.setText("9/"+journeyStepsText.size());
		    	tvStepTitle.setText("Motivator");
		    	tvComplete.setText("LOCKED");
		    	if(userJourneyModel.getMotivationStatus() < 4)
		    	{
		    		tvComplete.setText("LOCKED");
		    		tvComplete.setTextColor(Color.parseColor("#d9d9d9"));	
		    	}
		    	else if(userJourneyModel.getMotivationStatus()==4)
		    	{
		    		tvComplete.setText("READ");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));
		    	}
		    	else if(userJourneyModel.getMotivationStatus()>4)
		    	{
		    		tvComplete.setText("COMPLETED");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));	
		    		tvComplete.setCompoundDrawablePadding(5);
		    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
		    	}
				break;
				
			case 9:
				tvCount.setText("10/"+journeyStepsText.size());
		    	tvStepTitle.setText("Motivator");
		    	tvComplete.setText("LOCKED");
		    	if(userJourneyModel.getMotivationStatus() < 5)
		    	{
		    		tvComplete.setText("LOCKED");
		    		tvComplete.setTextColor(Color.parseColor("#d9d9d9"));	
		    	}
		    	else if(userJourneyModel.getMotivationStatus()==5)
		    	{
		    		tvComplete.setText("READ");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));
		    	}
		    	else if(userJourneyModel.getMotivationStatus()>5)
		    	{
		    		tvComplete.setText("COMPLETED");
		    		tvComplete.setTextColor(Color.parseColor("#088A08"));	
		    		tvComplete.setCompoundDrawablePadding(5);
		    		tvComplete.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right_green, 0, 0, 0);
		    	}
				break;
			
			}
			return convertView;
		}
    	
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	userJourneyModel = getData.getJourneyHabit(userName, habitId);
    	lvStep.setAdapter(new JourneyStepAdapter());
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
	switch (item.getItemId()) {
	        
	case android.R.id.home:
		onBackPressed();
		return true;
	}
	return super.onOptionsItemSelected(item);
    }
}

