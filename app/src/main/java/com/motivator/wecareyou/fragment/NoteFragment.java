package com.motivator.wecareyou.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.motivator.careplan.CarePlanHomeActivity;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.model.JourneyData;
import com.motivator.model.JourneyHabitModel;
import com.motivator.wecareyou.DataEntryActivity;
import com.motivator.wecareyou.Goal;
import com.motivator.wecareyou.Goal_GoldenChallenge;
import com.motivator.wecareyou.Letter;
import com.motivator.wecareyou.Motivator;
import com.motivator.wecareyou.OneTimeAction;
import com.motivator.wecareyou.R;

public class NoteFragment extends Fragment {
	
	
	Context mContext;
	ImageView imvNoteIcon;
	TextView tvNoteTitle, tvNoteDesc;
	LinearLayout llStatus,ll_note;
	
	TextView tvDay1, tvDay2, tvDay3;
	View progress1, progress2;
	
	JourneyHabitModel userJourneyModel = new JourneyHabitModel();
	
	int habitId, noteNum;
	String fragTag = "";
	String userName, journey_step, noteType, noteTitle, noteDesc;
	
//	public static final int INTENT_LETTER = 100;
//	public static final int INTENT_GOAL = 200;
//	public static final int INTENT_ACTION = 300;
//	public static final int INTENT_MOTIVATOR = 400;

	public static final  String ARG_NOTE_NUM = "ARG_NOTE_NUM";
	public static final  String ARG_NOTE_TYPE = "ARG_NOTE_TYPE";
	public static final  String ARG_NOTE_TITLE = "ARG_NOTE_TITLE";
	public static final  String ARG_NOTE_DESC = "ARG_NOTE_DESC";
	
