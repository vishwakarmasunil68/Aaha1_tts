package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.JourneyData;
import com.motivator.model.JourneyHabitModel;
import com.motivator.support.FileUtils;
import com.motivator.support.InteractiveScrollViewLetter;
import com.motivator.support.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Letter extends Activity implements OnClickListener{
	
	String userName, selectedStep;
	int habitId, letterNum;
	String callingFrag;
	ActionBar actionBar;
	MenuItem switchButton;
	
	LinearLayout llTop;
	TextView tvLetter, tvSubTitle;
//	ImageView imvPopUp;
	
	int letterStatus = 0;
	public static final String Letter_Num = "Letter_Num";
	JourneyHabitModel userJourneyModel = new JourneyHabitModel();
	GetData getData;
	UpdateData update;
	MediaPlayer player;
//	Button ok_btn;
	FloatingActionButton fab;
	
	InteractiveScrollViewLetter scroll;
	MediaPlayer mPlayer1;
	private final String TAG="letter";
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getData = new GetData(Letter.this);
        update = new UpdateData(Letter.this);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.letter);
        //Set ACTION BAR
        actionBar = getActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("");
        SpannableString s = new SpannableString("");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        actionBar.setTitle(s);
        
      //get Data from shared prefrences and intent
		userName = GeneralUtility.getPreferences(Letter.this, AppsConstant.user_name);
		selectedStep = getIntent().getExtras().getString(AppsConstant.selected_journey_step);
		habitId = getIntent().getExtras().getInt(AppsConstant.h_id);
		letterNum = getIntent().getExtras().getInt(Letter_Num);
		callingFrag = getIntent().getExtras().getString(AppsConstant.calling_frag);
		
		llTop = (LinearLayout)findViewById(R.id.ll_letter_top);
		tvLetter = (TextView)findViewById(R.id.tv_letter);
		tvSubTitle = (TextView)findViewById(R.id.tv_subtitle);
		scroll=(InteractiveScrollViewLetter) findViewById(R.id.scroll);
		
//		ok_btn=(Button) findViewById(R.id.ok_btn);
//		ok_btn.setVisibility(View.GONE);
		fab=(FloatingActionButton) findViewById(R.id.fab);
		fab.setVisibility(View.GONE);
		
		WebView myWebView = (WebView)findViewById(R.id.webview_letter);
		
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.loadUrl("file:///android_asset/html_files/"+getfileName());
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.getSettings().setDomStorageEnabled(true);
		myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		//myWebView.addJavascriptInterface(new PayUJavaScriptInterface(), "PayUMoney");
		myWebView.addJavascriptInterface(new AndroidJavaScriptInterface(), "Android");


//		mPlayer1= MediaPlayer.create(Letter.this, R.raw.reading_letter_1);
//		if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND))
//			mPlayer1.start();
//		int MAX_VOLUME = 100;
//		final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
//		mPlayer1.setVolume(volume, volume);

//		ok_btn.setOnClickListener(this);
		fab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("sunil","clicked");
				if(letterStatus <= TableAttributes.LETTER_Opened)
	    		{
	        		letterStatus = TableAttributes.LETTER_COMPLETED;
	        		update.updateJourneyStatus(userName, AppsConstant.SELECTED_JOURNEY, selectedStep);
	    		}
	        	update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY,habitId, 
	        			TableAttributes.LETTER_READ, TableAttributes.LETTER_COMPLETED);
	        	
	        	switchButton.setIcon(R.drawable.right_green);
	        	if(callingFrag!=null && callingFrag.length()>0)
				{	Intent intent=new Intent(Letter.this,MainActivity.class);
					intent.putExtra("mood","cancel");
					startActivity(intent);
					finishAffinity();
				}
				else{
					finish();
				}
			}
		});
		
		tvLetter.setTypeface(GeneralUtility.setTypeFace(Letter.this));
		tvSubTitle.setTypeface(GeneralUtility.setTypeFace(Letter.this));
		
		
		llTop.setBackgroundResource(JourneyData.getMotivatorImg(habitId));
		tvLetter.setText("Letter no."+letterNum);
