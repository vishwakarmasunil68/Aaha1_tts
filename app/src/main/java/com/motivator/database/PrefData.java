package com.motivator.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sunil on 17-09-2016.
 */
public class PrefData {
    private static final String DB_NAME="aahadata.txt";
    public static final String NAME_KEY="name";
    public static final String AGE_KEY="age";
    public static final String HEALTH_KEY="health";
    public static final String GENDER_KEY="gender";
    public static final String EMAIL_KEY="email";
    public static final String USER_ID="userid";
    public static final String WALKTHROUGH_FIRST_HABITLIST="firstwalkthroughhabitlist";


    public static void setStringPref(Context context, String key, String value){
        SharedPreferences sp=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getStringPref(Context context, String key){
        SharedPreferences sp=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }

    public static void setBooleanPref(Context context, String key, boolean value){
        SharedPreferences sp=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static boolean getBooleanPref(Context context, String key,boolean defValue){
        SharedPreferences sp=context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }
}
