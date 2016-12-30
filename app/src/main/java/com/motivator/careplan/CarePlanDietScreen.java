package com.motivator.careplan;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.wecareyou.R;

import java.util.ArrayList;
import java.util.List;

public class CarePlanDietScreen extends Fragment implements OnClickListener{
	
	Activity activity;
			int tv_tag=0;
	Button skip,done,add;
	LinearLayout custom_diet;
	EditText diet_et;
	List<TextView> lst_txt=new ArrayList<>();
	
	List<LinearLayout> ll_lst=new ArrayList<>();
	List<LinearLayout> inner_lst=new ArrayList<>();
	List<Button> btn_lst=new ArrayList<>();
	
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
		View infView=inflater.inflate(R.layout.care_plan_diet_screen, container,false);
		return infView;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		custom_diet=(LinearLayout) view.findViewById(R.id.custom_diet);
		skip=(Button) view.findViewById(R.id.skip);
		done=(Button) view.findViewById(R.id.done);
		add=(Button) view.findViewById(R.id.add);
		diet_et=(EditText) view.findViewById(R.id.diet_et);
		
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		
		skip.setOnClickListener(this);
		done.setOnClickListener(this);
		add.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) {		
		case R.id.skip:
			CarePlanFragmentToActivtiy mta1=(CarePlanFragmentToActivtiy) activity;
			mta1.FragmentToActivity("diet","skip");
			break;
		case R.id.done:
			String diet="";
			for(int i=0;i<lst_txt.size();i++){
				if((i+1)==lst_txt.size()){
					diet+=lst_txt.get(i).getText().toString();
				}
				else{
					diet+=lst_txt.get(i).getText().toString()+":";
				}
			}
//			for(TextView text:lst_txt){
//					diet+=text.getText().toString();
//			}
			CarePlanFragmentToActivtiy mta=(CarePlanFragmentToActivtiy) activity;
			mta.FragmentToActivity("diet",diet);
			break;
			
		case R.id.add:
			if(diet_et.getText().toString().length()>0){
				addLayout();
			}
			else{
				Toast.makeText(getActivity(), "Please First Enter", Toast.LENGTH_SHORT).show();
			}
		default:
			break;
		}
		
	}
	public void addLayout(){
		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		params.setMargins(50, 0, 50, 0);
		LinearLayout ll=new LinearLayout(getActivity());
		ll.setGravity(Gravity.CENTER);
		params.gravity = Gravity.CENTER; 
		ll.setLayoutParams(params);
		ll.setOrientation(LinearLayout.HORIZONTAL);
		
		
		LinearLayout inner_layout=new LinearLayout(getActivity());
		LayoutParams inner_params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		inner_params.weight=1;
		inner_layout.setGravity(Gravity.CENTER);
		inner_params.gravity = Gravity.CENTER;        
		inner_layout.setLayoutParams(inner_params);
		inner_layout.setOrientation(LinearLayout.HORIZONTAL);		
		
		TextView tv=new TextView(getActivity());
		LayoutParams tv_params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//		tv_params.weight=1;
		tv.setGravity(Gravity.CENTER);
		tv_params.gravity = Gravity.CENTER;   
		tv_params.setMargins(15, 0, 15, 0);
		tv.setTextColor(Color.parseColor("#000000"));
		tv.setTextSize(22);
		tv.setLayoutParams(tv_params);
		tv.setText(diet_et.getText().toString());
		
		
		LayoutParams btn_params=new LayoutParams(65,65);
		final Button btn=new Button(getActivity());
		btn.setLayoutParams(btn_params);
		btn.setBackground(getResources().getDrawable(R.drawable.diet_close_button));
		
		
		inner_layout.addView(tv);
		ll.addView(inner_layout);
		ll.addView(btn);
		
		custom_diet.addView(ll);
		diet_et.setText("");
		
		lst_txt.add(tv);
		
		ll_lst.add(ll);
		tv.setTag(tv_tag+"");
		btn.setTag(tv_tag+"");
		
//		btn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				for(TextView tv:lst_txt){
//					if(tv.getTag().equals(btn.getTag())){
//						
//						
////						LinearLayout ll1=null;
////						for(LinearLayout ll2:ll_lst){
////							if(ll2.getTag().equals(tv.getTag().toString())){
////								ll1=ll2;
////							}
////						}
//						LinearLayout ll3=(LinearLayout) custom_diet.findViewWithTag(tv.getTag().toString());
//						if(ll3!=null){
//							ll_lst.remove(Integer.parseInt(tv.getTag().toString()));
//							lst_txt.remove(Integer.parseInt(tv.getTag().toString()));
//							custom_diet.removeView(ll3);
//						}
//						
//						
//					}
//				}
//			}
//		});

	}
	
}
