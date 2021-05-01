package com.dreamernguyen.AppRaoVatSaFaCo;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigSharedPreferences {
    private static final String MY_PREF_ = "MY_PREF_";
    private Context context;

    public ConfigSharedPreferences(Context context) {
        this.context = context;
    }

    public void putBooleanValue(String key, boolean value){
        SharedPreferences sharedPreferences= context.getSharedPreferences(MY_PREF_, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences= context.getSharedPreferences(MY_PREF_, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }

    public void putStringValue(String key, String value){
        SharedPreferences sharedPreferences= context.getSharedPreferences(MY_PREF_, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String getStringValue(String key){
        SharedPreferences sharedPreferences= context.getSharedPreferences(MY_PREF_, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
