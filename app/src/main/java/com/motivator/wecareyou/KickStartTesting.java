package com.motivator.wecareyou;

import com.motivator.common.AppsConstant;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class KickStartTesting extends Activity implements OnCheckedChangeListener,OnClickListener{
	
	LinearLayout age_ll,gender_ll,problems_ll;
	RadioButton less_thirty_check,thirty_fourty_check,great_fourty_check,health_check,diabetes_check,other_check
	,guy_check,dad_check,girl_check,mom_check,grandad_check,grand_ma;
	Button next_gender_btn;
	
	RadioButton[] check=new RadioButton[12];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kick_testing);
		InitializeViews();
		initCheckBox();
		Checkvalidation(check);
	}
	public void InitializeViews(){
  		age_ll=(LinearLayout) findViewById(R.id.age_ll);
  		gender_ll=(LinearLayout) findViewById(R.id.gender_ll);
  		problems_ll=(LinearLayout) findViewById(R.id.problems_ll);
  		
  		less_thirty_check=(RadioButton) findViewById(R.id.less_thirty_check);
  		thirty_fourty_check=(RadioButton) findViewById(R.id.thirty_fourty_check);
  		great_fourty_check=(RadioButton) findViewById(R.id.great_fourty_check);
  		health_check=(RadioButton) findViewById(R.id.health_check);
  		diabetes_check=(RadioButton) findViewById(R.id.diabetes_check);
  		other_check=(RadioButton) findViewById(R.id.other_check);
  		guy_check=(RadioButton) findViewById(R.id.guy_check);
  		girl_check=(RadioButton) findViewById(R.id.girl_check);
  		mom_check=(RadioButton) findViewById(R.id.mom_check);
  		grandad_check=(RadioButton) findViewById(R.id.grandad_check);
  		grand_ma=(RadioButton) findViewById(R.id.grand_ma);
  		dad_check=(RadioButton) findViewById(R.id.dad_check);
  		
  		
  		
  		next_gender_btn=(Button) findViewById(R.id.next_gender_btn);
  		
  				
  		less_thirty_check.setOnCheckedChangeListener(this);
  		thirty_fourty_check.setOnCheckedChangeListener(this);
  		great_fourty_check.setOnCheckedChangeListener(this);
  		health_check.setOnCheckedChangeListener(this);
  		diabetes_check.setOnCheckedChangeListener(this);
  		other_check.setOnCheckedChangeListener(this);
  		guy_check.setOnCheckedChangeListener(this);
  		girl_check.setOnCheckedChangeListener(this);
  		mom_check.setOnCheckedChangeListener(this);
  		grandad_check.setOnCheckedChangeListener(this);
  		grand_ma.setOnCheckedChangeListener(this);
  		dad_check.setOnCheckedChangeListener(this);
  	
  		
  		next_gender_btn.setOnClickListener(this);
  	}
	
	public void initCheckBox(){
  		check[0]=less_thirty_check;
  		check[1]=thirty_fourty_check;
  		check[2]=great_fourty_check;
  		check[3]=health_check;
  		check[4]=diabetes_check;
  		check[5]=other_check;
  		check[6]=guy_check;
  		check[7]=dad_check;
  		check[8]=girl_check;
  		check[9]=mom_check;
  		check[10]=grandad_check;
  		check[11]=grand_ma;
  	}
	public boolean Checkvalidation(RadioButton...boxs ){
  		for(int i=0;i<boxs.length;){
  			RadioButton check=boxs[i];
  			if(check.isChecked()){
  				i++;
  				return true;
  			}
  			else{
  				i++;			
  			}			
  		}
  		return false;
  	}
  	public void checkCondition(RadioButton check,RadioButton...boxs){
  		for(int i=0;i<boxs.length;i++){
  			RadioButton checkbox=boxs[i];
  			if(checkbox.getId()==check.getId()){
  				checkbox.setChecked(true);
  			}
  			else{
  				checkbox.setChecked(false);
  			}
  		}
  	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		int id=buttonView.getId();
		RadioButton[] checbox=new RadioButton[3];
  		checbox[0]=less_thirty_check;
  		checbox[1]=thirty_fourty_check;
  		checbox[2]=great_fourty_check;
  		
  		
  		RadioButton[] checkbox1=new RadioButton[3];
  		checkbox1[0]=health_check;
  		checkbox1[1]=diabetes_check;
  		checkbox1[2]=other_check;
  		
  		
  		RadioButton[] checkbox2=new RadioButton[6];
  		checkbox2[0]=guy_check;
  		checkbox2[1]=dad_check;
  		checkbox2[2]=girl_check;
  		checkbox2[3]=mom_check;
  		checkbox2[4]=grandad_check;
  		checkbox2[5]=grand_ma;
  		
  		
  		switch (id) {
  		case R.id.less_thirty_check:
  			if(isChecked){
  			
  				RadioButton check=(RadioButton) findViewById(id);
  			checkCondition(check, checbox);
  			}
  			break;
  		case R.id.thirty_fourty_check:
  			if(isChecked){
  				RadioButton check1=(RadioButton) findViewById(id);
  			checkCondition(check1, checbox);
  			}
  			break;

  		case R.id.great_fourty_check:
  			if(isChecked){
  				RadioButton check2=(RadioButton) findViewById(id);
  			checkCondition(check2, checbox);
  			}
  			break;

  		case R.id.health_check:
  			if(isChecked){
  				RadioButton check3=(RadioButton) findViewById(id);
  			checkCondition(check3, checkbox1);
  			}
  			break;

  		case R.id.diabetes_check:
  			if(isChecked){
  				RadioButton check4=(RadioButton) findViewById(id);
  			checkCondition(check4, checkbox1);
  			}
  			break;

  		case R.id.other_check:
  			if(isChecked){
  				RadioButton check5=(RadioButton) findViewById(id);
  			checkCondition(check5, checkbox1);
  			}
  			break;

  		case R.id.guy_check:
  			if(isChecked){
  				RadioButton check6=(RadioButton) findViewById(id);
  			checkCondition(check6, checkbox2);
  			}
  			break;

  		case R.id.dad_check:
  			if(isChecked){
  				RadioButton check7=(RadioButton) findViewById(id);
  			checkCondition(check7, checkbox2);
  			}
  			break;

  		case R.id.girl_check:
  			if(isChecked){
  				RadioButton check8=(RadioButton) findViewById(id);
  			checkCondition(check8, checkbox2);
  			}
  			break;

  		case R.id.mom_check:
  			if(isChecked){
  				RadioButton check9=(RadioButton) findViewById(id);
  			checkCondition(check9, checkbox2);
  			}
  			break;

  		case R.id.grandad_check:
  			if(isChecked){
  				RadioButton check10=(RadioButton) findViewById(id);
  			checkCondition(check10, checkbox2);
  			}
  			break;

  		case R.id.grand_ma:
  			if(isChecked){
  				RadioButton check11=(RadioButton) findViewById(id);
  			checkCondition(check11, checkbox2);
  			}
  			break;


  		default:
//  			CheckBox check12=(CheckBox) findViewById(id);
//  			checkCondition(check12, this.check);
  			break;
  		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		RadioButton[] checbox=new RadioButton[3];
		checbox[0]=less_thirty_check;
		checbox[1]=thirty_fourty_check;
		checbox[2]=great_fourty_check;
		
		
		RadioButton[] checkbox1=new RadioButton[3];
		checkbox1[0]=health_check;
		checkbox1[1]=diabetes_check;
		checkbox1[2]=other_check;
		
		
		RadioButton[] check2=new RadioButton[6];
		check2[0]=guy_check;
		check2[1]=dad_check;
		check2[2]=girl_check;
		check2[3]=mom_check;
		check2[4]=grandad_check;
		check2[5]=grand_ma;
		
		if(Checkvalidation(checbox)){				
			RadioButton checkbox=SelectedCheckBox(checbox);
			int check_id=checkbox.getId();
			switch (check_id) {
			case R.id.less_thirty_check:
				Log.d("sun","less thirty check");
//				AppsConstant.user_age="<30";
				break;
			case R.id.thirty_fourty_check:
				Log.d("sun","thirty fourty check");
//				AppsConstant.user_age="30-45";
				break;
			case R.id.great_fourty_check:
				Log.d("sun","great fourty check");
//				AppsConstant.user_age=">45";
				break;
	
				default:
					break;
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "please select age", Toast.LENGTH_SHORT).show();
			return;
		}
		
		
		if(Checkvalidation(checkbox1)){
			RadioButton checkbox=SelectedCheckBox(checkbox1);
			int check_id=checkbox.getId();
			switch (check_id) {
			case R.id.health_check:
				Log.d("sun","health_check");
//				AppsConstant.user_health="health";
				break;
			case R.id.diabetes_check:
				Log.d("sun","diabetes_check");
//				AppsConstant.user_health="diabetes";
				break;
			case R.id.other_check:
				Log.d("sun","other_check");
//				AppsConstant.user_health="other";
				break;
	
				default:
					break;
			}
		}
		else{
			Toast.makeText(getApplicationContext(), "please Select the health Problem", Toast.LENGTH_SHORT).show();
			return;
		}
		
		
		if(Checkvalidation(check2)){
			RadioButton checkbox=SelectedCheckBox(check2);
			int check_id=checkbox.getId();
			switch (check_id) {
			case R.id.guy_check:
				Log.d("sun","Gender:-Guy");
//				AppsConstant.user_gender="guy";
				break;
			case R.id.dad_check:
				Log.d("sun","Gender:-Dad");
//				AppsConstant.user_gender="dad";
				break;
			case R.id.girl_check:
				Log.d("sun","Gender:-Girl");
//				AppsConstant.user_gender="girl";
				break;
			case R.id.mom_check:
				Log.d("sun","Gender:-Mom");
//				AppsConstant.user_gender="mom";
				break;

			case R.id.grandad_check:
				Log.d("sun","Gender:-GrandDad");
//				AppsConstant.user_gender="granddad";
				break;

			case R.id.grand_ma:
				Log.d("sun","Gender:-GrandMaa");
//				AppsConstant.user_gender="grandmaa";
				break;


			default:
				Log.d("sun","Gender:-Guy");
//				AppsConstant.user_gender="guy";
				break;
			}								
			
		}
		else{
			Toast.makeText(getApplicationContext(), "Please Select Your Gender", Toast.LENGTH_SHORT).show();
			return;
		}

	}
	
	public RadioButton SelectedCheckBox(RadioButton...boxs){
  		for(int i=0;i<boxs.length;i++){
  			if(boxs[i].isChecked()){
  				return boxs[i];
  			}
  		}
  		return boxs[0];
  	}
}
