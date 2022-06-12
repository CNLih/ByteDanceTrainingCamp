package com.bytedance.application.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bytedance.application.model.Dao.ChartDao;
import com.bytedance.application.model.entity.ChartEntity;

@Database(entities = {
    ChartEntity.class
}, version = 2)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ChartDao chartDao();
}
