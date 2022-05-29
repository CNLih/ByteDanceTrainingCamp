package com.bytedance.application;

import androidx.databinding.DataBindingUtil;

import com.bytedance.application.base.BaseInitActivity;
import com.bytedance.application.bean.User;
import com.example.application.R;
import com.example.application.databinding.ActivityMainBinding;

public class MainActivity extends BaseInitActivity<ActivityMainBinding> {

    @Override
    protected ActivityMainBinding getViewBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initViewsAndEvents() {
        binding.setUser(new User("冲突测试"));
    }

}