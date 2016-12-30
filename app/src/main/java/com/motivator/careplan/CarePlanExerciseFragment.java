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
import android.widget.EditText;
import android.widget.Toast;

import com.motivator.wecareyou.R;

public class CarePlanExerciseFragment extends Fragment implements OnClickListener{
	
	Activity activity;
	Button skip,done;
	
	boolean iswalk=false,iscardio=false,isyoga=false;
	
	Button walk_btn,cardio_btn,yoga_btn;
	EditText walk_et,cardio_et,yoga_et;
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
		View infView=inflater.inflate(R.layout.care_plan_exercise_fragment, container,false);
		return infView;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		skip=(Button) view.findViewById(R.id.skip);
		done=(Button) view.findViewById(R.id.done);
		
		walk_btn=(Button) view.findViewById(R.id.walk_btn);
		cardio_btn=(Button) view.findViewById(R.id.cardio_btn);
		yoga_btn=(Button) view.findViewById(R.id.yoga_btn);
		
		walk_et=(EditText) view.findViewById(R.id.walk_et);
		cardio_et=(EditText) view.findViewById(R.id.Cardio_et);
		yoga_et=(EditText) view.findViewById(R.id.yoga_et);
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		skip.setOnClickListener(this);
		done.setOnClickListener(this);
		walk_btn.setOnClickListener(this);
		cardio_btn.setOnClickListener(this);
		yoga_btn.setOnClickListener(this);		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) {
		case R.id.done:
			CarePlanFragmentToActivtiy cpha= (CarePlanFragmentToActivtiy) activity;
			String exer="";
			if(iswalk){
				if(walk_et.getText().toString().length()>0){
					exer+="Walk "+walk_et.getText().toString()+" min daily:";
				}
				else{
					Toast.makeText(getActivity(), "Please Enter Walk Duration", Toast.LENGTH_SHORT).show();
				}
			}
			if(iscardio){
				if(cardio_et.getText().toString().length()>0){
					exer+="Cardio "+cardio_et.getText().toString()+" min daily:";
				}
				else{
					Toast.makeText(getActivity(), "Please Enter Cardio Duration", Toast.LENGTH_SHORT).show();
				}
			}
			if(isyoga){
				if(yoga_et.getText().toString().length()>0){
					exer+="Yoga "+yoga_et.getText().toString()+" min daily";
				}
				else{
					Toast.makeText(getActivity(), "Please Enter Yoga Duration", Toast.LENGTH_SHORT).show();
				}
			}
			cpha.FragmentToActivity("exercise",exer);
			break;
		case R.id.skip:
			CarePlanFragmentToActivtiy cpha1= (CarePlanFragmentToActivtiy) activity;
			cpha1.FragmentToActivity("exercise","skip");
			break;
		case R.id.walk_btn:
					WalkOnOffMethod();
			break;
		case R.id.cardio_btn:
					CardioOnOffMethod();
			break;
		case R.id.yoga_btn:
					YogaOnOffMethod();
			break;
		default:
			break;
		}
	}
	
	public void WalkOnOffMethod(){
		if(iswalk){
			iswalk=false;
			walk_btn.setBackground(getResources().getDrawable(R.drawable.non_selected_care_plan));
		}
		else{
			iswalk=true;
			walk_btn.setBackground(getResources().getDrawable(R.drawable.selected_care_plan));
		}
	}
	public void CardioOnOffMethod(){
		if(iscardio){
			iscardio=false;
			cardio_btn.setBackground(getResources().getDrawable(R.drawable.non_selected_care_plan));
		}
		else{
			iscardio=true;
			cardio_btn.setBackground(getResources().getDrawable(R.drawable.selected_care_plan));
		}
	}
	public void YogaOnOffMethod(){
		if(isyoga){
			isyoga=false;
			yoga_btn.setBackground(getResources().getDrawable(R.drawable.non_selected_care_plan));
		}
		else{
			isyoga=true;
			yoga_btn.setBackground(getResources().getDrawable(R.drawable.selected_care_plan));
		}
	}

}
