package com.bytedance.application.datacharts;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.DataBindingUtil;

import com.bytedance.application.R;
import com.bytedance.application.base.BaseInitActivity;
import com.bytedance.application.databinding.DataWidgetConfigureBinding;

/**
 * The configuration screen for the {@link DataWidget DataWidget} AppWidget.
 */
public class DataWidgetConfigureActivity extends BaseInitActivity<DataWidgetConfigureBinding> {

    private static final String PREFS_NAME = "com.bytedance.application.datacharts.DataWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    View.OnClickListener mOnClickListener = v -> {
        final Context context = DataWidgetConfigureActivity.this;

        // When the button is clicked, store the string locally
        String widgetText = binding.appwidgetText.getText().toString();
        saveTitlePref(context, mAppWidgetId, widgetText);

        // It is the responsibility of the configuration activity to update the app widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        DataWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    };

    public DataWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }
//
//    @Override
//    public void onCreate(Bundle icicle) {
//        super.onCreate(icicle);
//
//        // Set the result to CANCELED.  This will cause the widget host to cancel
//        // out of the widget placement if the user presses the back button.
//        setResult(RESULT_CANCELED);
//
//        setContentView(binding.getRoot());
//
//        binding.addButton.setOnClickListener(mOnClickListener);
//
//        // Find the widget id from the intent.
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            mAppWidgetId = extras.getInt(
//                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
//        }
//
//        // If this activity was started with an intent without an app widget ID, finish with an error.
//        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
//            finish();
//            return;
//        }
//
//        mAppWidgetText.setText(loadTitlePref(DataWidgetConfigureActivity.this, mAppWidgetId));
//    }

    @Override
    protected DataWidgetConfigureBinding getViewBinding() {
        setResult(RESULT_CANCELED);
        return DataBindingUtil.setContentView(this, R.layout.data_widget_configure);
    }

    @Override
    protected void initViewsAndEvents() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        binding.addButton.setOnClickListener(mOnClickListener);
        binding.appwidgetText.setText(loadTitlePref(DataWidgetConfigureActivity.this, mAppWidgetId));
    }
}