package com.bytedance.application.infolist;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class InfoRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new InfoRemoteViewsFactory(this.getApplicationContext());
    }
}
