package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.database.NewDataBaseHelper;
import com.motivator.model.DataEntryPOJO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClickOnCalendarItem extends Activity {
    TextView routine_name,tv_before_breakfast_today,tv_before_breakfast_yesterday,tv_before_breakfast_last_week
            ,tv_after_breakfast_today,tv_after_breakfast_yesterday,tv_after_breakfast_last_week
            ,tv_before_lunch_today,tv_before_lunch_yesterday,tv_before_lunch_last_week
            ,tv_after_lunch_today,tv_after_lunch_yesterday,tv_after_lunch_last_week
            ,tv_before_dinner_today,tv_before_dinner_yesterday,tv_before_dinner_last_week
            ,tv_after_dinner_today,tv_after_dinner_yesterday,tv_after_dinner_last_week;
    LinearLayout ritual_layout;
    NewDataBaseHelper helper;
    String ritual="";

    final String ON="on";
    final String OFF="off";

    String breakfast_status=OFF;
    String lunch_status=ON;
    String dinner_status=ON;

    LinearLayout break_fast_ll,break_fast_data_ll,lunch_ll,lunch_data_ll,dinner_ll,dinner_data_ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_on_calendar_item);
        routine_name= (TextView) findViewById(R.id.routine_name);
        ritual_layout= (LinearLayout) findViewById(R.id.ritual_layout);
        break_fast_ll= (LinearLayout) findViewById(R.id.break_fast_ll);
        break_fast_data_ll= (LinearLayout) findViewById(R.id.break_fast_data_ll);
        lunch_data_ll= (LinearLayout) findViewById(R.id.lunch_data_ll);
        lunch_ll= (LinearLayout) findViewById(R.id.lunch_ll);
        dinner_data_ll= (LinearLayout) findViewById(R.id.dinner_data_ll);
        dinner_ll= (LinearLayout) findViewById(R.id.dinner_ll);

        tv_before_breakfast_today= (TextView) findViewById(R.id.tv_before_breakfast_today);
        tv_before_breakfast_yesterday= (TextView) findViewById(R.id.tv_before_breakfast_yesterday);
        tv_before_breakfast_last_week= (TextView) findViewById(R.id.tv_before_breakfast_last_week);
        tv_after_breakfast_today= (TextView) findViewById(R.id.tv_after_breakfast_today);
        tv_after_breakfast_yesterday= (TextView) findViewById(R.id.tv_after_breakfast_yesterday);
        tv_after_breakfast_last_week= (TextView) findViewById(R.id.tv_after_breakfast_last_week);
        tv_before_lunch_today= (TextView) findViewById(R.id.tv_before_lunch_today);
        tv_before_lunch_yesterday= (TextView) findViewById(R.id.tv_before_lunch_yesterday);
        tv_before_lunch_last_week= (TextView) findViewById(R.id.tv_before_lunch_last_week);
        tv_after_lunch_today= (TextView) findViewById(R.id.tv_after_lunch_today);
        tv_after_lunch_yesterday= (TextView) findViewById(R.id.tv_after_lunch_yesterday);
        tv_after_lunch_last_week= (TextView) findViewById(R.id.tv_after_lunch_last_week);
        tv_before_dinner_today= (TextView) findViewById(R.id.tv_before_dinner_today);
        tv_before_dinner_yesterday= (TextView) findViewById(R.id.tv_before_dinner_yesterday);
        tv_before_dinner_last_week= (TextView) findViewById(R.id.tv_before_dinner_last_week);
        tv_after_dinner_today= (TextView) findViewById(R.id.tv_after_dinner_today);
        tv_after_dinner_yesterday= (TextView) findViewById(R.id.tv_after_dinner_yesterday);
        tv_after_dinner_last_week= (TextView) findViewById(R.id.tv_after_dinner_last_week);




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

        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            ritual=extras.getString(AppsConstant.SELECTED_RITUAL);
            Log.d("sunil","ritual:-"+ritual);
            switch (ritual) {
                case "morning_ritual":ritual_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.morning_routine));
                    routine_name.setText("Morning Ritual");

                    break;
                case "afternoon_ritual":ritual_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.afternoon_routine));
                    routine_name.setText("Afternoon Ritual");

                    break;

                case "evening_ritual":ritual_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.night_routine));
                    routine_name.setText("Evening Ritual");

                    break;

                default:
                    ritual_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.morning_routine));

                    break;
            }
        }

        List<DataEntryPOJO> pojo=helper.getAllData();

