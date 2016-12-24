package com.motivator.wecareyou.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.model.UserRitualModel;
import com.motivator.wecareyou.MyHabits;
import com.motivator.wecareyou.R;

public class FirstFragment extends Fragment implements OnClickListener{
	
	String userName;
	String selectedRitual;

	RelativeLayout rllInfo;
	ImageView imvClick;
	TextView tvRitualName,tvRitualTime, tvInfo;
	ImageView imvRitual;

	GetData getData;
	UserRitualModel userRitual = new UserRitualModel();
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		getData = new GetData(getActivity());
		
		userName = GeneralUtility.getPreferences(getActivity(), AppsConstant.user_name);
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.rituals_home_img, container, false);

		userRitual = getData.getRitualsDetails(userName, selectedRitual);
		//intialize ui views
		imvRitual = (ImageView)view.findViewById(R.id.imv_ritual_img);
		tvRitualName= (TextView)view.findViewById(R.id.tv_ritual_name);
		tvRitualTime = (TextView)view.findViewById(R.id.tv_ritual_time);

		rllInfo = (RelativeLayout)view.findViewById(R.id.rll_info);
		imvClick = (ImageView)view.findViewById(R.id.imv_click);
		tvInfo = (TextView)view.findViewById(R.id.tv_info);

		tvRitualName.setTypeface(GeneralUtility.setTypeFace(getActivity()));
		tvRitualTime.setTypeface(GeneralUtility.setTypeFace(getActivity()));
		tvInfo.setTypeface(GeneralUtility.setTypeFace(getActivity()));

		imvClick.setOnClickListener(this);
		//SetVisibility of views
		setUpVisibilityOfUIViews();

		return view;
	}



	private void setUpVisibilityOfUIViews()
	{
		rllInfo.setVisibility(View.VISIBLE);
    	imvClick.setVisibility(View.VISIBLE);
    	tvInfo.setVisibility(View.VISIBLE);
        rllInfo.setAlpha(0.4f);

        String journey_mode = GeneralUtility.getPreferences(getActivity(), "journey_mode");
        int journey = Integer.parseInt(journey_mode);
        tvInfo.setText("Welcome "+userName+"! Let's set up your Journey. Just tap on the circle above.");

        tvRitualName.setText(userRitual.getRitualName()+"\n"+userRitual.getRitualTime());
  		tvRitualTime.setText(userRitual.getRitualTime());

        switch (journey) {
		case AppsConstant.FEEL_ENERGIZED:
			selectedRitual = AppsConstant.MORNING_RITUAL;
			imvRitual.setImageResource(R.drawable.morning_ritual);
			break;
		case AppsConstant.LOSE_WEIGHT:
			selectedRitual = AppsConstant.LUNCH_RITUAL;
			imvRitual.setImageResource(R.drawable.lunch_ritual);
			break;

		case AppsConstant.SLEEP_BETTER:
			selectedRitual = AppsConstant.EVENING_RITUAL;
			imvRitual.setImageResource(R.drawable.evening_ritual);
			break;
		}

	}



	@Override
	public void onClick(View v)
	{
		Intent intent;
		switch (v.getId()) {
		case R.id.imv_click:
			intent = new Intent(getActivity(), MyHabits.class);
			intent.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
			intent.putExtra("is_reminder_call", false);
			getActivity().startActivity(intent);
			getActivity().finish();
			break;
		}
	}
}

