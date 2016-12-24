package com.motivator.wecareyou.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.imanoweb.calendarview.CustomCalendarView;
import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;
import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.database.GetData;
import com.motivator.model.HabitModel;
import com.motivator.model.HabitSuccessModel;
import com.motivator.model.TimeLineModel;
import com.motivator.model.UserRitualModel;
import com.motivator.wecareyou.ClickOnCalendar;
import com.motivator.wecareyou.CompletionRate;
import com.motivator.wecareyou.R;
import com.motivator.wecareyou.TodayTimeLine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TimeLineFragment extends Fragment implements OnClickListener{
	
	
	Context mContext;
	Spinner spnRitual;
	ProgressBar progress;
	TextView tvToday, tvTodayStatus;
	TextView tvTimeline, tvtodayTitle, tvSuccessRate, tvMonthView;
	LinearLayout llTimeline;
	RelativeLayout rllSuccessRate;
	CustomCalendarView calendarView;
	View viewClick;
	int arg;
	public static final String ARG_OBJECT = "object";
	
	String userName = "", selectedRitual = AppsConstant.MORNING_RITUAL;
	ArrayList<String> ritualList;
	Calendar currentCalendar;
	GetData get;
	TextToSpeech tts;
	GetData getData;
	UserRitualModel userInfo ;
	String timeritual="";
	ArrayList<HabitModel> userHabit;
	TextView tv_habit1,tv_habit2,tv_habit3,tv_habit4,tv_status1,tv_status2,tv_status3,tv_status4;
	
	//success rate variable
	ArrayList<HabitSuccessModel> successRateList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	
    	mContext = getActivity();
    	userName = GeneralUtility.getPreferences(getActivity(), AppsConstant.user_name);
        // Inflate the layout for this fragment
    	View fragmentView = inflater.inflate(R.layout.time_line, container, false);
    	
    	//Initialize View from layout
    	llTimeline = (LinearLayout)fragmentView.findViewById(R.id.rll_timeline);
    	rllSuccessRate = (RelativeLayout)fragmentView.findViewById(R.id.rll_success_rate);
    	spnRitual = (Spinner)fragmentView.findViewById(R.id.spn_ritual);
    	
    	tvTimeline = (TextView)fragmentView.findViewById(R.id.tv_timeline);
    	tvtodayTitle = (TextView)fragmentView.findViewById(R.id.tv_today_title);
    	
    	tvToday = (TextView)fragmentView.findViewById(R.id.tv_today);
    	tvTodayStatus = (TextView)fragmentView.findViewById(R.id.tv_today_status);
    	tvSuccessRate = (TextView)fragmentView.findViewById(R.id.tv_success_rate);
    	tvMonthView = (TextView)fragmentView.findViewById(R.id.tv_month_view);
    	tv_habit1=(TextView) fragmentView.findViewById(R.id.tv_habit_name1);
    	tv_habit2=(TextView) fragmentView.findViewById(R.id.tv_habit_name2);
    	tv_habit3=(TextView) fragmentView.findViewById(R.id.tv_habit_name3);
    	tv_habit4=(TextView) fragmentView.findViewById(R.id.tv_habit_name4);
    	tv_status1=(TextView) fragmentView.findViewById(R.id.tv_habit_name1_status);
    	tv_status2=(TextView) fragmentView.findViewById(R.id.tv_habit_name2_status);
    	tv_status3=(TextView) fragmentView.findViewById(R.id.tv_habit_name3_status);
    	tv_status4=(TextView) fragmentView.findViewById(R.id.tv_habit_name4_status);
    	
    	
    	tv_habit1.setVisibility(View.GONE);
    	tv_habit2.setVisibility(View.GONE);
    	tv_habit3.setVisibility(View.GONE);
    	tv_habit4.setVisibility(View.GONE);
    	
    	tv_status1.setVisibility(View.GONE);
    	tv_status2.setVisibility(View.GONE);
    	tv_status3.setVisibility(View.GONE);
    	tv_status4.setVisibility(View.GONE);
    	
    	
    	
    	
    	tvTimeline.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvtodayTitle.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvToday.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvTodayStatus.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvSuccessRate.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	tvMonthView.setTypeface(GeneralUtility.setTypeFace(getActivity()));
    	progress = (ProgressBar)fragmentView.findViewById(R.id.pb_success);
        calendarView = (CustomCalendarView) fragmentView.findViewById(R.id.calendar_view);
        viewClick = (View)fragmentView.findViewById(R.id.view_cal_click);
        //Initialize calendar with date
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        
        
        //Getting the ritual
        getData = new GetData(getActivity());
        userInfo = new UserRitualModel();
		userInfo = getData.getRitualsDetails(userName, selectedRitual);
        
//        SpannableString s = new SpannableString(userInfo.getRitualDisplayName().toLowerCase());
//        s.setSpan(new com.motivator.support.TypefaceSpan(getActivity(), "fonts/Montez-Regular.ttf"), 0, s.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        timeritual=s.toString().replace("routine", "");
        Log.d("msg",timeritual);
        //Show monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        //Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false);

        //call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar);

        ritualList = new ArrayList<String>();
        setRitualList();
        
        userHabit = getData.getUserHabits(userName, selectedRitual);
       showHabitsCalendar();
        //Handling custom calendar events
//        calendarView.setCalendarListener(new CalendarListener() {
//            @Override
//            public void onDateSelected(Date date) {
//            	showHabitTimeLine();
//                //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//               // Toast.makeText(getActivity(), df.format(date), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onMonthChanged(Date date) {
//                //SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
//                //Toast.makeText(getActivity(), df.format(date), Toast.LENGTH_SHORT).show();
//            }
//        });

        spnRitual.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) 
			{
				selectedRitual = ritualList.get(position);
				SuccessRateMethod();
				showHabitsCalendar();
				decorateCalendar();
				setTodayTimeline();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		}) ;
    	
    	llTimeline.setOnClickListener(this);
    	rllSuccessRate.setOnClickListener(this);
    	viewClick.setOnClickListener(this);
        return fragmentView;
    }
    public void showHabitsCalendar(){
    	tv_habit1.setVisibility(View.GONE);
    	tv_habit2.setVisibility(View.GONE);
    	tv_habit3.setVisibility(View.GONE);
    	tv_habit4.setVisibility(View.GONE);
    	
    	tv_status1.setVisibility(View.GONE);
    	tv_status2.setVisibility(View.GONE);
    	tv_status3.setVisibility(View.GONE);
    	tv_status4.setVisibility(View.GONE);
    	
    	 try{
    		 	userHabit = getData.getUserHabits(userName, selectedRitual);
    	        int habitId1 = userHabit.get(0).getHabitId();
    			tv_habit1.setText(userHabit.get(0).getHabit());
    			tv_habit1.setCompoundDrawablesWithIntrinsicBounds(AppsConstant.getHabitIcon(habitId1), 0, 0, 0);
    			
    			double status1 = successRateList.get(0).getSuccessRate();
    			tv_status1.setText(new DecimalFormat("#.##").format(status1)+"%");
    			
    			tv_habit1.setVisibility(View.VISIBLE);
    			tv_status1.setVisibility(View.VISIBLE);
    			
    			
    			int habitId2 = userHabit.get(1).getHabitId();
    			tv_habit2.setText(userHabit.get(1).getHabit());
    			tv_habit2.setCompoundDrawablesWithIntrinsicBounds(AppsConstant.getHabitIcon(habitId2), 0, 0, 0);
    			tv_habit2.setVisibility(View.VISIBLE);
    			
    			double status2 = successRateList.get(2).getSuccessRate();
    			tv_status2.setText(new DecimalFormat("#.##").format(status2)+"%");
    			
    			tv_habit2.setVisibility(View.VISIBLE);
    			tv_status2.setVisibility(View.VISIBLE);
    			
    			
    			if(userHabit.size()>3){
    			int habitId3 = userHabit.get(userHabit.size()-2).getHabitId();
    			tv_habit3.setText(userHabit.get(userHabit.size()-2).getHabit());
    			tv_habit3.setCompoundDrawablesWithIntrinsicBounds(AppsConstant.getHabitIcon(habitId3), 0, 0, 0);
    			tv_habit3.setVisibility(View.VISIBLE);
    			
    			double status3 = successRateList.get(userHabit.size()-2).getSuccessRate();
    			tv_status3.setText(new DecimalFormat("#.##").format(status3)+"%");
    			
    			tv_habit3.setVisibility(View.VISIBLE);
    			tv_status3.setVisibility(View.VISIBLE);
    			
    			
    			int habitId4 = userHabit.get(userHabit.size()-1).getHabitId();
    			tv_habit4.setText(userHabit.get(userHabit.size()-1).getHabit());
    			tv_habit4.setCompoundDrawablesWithIntrinsicBounds(AppsConstant.getHabitIcon(habitId4), 0, 0, 0);
    			tv_habit4.setVisibility(View.VISIBLE);
    			
    			double status4 = successRateList.get(userHabit.size()-2).getSuccessRate();
    			tv_status4.setText(new DecimalFormat("#.##").format(status4)+"%");
    			
    			tv_habit4.setVisibility(View.VISIBLE);
    			tv_status4.setVisibility(View.VISIBLE);
    			}
    	        }
    	        catch(Exception e){
    	        	Log.d("sunil",e.toString());
    	        }
    }
    @Override
    public void setMenuVisibility(final boolean visible) {
       if (visible) {   
          //Do your stuff here
//    	   tts = new TextToSpeech(getActivity().getApplicationContext(), new OnInitListener() {
//
//				@Override
//				public void onInit(int status) {
//					// TODO Auto-generated method stub
//					convertTextToSpeech(status, genericChitChat());
//				}
//				
//			});
       }
       super.setMenuVisibility(visible);
    }
    public String genericChitChat(){    
    	Random rn = new Random();
		int range = 5 - 1 + 1;
		int randomNum =  rn.nextInt(range) + 1;
		System.out.println(randomNum);
		switch (randomNum) {
		case 1:return "So how have you been today? Did you try anything new?";
		case 2:return "I really enjoy our meetups. You�re pretty cool.";
		case 3:return "I really need a day, between Saturday and Sunday.";
		case 4:return "You are never too old to chase a new goal, or dream a new dream.";		
		case 5:return "Don�t worry about what people think. Most of them don�t do it very often.";
		case 6:return "Do you ever go out in yesterday�s wrinkled shirt and bed head, just to run into the ONE person you didn�t want to? Yeah. Never fails!";
		case 7:return "Do you know how to teach someone patience?.... I�ll tell you later.";
		case 8:return "I was listening to the news today. They said that the universe is made up of protons, electrons and neutrons... But they forgot to say �morons�";
		case 9:return "Do you ever get the feeling you are being watched?... Because if it bothers you, I�ll stop";		
		case 10:return "Life is short, smile while you still have teeth..";
		case 11:return "Live today as if it were your last day on earth... but pay your bills in case it isn�t. ";
		default:return "Don�t worry about what people think. Most of them don�t do it very often.";			
		}
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
				 tts.setPitch(1.0f);
				 tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			 }
		 } else {
			 Log.e("error", "Initilization Failed!");
		 }
	 }
    private void setTodayTimeline()
    {
    	double status = 0.0;
    	Date d= new Date();
		String theDate = DateUtility.formateDate(d, "E MMM dd yyyy");
    	TimeLineModel timeLine = get.getTimeline(userName, selectedRitual, theDate);
        if(timeLine!=null)//Table TImeLine does not contains the row of the date
        {
        	double total = timeLine.getTotalHabit();
        	double completed = timeLine.getHabitCompleted();
        	if(total !=0)
        	{
        		status = (completed/total)*100;
        	}
        	else
        	{
        		status = 0.0;
        	}
        }
        String day = DateUtility.formateDate(d, "dd");
    	String month = DateUtility.formateDate(d, "MMM");
    	tvToday.setText(day+"\n"+month);
    	tvTodayStatus.setText(new DecimalFormat("#.#").format(status)+"%");
    }
    
    private void setRitualList() 
	{
    	progress.setProgress(50);
    	get = new GetData(getActivity());
        ritualList = get.getRitualsList(userName);
        
		ArrayAdapter<String> salonsexAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ritualList);
		salonsexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnRitual.setAdapter(salonsexAdapter);	
	}
    
    private void decorateCalendar() 
    {
    	 //Setting custom font
        /*final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Arch_Rival_Bold.ttf");
        if (null != typeface) {
            calendarView.setCustomTypeface(typeface);
            calendarView.refreshCalendar(currentCalendar);
        }*/
        
        //adding calendar day decorators
        List<DayDecorator> decorators = new ArrayList<DayDecorator>();
        decorators.add(new ColorDecorator());
        calendarView.setDecorators(decorators);
        calendarView.refreshCalendar(currentCalendar);		
	}

	private class ColorDecorator implements DayDecorator {

    	@Override
        public void decorate(DayView cell) {
            //Random rnd = new Random();
            //int color = getResources().getColor(R.color.green);
            		//Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            //cell.setBackgroundColor(color);
    		
            Date d= cell.getDate();
    		String theDate = DateUtility.formateDate(d, "E MMM dd yyyy");
            
            TimeLineModel timeLine = get.getTimeline(userName, selectedRitual, theDate);
            if(timeLine!=null)//Table TImeLine does not contains the row of the date
            {
            	double total = timeLine.getTotalHabit();
            	double completed = timeLine.getHabitCompleted();
            	double status = 0.0;
            	if(total !=0)
            		status = (completed/total)*100;
            	if(status == 100)
	            {
	            	cell.setBackgroundResource(R.drawable.circle_completed);
	            }
            	else if(status == 50)
	            {
	            	cell.setBackgroundResource(R.drawable.circle_semi_filled);
	            }
            	else if(status > 50 && status< 100)
	            {
	            	cell.setBackgroundResource(R.drawable.circle_partially_rem);
	            }
            	else if(status < 50 && status>0)
	            {
	            	cell.setBackgroundResource(R.drawable.circle_partially_filled);
	            }
            	else 
            		cell.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }
            else 
        		cell.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }
    
	private void showHabitTimeLine()
	{
		//		Intent i = new Intent(getActivity(), HabitTimeline.class);
//		i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
//		startActivity(i);
		String rout=spnRitual.getSelectedItem().toString();
		String routine="";
		switch (rout) {
			case "Morning Routine":
				routine="morning_ritual";
				break;
			case "Afternoon Routine":
				routine="afternoon_ritual";
				break;
			case "Evening Routine":
				routine="evening_ritual";
				break;

			default:
				break;
		}
		Intent i = new Intent(getActivity(), ClickOnCalendar.class);
		i.putExtra(AppsConstant.SELECTED_RITUAL, routine);
		startActivity(i);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setRitualList();
	}

	@Override
	public void onClick(View v) 
	{
		Intent i;
		switch (v.getId()) {
		case R.id.rll_timeline:
			i = new Intent(getActivity(), TodayTimeLine.class);
			i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
			startActivity(i);
			break;

		case R.id.rll_success_rate:
			i = new Intent(getActivity(), CompletionRate.class);
			i.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
			startActivity(i);
			break;
			
		case R.id.view_cal_click:
			showHabitTimeLine();
			break;
		}
		
	}
	
	public void SuccessRateMethod(){
		successRateList = new ArrayList<HabitSuccessModel>();
		userHabit = getData.getUserHabits(userName, selectedRitual);
		 Date d= new Date();
	        int date = Integer.parseInt(DateUtility.formateDate(d, "dd"));
	        date = date-1;
	        for(int i=0; i<userHabit.size(); i++)
			{
	        	double totalCount = 0.0, completed = 0.0; 
	        	for(int dateCount = date; dateCount>=0; dateCount--)
	    		{
	        		String theDate = DateUtility.getPreviousDateString(-dateCount, "E MMM dd yyyy");
	        		int added = DateUtility.compareTwoDate(userHabit.get(i).getHabitAddedON(),theDate, "E MMM dd yyyy");
	        		if(added<=0)
	        		{
	        			totalCount++;
	            		boolean isCompleted = getData.isHabitCompletedOn(userName, userHabit.get(i).getHabitId(), theDate);
	            		if(isCompleted)
	            		{
	            			completed++;
	            		}
	        		}
	        		
	    		}
	        	
	        	double status = 0.0;
	        	if(totalCount !=0)
	        		status = (completed/totalCount)*100;
	        	
	        	HabitSuccessModel successModel = new HabitSuccessModel();
	        	successModel.setHabitId(userHabit.get(i).getHabitId());
	        	successModel.setHabit(userHabit.get(i).getHabit());
	        	successModel.setSuccessRate(status);
	        	successRateList.add(successModel);
			}
	}

}