package com.bytedance.application.bean;

import com.google.gson.annotations.SerializedName;

public class NewsBean {

    @SerializedName("currDate")
    private String currDate;
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
