package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.JourneyData;
import com.motivator.model.JourneyHabitModel;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class Goal extends Activity implements OnClickListener{
	
	String userName, callingFrag;
	int habitId;
	ActionBar actionBar;
	LinearLayout llTop;
	TextView tvGoalTitle, tvGoalDesc, tvChallenge;
	TextView tvDay1, tvDay2, tvDay3,tvAcceptChallenge;
	ImageView imvGoalImg;
	View progress1, progress2;
	Button btnShareOnFb;
	
	JourneyHabitModel userJourneyModel = new JourneyHabitModel();
	GetData getData;;
	UpdateData update;
	
	private static String APP_ID = "234992030177331";
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getData = new GetData(Goal.this);
        update = new UpdateData(Goal.this);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.goal);
      //Set ACTION BAR
        
        SpannableString s = new SpannableString("challenge");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("Challenge");
        actionBar.setTitle(s);
        
      //get Data from shared prefrences and intent
		userName = GeneralUtility.getPreferences(Goal.this, AppsConstant.user_name);
		habitId = getIntent().getExtras().getInt(AppsConstant.h_id);
		callingFrag = getIntent().getExtras().getString(AppsConstant.calling_frag);
		
		initializingUIViews();
		
		llTop.setBackgroundColor(Color.parseColor(JourneyData.getJourneyBackground(habitId)));
		tvGoalTitle.setText(JourneyData.getGoalTitle(habitId));
		imvGoalImg.setImageResource(JourneyData.getJourneyImg(habitId));
		tvGoalDesc.setText(JourneyData.getGoalText(habitId));
		//tvGoalText.setText(JourneyText.getGoalText(habitId));
		
		updateGoalStatus();

		mAsyncRunner = new AsyncFacebookRunner(facebook);
		
	}

	private void updateGoalStatus() 
	{
		userJourneyModel = getData.getJourneyHabit(userName, habitId);
		int inProgress = userJourneyModel.isChallengeAccepted();
		if(inProgress == TableAttributes.STATUS_VALUE_1)
		{
			int taskCompleted = userJourneyModel.getGoalStatus();
			tvAcceptChallenge.setText("Challenge In Progress"); 
			btnShareOnFb.setVisibility(View.VISIBLE);
			
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
				tvAcceptChallenge.setText("Completed"); 
				tvDay1.setBackgroundResource(R.drawable.circle_green_lite);
				progress1.setVisibility(View.VISIBLE);
				tvDay2.setBackgroundResource(R.drawable.circle_green_lite);
				progress2.setVisibility(View.VISIBLE);
				tvDay3.setBackgroundResource(R.drawable.circle_green_lite);
				break;
			default:
				tvAcceptChallenge.setText("Completed"); 
				tvDay1.setBackgroundResource(R.drawable.circle_green_lite);
				progress1.setVisibility(View.VISIBLE);
				tvDay2.setBackgroundResource(R.drawable.circle_green_lite);
				progress2.setVisibility(View.VISIBLE);
				tvDay3.setBackgroundResource(R.drawable.circle_green_lite);
				break;
			}
		}
		
	}

	private void initializingUIViews()
	{
		llTop = (LinearLayout)findViewById(R.id.ll_goal_top);
		imvGoalImg = (ImageView)findViewById(R.id.imv_goal_img);
		tvGoalTitle = (TextView)findViewById(R.id.tv_goal_title);
		tvGoalDesc = (TextView)findViewById(R.id.tv_goal_desc);
		
		tvChallenge = (TextView)findViewById(R.id.tv_challenge);
		tvDay1 = (TextView)findViewById(R.id.tv_goal_day1);
		tvDay2 = (TextView)findViewById(R.id.tv_goal_day2);
		tvDay3 = (TextView)findViewById(R.id.tv_goal_day3);
		
		progress1 = (View)findViewById(R.id.view_progress1);
		progress2 = (View)findViewById(R.id.view_progress2);
		
		tvAcceptChallenge = (TextView)findViewById(R.id.tv_accept);
		btnShareOnFb = (Button)findViewById(R.id.btn_share_on_fb);
		
		
		tvGoalTitle.setTypeface(GeneralUtility.setTypeFace(Goal.this));
		tvGoalDesc.setTypeface(GeneralUtility.setTypeFace(Goal.this));
		tvChallenge.setTypeface(GeneralUtility.setTypeFace(Goal.this));
		
		tvDay1.setOnClickListener(this);
		tvDay2.setOnClickListener(this);
		tvDay3.setOnClickListener(this);
		tvAcceptChallenge.setOnClickListener(this);
		btnShareOnFb.setOnClickListener(this);
	}
	
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.journey_menu, menu);
        MenuItem switchButton = menu.findItem(R.id.done);
        switchButton.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        
        case android.R.id.home:
            onBackPressed();
            return true;
        
        case R.id.action_feedback:
			View rootView = findViewById(android.R.id.content).getRootView();
			rootView.setDrawingCacheEnabled(true);
			Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
			rootView.setDrawingCacheEnabled(false);
			
			Intent feedback = new Intent(Goal.this, Feedback.class);
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
			feedback.putExtra(AppsConstant.sreenshot, bs.toByteArray());
			//feedback.putExtra(AppsConstant.sreenshot, bitmap);
			startActivity(feedback);
			bitmap.recycle();
			return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.tv_goal_day1:
