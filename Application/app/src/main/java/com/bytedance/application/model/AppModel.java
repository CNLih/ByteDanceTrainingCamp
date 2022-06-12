package com.bytedance.application.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.bytedance.application.model.entity.ChartEntity;

public class AppModel {
    private Context mContext;
    
    private static AppModel mInstance;

    public static AppModel getInstance(){
        if(mInstance == null){
            synchronized (AppModel.class){
                if(mInstance == null){
                    mInstance = new AppModel();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context){
        this.mContext = context;
        PreferenceHelper.init(context);
        DBHelper.init(context);
    }

    public String getLocation(){
        return PreferenceHelper.getInstance().getLocation();
    }

    public void setLocation(String location){
        PreferenceHelper.getInstance().setLocation(location);
    }

    public int getData(){
        return DBHelper.getInstance().getChartDao().loadChart().get(0).getData();
    }

    public void setData(ChartEntity entity){
        DBHelper.getInstance().getChartDao().insert(entity);
    }
}
