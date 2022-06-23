package com.bytedance.application.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bytedance.application.bean.StatisticsBean;

@Entity(tableName = "data")
public class DataEntity {

    //must annotate primary keys with @NonNull.
    @NonNull
    @PrimaryKey
    private String name;

    private int confirm;
    private int suspect;
    private int cure;
    private int dead;

    public DataEntity(){
    }

    public DataEntity(StatisticsBean statisticsBean){
        this.name = statisticsBean.getArea();
        this.confirm = statisticsBean.getConfirm();
        this.suspect = statisticsBean.getSuspect();
        this.cure = statisticsBean.getCure();
        this.dead = statisticsBean.getDead();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConfirm() {
        return confirm;
    }

    public void setConfirm(int confirm) {
        this.confirm = confirm;
    }

    public int getSuspect() {
        return suspect;
    }

    public void setSuspect(int suspect) {
        this.suspect = suspect;
    }

    public int getCure() {
        return cure;
    }

    public void setCure(int cure) {
        this.cure = cure;
    }

    public int getDead() {
        return dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }
}
