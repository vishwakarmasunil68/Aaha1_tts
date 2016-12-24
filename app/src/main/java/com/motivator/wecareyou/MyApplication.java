package com.motivator.wecareyou;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.speech.tts.TextToSpeech;
import android.support.multidex.MultiDex;
import android.util.Base64;
import android.util.Log;

import com.github.tamir7.contacts.Contacts;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Created by sunil on 05-06-2016.
 */
public class MyApplication extends Application implements TextToSpeech.OnInitListener{
    public static TextToSpeech tts;
    public static boolean tts_initialized;
    @Override
    public void onCreate() {
        super.onCreate();
        Contacts.initialize(this);
        tts = new TextToSpeech(this, this);
       printHashKey();
    }
    public void printHashKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.motivator.wecareyou",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("msg", "key:-"+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }



    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);
//            tts.setPitch(0.9f);
//            tts.setSpeechRate(0.8f);
            tts_initialized=true;
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

            }

        } else {
            tts_initialized=false;
            Log.e("TTS", "Initilization Failed!");
        }
    }
}
