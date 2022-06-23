package com.bytedance.application.newslist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bytedance.application.AppUtils;
import com.bytedance.application.R;

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
        refreshClick.setAction(AppUtils.ACTION_LOCATION_CLICK);
        //列表item监听
        Intent clickItemIntent = new Intent(context, NewsWidget.class);
        clickItemIntent.setAction(AppUtils.ACTION_ITEM_CLICK);

        PendingIntent locationPendingIntent = PendingIntent.getBroadcast(context, 0,
                locationClick, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0,
                refreshClick, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        //设置IMMUTABLE会导致无法接收到intent的extra
        PendingIntent itemPendingIntent = PendingIntent.getBroadcast(context, 0,
                clickItemIntent, PendingIntent.FLAG_UPDATE_CURRENT | AppUtils.FLAG_MUTABLE);

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
            Toast.makeText(context, "点击按钮", Toast.LENGTH_SHORT).show();
        }
        if(action.equals(AppUtils.ACTION_LOCATION_CLICK)){

        }
        if(action.equals(AppUtils.ACTION_ITEM_CLICK) && intent.getExtras() != null){
            Toast.makeText(context, "点击" + intent.getExtras().get(AppUtils.ITEM_ID), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate: ");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
        Log.d(TAG, "onEnabled: ");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
        Log.d(TAG, "onDisabled: ");
    }
}