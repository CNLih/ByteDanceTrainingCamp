package com.bytedance.application.model;

import android.content.Context;

import androidx.room.Room;

import com.bytedance.application.model.dao.ChartDao;
import com.bytedance.application.model.dao.DataDao;

public class DBHelper {
    private final static String DB_NAME = "nothing.db";

    private static MyDatabase mDatabase;
    public static DBHelper instance;

    public static synchronized void init(Context context){
        if(instance == null){
            mDatabase = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    public static DBHelper getInstance() {
        if(instance == null) {
            synchronized (DBHelper.class) {
                if(instance == null) {
                    instance = new DBHelper();
                }
            }
        }
        return instance;
    }

    public void closeDb() {
        if(mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    public ChartDao getChartDao(){
        if(mDatabase != null){
            return mDatabase.chartDao();
        }
        return null;
    }

    public DataDao getDataDao(){
        if(mDatabase != null){
            return mDatabase.dataDao();
        }
        return null;
    }
}
