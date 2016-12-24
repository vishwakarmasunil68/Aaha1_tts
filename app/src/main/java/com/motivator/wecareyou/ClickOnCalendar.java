package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.database.NewDataBaseHelper;
import com.motivator.model.CalendarPOJO;
import com.motivator.model.DataEntryPOJO;
import com.motivator.support.FileUtils;
import com.motivator.support.StringUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClickOnCalendar extends Activity{
	
	NewDataBaseHelper helper;
//	List<String> data_types=new ArrayList<>();
	TextView routine_name,glucose_tv;
	LinearLayout ritual_layout;
	String ritual="";
	TextView activity_min_today,activity_min_yesterday,	activity_min_last_week,	glucose_today,glucose_yesterday
		,glucose_last_week,	food_today,food_yesterday,food_last_week,medicine_today,medicine_yesterday,medicine_last_week;
	String routine="";
	MenuItem switchButton;
	boolean isSoundOn = false;
	MediaPlayer mpPlayer;
	private final String TAG="cickoncalendar";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_on_click);
		ritual_layout=(LinearLayout) findViewById(R.id.ritual_layout);
		
		
		helper=new NewDataBaseHelper(this);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
				.getColor(R.color.sky_blue)));
		actionBar.setTitle("");

		SpannableString s = new SpannableString("Calendar");
		s.setSpan(new com.motivator.support.TypefaceSpan(this,
				"fonts/Montez-Regular.ttf"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		actionBar.setTitle(s);

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		
		routine_name=(TextView) findViewById(R.id.routine_name);
		activity_min_today=(TextView) findViewById(R.id.activity_min_today);
		activity_min_yesterday=(TextView) findViewById(R.id.activity_min_yesterday);
		activity_min_last_week=(TextView) findViewById(R.id.activity_min_last_week);
		glucose_today=(TextView) findViewById(R.id.glucose_today);
		glucose_yesterday=(TextView) findViewById(R.id.glucose_yesterday);
		glucose_last_week=(TextView) findViewById(R.id.glucose_last_week);
		food_today=(TextView) findViewById(R.id.food_today);
		food_yesterday=(TextView) findViewById(R.id.food_yesterday);
		food_last_week=(TextView) findViewById(R.id.food_last_week);
		medicine_today=(TextView) findViewById(R.id.medicine_today);
		medicine_yesterday=(TextView) findViewById(R.id.medicine_yesterday);
		medicine_last_week=(TextView) findViewById(R.id.medicine_last_week);
		glucose_tv=(TextView) findViewById(R.id.glucose_tv);
		
		
		Bundle extras=getIntent().getExtras();
		if(extras!=null){
			ritual=extras.getString(AppsConstant.SELECTED_RITUAL);
			Log.d("sunil","ritual:-"+ritual);
			switch (ritual) {
			case "morning_ritual":ritual_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.morning_routine));
					routine_name.setText("Morning Ritual");
				routine="morning_ritual";
				break;
			case "afternoon_ritual":ritual_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.afternoon_routine));
					routine_name.setText("Afternoon Ritual");
				routine="afternoon_ritual";
			break;

			case "evening_ritual":ritual_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.night_routine));
					routine_name.setText("Evening Ritual");
				routine="evening_ritual";
			break;

			default:
				ritual_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.morning_routine));
				routine="morning_ritual";
				break;
			}
		}
		
		List<DataEntryPOJO> pojo=helper.getAllData();
		
		List<DataEntryPOJO> lst_ritual=new ArrayList<>();
		for(DataEntryPOJO p:pojo){
			if(ritual.equals(p.getRitual())){
				lst_ritual.add(p);
			}
		}
		
		for(DataEntryPOJO dep:lst_ritual){
			Log.d("sunil","lst_string:-"+dep.toString());
		}
		
		CalendarPOJO c1=new CalendarPOJO();
		CalendarPOJO c2=new CalendarPOJO();
		CalendarPOJO c3=new CalendarPOJO();
		CalendarPOJO c4=new CalendarPOJO();
		
		c1.setData_type("Activity(min)");
		c2.setData_type("Blood Glucose(mg/dL)");
		c3.setData_type("Food");
		c4.setData_type("Medicine");
		
		String yesterday_date=getYesterdayDateString();
		String today_date=getTodayDateString();
		
		c1.setYesterday("");
		c2.setYesterday("");
		c3.setYesterday("");
		c4.setYesterday("");
		
		c1.setToday("");
		c2.setToday("");
		c3.setToday("");
		c4.setToday("");
		
		c1.setLast_week("");
		c2.setLast_week("");
		c3.setLast_week("");
		c4.setLast_week("");
		
		for(DataEntryPOJO dep:lst_ritual){
			if(dep.getDate().equals(yesterday_date)){				
				c1.setYesterday(dep.getActivity_minutes()+"");
				c2.setYesterday(dep.getGlucose_no()+"");
				c3.setYesterday(dep.getFood_name());
				c4.setYesterday(dep.getMedicine_name());
			}
			if(dep.getDate().equals(today_date)){				
				c1.setToday(dep.getActivity_minutes()+"");
				c2.setToday(dep.getGlucose_no()+"");
				c3.setToday(dep.getFood_name());
				c4.setToday(dep.getMedicine_name());
			}
		}
			Log.d("sunil",c1.toString());
			Log.d("sunil",c2.toString());
			Log.d("sunil",c3.toString());
			Log.d("sunil",c4.toString());
		
		if(pojo!=null&&pojo.size()>0){
//			DataEntryPOJO dep=pojo.get(pojo.size()-1);
			activity_min_today.setText(c1.getToday());
			activity_min_yesterday.setText(c1.getYesterday());
			activity_min_last_week.setText(c1.getLast_week());
			
			glucose_today.setText(c2.getToday());
			glucose_yesterday.setText(c2.getYesterday());
			glucose_last_week.setText(c2.getLast_week());
			
			food_today.setText(c3.getToday());
			food_yesterday.setText(c3.getYesterday());
			food_last_week.setText(c3.getLast_week());
			
			medicine_today.setText(c4.getToday());
			medicine_yesterday.setText(c4.getYesterday());
			medicine_last_week.setText(c4.getLast_week());
			
			
			
		}

		glucose_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ClickOnCalendar.this, ClickOnCalendarItem.class);
				i.putExtra(AppsConstant.SELECTED_RITUAL, routine);
				startActivity(i);
			}
		});

