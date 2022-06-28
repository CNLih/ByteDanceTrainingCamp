package com.bytedance.application.newslist;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bytedance.application.R;
import com.bytedance.application.bean.NewsBean;
import com.bytedance.application.model.AppModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListWorker extends Worker {
    private static final String TAG = "WORKER_TAG";
    private static final String WORK_TAG = "news_list";
    private static final String WORKER_ID = "refresh";

    private final AppWidgetManager appWidgetManager;

    public NewsListWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        AppModel.getInstance().init(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: ");

        AppModel.getInstance().loadNewsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NewsBean> news) {
                        //通过bindService更新数据
                        Log.d(TAG, "onNext: ");
                        AppModel.getInstance().setNewsList(news);
                        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext().getApplicationContext());
                        //获取所有桌面上的newsWidgets的id并数据更新。
                        int[] widgets = AppModel.getInstance().getWidgets();
//                        manager.notifyAppWidgetViewDataChanged(widgets, R.id.appwidget_list);
                        for(int widget : widgets){
                            NewsWidget.updateAppWidget(getApplicationContext(), manager, widget);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        //出现网络问题，加载本地数据
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return Result.success();
    }

    /**
     * startSyncWork
     * @param context
     * @param updateInterval  单位分钟 -- TimeUnit.MILLISECONDS
     * @return
     */
    public static Operation startSyncWork(Context context, int updateInterval)
    {
        if (updateInterval <= 0)
        {
            // Log.w(TAG, "Update interval is lower than zero, can't start work");
            return null;
        }
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(NewsListWorker.class,
                updateInterval, TimeUnit.MINUTES,
                PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS, TimeUnit.MILLISECONDS)
                .addTag(WORK_TAG)
                .setConstraints(new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresBatteryNotLow(true)
                        .build())
                .setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        OneTimeWorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS,
                        TimeUnit.MILLISECONDS)
                .build();
        return WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(WORK_TAG, ExistingPeriodicWorkPolicy.KEEP, workRequest);
    }

    public static Operation requestWork(Context context)
    {
        Log.d(TAG, "requestWork: ");
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(NewsListWorker.class)
                .addTag(WORK_TAG)
//                .setConstraints(new Constraints.Builder()
//                        // NOTE 如果wifi连接受限的话, 即便是能连上网, 该Worker也不会执行
//                        .setRequiredNetworkType(NetworkType.CONNECTED)
//                        .build())
                // 此Worker似乎不需要成为加急任务
                // .setExpedited(OutOfQuotaPolicy.DROP_WORK_REQUEST)
                .build();
        return WorkManager.getInstance(context)
                .enqueueUniqueWork(WORK_TAG + WORKER_ID, ExistingWorkPolicy.REPLACE, workRequest);
    }

    public static Operation stopAllWorks(Context context)
    {
        return WorkManager.getInstance(context)
                .cancelAllWorkByTag(WORK_TAG);
    }
}
