package com.bytedance.application.newslist;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class NewsRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NewsRemoteViewsFactory(this.getApplicationContext());
    }
}
