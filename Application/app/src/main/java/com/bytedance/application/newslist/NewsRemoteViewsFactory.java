package com.bytedance.application.newslist;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.RequiresApi;

import com.bytedance.application.AppUtils;
import com.bytedance.application.R;
import com.bytedance.application.bean.NewsBean;
import com.bytedance.application.model.AppModel;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = "news remote factory";
    private final Context mContext;
    private List<NewsBean> list;

    public NewsRemoteViewsFactory(Context context) {
        mContext = context;
        list = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        AppModel.getInstance().init(mContext);
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged: ");
        list = AppModel.getInstance().getNewsList();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public RemoteViews getViewAt(int i) {
        final RemoteViews remoteItem = new RemoteViews(mContext.getPackageName(), R.layout.list_item);
        NewsBean bean = list.get(i);
        remoteItem.setTextViewText(R.id.remote_text_title, bean.getTitle());
        String time = null;
        try {
            time = AppUtils.getTimestampTimeV16(bean.getCurrDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        remoteItem.setTextViewText(R.id.remote_text_time,time);
        //放入intent，点击的时候触发广播
        //不能对集合的item用OnClickPendingIntent，而需要用list的setPendingIntentTemplate与item的fillIntent联动
        //remoteItem.setOnClickPendingIntent(R.id.remote_text, );
        Intent intent = new Intent();
        intent.putExtra(AppUtils.ITEM_ID, bean.getUrl());
        remoteItem.setOnClickFillInIntent(R.id.btn_more, intent);

        return remoteItem;
    }

    @Override
    public RemoteViews getLoadingView() {
        RemoteViews remoteLoading = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item);
        remoteLoading.setTextViewText(R.id.remote_text_title, "加载中");
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
