package com.motivator.wecareyou.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.PutData;
import com.motivator.database.TableAttributes;
import com.motivator.model.JourneyData;
import com.motivator.model.JourneyHabitModel;
import com.motivator.model.JourneyModel;
import com.motivator.wecareyou.JourneyEvents;
import com.motivator.wecareyou.Letter;
import com.motivator.wecareyou.R;

public class JourneyIntresting extends Fragment implements OnClickListener{
	
	
	Context mContext;
	String userName;
	RelativeLayout rll1, rll2, rll3, rll4, rll5;
	TextView tvEventsAcheived, tvStatus;
	TextView tvStatus_step1, tvStatus_step2, tvStatus_step3, tvStatus_step4, tvStatus_step5;
	TextView tvTitle1, tvTitle2, tvTitle3, tvTitle4, tvTitle5;
	ImageView imvStepCompleted;
	
	PutData putData;
	GetData getData;
	JourneyModel userJourney = new JourneyModel();
	JourneyHabitModel journeyHabit = new JourneyHabitModel();
	
	boolean isClikableRll2,isClikableRll3,isClikableRll4,isClikableRll5;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	
    	mContext = getActivity();
    	
    	putData = new PutData(getActivity());
    	getData = new GetData(getActivity());
    	userName = GeneralUtility.getPreferences(getActivity(), AppsConstant.user_name);
        // Inflate the layout for this fragment
    	View fragmentView = inflater.inflate(R.layout.journey_all_step, container, false);

    	rll1 = (RelativeLayout)fragmentView.findViewById(R.id.rll1);
    	rll2 = (RelativeLayout)fragmentView.findViewById(R.id.rll2);
    	rll3 = (RelativeLayout)fragmentView.findViewById(R.id.rll3);
    	rll4 = (RelativeLayout)fragmentView.findViewById(R.id.rll4);
    	rll5 = (RelativeLayout)fragmentView.findViewById(R.id.rll5);

    	tvEventsAcheived = (TextView)fragmentView.findViewById(R.id.tv_events_achieved);
    	tvStatus = (TextView)fragmentView.findViewById(R.id.tv_status);

    	tvTitle1 = (TextView)fragmentView.findViewById(R.id.tv_title1);
    	tvTitle2 = (TextView)fragmentView.findViewById(R.id.tv_title2);
    	tvTitle3 = (TextView)fragmentView.findViewById(R.id.tv_title3);
    	tvTitle4 = (TextView)fragmentView.findViewById(R.id.tv_title4);
    	tvTitle5 = (TextView)fragmentView.findViewById(R.id.tv_title5);
    	tvStatus_step1 = (TextView)fragmentView.findViewById(R.id.tv_status_step1);
    	tvStatus_step2 = (TextView)fragmentView.findViewById(R.id.tv_status_step2);
    	tvStatus_step3 = (TextView)fragmentView.findViewById(R.id.tv_status_step3);
    	tvStatus_step4 = (TextView)fragmentView.findViewById(R.id.tv_status_step4);
    	tvStatus_step5 = (TextView)fragmentView.findViewById(R.id.tv_status_step5);

    	imvStepCompleted = (ImageView)fragmentView.findViewById(R.id.imv_step_completed);

    	tvEventsAcheived.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvStatus.setTypeface(GeneralUtility.setTypeFace(getActivity()));

    	tvTitle1.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvTitle2.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvTitle3.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvTitle4.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvTitle5.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvStatus_step1.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvStatus_step2.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvStatus_step3.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvStatus_step4.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvStatus_step5.setTypeface(GeneralUtility.setTypeFace(getActivity()));

    	rll1.setOnClickListener(this);
    	rll2.setOnClickListener(this);
    	rll3.setOnClickListener(this);
    	rll4.setOnClickListener(this);
    	rll5.setOnClickListener(this);