//		mpPlayer = MediaPlayer.create(getApplicationContext(), R.raw.calendar_1);
//		if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND))
//			mpPlayer.start();
		ListFiles(new File(FileUtils.CALENDAR_FILE_PATH));
		
	}
	public void ListFiles(File f){
		File[] files=f.listFiles();
		Log.d(TAG,"length:-"+files.length);
		int val= Pref.getInteger(getApplicationContext(), StringUtils.CALENDAR,-1);
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
			mpPlayer = new MediaPlayer();
			mpPlayer.setDataSource(soundFile.toString());
			mpPlayer.prepare();
			if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
				mpPlayer.start();
			}
			int MAX_VOLUME = 100;
			final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
			mpPlayer.setVolume(volume, volume);
			Pref.setInteger(getApplicationContext(),StringUtils.CALENDAR,val);
			Log.d(TAG,"pref mood:-"+Pref.getInteger(getApplicationContext(),StringUtils.CALENDAR,-1));
		}
		catch (Exception e){
			Log.d("sunil",e.toString());
		}
	}
	private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);    
        return dateFormat.format(cal.getTime()).split(" ")[0];
	}
	private String getTodayDateString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateformat=dateFormat.format(date).split(" ")[0];
        return dateformat;
	}
	
//	public void getalldata() {
//		List<DataEntryPOJO> lst = helper.getAllData();
//		for (DataEntryPOJO obj : lst) {
//			Log.d("sunil", "Entry:-" + obj.toString());
//		}
//	}


	@Override
	protected void onPause() {
		super.onPause();
		if(mpPlayer!=null){
			mpPlayer.stop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mpPlayer!=null){
			mpPlayer.stop();
		}
	}

	private class DataList extends BaseAdapter
	{		
		List<CalendarPOJO> items;
		public DataList(List<CalendarPOJO> items) {
			// TODO Auto-generated constructor stub			
			this.items=items;		
		}
		@Override
		public int getCount() 
		{
			return items.size();
		}
	
		@Override
		public Object getItem(int position)
		{
			return null;
		}
	
		@Override
		public long getItemId(int position) 
		{
			return position;
		}
	
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) 
		{
			LayoutInflater inflater = getLayoutInflater();
			convertView = inflater.inflate(R.layout.click_on_calendar_list, null);
			
			TextView data_type=(TextView) convertView.findViewById(R.id.data_type);
			TextView today_data=(TextView) convertView.findViewById(R.id.today_data);
			TextView yesterday_data=(TextView) convertView.findViewById(R.id.yesterday_data);
			TextView last_week_data=(TextView) convertView.findViewById(R.id.last_week_data);
			
			CalendarPOJO cp=items.get(position);
			
			data_type.setText(cp.getData_type());
			today_data.setText(cp.getToday());
			yesterday_data.setText(cp.getYesterday());
			last_week_data.setText(cp.getLast_week());
			return convertView;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			case R.id.action:
				isSoundOn = GeneralUtility.getPreferencesBoolean(ClickOnCalendar.this, AppsConstant.AVS_SOUND);
				if(isSoundOn)
				{
					isSoundOn = false;
					switchButton.setIcon(R.drawable.voice_mute);
					GeneralUtility.setPreferencesBoolean(ClickOnCalendar.this, AppsConstant.AVS_SOUND, false);
					try{
						mpPlayer.pause();
					}
					catch (Exception e){

					}
				}
				else
				{
					isSoundOn = true;
					switchButton.setIcon(R.drawable.voice);
					GeneralUtility.setPreferencesBoolean(ClickOnCalendar.this, AppsConstant.AVS_SOUND, true);
					try{
						mpPlayer.start();
					}
					catch (Exception e){

					}
				}
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_my_healt, menu);
		switchButton = menu.findItem(R.id.action);

		isSoundOn = GeneralUtility.getPreferencesBoolean(ClickOnCalendar.this, AppsConstant.AVS_SOUND);
		if (isSoundOn) {
			switchButton.setIcon(R.drawable.voice);
		} else {
			switchButton.setIcon(R.drawable.voice_mute);
		}
		return true;
	}

}
