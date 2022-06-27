package com.bytedance.application.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.application.model.AppModel;

public class BaseActivity extends AppCompatActivity {
    public BaseActivity mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        AppModel.getInstance().init(this);
    }
}
