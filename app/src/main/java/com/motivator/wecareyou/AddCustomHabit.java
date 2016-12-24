package com.motivator.wecareyou;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.github.clans.fab.FloatingActionButton;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.common.WebServices;
import com.motivator.database.PutData;
import com.motivator.model.HabitImgModel;
import com.motivator.support.CustomVolleyRequestQueue;
import com.motivator.support.NonScrollableGridView;

public class AddCustomHabit extends Activity implements OnClickListener {
	
	String userName;
	String selectedRitual;
	
	EditText  edtHabit;
	TextView tvHabitTime, tvProgress;
	ProgressBar progress;
	NonScrollableGridView gvHabitIcon, gvHabitImg;
	TextView tvPickHabitImg;
//	Button ok_btn;
	String habitTime = "5";
	public static int RESULT_HABIT_TIME = 10;
	ArrayList<HabitImgModel> arrImgUrl;
	String fileAbsolutePath = "";
	String colorCode = "#F0F8FF";
	private String[] cell_num = {"#F0F8FF","#FAEBD7","#FA8072","#FFA500","#FFE4C4","#BDB76B",
			"#FF1744","#FF8A80","#FF5252","#FF69B4","#E21C52", "#4B0082",
			"#4da6ff","#4682B4","#00cccc","#1c7269","#65c6bb", "#8A2BE2",
			"#6BBC8B","#f2f2f2","#CCCCCC","#455A64","#999999","#CFCCDC",
			"#000000","#FFFFFF"};
	View view;
	
	FloatingActionButton fab;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
      //getDAta from shared prefrence
        setContentView(R.layout.add_habit);
        
