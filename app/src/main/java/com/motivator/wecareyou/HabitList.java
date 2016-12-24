package com.motivator.wecareyou;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.GeneralUtility;
import com.motivator.common.AppsConstant;
import com.motivator.common.Pref;
import com.motivator.common.WebServices;
import com.motivator.database.DeleteData;
import com.motivator.database.GetData;
import com.motivator.database.PrefData;
import com.motivator.database.PutData;
import com.motivator.database.TableAttributes;
import com.motivator.model.HabitModel;
import com.motivator.support.FileUtils;
import com.motivator.support.StringUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import static com.google.android.gms.analytics.internal.zzy.v;

public class HabitList extends Activity {

    Context mContext;
    public static int ADD_HABIT = 10;
    public static int RESULT_CUSTOM_HABIT = 11;
    //int selected;
    String ritualTime;
    ListView lvHabitList;
    EditText edtSearch;
    LinearLayout llAddCustomHabit;
    TextView tvCustomHabit, tvAddCustomHabit;
    TextToSpeech tts;
    String selectedRitual;
    boolean isRitualAdded = false;
    RelativeLayout rllInfo;
    ImageView imvClick;
    TextView tvInfo;
    int i = 0;
    String userName;
    GetData getData;
    PutData putData;
    DeleteData deleteData;
    HabitListAdapter habitListAdapter;
    ArrayList<HabitModel> allHabitList = new ArrayList<HabitModel>();
    String habitlisttts = "";
    MediaPlayer mPlayer, mPlayer1;
    private final String TAG = "habitlist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = HabitList.this;

