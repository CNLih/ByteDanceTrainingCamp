package com.bytedance.application;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.bytedance.application.base.BaseInitActivity;
import com.bytedance.application.bean.User;
import com.bytedance.application.databinding.ActivityMainBinding;
import com.bytedance.application.model.AppModel;
import com.bytedance.application.model.entity.ChartEntity;

public class MainActivity extends BaseInitActivity<ActivityMainBinding> {
    private static final String TAG = "MAIN_ACTIVITY_TAG";

    @Override
    protected ActivityMainBinding getViewBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initViewsAndEvents() {
        binding.setUser(new User("冲突测试"));
        binding.wvMini.loadUrl("weixin://dl/business/?t=QDZVQEO2z9f");
        AppModel.getInstance().init(this);

        binding.btSend.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("weixin://dl/business/?t=QDZVQEO2z9f"));
            startActivity(intent);
        });

        AppModel.getInstance().setData(new ChartEntity((int)System.currentTimeMillis()));
        Log.d(TAG, "initViewsAndEvents: " + AppModel.getInstance().getData());
        AppModel.getInstance().setLocation("广州");
        Log.d(TAG, "initViewsAndEvents: " + AppModel.getInstance().getLocation());
    }
}
