package com.bytedance.application.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import com.bytedance.application.R;

public abstract class BaseInitFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment {

    protected VB binding;
    protected VM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getViewBinding(inflater, container);
        viewModel = getViewModel();
        initViewsAndEvents();
        return binding.getRoot();
    }

    protected abstract VB getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);
    protected abstract VM getViewModel();

    protected abstract void initViewsAndEvents();
}
