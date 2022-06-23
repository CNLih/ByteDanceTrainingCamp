package com.bytedance.application.model;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    public static final String PREFERENCE_NAME = "saveInfo";
    private static PreferenceHelper preferenceHelper;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String SHARED_KEY_LOCATION = "shared_key_setting_location";

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
}
