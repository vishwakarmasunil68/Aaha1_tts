package com.motivator.wecareyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;
import com.motivator.common.AppsConstant;
import com.motivator.common.FacebookWallPost;
import com.motivator.common.GeneralUtility;
import com.motivator.database.UpdateData;

public class ShareOnFacebookPopUp extends Activity {
	
	int habitId;
	String userName, selectedRitual;
	
	private static String APP_ID = "234992030177331";
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        
//        userName = GeneralUtility.getPreferences(ShareOnFacebook.this, AppsConstant.user_name);
//        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        habitId = getIntent().getExtras().getInt(AppsConstant.h_id);
        
        setContentView(R.layout.share_on_fb);
        
        TextView tvShareFb = (TextView)findViewById(R.id.tv_share_fb);
        TextView tvShareFbTitle = (TextView)findViewById(R.id.tv_share_fb_title);
        TextView tvShareFbDesc = (TextView)findViewById(R.id.tv_share_fb_desc);
        Button tvOk = (Button)findViewById(R.id.tv_share);
        Button tvCancel = (Button)findViewById(R.id.tv_continue);
        
        tvShareFb.setTypeface(GeneralUtility.setTypeFace(ShareOnFacebookPopUp.this));
        tvShareFbTitle.setTypeface(GeneralUtility.setTypeFace(ShareOnFacebookPopUp.this));
        tvShareFbDesc.setTypeface(GeneralUtility.setTypeFace(ShareOnFacebookPopUp.this));
        
        tvOk.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) 
			{
				FacebookWallPost fb = new FacebookWallPost();
				
				if (! facebook.isSessionValid()) {
					fb.loginAndPostToWall(ShareOnFacebookPopUp.this, facebook, habitId);
				}
				else {
					fb.publishStory(ShareOnFacebookPopUp.this, facebook, habitId);
				}	
			}
		});
        
        tvCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				finish();				
			}
		});
        
       
        mAsyncRunner = new AsyncFacebookRunner(facebook);
	}

	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	     // Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	      facebook.authorizeCallback(requestCode, resultCode, data);
	      finish();	
	  }
}