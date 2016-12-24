package com.motivator.wecareyou.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.motivator.careplan.CarePlanHomeActivity;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.database.GetData;
import com.motivator.database.PrefData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.JourneyData;
import com.motivator.model.JourneyHabitModel;
import com.motivator.model.UserRitualModel;
import com.motivator.relaxationzone.RelaxationZone;
import com.motivator.support.InteractiveScrollView;
import com.motivator.support.NonScrollableListView;
import com.motivator.support.StringUtils;
import com.motivator.wecareyou.DataEntryActivity;
import com.motivator.wecareyou.FirstWalkThrough;
import com.motivator.wecareyou.MainActivity;
import com.motivator.wecareyou.MoodActivity;
import com.motivator.wecareyou.MyApplication;
import com.motivator.wecareyou.MyHabit_AVScreen;
import com.motivator.wecareyou.MyHabits;
import com.motivator.wecareyou.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class HomeFragment extends Fragment {
//    TextToSpeech tts;
    View fragmentView;
    String userName;
    LinearLayout llNotification, llMain;
    NonScrollableListView lvRituals;
//	String times="";

    UpdateData updateData;
    ArrayList<UserRitualModel> userRituals = new ArrayList<UserRitualModel>();
    int counter = 0;
    int counter1 = 0;
    int counter2 = 0;
    boolean flagcounter0 = true, flagcounter1 = false, flagcounter2 = false;
    String selectedRitual = AppsConstant.MORNING_RITUAL;
    GetData getData;
    UserRitualModel userInfo;
    String timeritual = "";
    InteractiveScrollView scrollview;

    public static MediaPlayer mpPlayer;
    FloatingActionButton fab_mrng_ritual,fab_afternoon_ritual,fab_evening_ritual, fab_relaxation, fab_care_plan, fab_data_entry, fab_journey1;
    public static boolean home_flag = true;

    LinearLayout data_notification, careplan_fragment;
    TextView tv_care_note1, tv_care_note2, tv_data_note1, tv_data_note2;

    MainActivity activity;
    private static final String TAG="homefragment";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity= (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("activity", "oncreate");
        getData = new GetData(getActivity());
        updateData = new UpdateData(getActivity());

        userName = GeneralUtility.getPreferences(getActivity(), AppsConstant.user_name);
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.home_fragment, container, false);

        llNotification = (LinearLayout) fragmentView.findViewById(R.id.frame_notification);
        lvRituals = (NonScrollableListView) fragmentView.findViewById(R.id.lv_rituals);

        fab_mrng_ritual = (FloatingActionButton) fragmentView.findViewById(R.id.fab_mrng_ritual);
        data_notification = (LinearLayout) fragmentView.findViewById(R.id.data_notification);
        tv_care_note1 = (TextView) fragmentView.findViewById(R.id.tv_care_note1);
        tv_care_note2 = (TextView) fragmentView.findViewById(R.id.tv_care_note2);

        tv_data_note1 = (TextView) fragmentView.findViewById(R.id.tv_data_note1);
        tv_data_note2 = (TextView) fragmentView.findViewById(R.id.tv_data_note2);
        fab_relaxation = (FloatingActionButton) fragmentView.findViewById(R.id.fab_relaxation);
        fab_care_plan = (FloatingActionButton) fragmentView.findViewById(R.id.fab_care_plan);
        fab_data_entry = (FloatingActionButton) fragmentView.findViewById(R.id.fab_data_entry);
        fab_journey1= (FloatingActionButton) fragmentView.findViewById(R.id.fab_journey1);
        fab_afternoon_ritual= (FloatingActionButton) fragmentView.findViewById(R.id.fab_afternoon_ritual);
        fab_evening_ritual= (FloatingActionButton) fragmentView.findViewById(R.id.fab_evening_ritual);

        setUpNotification();
        if (llNotification.getChildCount() > 0) {
            llNotification.removeAllViews();
        }

        try{
            FirstWalkThrough.mPlayer1.stop();
        }
        catch (Exception e){

        }

        return fragmentView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        scrollview = (InteractiveScrollView) view.findViewById(R.id.scroll_home_fragment);
        careplan_fragment = (LinearLayout) view.findViewById(R.id.careplan_fragment);
    }

    public void initializeRitualList(){
        userRituals = getData.getUserRituals(userName);
        Log.d("database",userRituals.toString());
        if (userRituals != null && userRituals.size() > 0)
            lvRituals.setAdapter(new RitualList());
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();


        initializeRitualList();
        Log.d("activity", "onstart");
        fab_mrng_ritual.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//				Intent intent = new Intent(getActivity(), MyHabits.class);
//				intent.putExtra("is_reminder_call", false);
//				intent.putExtra(AppsConstant.SELECTED_RITUAL, userRituals.get(0).getRitualName());
//				startActivity(intent);

                Intent i = new Intent(getActivity(), MyHabit_AVScreen.class);
                i.putExtra(AppsConstant.SELECTED_RITUAL, userRituals.get(0).getRitualName());
                i.putExtra("isFirstWalkthrough", false);
                i.putExtra("position", 0);
                i.putExtra("is_reminder_call", false);
                startActivity(i);
            }
        });
        data_notification.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DataEntryActivity.class));
            }
        });
        careplan_fragment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CarePlanHomeActivity.class));
            }
        });

        fab_care_plan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CarePlanHomeActivity.class));
            }
        });
        fab_data_entry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DataEntryActivity.class));
            }
        });
        fab_relaxation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RelaxationZone.class));
            }
        });
        fab_journey1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.GoToJourneyFeature();
            }
        });
        fab_afternoon_ritual.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyHabit_AVScreen.class);
                i.putExtra(AppsConstant.SELECTED_RITUAL, userRituals.get(1).getRitualName());
                i.putExtra("isFirstWalkthrough", false);
                i.putExtra("position", 1);
                i.putExtra("is_reminder_call", false);
                startActivity(i);
            }
        });
        fab_evening_ritual.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyHabit_AVScreen.class);
                i.putExtra(AppsConstant.SELECTED_RITUAL, userRituals.get(2).getRitualName());
                i.putExtra("isFirstWalkthrough", false);
                i.putExtra("position", 2);
                i.putExtra("is_reminder_call", false);
                startActivity(i);
            }
        });
    }

    private void setUpNotification() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//		System.out.println(dayOfWeek);
        String day = "";
        switch (dayOfWeek) {
            case 1:
                day = "sunday";
                break;
            case 2:
                day = "monday";
                break;
            case 3:
                day = "tuesday";
                break;
            case 4:
                day = "wednesday";
                break;
            case 5:
                day = "thursday";
                break;
            case 6:
                day = "friday";
                break;
            case 7:
                day = "saturday";
                break;
        }

