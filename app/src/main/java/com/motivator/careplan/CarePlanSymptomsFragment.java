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

public class CarePlanSymptomsFragment extends Fragment implements OnClickListener{
	
	Activity activity;
	Button skip,done;
	boolean isSleep=true,isFatigue=true,isJoint=true;
	Button sleep_btn,fatigue_btn,joint_btn;
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
		View infView=inflater.inflate(R.layout.care_plan_symptoms_fragment, container,false);
		return infView;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		skip=(Button) view.findViewById(R.id.skip);
		done=(Button) view.findViewById(R.id.done);
		sleep_btn=(Button) view.findViewById(R.id.sleep_btn);
		fatigue_btn=(Button) view.findViewById(R.id.fatigue_btn);
		joint_btn=(Button) view.findViewById(R.id.joint_btn);
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		skip.setOnClickListener(this);
		done.setOnClickListener(this);
		sleep_btn.setOnClickListener(this);
		fatigue_btn.setOnClickListener(this);
		joint_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) {
		case R.id.done:
			String symp="";
			if(isJoint){
				if(isFatigue){
					symp+="Joint Pain,";
				}
				else{
					symp+="Joint Pain";
				}
			}
			if(isFatigue){
				if(isSleep){
					symp+="fatigue,";
				}
				else{
					symp+="and fatigue.";
				}
			}
			if(isSleep){
				symp+=" and sleep problem.";
			}
			CarePlanFragmentToActivtiy cpha= (CarePlanFragmentToActivtiy) activity;
			cpha.FragmentToActivity("symptoms",symp);
			break;
		case R.id.skip:
			
			break;
		case R.id.sleep_btn:
					SleepOnOffMethod();
					break;
		case R.id.fatigue_btn:
				FatigueOnOffMethod();
			break;
		case R.id.joint_btn:
				JointOnOffMethod();
			break;
		default:
			break;
		}
	}

	public void SleepOnOffMethod(){
		if(isSleep){
			isSleep=false;
			sleep_btn.setBackground(getResources().getDrawable(R.drawable.uncheck_chk));
		}
		else{
			isSleep=true;
			sleep_btn.setBackground(getResources().getDrawable(R.drawable.checked_chk));
		}
	}
	public void FatigueOnOffMethod(){
		if(isFatigue){
			isFatigue=false;
			fatigue_btn.setBackground(getResources().getDrawable(R.drawable.uncheck_chk));
		}
		else{
			isFatigue=true;
			fatigue_btn.setBackground(getResources().getDrawable(R.drawable.checked_chk));
		}
	}
	public void JointOnOffMethod(){
		if(isJoint){
			isJoint=false;
			joint_btn.setBackground(getResources().getDrawable(R.drawable.uncheck_chk));
		}
		else{
			isJoint=true;
			joint_btn.setBackground(getResources().getDrawable(R.drawable.checked_chk));
		}
	}

}
