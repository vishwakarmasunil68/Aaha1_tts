package com.motivator.relaxationzone;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.model.RelaxationMoviePojo;
import com.motivator.support.FileUtils;
import com.motivator.wecareyou.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RelaxationZone extends Activity {
    GridView gridView_audio, gridView_video;
    Button audio_frag, video_frag;
    ImageView header_image;
    ArrayList<RelaxationMoviePojo> imageItems;
    ProgressDialog progressDialog;
    public static List<RelaxationMoviePojo> relaxationMoviePojosaudios,relaxationMovievideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_relaxation_home);

        SpannableString s = new SpannableString("Relaxation Zone");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sky_blue)/*Color.parseColor("#330000ff")*/));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("Relaxation Zone");
        actionBar.setTitle(s);
        initViews();

    }

    public void initViews() {
        audio_frag = (Button) findViewById(R.id.audio_btn);
        video_frag = (Button) findViewById(R.id.video_btn);
        gridView_audio = (GridView) findViewById(R.id.gridView_audio);
        gridView_video = (GridView) findViewById(R.id.gridView_video);
        header_image = (ImageView) findViewById(R.id.header_image);

        header_image.setScaleType(ScaleType.FIT_XY);


        relaxationMoviePojosaudios=getAudioData(new File(FileUtils.RELAXATION_ZONE_AUDIO_FILE_PATH));
        GridViewAdapter adapter1 = new GridViewAdapter(RelaxationZone.this, R.layout.grid_item_layout, relaxationMoviePojosaudios);
        gridView_audio.setAdapter(adapter1);

        relaxationMovievideos=getAudioData(new File(FileUtils.RELAXATION_ZONE_VIDEO_FILE_PATH));
        GridViewAdapter adapter = new GridViewAdapter(RelaxationZone.this, R.layout.grid_item_layout, relaxationMovievideos);
        gridView_video.setAdapter(adapter);




        gridView_audio.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                try {
                    RelaxationAudioPlayer.rzap.finish();
                } catch (Exception e) {

                }
                try {
                    RelaxationAudioPlayer.mediaplayer.stop();
                } catch (Exception e) {

                }
                Intent intent = new Intent(RelaxationZone.this, RelaxationAudioPlayer.class);
                intent.putExtra("pojo", relaxationMoviePojosaudios.get(position));
                intent.putExtra("song_number",position);
                startActivity(intent);
            }
        });
        gridView_video.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(RelaxationZone.this,RelaxationZoneVideoPlayer.class);
                intent.putExtra("url",relaxationMovievideos.get(position).getMovie_url());
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.relaxation_menu, menu);
        return true;
    }


    private ArrayList<RelaxationMoviePojo> getAudioData(File f) {
        File[] list_audios=f.listFiles();
        ArrayList<RelaxationMoviePojo> imageItems = new ArrayList<>();
        for (int i = 0; i < list_audios.length; i++) {
            File f1=list_audios[i];
            RelaxationMoviePojo moviePojo = new RelaxationMoviePojo();
            moviePojo.setImage(R.drawable.movie_back1);
            moviePojo.setMovie_name(f1.getName());
            moviePojo.setMovie_url(f1.toString());
            imageItems.add(moviePojo);
        }

        return imageItems;
    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        gridView_audio.setVisibility(View.VISIBLE);
        gridView_video.setVisibility(View.GONE);
        audio_frag.setBackgroundColor(Color.parseColor("#26AAE0"));
        video_frag.setBackgroundColor(Color.parseColor("#000000"));
        audio_frag.setTextColor(Color.WHITE);
        video_frag.setTextColor(Color.WHITE);
        audio_frag.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                gridView_audio.setVisibility(View.VISIBLE);
                gridView_video.setVisibility(View.GONE);
//				audio_frag.setBackgroundResource(R.drawable.selected_btn);
//				video_frag.setBackgroundResource(R.drawable.black_button);
                audio_frag.setBackgroundColor(Color.parseColor("#26AAE0"));
                video_frag.setBackgroundColor(Color.parseColor("#000000"));
                audio_frag.setTextColor(Color.WHITE);
                video_frag.setTextColor(Color.WHITE);
            }
        });
        video_frag.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                gridView_audio.setVisibility(View.GONE);
                gridView_video.setVisibility(View.VISIBLE);
//				audio_frag.setBackgroundResource(R.drawable.black_button);
//				video_frag.setBackgroundResource(R.drawable.selected_btn);
                audio_frag.setBackgroundColor(Color.parseColor("#000000"));
                video_frag.setBackgroundColor(Color.parseColor("#26AAE0"));
                audio_frag.setTextColor(Color.WHITE);
                video_frag.setTextColor(Color.WHITE);
            }
        });
    }


    class GridViewAdapter extends ArrayAdapter<RelaxationMoviePojo> {
        private Context context;
        private int layoutResourceId;
        private List<RelaxationMoviePojo> data = new ArrayList<>();

        public GridViewAdapter(Context context, int layoutResourceId, List<RelaxationMoviePojo> data) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.headphone:
                Toast.makeText(getApplicationContext(), "Please wear your headphones", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{
            if(RelaxationAudioPlayer.mediaplayer!=null){
                RelaxationAudioPlayer.mediaplayer.stop();
            }
        }
        catch (Exception e){

        }
    }
}
