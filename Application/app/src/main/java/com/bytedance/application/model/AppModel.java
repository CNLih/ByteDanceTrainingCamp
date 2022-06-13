package com.bytedance.application.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.bytedance.application.model.entity.ChartEntity;
import com.bytedance.application.model.entity.DataEntity;

import java.util.List;

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

    public List<DataEntity> getData(){
        return DBHelper.getInstance().getDataDao().getAllData();
    }

    public void setData(List<DataEntity> list){
        DBHelper.getInstance().getDataDao().insertAll(list);
    }


}
