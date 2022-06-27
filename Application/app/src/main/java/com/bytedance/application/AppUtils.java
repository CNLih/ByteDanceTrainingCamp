package com.bytedance.application;

import android.app.PendingIntent;
import android.os.Build;

public class AppUtils {
    public static final String QQ_NEWS_API = "https://api.inews.qq.com/";

    public static final String ACTION_ITEM_CLICK = "intent.action.item.CLICK";
    public static final String ACTION_REFRESH_CLICK = "intent.action.refresh.CLICK";
    public static final String ACTION_LOCATION_CLICK = "intent.action.location.CLICK";
    public static final String ACTION_HEALTH_CODE = "intent.action.health.CLICK";
    public static final String ACTION_NUCLEIC_CODE = "intent.action.nucleic.CLICK";
    public static final String ITEM_ID = "intent.action.CLICK";

    //请求权限标识位
    public static final int GET_IMAGE_REQUEST_CODE = 1;


    //版本适配
    public static final int FLAG_MUTABLE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : 0;
}
