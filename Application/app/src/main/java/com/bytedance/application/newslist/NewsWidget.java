package com.bytedance.application.newslist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.bytedance.application.AppUtils;
import com.bytedance.application.R;
import com.bytedance.application.bean.NewsBean;
import com.bytedance.application.model.AppModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Implementation of App Widget functionality.
 */
public class NewsWidget extends AppWidgetProvider {
    private static final String TAG = "INFO_WIDGET_TAG";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.info_widget);

        Intent intent = new Intent(context, NewsRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.appwidget_list, intent);

        //更新按键监听
        Intent refreshClick = new Intent(context, NewsWidget.class);
        refreshClick.setAction(AppUtils.ACTION_REFRESH_CLICK);
        //位置改变监听
        Intent locationClick = new Intent(context, NewsWidget.class);
        locationClick.setAction(AppUtils.ACTION_LOCATION_CLICK);
        //列表item监听
        Intent clickItemIntent = new Intent(context, NewsWidget.class);
        clickItemIntent.setAction(AppUtils.ACTION_ITEM_CLICK);

        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0,
                refreshClick, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        PendingIntent locationPendingIntent = PendingIntent.getBroadcast(context, 0,
                locationClick, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        //设置IMMUTABLE会导致无法接收到intent的extra
        PendingIntent itemPendingIntent = PendingIntent.getBroadcast(context, 0,
                clickItemIntent, PendingIntent.FLAG_UPDATE_CURRENT | AppUtils.FLAG_MUTABLE);

        views.setTextViewText(R.id.tv_time, AppUtils.getTime());
        views.setOnClickPendingIntent(R.id.btn_refresh, refreshPendingIntent);
        views.setOnClickPendingIntent(R.id.btn_location, locationPendingIntent);
        views.setPendingIntentTemplate(R.id.appwidget_list, itemPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.appwidget_list);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        super.onReceive(context, intent);
        String action = intent.getAction();
        if(action == null){
            return;
        }
        if(action.equals(AppUtils.ACTION_REFRESH_CLICK)){
            NewsListWorker.requestWork(context);
        }
        if(action.equals(AppUtils.ACTION_LOCATION_CLICK)){

        }
        if(action.equals(AppUtils.ACTION_ITEM_CLICK) && intent.getExtras() != null){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getExtras().getString(AppUtils.ITEM_ID)));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(browserIntent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate: ");
        //将id保存
        AppModel.getInstance().setWidgets(appWidgetIds);
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
        AppModel.getInstance().init(context);
        //启动worker并定时更新
        NewsListWorker.startSyncWork(context, 360);
        Log.d(TAG, "onEnabled: ");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
        NewsListWorker.stopAllWorks(context);
        Log.d(TAG, "onDisabled: ");
    }

}