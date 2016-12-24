package com.motivator.wecareyou;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.motivator.adapter.ViewPagerAdapter;


public class SplashScreen extends Activity implements View.OnClickListener{

//    Animation animTranslate,animFade;
   
	private Context mContext;
    int[] bgImage;
    ViewPager viewPager;
	PagerAdapter adapter;
	
    ViewPager imgNewsImg;
    TextView tvLetsStart;
    ImageView[] circleButtons = new ImageView[6];
    public int currentimageindex = -1;
   // boolean isGoingForaward = true;
  //  ArrayList<Integer> bannerNews;
   // Timer timer;
   // ImageLoader imageLoaderNormal;
  //  GraphicsUtil graphicUtil;
   // int delay = 1000;
   // int period = 5000;

	// private Handler mHandler;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
      	
        mContext=SplashScreen.this;

      //imageLoaderNormal = new ImageLoader(mContext);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        
        bgImage = new int[] { R.drawable.background, R.drawable.background, R.drawable.background, R.drawable.background,
        		R.drawable.background, R.drawable.background};
        
        //INITIALIZE UI VIEWS
        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.imgNewsImage);
        tvLetsStart=(TextView)findViewById(R.id.tv_started);

        circleButtons[0] = (ImageView) findViewById(R.id.img_circle_1);
		circleButtons[1] = (ImageView) findViewById(R.id.img_circle_2);
		circleButtons[2] = (ImageView) findViewById(R.id.img_circle_3);
		circleButtons[3] = (ImageView) findViewById(R.id.img_circle_4);
		circleButtons[4] = (ImageView) findViewById(R.id.img_circle_5);
		circleButtons[5] = (ImageView) findViewById(R.id.img_circle_6);
		
		tvLetsStart.setOnClickListener(this);

//       TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.titles);
//        titleIndicator.setViewPager(viewPager);
       		 
        /*final CirclePageIndicator circleIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
        final float density = getResources().getDisplayMetrics().density;
        circleIndicator.setFillColor(0xFFFFFFFF);
        circleIndicator.setStrokeColor(0xFFFFFFFF);
        circleIndicator.setStrokeWidth(1);
        circleIndicator.setRadius(8 * density);*/
                
		
		// Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(SplashScreen.this, bgImage);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);
                      
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
        	
        	@Override
        	public void onPageSelected(int arg0) {
        		int currentimageindex = arg0;
        		//  circleIndicator.setCurrentItem(currentimageindex);
        		Log.d("POSITION", "" + currentimageindex);
        	}
        	
        	@Override
        	public void onPageScrolled(int arg0, float arg1, int arg2) 
        	{
        		for (int i = 0; i < 6; i++) 
        		{
        			if (i == arg0)
						circleButtons[i].setImageResource(R.drawable.rounded_home_select);
        			else 
					 circleButtons[i].setImageResource(R.drawable.rounded_home_unselect);
				}
        	}
        	
        	@Override
        	public void onPageScrollStateChanged(int arg0) {
        		// TODO Auto-generated method stub
        		
        	}
        });
        		
        		
        		
        //bannerNews = new ArrayList<Integer>();
       // graphicUtil = new GraphicsUtil();
       // imgNewsImg = (ViewPager) findViewById(R.id.imgNewsImage);
        		
       
        
        
    }
    
    
    
