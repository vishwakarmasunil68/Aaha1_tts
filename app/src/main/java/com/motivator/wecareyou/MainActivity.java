package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.OnMixpanelUpdatesReceivedListener;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.database.TableAttributes;
import com.motivator.wecareyou.fragment.HomeFragment;
import com.motivator.wecareyou.fragment.JourneyIntresting;
import com.motivator.wecareyou.fragment.TimeLineFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class MainActivity extends FragmentActivity implements OnClickListener {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    MenuItem switchButton;
    boolean isSoundOn = false;
    private static final int NUM_PAGES = 3;
    //private int currentPage = 0;
    boolean isFirstWalkTrough;

    Context mContext;
    private ViewPager mViewPager;
    FrameLayout frame1;
    private PagerAdapter mPagerAdapter;

    String userName;
    ActionBar actionBar;
    boolean onResume = false;
    GetData getData;

    TextToSpeech tts;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab3;

    private FloatingActionButton fab12;
    private FloatingActionButton fab22;
    private FloatingActionButton fab32;
    FloatingActionMenu ftLeftMenu, ftRightMenu;
    private List<FloatingActionMenu> floatingMenu = new ArrayList<FloatingActionMenu>();
    private Handler mUiHandler = new Handler();

    public static final String MIXPANEL_API_TOKEN = "201b01e5f71a66be074d3eb15c8659f3";
    public static final String ANDROID_PUSH_SENDER_ID = "AIzaSyBAIBNpG6QM2tXd6WPjsbwJA4e50nJGD6w";

    private MixpanelAPI mMixpanel;
    private static String MIXPANEL_DISTINCT_ID_NAME = "Mixpanel Example $distinctid";
    public static String times = "0";


    //variables for voice in homescreen
    public static boolean bol_flag = true;
    public static int play_times = 0;
    public static long current_time;


    public static int habit_time = 0;
    public static boolean show_data_entry = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = MainActivity.this;
        getData = new GetData(MainActivity.this);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        userName = GeneralUtility.getPreferences(MainActivity.this, AppsConstant.user_name);


        MIXPANEL_DISTINCT_ID_NAME = userName;
        final String trackingDistinctId = getTrackingDistinctId();
        // Initialize the Mixpanel library for tracking and push notifications.
        mMixpanel = MixpanelAPI.getInstance(this, MIXPANEL_API_TOKEN);
//        SharedPreferences sp=getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sp.edit();
//        editor.putString("times", "0");
//        editor.commit();

        // We also identify the current user with a distinct ID, and
        // register ourselves for push notifications from Mixpanel.

        mMixpanel.identify(trackingDistinctId); //this is the distinct_id value that
        // will be sent with events. If you choose not to set this,
        // the SDK will generate one for you

        mMixpanel.getPeople().identify(trackingDistinctId); //this is the distinct_id
        // that will be used for people analytics. You must set this explicitly in order
        // to dispatch people data.

        // People analytics must be identified separately from event analytics.
        // The data-sets are separate, and may have different unique keys (distinct_id).
        // We recommend using the same distinct_id value for a given user in both,
        // and identifying the user with that id as early as possible.

        mMixpanel.getPeople().initPushHandling(ANDROID_PUSH_SENDER_ID);

        setContentView(R.layout.main_activity);

        //Set ACTION BAR
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(getResources().getString(R.string.app_name));
        actionBar.setDisplayUseLogoEnabled(false);

        SpannableString s = new SpannableString(getResources().getString(R.string.app_name));
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(s);
        //actionBar.setTitle(Html.fromHtml("<font color='#ffffff' size = 12 face = 'Montez-Regular'>aaha </font>"));


        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // get Ui Views Refrences
        mViewPager = (ViewPager) findViewById(R.id.pager);
        frame1 = (FrameLayout) findViewById(R.id.frame1);


        //Check is user coming first time in the app (by cheking isRitualAdded in database)
        //SetVisibility of views 
        setUpVisibilityOfUIViews();
        try{
            FirstWalkThrough.mPlayer1.stop();
        }
        catch (Exception e){

        }



    }


    private void setUpVisibilityOfUIViews() {
        isFirstWalkTrough = false;
        mViewPager.setVisibility(View.VISIBLE);
        frame1.setVisibility(View.GONE);

        // Adding floating action buttons
        ftLeftMenu = (FloatingActionMenu) findViewById(R.id.ft_left_menu);

        ftRightMenu = (FloatingActionMenu) findViewById(R.id.ft_right_menu);

        ftLeftMenu.hideMenuButton(false);

        floatingMenu.add(ftLeftMenu);
        floatingMenu.add(ftRightMenu);

        ftLeftMenu.hideMenuButton(false);
        ftRightMenu.hideMenuButton(false);
        ftLeftMenu.setClosedOnTouchOutside(true);
        ftRightMenu.setClosedOnTouchOutside(true);

        // ftLeftMenu.setVisibility(View.VISIBLE);
        //ftRightMenu.setVisibility(View.VISIBLE);

        int delay = 400;
        for (final FloatingActionMenu menu : floatingMenu) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }


        fab1 = (FloatingActionButton) findViewById(R.id.fab_journey1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_journey2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab_journey3);

        fab12 = (FloatingActionButton) findViewById(R.id.fab1);
        fab22 = (FloatingActionButton) findViewById(R.id.fab2);
        fab32 = (FloatingActionButton) findViewById(R.id.fab3);

        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);

        fab12.setOnClickListener(this);
        fab22.setOnClickListener(this);
        fab32.setOnClickListener(this);


        // Instantiate a ViewPager and a PagerAdapter.
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between pages, select the corresponding tab.
                getActionBar().setSelectedNavigationItem(position);
