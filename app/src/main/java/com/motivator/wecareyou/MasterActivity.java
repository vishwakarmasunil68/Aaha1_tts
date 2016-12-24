package com.motivator.wecareyou;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.common.WebServices;
import com.motivator.support.ClipRevealFrame;
import com.ogaclejapan.arclayout.ArcLayout;

public class MasterActivity extends Activity implements OnClickListener
{
	int SCREEN_TAP = 1;
	RelativeLayout rllHome;
	TextView tvInfo, tvClickDo;
	ImageView imvProfilePic, imvBack;
	Button btnFeel, btnDo, btnThink, btnChat,btnSee;
	String userID, userName, emailID;
	String userImgUrl;
	Bitmap bitmap;
	
	int SELECT_IMAGE = 10;
	
	// View rootLayout;
	 ClipRevealFrame menuLayout;
	 ArcLayout arcLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_master);
		
		userID = GeneralUtility.getPreferences(MasterActivity.this, "user_id");
		userName = GeneralUtility.getPreferences(MasterActivity.this, AppsConstant.user_name);
		emailID = GeneralUtility.getPreferences(MasterActivity.this, "user_email");
		userImgUrl = GeneralUtility.getPreferences(MasterActivity.this, "user_img_url");
		
		Typeface tfPhilospher = Typeface.createFromAsset(MasterActivity.this.getAssets(), "fonts/Philosopher-Regular.ttf");
		
		//rootLayout = findViewById(R.id.root_layout);
		rllHome = (RelativeLayout)findViewById(R.id.root_layout);
	    menuLayout = (ClipRevealFrame) findViewById(R.id.menu_layout);
	    arcLayout = (ArcLayout) findViewById(R.id.arc_layout);
	    
	    tvInfo = (TextView)findViewById(R.id.tv_info);
		tvClickDo = (TextView)findViewById(R.id.tv_click_do);
		
		imvBack = (ImageView)findViewById(R.id.imv_back);
		
		btnFeel= (Button)findViewById(R.id.btn_feel);
		btnDo= (Button)findViewById(R.id.btn_do);
		btnThink= (Button)findViewById(R.id.btn_think);
		btnChat= (Button)findViewById(R.id.btn_chat);
		btnSee= (Button)findViewById(R.id.btn_see);
	    
		
		rllHome.setOnClickListener(this);
		//imvProfilePic.setOnClickListener(this);
		imvBack.setOnClickListener(this);
		btnFeel.setOnClickListener(this);
		btnDo.setOnClickListener(this);
		btnThink.setOnClickListener(this);
		btnChat.setOnClickListener(this);
		btnSee.setOnClickListener(this);
		
		tvInfo.setTypeface(tfPhilospher);
		tvClickDo.setTypeface(tfPhilospher);
		tvInfo.setText("Follow and tap the white dot");
		
		/*
		if(userImgUrl != null && userImgUrl.length()>0)
		{
			Thread th = new Thread(new Runnable() {
				
				@Override
				public void run() 
				{
					final Bitmap b= GeneralUtility.getImage(userImgUrl);
					runOnUiThread(new  Runnable() {
						public void run() {
							if(b!=null)
								imvProfilePic.setImageBitmap(b);
							//b.recycle();
						}
					});
				}
			}); th.start();
			
		}*/
		
		/*SpannableString ss = new SpannableString("Android is a Software stack");
		ClickableSpan clickableSpan = new ClickableSpan() {
		    @Override
		    public void onClick(View textView) {
		        startActivity(new Intent(HomeActivity.this, Journey.class));
		    }
		    @Override
		    public void updateDrawState(TextPaint ds) {
		            super.updateDrawState(ds);
		            ds.setUnderlineText(false);
		        }
		};
		ss.setSpan(clickableSpan, 22, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		btnDo.setText(ss);
		btnDo.setMovementMethod(LinkMovementMethod.getInstance());
		btnDo.setHighlightColor(Color.TRANSPARENT);*/
		
		
		
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		
		case R.id.root_layout:
			onTapScreen();
			break;
			
