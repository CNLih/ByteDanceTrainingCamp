package com.bytedance.application.newslist;

import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViewsService;

public abstract class MyViewsService extends RemoteViewsService {
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}
