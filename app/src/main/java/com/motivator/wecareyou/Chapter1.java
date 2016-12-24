package com.motivator.wecareyou;

import com.motivator.common.GeneralUtility;
import com.motivator.common.AppsConstant;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Chapter1 extends Activity {
	
	String userName;
	TextView tvChapter, tvJourney, tvWhich, tvLearns, tvLearns2;
	ImageView imvPlay;
	LinearLayout llChapter;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter);

        userName = GeneralUtility.getPreferences(Chapter1.this, AppsConstant.user_name);
//		emailID = GeneralUtility.getPreferences(Journey.this, "user_email");
		
		Typeface tfPhilospher = Typeface.createFromAsset(Chapter1.this.getAssets(), "fonts/Philosopher-Regular.ttf");
		Typeface tfMontez = Typeface.createFromAsset(Chapter1.this.getAssets(), "fonts/Montez-Regular.ttf");
		
		llChapter = (LinearLayout)findViewById(R.id.ll_chapter);
        tvChapter = (TextView)findViewById(R.id.tv_chapter);
        tvJourney = (TextView)findViewById(R.id.tv_journey);
        tvWhich = (TextView)findViewById(R.id.tv_which);
        tvLearns = (TextView)findViewById(R.id.tv_learns);
        tvLearns2 = (TextView)findViewById(R.id.tv_learns2);
        imvPlay = (ImageView)findViewById(R.id.imv_play);
        
        
        tvChapter.setTypeface(tfPhilospher);
        tvJourney.setTypeface(tfMontez);
        tvWhich.setTypeface(tfPhilospher);
        tvLearns.setTypeface(tfPhilospher);
        tvLearns2.setTypeface(tfPhilospher);
        
        tvChapter.setText("Chapter 1");
        tvJourney.setText("An Interesting Journey");
        tvWhich.setText("In which");
        
        String journey_mode = GeneralUtility.getPreferences(Chapter1.this, "journey_mode");
        int journey = Integer.parseInt(journey_mode);
        
        switch (journey) {
		case AppsConstant.FEEL_ENERGIZED:
			llChapter.setBackgroundResource(R.drawable.feel_energized_bg);
			tvLearns.setText(userName +" learns how to stay");
	        tvLearns2.setText("energized the whole day");
			break;
		case AppsConstant.LOSE_WEIGHT:
			llChapter.setBackgroundResource(R.drawable.enjoing_healthy_eating_bg);
			tvLearns.setText(userName +" learns how to lose weight");
	        tvLearns2.setText("without counting calories");
			break;
		case AppsConstant.SLEEP_BETTER:
			llChapter.setBackgroundResource(R.drawable.pleasant_night_bg);
			tvLearns.setText(userName +" learns how to manufacture");
	        tvLearns2.setText("a great night's sleep");
			break;
		default:
			llChapter.setBackgroundResource(R.drawable.feel_energized_bg);
			tvLearns.setText(userName +" learns how to stay");
			tvLearns2.setText("energized the whole day");
			break;
		}
        
        
        imvPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Chapter1.this, FirstWalkThrough.class);
				startActivity(i);
				finish();
				
			}
		});
	}
	
}

