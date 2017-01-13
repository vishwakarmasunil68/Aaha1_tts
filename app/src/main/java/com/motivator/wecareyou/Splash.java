package com.motivator.wecareyou;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.sacot41.scviewpager.DotsView;
import com.dev.sacot41.scviewpager.SCPositionAnimation;
import com.dev.sacot41.scviewpager.SCViewAnimation;
import com.dev.sacot41.scviewpager.SCViewAnimationUtil;
import com.dev.sacot41.scviewpager.SCViewPager;
import com.dev.sacot41.scviewpager.SCViewPagerAdapter;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.DatabaseHelper;
import com.motivator.support.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@SuppressLint("NewApi")
public class Splash extends FragmentActivity implements OnClickListener{
	TextToSpeech tts;
    private static final int NUM_PAGES = 6;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS=1;
    private SCViewPager mViewPager;
    private SCViewPagerAdapter mPageAdapter;
    private DotsView mDotsView;
    Button login_btn;
    TextView tvLetsStart;
    String flag="";
    MediaPlayer mPlayer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);
        FileUtils.CreateALlMoodMusicDirectories();
        if(checkAndRequestPermissions()){
            makeSplash();
        }
        
    }
    private  boolean checkAndRequestPermissions() {
        int ACCESS_NETWORK_STATE= ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE);
        int INTERNET = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET);
        int GET_ACCOUNTS= ContextCompat.checkSelfPermission(this, android.Manifest.permission.GET_ACCOUNTS);
        int WRITE_EXTERNAL_STORAGE= ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int WAKE_LOCK= ContextCompat.checkSelfPermission(this, android.Manifest.permission.WAKE_LOCK);
        int RECEIVE_BOOT_COMPLETED = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_BOOT_COMPLETED);
        int BLUETOOTH = ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ACCESS_NETWORK_STATE  != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (INTERNET != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.INTERNET);
        }
        if (GET_ACCOUNTS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.GET_ACCOUNTS);
        }
        if (WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (WAKE_LOCK != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WAKE_LOCK);
        }
        if (RECEIVE_BOOT_COMPLETED != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.RECEIVE_BOOT_COMPLETED);
        }
        if (BLUETOOTH != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.BLUETOOTH);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("msg", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.GET_ACCOUNTS, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WAKE_LOCK, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.RECEIVE_BOOT_COMPLETED, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.BLUETOOTH, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(android.Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.RECEIVE_BOOT_COMPLETED) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            ) {
                        Log.d("msg", "All Permissions granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                        makeSplash();
                    } else {
                        Log.d("msg", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_NETWORK_STATE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.INTERNET)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.GET_ACCOUNTS)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WAKE_LOCK)
                                        || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)
                                        || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH)
                                        || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                ) {
                            showDialogOK("SMS and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }
    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    public void makeSplash(){

        login_btn= (Button) findViewById(R.id.login_button);
        login_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this,LoginActivity.class));
                finish();
            }
        });
        final SharedPreferences sp=getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        flag=sp.getString("flag", "");
        if(flag.toLowerCase().equals("done")){

        }
        else{
            editor.putString("flag", "done");
            editor.commit();
//			mPlayer = MediaPlayer.create(Splash.this, R.raw.welcome_msg);
//            mPlayer.start();
            try{
                String musicFile = "music/leter1.mp3";
                AssetFileDescriptor afd = getAssets().openFd(musicFile);
                mPlayer = new MediaPlayer();			//
                mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),afd.getLength());
                mPlayer.setLooping(false);
                mPlayer.prepare();
                mPlayer.start();
            }
            catch(Exception e){
                Log.d("sun",e.toString());
            }

        }
