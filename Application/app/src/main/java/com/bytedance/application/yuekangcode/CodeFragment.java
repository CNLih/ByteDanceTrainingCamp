package com.bytedance.application.yuekangcode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bytedance.application.R;
import com.bytedance.application.model.entity.CodeEntity;

public class CodeFragment extends Fragment {
    CodeEntity codeEntity;

    CodeFragment(CodeEntity codeEntity){
        this.codeEntity = codeEntity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nucleic_fragment_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(view.getContext())
                .load(codeEntity.getUri())
                .into((ImageView) view.findViewById(R.id.image_code));
    }
}
