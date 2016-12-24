package com.motivator.relaxationzone;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.motivator.wecareyou.R;

public class RelaxationZoneVideoPlayer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_relaxation_zone_video_player);

        Bundle extras=getIntent().getExtras();
        if(extras!=null){

            String url=extras.getString("url");

            VideoView videoView =(VideoView)findViewById(R.id.videoView1);

            //Creating MediaController
            MediaController mediaController= new MediaController(this);
            mediaController.setAnchorView(videoView);

            //specify the location of media file
            Uri uri=Uri.parse(url);

            //Setting MediaController and URI, then starting the videoView
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);
            videoView.requestFocus();
            videoView.start();

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    finish();
                }
            });
        }
    }
}
