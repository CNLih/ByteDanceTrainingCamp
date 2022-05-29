package com.bytedance.application.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.bytedance.application.BR;

public class User extends BaseObservable {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        //部分属性更新
        notifyPropertyChanged(BR.name);
        //全部更新
        //notifyChange();
    }
}