//        		currentPage = position;
                if (position == 0) {
                    //ftLeftMenu.setVisibility(View.VISIBLE);
                    //ftRightMenu.setVisibility(View.VISIBLE);
                } else {
                    ftLeftMenu.setVisibility(View.GONE);
                    ftRightMenu.setVisibility(View.GONE);
                }

            }
        });
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    //ftLeftMenu.setVisibility(View.VISIBLE);
                    //ftRightMenu.setVisibility(View.VISIBLE);
                } else {
                    ftLeftMenu.setVisibility(View.GONE);
                    ftRightMenu.setVisibility(View.GONE);
                }
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };
        // Add 4 tabs, specifying the tab's text and TabListener
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.action_home_icon).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.action_journey_icon).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setIcon(R.drawable.action_icon).setTabListener(tabListener));
        // actionBar.addTab(actionBar.newTab().setIcon(R.drawable.action_chat_icon).setTabListener(tabListener));

    }

    Fragment home_frag,time_line_frag,journey_frag;
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Create a new fragment
            Fragment fragment;
            /** Swaps fragments in the main content view */
            switch (position) {
                case 0:
                    home_frag = new HomeFragment();
                    return home_frag;

                case 1:
                    time_line_frag = new TimeLineFragment();
                    return time_line_frag;

                case 2:
                    AppsConstant.SELECTED_JOURNEY = AppsConstant.Interesting_Journey;
                    journey_frag = new JourneyIntresting();
                    return journey_frag;

//			case 3:
//				fragment = new ChatFragment();
//				break;
                default:
                    home_frag = new HomeFragment();
                    return home_frag;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        switchButton = menu.findItem(R.id.action);

        isSoundOn = GeneralUtility.getPreferencesBoolean(MainActivity.this, AppsConstant.AVS_SOUND);
        if (isSoundOn) {
            switchButton.setIcon(R.drawable.voice);
        } else {
            switchButton.setIcon(R.drawable.voice_mute);
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        GetData gt=new GetData(this);
        gt.GetTableJourney_Rows(TableAttributes.TABLE_JOURNEY);

        Log.d("database","Reminder Description");
//        gt.GetTableJourney_Rows(TableAttributes.TABLE_HBIT_TIMELINE);
//        gt.GetTableJourney_Rows(TableAttributes.TABLE_HBIT_TIMELINE);
//        gt.GetTableJourney_Rows(TableAttributes.TABLE_JOURNEY);
//        gt.GetTableJourney_Rows(TableAttributes.TABLE_USER_RITUALS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action:
                isSoundOn = GeneralUtility.getPreferencesBoolean(MainActivity.this, AppsConstant.AVS_SOUND);
                if(isSoundOn)
                {
                    isSoundOn = false;
                    switchButton.setIcon(R.drawable.voice_mute);
                    GeneralUtility.setPreferencesBoolean(MainActivity.this, AppsConstant.AVS_SOUND, false);
                    try{
                        HomeFragment.mpPlayer.pause();
                    }
                    catch (Exception e){

                    }
                }
                else
                {
                    isSoundOn = true;
                    switchButton.setIcon(R.drawable.voice);
                    GeneralUtility.setPreferencesBoolean(MainActivity.this, AppsConstant.AVS_SOUND, true);
                    try{
                        HomeFragment.mpPlayer.start();
                    }
                    catch (Exception e){

                    }
                }
                break;
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, NewRitual.class);
                startActivityForResult(intent, 101);
//                startActivity(intent);
                return true;

            case R.id.action_profile:
//                Intent i = new Intent(MainActivity.this, Registration.class);
//                startActivity(i);
                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                return true;
//		case R.id.action_setting:
//			Toast.makeText(MainActivity.this, "Setting...", 5).show();
//			return true;

            case R.id.action_feedback:
                View rootView = findViewById(android.R.id.content).getRootView();
                rootView.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
                rootView.setDrawingCacheEnabled(false);

                Intent feedback = new Intent(MainActivity.this, Feedback.class);
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
    protected void onResume() {
        super.onResume();
        onResume = true;
        //Check is user coming first time in the app
        if (isFirstWalkTrough) {
            Fragment fragment = new HomeFragment();
            FragmentManager fragmentManager1 = getSupportFragmentManager();
            fragmentManager1.beginTransaction().replace(R.id.frame1, fragment).commit();
        }

        setAnalytics();

    }

    private void setAnalytics() {
        final long nowInHours = hoursSinceEpoch();
        final int hourOfTheDay = hourOfTheDay();

        // For our simple test app, we're interested tracking
        // when the user views our application.

        // It will be interesting to segment our data by the date that they
        // first viewed our app. We use a
        // superProperty (so the value will always be sent with the
        // remainder of our events) and register it with
        // registerSuperPropertiesOnce (so no matter how many times
        // the code below is run, the events will always be sent
        // with the value of the first ever call for this user.)
        // all the change we make below are LOCAL. No API requests are made.
        try {
            final JSONObject properties = new JSONObject();
            properties.put("first viewed on", nowInHours);
            //properties.put("user domain", "(unknown)"); // default value
            mMixpanel.registerSuperPropertiesOnce(properties);
        } catch (final JSONException e) {
            throw new RuntimeException("Could not encode hour first viewed as JSON");
        }

        // Now we send an event to Mixpanel. We want to send a new
        // "App Resumed" event every time we are resumed, and
        // we want to send a current value of "hour of the day" for every event.
        // As usual,all of the user's super properties will be appended onto this event.
        try {
            final JSONObject properties = new JSONObject();
            properties.put("hour of the day", hourOfTheDay);
            mMixpanel.track("App Resumed", properties);
        } catch (final JSONException e) {
            throw new RuntimeException("Could not encode hour of the day in JSON");
        }

        // If you have surveys or notifications, and you have set AutoShowMixpanelUpdates set to false,
        // the onResume function is a good place to call the functions to display surveys or
        // in app notifications. It is safe to call both these methods right after each other,
        // since they do nothing if a notification or survey is already showing.
        mMixpanel.getPeople().showNotificationIfAvailable(this);
        mMixpanel.getPeople().showSurveyIfAvailable(this);
        OnMixpanelUpdatesReceivedListener listener = new OnMixpanelUpdatesReceivedListener() {
            @Override
            public void onMixpanelUpdatesReceived() {
                mMixpanel.getPeople().joinExperimentIfAvailable();
            }
        };
        mMixpanel.getPeople().addOnMixpanelUpdatesReceivedListener(listener);

    }

    private String getTrackingDistinctId() {
        final SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        String ret = prefs.getString(MIXPANEL_DISTINCT_ID_NAME, null);
        if (ret == null) {
            ret = generateDistinctId();
            final SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putString(MIXPANEL_DISTINCT_ID_NAME, ret);
            prefsEditor.commit();
        }

        return ret;
    }

    // These disinct ids are here for the purposes of illustration.
    // In practice, there are great advantages to using distinct ids that
    // are easily associated with user identity, either from server-side
    // sources, or user logins. A common best practice is to maintain a field
    // in your users table to store mixpanel distinct_id, so it is easily
    // accesible for use in attributing cross platform or server side events.
    private String generateDistinctId() {
        final Random random = new Random();
        final byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        return Base64.encodeToString(randomBytes, Base64.NO_WRAP | Base64.NO_PADDING);
    }

    private int hourOfTheDay() {
        final Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    private long hoursSinceEpoch() {
        final Date now = new Date();
        final long nowMillis = now.getTime();
        return nowMillis / 1000 * 60 * 60;
    }

    public void GoToJourneyFeature(){
        mViewPager.setCurrentItem(2);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // To preserve battery life, the Mixpanel library will store
        // events rather than send them immediately. This means it
        // is important to call flush() to send any unsent events
        // before your application is taken out of memory.

        mMixpanel.flush();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.fab_journey1:
                Toast.makeText(MainActivity.this, AppsConstant.Interesting_Journey, Toast.LENGTH_SHORT).show();
//			intent = new Intent(HomeActivity.this, Chapter1.class);
//			startActivity(intent);
                break;

            case R.id.fab_journey2:
                Toast.makeText(MainActivity.this, AppsConstant.Enjoying_Health_Eating, Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab_journey3:
                Toast.makeText(MainActivity.this, AppsConstant.A_Pleasant_Night, Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab1:
                Toast.makeText(MainActivity.this, "fab1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab2:
                Toast.makeText(MainActivity.this, "fab2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab3:
                Toast.makeText(MainActivity.this, "fab3", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                if(result.equals("added")){
                    HomeFragment homeFragment= (HomeFragment) home_frag;
                    homeFragment.initializeRitualList();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    /*@Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	// TODO Auto-generated method stub
	    	super.onActivityResult(requestCode, resultCode, data);
	    	
	    	if(data != null){
				if (resultCode == NoteFragment.INTENT_LETTER)
				{
					String fragmentTag = data.getStringExtra("tag");
					boolean isCompleted = data.getBooleanExtra("done", false);
					if(isCompleted)
					{
						Toast.makeText(MainActivity.this, "Letter Read!", 5).show();
						//getFragmentManager().beginTransaction().remove(this).commit();
						//Fragment f = getSupportFragmentManager().findFragmentByTag("home");
						//android:switcher:2131165463:0
						//Fragment f = getSupportFragmentManager().findFragmentById(mViewPager.getCurrentItem());
						Fragment f = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + mViewPager.getCurrentItem());
						
						if(f!=null)
						{
							Fragment removingFrag = f.getChildFragmentManager().findFragmentByTag(fragmentTag);
							if(removingFrag!=null) 
								getSupportFragmentManager().beginTransaction().remove(removingFrag).commit();
								//fragmentTransac.remove(f);
						}
						
					}
					
				}
				
				if (resultCode == NoteFragment.INTENT_ACTION)
				{
					String fragmentTag = data.getStringExtra("tag");
					boolean isCompleted = data.getBooleanExtra("done", false);
					
					if(isCompleted)
					{
						Toast.makeText(MainActivity.this, "Action Done!", 5).show();
						String s = "android:switcher:" + R.id.pager + ":" + mViewPager.getCurrentItem();
						Fragment f = getSupportFragmentManager().findFragmentByTag(s);
						
						if(f!=null)
						{
							Fragment removingFrag = f.getChildFragmentManager().findFragmentByTag(fragmentTag);
							if(removingFrag!=null) 
								getSupportFragmentManager().beginTransaction().remove(removingFrag).commit();
						}
						
					}
					
				}
	    	}
	    	
	    	
	    	
	    }*/

}
