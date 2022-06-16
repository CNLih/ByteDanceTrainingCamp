package com.bytedance.application.model.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bytedance.application.bean.DataBean;

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

    public DataEntity(DataBean dataBean){
        this.name = dataBean.getArea();
        this.confirm = dataBean.getConfirm();
        this.suspect = dataBean.getSuspect();
        this.cure = dataBean.getCure();
        this.dead = dataBean.getDead();
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