//		if(day.equalsIgnoreCase("monday")||day.equalsIgnoreCase("wednesday")||day.equalsIgnoreCase("friday")) {
//			if (MainActivity.show_data_entry) {
//				MainActivity.show_data_entry = false;
//				String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//				String[] day1 = date.split("-");
//				try {
//					int dayint = Integer.parseInt(day1[2]);
//					Log.d("sunil", "day:-" + dayint);
////				if(dayint!=AppsConstant.getIntegerFromPref(getActivity().getApplicationContext(),"saved_day")){
//					//save date to pref
//					AppsConstant.saveIntegerToPref(getActivity().getApplicationContext(), "saved_day", dayint);
//					Log.d("sunil", "Showing card");

        tv_data_note1.setText("My Health");
        tv_data_note2.setText("Card For My Health");

        tv_data_note1.setTypeface(GeneralUtility.setTypeFace(getActivity()));
        tv_data_note2.setTypeface(GeneralUtility.setTypeFace(getActivity()));
//				}
//				catch (Exception e){
//
//				}
//			}
//		}
//		else{
//			data_notification.setVisibility(View.GONE);
//		}


        tv_care_note1.setText("Care Plan");
        tv_care_note2.setText("Card For Care plan");
        tv_care_note1.setTypeface(GeneralUtility.setTypeFace(getActivity()));
        tv_care_note2.setTypeface(GeneralUtility.setTypeFace(getActivity()));

        if (llNotification.getChildCount() > 0)
            llNotification.removeAllViews();
        if (AppsConstant.SELECTED_JOURNEY.equalsIgnoreCase(AppsConstant.Interesting_Journey)) {
            ArrayList<JourneyHabitModel> journeyDetails = new ArrayList<JourneyHabitModel>();
            //get journey info
            journeyDetails = getData.getJourneyDetails(userName, AppsConstant.Interesting_Journey);

            Bundle args;
            String frag_tag = "";
            FragmentManager fragmentManager = getChildFragmentManager();
            for (int position = 0; position < journeyDetails.size(); position++) {
                int habitId = journeyDetails.get(position).getHabitId();
                int letterStatus = journeyDetails.get(position).isLetterRead();
                int isChallengeAccepted = journeyDetails.get(position).isChallengeAccepted();
                int isGoalCompleted = journeyDetails.get(position).getGoalStatus();
                int actionStatus = journeyDetails.get(position).getActionStatus();
                int motivatorStatus = journeyDetails.get(position).getMotivationStatus();
                String jName = journeyDetails.get(position).getJourneyName();
                String selectedStep = updateData.getJourneyStep(userName, jName, habitId);
                //ArrayList<String> journeyStepText = JourneyData.getJourneyStepsText(habitId);
                if (position == 0) {
                    if (letterStatus < TableAttributes.LETTER_COMPLETED) {
                        frag_tag = "letter" + position;
                        Fragment isAdded = getChildFragmentManager().findFragmentByTag(frag_tag);

                        int letterNum = position + 1;
                        Fragment letterFrag = new NoteFragment();
                        args = new Bundle();
                        args.putString(AppsConstant.selected_journey_step, selectedStep);
                        args.putInt(AppsConstant.h_id, habitId);
                        args.putString(NoteFragment.ARG_NOTE_TYPE, NoteFragment.NOTE_LETTER);
                        args.putInt(NoteFragment.ARG_NOTE_NUM, letterNum);
                        args.putString(NoteFragment.ARG_NOTE_TITLE, "Your Letter No." + letterNum);
                        args.putString(NoteFragment.ARG_NOTE_DESC, "if you forget, you'll be tired by mid-day");
                        letterFrag.setArguments(args);
                        fragmentManager.beginTransaction().add(R.id.frame_notification, letterFrag, frag_tag).commit();

                        if (counter == 0) {
                            flagcounter0 = false;
//							tts = new TextToSpeech(getActivity().getApplicationContext(), new OnInitListener() {
//
//								@Override
//								public void onInit(int status) {
//									// TODO Auto-generated method stub
//									convertTextToSpeech(status, "Hey, "+userName+". Be sure to check your Home screen for new updates each day. I will let you know about new motivations, tips, and encouragements from your friends and family and stalker-fans there.");
//								}
//							});
//							try {
//								Thread.sleep(5000);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
                            SharedPreferences sp = getActivity().getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
                            boolean first_msg1 = sp.getBoolean("home_voice", true);
                            if (first_msg1) {
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putBoolean("home_voice", false);
                                edit.commit();
                                if (GeneralUtility.getPreferencesBoolean(getActivity().getApplicationContext(), AppsConstant.AVS_SOUND)) {
                                    if(MyApplication.tts_initialized){
                                        MyApplication.tts.speak("Great that we now have a goal. This is the home screen of the app. You will see rituals and updates. Rituals are basically groups of habits. We have created three groups for you. Morning, afternoon and evening. Go ahead and tap the ritual to add habits and set it. It'll take you to the ritual's screen.", TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                }

                            } else {
                                Log.d("sun", "reminder playing");

                                if (GeneralUtility.getPreferencesBoolean(getActivity().getApplicationContext(), AppsConstant.AVS_SOUND)) {
                                    if(MyApplication.tts_initialized){
                                        MyApplication.tts.speak("Good day, "+ PrefData.getStringPref(getActivity().getApplicationContext(),PrefData.NAME_KEY)+". Be sure to check your Home screen for new updates each day. I will let you know about new motivations, tips, and encouragements from your friends and family there", TextToSpeech.QUEUE_FLUSH, null);
                                    }
                                }
                                counter++;
                            }
                        }
                    }
                } else {
                    if (letterStatus > TableAttributes.LETTER_Unread && letterStatus < TableAttributes.LETTER_COMPLETED) {
                        frag_tag = "letter" + position;
                        Fragment isAdded = getChildFragmentManager().findFragmentByTag(frag_tag);

                        int letterNum = position + 1;
                        Fragment letterFrag = new NoteFragment();
                        args = new Bundle();
                        args.putString(AppsConstant.selected_journey_step, selectedStep);
                        args.putInt(AppsConstant.h_id, habitId);
                        args.putString(NoteFragment.ARG_NOTE_TYPE, NoteFragment.NOTE_LETTER);
                        args.putInt(NoteFragment.ARG_NOTE_NUM, letterNum);
                        args.putString(NoteFragment.ARG_NOTE_TITLE, "Your Letter No." + letterNum);
                        args.putString(NoteFragment.ARG_NOTE_DESC, "if you forget, you'll be tired by mid-day");
                        letterFrag.setArguments(args);
                        fragmentManager.beginTransaction().add(R.id.frame_notification, letterFrag, frag_tag).commit();
                        if (counter1 == 0) {
                            flagcounter1 = true;
//							tts = new TextToSpeech(getActivity().getApplicationContext(), new OnInitListener() {
//
//								@Override
//								public void onInit(int status) {
//									// TODO Auto-generated method stub
//									convertTextToSpeech(status, genericChats());
//								}
//							});
                            counter1++;
                        }
                    }
                }

                if (isChallengeAccepted == TableAttributes.STATUS_VALUE_1 && isGoalCompleted < TableAttributes.CHALLENCE_COMPLETED) {
                    frag_tag = "goal" + position;
                    Fragment isAdded = getChildFragmentManager().findFragmentByTag(frag_tag);
                    Fragment goalFrag = new NoteFragment();
                    args = new Bundle();
                    args.putString(AppsConstant.selected_journey_step, selectedStep);
                    args.putInt(AppsConstant.h_id, habitId);
                    args.putString(NoteFragment.ARG_NOTE_TYPE, NoteFragment.NOTE_GOAL);
                    args.putInt(NoteFragment.ARG_NOTE_NUM, 1);
                    args.putString(NoteFragment.ARG_NOTE_TITLE, JourneyData.getGoalTitle(habitId));
                    args.putString(NoteFragment.ARG_NOTE_DESC, JourneyData.getGoalOnCard(habitId));
                    goalFrag.setArguments(args);
                    fragmentManager.beginTransaction().add(R.id.frame_notification, goalFrag, frag_tag).commit();
                    if (counter2 == 0) {
                        flagcounter2 = true;
//						tts = new TextToSpeech(getActivity().getApplicationContext(), new OnInitListener() {
//
//							@Override
//							public void onInit(int status) {
//								// TODO Auto-generated method stub
//								convertTextToSpeech(status, genericChats());
//							}
//						});
                        counter2++;
                    }
                }
                if (isChallengeAccepted > TableAttributes.STATUS_VALUE_0 && actionStatus <= TableAttributes.STATUS_VALUE_1) {
                    frag_tag = "action" + position;
                    Fragment isAdded = getChildFragmentManager().findFragmentByTag(frag_tag);
                    int action_num = 0;
                    switch (actionStatus) {
                        case TableAttributes.STATUS_VALUE_0:
                            action_num = 1;
                            break;

                        case TableAttributes.STATUS_VALUE_1:
                            action_num = 2;
                            break;
                    }
                    String actionDesc = JourneyData.getActionOnCard(habitId, action_num);
                    if (actionDesc != null && actionDesc.trim().length() > 0) {
                        Fragment actionFrag = new NoteFragment();
                        args = new Bundle();
                        args.putString(AppsConstant.selected_journey_step, selectedStep);
                        args.putInt(AppsConstant.h_id, habitId);
                        args.putString(NoteFragment.ARG_NOTE_TYPE, NoteFragment.NOTE_ACTION);
                        args.putInt(NoteFragment.ARG_NOTE_NUM, action_num);
                        args.putString(NoteFragment.ARG_NOTE_TITLE, "Your Action");
                        args.putString(NoteFragment.ARG_NOTE_DESC, actionDesc);
                        actionFrag.setArguments(args);
                        fragmentManager.beginTransaction().add(R.id.frame_notification, actionFrag, frag_tag).commit();
                    }

                }
                if (actionStatus > TableAttributes.STATUS_VALUE_0 && motivatorStatus <= 5) {
                    frag_tag = "motivator" + position;
                    Fragment isAdded = getChildFragmentManager().findFragmentByTag(frag_tag);
                    int motivator_num = 0;
                    switch (motivatorStatus) {
                        case TableAttributes.STATUS_VALUE_0:
                            motivator_num = 1;
                            break;
                        case TableAttributes.STATUS_VALUE_1:
                            motivator_num = 2;
                            break;
                        case TableAttributes.STATUS_VALUE_2:
                            motivator_num = 3;
                            break;
                        case 3:
                            motivator_num = 4;
                            break;
                        case 4:
                            motivator_num = 5;
                            break;
                        case 5:
                            motivator_num = 6;
                            break;
                    }
                    String motivatorDesc = JourneyData.getMotivatorOnCard(habitId, motivator_num);
                    if (motivatorDesc != null && motivatorDesc.trim().length() > 0) {
                        Fragment motivatorFrag = new NoteFragment();
                        args = new Bundle();
                        args.putString(AppsConstant.selected_journey_step, selectedStep);
                        args.putInt(AppsConstant.h_id, habitId);
                        args.putString(NoteFragment.ARG_NOTE_TYPE, NoteFragment.NOTE_MOTIVATOR);
                        args.putInt(NoteFragment.ARG_NOTE_NUM, motivator_num);
                        args.putString(NoteFragment.ARG_NOTE_TITLE, "Motivator");
                        args.putString(NoteFragment.ARG_NOTE_DESC, motivatorDesc);
                        motivatorFrag.setArguments(args);
                        fragmentManager.beginTransaction().add(R.id.frame_notification, motivatorFrag, frag_tag).commit();
                    }
                }
            }
        }
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.d("activity", "onpuase");
        Log.d("sun", "Home:-onPause");

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        Log.d("sun", "Home:-onDestroy");
        super.onDestroy();
        Log.d("activity", "ondestroy");
        if (mpPlayer != null) {
            mpPlayer.stop();
        }
        if (MyApplication.tts != null) {
            MyApplication.tts.stop();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("activity", "onstop");
    }

    public void playMusic() {
        Log.d("sun", "music playing");

        String[] list_strings= getResources().getStringArray(R.array.homescreenttstext);
        playRitualVoices(list_strings);
    }
    public void playRitualVoices(String[] list_files){
        int val= Pref.getInteger(getActivity().getApplicationContext(),StringUtils.VOICE_RITUAL_LIST_HOME,-1);
        Log.d(TAG,"pref for tis:-"+val);
        if(list_files.length>0){
            if((val+1)>=list_files.length){
                val=0;
            }
            else{
                val=val+1;
            }
        }
        try{
            Log.d(TAG,"final val:-"+val);
            Log.d(TAG,list_files[val]);
            if (GeneralUtility.getPreferencesBoolean(getActivity().getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if(MyApplication.tts_initialized){
                    MyApplication.tts.speak(list_files[val], TextToSpeech.QUEUE_FLUSH, null);
                }
            }
            Pref.setInteger(getActivity().getApplicationContext(),StringUtils.VOICE_RITUAL_LIST_HOME,val);
            Log.d(TAG,"pref mood:-"+Pref.getInteger(getActivity().getApplicationContext(),StringUtils.VOICE_RITUAL_LIST_HOME,-1));
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
        }
    }

    private String genericChats() {
        String mood = AppsConstant.user_mood;
        if (mood.equalsIgnoreCase("bored")) {
            Random rn = new Random();
            int range = 5 - 1 + 1;
            int randomNum = rn.nextInt(range) + 1;
            System.out.println(randomNum);
            switch (randomNum) {
                case 1:
                    return "Your stalkers left you a message";
                case 2:
                    return "Here�s something to do. You have some updates";
                case 3:
                    return "Maybe you�ll find these stimulating";
                case 4:
                    return "And now a word from our sponsors... Kidding...  Here�s your updates.";
                case 5:
                    return "messages will self destruct in� No idea. So don�t let them sit too long.";
                default:
                    return "Your stalkers left you a message";
            }
        }
        if (mood.equalsIgnoreCase("sad")) {
            Random rn = new Random();
            int range = 3 - 1 + 1;
            int randomNum = rn.nextInt(range) + 1;
            System.out.println(randomNum);
            switch (randomNum) {
                case 1:
                    return "Someone out there cares about you. There�s a message.";
                case 2:
                    return "Maybe these will brighten your day a little.";
                case 3:
                    return "It�s not some sweet fuzzy creature, but this should still lighten your day a little";
                default:
                    return "It�s not some sweet fuzzy creature, but this should still lighten your day a little";
            }
        }
        if (mood.equalsIgnoreCase("irritated")) {
            Random rn = new Random();
            int range = 2 - 1 + 1;
            int randomNum = rn.nextInt(range) + 1;
            System.out.println(randomNum);
            switch (randomNum) {
                case 1:
                    return "I bet you could use a small distraction.";
                case 2:
                    return "At least none of this is junk mail... right?";
                default:
                    return "It�s not some sweet fuzzy creature, but this should still lighten your day a little";
            }
        }
        if (mood.equalsIgnoreCase("stressed")) {
            return "There are some updates and messages for you. No rush.";
        }
        if (mood.equalsIgnoreCase("excited") || mood.equalsIgnoreCase("cheerful")) {
            Random rn = new Random();
            int range = 2 - 1 + 1;
            int randomNum = rn.nextInt(range) + 1;
            System.out.println(randomNum);
            switch (randomNum) {
                case 1:
                    return "Don�t forget these in all the excitement";
                case 2:
                    return "Well here�s some extra good news. You have some [updates/messages] waiting for you.";
                default:
                    return "Well here�s some extra good news. You have some [updates/messages] waiting for you.";
            }
        } else {
            Random rn = new Random();
            int range = 5 - 1 + 1;
            int randomNum = rn.nextInt(range) + 1;
            System.out.println(randomNum);
            switch (randomNum) {
                case 1:
                    return "Looks like you have an admirer! You got a message from " + userName + " while you were out.";
                case 2:
                    return "I left your mail on the table";
                case 3:
                    return "Here you go. It looks important.";
                case 4:
                    return "Let�s see...  an invitation to a yacht party... a multimillion dollar trade agreement.... oh! here�s some stuff for you too. Here you go.";
                case 5:
                    return "Now what do we have here? ";
                default:
                    return "Now what do we have here? ";
            }
        }
    }
//
//    private void convertTextToSpeech(int status, String text) {
//        if (status == TextToSpeech.SUCCESS) {
//            int lang = tts.setLanguage(Locale.US);
//            if (lang == TextToSpeech.LANG_MISSING_DATA
//                    || lang == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("error", "This Language is not supported");
//            } else {
//                if (null == text || "".equals(text)) {
//                    text = "";
//                }
//                tts.setPitch(1.0f);
//                tts.setSpeechRate(0.8f);
//                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//            }
//        } else {
//            Log.e("error", "Initilization Failed!");
//        }
//    }


    private class RitualList extends BaseAdapter {
        @Override
        public int getCount() {
            return userRituals.size();
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
            LayoutInflater inflater = getActivity().getLayoutInflater();
            convertView = inflater.inflate(R.layout.rituals_home_img, null);

            ImageView imvRitual = (ImageView) convertView.findViewById(R.id.imv_ritual_img);
            TextView tvRitualName = (TextView) convertView.findViewById(R.id.tv_ritual_name);
            TextView tvRitualTime = (TextView) convertView.findViewById(R.id.tv_ritual_time);

            tvRitualName.setTypeface(GeneralUtility.setTypeFace(getActivity()));
            tvRitualTime.setTypeface(GeneralUtility.setTypeFace(getActivity()));

            String ritualName = userRituals.get(position).getRitualDisplayName();
            imvRitual.setImageResource(AppsConstant.getRitualImg(userRituals.get(position).getRitualImg()));

            tvRitualName.setText(ritualName + "\n" + userRituals.get(position).getRitualTime());
            tvRitualTime.setText(userRituals.get(position).getRitualTime());

            imvRitual.setTag(position);
            imvRitual.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    int click = (Integer) v.getTag();
                    Intent intent = new Intent(getActivity(), MyHabits.class);
                    intent.putExtra("is_reminder_call", false);
                    intent.putExtra(AppsConstant.SELECTED_RITUAL, userRituals.get(click).getRitualName());
                    startActivityForResult(intent, 1);
                }
            });

            return convertView;
        }

    }

    public boolean getValuePreference(String key) {
        SharedPreferences sp = getActivity().getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        return sp.getBoolean(key, true);
    }

    public void setValuePreference(String key, boolean value) {
        SharedPreferences sp = getActivity().getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.d("msg", "result");

            if (getValuePreference("home_last")) {

                String back_home_screen="Did you check out other rituals as well? In fact, you can create your own ritual by tapping the plus in the top. We can get to that in a minute. We have planned an exciting surprise for you. Tap the magic lamp tab in the top or just slide the screen two times, to the left.";
//                mpPlayer = MediaPlayer.create(getActivity(), R.raw.back_home_screen);
//                mpPlayer.start();
                if(MyApplication.tts_initialized){
                    MyApplication.tts.speak(back_home_screen, TextToSpeech.QUEUE_FLUSH, null);
                }
                setValuePreference("home_last", false);
                home_flag = true;

            } else {

            }
//	        if(resultCode == getActivity().RESULT_OK){
//	            String result=data.getStringExtra("result");
//	        }
//	        if (resultCode == getActivity().RESULT_CANCELED) {
//	            //Write your code if there's no result
//	        }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("activity", "onresume");
//		SharedPreferences sp=getActivity().getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
//        times=sp.getString("times", "");
        if (flagcounter0) {
            if (MainActivity.times.equals("0")) {
                Bundle extras=getActivity().getIntent().getExtras();

                    if(extras!=null){
                        if(extras.getString("mood").equals("cancel")){

                        }
                        else{
                            showMoodDialog();
                            MainActivity.times = "1";
                        }
                    }
                    else{
                        showMoodDialog();
                        MainActivity.times = "1";
                    }
                }

//        	playMusic1();
            } else {
                musicMehtod1();
            }
        }


    //Logic methods start here
    private void musicMehtod1() {
        if (MainActivity.play_times == 0) {
            MainActivity.play_times = MainActivity.play_times + 1;
            musicPlayingMethod();
        } else {
            if (MainActivity.play_times % 2 == 0) {
                if (MainActivity.bol_flag) {
                    MainActivity.bol_flag = false;
                    MainActivity.current_time = System.currentTimeMillis();
                } else {
                    int time = (int) (((System.currentTimeMillis() - MainActivity.current_time) / 1000) / 60);
                    if (time > 30) {
                        MainActivity.play_times = MainActivity.play_times + 1;
                        musicPlayingMethod();
                        MainActivity.bol_flag = true;
                    } else {

                    }
                }
            } else {
                MainActivity.play_times = MainActivity.play_times + 1;
                musicPlayingMethod();
            }
        }
    }

    private void musicPlayingMethod() {

            playMusic1();
    }



    private void showMoodDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("How are you feeling?");
        dialog.setCancelable(false);
        // set the custom dialog components - text, image and button
        ImageButton tensed = (ImageButton) dialog.findViewById(R.id.tense);
        ImageButton excited = (ImageButton) dialog.findViewById(R.id.excited);
        ImageButton cheerful = (ImageButton) dialog.findViewById(R.id.cheerful);
        ImageButton relaxed = (ImageButton) dialog.findViewById(R.id.relaxed);
        ImageButton calm = (ImageButton) dialog.findViewById(R.id.calm);
        ImageButton bored = (ImageButton) dialog.findViewById(R.id.bored);
        ImageButton sad = (ImageButton) dialog.findViewById(R.id.sad);
        ImageButton irritated = (ImageButton) dialog.findViewById(R.id.irritated);
        ImageButton neutral = (ImageButton) dialog.findViewById(R.id.neutral);
        ImageButton stressed = (ImageButton) dialog.findViewById(R.id.stressed);
        Button skip = (Button) dialog.findViewById(R.id.skip);


        SharedPreferences sp = getActivity().getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);

        String user_gender = sp.getString("user_gender", AppsConstant.user_gender);
        Log.d("sun", user_gender);

        if (user_gender.equals("guy") || user_gender.endsWith("dad") || user_gender.equals("granddad")) {
            tensed.setImageDrawable(getResources().getDrawable(R.drawable.tensed_male));
            excited.setImageDrawable(getResources().getDrawable(R.drawable.excited_male));
            cheerful.setImageDrawable(getResources().getDrawable(R.drawable.cheerful_male));
            calm.setImageDrawable(getResources().getDrawable(R.drawable.calm_male));
            bored.setImageDrawable(getResources().getDrawable(R.drawable.bored_male));
            sad.setImageDrawable(getResources().getDrawable(R.drawable.sad_male));
            irritated.setImageDrawable(getResources().getDrawable(R.drawable.irritated_male));
            neutral.setImageDrawable(getResources().getDrawable(R.drawable.neutral_male));
            stressed.setImageDrawable(getResources().getDrawable(R.drawable.stressed_male));
            relaxed.setImageDrawable(getResources().getDrawable(R.drawable.relaxed_male));
        } else {

        }


        tensed.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppsConstant.user_mood = StringUtils.TENSED;
                startActivity(new Intent(getActivity(), MoodActivity.class));
                dialog.dismiss();

            }
        });
        excited.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppsConstant.user_mood = StringUtils.EXCITED;
                startActivity(new Intent(getActivity(), MoodActivity.class));
                dialog.dismiss();
            }
        });
        cheerful.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppsConstant.user_mood = StringUtils.CHEERFUL;
                startActivity(new Intent(getActivity(), MoodActivity.class));
                dialog.dismiss();
            }
        });
        relaxed.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppsConstant.user_mood = StringUtils.RELAXED;
                startActivity(new Intent(getActivity(), MoodActivity.class));
                dialog.dismiss();
            }
        });
        calm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppsConstant.user_mood = StringUtils.CALM;
                startActivity(new Intent(getActivity(), MoodActivity.class));
                dialog.dismiss();
            }
        });
        bored.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppsConstant.user_mood = StringUtils.BORED;
                startActivity(new Intent(getActivity(), MoodActivity.class));
                dialog.dismiss();
            }
        });
        sad.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppsConstant.user_mood = StringUtils.SAD;
                startActivity(new Intent(getActivity(), MoodActivity.class));
                dialog.dismiss();
            }
        });
        irritated.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppsConstant.user_mood = StringUtils.IRRITATED;
                startActivity(new Intent(getActivity(), MoodActivity.class));
                dialog.dismiss();
            }
        });
        neutral.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppsConstant.user_mood = StringUtils.NEUTRAL;
                startActivity(new Intent(getActivity(), MoodActivity.class));
                dialog.dismiss();
            }
        });
        stressed.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AppsConstant.user_mood = StringUtils.STRESSED;
                startActivity(new Intent(getActivity(), MoodActivity.class));
                dialog.dismiss();
            }
        });
