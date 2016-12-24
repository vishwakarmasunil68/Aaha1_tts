package com.motivator.common;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.motivator.alarm.AlarmReceiver;

import java.io.InputStream;
import java.util.Calendar;

public class GeneralUtility {
	
	public static Context mContext;
		
	public static Typeface setTypeFace(Context mContext) 
	{
		Typeface tfPhilospher = Typeface.createFromAsset(mContext.getAssets(), "fonts/Philosopher-Regular.ttf");
		return tfPhilospher;
	}
	
	public static void setPreferences(Context context, String key, String value) {
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences(
				"WED_APP", Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreferences(Context context, String key) {
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("WED_APP",
				Context.MODE_PRIVATE);
		String text = prefs.getString(key, "");
		return text;
	}
	
	public static void setPreferencesBoolean(Context context, String key, boolean value) {
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences(
				"APP_Bolean", Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getPreferencesBoolean(Context context, String key) {
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("APP_Bolean",
				Context.MODE_PRIVATE);
		boolean isOn = prefs.getBoolean(key, false);
		return isOn;
	}
	
	public static void setPreferencesInt(Context context, String key, int value) {
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences(
				"APP_INT", Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static int getPreferencesInt(Context context, String key) {
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("APP_INT",
				Context.MODE_PRIVATE);
		int text = prefs.getInt(key, 0);
		return text;
	}
	
	/**
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getCircleBitmap(Bitmap bitmap) {
		 final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
		  bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		 final Canvas canvas = new Canvas(output);

		 final int color = Color.RED;
		 final Paint paint = new Paint();
		 final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		 final RectF rectF = new RectF(rect);

		 paint.setAntiAlias(true);
		 canvas.drawARGB(0, 0, 0, 0);
		 paint.setColor(color);
		 canvas.drawOval(rectF, paint);

		 paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		 canvas.drawBitmap(bitmap, rect, rect, paint);

		 bitmap.recycle();

		 return output;
		}
	
	/**
	 * Check if network is available
	 */
	public static boolean isNetworkAvailable(Context ctx)
	{
		ConnectivityManager cm =
		        (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo i = cm.getActiveNetworkInfo();
		    if (i == null)
		      return false;
		    if (!i.isConnected())
		      return false;
		    if (!i.isAvailable())
		      return false;
		    return true;

	}
	
	/**
	 * Check is email id valid
	 */
	public static boolean isValidEmail1(String target) {
		String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		if(target.matches(emailPattern))
			return true;
		else
			return false;
	}
	
	/*public static boolean isValidEmail1(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}*/
	
	public static String addZero(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}


	/**
	 * show an alert dialog
	 */
	public static void PopUpInfo(Context _context, int title, int msg) {
			
		AlertDialog.Builder alrt= new AlertDialog.Builder(_context);
		alrt.setMessage(msg);
		alrt.setCancelable(false);
		
		alrt.setTitle(title);
		alrt.setNeutralButton("OK", new  DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});
		alrt.show();
	}

	
	/**
	 * for hidding keyboard in activity
	 */
	public static void hideKeyboard(Activity activity) {
		try {
			InputMethodManager inputManager = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			// check if no view has focus:
			View view = activity.getCurrentFocus();
			if (view != null) {
				inputManager.hideSoftInputFromWindow(view.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
			// Ignore exceptions if any
			Log.e("KeyBoardUtil", e.toString(), e);
		}
	}

	/**
	 * Downloads image from the url
	 */
	public static Bitmap getImage(String url)
	{
		try {
			// Download Image from URL
			InputStream input;
			input = new java.net.URL(url).openStream();
			// Decode Bitmap
			return BitmapFactory.decodeStream(input);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	 public static void setAlarmTime(Context context, String userName, String selectedRitual,int dayOfWeek, int hourOfDay, int minute, int rStamp, boolean isUpdate) 
	 {
		 Calendar now = Calendar.getInstance();
		 Calendar cal = Calendar.getInstance();
		 cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		 cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		 cal.set(Calendar.MINUTE, minute);
		 cal.set(Calendar.SECOND, 0);
		 cal.set(Calendar.MILLISECOND, 0);
        
		 long alarmTime = cal.getTimeInMillis();
//        long interval = 1000 * 60 * 20;/*24 * 60 * 60 * 1000*/
//    	 interval  = 16060 * 1000;
    	// interval = 1 * 60 * 60 * 1000;

        Intent intent= new Intent(context, AlarmReceiver.class);
        intent.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
        intent.putExtra(AppsConstant.user_name, userName);
        intent.putExtra("alarmTime", "day"+dayOfWeek+" hour"+hourOfDay+":"+minute);
        PendingIntent alarmIntent;
        if(!isUpdate)
        	alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), rStamp, intent, 0/*PendingIntent.FLAG_UPDATE_CURRENT*/);
        else
        	alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), rStamp, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        
      //check whether the time is earlier than current time. 
        if(cal.before(now))
        {
        	alarmTime = cal.getTimeInMillis()+(alarmManager.INTERVAL_DAY * 7);
        	// Toast.makeText(this, "Alarm Set for day"+dayOfWeek+"in next week :  at"+alarmTime+" MilliSeconds", Toast.LENGTH_LONG).show();
        }
//        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime,AlarmManager.INTERVAL_DAY * 7, alarmIntent);
      //  alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmTime, AlarmManager.INTERVAL_DAY * 7, alarmIntent);
       // Toast.makeText(this, "Alarm Set for day"+dayOfWeek+" at"+alarmTime+" MilliSeconds", Toast.LENGTH_LONG).show();
        
	 }
	
	 
	 public static void removeAlarmForADay(Context mContext, int remStamp) 
	 {
		 Intent intent = new Intent(mContext, AlarmReceiver.class);
		 PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, remStamp, intent, 0);
		 AlarmManager alarmManager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
		 alarmManager.cancel(pendingIntent);
	 }
	 
}
