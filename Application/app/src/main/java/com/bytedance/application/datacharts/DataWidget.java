package com.bytedance.application.datacharts;

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
 * App Widget Configuration implemented in {@link DataWidgetConfigureActivity DataWidgetConfigureActivity}
 */
public class DataWidget extends AppWidgetProvider {
    public static final String TAG = "DATA_CHART_CODE_TAG";
    private static RemoteViews views;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = DataWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.data_widget);
        views.setTextViewText(R.id.text_widget_chart_city, widgetText);

        //按键监听：新增确诊
        Intent addConfirmIntent = new Intent(context, DataWidget.class);
        addConfirmIntent.setAction(AppUtils.ACTION_ADD_CONFIRM_CLICK);
        addConfirmIntent.putExtra("appWidgetId", appWidgetId);
        PendingIntent acPendingIntent = PendingIntent.getBroadcast(context, 0,
                addConfirmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.text_widget_chart_addConfirm, acPendingIntent);

        //按键监听：新增无症状
        Intent addAsymptomaticIntent = new Intent(context, DataWidget.class);
        addAsymptomaticIntent.putExtra("appWidgetId", appWidgetId);
        addAsymptomaticIntent.setAction(AppUtils.ACTION_ADD_ASYMPTOMATIC_CLICK);
        PendingIntent aaPendingIntent = PendingIntent.getBroadcast(context, 0,
                addAsymptomaticIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.text_widget_chart_addAsymptomatic, aaPendingIntent);

        //按键监听：现存确诊
        Intent existConfirmIntent = new Intent(context, DataWidget.class);
        existConfirmIntent.putExtra("appWidgetId", appWidgetId);
        existConfirmIntent.setAction(AppUtils.ACTION_EXISTED_CONFIRM_CLICK);
        PendingIntent ecPendingIntent = PendingIntent.getBroadcast(context, 0,
                existConfirmIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.text_widget_chart_existConfirm, ecPendingIntent);

        //按键监听：展示详情
        Intent showDetailsIntent = new Intent(context, DataWidget.class);
        showDetailsIntent.putExtra("appWidgetId", appWidgetId);
        showDetailsIntent.setAction(AppUtils.ACTION_SHOW_DETAILS_CLICK);
        PendingIntent sdPendingIntent = PendingIntent.getBroadcast(context, 0,
                showDetailsIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.iv_widget_chart_chart, sdPendingIntent);

        //按键监听：隐藏详情
        Intent hideDetailsIntent = new Intent(context, DataWidget.class);
        hideDetailsIntent.putExtra("appWidgetId", appWidgetId);
        hideDetailsIntent.setAction(AppUtils.ACTION_HIDE_DETAILS_CLICK);
        PendingIntent hdPendingIntent = PendingIntent.getBroadcast(context, 0,
                hideDetailsIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.iv_widget_chart_details, hdPendingIntent);

        views.setInt(R.id.text_widget_chart_addConfirm, "setBackgroundResource", R.drawable.background_chart_selected);
        views.setImageViewResource(R.id.iv_widget_chart_chart, DataChartWorker.getAddConfirmChart());
        views.setImageViewResource(R.id.iv_widget_chart_details, DataChartWorker.getAddConfirmChart());
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        String action = intent.getAction();
        Log.d(TAG, "onReceive: " + action);

        int appWidgetId = intent.getIntExtra("appWidgetId", -1);

        if (action == null)
            return;
        switch (action) {
            case AppUtils.ACTION_ADD_CONFIRM_CLICK:
                onSelected(context, appWidgetId, 1);
                break;
            case AppUtils.ACTION_ADD_ASYMPTOMATIC_CLICK:
                onSelected(context, appWidgetId, 2);
                break;
            case AppUtils.ACTION_EXISTED_CONFIRM_CLICK:
                onSelected(context, appWidgetId, 3);
                break;
            case AppUtils.ACTION_SHOW_DETAILS_CLICK:
                views.setInt(R.id.iv_widget_chart_details, "setVisibility", 0);
                views.setInt(R.id.widget_chart_background, "setVisibility", 8);
                appWidgetManager.updateAppWidget(appWidgetId, views);
                break;
            case AppUtils.ACTION_HIDE_DETAILS_CLICK:
                views.setInt(R.id.iv_widget_chart_details, "setVisibility", 8);
                views.setInt(R.id.widget_chart_background, "setVisibility", 0);
                appWidgetManager.updateAppWidget(appWidgetId, views);
                break;
        }
    }

    /**
     * 按下按钮时更新Ui
     * @param context context
     * @param appWidgetId 当前组件的id
     * @param i 被按下按钮的位置
     */
    private void onSelected(Context context, int appWidgetId, int i) {
        Log.d(TAG, "onSelected: " + i);
        switch (i) {
            case 1:
                views.setImageViewResource(R.id.iv_widget_chart_chart, DataChartWorker.getAddConfirmChart());
                views.setImageViewResource(R.id.iv_widget_chart_details, DataChartWorker.getAddConfirmChart());
                views.setInt(R.id.text_widget_chart_addConfirm, "setBackgroundResource", R.drawable.background_chart_selected);
                views.setInt(R.id.text_widget_chart_addAsymptomatic, "setBackgroundResource", R.drawable.background_chart_unselected);
                views.setInt(R.id.text_widget_chart_existConfirm, "setBackgroundResource", R.drawable.background_chart_unselected);
                break;
            case 2:
                views.setImageViewResource(R.id.iv_widget_chart_chart, DataChartWorker.getAddAsymptomaticChart());
                views.setImageViewResource(R.id.iv_widget_chart_details, DataChartWorker.getAddAsymptomaticChart());
                views.setInt(R.id.text_widget_chart_addConfirm, "setBackgroundResource", R.drawable.background_chart_unselected);
                views.setInt(R.id.text_widget_chart_addAsymptomatic, "setBackgroundResource", R.drawable.background_chart_selected);
                views.setInt(R.id.text_widget_chart_existConfirm, "setBackgroundResource", R.drawable.background_chart_unselected);
                break;
            case 3:
                views.setImageViewResource(R.id.iv_widget_chart_chart, DataChartWorker.getExistConfirmChart());
                views.setImageViewResource(R.id.iv_widget_chart_details, DataChartWorker.getExistConfirmChart());
                views.setInt(R.id.text_widget_chart_addConfirm, "setBackgroundResource", R.drawable.background_chart_unselected);
                views.setInt(R.id.text_widget_chart_addAsymptomatic, "setBackgroundResource", R.drawable.background_chart_unselected);
                views.setInt(R.id.text_widget_chart_existConfirm, "setBackgroundResource", R.drawable.background_chart_selected);
                break;
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            DataWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}