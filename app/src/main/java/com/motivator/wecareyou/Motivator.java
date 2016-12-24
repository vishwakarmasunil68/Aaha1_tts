package com.motivator.wecareyou;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.JourneyData;
import com.motivator.model.JourneyHabitModel;

public class Motivator extends Activity {
	
	int habitId, motivatorNum;
	String callingFrag;
	String userName, selectedStep;
	ActionBar actionBar;
	MenuItem switchButton;
	LinearLayout llTop;
	WebView webView;
	
	UpdateData update;
	GetData getData;
	JourneyHabitModel userJourneyModel = new JourneyHabitModel();
	
	int COMPARE_WITH = TableAttributes.STATUS_VALUE_0;
	int motivatorStatus=TableAttributes.STATUS_VALUE_1;
	public static final String Motivator_Num = "Motivator_Num";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getData = new GetData(Motivator.this);
        update = new UpdateData(Motivator.this);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.motivator);
        
		
      //Set ACTION BAR
        actionBar = getActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("motivator");
        
        SpannableString s = new SpannableString("Motivator");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        actionBar.setTitle(s);
        
      //get Data from shared prefrences and intent
		userName = GeneralUtility.getPreferences(Motivator.this, AppsConstant.user_name);
		selectedStep = getIntent().getExtras().getString(AppsConstant.selected_journey_step);
		habitId = getIntent().getExtras().getInt(AppsConstant.h_id);
		motivatorNum = getIntent().getExtras().getInt(Motivator_Num);
		callingFrag = getIntent().getExtras().getString(AppsConstant.calling_frag);
		
		userJourneyModel = getData.getJourneyHabit(userName, habitId);
		motivatorStatus = userJourneyModel.getMotivationStatus();
		
		switch (motivatorNum) {
		case 1:
			COMPARE_WITH = TableAttributes.STATUS_VALUE_1;
			//updateUIView(COMPARE_WITH);
			break;

		case 2:
			COMPARE_WITH = TableAttributes.STATUS_VALUE_2;
			//updateUIView(COMPARE_WITH);
			break;
			
		case 3:
			COMPARE_WITH = 3;
			//updateUIView(COMPARE_WITH);
			break;
			
		case 4:
			COMPARE_WITH = 4;
			//updateUIView(COMPARE_WITH);
			break;
			
		case 5:
			COMPARE_WITH = 5;
			//updateUIView(COMPARE_WITH);
			break;
			
		case 6:
			COMPARE_WITH = 6;
			//updateUIView(COMPARE_WITH);
			break;
		}
		
		llTop = (LinearLayout)findViewById(R.id.ll_motivator_top);
		webView = (WebView)findViewById(R.id.tv_motivator_txt);
		//tvMotivatorText.setTypeface(GeneralUtility.setTypeFace(Motivator.this));
		
		llTop.setBackgroundResource(JourneyData.getMotivatorImg(habitId));
		setContent();
	}
	
	
	void setContent()
	{
		String fileName = getfileName();		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("file:///android_asset/"+fileName);
		//setContentView(webView);
		
//		try {
//			InputStream is = getAssets().open(fileName);
//			
//			// We guarantee that the available method returns the total
//			// size of the asset...  of course, this does mean that a single
//			// asset can't be more than 2 gigs.
//			int size = is.available();
//	            
//			// Read the entire asset into a local byte buffer.
//			byte[] buffer = new byte[size];
//			is.read(buffer);
//			is.close();
//			
//			// Convert the buffer into a string.
//			String text = new String(buffer);			
//			// Finally stick the string into the text view.
//			tvMotivatorText.setText(Html.fromHtml(text));
//		} catch (IOException e) {
//			// Should never happen!
//	            throw new RuntimeException(e);
//		}
	}
	
	private String getfileName() 
	{
		String fileName = "html_files/great_breakfast_motivator1.html";
		switch (habitId) {
		case TableAttributes.drinkWaterId:
			fileName = "html_files/drink_water_motivator.html";
			break;

		case TableAttributes.greatBreakFastID:
			switch (motivatorNum) {
			case 1:
				fileName = "html_files/great_breakfast_motivator1.html";
				break;
			case 2:
				fileName = "html_files/great_breakfast_motivator2.html";
				break;
			case 3:
				fileName = "html_files/great_breakfast_motivator3.html";
				break;
			}
			
			break;
			
		case TableAttributes.danceYourWayID:
			switch (motivatorNum) {
			case 1:
				fileName = "html_files/dance_motivator1.html";
				break;
			case 2:
				fileName = "html_files/dance_motivator2.html";
				break;
			case 3:
				fileName = "html_files/dance_motivator3.html";
				break;
			case 4:
				fileName = "html_files/dance_motivator4.html";
				break;
			case 5:
				fileName = "html_files/dance_motivator5.html";
				break;
			case 6:
				fileName = "html_files/dance_motivator6.html";
				break;
			}
			break;
		
		}
		return fileName;
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.journey_menu, menu);
        switchButton = menu.findItem(R.id.done);
        if(motivatorStatus >= COMPARE_WITH)
		{
        	switchButton.setIcon(R.drawable.right_green);
		}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        
        case android.R.id.home:
            onBackPressed();
            return true;
            
        case R.id.done:
        	switchButton.setIcon(R.drawable.right_green);
        	if(motivatorStatus < COMPARE_WITH)
        	{
        		++motivatorStatus;
        		update.updateJourneyStatus(userName, AppsConstant.SELECTED_JOURNEY, selectedStep);
        	}
        	update.updateJourneyHabit(userName, AppsConstant.SELECTED_JOURNEY,habitId, TableAttributes.MOTIVATION, motivatorStatus);
        	
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
			
			Intent feedback = new Intent(Motivator.this, Feedback.class);
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
    
}