//    private void checkUpdate()
//    {
//    	mHandler=new Handler();
//    	checkUpdate.start();
//    }
//    
//    NotificationManager notificationManager;
//    
//	private Thread checkUpdate = new Thread() {
//		public void run() {
//			try {
//				Log.d("STRING FROM SITE", "balh blah");
//				URL updateURL = new URL(
//						"http://oldmaker.com/glamberry/api/version.txt");
//				URLConnection conn = updateURL.openConnection();
//				InputStream is = conn.getInputStream();
//				BufferedInputStream bis = new BufferedInputStream(is);
//				ByteArrayBuffer baf = new ByteArrayBuffer(1000);
//
//				int current = 0;
//				while ((current = bis.read()) != -1) {
//					baf.append((byte) current);
//				}
//
//				/* Convert the Bytes read to a String. */
//				final String s = new String(baf.toByteArray());
//
//				int newVersionCode = 1, pkgIndex;
//				pkgIndex = s.indexOf("com.emobi.glamberry");
//
//				if ("\"".equals(Character.toString(s.charAt(pkgIndex - 3)))) {
//					newVersionCode = Integer.parseInt(Character.toString(s
//							.charAt(pkgIndex - 2)));
//				} else if ("\"".equals(Character.toString(s
//						.charAt(pkgIndex - 4)))) {
//					newVersionCode = Integer.parseInt(s.substring(pkgIndex - 3,
//							pkgIndex - 1));
//				}
//				Log.d("newversion!!!!!!!!", Integer.toString(newVersionCode));
//
//				Log.d("STRING FROM SITE",
//						Integer.toString(s.indexOf("com.emobi.glamberry")));
//				/* Get current Version Number */
//				int curVersion = getPackageManager().getPackageInfo(
//						"com.emobi.glamberry", 0).versionCode;
//				// int newVersion = Integer.valueOf(s);
//
//				Log.d("CURRENT VERSION!!!", Integer.toString(curVersion));
//
//				if (newVersionCode > curVersion)
//					mHandler.post(showUpdate);
//			
//				/* Is a higher version than the current already out? */
//				// if (newVersion > curVersion) {
//				// /* Post a Handler for the UI to pick up and open the Dialog
//				// */
//				// mHandler.post(showUpdate);
//				// }
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	};
//    
//	
//	private Runnable showUpdate = new Runnable() {
//		public void run() {
//			createNotification();
//			new AlertDialog.Builder(SplashScreen.this)
//					// .setIcon(R.drawable.icon)
//					.setTitle("Update Available")
//					.setMessage(
//							"An update for "
//									+ getResources().getString(
//											R.string.app_name)
//									+ " is available!\nOpen Google Play now?")
//					.setPositiveButton("Yes",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int whichButton) {
//									/* User clicked OK so do some stuff */
//									Intent intent = new Intent(
//											Intent.ACTION_VIEW,
//											Uri.parse("https://play.google.com/store/apps/details?id=com.emobi.glamberry"));
//									startActivity(intent);
//									if (notificationManager != null)
//										notificationManager.cancelAll();
//								}
//							})
//					.setNegativeButton("No",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int whichButton) {
//									/* User clicked Cancel */
//
//								}
//							}).show();
//		}
//	};
//	
//	
//	@SuppressWarnings("deprecation")
//	public void createNotification() {
//		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//		Notification notification = new Notification(R.drawable.ic_launcher,
//				"Update available", System.currentTimeMillis());
//		// Hide the notification after its selected
//		notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//		Intent intent = new Intent(Intent.ACTION_VIEW,
//				Uri.parse("https://play.google.com/store/apps/details?id=com.emobi.glamberry"));
//		// Intent intent = new Intent(this, NotificationReceiver.class);
//		PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
//		notification.setLatestEventInfo(this,
//				getResources().getString(R.string.app_name)
//						+ " Update Available!",
//				"Tap here to open Google Play & update", activity);
//		notification.number += 1;
//		notificationManager.notify(0, notification);
//
//	}
//	
	
	
    @Override
    public void onClick(View v) {
        switch (v.getId()){

        case R.id.tv_started:
        	
        	Intent login=new Intent(SplashScreen.this, KickStart.class);
        	startActivity(login);
        	finish();
        	//overridePendingTransition(R.anim.right_in, R.anim.left_out);
        	break;
                
        }
    }
    
    @Override
   	protected void onResume() {
   		super.onResume();
   		//new CallService().execute(url);
   	}
    
    @Override
    public void onBackPressed() {
     
    	Intent intent = new Intent(Intent.ACTION_MAIN);
       
    	  intent.addCategory(Intent.CATEGORY_HOME);
          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(intent);
    	
    	
    }
     
    
}
