package com.motivator.wecareyou;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.model.HabitModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class HabitCompletionActivity extends Activity {
	
	ScrollView scvMain;
	ImageView imvChatScreen;
	TextView tvToday, tvGreat, tvRitualCompleted, tvHabitInRow, tvGotoHome;
	
	int habitCompleted = 0;
	GetData getData;
	String userName, selectedRitual;
	TextToSpeech textToSpeech;
	ArrayList<HabitModel> userHabitList = new ArrayList<HabitModel>();
	
	boolean isFirstWalkthough = false;
	String congratsMsg = "You've done well";
	String statusMsg = "You've done "+habitCompleted+" habit";
	
	MediaPlayer mPlayer;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        getData = new GetData(HabitCompletionActivity.this);
        
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.habit_completion);
        
        scvMain = (ScrollView)findViewById(R.id.scv_main);
        
        imvChatScreen= (ImageView)findViewById(R.id.imv_habit_status);
        
        tvToday= (TextView)findViewById(R.id.tv_today);
        tvGreat= (TextView)findViewById(R.id.tv_great);
        tvRitualCompleted= (TextView)findViewById(R.id.tv_ritual_completed);
        tvHabitInRow= (TextView)findViewById(R.id.tv_habit_in_row);
        tvGotoHome= (TextView)findViewById(R.id.tv_return_home);
        
        tvToday.setTypeface(GeneralUtility.setTypeFace(HabitCompletionActivity.this));
        tvGreat.setTypeface(GeneralUtility.setTypeFace(HabitCompletionActivity.this));
        tvRitualCompleted.setTypeface(GeneralUtility.setTypeFace(HabitCompletionActivity.this));
        tvHabitInRow.setTypeface(GeneralUtility.setTypeFace(HabitCompletionActivity.this));
        tvGotoHome.setTypeface(GeneralUtility.setTypeFace(HabitCompletionActivity.this));
        
        
        userName = GeneralUtility.getPreferences(HabitCompletionActivity.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        isFirstWalkthough = getIntent().getExtras().getBoolean("isFirstWalkthrough");
        
        if(selectedRitual.equalsIgnoreCase(AppsConstant.MORNING_RITUAL))
        {
        	scvMain.setBackgroundResource(R.drawable.feel_energized_bg);
        }
        else if(selectedRitual.equalsIgnoreCase(AppsConstant.LUNCH_RITUAL))
        {
        	scvMain.setBackgroundResource(R.drawable.enjoing_healthy_eating_bg);
        }
        else if(selectedRitual.equalsIgnoreCase(AppsConstant.EVENING_RITUAL))
        {
        	scvMain.setBackgroundResource(R.drawable.pleasant_night_bg);
        }
        
        
        userHabitList = getData.getUserHabits(userName, selectedRitual);
        
        for(int i = 0; i<userHabitList.size(); i++)
        {
        	String completedOn = userHabitList.get(i).getHabitCompletedON();
        	if(completedOn!=null && completedOn.length()>0)
       	 	{
        		int result = DateUtility.compareDateWithToday(completedOn, "E MMM dd yyyy");
   	   				 //today.compareTo(savedDateObj);
        		if(result==0)
        		{
        			++habitCompleted;
        		}
       	 	}
        	
        }
        
        
        if(habitCompleted==userHabitList.size())
        {
        	congratsMsg = "Great!";
        	statusMsg = "You completed your Routine";
        		Log.d("sun","Habits completed");
				playMusic();
//        	textToSpeech = new TextToSpeech(getApplicationContext(), new OnInitListener() {
//
//    			@Override
//    			public void onInit(int status)
//    			{
//    				final boolean isSoundOn = GeneralUtility.getPreferencesBoolean(HabitCompletionActivity.this, AppsConstant.AVS_SOUND);
//    				if(isSoundOn)
//    				{
////    					convertTextToSpeech(status, congratsMsg+" "+statusMsg);
//    					playMusic();
//    				}
//    			}
//    		});
        }
        else
        {
        	Log.d("sun","Habits completed1");
        	congratsMsg = "You've done well";
        	statusMsg = "You've done "+habitCompleted+" habit";
        }
       
        tvGreat.setText(congratsMsg);
    	tvRitualCompleted.setText(statusMsg);
        
        
        tvGotoHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				convertTextToSpeech(TextToSpeech.SUCCESS, getMsg());
				onBackPressed();				
			}
		});
	}
	public void playMusic(){
		if(AppsConstant.user_mood.equals("excited")||AppsConstant.user_mood.equals("cheerful")){

			Random random=new Random();
			int num=random.nextInt(2 - 1+ 1) + 1;
			switch (num) {
			case 1:

				String roll_n_roll="rock and roll";
				if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
					if(MyApplication.tts_initialized){
						MyApplication.tts.speak(roll_n_roll, TextToSpeech.QUEUE_FLUSH, null);
					}
				}
			return;
			case 2:

				String good_hustle="Good hustle out there.";
				if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
					if(MyApplication.tts_initialized){
						MyApplication.tts.speak(good_hustle, TextToSpeech.QUEUE_FLUSH, null);
					}
				}
			return;
			default:
				if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
					if(MyApplication.tts_initialized){
						MyApplication.tts.speak("Good hustle out there.", TextToSpeech.QUEUE_FLUSH, null);
					}
				}
			}
		}
		if(AppsConstant.user_mood.equals("sad")||AppsConstant.user_mood.equals("irritated")||AppsConstant.user_mood.equals("stressed")){
			if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
				if(MyApplication.tts_initialized){
					MyApplication.tts.speak("It does my heart good to see you sticking with it.", TextToSpeech.QUEUE_FLUSH, null);
				}
			}
			return;
		}
		else{

		Random random=new Random();
		int num=random.nextInt(5 - 1+ 1) + 1;
		switch (num) {
		case 1:
			if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
				if(MyApplication.tts_initialized){
					MyApplication.tts.speak("Great job! Let’s do it all again tomorrow.\n", TextToSpeech.QUEUE_FLUSH, null);
				}
			}
			return;
		case 2:
			if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
				if(MyApplication.tts_initialized){
					MyApplication.tts.speak("Nice work! You’re on a roll.", TextToSpeech.QUEUE_FLUSH, null);
				}
			}
			return;
		case 3:
			if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
				if(MyApplication.tts_initialized){
					MyApplication.tts.speak("And there we go! We’re done for now.", TextToSpeech.QUEUE_FLUSH, null);
				}
			}
			return;
		case 4:
			if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
				if(MyApplication.tts_initialized){
					MyApplication.tts.speak("You got a lot done. Very nice.", TextToSpeech.QUEUE_FLUSH, null);
				}
			}
			return;
		case 5:
			if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
				if(MyApplication.tts_initialized){
					MyApplication.tts.speak("You got a lot done. Very nice.", TextToSpeech.QUEUE_FLUSH, null);
				}
			}
			return;
		default:
			return;
		}

		}
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	private String getMsg(){
		Random random=new Random();
		int num=random.nextInt(14 - 1+ 1) + 1;
		switch (num) {
		case 1:
			return "Great job! Let's do it all again tomorrow";
		case 2:
			return "Nice work! You're on a roll.";
		case 3:
			return "And there we go! We're done for now.";
		case 4:
			return "You got a lot done. Very nice.";
		case 5:
			return "Same time and place tomorrow?";
		case 6:
			return "Have I mentioned how awesome you are? ...Pretty awesome.";
		case 7:
			return "And that concludes our to-do list. You did good";
		case 8:
			return "Not bad. Not bad at all.";
		case 9:
			return "It does my heart good to see you sticking with it.";
		case 10:
			return "Sweet. You showed that to-do list who is boss.";
		case 11:
			return "At least one thing is going your way today, am I right? Good job.";
		case 12:
			return "You did good.";
		case 13:
			return "Rock and Roll.";
		case 14:
			return "Good hustle out there.";
		default:
			return "Great job! Let's do it all again tomorrow";
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mPlayer!=null) {
			mPlayer.stop();
		}
	}
	/**
	  * Speaks the string using the specified queuing strategy and speech
	  * parameters.
	  */
	 private void convertTextToSpeech(int status, String text)
	 {
		 if (status == TextToSpeech.SUCCESS)
		 {
			 int lang = textToSpeech.setLanguage(Locale.US);
			 if (lang == TextToSpeech.LANG_MISSING_DATA
					 || lang == TextToSpeech.LANG_NOT_SUPPORTED) {
				 Log.e("error", "This Language is not supported");
			 }
			 else {
				 if (null == text || "".equals(text)) {
					 text = "";
				 }
				 textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			 }
		 } else {
			 Log.e("error", "Initilization Failed!");
		 }
	 }


	 @Override
	public void onBackPressed() {
		super.onBackPressed();
		if(isFirstWalkthough)
		{
			Intent i = new Intent(HabitCompletionActivity.this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		}
		else
			finish();
	}
}

