package com.bytedance.application.datacharts;

import com.bytedance.application.R;

/**
 * 图标组件的工作类
 */
public class DataChartWorker {
    private static String city;

    private DataChartWorker() {
    }

    public static int getAddConfirmChart() {
        return R.drawable.chart_1;
    }

    public static int getAddAsymptomaticChart() {
        return R.drawable.chart_1;
    }

    public static int getExistConfirmChart() {
        return R.drawable.chart_1;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        DataChartWorker.city = city;
    }
}
