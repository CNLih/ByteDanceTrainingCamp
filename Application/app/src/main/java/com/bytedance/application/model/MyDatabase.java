package com.bytedance.application.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bytedance.application.model.dao.ChartDao;
import com.bytedance.application.model.dao.DataDao;
import com.bytedance.application.model.entity.ChartEntity;
import com.bytedance.application.model.entity.DataEntity;

//Schema export directory is not provided to the annotation processor...
@Database(entities = {
    ChartEntity.class, DataEntity.class
}, version = 3, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ChartDao chartDao();
    public abstract DataDao dataDao();
}
