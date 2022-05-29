package com.bytedance.application.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

//参考https://www.jianshu.com/p/997f61d5d94f
public abstract class BaseInitActivity<VB extends ViewBinding> extends BaseActivity{

    protected VB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        initViewsAndEvents();
    }

    protected abstract VB getViewBinding();

    protected abstract void initViewsAndEvents();

}
