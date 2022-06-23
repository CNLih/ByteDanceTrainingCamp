package com.bytedance.application.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedance.application.model.entity.DataEntity;

import java.util.List;

@Dao
public interface DataDao {
    //重复主键策略：替换
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DataEntity> list);

    @Query("select * from data")
    List<DataEntity> getAllData();
}
