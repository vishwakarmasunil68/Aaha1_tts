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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.motivator.wecareyou.R;

public class CarePlanMasalFragment extends Fragment implements OnClickListener{
	
	Activity activity;
	
	
	int salt_position=1;
	int sugar_position=1;
	int oil_position=1;
	int[] salt_drawable={R.drawable.main_line_1,R.drawable.main_line_2,R.drawable.main_line_3};
	ImageView salt_increase,salt_decrease,salt_line_image;
	
	ImageView sugar_increase,sugar_decrease,sugar_line_image;
	
	ImageView oil_increase,oil_decrease,oil_line_image;
	LinearLayout salt_decrease_layout,salt_increase_layout,sugar_decrease_layout,sugar_increase_layout,oil_decrease_layout,oil_increase_layout;
	
	Button skip,done;
	
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
		View infView=inflater.inflate(R.layout.care_plan_masala, container,false);
		return infView;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		salt_increase=(ImageView) view.findViewById(R.id.salt_increase);
		salt_decrease=(ImageView) view.findViewById(R.id.salt_decrease);
		salt_line_image=(ImageView) view.findViewById(R.id.salt_line_image);
		
		oil_increase=(ImageView) view.findViewById(R.id.oil_increase);
		oil_decrease=(ImageView) view.findViewById(R.id.oil_decrease);
		oil_line_image=(ImageView) view.findViewById(R.id.oil_main);
		
		sugar_increase=(ImageView) view.findViewById(R.id.sugar_increase);
		sugar_decrease=(ImageView) view.findViewById(R.id.sugar_decrease);
		sugar_line_image=(ImageView) view.findViewById(R.id.sugar_main);
		
		salt_decrease_layout=(LinearLayout) view.findViewById(R.id.salt_decrease_layout);
		salt_increase_layout=(LinearLayout) view.findViewById(R.id.salt_increase_layout);
		
		sugar_decrease_layout=(LinearLayout) view.findViewById(R.id.sugar_decrease_layout);
		sugar_increase_layout=(LinearLayout) view.findViewById(R.id.sugar_increase_layout);
		
		oil_decrease_layout=(LinearLayout) view.findViewById(R.id.oil_decrease_layout);
		oil_increase_layout=(LinearLayout) view.findViewById(R.id.oil_increase_layout);
		
		skip=(Button) view.findViewById(R.id.skip);
		done=(Button) view.findViewById(R.id.done);
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		salt_decrease.setOnClickListener(this);
		salt_increase.setOnClickListener(this);
		sugar_decrease.setOnClickListener(this);
		sugar_increase.setOnClickListener(this);
		oil_decrease.setOnClickListener(this);
		oil_increase.setOnClickListener(this);
		
		oil_decrease_layout.setOnClickListener(this);
		oil_increase_layout.setOnClickListener(this);
		salt_decrease_layout.setOnClickListener(this);
		salt_increase_layout.setOnClickListener(this);
		sugar_decrease_layout.setOnClickListener(this);
		sugar_increase_layout.setOnClickListener(this);
		
		skip.setOnClickListener(this);
		done.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) {
		case R.id.salt_decrease:
						if(salt_position!=0){
							salt_position=salt_position-1;							
							salt_line_image.setImageResource(salt_drawable[salt_position]);
						}
			break;
		case R.id.salt_increase:
						if(salt_position!=2){
							salt_position=salt_position+1;							
							salt_line_image.setImageResource(salt_drawable[salt_position]);
						}
			break;
			
		case R.id.sugar_decrease:
			if(sugar_position!=0){
				sugar_position=sugar_position-1;							
				sugar_line_image.setImageResource(salt_drawable[sugar_position]);
			}
		break;
		case R.id.sugar_increase:
					if(sugar_position!=2){
						sugar_position=sugar_position+1;							
						sugar_line_image.setImageResource(salt_drawable[sugar_position]);
					}
		break;
		
		case R.id.oil_decrease:
			if(oil_position!=0){
				oil_position=oil_position-1;							
				oil_line_image.setImageResource(salt_drawable[oil_position]);
			}
		break;
		case R.id.oil_increase:
			if(oil_position!=2){
				oil_position=oil_position+1;							
				oil_line_image.setImageResource(salt_drawable[oil_position]);
			}
		break;
		////
		case R.id.salt_decrease_layout:
			if(salt_position!=0){
				salt_position=salt_position-1;							
				salt_line_image.setImageResource(salt_drawable[salt_position]);
			}
		break;
		case R.id.salt_increase_layout:
			if(salt_position!=2){
				salt_position=salt_position+1;							
				salt_line_image.setImageResource(salt_drawable[salt_position]);
			}
		break;

		case R.id.sugar_decrease_layout:
			if(sugar_position!=0){
				sugar_position=sugar_position-1;							
				sugar_line_image.setImageResource(salt_drawable[sugar_position]);
			}
			break;
		case R.id.sugar_increase_layout:
			if(sugar_position!=2){
				sugar_position=sugar_position+1;							
				sugar_line_image.setImageResource(salt_drawable[sugar_position]);
			}
		break;
		case R.id.oil_decrease_layout:
			if(oil_position!=0){
				oil_position=oil_position-1;							
				oil_line_image.setImageResource(salt_drawable[oil_position]);
			}
			break;
		case R.id.oil_increase_layout:
			if(oil_position!=2){
				oil_position=oil_position+1;							
				oil_line_image.setImageResource(salt_drawable[oil_position]);
			}
		break;
		case R.id.skip:
			CarePlanFragmentToActivtiy mta1=(CarePlanFragmentToActivtiy) activity;			
			mta1.FragmentToActivity("masala","skip");
			break;
		case R.id.done:
			CarePlanFragmentToActivtiy mta=(CarePlanFragmentToActivtiy) activity;
			String s=getSaltStatus()+":"+getSugarStatus()+":"+getOilStatus();
			mta.FragmentToActivity("masala",s);
			break;
		default:
			break;
		}
		
	}
	public String getOilStatus(){
		switch (oil_position) {
		case 0:return "Reduce oil";
		case 1:return "Maintain oil";
		case 2:return "Increase oil";
		default: return "Normal oil";
		}
	}
	public String getSaltStatus(){
		switch (salt_position) {
		case 0:return "Reduce salt";
		case 1:return "Maintain salt";
		case 2:return "Increase salt";
		default: return "Normal salt";
		}
	}
	public String getSugarStatus(){
		switch (sugar_position) {
		case 0:return "Reduce sugar";
		case 1:return "Maintain sugar";
		case 2:return "Increase sugar";
		default: return "Normal sugar";
		}
	}
	
}
