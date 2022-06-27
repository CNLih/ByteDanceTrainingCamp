package com.bytedance.application.yuekangcode;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bytedance.application.AppUtils;
import com.bytedance.application.model.AppModel;
import com.bytedance.application.model.entity.CodeEntity;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private List<CodeEntity> list;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<CodeEntity> list) {
        super(fragmentActivity);
        this.list = list;
    }

    public void notifyItem(List<CodeEntity> list, int position){
        if(list.size() == position){
            this.list = list;
            notifyItemInserted(position);
        }else {
            this.list = list;
            notifyItemChanged(position);
        }
    }

    public void notifyRemoveItem(List<CodeEntity> list, int position){
        this.list = list;
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new CodeFragment(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
