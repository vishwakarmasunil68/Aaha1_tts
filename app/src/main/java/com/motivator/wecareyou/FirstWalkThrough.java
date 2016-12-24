package com.motivator.wecareyou;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.database.GetData;
import com.motivator.model.UserRitualModel;
import com.motivator.support.FileUtils;
import com.motivator.support.StringUtils;

import java.io.File;
import java.util.Locale;

public class FirstWalkThrough extends Activity{

	String userName;
	String selectedRitual;

	RelativeLayout rllInfo;
	ImageView imvClick;
	TextView tvRitualName,tvRitualTime, tvInfo;
	ImageView imvRitual;

	GetData getData;
	UserRitualModel userRitual = new UserRitualModel();
	TextToSpeech tts;
	String firstwalkthrough="";
	public static MediaPlayer mPlayer1;
	MediaPlayer mPlayer;
	private final String TAG="walkthrough";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getData = new GetData(FirstWalkThrough.this);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.first_walkthrough);

		SpannableString s = new SpannableString(getResources().getString(R.string.app_name));
		s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setTitle(getResources().getString(R.string.app_name));

		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setDisplayUseLogoEnabled(false);

		getActionBar().setTitle(s);

		userName = GeneralUtility.getPreferences(FirstWalkThrough.this, AppsConstant.user_name);
		final SharedPreferences sp=getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
		firstwalkthrough=sp.getString("firstwalkthrough", "");
//		Log.d("sunil","firstwalkthrough:-"+firstwalkthrough);
		if(firstwalkthrough.equals("")){
			try{
				mPlayer = MediaPlayer.create(FirstWalkThrough.this, R.raw.second_first_walk_through);
				mPlayer.start();
//				mPlayer1= MediaPlayer.create(FirstWalkThrough.this, R.raw.walkthrough_2);
//				mPlayer1.start();
//				int MAX_VOLUME = 100;
//				final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
//				mPlayer1.setVolume(volume, volume);
				ListFiles(new File(FileUtils.WALKTHROUGH_FILE_PATH));
			}
			catch(Exception e){
				Log.d("sun",e.toString());
			}
			SharedPreferences.Editor editor=sp.edit();
			editor.putString("firstwalkthrough", "done");
			editor.commit();

//        	tts = new TextToSpeech(getApplicationContext(), new OnInitListener() {
//
//    			@Override
//    			public void onInit(int status) {
//    				// TODO Auto-generated method stub
//    				 tts = new TextToSpeech(getApplicationContext(), new OnInitListener() {
//
//    		    			@Override
//    		    			public void onInit(int status) {
//    		    				// TODO Auto-generated method stub
//    		    				SharedPreferences.Editor editor=sp.edit();
//    		    				editor.commit();
//    		    				convertTextToSpeech(status, "Everything you do is divided into rituals, or routines'... They are divided into the morning, afternoon, and evening time.It’s my job to help you on your journey. I will help you organize your tasks, also known as rituals, into the three time blocks. Let’s take a look.");
//    		    			}
//    		    		});
//    			}
//    		});	
		}
		userRitual = getData.getRitualsDetails(userName, selectedRitual);
	}
	public void ListFiles(File f){
		File[] files=f.listFiles();
		Log.d(TAG,"length:-"+files.length);
		int val= Pref.getInteger(getApplicationContext(), StringUtils.WALKTHROUGH,-1);
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
			mPlayer1 .setDataSource(soundFile.toString());
			mPlayer1 .prepare();
			if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
				mPlayer1 .start();
			}
			int MAX_VOLUME = 100;
			final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
			mPlayer1 .setVolume(volume, volume);
			Pref.setInteger(getApplicationContext(),StringUtils.WALKTHROUGH,val);
			Log.d(TAG,"pref mood:-"+Pref.getInteger(getApplicationContext(),StringUtils.WALKTHROUGH,-1));
		}
		catch (Exception e){
			Log.d("sunil",e.toString());
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mPlayer!=null){
			mPlayer.stop();
		}
		if(mPlayer1!=null){
			mPlayer1.stop();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(mPlayer!=null){
			mPlayer.stop();
		}
		if(mPlayer1!=null){
			mPlayer1.stop();
		}
	}

	private void convertTextToSpeech(int status, String text)
	{
		if (status == TextToSpeech.SUCCESS)
		{
			int lang = tts.setLanguage(Locale.US);
			if (lang == TextToSpeech.LANG_MISSING_DATA
					|| lang == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("error", "This Language is not supported");
			}
			else {
				if (null == text || "".equals(text)) {
					text = "";
				}
				tts.setPitch(1.0f);
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			}
		} else {
			Log.e("error", "Initilization Failed!");
		}
	}


}