//        tts = new TextToSpeech(getApplicationContext(), new OnInitListener() {
//
//			@Override
//			public void onInit(int status) {
//				// TODO Auto-generated method stub
//				SharedPreferences.Editor editor=sp.edit();
//				editor.putString("flag", "done");
//				editor.commit();
//				convertTextToSpeech(status, "Hey, Nice to meet you. I'm here to help you become a better you. Not that there's anything wrong with the current you. But everyone has room to improve. First, I'd love to learn more about you..");
//			}
//		});
        createDataBase();
        String userName = GeneralUtility.getPreferences(Splash.this, AppsConstant.user_name);
        Log.d("sunil","username:-"+userName);
        if((userName.equalsIgnoreCase("")))
        {
            GeneralUtility.setPreferencesBoolean(Splash.this, AppsConstant.AVS_SOUND, true);
        }
        else
        {
            Intent start;
        	/*start =new Intent(Splash.this, MainActivity.class);
        	startActivity(start);
        	finish();*/
            //Check is user coming first time in the app (by cheking isRitualAdded in database)
            boolean isRitualAdded = GeneralUtility.getPreferencesBoolean(Splash.this, AppsConstant.IS_RITUAL_ADDED);
            if(isRitualAdded)
            {
                start=new Intent(Splash.this, MainActivity.class);
                startActivity(start);
                finish();
            }
            else
            {
                start=new Intent(Splash.this, FirstWalkThrough.class);
                startActivity(start);
                finish();
            }

        }

        tvLetsStart=(TextView)findViewById(R.id.tv_started);
        tvLetsStart.setOnClickListener(this);


        mViewPager = (SCViewPager) findViewById(R.id.viewpager_main_activity);
        mDotsView = (DotsView) findViewById(R.id.dotsview_main);
        mDotsView.setDotRessource(R.drawable.dot_selected, R.drawable.dot_unselected);
        mDotsView.setNumberOfPage(NUM_PAGES);

        mPageAdapter = new SCViewPagerAdapter(getSupportFragmentManager());
        mPageAdapter.setNumberOfPage(NUM_PAGES);
        mPageAdapter.setFragmentBackgroundColor(getResources().getColor(R.color.theme_100));
        mViewPager.setAdapter(mPageAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mDotsView.selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        final Point size = SCViewAnimationUtil.getDisplaySize(this);

        //Image 1
        View nameTag = findViewById(R.id.imv_journey1);
        SCViewAnimation nameTagAnimation = new SCViewAnimation(nameTag);
        nameTagAnimation.addPageAnimation(new SCPositionAnimation(this, 0,-size.x,0));
        mViewPager.addAnimation(nameTagAnimation);
        //Text 1
        View currentlyWork = findViewById(R.id.txt_journey1);
        SCViewAnimation currentlyWorkAnimation = new SCViewAnimation(currentlyWork);
        currentlyWorkAnimation.addPageAnimation(new SCPositionAnimation(this, 0, 0, 0));
        currentlyWorkAnimation.addPageAnimation(new SCPositionAnimation(this, 0, -size.x, 0));
        mViewPager.addAnimation(currentlyWorkAnimation);

        //Image2
        View mobileView = findViewById(R.id.imv_journey2);
        SCViewAnimation mobileAnimation = new SCViewAnimation(mobileView);
        mobileAnimation.startToPosition((size.x), null);
        mobileAnimation.addPageAnimation(new SCPositionAnimation(this, 0, -size.x,0));
        mobileAnimation.addPageAnimation(new SCPositionAnimation(this, 1, -size.x ,0));
        mViewPager.addAnimation(mobileAnimation);
        //Image2b
       /* View arduinoView = findViewById(R.id.img_acc);
        SCViewAnimation arduinoAnimation = new SCViewAnimation(arduinoView);
        arduinoAnimation.startToPosition((int)(size.x*2), null);
        arduinoAnimation.addPageAnimation(new SCPositionAnimation(this, 0, - size.x *2, 0));
        arduinoAnimation.addPageAnimation(new SCPositionAnimation(this, 1, - size.x*2, 0));
        mViewPager.addAnimation(arduinoAnimation);*/
        //Text2
        View butView = findViewById(R.id.txt_journey2);
        SCViewAnimation butAnimation = new SCViewAnimation(butView);
        butAnimation.startToPosition((int)(size.x*2), null);
        butAnimation.addPageAnimation(new SCPositionAnimation(this, 0, -(int)(size.x*2),0));
        butAnimation.addPageAnimation(new SCPositionAnimation(this, 1, -(int)(size.x*2) ,0));
        mViewPager.addAnimation(butAnimation);

        //Image3
        View diplomeView = findViewById(R.id.imv_journey3);
        SCViewAnimation diplomeAnimation = new SCViewAnimation(diplomeView);
        diplomeAnimation.startToPosition((size.x), null);
        diplomeAnimation.addPageAnimation(new SCPositionAnimation(this, 1, -size.x,0));
        diplomeAnimation.addPageAnimation(new SCPositionAnimation(this, 2, -size.x ,0));
        mViewPager.addAnimation(diplomeAnimation);
        //Image3b
       /* View raspberryView = findViewById(R.id.img_cross);
        SCViewAnimation raspberryAnimation = new SCViewAnimation(raspberryView);
        raspberryAnimation.startToPosition((int)(size.x*1.5), null);
        raspberryAnimation.addPageAnimation(new SCPositionAnimation(this, 1, -(int)(size.x*1.5), 0));
        raspberryAnimation.addPageAnimation(new SCPositionAnimation(this, 2, -(int)(size.x*1.5), 0));
        mViewPager.addAnimation(raspberryAnimation);*/
        //Text3
        View whyView = findViewById(R.id.txt_journey3);
        SCViewAnimation whyAnimation = new SCViewAnimation(whyView);
        whyAnimation.startToPosition(size.x*2, null);
        whyAnimation.addPageAnimation(new SCPositionAnimation(this, 1, -size.x*2, 0));
        whyAnimation.addPageAnimation(new SCPositionAnimation(this, 2, -size.x*2, 0));
        mViewPager.addAnimation(whyAnimation);

        //Image4
        View atSkex = findViewById(R.id.imv_journey4);
        SCViewAnimationUtil.prepareViewToGetSize(atSkex);
        SCViewAnimation atSkexAnimation = new SCViewAnimation(atSkex);
        atSkexAnimation.startToPosition((size.x), null);
        atSkexAnimation.addPageAnimation(new SCPositionAnimation(this, 2, -size.x,0));
        atSkexAnimation.addPageAnimation(new SCPositionAnimation(this, 3, -size.x,0));
        mViewPager.addAnimation(atSkexAnimation);
        //Image4b
        /*View ios = findViewById(R.id.img_as);
        SCViewAnimation asAnimation = new SCViewAnimation(ios);
        asAnimation.startToPosition((int)(size.x*1.5),null);
        asAnimation.addPageAnimation(new SCPositionAnimation(this, 2, -(int)(size.x*1.5), 0));
        asAnimation.addPageAnimation(new SCPositionAnimation(this, 3, -(int)(size.x*1.5) ,0));
        mViewPager.addAnimation(asAnimation);*/
        //Text4
        View commonlyView = findViewById(R.id.txt_journey4);
        SCViewAnimation commonlyAnimation = new SCViewAnimation(commonlyView);
        commonlyAnimation.startToPosition(size.x*2, null);
        commonlyAnimation.addPageAnimation(new SCPositionAnimation(this, 2, -size.x*2, 0));
        commonlyAnimation.addPageAnimation(new SCPositionAnimation(this, 3, -size.x*2, 0));
        mViewPager.addAnimation(commonlyAnimation);

        //Image5
        View connectedDeviceView = findViewById(R.id.imv_journey5);
        SCViewAnimation connectedDeviceAnimation = new SCViewAnimation(connectedDeviceView);
        connectedDeviceAnimation.startToPosition((int)(size.x), null);
        connectedDeviceAnimation.addPageAnimation(new SCPositionAnimation(this, 3, -(int) (size.x), 0));
        connectedDeviceAnimation.addPageAnimation(new SCPositionAnimation(this, 4,  -(int)(size.x), 0));
        mViewPager.addAnimation(connectedDeviceAnimation);
        //Image5b
        /*View djangoView = findViewById(R.id.img_home);
        SCViewAnimation djangoAnimation = new SCViewAnimation(djangoView);
        djangoAnimation.startToPosition((int)(size.x*1.5),null);
        djangoAnimation.addPageAnimation(new SCPositionAnimation(this, 3, -(int)(size.x*1.5), 0));
        djangoAnimation.addPageAnimation(new SCPositionAnimation(this, 4, -(int)(size.x*1.5) ,0));
        mViewPager.addAnimation(djangoAnimation);*/
        //Text5
        View futureView = findViewById(R.id.txt_journey5);
        SCViewAnimation futureAnimation = new SCViewAnimation(futureView);
        futureAnimation.startToPosition((size.x *2), null);
        futureAnimation.addPageAnimation(new SCPositionAnimation(this, 3, -size.x*2, 0));
        futureAnimation.addPageAnimation(new SCPositionAnimation(this, 4, -size.x*2, 0));
        mViewPager.addAnimation(futureAnimation);


        View linkedinView = findViewById(R.id.imv_journey6);
        SCViewAnimation iosAnimation = new SCViewAnimation(linkedinView);
        iosAnimation.startToPosition(size.x, null);
        iosAnimation.addPageAnimation(new SCPositionAnimation(this, 4, -(int) size.x, 0));
        iosAnimation.addPageAnimation(new SCPositionAnimation(this, 5, -(int) size.x, 0));
        mViewPager.addAnimation(iosAnimation);

        View githubView = findViewById(R.id.txt_journey6);
        SCViewAnimation checkAnimation = new SCViewAnimation(githubView);
        checkAnimation.startToPosition(size.x*2, null);
        checkAnimation.addPageAnimation(new SCPositionAnimation(this, 4, -size.x*2, 0));
        checkAnimation.addPageAnimation(new SCPositionAnimation(this, 5, -size.x*2, 0));
        mViewPager.addAnimation(checkAnimation);
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
				 tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			 }
		 } else {
			 Log.e("error", "Initilization Failed!");
		 }
	 }
    
    public void createDataBase() {

		DatabaseHelper dbHelper = new DatabaseHelper(this);
		try {
			DatabaseHelper.closedatabase();
			dbHelper.createDataBase();
			//DatabaseHelper.openDataBase();
		} catch (Exception e) {
			Log.e("Database Error MSG", e + "");
		}
	}
    
	@Override
	public void onClick(View v)
	{
		switch (v.getId()){

        case R.id.tv_started:
        	Intent login=new Intent(Splash.this, KickStart.class);
        	startActivity(login);
        	//setupWindowAnimations();
        	finish();
        	break;
                
        }
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mPlayer!=null){
			mPlayer.stop();
		}
	}
	 private void setupWindowAnimations() {
		 	Slide slide = new Slide();
	        slide.setDuration(1000);
	        getWindow().setExitTransition(slide);
	    }

}