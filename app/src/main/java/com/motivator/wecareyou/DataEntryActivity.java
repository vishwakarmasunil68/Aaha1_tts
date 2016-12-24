package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.database.NewDataBaseHelper;
import com.motivator.model.DataEntryPOJO;
import com.motivator.support.FileUtils;
import com.motivator.support.StringUtils;

import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataEntryActivity extends Activity {
	FloatingActionButton fab;

	MediaPlayer mpPlayer;
	NewDataBaseHelper helper;
	MenuItem switchButton;
	Spinner glucose_spinner, spinner_medicine_type;
	EditText activity_et, glucose_et, food_et, food_quantity_et, medicine_et,
			medicine_quantity_et;
	ProgressDialog progressDialog;
	boolean isSoundOn = false;
	private final String TAG="myhealth";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_entry);
		fab = (FloatingActionButton) findViewById(R.id.fab);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
				.getColor(R.color.sky_blue)));
		actionBar.setTitle("");

		SpannableString s = new SpannableString("My Health");
		s.setSpan(new com.motivator.support.TypefaceSpan(this,
						"fonts/Montez-Regular.ttf"), 0, s.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		actionBar.setTitle(s);

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);

		InitializeViews();

		// newdbhelper=new NewDataBaseHelper(this);
		helper = new NewDataBaseHelper(this);
		List<DataEntryPOJO> timepojolst=new ArrayList<>();
		List<DataEntryPOJO> pojo=helper.getAllData();
//		for(DataEntryPOJO p:pojo){
		if(pojo!=null){
			for(int i=0;i<pojo.size();i++){
//				DataEntryPOJO p=pojo.get(pojo.size()-1);
				DataEntryPOJO dep=pojo.get(i);
				try{
					Log.d("sunil","time:-"+dep.getTime());
					Log.d("sunil","Date:-"+dep.getDate());
					int t=Integer.parseInt(dep.getTime().split(":")[0]);

					Calendar now=Calendar.getInstance();
					String current_time=now.get(Calendar.HOUR_OF_DAY)+":"+ now.get(Calendar.MINUTE)+":"+ now.get(Calendar.SECOND);
					int t1=Integer.parseInt(current_time.split(":")[0]);
					if((t1-1)==t||(t1+1)==t||t1==t){
//						Log.d("sunil","new time:-"+dep.toString());
						timepojolst.add(dep);
					}
				}
				catch(Exception e){
					Log.d("sunil", e.toString());
				}
			}
			if(timepojolst.size()>0){
				DataEntryPOJO p=timepojolst.get(timepojolst.size()-1);
				Log.d("sunil",p.toString());
				activity_et.setText(p.getActivity_minutes()+"");
				glucose_et.setText(p.getGlucose_no()+"");
				glucose_spinner.setSelection(getIndex(glucose_spinner, p.getGlucose_time()));
				food_et.setText(p.getFood_name()+"");
				food_quantity_et.setText(p.getFood_quantity()+"");
				medicine_et.setText(p.getMedicine_name()+"");
				medicine_quantity_et.setText(p.getMedicine_quantity()+"");
				spinner_medicine_type.setSelection(getIndex(spinner_medicine_type, p.getMedicine_type()));

			}
			else{
				activity_et.setHint("How many minutes did you walked for?");
			}
		}

//		}

		fab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DataEntryPOJO obj = new DataEntryPOJO();

				// TODO Auto-generated method stub

				if (activity_et.getText().toString().equals("")
						|| glucose_et.getText().toString().equals("")
						|| food_et.getText().toString().equals("")
						|| food_quantity_et.getText().toString().equals("")
						|| medicine_et.getText().toString().equals("")
						|| medicine_quantity_et.getText().toString().equals("")
						||glucose_spinner == null
						||glucose_spinner.getSelectedItem() == null
						||spinner_medicine_type == null
						||spinner_medicine_type.getSelectedItem() == null) {
					Toast.makeText(DataEntryActivity.this, "Please fill the details properly", Toast.LENGTH_LONG).show();
				}
				else{
					String activity_minutes = activity_et.getText()
							.toString();
					String glucose_number = glucose_et.getText().toString();
					String food = food_et.getText().toString();
					String food_quantity = food_quantity_et.getText()
							.toString();
					String medicine_name = medicine_et.getText().toString();
					String medicine_quantity = medicine_quantity_et
							.getText().toString();

					String glucose_spinner_text = glucose_spinner
							.getSelectedItem().toString();
					String medicine_type = spinner_medicine_type
							.getSelectedItem().toString();

					obj.setActivity_minutes(Integer
							.parseInt(activity_minutes));
					obj.setGlucose_no(Integer.parseInt(glucose_number));
					obj.setFood_name(food);
					obj.setFood_quantity(Integer.parseInt(food_quantity));
					obj.setGlucose_time(glucose_spinner_text);
					obj.setMedicine_name(medicine_name);
					obj.setMedicine_quantity(Integer
							.parseInt(medicine_quantity));
					obj.setMedicine_type(medicine_type);

					Calendar now = Calendar.getInstance();

					obj.setTime(now.get(Calendar.HOUR_OF_DAY)+":"+ now.get(Calendar.MINUTE)+":"+ now.get(Calendar.SECOND));
//						obj.setDate(now.get(Calendar.YEAR)+"-"+ now.get(Calendar.MONTH)+"-"+ now.get(Calendar.DAY_OF_MONTH));
					obj.setDate(getTodayDateString());
					String ritual="";

					Calendar cal = Calendar.getInstance();
					//
					int millisecond = cal.get(Calendar.MILLISECOND);
					int second = cal.get(Calendar.SECOND);
					int minute = cal.get(Calendar.MINUTE);
					//12 hour format
					int hour = cal.get(Calendar.HOUR);
					//24 hour format
					int hourofday = cal.get(Calendar.HOUR_OF_DAY);
					Log.d("msg",hourofday+":"+minute+":"+second);
					String currenttime=hourofday+":"+minute+":"+second;


					if(hourofday<12){
						ritual="morning_ritual";
					}
					else{
						if(hourofday>=12&&hourofday<17){
							ritual="afternoon_ritual";
						}
						else{
							if(hourofday>=17){
								ritual="evening_ritual";
							}
						}
					}
					obj.setRitual(ritual);

					long id = helper.insertData(obj);
					if (id < 0) {
						Toast.makeText(getApplicationContext(),
								"Entry Failed", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(),
								"Data entered Successfully", Toast.LENGTH_SHORT).show();
						getalldata();
					}
				}
				callService(obj);
			}
		});