    	/*int numOfEvents = 27;
    	long row = putData.insertJourneyInfo(userName, AppsConstant.Interesting_Journey,numOfEvents);
    	if(row>0)
    	{
    		putData.insertJourneyHabit(userName, AppsConstant.Interesting_Journey, TableAttributes.drinkWaterId);
    		putData.insertJourneyHabit(userName, AppsConstant.Interesting_Journey, TableAttributes.greatBreakFastID);
    		putData.insertJourneyHabit(userName, AppsConstant.Interesting_Journey, TableAttributes.danceYourWayID);

    		putData.insertJourneyHabit(userName, AppsConstant.Interesting_Journey, TableAttributes.goldenChallengeID);
    		putData.insertJourneyHabit(userName, AppsConstant.Interesting_Journey, TableAttributes.SecretLetterID);
    	}*/
//    	putData.insertJourneyInfo(userName, AppsConstant.CELEBRATING_HEALTHY_EATING, 7);
//    	putData.insertJourneyInfo(userName, AppsConstant.A_FABULOUS_NIGHT, 7);

    	updateAllStepInfo();

    	return fragmentView;
    }

	private void updateAllStepInfo()
	{
		if(AppsConstant.SELECTED_JOURNEY.equalsIgnoreCase(AppsConstant.Interesting_Journey))
    	{
			//get journey info
    		userJourney = getData.getJourneyInfo(userName, AppsConstant.Interesting_Journey);
    		//show total events achieved status
    		tvEventsAcheived.setText(userJourney.getEventsAchieved()+"/"+userJourney.getTotalEvents()+" events achieved");
    		Double s = ((double)userJourney.getEventsAchieved()/userJourney.getTotalEvents()) * 100;
    		int status = s.intValue();
    		tvStatus.setText(status+"% Completed");

    		//Check Step 1 Status
    		if(userJourney.getStatusStep1() == TableAttributes.STATUS_VALUE_0)
    			tvStatus_step1.setText("Not yet started");
    		else
    			tvStatus_step1.setText(userJourney.getStatusStep1()+"/"+ JourneyData.getJourneySteps(TableAttributes.drinkWaterId)+" achieved");

    		//Check if the user completed the goal and action of first step
    		journeyHabit = getData.getJourneyHabit(userName, TableAttributes.drinkWaterId);
    		int goal1 = journeyHabit.getGoalStatus();
    		int actionCompleted = journeyHabit.getActionStatus();

    		if(goal1>= TableAttributes.CHALLENCE_COMPLETED)
    		{
    			isClikableRll2 = true;
    			tvStatus_step2.setTextColor(Color.parseColor("#088A08"));
    			if(userJourney.getStatusStep2() == TableAttributes.STATUS_VALUE_0)
        			tvStatus_step2.setText("Not yet started");
    			else
    				tvStatus_step2.setText(userJourney.getStatusStep2()+"/"+ JourneyData.getJourneySteps(TableAttributes.greatBreakFastID)+" achieved");
    		}

    		if(goal1>= TableAttributes.CHALLENCE_COMPLETED && actionCompleted >= TableAttributes.STATUS_VALUE_1)
    		{
    			isClikableRll3 = true;
    			tvStatus_step3.setTextColor(Color.parseColor("#088A08"));
    			if(userJourney.getStatusStep3() == TableAttributes.STATUS_VALUE_0)
        			tvStatus_step3.setText("Not yet started");
    			else
    				tvStatus_step3.setText(userJourney.getStatusStep3()
    						+"/"+ JourneyData.getJourneySteps(TableAttributes.danceYourWayID)+" achieved");
    		}

    		if(goal1>= TableAttributes.CHALLENCE_COMPLETED && actionCompleted >= TableAttributes.STATUS_VALUE_1)
    		{
    			isClikableRll4 = true;
    			tvStatus_step4.setTextColor(Color.parseColor("#088A08"));
    			if(userJourney.getStatusStep4() == TableAttributes.STATUS_VALUE_0)
        			tvStatus_step4.setText("Not yet started");
    			else
    				tvStatus_step4.setText(userJourney.getStatusStep4()
    						+"/"+ JourneyData.getJourneySteps(TableAttributes.goldenChallengeID)+" achieved");
    		}

    		if(goal1>= TableAttributes.CHALLENCE_COMPLETED && actionCompleted >= TableAttributes.STATUS_VALUE_1)
    		{
    			isClikableRll5 = true;
    			tvStatus_step5.setTextColor(Color.parseColor("#088A08"));
    			if(userJourney.getStatusStep5() == TableAttributes.STATUS_VALUE_0)
        			tvStatus_step5.setText("Not yet started");
    			else
    				tvStatus_step5.setText(userJourney.getStatusStep5()
    						+"/"+ JourneyData.getJourneySteps(TableAttributes.SecretLetterID)+" achieved");
    		}


    	}
    	else if(AppsConstant.SELECTED_JOURNEY.equalsIgnoreCase(AppsConstant.Enjoying_Health_Eating))
    	{

    	}
    	else if(AppsConstant.SELECTED_JOURNEY.equalsIgnoreCase(AppsConstant.A_Pleasant_Night))
    	{

    	}

	}

	@Override
	public void onClick(View v)
	{
		Intent i;
		switch (v.getId()) {
		case R.id.rll1:
			i = new Intent(getActivity(), JourneyEvents.class);
			i.putExtra(AppsConstant.selected_journey_step, TableAttributes.STATUS_STEP1);
			i.putExtra(AppsConstant.h_id, TableAttributes.drinkWaterId);
			i.putExtra(Letter.Letter_Num, 1);
			getActivity().startActivity(i);
			break;
		case R.id.rll2:
			if(isClikableRll2)
			{
				i = new Intent(getActivity(), JourneyEvents.class);
				i.putExtra(AppsConstant.selected_journey_step, TableAttributes.STATUS_STEP2);
				i.putExtra(AppsConstant.h_id, TableAttributes.greatBreakFastID);
				i.putExtra(Letter.Letter_Num, 2);
				getActivity().startActivity(i);
			}
			else
				Toast.makeText(getActivity(), R.string.locked, 5).show();

			break;
		case R.id.rll3:
			if(isClikableRll3)
			{
				i = new Intent(getActivity(), JourneyEvents.class);
				i.putExtra(AppsConstant.selected_journey_step, TableAttributes.STATUS_STEP3);
				i.putExtra(AppsConstant.h_id, TableAttributes.danceYourWayID);
				i.putExtra(Letter.Letter_Num, 3);
				getActivity().startActivity(i);
			}
			else
				Toast.makeText(getActivity(), R.string.locked, 5).show();
			break;

		case R.id.rll4:
			if(isClikableRll4)
			{
				i = new Intent(getActivity(), JourneyEvents.class);
				i.putExtra(AppsConstant.selected_journey_step, TableAttributes.STATUS_STEP4);
				i.putExtra(AppsConstant.h_id, TableAttributes.goldenChallengeID);
				i.putExtra(Letter.Letter_Num, 4);
				getActivity().startActivity(i);
			}
			else
				Toast.makeText(getActivity(), R.string.locked, 5).show();

			break;

		case R.id.rll5:
			if(isClikableRll5)
			{
				i = new Intent(getActivity(), JourneyEvents.class);
				i.putExtra(AppsConstant.selected_journey_step, TableAttributes.STATUS_STEP5);
				i.putExtra(AppsConstant.h_id, TableAttributes.SecretLetterID);
				i.putExtra(Letter.Letter_Num, 5);
				getActivity().startActivity(i);
			}
			else
				Toast.makeText(getActivity(), R.string.locked, 5).show();
			break;

		}
		
	}
    
   @Override
   public void onResume() {
	   	super.onResume();
	   	updateAllStepInfo();
   }
}