//		imvPopUp = (ImageView)findViewById(R.id.imv_popup);
		
		
		userJourneyModel = getData.getJourneyHabit(userName, habitId);
		letterStatus = userJourneyModel.isLetterRead();
		if(letterStatus==TableAttributes.LETTER_Unread)
		{
//			setBackgroundMusic();					
//			imvPopUp.setVisibility(View.VISIBLE);
			update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY,habitId, 
					TableAttributes.LETTER_READ, TableAttributes.LETTER_Opened);
		}
		
		
		llTop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
//				imvPopUp.setVisibility(View.GONE);
			}
		});
		/*myWebView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imvPopUp.setVisibility(View.GONE);				
			}
		});*/
		
		/*myWebView.setOnTouchListener(new View.OnTouchListener() {
		    @Override
		    public boolean onTouch(View v, MotionEvent event) 
		    {
		    	imvPopUp.setVisibility(View.GONE);
		        return true;
		    }
		});*/
		
		
//		ok_btn.setVisibility(View.VISIBLE);
		fab.setVisibility(View.VISIBLE);
		ListFiles(new File(FileUtils.JOURNEYLETTER_FILE_PATH));
	}


	public void ListFiles(File f){
		File[] files=f.listFiles();
		Log.d(TAG,"length:-"+files.length);
		int val= Pref.getInteger(getApplicationContext(), StringUtils.JOURNEYLETTER,-1);
		Log.d(TAG,"pref val:-"+val);
		if(files.length>0){

			if((val+1)>=files.length){
				val=0;
			}
			else{
				val=val+1;
			}
		}
		try{
			Log.d(TAG,"final val:-"+val);
			File soundFile=files[val];
			mPlayer1 = new MediaPlayer();
			mPlayer1.setDataSource(soundFile.toString());
			mPlayer1.prepare();
			if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
				mPlayer1.start();
			}
			int MAX_VOLUME = 100;
			final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
			mPlayer1.setVolume(volume, volume);
			Pref.setInteger(getApplicationContext(),StringUtils.JOURNEYLETTER,val);
			Log.d(TAG,"pref mood:-"+Pref.getInteger(getApplicationContext(),StringUtils.JOURNEYLETTER,-1));
		}
		catch (Exception e){
			Log.d("sunil",e.toString());
		}
	}


	
	 @Override
	 public boolean onTouchEvent(MotionEvent event) {
		 
//		 imvPopUp.setVisibility(View.GONE);
		 return true;
	 }
	
	private String getfileName() 
	{
		String fileName = "letter_2.html";
		switch (habitId) {
		case TableAttributes.drinkWaterId:
			tvSubTitle.setText("Get your morning ritual in place");
			fileName = "letter_1.html";
			break;

		case TableAttributes.greatBreakFastID:
			tvSubTitle.setText("Getting a great breakfast");
			fileName = "letter_2.html";
			break;
			
		case TableAttributes.danceYourWayID:
			tvSubTitle.setText("Seize the day!");
			fileName = "letter_3.html";
			break;
		
		case TableAttributes.goldenChallengeID:
			tvSubTitle.setText("Wrap-up : Week One Golden Triangle");
			fileName = "letter_4.html";
			break;
		
		case TableAttributes.SecretLetterID:
			tvSubTitle.setText("Secret Letter : Reprogram your Mind for Boundless Energy");
			fileName = "letter_5.html";
			break;
		}
		return fileName;
	}
	
	private void setBackgroundMusic() 
	{
		try {
			String musicFile = "music/leter1.mp3";
			// Read the music file from the asset folder
			AssetFileDescriptor afd = getAssets().openFd(musicFile);
			//afd = getAssets().openFd(AppsConstant.getHabitMusic(habitId));
			// Creation of new media player;
			player = new MediaPlayer();
			// Set the player music source.
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),afd.getLength());
			// Set the looping and play the music.
			player.setLooping(false);
			player.prepare();
			player.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private final class AndroidJavaScriptInterface 
	{
		AndroidJavaScriptInterface() 
        {   }
        
		 @JavascriptInterface 
		 public boolean updateUI() 
		 { 
			 int challengeAccepted = userJourneyModel.isChallengeAccepted();
			 if(challengeAccepted>TableAttributes.STATUS_VALUE_0)
				 return true;
			 else
				 return false;
		 } 
		
        @JavascriptInterface 
        public void success() 
        { 
        	//Toast.makeText(WebViewActivity.this, "Status is txn is success "+" payment id is "+paymentId, 8000).show();
        	
//        	tvChallengeAccepted.setText("Challenge Accepted!");
//			tvChallengeAccepted.setCompoundDrawablePadding(5);
//			tvChallengeAccepted.setCompoundDrawablesWithIntrinsicBounds( R.drawable.right, 0, 0, 0);
			update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY,habitId, TableAttributes.CHALLENGE_ACCEPTED, TableAttributes.STATUS_VALUE_1);
			
			Intent i = new Intent(Letter.this, ShareOnFacebookPopUp.class);
			i.putExtra(AppsConstant.h_id, habitId);
			startActivity(i);
        } 
        
        
        @JavascriptInterface
        public void failure() 
        {
            failure("");
//            Toast.makeText(Letter.this, "Failed", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void failure(final String params) 
        {
        	String paymentId=params;
            String status="Faluire";
//        	Toast.makeText(Letter.this, "Failed ", Toast.LENGTH_SHORT).show();
        }
   
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.journey_menu, menu);
        switchButton = menu.findItem(R.id.done);
        if(letterStatus==TableAttributes.LETTER_COMPLETED)
		{
        	switchButton.setIcon(R.drawable.right_green);
		}
        switchButton.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        
        case android.R.id.home:
            onBackPressed();
            return true;
        
        case R.id.done:
            	if(letterStatus <= TableAttributes.LETTER_Opened)
        		{
            		letterStatus = TableAttributes.LETTER_COMPLETED;
            		update.updateJourneyStatus(userName, AppsConstant.SELECTED_JOURNEY, selectedStep);
        		}
            	update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY,habitId, 
            			TableAttributes.LETTER_READ, TableAttributes.LETTER_COMPLETED);
            	
            	switchButton.setIcon(R.drawable.right_green);
            	if(callingFrag!=null && callingFrag.length()>0)
				{
					finish();
				}
                return true;
                
                
        case R.id.action_feedback:
			View rootView = findViewById(android.R.id.content).getRootView();
			rootView.setDrawingCacheEnabled(true);
			Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
			rootView.setDrawingCacheEnabled(false);
			
			Intent feedback = new Intent(Letter.this, Feedback.class);
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
	 protected void onPause() 
	 {
		 super.onPause();
		 if(player!=null && player.isPlaying())
			 player.pause();

		 if(mPlayer1!=null){
			 mPlayer1.pause();
		 }
	 }
	
	 @Override
	 protected void onResume() 
	 {
		 super.onResume();
		 if(letterStatus==TableAttributes.LETTER_Unread)
		 {
			 if(player!=null)
				 player.start();
			 else
			 {
//				 setBackgroundMusic();
			 } 
		 }
	 }
	    
	 void stopBackgroundMusic()
	 {
		 if(player!=null)
		 {
			 player.stop();
			 player = null;
		 }
	 }
	 @Override
	 protected void onStop() {
		 super.onStop();
		 stopBackgroundMusic();
	 }
	 
	 @Override
	 public void onDestroy() {
		 super.onDestroy();
		 stopBackgroundMusic();
		 if(mPlayer1!=null){
			 mPlayer1.stop();
		 }
	 }
	 
	 @Override
	public void onBackPressed() {
		super.onBackPressed();
		stopBackgroundMusic();		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fab:
			if(letterStatus <= TableAttributes.LETTER_Opened)
    		{
        		letterStatus = TableAttributes.LETTER_COMPLETED;
        		update.updateJourneyStatus(userName, AppsConstant.SELECTED_JOURNEY, selectedStep);
    		}
        	update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY,habitId, 
        			TableAttributes.LETTER_READ, TableAttributes.LETTER_COMPLETED);
        	
        	switchButton.setIcon(R.drawable.right_green);
        	if(callingFrag!=null && callingFrag.length()>0)
			{
				finish();
			}
			break;
		}
	}
	public void makeFloatColor(){
		fab.setColorNormal(getResources().getColor(R.color.green));
	}
}