        getData = new GetData(HabitList.this);
        putData = new PutData(HabitList.this);
        deleteData = new DeleteData(HabitList.this);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.habit_list);

        SpannableString s = new SpannableString("add habit");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sky_blue)/*Color.parseColor("#330000ff")*/));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("Add Habit");
        actionBar.setTitle(s);

        //intializing UI Views
        initializeUIViews();
        userName = GeneralUtility.getPreferences(HabitList.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        ritualTime = getIntent().getExtras().getString(AppsConstant.RITUAL_TIME);

        SharedPreferences sp = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        habitlisttts = sp.getString("habitlisttts", "");


        if (habitlisttts.equals("")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("habitlisttts", "done");
            editor.commit();
//        	tts = new TextToSpeech(getApplicationContext(), new OnInitListener() {
//
//    			@Override
//    			public void onInit(int status) {
//    				// TODO Auto-generated method stub
//    				convertTextToSpeech(status, "In this room, you will add and subtract the tasks you wish to do each day. Eventually, I'll be popping in to keep you on these things through the whole day.");
//    			}
//
//    		});
            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if (MyApplication.tts_initialized) {
                    MyApplication.tts.speak("next we will go over your habits. In this room you will add and subtract the task you wish to do each day. we have vaious things to do through out the day some will be set for the morning, some for the afternoon and some for the evening here how it works", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        } else {
            SharedPreferences sp1 = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
            boolean first_msg1 = sp1.getBoolean("add_habit_first", true);
            if (first_msg1) {
                SharedPreferences.Editor edit = sp1.edit();
                edit.putBoolean("add_habit_first", false);
                edit.commit();
                String add_habit_list = "You can tap each habit to see why it helps with a better health and happiness. Tap the add button to add it to your routine. If you want to add your own habit just type it in the search line below. You can even choose an image for it. Tap add new habit.";
                if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                    if (MyApplication.tts_initialized) {
                        MyApplication.tts.speak(add_habit_list, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            } else {
            }

        }
        //Check is user coming first time in the app (by cheking isRitualAdded in database)
        //isRitualAdded = getData.isValueAdded(userName, TableAttributes.IS_RITUAL_ADDED);
        isRitualAdded = GeneralUtility.getPreferencesBoolean(HabitList.this, AppsConstant.IS_RITUAL_ADDED);
        //SetVisibility of views
        if (isRitualAdded) {
            rllInfo.setVisibility(View.GONE);
            imvClick.setVisibility(View.GONE);
            tvInfo.setVisibility(View.GONE);
            //Get DAta from the database
            allHabitList = getData.getAllHabitsUnionUser(userName, selectedRitual);
            habitListAdapter = new HabitListAdapter();
            lvHabitList.setAdapter(habitListAdapter);
        } else {
            rllInfo.setVisibility(View.VISIBLE);
            imvClick.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.VISIBLE);
            rllInfo.setAlpha(0.5f);
            //Get DAta from the database
            allHabitList = getData.getAllHabits();
            habitListAdapter = new HabitListAdapter();
            lvHabitList.setAdapter(habitListAdapter);
            lvHabitList.setSelection(4);
            tvInfo.setText(userName + ", you can choose your own habits later.");
        }

        lvHabitList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

                if (allHabitList.get(position).getHabitDescription().equalsIgnoreCase(TableAttributes.custom_habit)) {
                    addHabit(position);
                } else {
                    //selected = position;
                    Intent i = new Intent(HabitList.this, Habit.class);
                    AppsConstant.habitList = allHabitList;
                    i.putExtra("selected", position);
                    i.putExtra("selected_ritual", selectedRitual);
                    startActivityForResult(i, ADD_HABIT);
                    overridePendingTransition(R.anim.buttom_to_top, R.anim.slide_out_top);
                }

            }

        });

        edtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                if (allHabitList.size() > 0) {
                    allHabitList.clear();
                }
                String keywoard = cs.toString().toLowerCase();
                for (int i = 0; i < AppsConstant.savedHabitList.size(); i++) {
                    if (AppsConstant.savedHabitList.get(i).getHabit().toLowerCase().startsWith(keywoard)) {
                        allHabitList.add(AppsConstant.savedHabitList.get(i));
                    }
                }
//				habitListAdapter = new HabitListAdapter();
//	        	lvHabitList.setAdapter(habitListAdapter);
                habitListAdapter.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (allHabitList.size() == 0) {
                    llAddCustomHabit.setVisibility(View.VISIBLE);
                    lvHabitList.setVisibility(View.INVISIBLE);
                    tvCustomHabit.setText(s.toString());
                }
            }
        });
        ListFiles(new File(FileUtils.HEALTH_LIST_FILE_PATH));
    }

    public void ListFiles(File f) {
        File[] files = f.listFiles();
        Log.d(TAG, "length:-" + files.length);
        int val = Pref.getInteger(getApplicationContext(), StringUtils.HABITLIST, -1);
        Log.d(TAG, "pref val:-" + val);
        if (files.length > 0) {

            if ((val + 1) >= files.length) {
                val = 0;
            } else {
                val = val + 1;
            }
        }
        try {
            Log.d(TAG, "final val:-" + val);
            File soundFile = files[val];
            mPlayer1 = new MediaPlayer();
            mPlayer1.setDataSource(soundFile.toString());
            mPlayer1.prepare();
            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                mPlayer1.start();
            }
            int MAX_VOLUME = 100;
            final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
            mPlayer1.setVolume(volume, volume);
            Pref.setInteger(getApplicationContext(), StringUtils.HABITLIST, val);
            Log.d(TAG, "pref mood:-" + Pref.getInteger(getApplicationContext(), StringUtils.HABITLIST, -1));
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
        }
        if (mPlayer1 != null) {
            mPlayer1.stop();
        }
        if (MyApplication.tts != null) {
            MyApplication.tts.stop();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mPlayer != null) {
            mPlayer.stop();
        }
        if (mPlayer1 != null) {
            mPlayer1.stop();
        }

    }

    private void convertTextToSpeech(int status, String text) {
        if (status == TextToSpeech.SUCCESS) {
            int lang = tts.setLanguage(Locale.US);
            if (lang == TextToSpeech.LANG_MISSING_DATA
                    || lang == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "This Language is not supported");
            } else {
                if (null == text || "".equals(text)) {
                    text = "";
                }
                tts.setPitch(1.0f);
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        } else {
            Log.e("error", "Initilization Failed!");
        }
    }

    private void initializeUIViews() {
        edtSearch = (EditText) findViewById(R.id.edt_search_habit);
        lvHabitList = (ListView) findViewById(R.id.lv_habit_list);
        rllInfo = (RelativeLayout) findViewById(R.id.rll_info);
        imvClick = (ImageView) findViewById(R.id.imv_click);
        tvInfo = (TextView) findViewById(R.id.tv_info);

        llAddCustomHabit = (LinearLayout) findViewById(R.id.ll_add_custom_habit);
        tvCustomHabit = (TextView) findViewById(R.id.tv_custom_habit);
        tvAddCustomHabit = (TextView) findViewById(R.id.tv_add_new_habit);


        edtSearch.setTypeface(GeneralUtility.setTypeFace(HabitList.this));
        tvInfo.setTypeface(GeneralUtility.setTypeFace(HabitList.this));
        tvCustomHabit.setTypeface(GeneralUtility.setTypeFace(HabitList.this));
        tvAddCustomHabit.setTypeface(GeneralUtility.setTypeFace(HabitList.this));

        tvAddCustomHabit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String hsearch = tvCustomHabit.getText().toString().trim();
                Intent i = new Intent(HabitList.this, AddCustomHabit.class);
                i.putExtra("habit", hsearch);
                startActivityForResult(i, RESULT_CUSTOM_HABIT);
            }
        });
    }


    private class HabitListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (allHabitList.size() > 0)
                return allHabitList.size();
            else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            convertView = inflater.inflate(R.layout.habit_list_item, null);

            ImageView imvHabitIcon = (ImageView) convertView.findViewById(R.id.imv_habit_icon);
            TextView tvHabit = (TextView) convertView.findViewById(R.id.tv_habit_name);
            TextView tvHabitTime = (TextView) convertView.findViewById(R.id.tv_habit_time);
            TextView tvHabitDesc = (TextView) convertView.findViewById(R.id.tv_habit_description);
            TextView tvAdd = (TextView) convertView.findViewById(R.id.tv_add);

            tvHabit.setTypeface(GeneralUtility.setTypeFace(HabitList.this));
            tvHabitTime.setTypeface(GeneralUtility.setTypeFace(HabitList.this));
            tvHabitDesc.setTypeface(GeneralUtility.setTypeFace(HabitList.this));

            tvHabit.setText(allHabitList.get(position).getHabit());
            tvHabitTime.setText(allHabitList.get(position).getHabitTime() + " min");
            tvHabitDesc.setText(allHabitList.get(position).getHabitDescription());
            if (allHabitList.get(position).getHabitDescription().equalsIgnoreCase(TableAttributes.custom_habit)) {
                tvHabitDesc.setVisibility(View.INVISIBLE);
                if (allHabitList.get(position).getReminderDesc2() != null && allHabitList.get(position).getReminderDesc2().length() > 0) {
                    GradientDrawable bgShape = (GradientDrawable) imvHabitIcon.getBackground();
                    bgShape.setColor(Color.parseColor(allHabitList.get(position).getReminderDesc2()));
                    //imvHabitIcon.setBackgroundColor(Color.parseColor(allHabitList.get(position).getReminderDesc2()));
                } else
                    imvHabitIcon.setBackgroundResource(R.drawable.habit_custom_icon);
            } else {
                tvHabitDesc.setVisibility(View.VISIBLE);
                imvHabitIcon.setBackgroundResource(AppsConstant.getHabitIcon(allHabitList.get(position).getHabitId()));
            }

            if (allHabitList.get(position).isHabitAdded()) {
                tvAdd.setText("REMOVE");
            } else {
                tvAdd.setText("  ADD  ");
            }

            tvAdd.setTag(position);
            tvAdd.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    int clcikItem = (Integer) v.getTag();
                    addHabit(clcikItem);

                }
            });

            return convertView;
        }


    }


    protected void addHabit(int clcikItem) {
        if (isRitualAdded) {
            if (allHabitList.get(clcikItem).isHabitAdded()) {
                int del = deleteData.removeHabit(allHabitList.get(clcikItem).getHabitId(), userName, selectedRitual);
                if (del > 0) {
                    allHabitList.get(clcikItem).setSelection(false);
                    final Toast toast = Toast.makeText(HabitList.this, "Habit Removed", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (toast != null) {
                                toast.cancel();
                            }
                        }
                    }, 500);
                    habitListAdapter.notifyDataSetChanged();
                }
            } else {
                long row = putData.addUserHabit(userName, allHabitList.get(clcikItem).getHabitId(),
                        selectedRitual, allHabitList.get(clcikItem).getHabitTime());
                Log.d("sunil", "habit list:-" + allHabitList.toString());
                HabitModel hm = allHabitList.get(clcikItem);
//				String user_id=GeneralUtility.getPreferences(getApplicationContext(), "user_id");
                String user_id = PrefData.getStringPref(getApplicationContext(), PrefData.USER_ID);
//				Log.d("sunil","selected Ritual:-"+selectedRitual);
                int category = 0;
                switch (selectedRitual) {
                    case "Morning Routine":
                        category = 1;
                        break;
                    case "Afternoon Routine":
                        category = 2;
                        break;
                    case "Evening Routine":
                        category = 3;
                        break;
                    default:
                        category = 1;
                        break;
                }
//                new SaveToServer(user_id, hm.getHabitId() + "", hm.getHabit(), category + "").execute("http://www.funhabits.co/aaha/habitcategory.php");

                if (row > 0) {
                    allHabitList.get(clcikItem).setSelection(true);
                    MyHabits.habit_flag = true;
                    if (i > 2) {
                        final Toast toast = Toast.makeText(HabitList.this, "Habit Added", 1);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (toast != null) {
                                    toast.cancel();
                                }
                            }
                        }, 500);
                    } else {
                        i++;
                        final Toast toast = Toast.makeText(HabitList.this, "You can also add your own by typing it at top", 1);
                        toast.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (toast != null) {
                                    toast.cancel();
                                }
                            }
                        }, 1000);
                    }
                    habitListAdapter.notifyDataSetChanged();
                } else if (row == -10)
                    Toast.makeText(HabitList.this, R.string.max_habit, 1).show();
            }
        }

    }
