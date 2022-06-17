package com.bytedance.application.infolist;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bytedance.application.AppUtils;
import com.bytedance.application.R;
import com.bytedance.application.model.AppModel;

public class InfoRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context mContext;
    private static final int REQUEST_CODE_FROM_COLLECTION_WIDGET = 0;

    public InfoRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        AppModel.getInstance().loadInfoData();
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return AppModel.getInstance().getInfoList().size();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public RemoteViews getViewAt(int i) {
        final RemoteViews remoteItem = new RemoteViews(mContext.getPackageName(), R.layout.list_item);
        CharSequence sequence = AppModel.getInstance().getInfoList().get(i);
        remoteItem.setTextViewText(R.id.remote_text, sequence);
        //放入intent，点击的时候触发广播
        //不能对集合的item用OnClickPendingIntent，而需要用list的setPendingIntentTemplate与item的fillIntent联动
        //remoteItem.setOnClickPendingIntent(R.id.remote_text, );
        Intent intent = new Intent();
        intent.putExtra(AppUtils.ITEM_ID, i);
        remoteItem.setOnClickFillInIntent(R.id.btn_more, intent);

        return remoteItem;
    }

    @Override
    public RemoteViews getLoadingView() {
        RemoteViews remoteLoading = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item);
        remoteLoading.setTextViewText(R.id.remote_text, "加载中");
        return remoteLoading;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }


    //返回当前索引
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
