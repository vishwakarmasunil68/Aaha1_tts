package com.motivator.careplan;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.motivator.wecareyou.R;

public class CarePlanDoctorVisitFragment extends Fragment{
	
	Activity activity;
	Button yes,no;
	@Override
	@Deprecated
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity=activity;
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View infView=inflater.inflate(R.layout.care_plan_doctor_visit, container,false);
		return infView;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		yes=(Button) view.findViewById(R.id.doctor_visit_yes);
		no=(Button) view.findViewById(R.id.doctor_visit_no);
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CarePlanFragmentToActivtiy cpha= (CarePlanFragmentToActivtiy) activity;
				cpha.FragmentToActivity("doctor","yes");
			}
		});
		
		no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CarePlanFragmentToActivtiy cpha= (CarePlanFragmentToActivtiy) activity;
				cpha.FragmentToActivity("doctor","no");				
			}
		});
		
	}

}
