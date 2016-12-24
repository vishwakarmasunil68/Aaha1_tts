package com.motivator.relaxationzone;

import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.model.RelaxationMoviePojo;
import com.motivator.wecareyou.R;

import java.util.concurrent.TimeUnit;

public class RelaxationAudioPlayer extends AppCompatActivity {
    SeekBar song_seekbar;
    Button forward, reverse;
    ImageView playpause;
    int song_number = 0;
    public static MediaPlayer mediaplayer;
    TextView played_time, total_time, song_name;
    int starttime = 0;
    public Handler myHandler = new Handler();

    public static RelaxationAudioPlayer rzap;
    private Toolbar toolbar;
    private final String TAG="relaxationaudio";
    MenuItem switchButton;
    boolean isSoundOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        rzap = this;
        setContentView(R.layout.acitivity_audio_player);
        song_seekbar = (SeekBar) findViewById(R.id.song_seekbar);
        playpause = (ImageView) findViewById(R.id.playpause);
        played_time = (TextView) findViewById(R.id.playedtime);
        total_time = (TextView) findViewById(R.id.totaltime);
        song_name = (TextView) findViewById(R.id.song_name);
        forward = (Button) findViewById(R.id.forward);
        reverse = (Button) findViewById(R.id.reverse);

        song_seekbar.setClickable(false);
        toolbar = (Toolbar) findViewById(R.id.image_toolbar);

        SpannableString s = new SpannableString("Relaxation Zone");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sky_blue)));

//        Intent intent = getIntent();
//        RelaxationMoviePojo relxRelaxationMoviePojo= (RelaxationMoviePojo) intent.getExtras().getSerializable("pojo");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            song_number=extras.getInt("song_number");
            RelaxationMoviePojo relxRelaxationMoviePojo= (RelaxationMoviePojo) extras.getSerializable("pojo");

            PlaySong(relxRelaxationMoviePojo);

        }

        playpause.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                playPauseMethod();
            }
        });

        song_seekbar.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                seekChange(v);
                return false;
            }
        });
        forward.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                nextSong();
            }
        });
        reverse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                previousSong();
            }
        });
    }

    private void seekChange(View v) {
        // if(mediaPlayer.isPlaying()){
        SeekBar sb = (SeekBar) v;
        mediaplayer.seekTo(sb.getProgress());
    }

    public void playPauseMethod() {
        if (mediaplayer != null)
            if (mediaplayer.isPlaying()) {
                mediaplayer.pause();
                playpause.setImageResource(R.drawable.play_song);
            } else {
                mediaplayer.start();
                playpause.setImageResource(R.drawable.bars);
            }
    }

    public void PlaySong(RelaxationMoviePojo pojo) {
        try {
            if (mediaplayer != null) {
                mediaplayer.stop();
                mediaplayer.reset();
            }
            song_name.setText(pojo.getMovie_name());
            mediaplayer = new MediaPlayer();
            mediaplayer.setDataSource(pojo.getMovie_url());
            mediaplayer.prepare();
            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND))
                mediaplayer.start();

            playpause.setImageResource(R.drawable.bars);
            int finalTime = mediaplayer.getDuration();
            total_time.setText(String.format("%d : %d ",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
            );
            myHandler.postDelayed(UpdateSongTime, 1000);
            mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    nextSong();
                }
            });
        }
        catch (Exception e){
            Log.d(TAG,e.toString());
        }

    }

    public void nextSong() {
        if ((song_number + 1) == RelaxationZone.relaxationMoviePojosaudios.size()) {
            song_number=0;
            RelaxationMoviePojo pojo=RelaxationZone.relaxationMoviePojosaudios.get(song_number);
            PlaySong(pojo);
        } else {
            song_number++;
            RelaxationMoviePojo pojo=RelaxationZone.relaxationMoviePojosaudios.get(song_number);
            PlaySong(pojo);
        }
    }

    public void previousSong() {
        if (song_number == 0) {
            song_number=RelaxationZone.relaxationMoviePojosaudios.size()-1;
            RelaxationMoviePojo pojo=RelaxationZone.relaxationMoviePojosaudios.get(song_number);
            PlaySong(pojo);

        } else {
            song_number--;
            RelaxationMoviePojo pojo=RelaxationZone.relaxationMoviePojosaudios.get(song_number);
            PlaySong(pojo);
        }
    }

    public Runnable UpdateSongTime = new Runnable() {
        public void run() {


            starttime = mediaplayer.getCurrentPosition();
            song_seekbar.setMax(mediaplayer.getDuration());
            song_seekbar.setProgress(mediaplayer.getCurrentPosition());
            // seekHandler.postDelayed(run, 1000);
            myHandler.postDelayed(this, 1000);
            played_time.setText(String.format("%d: %d ",

                    TimeUnit.MILLISECONDS.toMinutes((long) starttime),
                    TimeUnit.MILLISECONDS.toSeconds((long) starttime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) starttime)))
            );

        }
    };
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_relaxation_audio, menu);
//        switchButton = menu.findItem(R.id.action);
//        isSoundOn = GeneralUtility.getPreferencesBoolean(RelaxationAudioPlayer.this, AppsConstant.AVS_SOUND);
//        if(isSoundOn)
//        {
//            switchButton.setIcon(R.drawable.voice);
//        }
//        else
//        {
//            switchButton.setIcon(R.drawable.voice_mute);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//            case R.id.action:
//                if (isSoundOn) {
//                    isSoundOn = false;
//                    switchButton.setIcon(R.drawable.voice_mute);
//
////                    if (mediaplayer != null)
////                        mediaplayer.pause();
////                    int MAX_VOLUME = 100;
////                    final float volume = (float) (1 - (Math.log(MAX_VOLUME) / Math.log(MAX_VOLUME)));
////                    mediaplayer.setVolume(volume, volume);
//                } else {
//                    isSoundOn = true;
//                    switchButton.setIcon(R.drawable.voice);
////                    if (mediaplayer != null) {
////                        mediaplayer.start();
////                        int MAX_VOLUME = 100;
////                        final float volume = (float) (1 - (Math.log(MAX_VOLUME-100) / Math.log(MAX_VOLUME)));
////                        mediaplayer.setVolume(volume, volume);
//
//                }
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