//
        skip.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                musicPlayingMethod();
            }
        });
        dialog.show();
    }

    public void playMusic1() {
        SharedPreferences sp = getActivity().getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        userRituals = getData.getUserRituals(userName);
        Calendar cal = Calendar.getInstance();
        //
        int millisecond = cal.get(Calendar.MILLISECOND);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        //12 hour format
        int hour = cal.get(Calendar.HOUR);
        //24 hour format
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
        Log.d("msg", hourofday + ":" + minute + ":" + second);
        String currenttime = hourofday + ":" + minute + ":" + second;
        String timing = "";
        if (hourofday < 12) {
            timing = "morning";
        } else {
            if (hourofday >= 12 && hourofday < 17) {
                timing = "afternoon";
            } else {
                if (hourofday >= 17) {
                    timing = "evening";
                }
            }
        }

        String currentdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String saveddate = sp.getString("date", "");
        String savetime = sp.getString("time", "");


        editor.putString("date", currentdate);
        editor.putString("time", timing);
        editor.commit();

//		setUpNotification();
        if (userRituals != null && userRituals.size() > 0)
            lvRituals.setAdapter(new RitualList());
        if (flagcounter0) {
//
//			tts = new TextToSpeech(getActivity().getApplicationContext(), new OnInitListener() {
//
//				@Override
//				public void onInit(int status) {
//					// TODO Auto-generated method stub
//					String s=getChatRitual();
//					Log.d("msg",s+"...");
//					convertTextToSpeech(status, s);
//				}
//
//			});
            Log.d("sun", "saved time:-" + savetime);
            Log.d("sun", "saved date:-" + saveddate);

            Log.d("sun", "current time:-" + timing);
            Log.d("sun", "current date:-" + currentdate);
            if (saveddate.equals(currentdate) && savetime.equals(timing)) {
                Log.d("sun", "Play Music");
                playMusic();
            } else {
                Log.d("sun", "scheduled Music");
                if (home_flag) {
                    scheduledMusic();
                }
            }
        }
    }

    public void scheduledMusic() {
        if (AppsConstant.user_mood.equals("irritated")) {
            String i_wont_trouble_you="I won’t trouble you long on this part. Would you mind taking a moment to fill these in?";
            if (GeneralUtility.getPreferencesBoolean(getActivity().getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if(MyApplication.tts_initialized){
                    MyApplication.tts.speak(i_wont_trouble_you, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
            return;
        }
        if (AppsConstant.user_mood.equals("excited") || AppsConstant.user_mood.equals("cheerful")) {
            String while_we_are_on_a_roll="While we are on a roll, let’s look over a few notes and make sure";
            if (GeneralUtility.getPreferencesBoolean(getActivity().getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if(MyApplication.tts_initialized){
                    MyApplication.tts.speak(while_we_are_on_a_roll, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
            return;
        }
        if (AppsConstant.user_mood.equals("stressed") || AppsConstant.user_mood.equals("tensed") || AppsConstant.user_mood.equals("sad")) {
            String understand="I understand the day is overwhelming right now. We’ll update our records later.";
            if (GeneralUtility.getPreferencesBoolean(getActivity().getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if(MyApplication.tts_initialized){
                    MyApplication.tts.speak(understand, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
            return;
        } else {
            Random rn = new Random();
            int range = 3 - 1 + 1;
            int randomNum = rn.nextInt(range) + 1;
            System.out.println(randomNum);
            switch (randomNum) {
                case 1:
                    if (GeneralUtility.getPreferencesBoolean(getActivity().getApplicationContext(), AppsConstant.AVS_SOUND)) {
                        if(MyApplication.tts_initialized){
                            MyApplication.tts.speak("Hello, buddy. Please don’t forget to update your health journal, today. It will help me plan our strategy for the journey.", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    return;
                case 2:
                    if (GeneralUtility.getPreferencesBoolean(getActivity().getApplicationContext(), AppsConstant.AVS_SOUND)) {
                        if(MyApplication.tts_initialized){
                            MyApplication.tts.speak("Hey buddy how is your blood ad energy levels today?", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    return;
                case 3:
                    if (GeneralUtility.getPreferencesBoolean(getActivity().getApplicationContext(), AppsConstant.AVS_SOUND)) {
                        if(MyApplication.tts_initialized){
                            MyApplication.tts.speak("I don’t want you falling over on me. Let’s update a few things before we continue.", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    return;
                default:
                    if (GeneralUtility.getPreferencesBoolean(getActivity().getApplicationContext(), AppsConstant.AVS_SOUND)) {
                        if(MyApplication.tts_initialized){
                            MyApplication.tts.speak("I don’t want you falling over on me. Let’s update a few things before we continue.", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    return;
            }
        }
    }


//	private String getChatRitual(){
//		 	Calendar cal = Calendar.getInstance();
//
//	        int millisecond = cal.get(Calendar.MILLISECOND);
//	        int second = cal.get(Calendar.SECOND);
//	        int minute = cal.get(Calendar.MINUTE);
//	              //12 hour format
//	        int hour = cal.get(Calendar.HOUR);
//	              //24 hour format
//	        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
//	        Log.d("msg",hourofday+":"+minute+":"+second);
//		Log.d("msg","playing tts");
//		if(hourofday<12){
//			Random rn = new Random();
//			int range = 3 - 1 + 1;
//			int randomNum =  rn.nextInt(range) + 1;
//			System.out.println(randomNum);
//			switch (randomNum) {
//			case 1:return "Good Morning";
//			case 2:return "Time to get started on your day. ";
//			case 3:return "I was up extra early this morning. Even the coffee looked surprised to see me.";
//			default:return "Now what do we have here? ";
//		}
//		}
//		if(hourofday<17){
//			Random rn = new Random();
//			int range = 4 - 1 + 1;
//			int randomNum =  rn.nextInt(range) + 1;
//			System.out.println(randomNum);
//			switch (randomNum) {
//			case 1:return "The day is half over. You can make it.";
//			case 2:return "The day is only half over, and I�m ready to go back to bed.";
//			case 3:return "The world would be a better place, with more coffee breaks.";
//			case 4:return "It�s too early to call it a day, and too late to go back to bed.";
//			default:return "It�s too early to call it a day, and too late to go back to bed.";
//		}
//		}
//		if(hourofday>=17){
//			Random rn = new Random();
//			int range = 7 - 1 + 1;
//			int randomNum =  rn.nextInt(range) + 1;
//			System.out.println(randomNum);
//			switch (randomNum) {
//			case 1:return "Just a little bit more to go.";
//			case 2:return "No rest for the wicked. But there�s no rest for the rest of us either, right?";
//			case 3:return "If this day gets any longer, we�ll have to make a sequel.";
//			case 4:return "Finally... it�s almost over! I can hear my bed crying for me. ";
//			case 5:return "Is it bedtime yet?";
//			case 6:return "Where did the day go? I could have sworn there was more of it left.";
//			case 7:return "What�s for dinner?";
//			default:return "What�s for dinner?";
//		}
//		}
//		else{
//			return "Now what do we have here?";
//		}
//	}


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("saved", true);
        super.onSaveInstanceState(outState);
    }
}

