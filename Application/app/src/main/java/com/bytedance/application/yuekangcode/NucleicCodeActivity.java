package com.bytedance.application.yuekangcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.bytedance.application.R;
import com.bytedance.application.base.BaseInitActivity;
import com.bytedance.application.databinding.ActivityNucleicCodeBinding;

public class NucleicCodeActivity extends BaseInitActivity<ActivityNucleicCodeBinding> {

    @Override
    protected ActivityNucleicCodeBinding getViewBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_nucleic_code);
    }

    @Override
    protected void initViewsAndEvents() {

    }
}