package com.bytedance.application.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.bytedance.application.AppUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PreferenceHelper {
    public static final String PREFERENCE_NAME = "saveInfo";
    private static PreferenceHelper preferenceHelper;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String SHARED_KEY_LOCATION = "shared_key_setting_location";
    public static final String SHARED_KEY_WIDGETS = "widgets";

    public PreferenceHelper(Context cxt) {
        sharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public synchronized static PreferenceHelper getInstance() {
        if (preferenceHelper == null) {
            throw new RuntimeException("please init first!");
        }
        return preferenceHelper;
    }

    public static synchronized void init(Context cxt){
        if(preferenceHelper == null){
            preferenceHelper = new PreferenceHelper(cxt);
        }
    }

    public String getLocation(){
        return sharedPreferences.getString(SHARED_KEY_LOCATION, "未设置");
    }

    public void setLocation(String location){
        editor.putString(SHARED_KEY_LOCATION,  location);
        editor.apply();
    }

    public int[] getWidgets(){
        String[] strings = sharedPreferences.getStringSet(SHARED_KEY_WIDGETS, new HashSet<>()).toArray(new String[0]);
        int[] ids = new int[strings.length];
        for(int i = 0; i < strings.length; i ++){
            ids[i] = Integer.parseInt(strings[i]);
        }
        return ids;
    }

    public void setWidgets(int[] widgets){
        Set<String> set = new HashSet<>();
        for(int widget : widgets){
            set.add(String.valueOf(widget));
        }
        editor.putStringSet(SHARED_KEY_WIDGETS, set);
        editor.apply();
    }
}