//        List<DataEntryPOJO> lst_ritual=new ArrayList<>();
//        for(DataEntryPOJO p:pojo){
//            if(ritual.equals(p.getRitual())){
//                lst_ritual.add(p);
//            }
//        }

        String today_date=getTodayDateString();
        String yesterday_date=getYesterdayDateString();
        for(DataEntryPOJO dep:pojo){
            Log.d("sunil","lst_string:-"+dep.toString());
            if(dep.getDate().equals(yesterday_date)){
                switch (dep.getGlucose_time()){
                    case "Before Breakfast":
                        tv_before_breakfast_yesterday.setText(dep.getGlucose_no()+"");
                        break;
                    case "After Breakfast":
                        tv_after_breakfast_yesterday.setText(dep.getGlucose_no()+"");
                        break;
                    case "Before Lunch":
                        tv_before_lunch_yesterday.setText(dep.getGlucose_no()+"");
                        break;
                    case "After Lunch":
                        tv_after_lunch_yesterday.setText(dep.getGlucose_no()+"");
                        break;
                    case "Before Dinner":
                        tv_before_dinner_yesterday.setText(dep.getGlucose_no()+"");
                        break;
                    case "After Dinner":
                        tv_after_dinner_yesterday.setText(dep.getGlucose_no()+"");
                        break;
                }

            }
            if(dep.getDate().equals(today_date)){
                switch (dep.getGlucose_time()){
                    case "Before Breakfast":
                        tv_before_breakfast_today.setText(dep.getGlucose_no()+"");
                        break;
                    case "After Breakfast":
                        tv_after_breakfast_today.setText(dep.getGlucose_no()+"");
                        break;
                    case "Before Lunch":
                        tv_before_lunch_today.setText(dep.getGlucose_no()+"");
                        break;
                    case "After Lunch":
                        tv_after_lunch_today.setText(dep.getGlucose_no()+"");
                        break;
                    case "Before Dinner":
                        tv_before_dinner_today.setText(dep.getGlucose_no()+"");
                        break;
                    case "After Dinner":
                        tv_after_dinner_today.setText(dep.getGlucose_no()+"");
                        break;
                }
            }
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

    @Override
    protected void onStart() {
        super.onStart();
        SetBreakFastLL();
        SetLunchLL();
        SetDinnerLL();
        break_fast_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetBreakFastLL();
            }
        });
        lunch_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetLunchLL();
            }
        });

        dinner_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDinnerLL();
            }
        });

    }
    public void SetBreakFastLL(){
        switch (breakfast_status){
            case ON:breakfast_status=OFF;
                    break_fast_ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.non_selected_tab));
                break_fast_data_ll.setVisibility(View.GONE);
                break;
            case OFF:
                breakfast_status=ON;
                break_fast_ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_tab));
                break_fast_data_ll.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void SetLunchLL(){
        switch (lunch_status){
            case ON:lunch_status=OFF;
                lunch_ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.non_selected_tab));
                lunch_data_ll.setVisibility(View.GONE);
                break;
            case OFF:
                lunch_status=ON;
                lunch_ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_tab));
                lunch_data_ll.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void SetDinnerLL(){
        switch (dinner_status){
            case ON:dinner_status=OFF;
                dinner_ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.non_selected_tab));
                dinner_data_ll.setVisibility(View.GONE);
                break;
            case OFF:
                dinner_status=ON;
                dinner_ll.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_tab));
                dinner_data_ll.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
