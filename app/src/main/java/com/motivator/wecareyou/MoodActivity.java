package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.database.PrefData;
import com.motivator.relaxationzone.RelaxationZone;
import com.motivator.support.FileUtils;
import com.motivator.support.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoodActivity extends Activity {
    TextToSpeech tts;
    String mood = AppsConstant.user_mood;
    Button keep_me_company, leave_me_alone, skip;
    MediaPlayer player, mplayer, mpPlayer;
    boolean isSoundOn = false;
    MenuItem switchbtn;
    MenuItem switchButton;
    private final String TAG = "MoodActivity";


    List<String> list_mood_tsi = new ArrayList<>();
    List<String> list_mood_tsi_else = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        keep_me_company = (Button) findViewById(R.id.keep_me_company);
        leave_me_alone = (Button) findViewById(R.id.leave_me_alone);
        skip = (Button) findViewById(R.id.skip);
        keep_me_company.setEnabled(false);
        leave_me_alone.setEnabled(false);

        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sky_blue)));
        actionBar.setTitle("");

        SpannableString s = new SpannableString("aaha");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        actionBar.setTitle(s);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);


        list_mood_tsi.add("I'm sorry to hear that. I'll right here for you "+ PrefData.getStringPref(getApplicationContext(),PrefData.NAME_KEY)+". I'll do what I can. Why don't you relax. Maybe grab some hot tea or something, and I'll turn on some music.</item>" +
                "I get that. Take a break. Enjoy some ambiance.");
        list_mood_tsi.add("I understand the day is overwhelming right now. We will update our records later.");
        list_mood_tsi.add("Why don't we use this as an opportunity to decompress, hmm? You will feel much better about your routine if you get a minute of you-time");


        list_mood_tsi_else.add("Got it! Why don’t you rest in the garden a while? The band is doing something nice out there. No rush or anything. Let me know when you are ready to continue.");
        list_mood_tsi_else.add("I know how you feel. Those days can be frustrating.But you’ll succeed soon. Take a moment for yourself. We’ll get rolling afterward.");
        list_mood_tsi_else.add("Alright "+ PrefData.getStringPref(getApplicationContext(),PrefData.NAME_KEY)+". Let’s take a deep breath, and think over the situation. Here is a new way to help bring life back into balance.");


        GetMusicFilePath();
        playMoodBasedVoice();
        keep_me_company.setEnabled(true);
        leave_me_alone.setEnabled(true);

        leave_me_alone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (player != null) {
                    player.stop();
                }
                if (mplayer != null) {
                    mplayer.stop();
                }
                finish();
            }
        });
        keep_me_company.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setBackgroundMusic();
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

    }

    public void GetMusicFilePath() {
        Log.d(TAG, mood);
        File f;
        switch (mood) {
            case StringUtils.BORED:
                f = new File(FileUtils.BORED_FILE_PATH);
                ListFiles(f, StringUtils.BORED);
                break;
            case StringUtils.TENSED:
                f = new File(FileUtils.TENSED_FILE_PATH);
                ListFiles(f, StringUtils.TENSED);
                break;
            case StringUtils.EXCITED:
                f = new File(FileUtils.EXCITED_FILE_PATH);
                ListFiles(f, StringUtils.EXCITED);
                break;
            case StringUtils.CHEERFUL:
                f = new File(FileUtils.CHEER_FILE_PATH);
                ListFiles(f, StringUtils.CHEERFUL);
                break;
            case StringUtils.RELAXED:
                f = new File(FileUtils.RELAXED_FILE_PATH);
                ListFiles(f, StringUtils.RELAXED);
                break;
            case StringUtils.CALM:
                f = new File(FileUtils.CALM_FILE_PATH);
                ListFiles(f, StringUtils.CALM);
                break;
            case StringUtils.SAD:
                f = new File(FileUtils.SAD_FILE_PATH);
                ListFiles(f, StringUtils.SAD);
                break;
            case StringUtils.IRRITATED:
                f = new File(FileUtils.IRRITATED_FILE_PATH);
                ListFiles(f, StringUtils.IRRITATED);
                break;
            case StringUtils.NEUTRAL:
                f = new File(FileUtils.NEUTRAL_FILE_PATH);
                ListFiles(f, StringUtils.NEUTRAL);
                break;
            case StringUtils.STRESSED:
                f = new File(FileUtils.STRESSED_FILE_PATH);
                ListFiles(f, StringUtils.STRESSED);
                break;
        }
    }

    public void playMoodBasedVoice() {
        if (mood.toLowerCase().contains("tense") || mood.toLowerCase().contains("irritated") || mood.toLowerCase().contains("sad")) {
            PlayMoodVoiceFilesforTIS(list_mood_tsi);
        } else {
            playMoodVoiceForOthers(list_mood_tsi_else);

        }
    }

    // play voice for tensed,irritated,sad.
    public void PlayMoodVoiceFilesforTIS(List<String> list_files) {
        int val = Pref.getInteger(getApplicationContext(), StringUtils.TENSED_IRRITATED_SAD, -1);
        Log.d(TAG, "pref for tis:-" + val);
        if (list_files.size() > 0) {
            if ((val + 1) >= list_files.size()) {
                val = 0;
            } else {
                val = val + 1;
            }
        }
        try {
            Log.d(TAG, "final val:-" + val);

            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if (MyApplication.tts_initialized) {
                    MyApplication.tts.speak(list_files.get(val), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
            Pref.setInteger(getApplicationContext(), StringUtils.TENSED_IRRITATED_SAD, val);
            Log.d(TAG, "pref mood:-" + Pref.getInteger(getApplicationContext(), StringUtils.TENSED_IRRITATED_SAD, -1));
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }
    }

    //other than TENSED,IRRITATED,SAD
    public void playMoodVoiceForOthers(List<String> list_files) {
        int val = Pref.getInteger(getApplicationContext(), StringUtils.OTHERTHAN_TIS, -1);
        Log.d(TAG, "pref for tis:-" + val);
        if (list_files.size() > 0) {
            if ((val + 1) >= list_files.size()) {
                val = 0;
            } else {
                val = val + 1;
            }
        }
        try {
            Log.d(TAG, "final val:-" + val);
            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                if (MyApplication.tts_initialized) {
                    MyApplication.tts.speak(list_files.get(val), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
            Pref.setInteger(getApplicationContext(), StringUtils.OTHERTHAN_TIS, val);
            Log.d(TAG, "pref mood:-" + Pref.getInteger(getApplicationContext(), StringUtils.OTHERTHAN_TIS, -1));
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }
    }


    public void ListFiles(File f, String mood) {
        File[] files = f.listFiles();
        Log.d(TAG, "length:-" + files.length);
        int val = Pref.getInteger(getApplicationContext(), mood, -1);
        Log.d(TAG, "pref val:-" + val);
        if (files.length > 0) {

            if ((val + 1) >= files.length) {
                val = 0;
            } else {
                val = val + 1;
            }
        }
        try {
            Log.d(TAG, "final val:-" + val);
            File soundFile = files[val];
            mplayer = new MediaPlayer();
            mplayer.setDataSource(soundFile.toString());
            mplayer.prepare();
            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                mplayer.start();
            }
            int MAX_VOLUME = 100;
            final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
            mplayer.setVolume(volume, volume);
            Pref.setInteger(getApplicationContext(), mood, val);
            Log.d(TAG, "pref mood:-" + Pref.getInteger(getApplicationContext(), mood, -1));
        } catch (Exception e) {
            Log.d("sunil", e.toString());
        }
    }


    private void setBackgroundMusic() {
        try {
            String musicFile = "music/morning.mp3";
//			if(selectedRitual.equalsIgnoreCase(AppsConstant.MORNING_RITUAL))
//				musicFile = "music/morning.mp3";
//			else if(selectedRitual.equalsIgnoreCase(AppsConstant.LUNCH_RITUAL))
//				musicFile = "music/lunch.mp3";
//			else if(selectedRitual.equalsIgnoreCase(AppsConstant.EVENING_RITUAL))
//				musicFile = "music/evening.mp3";
            // Read the music file from the asset folder
            AssetFileDescriptor afd = getAssets().openFd(musicFile);
            //afd = getAssets().openFd(AppsConstant.getHabitMusic(habitId));
            // Creation of new media player;
            player = new MediaPlayer();
            // Set the player music source.
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            // Set the looping and play the music.
            player.setLooping(true);
            player.prepare();
//			if(isSoundOn)
//			{
            player.start();
//			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (player != null) {
            player.stop();
        }
        if (mplayer != null) {
            mplayer.stop();
        }
        if (mpPlayer != null) {
            mpPlayer.stop();
        }
        if (MyApplication.tts != null) {
            MyApplication.tts.stop();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (player != null) {
            player.stop();
        }
        if (mplayer != null) {
            mplayer.stop();
        }
        if (mpPlayer != null) {
            mpPlayer.stop();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mood_menu, menu);
        switchbtn = menu.findItem(R.id.action);
        switchButton = menu.findItem(R.id.action);
        isSoundOn = GeneralUtility.getPreferencesBoolean(MoodActivity.this, AppsConstant.AVS_SOUND);
        if (isSoundOn) {
            switchButton.setIcon(R.drawable.voice);
        } else {
            switchButton.setIcon(R.drawable.voice_mute);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.relaxation_zone:
                startActivity(new Intent(MoodActivity.this, RelaxationZone.class));
                finish();
                break;
            case R.id.action:
                if (isSoundOn) {
                    isSoundOn = false;
                    switchbtn.setIcon(R.drawable.voice_mute);

                    if (player != null)
                        player.pause();
                    if (mplayer != null) {
                        mplayer.pause();
                    }
                } else {
                    isSoundOn = true;
                    switchbtn.setIcon(R.drawable.voice);
                    if (player != null) {
                        player.start();
                    } else {
//                        setBackgroundMusic();
                    }
                    if (mplayer != null) {
                        mplayer.start();
                    }
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
