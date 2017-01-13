package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
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
import com.motivator.common.FacebookWallPost;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.JourneyData;
import com.motivator.model.JourneyHabitModel;
import com.motivator.support.FileUtils;
import com.motivator.support.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Goal_GoldenChallenge extends Activity implements OnClickListener{
	
	String userName, selectedStep;
	String callingFrag;
	int habitId;
	ActionBar actionBar;
	LinearLayout llTop;
	TextView tvGoalTitle, tvGoalDesc, tvChallenge;
	TextView tvDay1, tvDay2, tvDay3;
	Button tvAcceptChallenge;
	ImageView imvGoalImg;
	View progress1, progress2;
	Button btnShareOnFb;
	
	JourneyHabitModel userJourneyModel = new JourneyHabitModel();
	GetData getData;
	UpdateData update;
	MediaPlayer mplayer;
	private static String APP_ID = "234992030177331";
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getData = new GetData(Goal_GoldenChallenge.this);
        update = new UpdateData(Goal_GoldenChallenge.this);
        
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
		userName = GeneralUtility.getPreferences(Goal_GoldenChallenge.this, AppsConstant.user_name);
		selectedStep = getIntent().getExtras().getString(AppsConstant.selected_journey_step);
		habitId = getIntent().getExtras().getInt(AppsConstant.h_id);
		callingFrag = getIntent().getExtras().getString(AppsConstant.calling_frag);
		
		initializingUIViews();
		
		llTop.setBackgroundColor(Color.parseColor(JourneyData.getJourneyBackground(habitId)));
		tvGoalTitle.setText(JourneyData.getGoalTitle(habitId));
		imvGoalImg.setImageResource(JourneyData.getJourneyImg(habitId));
		tvGoalDesc.setText(JourneyData.getGoalText(habitId));
		//tvGoalText.setText(JourneyText.getGoalText(habitId));

		userJourneyModel = getData.getJourneyHabit(userName, habitId);
		int inProgress = userJourneyModel.isChallengeAccepted();
		if(inProgress == TableAttributes.STATUS_VALUE_1)
		{
			tvAcceptChallenge.setText("Challenge In Progress");
			tvAcceptChallenge.setBackgroundColor(getResources().getColor(android.R.color.transparent));
			tvAcceptChallenge.setTextColor(Color.parseColor("#088A08"));
			btnShareOnFb.setVisibility(View.VISIBLE);
			
			int taskCompleted = 0 ;
//			= userJourneyModel.getGoalStatus();
			int status1= getData.getJourneyGoldenStatus(userName, TableAttributes.drinkWaterId);
			int status2= getData.getJourneyGoldenStatus(userName, TableAttributes.greatBreakFastID);
			int status3= getData.getJourneyGoldenStatus(userName, TableAttributes.danceYourWayID);
			
			int min = status1<=status2?status1:status2;
			taskCompleted = min<=status3?min:status3;
			/*if(status1<=status2)
			{
				taskCompleted = status1<=status3?status1:status3;
			}
			else if(status2<=status3)
			{
				taskCompleted = status2;
				taskCompleted = status2<=status3?status2:status3;
			}
			else
				taskCompleted = status3;*/
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
				if(userJourneyModel.getGoldenChalengeStatus()==TableAttributes.STATUS_VALUE_1)
        		{
					update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY, habitId, 
								TableAttributes.GOLDEN_CHALLENGE, TableAttributes.STATUS_VALUE_2);
            		update.updateJourneyStatus(userName, AppsConstant.SELECTED_JOURNEY, selectedStep);
        		}
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
		
		mAsyncRunner = new AsyncFacebookRunner(facebook);

	}
	private final String TAG=getClass().getName();
	public void ListFiles(File f, String mood) {
		File[] files = f.listFiles();
		Log.d(TAG, "length:-" + files.length);
		int val = Pref.getInteger(getApplicationContext(), mood, -1);
		Log.d(TAG, "pref val:-" + val);
		if (files.length > 0) {

			if ((val + 1) >= files.length) {
				val = 0;
			} else {
				val = val + 1;
			}
		}
		try {
			Log.d(TAG, "final val:-" + val);
			File soundFile = files[val];
			mplayer = new MediaPlayer();
			mplayer.setDataSource(soundFile.toString());
			mplayer.prepare();
			if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
				mplayer.start();
			}
//            int MAX_VOLUME = 100;
//            final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
//            mplayer.setVolume(volume, volume);
//            Pref.setInteger(getApplicationContext(), mood, val);
			Log.d(TAG, "pref mood:-" + Pref.getInteger(getApplicationContext(), mood, -1));
		} catch (Exception e) {
			Log.d("sunil", e.toString());
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mplayer!=null&&mplayer.isPlaying()){
			mplayer.stop();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(mplayer!=null&&mplayer.isPlaying()){
			mplayer.stop();
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
		
		tvAcceptChallenge = (Button)findViewById(R.id.tv_accept);
		btnShareOnFb = (Button)findViewById(R.id.btn_share_on_fb);
		
		
		tvGoalTitle.setTypeface(GeneralUtility.setTypeFace(Goal_GoldenChallenge.this));
		tvGoalDesc.setTypeface(GeneralUtility.setTypeFace(Goal_GoldenChallenge.this));
		tvChallenge.setTypeface(GeneralUtility.setTypeFace(Goal_GoldenChallenge.this));
		
		tvDay1.setOnClickListener(this);
		tvDay2.setOnClickListener(this);
		tvDay3.setOnClickListener(this);
		tvAcceptChallenge.setOnClickListener(this);
		btnShareOnFb.setOnClickListener(this);

		ListFiles(new File(FileUtils.JOURNEY_CHALLENGE), StringUtils.GOLDEN_CHALLENGE);
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
			
			Intent feedback = new Intent(Goal_GoldenChallenge.this, Feedback.class);
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
			Toast.makeText(Goal_GoldenChallenge.this, "Want to mark this as done? Go to the home page, then tap on the ritual card", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.tv_accept:
			if(userJourneyModel.isChallengeAccepted()<=TableAttributes.STATUS_VALUE_0)
			{
				tvAcceptChallenge.setText("Challenge In Progress");
				tvAcceptChallenge.setBackgroundColor(getResources().getColor(android.R.color.transparent));
				tvAcceptChallenge.setTextColor(Color.parseColor("#088A08"));
				btnShareOnFb.setVisibility(View.VISIBLE);
				tvDay1.setBackgroundResource(R.drawable.circle_gray);
				progress1.setVisibility(View.INVISIBLE);
				tvDay2.setBackgroundResource(R.drawable.circle_gray);
				progress2.setVisibility(View.INVISIBLE);
				tvDay3.setBackgroundResource(R.drawable.circle_gray);
				update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY,habitId, TableAttributes.CHALLENGE_ACCEPTED, TableAttributes.STATUS_VALUE_1);
				
				if(habitId==TableAttributes.goldenChallengeID)
					update.updateJourneyGoldenChallenge(userName, AppsConstant.SELECTED_JOURNEY, TableAttributes.GOLDEN_CHALLENGE, TableAttributes.STATUS_VALUE_1);
				
			}
			break;
			
		case R.id.btn_share_on_fb:
			Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, (String) v.getTag(R.string.app_name));
			shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "New Challenge :"+JourneyData.getGoalTitle(habitId));
			//shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
			startActivity(shareIntent);
			
			
			FacebookWallPost fb = new FacebookWallPost();

			if (! facebook.isSessionValid()) {
				fb.loginAndPostToWall(Goal_GoldenChallenge.this, facebook, habitId);
			}
			else {
				fb.publishStory(Goal_GoldenChallenge.this, facebook, habitId);
			}
			break;

//			String shareBody = "Here is the share content body";
//			Log.d("sunil","sharing");
//			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//			sharingIntent.setType("text/plain");
//			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, (String) v.getTag(R.string.app_name));
//			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "New Challenge :"+JourneyData.getGoalTitle(habitId));
//			startActivity(Intent.createChooser(sharingIntent, "Share Using"));
		}
		
	}
	
	
	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	     // Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	      facebook.authorizeCallback(requestCode, resultCode, data);
	      
	  }
    
}

