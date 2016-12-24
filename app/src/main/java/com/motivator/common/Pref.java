package com.motivator.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sunil on 08-11-2016.
 */
public class Pref {

    public static final String MOOD_TENSED="tense";
    public static final String MOOD_EXCITED="excited";
    public static final String MOOD_CHEERFUL="cheerful";
    public static final String MOOD_RELAXED="relaxed";
    public static final String MOOD_CALM="calm";
    public static final String MOOD_BORED="bored";
    public static final String MOOD_SAD="sad";
    public static final String MOOD_IRRITATED="irritated";
    public static final String MOOD_NEUTRAL="neutral";
    public static final String MOOD_STRESSED="stressed";



    public static void setString(Context context,String KEY, String VALUE){
        SharedPreferences sharedPreferences=context.getSharedPreferences("aaha.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY,VALUE);
        editor.commit();
    }

    public static String getString(Context context,String KEY,String DefVALUE){
        SharedPreferences sharedPreferences=context.getSharedPreferences("aaha.txt",Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY,DefVALUE);
    }

    public static void setInteger(Context context,String KEY, int VALUE){
        SharedPreferences sharedPreferences=context.getSharedPreferences("aaha.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY,VALUE);
        editor.commit();
    }

    public static int getInteger(Context context,String KEY,int DefVALUE){
        SharedPreferences sharedPreferences=context.getSharedPreferences("aaha.txt",Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY,DefVALUE);
    }

    public static void setBoolean(Context context,String KEY, boolean VALUE){
        SharedPreferences sharedPreferences=context.getSharedPreferences("aaha.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(KEY,VALUE);
        editor.commit();
    }

    public static boolean getBoolean(Context context,String KEY,boolean DefVALUE){
        SharedPreferences sharedPreferences=context.getSharedPreferences("aaha.txt",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY,DefVALUE);
    }
}
