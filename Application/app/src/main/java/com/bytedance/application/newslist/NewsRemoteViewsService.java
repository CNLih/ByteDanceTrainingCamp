package com.bytedance.application.newslist;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.bytedance.application.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

public class NewsRemoteViewsService extends RemoteViewsService {
    private static final String TAG = "news list service";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NewsRemoteViewsFactory(this.getApplicationContext());
    }
}