//			break;

		case R.id.tv_goal_day2:
					
//			break;
					
		case R.id.tv_goal_day3:
			Toast.makeText(Goal.this, "Want to mark this as done? Go to the home page, then tap on the ritual card", 5).show();
			break;
			
		case R.id.tv_accept:
			if(userJourneyModel.isChallengeAccepted()<=TableAttributes.STATUS_VALUE_0)
			{
				tvAcceptChallenge.setText("Challenge In Progress");
				btnShareOnFb.setVisibility(View.VISIBLE);
				tvDay1.setBackgroundResource(R.drawable.circle_gray);
				progress1.setVisibility(View.INVISIBLE);
				tvDay2.setBackgroundResource(R.drawable.circle_gray);
				progress2.setVisibility(View.INVISIBLE);
				tvDay3.setBackgroundResource(R.drawable.circle_gray);
				update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY,habitId, TableAttributes.CHALLENGE_ACCEPTED, TableAttributes.STATUS_VALUE_1);
				
				//check if user have already completed the habit today
				String theDate = DateUtility.formateDate(new Date(), "E MMM dd yyyy");     
				boolean isCompletedOnDate = getData.isHabitCompletedOn(userName, habitId, theDate);
				if(isCompletedOnDate)
				{
					update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY,habitId, TableAttributes.GOAL_COMPLETED, 1);
					updateGoalStatus();
				}
			}
	
			break;
			
		case R.id.btn_share_on_fb:
//			Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
//			shareIntent.setType("text/plain");
//			shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, (String) v.getTag(R.string.app_name));
//			shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "New Challenge :"+JourneyData.getGoalTitle(habitId));
//			//shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//			startActivity(shareIntent);
			
//            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//            sharingIntent.setType("text/plain");
//            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, (String) v.getTag(R.string.app_name));
//            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "New Challenge :"+JourneyData.getGoalTitle(habitId));
//            startActivity(Intent.createChooser(sharingIntent, "Share Via"));
	
//			FacebookWallPost fb = new FacebookWallPost();
//
//			if (! facebook.isSessionValid()) {
//				fb.loginAndPostToWall(Goal.this, facebook, habitId);
//			}
//			else {
//				fb.publishStory(Goal.this, facebook, habitId);
//			}
//			break;
			Log.d("sunil","sharing");
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, (String) v.getTag(R.string.app_name));
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "New Challenge :"+JourneyData.getGoalTitle(habitId));
			startActivity(Intent.createChooser(sharingIntent, "Share Using"));
		}
		
	}
	
	
	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	     // Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	      facebook.authorizeCallback(requestCode, resultCode, data);
	      
	  }
    
}