//		mpPlayer = MediaPlayer.create(getApplicationContext(), R.raw.data_entry1);
//		if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND))
//			mpPlayer.start();
		ListFiles(new File(FileUtils.MYHEALTH_FILE_PATH));
	}

	public void ListFiles(File f){
		File[] files=f.listFiles();
		Log.d(TAG,"length:-"+files.length);
		int val= Pref.getInteger(getApplicationContext(), StringUtils.MYHEALTH,-1);
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
			Pref.setInteger(getApplicationContext(),StringUtils.MYHEALTH,val);
			Log.d(TAG,"pref mood:-"+Pref.getInteger(getApplicationContext(),StringUtils.MYHEALTH,-1));
		}
		catch (Exception e){
			Log.d("sunil",e.toString());
		}
	}

	public void callService(final DataEntryPOJO obj) {

		StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.funhabits.co/aaha/dataenterytable.php",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							Log.d("sunil", response);
							JSONObject jsonObject = new JSONObject(response);
							String success = jsonObject.optString("success");
							if (success.equals("true")) {
								JSONObject jsonObject1 = jsonObject.optJSONObject("result");

								Toast.makeText(getApplicationContext(), "Data Entered", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(getApplicationContext(), "Data Entry Fail", Toast.LENGTH_SHORT).show();
							}
							finish();
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("sunil", "" + error);
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("date", obj.getDate());
				params.put("time", obj.getTime());
				params.put("routine", obj.getRitual());
				params.put("activity", obj.getActivity_minutes()+"");
				params.put("blood_glucose", obj.getGlucose_no()+"");
				params.put("time_of_bg", obj.getGlucose_time());
				params.put("food", obj.getFood_name());
				params.put("food_quantity", obj.getFood_quantity()+"");
				params.put("medicine", obj.getMedicine_name());
				params.put("medicine_quantity", obj.getMedicine_quantity()+"");
				params.put("medicine_type", obj.getMedicine_type());

				return params;
			}

		};
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
		progressDialog = new ProgressDialog(DataEntryActivity.this);
		progressDialog.setMessage("Please wait...");
		progressDialog.show();
		progressDialog.setCancelable(false);
	}
	private String getTodayDateString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateformat=dateFormat.format(date).split(" ")[0];
		return dateformat;
	}

	private int getIndex(Spinner spinner, String myString)
	{
		int index = 0;

		for (int i=0;i<spinner.getCount();i++){
			if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
				index = i;
				break;
			}
		}
		return index;
	}

	public void InitializeViews() {

		glucose_spinner = (Spinner) findViewById(R.id.glucose_spinner);
		spinner_medicine_type = (Spinner) findViewById(R.id.spinner_medicine_type);

		activity_et = (EditText) findViewById(R.id.activity_et);
		glucose_et = (EditText) findViewById(R.id.glucose_et);
		food_et = (EditText) findViewById(R.id.food_et);
		food_quantity_et = (EditText) findViewById(R.id.food_quantity_et);
		medicine_et = (EditText) findViewById(R.id.medicine_et);
		medicine_quantity_et = (EditText) findViewById(R.id.medicine_quantity_et);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(mpPlayer!=null){
			if(mpPlayer.isPlaying()){
				mpPlayer.stop();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mpPlayer!=null){
			if(mpPlayer.isPlaying()){
				mpPlayer.stop();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			case R.id.action:
				isSoundOn = GeneralUtility.getPreferencesBoolean(DataEntryActivity.this, AppsConstant.AVS_SOUND);
				if(isSoundOn)
				{
					isSoundOn = false;
					switchButton.setIcon(R.drawable.voice_mute);
					GeneralUtility.setPreferencesBoolean(DataEntryActivity.this, AppsConstant.AVS_SOUND, false);
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
					GeneralUtility.setPreferencesBoolean(DataEntryActivity.this, AppsConstant.AVS_SOUND, true);
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

		isSoundOn = GeneralUtility.getPreferencesBoolean(DataEntryActivity.this, AppsConstant.AVS_SOUND);
		if (isSoundOn) {
			switchButton.setIcon(R.drawable.voice);
		} else {
			switchButton.setIcon(R.drawable.voice_mute);
		}
		return true;
	}

	public void getalldata() {
		List<DataEntryPOJO> lst = helper.getAllData();
		for (DataEntryPOJO obj : lst) {
			Log.d("sunil", "Entry:-" + obj.toString());
		}
	}
}
