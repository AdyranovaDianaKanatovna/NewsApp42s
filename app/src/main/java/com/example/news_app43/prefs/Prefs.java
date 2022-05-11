package com.example.news_app43.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.example.news_app43.R;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }
    public void saveState(){
     preferences.edit().putBoolean("isShown",true).apply();
    }
    public boolean isShown(){
        return preferences.getBoolean("isShown",false);
    }
    public String getText(){
        return preferences.getString("key","");
    }
    public void saveEditText(String all){
        preferences.edit().putString("key",all).apply();
    }
    public void savePicture(String al){
        preferences.edit().putString("ke",al).apply();
    }
    public String getPic(){
        Uri sa  = Uri.parse(("android.resource://com.example.news_app43/" + R.drawable.moon));
        return preferences.getString("ke",sa.toString());
    }
    public void clearPreferences(){
        preferences.edit().remove("ke").apply();
        preferences.edit().remove("key").apply();
    }
}