//		case R.id.imv_user_pic:
//			Intent intent = new Intent();
//			intent.setType("image/*");
//			intent.setAction(Intent.ACTION_GET_CONTENT);
//			startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
//			break;

		case R.id.imv_back:
			clearData();
			Intent i = new Intent(MasterActivity.this, KickStart.class);
			startActivity(i);
			finish();
			break;
			
		case R.id.btn_feel:
			onTapScreen();
			break;
			
		case R.id.btn_think:
			onTapScreen();
			break;
			
		case R.id.btn_chat:
			onTapScreen();
			break;
			
		case R.id.btn_see:
			onTapScreen();			
			break;
			
		case R.id.btn_do:
			if(SCREEN_TAP>=7)
			{
				Intent in = new Intent(MasterActivity.this, Chapter1.class);
				startActivity(in);
				finish();
			}
			else
				onTapScreen();
			
			break;
		}
		
	}
	
	private void onTapScreen()
	{
		switch (SCREEN_TAP) {
		case 1:
			SCREEN_TAP++;
			btnFeel.setBackgroundResource(R.drawable.feel);
			btnSee.setBackgroundResource(R.drawable.see);
			btnThink.setBackgroundResource(R.drawable.think);
			btnChat.setBackgroundResource(R.drawable.chat);
			btnDo.setBackgroundResource(R.drawable.do1);
			tvInfo.setText("This is where you track your habits");
			break;

		case 2:
			SCREEN_TAP++;
			tvInfo.setText("This is where you can relax and rejoice");
			btnFeel.setBackgroundResource(R.drawable.feel1);
			btnSee.setBackgroundResource(R.drawable.see);
			btnThink.setBackgroundResource(R.drawable.think);
			btnChat.setBackgroundResource(R.drawable.chat);
			btnDo.setBackgroundResource(R.drawable.do2);
			break;
			
		case 3:
			SCREEN_TAP++;
			tvInfo.setText("This is where you can find amazing tools to knockout street and other problems");
			
			btnFeel.setBackgroundResource(R.drawable.feel);
			btnSee.setBackgroundResource(R.drawable.see);
			btnThink.setBackgroundResource(R.drawable.think1);
			btnChat.setBackgroundResource(R.drawable.chat);
			btnDo.setBackgroundResource(R.drawable.do2);
			break;
			
		case 4:
			SCREEN_TAP++;
			tvInfo.setText("This is where you track progress, get amazing insights and other important stuffs");
			btnFeel.setBackgroundResource(R.drawable.feel);
			btnSee.setBackgroundResource(R.drawable.see1);
			btnThink.setBackgroundResource(R.drawable.think);
			btnChat.setBackgroundResource(R.drawable.chat);
			btnDo.setBackgroundResource(R.drawable.do2);
			break;
		case 5:
			SCREEN_TAP++;
			tvInfo.setText("This is where you share and celebrate with friends and family");
			btnFeel.setBackgroundResource(R.drawable.feel);
			btnSee.setBackgroundResource(R.drawable.see);
			btnThink.setBackgroundResource(R.drawable.think);
			btnChat.setBackgroundResource(R.drawable.chat1);
			btnDo.setBackgroundResource(R.drawable.do2);
			
			break;
		case 6:
			SCREEN_TAP++;
			btnFeel.setBackgroundResource(R.drawable.feel);
			btnSee.setBackgroundResource(R.drawable.see);
			btnThink.setBackgroundResource(R.drawable.think);
			btnChat.setBackgroundResource(R.drawable.chat);
			btnDo.setBackgroundResource(R.drawable.do_click);
			tvInfo.setText("Let's get started with some habits!");
			tvClickDo.setVisibility(View.VISIBLE);
			
			break;
		}
		
	}

	private void clearData() 
	{
		GeneralUtility.setPreferences(MasterActivity.this, AppsConstant.user_name, "");
		GeneralUtility.setPreferences(MasterActivity.this, "user_email", "");
		GeneralUtility.setPreferences(MasterActivity.this, "user_id", "");
		GeneralUtility.setPreferences(MasterActivity.this, "user_img_url", "");
		
	}

	/*class UploadImage extends AsyncTask<String, Void, String>
	   {
		   ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		   String jsonResponse;
		   ProgressDialog progressDialog;
		   String password;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progressDialog = ProgressDialog.show(MasterActivity.this, "Please wait...","");
				progressDialog.setCancelable(true);
			}
		@Override
		protected String doInBackground(String... params) {
			try {
				//jsonResponse = GeneralUtility.httpCall(params[0], nameValuePairs);
				jsonResponse = GeneralUtility.httpCallforImage(params[0],"setimage",  params[1], params[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonResponse;
		}
		@Override
		protected void onPostExecute(String jResponse) 
		{
			super.onPostExecute(jResponse);
			if(progressDialog!=null)
				progressDialog.dismiss();
			
			if(jResponse!=null && jResponse.length()>0)
			{
				try
				{
					JSONObject jObj = new JSONObject(jResponse);
					String isSuccess = jObj.getString("isSuccess");
					String successMsg = jObj.getString("success_msg");
					String result = jObj.getString("result");
					if(isSuccess.equalsIgnoreCase("true"))
					{
						Toast.makeText(MasterActivity.this, R.string.image_uploaded, 5).show();
						imvProfilePic.setImageBitmap(bitmap);
//						bitmap.
					}
					else
						Toast.makeText(MasterActivity.this, successMsg, 5).show();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		   
	   }*/
	
	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == RESULT_OK)
            {
                if (data != null) 
                {
                    try {
                    	
                    	Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();

                        if(GeneralUtility.isNetworkAvailable(MasterActivity.this))
                	    	new UploadImage().execute(WebServices.ACCESS_LOGIN, userID, filePath);
                	    else
                	    	GeneralUtility.PopUpInfo(MasterActivity.this, R.string.network_failure, R.string.no_internet);

                        bitmap = BitmapFactory.decodeFile(filePath);

                        //bitmap = MediaStore.Images.Media.getBitmap(HomeActivity.this.getContentResolver(), data.getData());
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(MasterActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
*/
}