        SpannableString s = new SpannableString("Create New Habit");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setTitle("create new habit");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false); 
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(s);
        
        colorCode = cell_num[0];
        //SetUPUIVIEWS
  		intializeUIViews();
        userName = GeneralUtility.getPreferences(AddCustomHabit.this, AppsConstant.user_name);
        
        //Get DAta from the database
        String habit = getIntent().getExtras().getString("habit");
        edtHabit.setText(habit);
        //call api to get images
        callImgApi(habit);
        
        edtHabit.setSelection(edtHabit.getText().length());
        
        edtHabit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) 
			{
				callImgApi(s.toString());				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) 			{
				// TODO Auto-generated method stub				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
        
        
	}

	private void callImgApi(String habit)
	{
		if(GeneralUtility.isNetworkAvailable(AddCustomHabit.this))
        	new GetHabitImg().execute(habit);
        else
        	Toast.makeText(AddCustomHabit.this, R.string.no_internet, 5).show();
	}
	private void intializeUIViews() 
	{
		TextView tvTitleSetting = (TextView)findViewById(R.id.tv_title_setting);
		TextView tvTitleHabit = (TextView)findViewById(R.id.tv_title_habit);
		TextView tvHabitIcon = (TextView)findViewById(R.id.tv_habit_icon);
        edtHabit= (EditText)findViewById(R.id.edt_habit_name);
        //edtHabitDesc = (EditText)findViewById(R.id.edt_habit_Desc);
        tvHabitTime = (TextView)findViewById(R.id.tv_habit_duration);
        gvHabitIcon = (NonScrollableGridView)findViewById(R.id.grid_icon);
        tvPickHabitImg = (TextView)findViewById(R.id.tv_pick_img);
        
        progress = (ProgressBar)findViewById(R.id.progress);
        tvProgress = (TextView)findViewById(R.id.tv_progress_info);
        
        gvHabitImg = (NonScrollableGridView)findViewById(R.id.grid_img);
		
        fab=(FloatingActionButton) findViewById(R.id.fab);
        
        view=findViewById(R.id.view);
        
        
        tvTitleSetting.setTypeface(GeneralUtility.setTypeFace(AddCustomHabit.this));
        tvTitleHabit.setTypeface(GeneralUtility.setTypeFace(AddCustomHabit.this));		
        edtHabit.setTypeface(GeneralUtility.setTypeFace(AddCustomHabit.this));
        tvHabitTime.setTypeface(GeneralUtility.setTypeFace(AddCustomHabit.this));        
        tvHabitIcon.setTypeface(GeneralUtility.setTypeFace(AddCustomHabit.this));		
        tvPickHabitImg.setTypeface(GeneralUtility.setTypeFace(AddCustomHabit.this));		
        
        tvHabitTime.setOnClickListener(this);
        tvPickHabitImg.setOnClickListener(this);
        gvHabitIcon.setAdapter(new GridCustomAdapter());
        fab.setOnClickListener(this);
	}

	public class GridCustomAdapter extends BaseAdapter 
	{
		int selectedIcon = 0;	
		public int getCount() {
			return cell_num.length;
		}

		public Object getItem(int position) {
			return null;
		}
		
		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) 
		{
			ImageView imvIcon;
			if (convertView == null) 
			{
				imvIcon = new ImageView(AddCustomHabit.this);
				imvIcon.setLayoutParams(new GridView.LayoutParams(70, 70));
			} else {
				imvIcon = (ImageView) convertView;
			}

//			Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.app_icon);		
//			imvIcon.setImageBitmap(GeneralUtility.getCircleBitmap(bm));
//			GradientDrawable bgShape = (GradientDrawable)btn.getBackground();
//			bgShape.setColor(Color.BLACK);
			imvIcon.setBackgroundResource(R.drawable.habit_custom_icon);
			
			GradientDrawable bgShape = (GradientDrawable)imvIcon.getBackground();
			bgShape.setColor(Color.parseColor(cell_num[position]));
			
			
			if(position == selectedIcon){
				imvIcon.setImageResource(R.drawable.right_green);
		    } else {
		    	imvIcon.setImageResource(0);
		    }
			
			imvIcon.setTag(position);
			imvIcon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) 
				{
					int click = (Integer) v.getTag();
					selectedIcon = click;
					colorCode = cell_num[click];
					notifyDataSetChanged();
				}
			});
			return imvIcon;
		}
		
	}
	
	class GetHabitImg extends AsyncTask<String, Void, String>
	{
		String jResult;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress.setVisibility(View.VISIBLE);
			tvProgress.setVisibility(View.VISIBLE);
			tvProgress.setText("Searching the related image...");
			tvPickHabitImg.setVisibility(View.VISIBLE);
			progress.setVisibility(View.VISIBLE);
			tvProgress.setVisibility(View.VISIBLE);
			gvHabitImg.setVisibility(View.VISIBLE);
		}
		@Override
		protected String doInBackground(String... params) {
			try {
				jResult = WebServices.executeHttpGetWithHeader(WebServices.Custom_habit_img_full,
						WebServices.gettyImageApiKey,params[0]/*, nameValuePairs*/);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jResult;
		}
		
		@Override
		protected void onPostExecute(String jsonResponse) 
		{
			super.onPostExecute(jsonResponse);
			if(!jsonResponse.toLowerCase().toString().equals("")){						
				
			if(jsonResponse.toLowerCase().toString().contains("service")){
				tvPickHabitImg.setVisibility(View.GONE);
				progress.setVisibility(View.GONE);
				tvProgress.setVisibility(View.GONE);
				gvHabitImg.setVisibility(View.GONE);
				view.setVisibility(View.GONE);
			}
			else{
				tvPickHabitImg.setVisibility(View.VISIBLE);
				progress.setVisibility(View.VISIBLE);
				tvProgress.setVisibility(View.VISIBLE);
				gvHabitImg.setVisibility(View.VISIBLE);
				view.setVisibility(View.VISIBLE);
			Log.d("sunil","JSONRESPONSE:-"+jsonResponse);
			progress.setVisibility(View.GONE);
			arrImgUrl = new ArrayList<HabitImgModel>();
			if(jsonResponse!=null && jsonResponse.length()>0)
			{
				arrImgUrl = WebServices.parseHabitImg(jsonResponse);
				if(arrImgUrl!=null && arrImgUrl.size()>0)
				{
					tvProgress.setVisibility(View.GONE);
					gvHabitImg.setAdapter(new GridImgCustomAdapter());
				}
				else
				{
					tvProgress.setVisibility(View.VISIBLE);
					tvProgress.setText("No related Images found, please try with different name.");
				}
			}
			else
			{
				tvProgress.setVisibility(View.VISIBLE);
				tvProgress.setText("Service down, please try after some time!");
			}

		}
		}
			else{
				tvPickHabitImg.setVisibility(View.GONE);
				progress.setVisibility(View.GONE);
				tvProgress.setVisibility(View.GONE);
				gvHabitImg.setVisibility(View.GONE);
				view.setVisibility(View.GONE);
			}
			}
		   
	}
	public class GridImgCustomAdapter extends BaseAdapter 
	{
		int selectedImg = -1;
		public int getCount() {
			return arrImgUrl.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) 
		{
			NetworkImageView imvIcon;
			if (convertView == null) 
			{
				imvIcon = new NetworkImageView(AddCustomHabit.this);
				imvIcon.setLayoutParams(new GridView.LayoutParams(200, 300));
			} else {
				imvIcon = (NetworkImageView) convertView;
			}

			ImageLoader mImageLoader = CustomVolleyRequestQueue.getInstance(AddCustomHabit.this.getApplicationContext()).getImageLoader();
			mImageLoader.get(arrImgUrl.get(position).getUrl(), ImageLoader.getImageListener(imvIcon,
	                R.drawable.app_icon, android.R.drawable.ic_dialog_alert));
	        
			imvIcon.setImageUrl(arrImgUrl.get(position).getUrl(), mImageLoader);
			if(position == selectedImg)
			{
				imvIcon.setBackgroundColor(getResources().getColor(R.color.gray_light));
			}
			else
			{
				imvIcon.setBackgroundColor(getResources().getColor(R.color.white));
			}
			imvIcon.setTag(position);
			imvIcon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) 
				{
					int click = (Integer) v.getTag();
					selectedImg = click;
					new DownloadFileFromURL1().execute(arrImgUrl.get(click).getName(), arrImgUrl.get(click).getUrl());
					notifyDataSetChanged();
				}

				
			});
			return imvIcon;
		}
		
	}
	
	
	class DownloadFileFromURL1 extends AsyncTask<String, String, String> 
	{
	    String result = "", imgName="", imgUrl = "";
	    ProgressDialog piDialog;
	    final String APP_FOLDER_PATH ="/sdcard/Aaha/";
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        piDialog = new ProgressDialog(AddCustomHabit.this);
	        piDialog.setMessage("Please wait..."+"\n"+"Image is downloading");
	        piDialog.setIndeterminate(false);
	        piDialog.setCancelable(true);
	        piDialog.show();
	    }

	    /**
	     * Downloading file in background thread
	     * */
	    @Override
	    protected String doInBackground(String... params) 
	    {
	    	imgName = params[0];
	    	imgUrl = params[1];
	        try {
	            URL url = new URL(imgUrl);
	            URLConnection conection = url.openConnection();
	            conection.connect();

	            // this will be useful so that you can show a tipical 0-100%
	            // progress bar
	            int lenghtOfFile = conection.getContentLength();

	            // download the file
	            InputStream input = new BufferedInputStream(url.openStream(),8192);

	            File APP_FOLDER = new File(APP_FOLDER_PATH);
	            if (!APP_FOLDER.exists())
	            {
	                APP_FOLDER.mkdirs();
	            }
//	            Long tsLong = System.currentTimeMillis()/1000;
//	            String ts = tsLong.toString();
	            //String file_path = Environment.getExternalStorageDirectory().toString()+ "/Jesus/downloadedfile"+ts;
	            String file_path = "habitImg_"+imgName;

	            File finalHabitImg =new File(APP_FOLDER , file_path);
	            if(!finalHabitImg.exists())
	            {
	                FileOutputStream output = new FileOutputStream(finalHabitImg);
	                byte data[] = new byte[1024];
	                long total = 0;
	                int count = 0;
	                while ((count = input.read(data)) != -1) {
	                    total += count;
	                    // publishing the progress....
	                    // After this onProgressUpdate will be called
	                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
	                    // writing data to file
	                    output.write(data, 0, count);
	                }

	                // flushing output
	                output.flush();
	                // closing streams
	                output.close();
	                input.close();
	            }
	            fileAbsolutePath = finalHabitImg.getAbsolutePath();

	        } catch (Exception e) {
	            Log.e("Error: ", e.getMessage());
	        }

	        return null;
	    }

	    /**
	     * Updating progress bar
	     * */
	    protected void onProgressUpdate(String... progress) {
	        // setting progress percentage
	        //pDialog.setProgress(Integer.parseInt(progress[0]));
	    }

	    /**
	     * After completing background task Dismiss the progress dialog
	     * **/
	    @Override
	    protected void onPostExecute(String file_url) {
	        // dismiss the dialog after the file was downloaded
	        piDialog.dismiss();
	        Toast.makeText(AddCustomHabit.this,"image downloaded", 5).show();

	    }
	}
	
	void addHabit()
	{
		String habit = edtHabit.getText().toString();
		//String habitDesc = edtHabitDesc.getText().toString();
		if(habit==null || habit.trim().length()==0)
			edtHabit.setError("Please enter habit name");
		else
		{
			PutData putData = new PutData(AddCustomHabit.this);
			long row = putData.addCustomHabit(habit, habitTime, fileAbsolutePath, colorCode);
			if(row>0)
			{
				Intent i = new Intent();
				i.putExtra("new_added", true);
				setResult(HabitList.RESULT_CUSTOM_HABIT, i);
				finish();
				Toast.makeText(AddCustomHabit.this, "habit added successfully!", 5).show();
			}
			else
				Toast.makeText(AddCustomHabit.this, "There is some problem in adding habit", 5).show();
		}
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.top_menu, menu);
		MenuItem switchButton = menu.findItem(R.id.action);
		switchButton.setIcon(R.drawable.right_green);
		switchButton.setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
		case android.R.id.home:
			onBackPressed();
			return true;
			 
		case R.id.action:
			addHabit();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
		case R.id.tv_habit_duration:
			String habit = edtHabit.getText().toString().trim();
			Intent i = new Intent(AddCustomHabit.this, SetHabitTime.class);
			i.putExtra("habit", habit);
			i.putExtra("is_new_habit", true);
			startActivityForResult(i, RESULT_HABIT_TIME);
			break;
			
		case R.id.tv_pick_img:
			break;
		case R.id.fab:
			addHabit();
			break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data!=null)
		{
			if(resultCode == RESULT_HABIT_TIME)
			{
				habitTime = data.getStringExtra("time_duration");
				tvHabitTime.setText("Habit Duration\n\n"+habitTime+" minutes");
			}
		}
	}
	
	
}