//		class SaveToServer extends AsyncTask<String, Void, String> {
//			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//			String jResult;
//			ProgressDialog progressDialog;
//			String habit_user_id;
//			String habit_cat_id;
//			String habit_user_name;
//			String category;
//			public SaveToServer(String habit_user_id,String habit_cat_id,String habit_user_name, String category){
//				this.habit_user_id=habit_user_id;
//				this.habit_cat_id=habit_cat_id;
//				this.habit_user_name=habit_user_name;
//				this.category=category;
//			}
//
//			@Override
//			protected void onPreExecute() {
//				super.onPreExecute();
//				Log.d("sunil","user_id:-"+habit_user_id);
//				Log.d("sunil","habit_cat_id:-"+habit_cat_id);
//				Log.d("sunil","habit_user_name:-"+habit_user_name);
//
//				progressDialog = ProgressDialog.show(HabitList.this, "Please wait...", "");
//				progressDialog.setCancelable(true);
//			}
//
//			@Override
//			protected String doInBackground(String... params) {
//
//				nameValuePairs.add(new BasicNameValuePair("habit_user_id", habit_user_id));
//				nameValuePairs.add(new BasicNameValuePair("habit_cat_id", habit_cat_id));
//				nameValuePairs.add(new BasicNameValuePair("habit_user_name", habit_user_name));
//				nameValuePairs.add(new BasicNameValuePair("category", category));
//
//				try {
//					jResult = WebServices.httpCall(params[0], nameValuePairs);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				return jResult;
//			}
//
//			@Override
//			protected void onPostExecute(String jsonResponse) {
//				super.onPostExecute(jsonResponse);
//
//				if (progressDialog != null)
//					progressDialog.dismiss();
//				try {
//					Log.d("sunil", jsonResponse);
//					JSONObject jsonObject = new JSONObject(jsonResponse);
//					String success = jsonObject.optString("success");
//					if (success.equals("true")) {
//
//					} else {
//						Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
//					}
//				} catch (Exception e) {
//					Log.d("sunil",e.toString());
//				}
//
//
//			}
//
//		}

    /**
     * show an alert dialog
     */
    public void showPopUp(Context _context, String title, String msg) {
        try {
            KickStart.mediaPlayer.stop();
        } catch (Exception e) {

        }
        AlertDialog.Builder alrt = new AlertDialog.Builder(_context);
        alrt.setMessage(msg);
        alrt.setCancelable(false);

        alrt.setTitle(title);
        alrt.setPositiveButton("SHOW ME!", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent i = new Intent(HabitList.this, MyHabit_AVScreen.class);
                i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
                i.putExtra("isFirstWalkthrough", true);
                i.putExtra("position", 0);
                i.putExtra("is_reminder_call", false);
                startActivity(i);
                finish();
            }
        });
        alrt.setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent i = new Intent(HabitList.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        alrt.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
//		super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "ok");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (resultCode == ADD_HABIT) {
                boolean isAdded = data.getBooleanExtra("opted", false);
                if (isAdded) {
//					allHabitList.get(selected).setSelection(true);
//					new HabitListAdapter().notifyDataSetChanged();
                    rllInfo.setVisibility(View.GONE);
                    imvClick.setVisibility(View.GONE);
                    tvInfo.setVisibility(View.GONE);
                    habitListAdapter = new HabitListAdapter();
                    lvHabitList.setAdapter(habitListAdapter);
                    if (!isRitualAdded) {
                        String time[] = selectedRitual.split(" ");
                        String msg = "Want to know what will happen tomorrow at " + time[0].toLowerCase().toString() + "?";
                        String title = "You are all set!";
                        if (ritualTime != null)
                            msg = "Want to know what will happen tommorrow at " + ritualTime + "?";
                        showPopUp(HabitList.this, title, msg);
                    }
                }
            }

            if (resultCode == RESULT_CUSTOM_HABIT) {
                llAddCustomHabit.setVisibility(View.GONE);
                lvHabitList.setVisibility(View.VISIBLE);
                boolean isAdded = data.getBooleanExtra("new_added", false);
                if (isAdded) {
                    //Get DAta from the database
                    allHabitList = getData.getAllHabitsUnionUser(userName, selectedRitual);
                    habitListAdapter.notifyDataSetChanged();
                    //since the item will be added at last position
                    //so, we pass last position of arrylist in the method add habit
                    addHabit(allHabitList.size() - 1);
                }
            }
        }
    }
}

