package testing;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.DeleteData;
import com.motivator.database.GetData;
import com.motivator.model.HabitModel;
import com.motivator.support.DynamicListView;
import com.motivator.wecareyou.R;

public class DragandDropList extends Activity{
	DynamicListView listview;
	ArrayList<HabitModel> userHabit = new ArrayList<HabitModel>();
	GetData getData;
	String userName, selectedRitual="Morning Routine";
	HabitAdapter habitAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_drop);
		listview=(DynamicListView) findViewById(R.id.lv_habit_frag);
		getData = new GetData(this);
//		selectedRitual = getArguments().getString(AppsConstant.SELECTED_RITUAL);
		userName = GeneralUtility.getPreferences(this, AppsConstant.user_name);
		 userHabit = getData.getUserHabits(userName, selectedRitual);
		 
		 listview.setArrayList(userHabit);
		 
		 if(userHabit!=null && userHabit.size()>0)
	        {
	        	habitAdapter = new HabitAdapter();
	        	listview.setAdapter(habitAdapter);
	        }
	        else
	        {
	        	Log.d("sunil","list is empty");
//	        	llAddFirstHabit.setVisibility(View.VISIBLE);
//	        	lvFragment.setVisibility(View.GONE);
	        }
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
			LayoutInflater inflater = getLayoutInflater();
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
					DeleteData deleteData = new DeleteData(DragandDropList.this);
					int del = deleteData.removeHabit(userHabit.get(select).getHabitId(), userName, selectedRitual);
					if(del>0)
					{
						//userHabit.get(select).setSelection(false);
						userHabit = getData.getUserHabits(userName, selectedRitual);
						mIdMap.clear();
						for (int i = 0; i < userHabit.size(); ++i) {
			    			 mIdMap.put(userHabit.get(i), i);
						}
						listview.setArrayList(userHabit);
						Toast.makeText(DragandDropList.this, "Habit Removed", 5).show();
						notifyDataSetChanged();
					}
					
				}
			});
	    	
			return convertView;
		}
    	
    }

}
