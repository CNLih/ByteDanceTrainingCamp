package com.bytedance.application.yuekangcode;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bytedance.application.AppUtils;
import com.bytedance.application.R;

/**
 * Implementation of App Widget functionality.
 */
public class YueKangCode extends AppWidgetProvider {
    public static final String TAG = "YUE_KANG_CODE_TAG";
    private static final String URL = "weixin://dl/business/?t=QDZVQEO2z9f";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.yue_kang_code);
        //iews.setTextViewText(R.id.appwidget_text, widgetText);

        //按键监听：健康码
        Intent healthIntent = new Intent(context, YueKangCode.class);
        healthIntent.setAction(AppUtils.ACTION_HEALTH_CODE);
        PendingIntent hPendingIntent = PendingIntent.getBroadcast(context, 0,
                healthIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        //按键监听：核酸码
        Intent checkIntent = new Intent(context, YueKangCode.class);
        checkIntent.setAction(AppUtils.ACTION_NUCLEIC_CODE);
        PendingIntent cPendingIntent = PendingIntent.getBroadcast(context, 0,
                checkIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        views.setOnClickPendingIntent(R.id.appwidget_check_code, hPendingIntent);
        views.setOnClickPendingIntent(R.id.appwidget_nucleic_code, cPendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Intent uriIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        uriIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String action = intent.getAction();
        if(action == null){
            return;
        }
        if(action.equals(AppUtils.ACTION_HEALTH_CODE)){
            try {
                context.startActivity(uriIntent);
            }catch (Exception e){
                Toast.makeText(context, "未检测到微信", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        if(action.equals(AppUtils.ACTION_NUCLEIC_CODE)){
            Intent activityIntent = new Intent(context, NucleicCodeActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);
        }

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