	//Arg values
	public static final  String NOTE_LETTER = "NOTE_LETTER";
	public static final  String NOTE_GOAL = "NOTE_GOAL";
	public static final  String NOTE_ACTION = "NOTE_ACTION";
	public static final  String NOTE_MOTIVATOR = "NOTE_MOTIVATOR";
	public static final String NOTE_DATA_ENTRY="NOTE_DATA_ENTRY";
	public static final String NOTE_CARE_PLAN="NOTE_CARE_PLAN";
			
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
    {
    	mContext = getActivity();
    	fragTag = getTag();
    	userName = GeneralUtility.getPreferences(getActivity(), AppsConstant.user_name);
        // Inflate the layout for this fragment
    	View fragmentView = inflater.inflate(R.layout.notification_frag, container, false);
    	imvNoteIcon= (ImageView)fragmentView.findViewById(R.id.imv_note_icon);
    	tvNoteTitle = (TextView)fragmentView.findViewById(R.id.tv_note1);
    	tvNoteDesc = (TextView)fragmentView.findViewById(R.id.tv_note2);
    	llStatus = (LinearLayout)fragmentView.findViewById(R.id.ll_status);
    	
    	tvDay1 = (TextView)fragmentView.findViewById(R.id.tv_goal_day1);
		tvDay2 = (TextView)fragmentView.findViewById(R.id.tv_goal_day2);
		tvDay3 = (TextView)fragmentView.findViewById(R.id.tv_goal_day3);
		
		progress1 = (View)fragmentView.findViewById(R.id.view_progress1);
		progress2 = (View)fragmentView.findViewById(R.id.view_progress2);

		ll_note=(LinearLayout) fragmentView.findViewById(R.id.ll_note);

    	habitId = getArguments().getInt(AppsConstant.h_id);
    	journey_step = getArguments().getString(AppsConstant.selected_journey_step);
    	noteType = getArguments().getString(ARG_NOTE_TYPE);
    	noteNum = getArguments().getInt(ARG_NOTE_NUM);
    	noteTitle = getArguments().getString(ARG_NOTE_TITLE);
    	noteDesc = getArguments().getString(ARG_NOTE_DESC);
    	
    	tvNoteTitle.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvNoteDesc.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	
    	tvNoteTitle.setText(noteTitle);
    	tvNoteDesc.setText(userName+", "+noteDesc);
    	
    	if(noteType.equalsIgnoreCase(NOTE_LETTER))
    	{
    		imvNoteIcon.setImageResource(R.drawable.letter_icon);
    	}
    	else 
    		imvNoteIcon.setImageResource(JourneyData.getNoteImg(habitId));
    		//imvNoteIcon.setImageResource(android.R.color.transparent);


		if(noteType.equalsIgnoreCase(NOTE_DATA_ENTRY)){
			Log.d("sunil","data_entry");
			ll_note.setVisibility(View.VISIBLE);
		}
		if(noteType.equalsIgnoreCase(NOTE_CARE_PLAN)){
			Log.d("sunil","care plan");
			ll_note.setVisibility(View.VISIBLE);
		}
    	if(noteType.equalsIgnoreCase(NOTE_GOAL))
    	{
    		llStatus.setVisibility(View.VISIBLE);    		
    		GetData getData = new GetData(getActivity());
    		userJourneyModel = getData.getJourneyHabit(userName, habitId);
    		int inProgress = userJourneyModel.isChallengeAccepted();
    		if(inProgress == TableAttributes.STATUS_VALUE_1)
    		{
    			int taskCompleted = userJourneyModel.getGoalStatus();
    			
    			switch (taskCompleted) {
    			case 0:
    				tvDay1.setBackgroundResource(R.drawable.circle_gray);
    				progress1.setVisibility(View.INVISIBLE);
    				tvDay2.setBackgroundResource(R.drawable.circle_gray);
    				progress2.setVisibility(View.INVISIBLE);
    				tvDay3.setBackgroundResource(R.drawable.circle_gray);
    				break;
    			case 1:
    				tvDay1.setBackgroundResource(R.drawable.circle_green_lite);
    				progress1.setVisibility(View.VISIBLE);
    				tvDay2.setBackgroundResource(R.drawable.circle_gray);
    				progress2.setVisibility(View.INVISIBLE);
    				tvDay3.setBackgroundResource(R.drawable.circle_gray);
    				break;

    			case 2:
    				tvDay1.setBackgroundResource(R.drawable.circle_green_lite);
    				progress1.setVisibility(View.VISIBLE);
    				tvDay2.setBackgroundResource(R.drawable.circle_green_lite);
    				progress2.setVisibility(View.VISIBLE);
    				tvDay3.setBackgroundResource(R.drawable.circle_gray);
    				break;
    				
    			case 3:
    				tvDay1.setBackgroundResource(R.drawable.circle_green_lite);
    				progress1.setVisibility(View.VISIBLE);
    				tvDay2.setBackgroundResource(R.drawable.circle_green_lite);
    				progress2.setVisibility(View.VISIBLE);
    				tvDay3.setBackgroundResource(R.drawable.circle_green_lite);
    				break;
    			default:
    				tvDay1.setBackgroundResource(R.drawable.circle_green_lite);
    				progress1.setVisibility(View.VISIBLE);
    				tvDay2.setBackgroundResource(R.drawable.circle_green_lite);
    				progress2.setVisibility(View.VISIBLE);
    				tvDay3.setBackgroundResource(R.drawable.circle_green_lite);
    				break;
    			}
    		}
    	}
    	else 
    		llStatus.setVisibility(View.GONE);
    	
    	fragmentView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				Intent intent;
				if(noteType.equalsIgnoreCase(NOTE_DATA_ENTRY)){
					intent=new Intent(getActivity(), DataEntryActivity.class);
					getActivity().startActivity(intent);
				}
				if(noteType.equalsIgnoreCase(NOTE_CARE_PLAN)){
					intent=new Intent(getActivity(), CarePlanHomeActivity.class);
					getActivity().startActivity(intent);
				}
				if(noteType.equalsIgnoreCase(NOTE_LETTER))
				{
					//AppsConstant.toRemoveNote = false;
					intent = new Intent(getActivity(), Letter.class);
					intent.putExtra(AppsConstant.selected_journey_step, journey_step);
					intent.putExtra(AppsConstant.h_id, habitId);
					intent.putExtra(Letter.Letter_Num, noteNum);
					intent.putExtra(AppsConstant.calling_frag, fragTag);
					//getActivity().startActivityForResult(intent, INTENT_LETTER);
					getActivity().startActivity(intent);
				}
				else if(noteType.equalsIgnoreCase(NOTE_GOAL))
				{
					//AppsConstant.toRemoveNote = false;
					if(habitId== TableAttributes.goldenChallengeID)
						intent = new Intent(getActivity(), Goal_GoldenChallenge.class);
					else
						intent = new Intent(getActivity(), Goal.class);
					intent.putExtra(AppsConstant.selected_journey_step, journey_step);
					intent.putExtra(AppsConstant.h_id, habitId);
					intent.putExtra(AppsConstant.calling_frag, fragTag);
					startActivity(intent);
				}
				else if(noteType.equalsIgnoreCase(NOTE_ACTION))
				{
					//AppsConstant.toRemoveNote = false;
					intent = new Intent(getActivity(), OneTimeAction.class);
					intent.putExtra(AppsConstant.selected_journey_step, journey_step);
					intent.putExtra(AppsConstant.h_id, habitId);
					intent.putExtra(OneTimeAction.Action_Num, noteNum);
					intent.putExtra(AppsConstant.calling_frag, fragTag);
					//getActivity().startActivityForResult(intent,INTENT_ACTION);
					getActivity().startActivity(intent);
				}
				else if(noteType.equalsIgnoreCase(NOTE_MOTIVATOR))
				{
					//AppsConstant.toRemoveNote = false;
					intent = new Intent(getActivity(), Motivator.class);
					intent.putExtra(AppsConstant.selected_journey_step, journey_step);
					intent.putExtra(AppsConstant.h_id, habitId);
					intent.putExtra(Motivator.Motivator_Num, 1);
					intent.putExtra(AppsConstant.calling_frag, fragTag);
					//getActivity().startActivityForResult(intent,INTENT_MOTIVATOR);
					getActivity().startActivity(intent);
				}
				
			}
		});
    	
    	return fragmentView;
    }
    
}