package com.motivator.wecareyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.database.UpdateData;

public class RitualImageUpdate extends Activity {
	
	int selectedImg = -1;
	GridView gvRitualImg;
	String userName, selectedRitual, updatedName;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        
        userName = GeneralUtility.getPreferences(RitualImageUpdate.this, AppsConstant.user_name);
        selectedRitual = getIntent().getExtras().getString(AppsConstant.SELECTED_RITUAL);
        updatedName = getIntent().getExtras().getString("new_name");
        
        setContentView(R.layout.ritual_grid);
        gvRitualImg = (GridView)findViewById(R.id.gv_img);
        gvRitualImg.setAdapter(new GridAdapter());
        
        TextView tvRitualName = (TextView)findViewById(R.id.tv_ritual_name);
        TextView tvOk = (TextView)findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView)findViewById(R.id.tv_cancel);
        
        tvRitualName.setText(selectedRitual);
        
        tvOk.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) 
			{
				if(updatedName!=null && updatedName.length()>0)
				{
					UpdateData update = new UpdateData(RitualImageUpdate.this);
					int row = update.updateUserRitualName(userName, selectedRitual, updatedName, selectedImg+1);
					
					Intent i = new Intent();
					i.putExtra("img_num", selectedImg+1);
					setResult(AppsConstant.RITUAL_IMG,i); 
				}
				finish();				
			}
		});
        
        tvCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				finish();				
			}
		});
        
       /* gvRitualImg.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
            {
            	selectedImg = position;
            }
        });*/
        
	}

	public class GridAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return img.length;
		}
	 
		@Override
		public Object getItem(int position) {
			return position;
		}
	 
		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
	 
			LayoutInflater inflater = getLayoutInflater();
			convertView = inflater.inflate(R.layout.ritual_grid_cell, null);
			
			final ImageView imvRitualImg = (ImageView)convertView.findViewById(R.id.imv_img);
			final ImageView imvSelected = (ImageView)convertView.findViewById(R.id.imv_selected);
	    	
			imvRitualImg.setImageResource(img[position]);
			
			if(position == selectedImg){
				imvRitualImg.setBackgroundColor(getResources().getColor(R.color.gray_medium));
				imvSelected.setVisibility(View.VISIBLE);
		    } else {
				imvRitualImg.setBackgroundColor(getResources().getColor(R.color.white));
				imvSelected.setVisibility(View.GONE);
		    }
			
			convertView.setTag(position);
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					selectedImg = (Integer) v.getTag();
					notifyDataSetChanged();
				}
			});

			return convertView;
		}
		
		int img[] = {R.drawable.ritual_top, R.drawable.ritual_top2, R.drawable.ritual_top3,
				R.drawable.ritual_top4, R.drawable.ritual_top5, R.drawable.ritual_top6};
	}

}