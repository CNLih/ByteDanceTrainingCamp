package com.bytedance.application.model.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "chart")
public class ChartEntity {

    @PrimaryKey
    private int data;

    public ChartEntity(){

    }

    @Ignore
    public ChartEntity(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
