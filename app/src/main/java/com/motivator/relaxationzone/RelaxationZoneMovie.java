package com.motivator.relaxationzone;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.motivator.model.RelaxationMoviePojo;
import com.motivator.wecareyou.R;

import java.util.ArrayList;

public class RelaxationZoneMovie extends Fragment{
	GridView gridView;
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.frag_relax_movie, container,false);
	}
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		gridView=(GridView) view.findViewById(R.id.gridView);
		GridViewAdapter adapter=new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData());
		gridView.setAdapter(adapter);
	}
	
	private ArrayList<RelaxationMoviePojo> getData() {
		ArrayList<RelaxationMoviePojo> imageItems=new ArrayList<>();
        for(int i=0;i<9;i++){
        	RelaxationMoviePojo moviePojo=new RelaxationMoviePojo();
        	moviePojo.setImage(R.drawable.ic_launcher);
        	moviePojo.setMovie_name("movie:"+i);
        	imageItems.add(moviePojo);
        }
        
        return imageItems;
    }
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}
	class GridViewAdapter extends ArrayAdapter<RelaxationMoviePojo> {
        private Context context;
        private int layoutResourceId;
        private ArrayList<RelaxationMoviePojo> data=new ArrayList<>();

        public GridViewAdapter(Context context, int layoutResourceId, ArrayList<RelaxationMoviePojo> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) row.findViewById(R.id.image);
                holder.movie_name = (TextView) row.findViewById(R.id.movie_name);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }

            holder.image.setImageResource(data.get(position).getImage());
            holder.movie_name.setText(data.get(position).getMovie_name());
            return row;
        }

        class ViewHolder {
            ImageView image;
            TextView movie_name;
        }
    }
}
