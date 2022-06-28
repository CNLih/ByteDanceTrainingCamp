package com.bytedance.application;

import android.app.PendingIntent;
import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AppUtils {
    public static final String QQ_NEWS_API = "https://api.inews.qq.com/";
    //这里不能用localhost表示电脑--而表示手机本机地址
    public static final String LOCAL_NEWS_LIST_API = "http://192.168.3.3:8080/";

    public static final String ACTION_ITEM_CLICK = "intent.action.item.CLICK";
    public static final String ACTION_REFRESH_CLICK = "intent.action.refresh.CLICK";
    public static final String ACTION_LOCATION_CLICK = "intent.action.location.CLICK";
    public static final String ACTION_HEALTH_CODE = "intent.action.health.CLICK";
    public static final String ACTION_NUCLEIC_CODE = "intent.action.nucleic.CLICK";
    public static final String ACTION_ADD_CONFIRM_CLICK = "intent.action.addConfirm.CLICK";
    public static final String ACTION_ADD_ASYMPTOMATIC_CLICK = "intent.action.addAsymptomatic.CLICK";
    public static final String ACTION_EXISTED_CONFIRM_CLICK = "intent.action.existConfirm.CLICK";
    public static final String ITEM_ID = "intent.action.CLICK";

    //请求权限标识位
    public static final int GET_IMAGE_REQUEST_CODE = 1;

    //版本适配
    public static final int FLAG_MUTABLE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : 0;

    public static String getTimestampTimeV16(String str) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        sdf.setTimeZone(tz);
        Date date = sdf.parse(str);
        return date.toString();
    }

    public static String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
        return formatter.format(new Date(System.currentTimeMillis()));
    }
}
