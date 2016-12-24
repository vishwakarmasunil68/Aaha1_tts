package com.motivator.wecareyou;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;

public class Feedback extends Activity {
	
	String userName;
	ActionBar actionBar;
	RelativeLayout rllScreenshot;
	ImageView imvCross, imvScreenshot;
	EditText edtFeedback;
	Bitmap bitmap;
	boolean sendScreenshot = true;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.feedback);
        
        
        SpannableString s = new SpannableString("feedback");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
      //Set ACTION BAR
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("Feedback");
        
        actionBar.setTitle(s);
        
      //get Data from shared prefrences and intent
		userName = GeneralUtility.getPreferences(Feedback.this, AppsConstant.user_name);
		//bitmap = (Bitmap) getIntent().getParcelableExtra(AppsConstant.sreenshot);
		
		bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra(AppsConstant.sreenshot),
				0,getIntent().getByteArrayExtra(AppsConstant.sreenshot).length);        

		//Initializing UI VIews
		rllScreenshot = (RelativeLayout)findViewById(R.id.rll_screenshot);
		imvCross = (ImageView)findViewById(R.id.imv_cross);
		imvScreenshot = (ImageView)findViewById(R.id.imv_screenshot);
		edtFeedback = (EditText)findViewById(R.id.edt_feedback);
		
		imvScreenshot.setImageBitmap(bitmap);
		
		imvCross.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				rllScreenshot.setVisibility(View.GONE);	
				sendScreenshot = false;
			}
		});
		
	}

	
	
	 private File savebitmap(Bitmap bmp) 
	 {
		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		OutputStream outStream = null;
		File file = new File(extStorageDirectory, "AahaImg.png");
		if (file.exists()) {
			file.delete();
			file = new File(extStorageDirectory, "AahaImg.png");
		}

		try {
			outStream = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return file;
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);
        MenuItem switchButton = menu.findItem(R.id.action);
        switchButton.setIcon(R.drawable.send);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        
        case android.R.id.home:
            onBackPressed();
            return true;
        
        case R.id.action:
        	Intent emailIntent;
        	String feedback = edtFeedback.getText().toString();
        	if(feedback!=null && feedback.trim().length()>0)
        	{
        		if(sendScreenshot && bitmap!=null)
        		{
            		File  mFile = savebitmap(bitmap);
                	Uri u = null;
                	u = Uri.fromFile(mFile);

                	emailIntent = new Intent(Intent.ACTION_SEND);
                	emailIntent.setType("image/*");
                	emailIntent.putExtra(Intent.EXTRA_STREAM, u);
        		}
        		else
        		{
        			emailIntent = new Intent(Intent.ACTION_SEND);
        			emailIntent.setType("plain/text");
        		}
        		
            	try {
            		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
            		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Aaha Feedback");
                	// feed.get(Selectedposition).DETAIL_OBJECT.IMG_URL
                	emailIntent.putExtra(Intent.EXTRA_TEXT, feedback);
   				    startActivity(emailIntent);
   				 //startActivity(Intent.createChooser(emailIntent, "Send email..."));
   				} catch (Exception e) 
   				{
   					Toast.makeText(Feedback.this, "No App found to share this content.", 5).show();
   				}
        	}
        	else
        		edtFeedback.setError("please provide us your feedback");
        	
        	return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
}

