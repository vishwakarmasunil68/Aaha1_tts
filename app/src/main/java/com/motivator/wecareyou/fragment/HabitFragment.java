package com.motivator.wecareyou.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.DeleteData;
import com.motivator.database.GetData;
import com.motivator.model.HabitModel;
import com.motivator.wecareyou.HabitList;
import com.motivator.wecareyou.R;
import com.motivator.wecareyou.SetHabitTime;

import java.util.ArrayList;
import java.util.HashMap;

public class HabitFragment extends Fragment implements OnClickListener{
	
	
	Context mContext;
	LinearLayout llAddFirstHabit;
	Button btnAddHabit1;
	com.motivator.support.DynamicListView lvFragment;
	ColorDrawable greyBox;
	
	HabitAdapter habitAdapter;
	String userName, selectedRitual, ritualTime;
	GetData getData;
	ArrayList<HabitModel> userHabit = new ArrayList<HabitModel>();
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	
    	mContext = getActivity();
    	getData = new GetData(getActivity());
    	//getDAta from shared prefrence
        userName = GeneralUtility.getPreferences(getActivity(), AppsConstant.user_name);
        
    	selectedRitual = getArguments().getString(AppsConstant.SELECTED_RITUAL);
    	ritualTime = getArguments().getString(AppsConstant.RITUAL_TIME);
        // Inflate the layout for this fragment
    	View view = inflater.inflate(R.layout.frag_habit_list, container, false);

    	lvFragment = (com.motivator.support.DynamicListView)view.findViewById(R.id.lv_habit_frag);
    	llAddFirstHabit = (LinearLayout)view.findViewById(R.id.ll_add_first_habit_alarm);
        btnAddHabit1 = (Button)view.findViewById(R.id.btn_add_habit1_setting);


        btnAddHabit1.setOnClickListener(this);

    	// Get User Selected Habits from Databse
        userHabit = getData.getUserHabits(userName, selectedRitual);

        lvFragment.setArrayList(userHabit);
        if(userHabit!=null && userHabit.size()>0)
        {
        	habitAdapter = new HabitAdapter();
        	lvFragment.setAdapter(habitAdapter);
        }
        else
        {
        	llAddFirstHabit.setVisibility(View.VISIBLE);
        	lvFragment.setVisibility(View.GONE);
        }

        lvFragment.setOnItemClickListener(new OnItemClickListener()
        {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,int position, long id) {

				Intent i = new Intent(getActivity(), SetHabitTime.class);
				i.putExtra("is_new_habit", false);
				i.putExtra("h_id", userHabit.get(position).getHabitId());
				i.putExtra("habit", userHabit.get(position).getHabit());
				i.putExtra("time", userHabit.get(position).getHabitTime());
				startActivity(i);
			}

		});

    	return view;
    }


    public class HabitAdapter extends BaseAdapter
    {
    	 final int INVALID_ID = -1;
    	 HashMap<HabitModel, Integer> mIdMap = new HashMap<HabitModel, Integer>();

    	 public HabitAdapter()
    	 {
    		 //super(context, textViewResourceId, objects);
    		 for (int i = 0; i < userHabit.size(); ++i) {
    			 mIdMap.put(userHabit.get(i), i);
    		 }
    	 }
		@Override
		public int getCount() {
			return userHabit.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public long getItemId(int position) {
			if (position < 0 || position >= mIdMap.size()) {
	            return INVALID_ID;
	        }
			HabitModel habit = userHabit.get(position);
	        return mIdMap.get(habit);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = getActivity().getLayoutInflater();
			convertView = inflater.inflate(R.layout.habit_frag, null);

			TextView tvHabit;
			ImageView imvHabitAlarm, imvHabitremove;

			tvHabit = (TextView)convertView.findViewById(R.id.tv_habit_setting);
	    	imvHabitAlarm = (ImageView)convertView.findViewById(R.id.imv_habit_setting);
	    	imvHabitremove = (ImageView)convertView.findViewById(R.id.imv_habit_remove);

	    	tvHabit.setText(userHabit.get(position).getHabit()/*+ " Priority "+userHabit.get(position).getHabitPriority()*/);

	    	imvHabitAlarm.setTag(position);
	    	imvHabitremove.setTag(position);

	    	imvHabitremove.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v)
				{
					int select = (Integer) v.getTag();
					DeleteData deleteData = new DeleteData(getActivity());
					int del = deleteData.removeHabit(userHabit.get(select).getHabitId(), userName, selectedRitual);
					if(del>0)
					{
						//userHabit.get(select).setSelection(false);
						userHabit = getData.getUserHabits(userName, selectedRitual);
						mIdMap.clear();
						for (int i = 0; i < userHabit.size(); ++i) {
			    			 mIdMap.put(userHabit.get(i), i);
						}
						lvFragment.setArrayList(userHabit);
						Toast.makeText(getActivity(), "Habit Removed", 5).show();
						notifyDataSetChanged();
					}

				}
			});

			return convertView;
		}

    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_habit1_setting:
			Intent intent = new Intent(getActivity(), HabitList.class);
			intent.putExtra(AppsConstant.SELECTED_RITUAL, selectedRitual);
			intent.putExtra(AppsConstant.RITUAL_TIME, ritualTime);
			startActivity(intent);
			break;
		}

	}
    
}