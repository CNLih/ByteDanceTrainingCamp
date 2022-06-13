package com.bytedance.application.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.bytedance.application.model.entity.ChartEntity;

import java.util.List;

@Dao
public interface ChartDao {
    @Insert
    void insert(ChartEntity chartEntity);

    @Query("select * from chart")
    List<ChartEntity> loadChart();

}
