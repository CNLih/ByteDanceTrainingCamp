package com.bytedance.application.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bytedance.application.model.entity.CodeEntity;

import java.util.List;

@Dao
public interface CodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CodeEntity codeEntity);

    @Delete
    void delete(CodeEntity codeEntity);

    @Query("select * from qrcode")
    List<CodeEntity> getAllCode();
}
