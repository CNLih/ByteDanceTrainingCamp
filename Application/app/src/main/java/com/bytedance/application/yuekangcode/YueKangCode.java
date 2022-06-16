package com.bytedance.application.yuekangcode;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.bytedance.application.R;

/**
 * Implementation of App Widget functionality.
 */
public class YueKangCode extends AppWidgetProvider {
    private static final String URI = "weixin://dl/business/?t=QDZVQEO2z9f";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.yue_kang_code);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        Intent btIntent = new Intent().setAction(URI);
        Intent btIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("weixin://dl/business/?t=QDZVQEO2z9f"));
        views.setOnClickFillInIntent(R.id.appwidget_text,btIntent1);
        // Instruct the widget manager to update the widget
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
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}